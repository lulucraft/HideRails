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

public class UnHideCommand extends AbstractCommand
{
	public UnHideCommand() {
		super("unhide", "hiderails.unhide", 1, Arrays.asList("show"));
	}

	/*
	 * UnHide Block
	 */
	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		final Player p = (Player) sender;
		HideRailsManager.removeBlocks(p, p.getTargetBlock((Set<Material>) null, 25), true, false);
	}
}
