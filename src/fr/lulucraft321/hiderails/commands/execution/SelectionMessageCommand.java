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

import fr.lulucraft321.hiderails.managers.FileConfigurationManager;
import fr.lulucraft321.hiderails.managers.MessagesManager;
import fr.lulucraft321.hiderails.managers.PlayerClaimDataManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;

public class SelectionMessageCommand extends AbstractCommand
{
	public SelectionMessageCommand() {
		super("selectionmessage", "hiderails.selection", 1);
	}

	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
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
	}
}
