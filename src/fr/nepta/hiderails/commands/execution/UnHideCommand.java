/**
 * Copyright
 * Code under MIT licence
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

public class UnHideCommand extends AbstractCommand
{
	public UnHideCommand() {
		super("unhide", "hiderails.unhide", 1, Arrays.asList("show"));
	}

	/*
	 * UnHide Block
	 */
	@Override
	public void execute(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		HideRailsManager.removeBlocks(p, p.getTargetBlock((Set<Material>) null, 25), true, false);
	}
}
