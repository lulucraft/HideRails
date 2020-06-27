/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.utils.abstractclass;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class AbstractCommand
{
	public static List<AbstractCommand> commands = new ArrayList<>();

	private final String COMMAND;
	private final String PERMISSION;
	private int argsNumber;
	private List<String> aliases = new ArrayList<>();
	private boolean allowConsole; // If true -> Allow console command


	public AbstractCommand(String command, String permission, int argsNumber) {
		this.COMMAND = command;
		this.PERMISSION = permission;
		this.argsNumber = argsNumber;
		this.allowConsole = false;
		commands.add(this);
	}

	public AbstractCommand(String command, String permission, int argsNumber, List<String> aliases) {
		this(command, permission, argsNumber);
		this.aliases = aliases;
	}

	public AbstractCommand(String command, String permission, int argsNumber, boolean allowConsole) {
		this(command, permission, argsNumber);
		this.allowConsole = allowConsole;
	}

	public AbstractCommand(String command, String permission, int argsNumber, List<String> aliases, boolean allowConsole) {
		this(command, permission, argsNumber, aliases);
		this.allowConsole = allowConsole;
	}


	public String getCommand() {
		return COMMAND;
	}

	public boolean hasPermission(CommandSender sender) {
		return sender.hasPermission(this.PERMISSION) || sender.isOp() || sender.hasPermission("hiderails.admin");
	}

	public List<String> getCommandsAliases() {
		return aliases;
	}

	public void addCommandAliase(String command) {
		this.aliases.add(command);
	}

	public int getArgsNumber() {
		return argsNumber;
	}

	public boolean isConsoleAllowed() {
		return allowConsole;
	}


	public abstract void execute(CommandSender sender, Command cmd, String[] args);
}
