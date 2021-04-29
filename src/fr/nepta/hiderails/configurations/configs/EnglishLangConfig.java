/**
 * Copyright
 * Code under MIT licence
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.configurations.configs;

import static fr.nepta.hiderails.managers.FileConfigurationManager.MSG_PATH;
import static fr.nepta.hiderails.managers.FileConfigurationManager.checkConfContains;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;

import fr.nepta.hiderails.managers.FileConfigurationManager;
import fr.nepta.hiderails.managers.MessagesManager;

public class EnglishLangConfig extends AbstractLangConfig
{
	public EnglishLangConfig() {
		super(new File(FileConfigurationManager.LANG_PATH, "EN.yml"));
	}

	@Override
	public void setupConfig()
	{
		// EN.yml
		File enLangFile = getFile();
		FileConfiguration enLangConfig;

		if(!enLangFile.exists())
		{
			try {
				enLangFile.createNewFile();
			} catch (IOException e) {
				MessagesManager.LOGGER.warning("Error when creating file \"" + enLangFile.getName() + "\"!");
				return;
			}

			enLangConfig = loadConfig();
			enLangConfig.options().header("Translation by Nepta_").copyDefaults(true);
			enLangConfig.set(MSG_PATH + "sender_type_error", "&cYou must be a player to execute this command!");
			enLangConfig.set(MSG_PATH + "player_no_enough_permission", "&cYou do not have permission to execute this command!");
			enLangConfig.set(MSG_PATH + "rail_success_change", "&2You have replaced the rails with %blocktype%!");
			enLangConfig.set(MSG_PATH + "material_type_error", "&cThis bloc does not exist!");
			enLangConfig.set(MSG_PATH + "rail_error", "&cThe target block is not a rail!");
			enLangConfig.set(MSG_PATH + "rail_success_break", "&2You have broken a hidden rail!");
			enLangConfig.set(MSG_PATH + "rail_success_unhide", "&2You have displayed the rails!");
			enLangConfig.set(MSG_PATH + "water_protection_status_success_change", "&2You have %status% the under-water protection in %world%");
			enLangConfig.set(MSG_PATH + "invalid_worldname", "&cThis world name is invalid!");
			enLangConfig.set(MSG_PATH + "plugin_success_reloaded", "&2Plugin successfully reloaded");
			enLangConfig.set(MSG_PATH + "water_protection_status_already", "&cThe underwater protection in %world% is already %status%");
			enLangConfig.set(MSG_PATH + "no_backup", "&cNo backup available!");
			enLangConfig.set(MSG_PATH + "return_backup_success", "&2successfully restored backup!");
			//enLangConfig.set(MSG_PATH + "worldedit_not_installed", "&cWorldedit plugin is not installed on this server!");
			//enLangConfig.set(MSG_PATH + "worldedit_no_selection", "&cYou must first select region with Worldedit!");
			enLangConfig.set(MSG_PATH + "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			enLangConfig.set(MSG_PATH + "hiderails_selection_pos", "&8You have selected position &e%pos%");
			enLangConfig.set(MSG_PATH + "selection_message_status", "&8You have &e%status% &8selection messages!");
			enLangConfig.set(MSG_PATH + "display_hidden_blocks", "&2You have %hide% the hidden blocks for you!");
			enLangConfig.set(MSG_PATH + "invalid_player", "&cThe player cannot be found!");
			enLangConfig.set(MSG_PATH + "update_found", "&bNew update Available!\n&o%link%");
			enLangConfig.set(MSG_PATH + "kick_spam_hidden_block", "&cDon't spam blocks please!!");
		} else {
			enLangConfig = loadConfig();
			enLangConfig.options().header("Translation by Nepta_").copyDefaults(true);
			checkConfContains(enLangConfig, "sender_type_error", "&cYou must be a player to execute this command!");
			checkConfContains(enLangConfig, "player_no_enough_permission", "&cYou do not have permission to execute this command!");
			checkConfContains(enLangConfig, "rail_success_change", "&2You have replaced the rails with %blocktype%!");
			checkConfContains(enLangConfig, "material_type_error", "&cThis bloc does not exist!");
			checkConfContains(enLangConfig, "rail_error", "&cThe target block is not a rail!");
			checkConfContains(enLangConfig, "rail_success_break", "&2You have broken a hidden rail!");
			checkConfContains(enLangConfig, "rail_success_unhide", "&2You have displayed the rails!");
			checkConfContains(enLangConfig, "water_protection_status_success_change", "&2You have %status% the under-water protection in %world%");
			checkConfContains(enLangConfig, "invalid_worldname", "&cThis world name is invalid!");
			checkConfContains(enLangConfig, "plugin_success_reloaded", "&2Plugin successfully reloaded");
			checkConfContains(enLangConfig, "water_protection_status_already", "&cThe underwater protection in %world% is already %status%");
			checkConfContains(enLangConfig, "no_backup", "&cNo backup available!");
			checkConfContains(enLangConfig, "return_backup_success", "&2successfully restored backup!");
			//checkConfContains(enLangConfig, "worldedit_not_installed", "&cWorldedit plugin is not installed on this server!");
			//checkConfContains(enLangConfig, "worldedit_no_selection", "&cYou must first select region with Worldedit!");
			checkConfContains(enLangConfig, "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			checkConfContains(enLangConfig, "hiderails_selection_pos", "&8You have selected position &e%pos%");
			checkConfContains(enLangConfig, "selection_message_status", "&8You have &e%status% &8selection messages!");
			checkConfContains(enLangConfig, "display_hidden_blocks", "&2You have %hide% the hidden blocks for you!");
			checkConfContains(enLangConfig, "invalid_player", "&cThe player cannot be found!");
			checkConfContains(enLangConfig, "update_found", "&bNew update Available!\n&o%link%");
			checkConfContains(enLangConfig, "kick_spam_hidden_block", "&cDon't spam blocks please!!");
		}
	}
}