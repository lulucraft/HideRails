/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.commands.execution;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.enums.Messages;
import fr.lulucraft321.hiderails.managers.MessagesManager;
import fr.lulucraft321.hiderails.managers.PlayerCommandBackupManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;

public class RestoreBackupCommand extends AbstractCommand
{
	public RestoreBackupCommand(CommandSender sender) {
		super(sender, "hiderails.return");
	}

	/*
	 * Restore backup player command
	 */
	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		if(sender instanceof Player) {
			if (hasPermission()) {
				PlayerCommandBackupManager.restoreBackupRails((Player) sender);
			} else {
				// Si sender n'est pas op ou n'a pas la perm
				MessagesManager.sendPluginMessage(sender, Messages.PLAYER_NO_ENOUGH_PERMISSION);
			}
		} else {
			MessagesManager.sendPluginMessage(sender, Messages.SENDER_TYPE_ERROR);
		}
	}

}
