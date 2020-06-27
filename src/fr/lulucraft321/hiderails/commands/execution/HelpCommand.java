/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.commands.execution;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.lulucraft321.hiderails.managers.MessagesManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;

public class HelpCommand extends AbstractCommand
{
	public HelpCommand() {
		super("help", "hiderails.help", 1, true);
	}

	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		MessagesManager.sendHelpPluginMessage(sender);
	}
}
