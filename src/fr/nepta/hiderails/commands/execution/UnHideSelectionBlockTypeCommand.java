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

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.commands.AbstractCommand;
import fr.nepta.hiderails.enums.Messages;
import fr.nepta.hiderails.managers.HideRailsManager;
import fr.nepta.hiderails.managers.MessagesManager;
import fr.nepta.hiderails.models.ClaimData;
import fr.nepta.hiderails.models.selectionsystem.Cuboid;
import fr.nepta.hiderails.utils.HideRailsSelectionChecker;

public class UnHideSelectionBlockTypeCommand extends AbstractCommand
{
	public UnHideSelectionBlockTypeCommand() {
		super("unhideselection", "hiderails.unhideselection", 2,
				Arrays.asList("unhideselect", "unhidesel", "showselection", "showselect", "showsel", "selectionunhide", "selectunhide", "selectionshow", "selectshow", "selshow"));
	}

	/*
	 * UnHide hiddenBlocks type with HideRails selection
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

		List<Material> types = new ArrayList<>();
		String[] splitter = args[1].split(",");
		for (int i = 0; i < splitter.length; i++) {
			Material mat = Material.getMaterial(splitter[i].toUpperCase());
			if (mat != null)
				types.add(mat);
		}

		HideRailsManager.removeSelectionBlocks(p, cuboid, true, types);
	}
}
