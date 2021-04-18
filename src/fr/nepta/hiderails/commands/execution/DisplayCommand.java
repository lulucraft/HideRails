/**
 * Copyright
 * Code under MIT licence
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.commands.execution;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.commands.AbstractCommand;
import fr.nepta.hiderails.managers.HideRailsManager;

public class DisplayCommand extends AbstractCommand
{
	public DisplayCommand() {
		super("display", "hiderails.display", 1);
	}

	/*
	 * Hide/Show hidden blocks (for one player)
	 */
	@Override
	public void execute(CommandSender sender, String[] args) {
		HideRailsManager.displayBlocks((Player) sender);
	}
}
