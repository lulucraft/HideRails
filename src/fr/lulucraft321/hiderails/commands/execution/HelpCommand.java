/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.commands.execution;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.lulucraft321.hiderails.enums.Messages;
import fr.lulucraft321.hiderails.managers.MessagesManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;

public class HelpCommand extends AbstractCommand
{
	public HelpCommand(CommandSender sender) {
		super(sender, "hiderails.help");
	}

	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		if (hasPermission()) {
			MessagesManager.sendHelpPluginMessage(sender);
		} else {
			// Si sender n'est pas op ou n'a pas la perm
			MessagesManager.sendPluginMessage(sender, Messages.PLAYER_NO_ENOUGH_PERMISSION);
		}
	}
}
