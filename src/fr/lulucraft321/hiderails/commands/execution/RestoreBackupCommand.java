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

import fr.lulucraft321.hiderails.managers.PlayerCommandBackupManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;

public class RestoreBackupCommand extends AbstractCommand
{
	public RestoreBackupCommand() {
		super("return", "hiderails.return", 1, Arrays.asList("undo", "back"));
	}

	/*
	 * Restore backup player command
	 */
	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		PlayerCommandBackupManager.restoreBackupRails((Player) sender);
	}

}
