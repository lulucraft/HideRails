/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.commands;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.manager.HideRailsManager;
import fr.lulucraft321.hiderails.manager.MessagesManager;
import fr.lulucraft321.hiderails.manager.PlayerCommandBackupManager;
import fr.lulucraft321.hiderails.utils.Checker;
import fr.lulucraft321.hiderails.utils.Messages;

public class HideRailsCommand implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(sender instanceof Player)
		{
			Player p = (Player) sender;

			if(p.hasPermission("hiderails.admin"))
			{
				if(cmd.getName().equalsIgnoreCase("hiderails"))
				{
					if(args.length == 0)
					{
						MessagesManager.sendHelpPluginMessage(p);
					}

					if(args.length == 1)
					{
						if(args[0].equalsIgnoreCase("reload"))
						{
							Bukkit.getServer().getScheduler().cancelTasks(HideRails.getInstance());

							HideRails.getInstance().reloadConfig();
							HideRails.getInstance().saveConfig();
							HideRails.getInstance().setupConfig();
							HideRails.getInstance().saveConfigs();

							HideRailsManager.loadHideRails();

							MessagesManager.sendPluginMessage(p, Messages.SUCCESS_RELOAD);
							return true;
						}

						if(args[0].equalsIgnoreCase("return") || args[0].equalsIgnoreCase("undo"))
						{
							PlayerCommandBackupManager.restoreBackupRails(p);
							return true;
						}


						/* UnHide Rails with WorldEdit selection */
						if(args[0].equalsIgnoreCase("unhideselect") || args[0].equalsIgnoreCase("unhideselection"))
						{
							Selection sel = Checker.getWorldeditSelection(p);
							if (sel == null) {
								MessagesManager.sendPluginMessage(p, Messages.WORLDEDIT_NO_SELECTION);
								return true;
							}

							HideRailsManager.removeSelectionBlocks(p, sel, true);
							return true;
						}


						/* UnHide single Rail */
						if(args[0].equalsIgnoreCase("unhide") || args[0].equalsIgnoreCase("show"))
						{
							Block targetBlock = p.getTargetBlock((Set<Material>) null, 25);
							HideRailsManager.removeBlocks(p, targetBlock, true, false);
							return true;
						}

						/* UnHide Rails */
						if(args[0].equalsIgnoreCase("singleunhide")
								|| args[0].equalsIgnoreCase("solounhide")
								|| args[0].equalsIgnoreCase("unhideone")
								|| args[0].equalsIgnoreCase("unhidesingle")
								|| args[0].equalsIgnoreCase("unhidesolo")
								|| args[0].equalsIgnoreCase("showsingle")
								|| args[0].equalsIgnoreCase("showsolo")
								|| args[0].equalsIgnoreCase("singleshow")
								|| args[0].equalsIgnoreCase("soloshow"))
						{
							Block targetBlock = p.getTargetBlock((Set<Material>) null, 25);
							HideRailsManager.removeBlocks(p, targetBlock, true, true);
							return true;
						}

						MessagesManager.sendHelpPluginMessage(p);
					}

					if(args.length == 2)
					{

						/* Hide Rails with WorldEdit selection */
						if(args[0].equalsIgnoreCase("hideselect") || args[0].equalsIgnoreCase("hideselection"))
						{
							String in = args[1];
							Selection sel = Checker.getWorldeditSelection(p);
							if (sel == null) {
								MessagesManager.sendPluginMessage(p, Messages.WORLDEDIT_NO_SELECTION);
								return true;
							}

							HideRailsManager.hideSelectionBlocks(p, sel, in, true);
							return true;
						}


						/* Hide Rails */
						if(args[0].equalsIgnoreCase("hide") || args[0].equalsIgnoreCase("hiderails"))
						{
							String in = args[1];
							HideRailsManager.saveChangedBlocks(p, in, true, false);
							return true;
						}

						/* Hide single Rail */
						if(args[0].equalsIgnoreCase("hidesingle")
								|| args[0].equalsIgnoreCase("hidesolo")
								|| args[0].equalsIgnoreCase("hideone")
								|| args[0].equalsIgnoreCase("hideone")
								|| args[0].equalsIgnoreCase("singlehide")
								|| args[0].equalsIgnoreCase("solohide")
								|| args[0].equalsIgnoreCase("showone"))
						{
							String in = args[1];
							HideRailsManager.saveChangedBlocks(p, in, true, true);
							return true;
						}

						MessagesManager.sendHelpPluginMessage(p);
					}

					if(args.length == 3)
					{
						if(args[0].equalsIgnoreCase("waterprotection") || args[0].equalsIgnoreCase("waterprotect"))
						{
							List<World> world = Bukkit.getWorlds();
							String worldInput = String.valueOf(args[1]);

							if(world.contains(Bukkit.getServer().getWorld(worldInput)))
							{
								String bInput = Checker.getBoolean(args[2].toLowerCase().toString());

								if(bInput == null) {
									MessagesManager.sendHelpPluginMessage(p);
								} else {
									HideRailsManager.setWaterProtection(p, worldInput, Boolean.parseBoolean(bInput));
								}
							} else {
								MessagesManager.sendPluginMessage(p, Messages.INVALID_WORLDNAME);
							}

							return true;
						}

						MessagesManager.sendHelpPluginMessage(p);
					}
				}
			} else {
				MessagesManager.sendPluginMessage(p, Messages.PLAYER_NO_ENOUGH_PERMISSION);
			}
		} else {
			MessagesManager.sendPluginMessage(sender, Messages.SENDER_TYPE_ERROR);
		}

		return false;
	}
}
