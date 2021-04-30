/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.commands.execution;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.commands.AbstractCommand;
import fr.nepta.hiderails.managers.PlayerCommandBackupManager;

public class RestoreBackupCommand extends AbstractCommand
{
	public RestoreBackupCommand() {
		super("return", "hiderails.return", 1, Arrays.asList("undo", "back"));
	}

	/*
	 * Restore backup player command
	 */
	@Override
	public void execute(CommandSender sender, String[] args) {
		PlayerCommandBackupManager.restoreBackupRails((Player) sender);
	}

}
