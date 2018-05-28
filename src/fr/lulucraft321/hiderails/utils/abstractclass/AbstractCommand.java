/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.utils.abstractclass;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class AbstractCommand
{
	private final CommandSender sender;
	private final String permission;

	public AbstractCommand(CommandSender sender, String permission) {
		this.sender = sender;
		this.permission = permission;
	}

	public CommandSender getSender() {
		return sender;
	}

	public String getPermission() {
		return permission;
	}

	public boolean hasPermission() {
		return this.sender.hasPermission(this.permission) || this.sender.isOp() || this.sender.hasPermission("hiderails.admin");
	}

	public abstract void execute(CommandSender sender, Command cmd, String[] args);
}
