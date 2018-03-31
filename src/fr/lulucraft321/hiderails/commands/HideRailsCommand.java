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

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.manager.HideRailsManager;
import fr.lulucraft321.hiderails.manager.MessagesManager;
import fr.lulucraft321.hiderails.utils.Checker;
import fr.lulucraft321.hiderails.utils.MaterialData;
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

						if(args[0].equalsIgnoreCase("unhide") || args[0].equalsIgnoreCase("show"))
						{
							Block targetBlock = p.getTargetBlock((Set<Material>) null, 25);
							HideRailsManager.removeRails(p, targetBlock);
							return true;
						}

						MessagesManager.sendHelpPluginMessage(p);
					}

					if(args.length == 2)
					{
						if(args[0].equalsIgnoreCase("hide") || args[0].equalsIgnoreCase("hiderails"))
						{
							Block targetBlock = p.getTargetBlock((Set<Material>) null, 25);

							if(Checker.isRail(targetBlock))
							{
								String in = args[1];

								MaterialData matData = Checker.getMatData(p, in);
								byte data = matData.getData();
								Material mat = matData.getMat();

								if(mat != null)
									HideRailsManager.saveChangedRails(targetBlock, mat, data);
							} else {
								MessagesManager.sendPluginMessage(p, Messages.RAIL_ERROR);
							}

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
