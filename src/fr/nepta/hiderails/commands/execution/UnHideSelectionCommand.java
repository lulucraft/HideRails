/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.commands.execution;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.commands.AbstractCommand;
import fr.nepta.hiderails.enums.Messages;
import fr.nepta.hiderails.managers.HideRailsManager;
import fr.nepta.hiderails.managers.MessagesManager;
import fr.nepta.hiderails.models.ClaimData;
import fr.nepta.hiderails.models.selectionsystem.Cuboid;
import fr.nepta.hiderails.utils.HideRailsSelectionChecker;

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
	public void execute(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		ClaimData sel = HideRailsSelectionChecker.getHideRailsSelection(p);

		if (sel == null) {
			MessagesManager.sendPluginMessage(p, Messages.HIDERAILS_NO_SELECTION);
			return;
		}

		Cuboid cuboid = sel.getCuboid();
		if (cuboid == null) {
			MessagesManager.sendPluginMessage(p, Messages.HIDERAILS_NO_SELECTION);
			return;
		}

		HideRailsManager.removeSelectionBlocks(p, cuboid, true, null);
	}
}
