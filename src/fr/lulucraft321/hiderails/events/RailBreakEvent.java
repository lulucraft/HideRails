/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;

import fr.lulucraft321.hiderails.enums.Messages;
import fr.lulucraft321.hiderails.managers.FileConfigurationManager;
import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.managers.MessagesManager;
import fr.lulucraft321.hiderails.reflection.BukkitNMS;
import fr.lulucraft321.hiderails.utils.Checker;
import fr.lulucraft321.hiderails.utils.railsdata.HiddenRail;
import fr.lulucraft321.hiderails.utils.railsdata.HiddenRailsWorld;

public class RailBreakEvent implements Listener
{
	// Temporary cancellable blocks physic
	private List<Block> trashList = new ArrayList<>();

	@SuppressWarnings("deprecation")
	@EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBreak(BlockBreakEvent e)
	{
		Block b = e.getBlock();

		if (b == null) return;
		if (!Checker.isRail(b) && !Checker.isIronBar(b) && !Checker.isCommandBlock(b) && !Checker.isRedstone(b) && !Checker.isSign(b)) return;
		String worldName = b.getWorld().getName();
		if (!FileConfigurationManager.getHiddenRailsConfig().contains(HideRailsManager.path + "." + worldName)) return;
		if (HideRailsManager.getRailsToWorld(worldName) == null) return;

		final BlockFace[] faces = new BlockFace[]{ BlockFace.UP, BlockFace.DOWN, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.EAST, BlockFace.WEST };
		for (BlockFace blockFace : faces)
		{
			Block block = b.getRelative(blockFace);

			if (Checker.isRail(block) || Checker.isRedstone(block)  || Checker.isSign(block)) {
				trashList.add(block);
			}
		}

		HiddenRailsWorld world = HideRailsManager.getWorldHiddenRails(worldName);
		boolean bool = false;

		for (HiddenRail hRail : HideRailsManager.getRailsToWorld(worldName))
		{
			Block block = b.getRelative(BlockFace.UP);
			Block bl = Bukkit.getWorld(block.getWorld().getName()).getBlockAt(block.getLocation());
			Location hLoc = hRail.getLocation();

			if (block.getLocation().equals(hLoc))
			{
				BukkitNMS.changeBlock(e.getPlayer(), bl.getType(), bl.getData(), bl.getX(), bl.getY(), bl.getZ());
				world.getHiddenRails().remove(hRail);
				HideRailsManager.saveWorld(worldName);
				MessagesManager.sendPluginMessage(e.getPlayer(), Messages.SUCCESS_BREAK_RAIL);
				bool = true;
			}

			if(b.getLocation().equals(hLoc))
			{
				BukkitNMS.changeBlock(e.getPlayer(), bl.getType(), bl.getData(), bl.getX(), bl.getY(), bl.getZ());
				BukkitNMS.changeBlock(e.getPlayer(), hRail.getMaterial(), hRail.getData(), hRail.getLocation().getBlockX(), hRail.getLocation().getBlockY(), hRail.getLocation().getBlockZ());
				HiddenRail upHRail = HideRailsManager.getHiddenRail(bl.getLocation());
				if (upHRail != null) {
					world.getHiddenRails().remove(upHRail);
				}
				world.getHiddenRails().remove(hRail);
				HideRailsManager.saveWorld(worldName);
				MessagesManager.sendPluginMessage(e.getPlayer(), Messages.SUCCESS_BREAK_RAIL);
				bool = true;
			}

			if (bool) return;
		}
	}


	/*
	 * Disable breaking block around hidden rail if player break hiddenrail
	 */
	@EventHandler (priority = EventPriority.MONITOR)
	public void onPhysic(BlockPhysicsEvent e)
	{
		Block b = e.getBlock();

		if (Checker.isRail(b) || Checker.isRedstone(b)  || Checker.isSign(b)) {
			if (trashList.contains(b)) {
				e.setCancelled(true);
				trashList.remove(b);
			}
		}
	}
}
