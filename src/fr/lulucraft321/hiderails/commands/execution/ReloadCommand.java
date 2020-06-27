/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.commands.execution;

import java.util.Arrays;

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
	public ReloadCommand() {
		super("reload", "hiderails.reload", 1, Arrays.asList("rel", "rl"), true);
	}

	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		Bukkit.getServer().getScheduler().cancelTasks(HideRails.getInstance());

		FileConfigurationManager.setupConfigs();
		FileConfigurationManager.saveConfigs();

		HideRailsManager.loadHideRails();

		MessagesManager.sendPluginMessage(sender, Messages.SUCCESS_RELOAD);
	}
}
