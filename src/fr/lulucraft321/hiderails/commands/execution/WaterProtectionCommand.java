/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.commands.execution;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.enums.Messages;
import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.managers.MessagesManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;
import fr.lulucraft321.hiderails.utils.checkers.Checker;

public class WaterProtectionCommand extends AbstractCommand
{
	public WaterProtectionCommand(CommandSender sender) {
		super(sender, "hiderails.waterprotection");
	}

	/*
	 * Change waterprotection status in world
	 */
	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		if (sender instanceof Player) {
			if (hasPermission()) {
				Player p = (Player) sender;
				List<World> world = Bukkit.getWorlds();
				String worldInput = String.valueOf(args[1]);

				if (world.contains(Bukkit.getServer().getWorld(worldInput))) {
					// args[2] == boolean value
					String bInput = Checker.getBoolean(args[2].toLowerCase().toString());

					if (bInput == null) {
						MessagesManager.sendHelpPluginMessage(p);
					} else {
						HideRailsManager.setWaterProtection(p, worldInput, Boolean.parseBoolean(bInput));
					}
				} else {
					MessagesManager.sendPluginMessage(p, Messages.INVALID_WORLDNAME);
				}

			} else {
				// Si sender n'est pas op ou n'a pas la perm
				MessagesManager.sendPluginMessage(sender, Messages.PLAYER_NO_ENOUGH_PERMISSION);
			}
		} else {
			MessagesManager.sendPluginMessage(sender, Messages.SENDER_TYPE_ERROR);
		}
	}

}
