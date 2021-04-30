/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.commands.execution;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.commands.AbstractCommand;
import fr.nepta.hiderails.managers.HideRailsManager;

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
	public void execute(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		HideRailsManager.removeBlocks(p, p.getTargetBlock((Set<Material>) null, 25), true, true);
	}
}
