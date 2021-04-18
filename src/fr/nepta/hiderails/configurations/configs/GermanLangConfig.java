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

public class GermanLangConfig extends AbstractLangConfig
{
	public GermanLangConfig() {
		super(new File(FileConfigurationManager.LANG_PATH, "DE.yml"));
	}

	@Override
	public void setupConfig()
	{
		// DE.yml
		File deLangFile = getFile();
		FileConfiguration deLangConfig;

		if(!deLangFile.exists())
		{
			try {
				deLangFile.createNewFile();
			} catch (IOException e) {
				System.out.println("Erreur lors de la creation du fichier de configuration \"" + deLangFile.getName().toString() + "\"!");
				return;
			}

			deLangConfig = loadConfig();
			deLangConfig.options().header("Translation by DevSnox").copyDefaults(true);
			deLangConfig.set(MSG_PATH + "sender_type_error", "&cNur Spieler können diesen Command ausführen!");
			deLangConfig.set(MSG_PATH + "player_no_enough_permission", "&cDir fehlen die benötigten Rechte!");
			deLangConfig.set(MSG_PATH + "rail_success_change", "&2Du hast die Schienen durch %blocktype% ersetzt!");
			deLangConfig.set(MSG_PATH + "material_type_error", "&cDieser Block existiert nicht!");
			deLangConfig.set(MSG_PATH + "rail_error", "&cDer ausgewählte Block ist keine Schiene!");
			deLangConfig.set(MSG_PATH + "rail_success_break", "&2Du hast eine versteckte Schiene abgebaut!");
			deLangConfig.set(MSG_PATH + "rail_success_unhide", "&2Die Schienen sind nun wieder sichtbar!");
			deLangConfig.set(MSG_PATH + "water_protection_status_success_change", "&2Der unterwasser Schutz iwurde auf %status% in %world% geändert.");
			deLangConfig.set(MSG_PATH + "invalid_worldname", "&cDiese Welt existiert nicht!");
			deLangConfig.set(MSG_PATH + "plugin_success_reloaded", "&2Das Plugin wurde erfolgreich neugeladen.");
			deLangConfig.set(MSG_PATH + "water_protection_status_already", "&cDer unterwasser Schutz in %world% ist schon %status%");
			deLangConfig.set(MSG_PATH + "no_backup", "&cKein Backup verfügbar!");
			deLangConfig.set(MSG_PATH + "return_backup_success", "&2Das Backup wurde erfolgreich wiederhergestellt!");
			//deLangConfig.set(MSG_PATH + "worldedit_not_installed", "&cWorldEdit ist nicht auf dem Server installiert!");
			//deLangConfig.set(MSG_PATH + "worldedit_no_selection", "&cDu musst erst eine WorldEdit-Selektion machen!");
			deLangConfig.set(MSG_PATH + "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			deLangConfig.set(MSG_PATH + "hiderails_selection_pos", "&8You have selected position &e%pos%");
			deLangConfig.set(MSG_PATH + "selection_message_status", "&8You have &e%status% &8selection messages!");
			deLangConfig.set(MSG_PATH + "display_hidden_blocks", "&2Du hast die versteckten Schienen für dich %hide%!");
			deLangConfig.set(MSG_PATH + "invalid_player", "&cDieser Spieler existiert nicht!");
			deLangConfig.set(MSG_PATH + "update_found", "&bEine neue Version ist verfügbar: !\n&o%link%");
			deLangConfig.set(MSG_PATH + "kick_spam_hidden_block", "&cDon't spam blocks please!!");
		} else {
			deLangConfig = loadConfig();
			deLangConfig.options().header("Translation by DevSnox").copyDefaults(true);
			checkConfContains(deLangConfig, "sender_type_error", "&cNur Spieler können diesen Command ausführen!");
			checkConfContains(deLangConfig, "player_no_enough_permission", "&cDir fehlen die benötigten Rechte!");
			checkConfContains(deLangConfig, "rail_success_change", "&2Du hast die Schienen durch %blocktype% ersetzt!");
			checkConfContains(deLangConfig, "material_type_error", "&cDieser Block existiert nicht!");
			checkConfContains(deLangConfig, "rail_error", "&cDer ausgewählte Block ist keine Schiene!");
			checkConfContains(deLangConfig, "rail_success_break", "&2Du hast eine versteckte Schiene abgebaut!");
			checkConfContains(deLangConfig, "rail_success_unhide", "&2Die Schienen sind nun wieder sichtbar!");
			checkConfContains(deLangConfig, "water_protection_status_success_change", "&2Der unterwasser Schutz iwurde auf %status% in %world% geändert.");
			checkConfContains(deLangConfig, "invalid_worldname", "&cDiese Welt existiert nicht!");
			checkConfContains(deLangConfig, "plugin_success_reloaded", "&2Das Plugin wurde erfolgreich neugeladen.");
			checkConfContains(deLangConfig, "water_protection_status_already", "&cDer unterwasser Schutz in %world% ist schon %status%");
			checkConfContains(deLangConfig, "no_backup", "&cKein Backup verfügbar!");
			checkConfContains(deLangConfig, "return_backup_success", "&2Das Backup wurde erfolgreich wiederhergestellt!");
			//checkConfContains(deLangConfig, "worldedit_not_installed", "&cWorldEdit ist nicht auf dem Server installiert!");
			//checkConfContains(deLangConfig, "worldedit_no_selection", "&cDu musst erst eine WorldEdit-Selektion machen!");
			checkConfContains(deLangConfig, "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			checkConfContains(deLangConfig, "hiderails_selection_pos", "&8You have selected position &e%pos%");
			checkConfContains(deLangConfig, "selection_message_status", "&8You have &e%status% &8selection messages!");
			checkConfContains(deLangConfig, "display_hidden_blocks", "&2Du hast die versteckten Schienen für dich %hide%!");
			checkConfContains(deLangConfig, "invalid_player", "&cDieser Spieler existiert nicht!");
			checkConfContains(deLangConfig, "update_found", "&bEine neue Version ist verfügbar: !\n&o%link%");
			checkConfContains(deLangConfig, "kick_spam_hidden_block", "&cDon't spam blocks please!!");
		}
		// Save file
		save();
	}

}