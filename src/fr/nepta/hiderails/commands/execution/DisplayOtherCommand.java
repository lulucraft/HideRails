/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.commands.execution;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.commands.AbstractCommand;
import fr.nepta.hiderails.enums.Messages;
import fr.nepta.hiderails.managers.HideRailsManager;
import fr.nepta.hiderails.managers.MessagesManager;

public class DisplayOtherCommand extends AbstractCommand
{
	public DisplayOtherCommand() {
		super("display", "hiderails.display.other", 2, Arrays.asList("displayother", "otherdisplay"), true);
	}

	/*
	 * Hide/Show hidden blocks (for one target player)
	 */
	@Override
	public void execute(CommandSender sender, String[] args) {
		Player pl = Bukkit.getPlayer(String.valueOf(args[1]));
		if (pl != null) {
			HideRailsManager.displayBlocks(pl);
		} else {
			MessagesManager.sendPluginMessage(sender, Messages.INVALID_PLAYER);
		}
	}
}
