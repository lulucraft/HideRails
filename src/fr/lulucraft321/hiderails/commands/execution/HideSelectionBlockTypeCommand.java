/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.commands.execution;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.lulucraft321.hiderails.enums.Messages;
import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.managers.MessagesManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;
import fr.lulucraft321.hiderails.utils.checkers.WorldeditChecker;

public class HideSelectionBlockTypeCommand extends AbstractCommand
{
	public HideSelectionBlockTypeCommand(CommandSender sender) {
		super(sender, "hiderails.hideselection");
	}

	/*
	 * Hide hiddenBlocks type with Worldedit selection
	 */
	public void execute(CommandSender sender, Command cmd, String[] args) {
		if (sender instanceof Player) {
			if (hasPermission()) {
				Player p = (Player)sender;
				Selection sel = WorldeditChecker.getWorldeditSelection(p);
				if (sel == null) {
					MessagesManager.sendPluginMessage(p, Messages.WORLDEDIT_NO_SELECTION);
					return;
				}

				List<Material> types = new ArrayList<>();
				String[] splitter = args[1].split(",");
				for (int i = 0; i < splitter.length; i++) {
					Material mat = Material.getMaterial(splitter[i].toUpperCase());
					if (mat != null)
						types.add(mat);
				}

				HideRailsManager.hideSelectionBlocks(p, sel, args[2], true, types);
			} else {
				// Si sender n'est pas op ou n'a pas la perm
				MessagesManager.sendPluginMessage(sender, Messages.PLAYER_NO_ENOUGH_PERMISSION);
			}
		} else {
			MessagesManager.sendPluginMessage(sender, Messages.SENDER_TYPE_ERROR);
		}
	}
}
