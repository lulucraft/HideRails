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

public class HideSelectionCommand extends AbstractCommand
{
	public HideSelectionCommand() {
		super("hideselection", "hiderails.hideselection", 2, Arrays.asList("hideselect", "hidesel", "selectionhide", "selhide"));
	}

	/*
	 * Hide blocks with HideRails selection
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

		// args[1] == Material
		HideRailsManager.hideSelectionBlocks(p, cuboid, args[1], true, null);
	}

}
