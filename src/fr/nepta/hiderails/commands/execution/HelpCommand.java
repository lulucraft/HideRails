/**
 * Copyright
 * Code under MIT licence
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.commands.execution;

import org.bukkit.command.CommandSender;

import fr.nepta.hiderails.commands.AbstractCommand;
import fr.nepta.hiderails.managers.MessagesManager;

public class HelpCommand extends AbstractCommand
{
	public HelpCommand() {
		super("help", "hiderails.help", 1, true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		MessagesManager.sendHelpPluginMessage(sender);
	}
}
