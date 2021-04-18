/**
 * Copyright
 * Code under MIT licence
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

public abstract class AbstractCommand
{
	public static List<AbstractCommand> commands = new ArrayList<>();

	private final String command;
	private final String permission;
	private int argsNumber;
	private List<String> aliases = new ArrayList<>();
	private boolean allowConsole; // If true -> Allow console command


	public AbstractCommand(String command, String permission, int argsNumber) {
		this.command = command;
		this.permission = permission;
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
		return command;
	}

	public boolean hasPermission(CommandSender sender) {
		return sender.hasPermission(this.permission) || sender.isOp() || sender.hasPermission("hiderails.admin");
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


	public abstract void execute(CommandSender sender, String[] args);
}
