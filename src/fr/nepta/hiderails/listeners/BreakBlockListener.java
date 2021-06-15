/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import fr.nepta.hiderails.enums.Messages;
import fr.nepta.hiderails.managers.FileConfigurationManager;
import fr.nepta.hiderails.managers.HideRailsManager;
import fr.nepta.hiderails.managers.MessagesManager;
import fr.nepta.hiderails.models.railsdata.HiddenRail;
import fr.nepta.hiderails.models.railsdata.HiddenRailsWorld;
import fr.nepta.hiderails.utils.BlocksChecker;

public class BreakBlockListener extends Listener
{
	// Temporary cancel blocks physic
	protected static List<Block> trashList = new ArrayList<>();
	// List of players who broken hidden block
	protected static List<Player> breakBlocks = new ArrayList<>();

	/*
	 * Remove  hiddenBlocks to config
	 */
	@EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBreak(BlockBreakEvent e)
	{
		Block b = e.getBlock();

		if (b == null) return;
		String worldName = b.getWorld().getName();
		if (!FileConfigurationManager.getHiddenRailsConfig().contains(HideRailsManager.HIDDEN_RAILS_PATH + "." + worldName)) return;
		if (HideRailsManager.getRailsToWorld(worldName) == null) return;

		// Add block in trashList -> blockphysicevent
		List<Block> blockFaceChecker = BlocksChecker.getBlockFaceHiddenBlocks(b);
		if (blockFaceChecker != null) {
			if (!blockFaceChecker.isEmpty()) {
				for (Block block : blockFaceChecker) {
					if (!BlocksChecker.isSolid(block)) {
						trashList.add(block);
					}
				}
			}
		}


		HiddenRailsWorld world = HideRailsManager.getWorldHiddenRails(worldName);
		HiddenRail baseB = HideRailsManager.getHiddenRail(b.getLocation());
		Player p = e.getPlayer();

		// If the broken block is not a rail, redstone or sign
		if (BlocksChecker.isSolid(b)) {
			// Check all hidden blocks around the broken block
			if (!trashList.isEmpty()) {
				Iterator<Block> it = trashList.iterator();
				while (it.hasNext()) {
					Block block = it.next();
					if (!BlocksChecker.isSolid(block)) {
						Location bLoc = block.getLocation();
						HiddenRail hRail = HideRailsManager.getHiddenRail(bLoc);

						if (hRail != null) {
							// Remove block around hidden block
							//world.getHiddenRails().remove(hRail);

							// Remove broken hiddenBlock and send broken message to the player
							//delOneHiddenBlock(p, world, baseB, worldName);

							// Send broken hidden block message
							MessagesManager.sendPluginMessage(p, Messages.SUCCESS_BREAK_RAIL);

							// Disable interactChangeBlock for admins
							BreakBlockListener.breakBlocks.add(p);

							// Remove block to avoid ConcurrentModificationException
							// when removing the block in the removeBlocks method
							it.remove();

							// Remove block around hidden block
							HideRailsManager.removeBlocks(p, block, false, true);

							// Show block around hidden broken blocks (enable the player to see the broken block)
							//BukkitNMS.changeBlock(p, block.getType(), block.getData(), block.getX(), block.getY(), block.getZ());
						}
					}
				}
			} else {
				// Remove broken hiddenBlock and send broken message to player
				delOneHiddenBlock(p, world, baseB, worldName);
			}
		} else {
			// Remove broken hiddenBlock and send broken message to player
			delOneHiddenBlock(p, world, baseB, worldName);
		}
	}

	private void delOneHiddenBlock(Player p, HiddenRailsWorld world, HiddenRail baseHBlock, String worldName) {
		// If broken block is an hiddenBlock
		if (baseHBlock != null) {
			// Remove broken hidden block
			world.getHiddenRails().remove(baseHBlock);

			// Disable interactChangeBlock for admins
			BreakBlockListener.breakBlocks.add(p);

			// Send broken hidden block message
			MessagesManager.sendPluginMessage(p, Messages.SUCCESS_BREAK_RAIL);
		}
		// Save deleted blocks
		HideRailsManager.saveWorld(worldName);
	}
}