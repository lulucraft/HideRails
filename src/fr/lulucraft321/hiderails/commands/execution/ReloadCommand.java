/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.commands.execution;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.enums.Messages;
import fr.lulucraft321.hiderails.managers.FileConfigurationManager;
import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.managers.MessagesManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;

public class ReloadCommand extends AbstractCommand
{
	public ReloadCommand(CommandSender sender) {
		super(sender, "hiderails.reload");
	}

	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		if (hasPermission()) {
			Bukkit.getServer().getScheduler().cancelTasks(HideRails.getInstance());

			FileConfigurationManager.setupConfigs();
			FileConfigurationManager.saveConfigs();

			HideRailsManager.loadHideRails();

			MessagesManager.sendPluginMessage(sender, Messages.SUCCESS_RELOAD);
		} else {
			// Si sender n'est pas op ou n'a pas la perm
			MessagesManager.sendPluginMessage(sender, Messages.PLAYER_NO_ENOUGH_PERMISSION);
		}
	}
}
