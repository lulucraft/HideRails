/**
 * Copyright
 * Code under MIT licence
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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
import fr.nepta.hiderails.packets.BukkitNMS;
import fr.nepta.hiderails.utils.BlocksChecker;

public class BreakBlockListener extends Listener
{
	// Temporary cancellable blocks physic
	protected static List<Block> trashList = new ArrayList<>();
	// List of players who broken hidden block
	protected static List<Player> breakBlocks = new ArrayList<>();

	/*
	 * Remove  hiddenBlocks to config
	 */
	@SuppressWarnings("deprecation")
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
				for (Block block : trashList) {
					if (!BlocksChecker.isSolid(block)) {
						Location bLoc = block.getLocation();
						HiddenRail hRail = HideRailsManager.getHiddenRail(bLoc);

						if (hRail != null) {
							Block bl = Bukkit.getWorld(worldName).getBlockAt(bLoc);

							if (bLoc.equals(bLoc)) {
								// Remove block around hidden block
								world.getHiddenRails().remove(hRail);

								// Remove broken hiddenBlock and send broken message to player
								delOneHiddenBlock(p, world, baseB, worldName);

								// Send broken hidden block message
								MessagesManager.sendPluginMessage(p, Messages.SUCCESS_BREAK_RAIL);

								// Block interactChangeBlock for admins
								BreakBlockListener.breakBlocks.add(p);

								// Change block around hidden broken blocks (for the player see broken block)
								BukkitNMS.changeBlock(p, bl.getType(), bl.getData(), bl.getX(), bl.getY(), bl.getZ());
							}
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
		// If broken block is hiddenBlock
		if (baseHBlock != null) {
			// Remove broken hidden block
			world.getHiddenRails().remove(baseHBlock);

			// Block interactChangeBlock for admins
			BreakBlockListener.breakBlocks.add(p);

			// Send broken hidden block message
			MessagesManager.sendPluginMessage(p, Messages.SUCCESS_BREAK_RAIL);
		}
		// Save deleted blocks
		HideRailsManager.saveWorld(worldName);
	}
}