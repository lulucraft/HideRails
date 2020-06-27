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

import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;

public class DisplayCommand extends AbstractCommand
{
	public DisplayCommand() {
		super("display", "hiderails.display", 1);
	}

	/*
	 * Hide/Show hidden blocks (for one player)
	 */
	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		HideRailsManager.displayBlocks((Player) sender);
	}
}
