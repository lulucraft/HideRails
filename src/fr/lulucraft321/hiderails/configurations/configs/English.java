/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.configurations.configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import fr.lulucraft321.hiderails.managers.FileConfigurationManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractLangConfig;

public class English extends FileConfigurationManager implements AbstractLangConfig
{
	@Override
	public void setupConfig()
	{
		// EN.yml
		FileConfigurationManager.enLangFile = new File(FileConfigurationManager.LANG_PATH, "EN.yml");
		if(!FileConfigurationManager.enLangFile.exists())
		{
			try {
				FileConfigurationManager.enLangFile.createNewFile();
			} catch (IOException e) {
				System.out.println("Erreur lors de la creation du fichier de configuration \"" + FileConfigurationManager.enLangConfig.getName().toString() + "\"!");
				return;
			}

			FileConfigurationManager.enLangConfig = YamlConfiguration.loadConfiguration(FileConfigurationManager.enLangFile);
			FileConfigurationManager.enLangConfig.options().header("Translation by Nepta_").copyDefaults(true);
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "sender_type_error", "&cYou must be a player to execute this command!");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "player_no_enough_permission", "&cYou do not have permission to execute this command!");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "rail_success_change", "&2You have replaced the rails with %blocktype%!");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "material_type_error", "&cThis bloc does not exist!");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "rail_error", "&cThe target block is not a rail!");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "rail_success_break", "&2You have broken a hidden rail!");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "rail_success_unhide", "&2You have displayed the rails!");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "water_protection_status_success_change", "&2You have %status% the under-water protection in %world%");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "invalid_worldname", "&cThis world name is invalid!");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "plugin_success_reloaded", "&2Plugin successfully reloaded");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "water_protection_status_already", "&cThe underwater protection in %world% is already %status%");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "no_backup", "&cNo backup available!");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "return_backup_success", "&2successfully restored backup!");
			//FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "worldedit_not_installed", "&cWorldedit plugin is not installed on this server!");
			//FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "worldedit_no_selection", "&cYou must first select region with Worldedit!");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "hiderails_selection_pos", "&8You have selected position &e%pos%");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "selection_message_status", "&8You have &e%status% &8selection messages!");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "display_hidden_blocks", "&2You have %hide% the hidden blocks for you!");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "invalid_player", "&cThe player cannot be found!");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "update_found", "&bNew update Available!\n&o%link%");
			FileConfigurationManager.enLangConfig.set(FileConfigurationManager.msgPath + "kick_spam_hidden_block", "&cDon't spam blocks please!!");

			// Sauveguarde des modifs
			try {
				FileConfigurationManager.enLangConfig.save(FileConfigurationManager.enLangFile);
			} catch (IOException e1) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + FileConfigurationManager.enLangConfig.getName().toString() + "\"!"); }
		} else {
			FileConfigurationManager.enLangConfig = YamlConfiguration.loadConfiguration(FileConfigurationManager.enLangFile);
			FileConfigurationManager.enLangConfig.options().header("Translation by Nepta_").copyDefaults(true);
			checkConfContains(FileConfigurationManager.enLangConfig, "sender_type_error", "&cYou must be a player to execute this command!");
			checkConfContains(FileConfigurationManager.enLangConfig, "player_no_enough_permission", "&cYou do not have permission to execute this command!");
			checkConfContains(FileConfigurationManager.enLangConfig, "rail_success_change", "&2You have replaced the rails with %blocktype%!");
			checkConfContains(FileConfigurationManager.enLangConfig, "material_type_error", "&cThis bloc does not exist!");
			checkConfContains(FileConfigurationManager.enLangConfig, "rail_error", "&cThe target block is not a rail!");
			checkConfContains(FileConfigurationManager.enLangConfig, "rail_success_break", "&2You have broken a hidden rail!");
			checkConfContains(FileConfigurationManager.enLangConfig, "rail_success_unhide", "&2You have displayed the rails!");
			checkConfContains(FileConfigurationManager.enLangConfig, "water_protection_status_success_change", "&2You have %status% the under-water protection in %world%");
			checkConfContains(FileConfigurationManager.enLangConfig, "invalid_worldname", "&cThis world name is invalid!");
			checkConfContains(FileConfigurationManager.enLangConfig, "plugin_success_reloaded", "&2Plugin successfully reloaded");
			checkConfContains(FileConfigurationManager.enLangConfig, "water_protection_status_already", "&cThe underwater protection in %world% is already %status%");
			checkConfContains(FileConfigurationManager.enLangConfig, "no_backup", "&cNo backup available!");
			checkConfContains(FileConfigurationManager.enLangConfig, "return_backup_success", "&2successfully restored backup!");
			//checkConfContains(FileConfigurationManager.enLangConfig, "worldedit_not_installed", "&cWorldedit plugin is not installed on this server!");
			//checkConfContains(FileConfigurationManager.enLangConfig, "worldedit_no_selection", "&cYou must first select region with Worldedit!");
			checkConfContains(FileConfigurationManager.enLangConfig, "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			checkConfContains(FileConfigurationManager.enLangConfig, "hiderails_selection_pos", "&8You have selected position &e%pos%");
			checkConfContains(FileConfigurationManager.enLangConfig, "selection_message_status", "&8You have &e%status% &8selection messages!");
			checkConfContains(FileConfigurationManager.enLangConfig, "display_hidden_blocks", "&2You have %hide% the hidden blocks for you!");
			checkConfContains(FileConfigurationManager.enLangConfig, "invalid_player", "&cThe player cannot be found!");
			checkConfContains(FileConfigurationManager.enLangConfig, "update_found", "&bNew update Available!\n&o%link%");
			checkConfContains(FileConfigurationManager.enLangConfig, "kick_spam_hidden_block", "&cDon't spam blocks please!!");

			// Sauveguarde des modifs
			try {
				FileConfigurationManager.enLangConfig.save(FileConfigurationManager.enLangFile);
			} catch (IOException e) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + FileConfigurationManager.enLangConfig.getName().toString() + "\"!"); }
		}
		FileConfigurationManager.enLangConfig = YamlConfiguration.loadConfiguration(FileConfigurationManager.enLangFile);
		//
	}
}