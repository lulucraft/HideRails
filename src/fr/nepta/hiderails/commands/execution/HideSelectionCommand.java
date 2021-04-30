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

public class HideSelectionCommand extends AbstractCommand
{
	public HideSelectionCommand() {
		super("hideselection", "hiderails.hideselection", 2, Arrays.asList("hideselect", "hidesel", "selectionhide", "selhide"));
	}

	/*
	 * Hide blocks with HideRails selection
	 */
	@Override
	public void execute(CommandSender sender, String[] args) {
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

		// args[1] == Material
		HideRailsManager.hideSelectionBlocks(p, cuboid, args[1], true, null);
	}

}
