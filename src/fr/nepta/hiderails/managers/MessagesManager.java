/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.managers;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.HideRails;
import fr.nepta.hiderails.enums.Messages;
import fr.nepta.hiderails.managers.PlayerClaimDataManager.LocType;

public class MessagesManager
{
	public static final String PLUGIN_PREFIX = "§8[§6Hide§7Rails§8] ";
	public static final Logger LOGGER = Bukkit.getLogger();

	private static final String MSG_PATH;

	static {
		MSG_PATH = FileConfigurationManager.MSG_PATH;
	}

	/**
	 * Load all plugin messages
	 */
	public static void loadAllMessages()
	{
		FileConfiguration lang_conf = FileConfigurationManager.getLangConfig().getConfig();

		Messages.SENDER_TYPE_ERROR.setMessage(lang_conf.getString(MSG_PATH + "sender_type_error"));
		Messages.PLAYER_NO_ENOUGH_PERMISSION.setMessage(lang_conf.getString(MSG_PATH + "player_no_enough_permission"));
		Messages.MATERIAL_TYPE_ERROR.setMessage(lang_conf.getString(MSG_PATH + "material_type_error"));
		Messages.SUCCESS_CHANGE_RAIL.setMessage(lang_conf.getString(MSG_PATH + "rail_success_change"));
		Messages.SUCCESS_BREAK_RAIL.setMessage(lang_conf.getString(MSG_PATH + "rail_success_break"));
		Messages.SUCCESS_UNHIDE_RAIL.setMessage(lang_conf.getString(MSG_PATH + "rail_success_unhide"));
		Messages.RAIL_ERROR.setMessage(lang_conf.getString(MSG_PATH + "rail_error"));
		Messages.INVALID_WORLDNAME.setMessage(lang_conf.getString(MSG_PATH + "invalid_worldname"));
		Messages.WATER_PROTECTION_STATUS_ALREADY.setMessage(lang_conf.getString(MSG_PATH + "water_protection_status_already"));
		Messages.SUCCESS_CHANGE_WATER_PROTECTION_STATUS.setMessage(lang_conf.getString(MSG_PATH + "water_protection_status_success_change"));
		Messages.SUCCESS_RELOAD.setMessage(lang_conf.getString(MSG_PATH + "plugin_success_reloaded"));
		Messages.NO_BACKUP.setMessage(lang_conf.getString(MSG_PATH + "no_backup"));
		Messages.RETURN_BACKUP_SUCCESS.setMessage(lang_conf.getString(MSG_PATH + "return_backup_success"));
		Messages.HIDERAILS_NO_SELECTION.setMessage(lang_conf.getString(MSG_PATH + "hiderails_no_selection"));
		Messages.HIDERAILS_SELECTION_POS.setMessage(lang_conf.getString(MSG_PATH + "hiderails_selection_pos"));
		Messages.CHANGE_HIDERAILS_SELECTION_MESSAGE_STATUS.setMessage(lang_conf.getString(MSG_PATH + "selection_message_status"));
		Messages.DISPLAY_HIDDEN_BLOCKS.setMessage(lang_conf.getString(MSG_PATH + "display_hidden_blocks"));
		Messages.INVALID_PLAYER.setMessage(lang_conf.getString(MSG_PATH + "invalid_player"));
		Messages.UPDATE_FOUND.setMessage(lang_conf.getString(MSG_PATH + "update_found"));
		Messages.KICK_SPAM_BLOCK.setMessage(lang_conf.getString(MSG_PATH + "kick_spam_hidden_block"));
	}

	/**
	 * Send plugin help to sender
	 * 
	 * @param sender
	 */
	public static void sendHelpPluginMessage(CommandSender sender)
	{
		sender.sendMessage("");
		sender.sendMessage("§8§l§m---------------§8§l[§6Hide§7Rails§8§l]§8§l§m---------------\n");
		sender.sendMessage("§f§l § §6/hiderails reload");
		sender.sendMessage("§f§l § §6/hiderails help\n");
		sender.sendMessage("§f§l § §6/hiderails hideone §o\"block:data\"");
		sender.sendMessage("§f§l § §6/hiderails hide §o\"block:data\"");
		sender.sendMessage("§f§l § §6/hiderails unhideone");
		sender.sendMessage("§f§l § §6/hiderails unhide");
		sender.sendMessage("§f§l § §6/hiderails hideselection §o\"block:data\"");
		sender.sendMessage("§f§l § §6/hiderails unhideselection §o[\"block_type\"]\n");
		sender.sendMessage("§f§l § §6/hiderails undo");
		sender.sendMessage("§f§l § §6/hiderails display");
		sender.sendMessage("§f§l § §6/hiderails selectionmessage");
		sender.sendMessage("§f§l § §6/hiderails waterprotection §o\"world\" \"value\"");
		sender.sendMessage("§7§l § §6§o/hiderails display [\"player\"]\n");// §8(Command Block only)");
		sender.sendMessage("§8§l§m-------------------------------------\n§r§o Plugin by Nepta_ - Version " + HideRails.getInstance().getDescription().getVersion());
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