/**
 * Copyright
 * Code under MIT licence
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.commands.execution;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.commands.AbstractCommand;
import fr.nepta.hiderails.managers.HideRailsManager;

public class HideCommand extends AbstractCommand
{
	public HideCommand() {
		super("hide", "hiderails.hide", 2, Arrays.asList("hiderails"));
	}

	/*
	 * Hide blocks
	 */
	@Override
	public void execute(CommandSender sender, String[] args) {
		// args[1] == Material
		HideRailsManager.saveChangedBlocks((Player) sender, args[1], true, false);
	}

}
