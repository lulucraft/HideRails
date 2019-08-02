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
import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.managers.MessagesManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;

public class HideSingleCommand extends AbstractCommand
{
	public HideSingleCommand(CommandSender sender) {
		super(sender, "hiderails.hidesingle");
	}

	/*
	 * Hide single block
	 */
	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		if (sender instanceof Player) {
			if (hasPermission()) {
				// args[1] == Material
				HideRailsManager.saveChangedBlocks((Player) sender, args[1], true, true);
			} else {
				// Si sender n'est pas op ou n'a pas la perm
				MessagesManager.sendPluginMessage(sender, Messages.PLAYER_NO_ENOUGH_PERMISSION);
			}
		} else {
			MessagesManager.sendPluginMessage(sender, Messages.SENDER_TYPE_ERROR);
		}
	}

}
