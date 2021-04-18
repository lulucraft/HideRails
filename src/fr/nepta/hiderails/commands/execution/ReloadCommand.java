/**
 * Copyright
 * Code under MIT licence
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.commands.execution;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import fr.nepta.hiderails.HideRails;
import fr.nepta.hiderails.commands.AbstractCommand;
import fr.nepta.hiderails.enums.Messages;
import fr.nepta.hiderails.managers.FileConfigurationManager;
import fr.nepta.hiderails.managers.HideRailsManager;
import fr.nepta.hiderails.managers.MessagesManager;

public class ReloadCommand extends AbstractCommand
{
	public ReloadCommand() {
		super("reload", "hiderails.reload", 1, Arrays.asList("rel", "rl"), true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Bukkit.getServer().getScheduler().cancelTasks(HideRails.getInstance());

		FileConfigurationManager.setupConfigs();
		FileConfigurationManager.saveConfigs();

		HideRailsManager.loadHideRails();

		MessagesManager.sendPluginMessage(sender, Messages.SUCCESS_RELOAD);
	}
}
