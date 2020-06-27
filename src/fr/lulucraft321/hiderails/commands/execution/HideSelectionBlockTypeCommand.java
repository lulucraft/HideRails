/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.commands.execution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
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

public class HideSelectionBlockTypeCommand extends AbstractCommand
{
	public HideSelectionBlockTypeCommand() {
		super("hideselection", "hiderails.hideselection", 3, Arrays.asList("hideselect", "hidesel", "selectionhide", "selhide"));
	}

	/*
	 * Hide hiddenBlocks type with HideRails selection
	 */
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

		List<Material> types = new ArrayList<>();
		String[] splitter = args[1].split(",");
		for (int i = 0; i < splitter.length; i++) {
			Material mat = Material.getMaterial(splitter[i].toUpperCase());
			if (mat != null)
				types.add(mat);
		}

		HideRailsManager.hideSelectionBlocks(p, cuboid, args[2], true, types);
	}
}
