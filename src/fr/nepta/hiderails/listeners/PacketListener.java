/**
 * @author Nepta_
 *
 */
package fr.nepta.hiderails.listeners;

import fr.nepta.hiderails.nms.BukkitNMS;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.server.v1_16_R3.EnumDirection;
import net.minecraft.server.v1_16_R3.MovingObjectPositionBlock;

public class PacketListener extends ChannelDuplexHandler {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {

		// Replace : if (packet instanceof PacketPlayInUseItem) {
		if (packet.getClass().isAssignableFrom(BukkitNMS.getPacketPlayOutInUseItem())) {
//			Method c = NMSClass.getNMSClass("PacketPlayInUseItem").getMethod("c", null);
//			Object mopb = NMSClass.invoke(moving_object_position_block_field, packet, null);
			Object mopb = BukkitNMS.getMovingObjectPositionBlock(packet);
//			BlockPosition bp = ((MovingObjectPositionBlock) mopb).getBlockPosition();
			EnumDirection dir = ((MovingObjectPositionBlock) mopb).getDirection();

//			PacketPlayInUseItem p = (PacketPlayInUseItem) packet;
//			MovingObjectPositionBlock c = p.c();
//			BlockPosition bp = c.getBlockPosition();
//			int x = bp.getX();

//			long s = System.nanoTime();
//			System.err.println(s);
			Object bp = BukkitNMS.getBlockPosition(mopb);
			int x = BukkitNMS.getXBlockPosition(bp);
			int y = BukkitNMS.getYBlockPosition(bp);
			int z = BukkitNMS.getZBlockPosition(bp);
//			long e = System.nanoTime();
//			double el = e - s;
//			System.err.println(" " + (el/1_000_000_000));
//
//			System.err.println("-----------------------");
//			s = System.nanoTime();
//			System.err.println(s);
//			x = BukkitNMS.getXBlockPosition1(mopb);
//			e = System.nanoTime();
//			el = e - s;
//			System.err.println(" " + (el/1_000_000_000));
//			System.err.println("-----------------------");

//			int y = bp.getY();
//			int z = bp.getZ();
//			EnumDirection dir = c.getDirection();
			if (
					x == -10 && y == 10 && z == 0
					|| x == -10 && y == 9 && z == 0 && dir == EnumDirection.UP//Bloc y-1 (au dessous)
					|| x == -10 && y == 11 && z == 0 && dir == EnumDirection.DOWN//Bloc y+1 (au dessus)
					|| x == -10 && y == 10 && z == -1 && dir == EnumDirection.SOUTH//Bloc z-1
					|| x == -10 && y == 10 && z == 1 && dir == EnumDirection.NORTH//Bloc z+1
					|| x == -11 && y == 10 && z == 0 && dir == EnumDirection.EAST//Bloc x-1
					|| x == -9 && y == 10 && z == 0 && dir == EnumDirection.WEST//Bloc x+1
					) {
				// TODO : ACTION
				return;//Cancel packet read to avoid block update
			}
		}
		//

		super.channelRead(ctx, packet);
	}
}
