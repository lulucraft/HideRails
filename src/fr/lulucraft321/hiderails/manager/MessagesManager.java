/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.manager;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.utils.Messages;

public class MessagesManager
{
	public static final String PREFIX = "§8[§6Hide§7Rails§8] ";

	public static void loadAllMessages()
	{
		Messages.SENDER_TYPE_ERROR.setMessage(HideRails.getInstance().getLangConfig().getString("messages." + "sender_type_error"));
		Messages.PLAYER_NO_ENOUGH_PERMISSION.setMessage(HideRails.getInstance().getLangConfig().getString("messages." + "player_no_enough_permission"));
		Messages.MATERIAL_TYPE_ERROR.setMessage(HideRails.getInstance().getLangConfig().getString("messages." + "material_type_error"));
		Messages.SUCCESS_CHANGE_RAIL.setMessage(HideRails.getInstance().getLangConfig().getString("messages." + "rail_success_change"));
		Messages.SUCCESS_BREAK_RAIL.setMessage(HideRails.getInstance().getLangConfig().getString("messages." + "rail_success_break"));
		Messages.SUCCESS_UNHIDE_RAIL.setMessage(HideRails.getInstance().getLangConfig().getString("messages." + "rail_success_unhide"));
		Messages.RAIL_ERROR.setMessage(HideRails.getInstance().getLangConfig().getString("messages." + "rail_error"));
		Messages.INVALID_WORLDNAME.setMessage(HideRails.getInstance().getLangConfig().getString("messages." + "invalid_worldname"));
		Messages.WATER_PROTECTION_STATUS_ALREADY.setMessage(HideRails.getInstance().getLangConfig().getString("messages." + "water_protection_status_already"));
		Messages.SUCCESS_CHANGE_WATER_PROTECTION_STATUS.setMessage(HideRails.getInstance().getLangConfig().getString("messages." + "water_protection_status_success_change"));
		Messages.SUCCESS_RELOAD.setMessage(HideRails.getInstance().getLangConfig().getString("messages." + "plugin_success_reloaded"));
		Messages.NO_BACKUP.setMessage(HideRails.getInstance().getLangConfig().getString("messages." + "no_backup"));
		Messages.RETURN_BACKUP_SUCCESS.setMessage(HideRails.getInstance().getLangConfig().getString("messages." + "return_backup_success"));
	}

	public static void sendHelpPluginMessage(CommandSender sender)
	{
		sender.sendMessage("\n" + "§8§l§m--------------§8§l[§6Hide§7Rails§8§l]§8§l§m--------------");
		sender.sendMessage("\n" + "§f§l » §6/hiderails reload");
		sender.sendMessage("§f§l » §6/hiderails help");
		sender.sendMessage("\n" + "§f§l » §6/hiderails hideone \"§oblock§6\"");
		sender.sendMessage("§f§l » §6/hiderails hide \"§oblock§6\"");
		sender.sendMessage("§f§l » §6/hiderails unhideone");
		sender.sendMessage("§f§l » §6/hiderails unhide");
		sender.sendMessage("\n" + "§f§l » §6/hiderails return");
		sender.sendMessage("§f§l » §6/hiderails waterprotection \"§oworld§6\" \"§ovalue§6\"");
		sender.sendMessage("\n" + "§8§l§m-----------------------------------\n§r§o Plugin by lulucraft321");
	}

	public static void sendPluginMessage(CommandSender sender, Messages messageType)
	{
		sender.sendMessage(PREFIX + ChatColor.translateAlternateColorCodes('&', messageType.getMessage()).replace("\\n", "\n"));
	}

	public static void sendRailChangeMessage(CommandSender sender, Messages messageType, String block)
	{
		sender.sendMessage(PREFIX + ChatColor.translateAlternateColorCodes('&', messageType.getMessage()).replace("\\n", "\n").replace("%blocktype%", block));
	}

	public static void sendAlreadyStatusMessage(CommandSender sender, Messages messageType, String worldName, boolean b)
	{
		sender.sendMessage(PREFIX + ChatColor.translateAlternateColorCodes('&', messageType.getMessage()).replace("\\n", "\n").replace("%world%", worldName).replace("%status%", String.valueOf(b)));
	}

	public static void sendChangeStatusMessage(CommandSender sender, Messages messageType, String worldName, boolean b)
	{
		sender.sendMessage(PREFIX + ChatColor.translateAlternateColorCodes('&', messageType.getMessage()).replace("\\n", "\n").replace("%world%", worldName).replace("%status%", String.valueOf(b)));
	}
}
