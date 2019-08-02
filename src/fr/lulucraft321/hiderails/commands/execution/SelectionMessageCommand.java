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
import fr.lulucraft321.hiderails.managers.FileConfigurationManager;
import fr.lulucraft321.hiderails.managers.MessagesManager;
import fr.lulucraft321.hiderails.managers.PlayerClaimDataManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;

public class SelectionMessageCommand extends AbstractCommand
{
	public SelectionMessageCommand(CommandSender sender) {
		super(sender, "hiderails.selection");
	}

	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		if (sender instanceof Player) {
			if (hasPermission()) {
				final Player p = (Player) sender;
				boolean b = false;

				if (!PlayerClaimDataManager.isBlacklistedPlayer(p)) {
					PlayerClaimDataManager.addBlacklistedPlayer(p);
					b = false;
				} else {
					PlayerClaimDataManager.delBlacklistedPlayer(p);
					b = true;
				}

				// Save in config file
				FileConfigurationManager.getPlayersDataConfig().set(p.getName() + ".selectionMessage", b);
				FileConfigurationManager.saveConfigs();

				// Send message
				MessagesManager.sendChangeStatusSelectionMessage(p, b);
			} else {
				// Si sender n'est pas op ou n'a pas la perm
				MessagesManager.sendPluginMessage(sender, Messages.PLAYER_NO_ENOUGH_PERMISSION);
			}
		} else {
			MessagesManager.sendPluginMessage(sender, Messages.SENDER_TYPE_ERROR);
		}
	}
}
