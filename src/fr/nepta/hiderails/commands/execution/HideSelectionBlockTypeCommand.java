/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.commands.execution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.commands.AbstractCommand;
import fr.nepta.hiderails.enums.BlockReplacementType;
import fr.nepta.hiderails.enums.Messages;
import fr.nepta.hiderails.managers.HideRailsManager;
import fr.nepta.hiderails.managers.MessagesManager;
import fr.nepta.hiderails.models.ClaimData;
import fr.nepta.hiderails.models.selectionsystem.Cuboid;
import fr.nepta.hiderails.utils.HideRailsSelectionChecker;

public class HideSelectionBlockTypeCommand extends AbstractCommand
{
	public HideSelectionBlockTypeCommand() {
		super("hideselection", "hiderails.hideselection", 3, Arrays.asList("hideselect", "hidesel", "selectionhide", "selhide"));
	}

	/*
	 * Hide hiddenBlocks type with HideRails selection
	 */
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

		List<BlockReplacementType> types = new ArrayList<>();
		String[] splitter = args[1].split(",");
		for (int i = 0; i < splitter.length; i++) {
			BlockReplacementType brt = BlockReplacementType.getBlockReplacementType(splitter[i]);
			if (brt != null)
				types.add(brt);
		}

		HideRailsManager.hideSelectionBlocks(p, cuboid, args[2], true, types);
	}
}
