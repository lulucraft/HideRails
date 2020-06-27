/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.commands.execution;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.enums.Messages;
import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.managers.MessagesManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;
import fr.lulucraft321.hiderails.utils.checkers.HideRailsSelectionChecker;
import fr.lulucraft321.hiderails.utils.data.ClaimData;
import fr.lulucraft321.hiderails.utils.selectionsystem.Cuboid;

public class UnHideSelectionCommand extends AbstractCommand
{
	public UnHideSelectionCommand() {
		super("unhideselection", "hiderails.unhideselection", 1,
				Arrays.asList("unhideselect", "unhidesel", "showselection", "showselect", "showsel", "selectionunhide", "selectunhide", "selectionshow", "selectshow", "selshow"));
	}

	/*
	 * UnHide blocks with HideRails selection
	 */
	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		final Player p = (Player) sender;
		final ClaimData sel = HideRailsSelectionChecker.getHideRailsSelection(p);

		if (sel == null) {
			MessagesManager.sendPluginMessage(p, Messages.HIDERAILS_NO_SELECTION);
			return;
		}

		final Cuboid cuboid = sel.getCuboid();
		if (cuboid == null) {
			MessagesManager.sendPluginMessage(p, Messages.HIDERAILS_NO_SELECTION);
			return;
		}

		HideRailsManager.removeSelectionBlocks(p, cuboid, true, null);
	}
}
