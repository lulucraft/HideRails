/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.commands.execution;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.enums.Messages;
import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.managers.MessagesManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;

public class DisplayOtherCommand extends AbstractCommand
{
	public DisplayOtherCommand(CommandSender sender) {
		super(sender, "hiderails.display.other");
	}

	/*
	 * Hide/Show hidden blocks (for one target player)
	 */
	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		if (hasPermission()) {
			Player pl = Bukkit.getPlayer(String.valueOf(args[1]));
			if (pl != null) {
				HideRailsManager.displayBlocks(pl);
				MessagesManager.sendDisplayChangeMessage(sender, Messages.DISPLAY_HIDDEN_BLOCKS, HideRailsManager.displayBlocksPlayers.contains(pl));
			} else {
				MessagesManager.sendPluginMessage(sender, Messages.INVALID_PLAYER);
			}
		} else {
			// Si sender n'est pas op ou n'a pas la perm
			MessagesManager.sendPluginMessage(sender, Messages.PLAYER_NO_ENOUGH_PERMISSION);
		}
	}
}
