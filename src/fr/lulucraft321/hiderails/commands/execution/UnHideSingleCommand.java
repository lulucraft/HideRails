/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.commands.execution;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;

public class UnHideSingleCommand extends AbstractCommand
{
	public UnHideSingleCommand() {
		super("unhidesingle", "hiderails.unhidesingle", 1,
				Arrays.asList("singleunhide", "singleshow", "solounhide", "soloshow", "oneunhide", "oneshow", "showone", "showsingle", "showsolo", "unhideone", "unhidesolo"));
	}

	/*
	 * UnHide single block
	 */
	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		final Player p = (Player) sender;
		HideRailsManager.removeBlocks(p, p.getTargetBlock((Set<Material>) null, 25), true, true);
	}
}
