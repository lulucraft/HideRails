/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.enums.EnumDirection;
import fr.nepta.hiderails.managers.HideRailsManager;
import fr.nepta.hiderails.models.railsdata.HiddenRail;
import fr.nepta.hiderails.nms.BukkitNMS;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

public class PacketListener extends ChannelDuplexHandler {

	private Player p;

	public PacketListener(Player player) {
		this.p = player;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {

		// Replace : if (packet instanceof PacketPlayInUseItem) {
		if (packet.getClass().isAssignableFrom(BukkitNMS.getPacketPlayOutInUseItem())) {
//			BlockPosition bp = ((MovingObjectPositionBlock) mopb).getBlockPosition();
//			EnumDirection dir = ((MovingObjectPositionBlock) mopb).getDirection();

//			PacketPlayInUseItem p = (PacketPlayInUseItem) packet;
//			MovingObjectPositionBlock c = p.c();
//			BlockPosition bp = c.getBlockPosition();

//			int x = bp.getX();
//			int y = bp.getY();
//			int z = bp.getZ();
//			EnumDirection dir = c.getDirection();

			Object mopb = BukkitNMS.getMovingObjectPositionBlock(packet);
			EnumDirection dir = BukkitNMS.getMovingObjectPositionBlockDirection(mopb);
			Object bp = BukkitNMS.getBlockPosition(mopb);
			int x = BukkitNMS.getXBlockPosition(bp);
			int y = BukkitNMS.getYBlockPosition(bp);
			int z = BukkitNMS.getZBlockPosition(bp);

			Location loc = new Location(p.getWorld(), x, y, z);
			HiddenRail hr = HideRailsManager.getHiddenRail(loc);
			System.err.println(loc.toString());
			if (hr != null) {
				Location hrLoc = hr.getLocation();
				System.err.println(hrLoc.toString());
				if (loc.equals(hrLoc)) {
					return;
				}

				int hrX = hrLoc.getBlockX();
				int hrY = hrLoc.getBlockY();
				int hrZ = hrLoc.getBlockZ();
				if (
					x == -10 && y == 10 && z == 0
					|| x == -10 && y == 9 && z == 0 && dir == EnumDirection.UP//Block y-1 (down)
					|| x == -10 && y == 11 && z == 0 && dir == EnumDirection.DOWN//Block y+1 (up)
					|| x == -10 && y == 10 && z == -1 && dir == EnumDirection.SOUTH//Block z-1
					|| x == -10 && y == 10 && z == 1 && dir == EnumDirection.NORTH//Block z+1
					|| x == -11 && y == 10 && z == 0 && dir == EnumDirection.EAST//Block x-1
					|| x == -9 && y == 10 && z == 0 && dir == EnumDirection.WEST//Block x+1
					) {
					// TODO : ACTION
					return;//Cancel packet read to avoid block update/unhide
				}
			}
		}

		super.channelRead(ctx, packet);
	}
}
