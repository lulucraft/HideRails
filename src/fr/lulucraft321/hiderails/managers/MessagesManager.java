/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.managers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.enums.Messages;
import fr.lulucraft321.hiderails.managers.PlayerClaimDataManager.LocType;

public class MessagesManager
{
	public static final String PLUGIN_PREFIX = "§8[§6Hide§7Rails§8] ";
	private static final String MSG_PATH;

	static {
		MSG_PATH = FileConfigurationManager.MSG_PATH;
	}

	public static void loadAllMessages()
	{
		final FileConfiguration LANG_CONF = FileConfigurationManager.getLangConfig();

		Messages.SENDER_TYPE_ERROR.setMessage(LANG_CONF.getString(MSG_PATH + "sender_type_error"));
		Messages.PLAYER_NO_ENOUGH_PERMISSION.setMessage(LANG_CONF.getString(MSG_PATH + "player_no_enough_permission"));
		Messages.MATERIAL_TYPE_ERROR.setMessage(LANG_CONF.getString(MSG_PATH + "material_type_error"));
		Messages.SUCCESS_CHANGE_RAIL.setMessage(LANG_CONF.getString(MSG_PATH + "rail_success_change"));
		Messages.SUCCESS_BREAK_RAIL.setMessage(LANG_CONF.getString(MSG_PATH + "rail_success_break"));
		Messages.SUCCESS_UNHIDE_RAIL.setMessage(LANG_CONF.getString(MSG_PATH + "rail_success_unhide"));
		Messages.RAIL_ERROR.setMessage(LANG_CONF.getString(MSG_PATH + "rail_error"));
		Messages.INVALID_WORLDNAME.setMessage(LANG_CONF.getString(MSG_PATH + "invalid_worldname"));
		Messages.WATER_PROTECTION_STATUS_ALREADY.setMessage(LANG_CONF.getString(MSG_PATH + "water_protection_status_already"));
		Messages.SUCCESS_CHANGE_WATER_PROTECTION_STATUS.setMessage(LANG_CONF.getString(MSG_PATH + "water_protection_status_success_change"));
		Messages.SUCCESS_RELOAD.setMessage(LANG_CONF.getString(MSG_PATH + "plugin_success_reloaded"));
		Messages.NO_BACKUP.setMessage(LANG_CONF.getString(MSG_PATH + "no_backup"));
		Messages.RETURN_BACKUP_SUCCESS.setMessage(LANG_CONF.getString(MSG_PATH + "return_backup_success"));
		Messages.HIDERAILS_NO_SELECTION.setMessage(LANG_CONF.getString(MSG_PATH + "hiderails_no_selection"));
		Messages.HIDERAILS_SELECTION_POS.setMessage(LANG_CONF.getString(MSG_PATH + "hiderails_selection_pos"));
		Messages.CHANGE_HIDERAILS_SELECTION_MESSAGE_STATUS.setMessage(LANG_CONF.getString(MSG_PATH + "selection_message_status"));
		Messages.DISPLAY_HIDDEN_BLOCKS.setMessage(LANG_CONF.getString(MSG_PATH + "display_hidden_blocks"));
		Messages.INVALID_PLAYER.setMessage(LANG_CONF.getString(MSG_PATH + "invalid_player"));
		Messages.UPDATE_FOUND.setMessage(LANG_CONF.getString(MSG_PATH + "update_found"));
		Messages.KICK_SPAM_BLOCK.setMessage(LANG_CONF.getString(MSG_PATH + "kick_spam_hidden_block"));
	}

