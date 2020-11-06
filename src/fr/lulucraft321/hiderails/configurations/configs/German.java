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

public class German extends FileConfigurationManager implements AbstractLangConfig
{
	@Override
	public void setupConfig()
	{
		// DE.yml
		FileConfigurationManager.deLangFile = new File(FileConfigurationManager.LANG_PATH, "DE.yml");
		if(!FileConfigurationManager.deLangFile.exists())
		{
			try {
				FileConfigurationManager.deLangFile.createNewFile();
			} catch (IOException e) {
				System.out.println("Erreur lors de la creation du fichier de configuration \"" + FileConfigurationManager.deLangConfig.getName().toString() + "\"!");
				return;
			}

			FileConfigurationManager.deLangConfig = YamlConfiguration.loadConfiguration(FileConfigurationManager.deLangFile);
			FileConfigurationManager.deLangConfig.options().header("Translation by DevSnox").copyDefaults(true);
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "sender_type_error", "&cNur Spieler können diesen Command ausführen!");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "player_no_enough_permission", "&cDir fehlen die benötigten Rechte!");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "rail_success_change", "&2Du hast die Schienen durch %blocktype% ersetzt!");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "material_type_error", "&cDieser Block existiert nicht!");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "rail_error", "&cDer ausgewählte Block ist keine Schiene!");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "rail_success_break", "&2Du hast eine versteckte Schiene abgebaut!");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "rail_success_unhide", "&2Die Schienen sind nun wieder sichtbar!");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "water_protection_status_success_change", "&2Der unterwasser Schutz iwurde auf %status% in %world% geändert.");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "invalid_worldname", "&cDiese Welt existiert nicht!");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "plugin_success_reloaded", "&2Das Plugin wurde erfolgreich neugeladen.");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "water_protection_status_already", "&cDer unterwasser Schutz in %world% ist schon %status%");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "no_backup", "&cKein Backup verfügbar!");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "return_backup_success", "&2Das Backup wurde erfolgreich wiederhergestellt!");
			//FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "worldedit_not_installed", "&cWorldEdit ist nicht auf dem Server installiert!");
			//FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "worldedit_no_selection", "&cDu musst erst eine WorldEdit-Selektion machen!");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "hiderails_selection_pos", "&8You have selected position &e%pos%");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "selection_message_status", "&8You have &e%status% &8selection messages!");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "display_hidden_blocks", "&2Du hast die versteckten Schienen für dich %hide%!");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "invalid_player", "&cDieser Spieler existiert nicht!");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "update_found", "&bEine neue Version ist verfügbar: !\n&o%link%");
			FileConfigurationManager.deLangConfig.set(FileConfigurationManager.MSG_PATH + "kick_spam_hidden_block", "&cDon't spam blocks please!!");

			// Sauveguarde des modifs
			try {
				FileConfigurationManager.deLangConfig.save(FileConfigurationManager.deLangFile);
			} catch (IOException e1) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + FileConfigurationManager.deLangConfig.getName().toString() + "\"!"); }
		} else {
			FileConfigurationManager.deLangConfig = YamlConfiguration.loadConfiguration(FileConfigurationManager.deLangFile);
			FileConfigurationManager.deLangConfig.options().header("Translation by DevSnox").copyDefaults(true);
			checkConfContains(FileConfigurationManager.deLangConfig, "sender_type_error", "&cNur Spieler können diesen Command ausführen!");
			checkConfContains(FileConfigurationManager.deLangConfig, "player_no_enough_permission", "&cDir fehlen die benötigten Rechte!");
			checkConfContains(FileConfigurationManager.deLangConfig, "rail_success_change", "&2Du hast die Schienen durch %blocktype% ersetzt!");
			checkConfContains(FileConfigurationManager.deLangConfig, "material_type_error", "&cDieser Block existiert nicht!");
			checkConfContains(FileConfigurationManager.deLangConfig, "rail_error", "&cDer ausgewählte Block ist keine Schiene!");
			checkConfContains(FileConfigurationManager.deLangConfig, "rail_success_break", "&2Du hast eine versteckte Schiene abgebaut!");
			checkConfContains(FileConfigurationManager.deLangConfig, "rail_success_unhide", "&2Die Schienen sind nun wieder sichtbar!");
			checkConfContains(FileConfigurationManager.deLangConfig, "water_protection_status_success_change", "&2Der unterwasser Schutz iwurde auf %status% in %world% geändert.");
			checkConfContains(FileConfigurationManager.deLangConfig, "invalid_worldname", "&cDiese Welt existiert nicht!");
			checkConfContains(FileConfigurationManager.deLangConfig, "plugin_success_reloaded", "&2Das Plugin wurde erfolgreich neugeladen.");
			checkConfContains(FileConfigurationManager.deLangConfig, "water_protection_status_already", "&cDer unterwasser Schutz in %world% ist schon %status%");
			checkConfContains(FileConfigurationManager.deLangConfig, "no_backup", "&cKein Backup verfügbar!");
			checkConfContains(FileConfigurationManager.deLangConfig, "return_backup_success", "&2Das Backup wurde erfolgreich wiederhergestellt!");
			//checkConfContains(FileConfigurationManager.deLangConfig, "worldedit_not_installed", "&cWorldEdit ist nicht auf dem Server installiert!");
			//checkConfContains(FileConfigurationManager.deLangConfig, "worldedit_no_selection", "&cDu musst erst eine WorldEdit-Selektion machen!");
			checkConfContains(FileConfigurationManager.deLangConfig, "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			checkConfContains(FileConfigurationManager.deLangConfig, "hiderails_selection_pos", "&8You have selected position &e%pos%");
			checkConfContains(FileConfigurationManager.deLangConfig, "selection_message_status", "&8You have &e%status% &8selection messages!");
			checkConfContains(FileConfigurationManager.deLangConfig, "display_hidden_blocks", "&2Du hast die versteckten Schienen für dich %hide%!");
			checkConfContains(FileConfigurationManager.deLangConfig, "invalid_player", "&cDieser Spieler existiert nicht!");
			checkConfContains(FileConfigurationManager.deLangConfig, "update_found", "&bEine neue Version ist verfügbar: !\n&o%link%");
			checkConfContains(FileConfigurationManager.deLangConfig, "kick_spam_hidden_block", "&cDon't spam blocks please!!");

			// Sauveguarde des modifs
			try {
				FileConfigurationManager.deLangConfig.save(FileConfigurationManager.deLangFile);
			} catch (IOException e) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + FileConfigurationManager.deLangConfig.getName().toString() + "\"!"); }
		}
		FileConfigurationManager.deLangConfig = YamlConfiguration.loadConfiguration(FileConfigurationManager.deLangFile);
		//
	}
}