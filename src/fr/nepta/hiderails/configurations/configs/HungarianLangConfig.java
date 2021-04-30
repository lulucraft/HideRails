/**
 * Copyright
 * Code under MIT license
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

public class HungarianLangConfig extends AbstractLangConfig
{
	public HungarianLangConfig() {
		super(new File(FileConfigurationManager.LANG_PATH, "HU.yml"));
	}

	@Override
	public void setupConfig()
	{
		// HU.yml
		File huLangFile = getFile();
		FileConfiguration huLangConfig;

		if(!huLangFile.exists())
		{
			try {
				huLangFile.createNewFile();
			} catch (IOException e) {
				MessagesManager.LOGGER.warning("Error when creating file \"" + huLangFile.getName() + "\" !");
				return;
			}

			huLangConfig = loadConfig();
			huLangConfig.options().header("Köszönöm Zsomi-nak hogy leforditotta magyarra !").copyDefaults(true);
			huLangConfig.set(MSG_PATH + "sender_type_error", "&cEzt a parancsot csak játékosként hajtható végre !");
			huLangConfig.set(MSG_PATH + "player_no_enough_permission", "&cNincs jogosultságod ehhez !");
			huLangConfig.set(MSG_PATH + "rail_success_change", "&2Kicserélted a sínt a következő blokkra: %blocktype% !");
			huLangConfig.set(MSG_PATH + "material_type_error", "&cEz a blokk nem létezik !");
			huLangConfig.set(MSG_PATH + "rail_error", "&cA keresett blokk nem sín !");
			huLangConfig.set(MSG_PATH + "rail_success_break", "&2Kiütöttél egy rejtett sínt !");
			huLangConfig.set(MSG_PATH + "rail_success_unhide", "&2Megjelenítetted a sineket !");
			huLangConfig.set(MSG_PATH + "water_protection_status_success_change", "&2Ez a(z) %status% státusza a víz alatti védelem ebben a világban %world%");
			huLangConfig.set(MSG_PATH + "invalid_worldname", "&cA világ neve hibás !");
			huLangConfig.set(MSG_PATH + "plugin_success_reloaded", "&2A plugin sikeresen reloadolva(újratöltve)!");
			huLangConfig.set(MSG_PATH + "water_protection_status_already", "&cA víz alatti védelem a(z) %world% világban %status%");
			huLangConfig.set(MSG_PATH + "no_backup", "&cNincsenek mentések !");
			huLangConfig.set(MSG_PATH + "return_backup_success", "&2Sikeresen vissza lett állítva a mentés !");
			//huLangConfig.set(MSG_PATH + "worldedit_not_installed", "&cNincs letöltve a szerverre a WorldEdit plugin !");
			//huLangConfig.set(MSG_PATH + "worldedit_no_selection", "&cElső lépés, hogy jelöld ki WorldEdit-el a területet !");
			huLangConfig.set(MSG_PATH + "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			huLangConfig.set(MSG_PATH + "hiderails_selection_pos", "&8You have selected position &e%pos%");
			huLangConfig.set(MSG_PATH + "selection_message_status", "&8You have &e%status% &8selection messages!");
			huLangConfig.set(MSG_PATH + "display_hidden_blocks", "&2Szamoda a(z) %hide% blokk rejtett !");
			huLangConfig.set(MSG_PATH + "invalid_player", "&cA játékos nem található !");
			huLangConfig.set(MSG_PATH + "update_found", "&bÙj frissitès elèrhető !\n&o%link%");
			huLangConfig.set(MSG_PATH + "kick_spam_hidden_block", "&cNe spameld a blokkokat !!");
		} else {
			huLangConfig = loadConfig();
			huLangConfig.options().header("Köszönöm Zsomi-nak hogy leforditotta magyarra !").copyDefaults(true);
			checkConfContains(huLangConfig, "sender_type_error", "&cEzt a parancsot csak játékosként hajtható végre !");
			checkConfContains(huLangConfig, "player_no_enough_permission", "&cNincs jogosultságod ehhez !");
			checkConfContains(huLangConfig, "rail_success_change", "&2Kicserélted a sínt a következő blokkra: %blocktype% !");
			checkConfContains(huLangConfig, "material_type_error", "&cEz a blokk nem létezik !");
			checkConfContains(huLangConfig, "rail_error", "&cA keresett blokk nem sín !");
			checkConfContains(huLangConfig, "rail_success_break", "&2Kiütöttél egy rejtett sínt !");
			checkConfContains(huLangConfig, "rail_success_unhide", "&2Megjelenítetted a sineket !");
			checkConfContains(huLangConfig, "water_protection_status_success_change", "&2Ez a(z) %status% státusza a víz alatti védelem ebben a világban %world%");
			checkConfContains(huLangConfig, "invalid_worldname", "&cA világ neve hibás !");
			checkConfContains(huLangConfig, "plugin_success_reloaded", "&2A plugin sikeresen reloadolva(újratöltve)!");
			checkConfContains(huLangConfig, "water_protection_status_already", "&cA víz alatti védelem a(z) %world% világban %status%");
			checkConfContains(huLangConfig, "no_backup", "&cNincsenek mentések !");
			checkConfContains(huLangConfig, "return_backup_success", "&2Sikeresen vissza lett állítva a mentés !");
			//checkConfContains(huLangConfig, "worldedit_not_installed", "&cNincs letöltve a szerverre a WorldEdit plugin !");
			//checkConfContains(huLangConfig, "worldedit_no_selection", "&cElső lépés, hogy jelöld ki WorldEdit-el a területet !");
			checkConfContains(huLangConfig, "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			checkConfContains(huLangConfig, "hiderails_selection_pos", "&8You have selected position &e%pos%");
			checkConfContains(huLangConfig, "selection_message_status", "&8You have &e%status% &8selection messages!");
			checkConfContains(huLangConfig, "display_hidden_blocks", "&2Szamoda a(z) %hide% blokk rejtett !");
			checkConfContains(huLangConfig, "invalid_player", "&cA játékos nem található !");
			checkConfContains(huLangConfig, "update_found", "&bÙj frissitès elèrhető !\n&o%link%");
			checkConfContains(huLangConfig, "kick_spam_hidden_block", "&cNe spameld a blokkokat !!");
		}
	}
}