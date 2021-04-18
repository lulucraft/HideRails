/**
 * Copyright
 * Code under MIT licence
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.commands.execution.HelpCommand;
import fr.nepta.hiderails.enums.Messages;
import fr.nepta.hiderails.managers.MessagesManager;

public class CommandsHandle implements CommandExecutor
{
	private static AbstractCommand helpCommand = new HelpCommand();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		AbstractCommand a = null;

		for (AbstractCommand command : AbstractCommand.commands) {
			if (args.length == command.getArgsNumber()) {
				String arg = args[0].toLowerCase();
				if (command.getCommand().equals(arg)) {
					a = command;
					break;
				} else {
					for (String commandAliase : command.getCommandsAliases()) {
						if (commandAliase.equals(arg)) {
							a = command;
							break;
						}
					}
				}
			}
		}


		// Send help page
		if (a == null) a = CommandsHandle.helpCommand;

		// If console not allowed to execute command
		if (!(sender instanceof Player) && !a.isConsoleAllowed()) {
			MessagesManager.sendPluginMessage(sender, Messages.SENDER_TYPE_ERROR);
			return false;
		}


		if (a.hasPermission(sender)) {
			a.execute(sender, args);
		} else {
			MessagesManager.sendPluginMessage(sender, Messages.PLAYER_NO_ENOUGH_PERMISSION);
		}
		return true;
	}
}
