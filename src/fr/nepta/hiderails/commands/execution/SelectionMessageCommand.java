/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.commands.execution;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.commands.AbstractCommand;
import fr.nepta.hiderails.managers.FileConfigurationManager;
import fr.nepta.hiderails.managers.MessagesManager;
import fr.nepta.hiderails.managers.PlayerClaimDataManager;

public class SelectionMessageCommand extends AbstractCommand
{
	public SelectionMessageCommand() {
		super("selectionmessage", "hiderails.selection", 1);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		boolean b = false;

		if (!PlayerClaimDataManager.isBlacklistedPlayer(p)) {
			PlayerClaimDataManager.addBlacklistedPlayer(p);
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
