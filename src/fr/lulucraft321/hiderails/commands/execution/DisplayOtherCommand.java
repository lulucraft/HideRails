/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.commands.execution;

import java.util.Arrays;

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
	public DisplayOtherCommand() {
		super("display", "hiderails.display.other", 2, Arrays.asList("displayother", "otherdisplay"), true);
	}

	/*
	 * Hide/Show hidden blocks (for one target player)
	 */
	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		Player pl = Bukkit.getPlayer(String.valueOf(args[1]));
		if (pl != null) {
			HideRailsManager.displayBlocks(pl);
		} else {
			MessagesManager.sendPluginMessage(sender, Messages.INVALID_PLAYER);
		}
	}
}
