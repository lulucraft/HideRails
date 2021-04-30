/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.configurations.configs;

import static fr.nepta.hiderails.managers.FileConfigurationManager.checkConfContains;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class CustomLangConfig extends AbstractLangConfig {

	/**
	 * @param langFile
	 */
	public CustomLangConfig(File langFile) {
		super(langFile);
	}

	@Override
	public final void setupConfig()
	{
		// User language file

		if (getFile().exists())
		{
			FileConfiguration langConfig = loadConfig();
			langConfig.options().header("Custom language\nYou can suggest your language at https://www.spigotmc.org/resources/55158/").copyDefaults(true);
			checkConfContains(langConfig, "sender_type_error", "&cTO_DEFINE");
			checkConfContains(langConfig, "player_no_enough_permission", "&cTO_DEFINE");
			checkConfContains(langConfig, "rail_success_change", "&2TO_DEFINE");
			checkConfContains(langConfig, "material_type_error", "&cTO_DEFINE");
			checkConfContains(langConfig, "rail_error", "&cTO_DEFINE");
			checkConfContains(langConfig, "rail_success_break", "&2TO_DEFINE");
			checkConfContains(langConfig, "rail_success_unhide", "&2TO_DEFINE");
			checkConfContains(langConfig, "water_protection_status_success_change", "&2TO_DEFINE");
			checkConfContains(langConfig, "invalid_worldname", "&cTO_DEFINE");
			checkConfContains(langConfig, "plugin_success_reloaded", "&2TO_DEFINE");
			checkConfContains(langConfig, "water_protection_status_already", "&cTO_DEFINE");
			checkConfContains(langConfig, "no_backup", "&cTO_DEFINE");
			checkConfContains(langConfig, "return_backup_success", "&2TO_DEFINE");
			checkConfContains(langConfig, "hiderails_no_selection", "&cTO_DEFINE");
			checkConfContains(langConfig, "hiderails_selection_pos", "&8TO_DEFINE");
			checkConfContains(langConfig, "selection_message_status", "&8TO_DEFINE");
			checkConfContains(langConfig, "display_hidden_blocks", "&2TO_DEFINE");
			checkConfContains(langConfig, "invalid_player", "&cTO_DEFINE");
			checkConfContains(langConfig, "update_found", "&bTO_DEFINE");
			checkConfContains(langConfig, "kick_spam_hidden_block", "&cTO_DEFINE");
		}
	}

}
