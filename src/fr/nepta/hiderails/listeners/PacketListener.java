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

		if (!p.isOp()) {
			// Replace : if (packet instanceof PacketPlayInUseItem) {
			if (packet.getClass().isAssignableFrom(BukkitNMS.getPacketPlayOutInUseItem())) {
				// Replace : MovingObjectPositionBlock mopb = (PacketPlayInUseItem) packet.c();
				Object mopb = BukkitNMS.getMovingObjectPositionBlock(packet);
				// Replace : EnumDirection dir = mopb.getDirection();
				EnumDirection dir = BukkitNMS.getMovingObjectPositionBlockDirection(mopb);
				// Replace : BlockPosition bp = mopb.getBlockPosition();
				Object bp = BukkitNMS.getBlockPosition(mopb);
				// Replace : int x = bp.getX();
				int x = BukkitNMS.getXBlockPosition(bp);
				// Replace : int y = bp.getY();
				int y = BukkitNMS.getYBlockPosition(bp);
				// Replace : int z = bp.getZ();
				int z = BukkitNMS.getZBlockPosition(bp);

				Location loc = null;
				HiddenRail hr = null;
				switch (dir) {
				case UP:
					loc = new Location(p.getWorld(), x, y + 1, z);// Click in block y-1 (block down)
					break;
				case DOWN:
					loc = new Location(p.getWorld(), x, y - 1, z);// Click in block y+1 (block up)
					break;
				case SOUTH:
					loc = new Location(p.getWorld(), x, y, z + 1);// Click in block z-1
					break;
				case NORTH:
					loc = new Location(p.getWorld(), x, y, z - 1);// Click in block z+1
					break;
				case EAST:
					loc = new Location(p.getWorld(), x + 1, y, z);// Click in block x-1
					break;
				case WEST:
					loc = new Location(p.getWorld(), x - 1, y, z);// Click in block x+1
					break;

				default:
					loc = new Location(p.getWorld(), x, y, z);
					break;
				}

				hr = HideRailsManager.getHiddenRail(loc);
				if (hr != null) {
					return;// Cancel packet read to avoid block update/unhide
				}
			}
		}

		super.channelRead(ctx, packet);
	}
}
