/**
 * Copyright
 * Code under MIT licence
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.nepta.hiderails.HideRails;
import fr.nepta.hiderails.enums.Version;
import fr.nepta.hiderails.managers.HideRailsManager;
import fr.nepta.hiderails.managers.PlayerClaimDataManager;
import fr.nepta.hiderails.managers.PlayerClaimDataManager.LocType;
import fr.nepta.hiderails.models.railsdata.HiddenRail;
import fr.nepta.hiderails.packets.BukkitNMS;
import fr.nepta.hiderails.utils.BlocksChecker;
import fr.nepta.hiderails.utils.JavaChecker;

public class BlockClickListener extends Listener
{
	/*
	 * Refresh hidden signs after clicking
	 */
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		final Block block = e.getClickedBlock();
		if (block == null) return;
		final Player p = e.getPlayer();
		final Location blockLoc = block.getLocation();


		// HideRails selection system
		if (p.isOp() || p.hasPermission("hiderails.admin") || p.hasPermission("hiderails.selection")) {
			final ItemStack item = e.getItem();

			if (item != null) {
				final Material itType = item.getType();

				if (itType != null) {
					boolean b = false;

					if (HideRails.version == Version.V1_12) {
						if (itType == JavaChecker.enumCheck("WOOD_AXE")) b = true;
					} else if (HideRails.version == Version.V1_13 || HideRails.version == Version.V1_14 || HideRails.version == Version.V1_15) {
						if (itType == JavaChecker.enumCheck("LEGACY_WOOD_AXE") || itType == JavaChecker.enumCheck("WOODEN_AXE")) b = true;
					}

					if (b) {
						e.setCancelled(true);
						final Action a = e.getAction();

						// Save selection pos1
						if (a == Action.LEFT_CLICK_BLOCK)
							PlayerClaimDataManager.setPos(p, blockLoc, LocType.LOC1);

						// Save selection pos2
						if (a == Action.RIGHT_CLICK_BLOCK)
							PlayerClaimDataManager.setPos(p, blockLoc, LocType.LOC2);
					}
				}
			}
		}


		if (HideRailsManager.isInPlayerWhoDisplayedBlocks(p)) return;

		if (p.isOp() || p.hasPermission("hiderails.admin")) {
			// If player click in hiddenSign
			if (BlocksChecker.isSign(block)) {
				Sign s = (Sign) Bukkit.getServer().getWorld(block.getWorld().getName()).getBlockAt(blockLoc).getState();
				if (HideRailsManager.getHiddenRail(s.getLocation()) != null) {
					s.setLine(0, s.getLine(0));
					s.setLine(1, s.getLine(1));
					s.setLine(2, s.getLine(2));
					s.setLine(3, s.getLine(3));
					s.update(true);
				}
			} else {
				// If player click in block around hiddenSign
				Block checkedBlock = BlocksChecker.getBlockFaceHiddenSign(block);
				if (checkedBlock != null) {
					if (BlocksChecker.isSign(checkedBlock)) {
						if (HideRailsManager.getHiddenRail(checkedBlock.getLocation()) != null) {
							Sign s = (Sign) Bukkit.getServer().getWorld(checkedBlock.getWorld().getName()).getBlockAt(checkedBlock.getLocation()).getState();
							s.setLine(0, s.getLine(0));
							s.setLine(1, s.getLine(1));
							s.setLine(2, s.getLine(2));
							s.setLine(3, s.getLine(3));
							s.update(true);
						}
					}
				}
			}
		}


		// Renvoie des packets si le joueur n'est pas OP et qu'il clique sur un block masque
		List<Block> checked = BlocksChecker.getBlockFaceHiddenBlocks(block);
		if (checked != null) {
			if (!checked.isEmpty()) {
				for (Block checkedB : checked) {
					if (BlocksChecker.checkBlockIfActive(checkedB)) {
						HiddenRail hRail = HideRailsManager.getHiddenRail(checkedB.getLocation());

						if (hRail != null) {
							Location loc = hRail.getLocation();

//							if (!p.isOp() && !p.hasPermission("hiderails.admin")) {
//								if (SpamPlayerDataManager.getSpamNumber(p) >= HideRailsManager.max_spam_nbr) {
//									SpamPlayerDataManager.delPlayer(p);
//									if (HideRailsManager.spam_kick) {
//										p.kickPlayer(MessagesManager.getColoredMessage(Messages.KICK_SPAM_BLOCK));
//										return;
//									}
//								}
//								e.setUseInteractedBlock(PlayerInteractEvent.Result.DENY);
//								e.setUseItemInHand(PlayerInteractEvent.Result.DENY);
//
//								// Si le joueur n'a pas de task de suppresion en cours
//								if (SpamPlayerDataManager.getPendingTask(p) == null)
//									SpamPlayerDataManager.setPendingTask(p, Bukkit.getServer().getScheduler().runTaskLater(HideRails.getInstance(), () -> SpamPlayerDataManager.delPlayer(p), 50L));
//
//								// Re-send block change packet
//								SpamPlayerDataManager.addPlayerSpamTask(p, Bukkit.getServer().getScheduler().runTaskLater(HideRails.getInstance(), () -> {
//									BukkitNMS.changeBlock(p, hRail.getMaterial(), hRail.getData(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
//								}, 1L));
//							} else {
							if (p.isOp() || p.hasPermission("hiderails.admin")) {
								if (!BreakBlockListener.trashList.contains(checkedB)) {
									Bukkit.getServer().getScheduler().runTaskLater(HideRails.getInstance(), () -> {
										if (!BreakBlockListener.breakBlocks.contains(p)) {
											BukkitNMS.changeBlock(p, hRail.getMaterial(), hRail.getData(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
										} else {
											Bukkit.getServer().getScheduler().runTaskLater(HideRails.getInstance(), () -> BreakBlockListener.breakBlocks.remove(p), 20L);
										}
									}, 20L);
								}
							}
						}
					}
				}
			}
		}
	}
}
