/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.commands.execution;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.lulucraft321.hiderails.enums.Messages;
import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.managers.MessagesManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;
import fr.lulucraft321.hiderails.utils.checkers.WorldeditChecker;

public class UnHideSelectionCommand extends AbstractCommand
{
	public UnHideSelectionCommand(CommandSender sender) {
		super(sender, "hiderails.unhideselection");
	}

	/*
	 * UnHide blocks with WorldEdit selection
	 */
	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		if (sender instanceof Player) {
			if (hasPermission()) {
				Player p = (Player) sender;
				Selection sel = WorldeditChecker.getWorldeditSelection(p);
				if (sel == null) {
					MessagesManager.sendPluginMessage(p, Messages.WORLDEDIT_NO_SELECTION);
					return;
				}
				HideRailsManager.removeSelectionBlocks(p, sel, true, null);
			} else {
				// Si sender n'est pas op ou n'a pas la perm
				MessagesManager.sendPluginMessage(sender, Messages.PLAYER_NO_ENOUGH_PERMISSION);
			}
		} else {
			MessagesManager.sendPluginMessage(sender, Messages.SENDER_TYPE_ERROR);
		}
	}

}
