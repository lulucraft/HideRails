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

import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;

public class HideCommand extends AbstractCommand
{
	public HideCommand() {
		super("hide", "hiderails.hide", 2, Arrays.asList("hiderails"));
	}

	/*
	 * Hide blocks
	 */
	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		// args[1] == Material
		HideRailsManager.saveChangedBlocks((Player) sender, args[1], true, false);
	}

}
