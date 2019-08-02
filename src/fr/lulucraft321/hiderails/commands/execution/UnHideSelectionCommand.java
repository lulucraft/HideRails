/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.commands.execution;

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
	public UnHideSelectionCommand(CommandSender sender) {
		super(sender, "hiderails.unhideselection");
	}

	/*
	 * UnHide blocks with HideRails selection
	 */
	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		if (sender instanceof Player) {
			if (hasPermission()) {
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
			} else {
				// Si sender n'est pas op ou n'a pas la perm
				MessagesManager.sendPluginMessage(sender, Messages.PLAYER_NO_ENOUGH_PERMISSION);
			}
		} else {
			MessagesManager.sendPluginMessage(sender, Messages.SENDER_TYPE_ERROR);
		}
	}

}