	public static void sendHelpPluginMessage(CommandSender sender)
	{
		String displayColor1 = "§f";
		String displayColor2 = "§6";
		String instruct = "";
		// If version is 1.8
		if (HideRails.version.isOldVersion()) {
			displayColor1 = "§c";
			displayColor2 = "§c§m";
			instruct = "§c§o > Only for 1.10+";
		}
		sender.sendMessage("\n" + "§8§l§m---------------§8§l[§6Hide§7Rails§8§l]§8§l§m---------------");
		sender.sendMessage("\n" + "§f§l » §6/hiderails reload");
		sender.sendMessage("§f§l » §6/hiderails help");
		sender.sendMessage("\n" + "§f§l » §6/hiderails hideone \"§oblock:data§6\"");
		sender.sendMessage("§f§l » §6/hiderails hide \"§oblock:data§6\"");
		sender.sendMessage("§f§l » §6/hiderails unhideone");
		sender.sendMessage("§f§l » §6/hiderails unhide");
		sender.sendMessage("§l » §6/hiderails hideselection \"block:data\"");
		sender.sendMessage("§l » §6/hiderails unhideselection");
		sender.sendMessage("\n" + "§f§l » §6/hiderails return");
		sender.sendMessage(displayColor1 + "§l » " + displayColor2 + "/hiderails display" + instruct);
		sender.sendMessage("§f§l » §6/hiderails selectionmessage");
		sender.sendMessage("§f§l » §6/hiderails waterprotection \"§oworld§6\" \"§ovalue§6\"");
		sender.sendMessage("§7§l » §6§o/hiderails display [\"player\"] §8(Command Block only)");
		sender.sendMessage("\n" + "§8§l§m-------------------------------------\n§r§o Plugin by Nepta_ - Version " + HideRails.getInstance().getDescription().getVersion());
	}

	public static void sendPluginMessage(CommandSender sender, Messages messageType)
	{
		sender.sendMessage(getColoredMessage(messageType));
	}

	public static void sendRailChangeMessage(CommandSender sender, Messages messageType, String block)
	{
		sender.sendMessage(getColoredMessage(messageType, block));
	}

	public static void sendAlreadyStatusMessage(CommandSender sender, Messages messageType, String worldName, boolean b)
	{
		sendChangeStatusMessage(sender, messageType, worldName, b);
	}

	public static void sendChangeStatusMessage(CommandSender sender, Messages messageType, String worldName, boolean b)
	{
		sender.sendMessage(getColoredMessage(messageType, worldName, b));
	}

	public static void sendDisplayChangeMessage(CommandSender sender, Messages messageType, boolean b)
	{
		sender.sendMessage(getColoredMessage(messageType, b));
	}

	public static void sendSelectPosMessage(Player player, LocType pos)
	{
		if (!PlayerClaimDataManager.isBlacklistedPlayer(player)) player.sendMessage(getColoredMessage(Messages.HIDERAILS_SELECTION_POS, pos));
	}

	public static void sendChangeStatusSelectionMessage(Player player, boolean b)
	{
		player.sendMessage(getColoredMessage(Messages.CHANGE_HIDERAILS_SELECTION_MESSAGE_STATUS).replace("%status%", String.valueOf(b)));
	}


	public static String getColoredMessage(Messages messageType) {
		return PLUGIN_PREFIX + ChatColor.translateAlternateColorCodes('&', messageType.getMessage())
		.replace("\\n", "\n")
		.replace("%link%", "https://www.spigotmc.org/resources/55158/")
		.replace("%pluginlink%", "https://www.spigotmc.org/resources/55158/")
		.replace("%plugin_link%", "https://www.spigotmc.org/resources/55158/");
	}

	private static String getColoredMessage(Messages messageType, String block) {
		return getColoredMessage(messageType).replace("%blocktype%", block);
	}

	private static String getColoredMessage(Messages messageType, String worldName, boolean b) {
		return getColoredMessage(messageType).replace("%world%", worldName).replace("%status%", String.valueOf(b));
	}

	private static String getColoredMessage(Messages messageType, boolean b) {
		return getColoredMessage(messageType).replace("%hide%", String.valueOf(b));
	}

	private static String getColoredMessage(Messages messageType, LocType pos) {
		return getColoredMessage(messageType).replace("%pos%", (pos == LocType.LOC1 ? "§e1" : "§e2"));
	}
}
