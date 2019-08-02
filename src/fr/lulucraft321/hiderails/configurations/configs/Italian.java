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

public class Italian extends FileConfigurationManager implements AbstractLangConfig
{
	@Override
	public void setupConfig()
	{
		// IT.yml
		FileConfigurationManager.itLangFile = new File(FileConfigurationManager.LANG_PATH, "IT.yml");
		if(!FileConfigurationManager.itLangFile.exists())
		{
			try {
				FileConfigurationManager.itLangFile.createNewFile();
			} catch (IOException e) {
				System.out.println("Erreur lors de la creation du fichier de configuration \"" + FileConfigurationManager.itLangConfig.getName().toString() + "\" !");
				return;
			}

			FileConfigurationManager.itLangConfig = YamlConfiguration.loadConfiguration(FileConfigurationManager.itLangFile);
			FileConfigurationManager.itLangConfig.options().header("Grazie a Sitieno14 per la traduzione in italiano !").copyDefaults(true);
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "sender_type_error", "&cDevi essere un giocatore per poter eseguire questo comando !");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "player_no_enough_permission", "&cNon hai il permesso per eseguire questo comando !");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "rail_success_change", "&2Hai rimpiazzato i binari con %blocktype% !");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "material_type_error", "&cQuesto blocco non esiste !");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "rail_error", "&cIl blocco da te bersagliato non è un binario !");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "rail_success_break", "&2Hai rotto un binario nascosto !");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "rail_success_unhide", "&2Hai mostrato i binari !");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "water_protection_status_success_change", "&2Tu hai %status% la protezione sott'acqua nel mondo %world%");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "invalid_worldname", "&cIl nome di questo mondo non è valido");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "plugin_success_reloaded", "&2Plugin ricaricato con successo !");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "water_protection_status_already", "&cLa protezione sott'acqua nel mondo %world% è già %status%");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "no_backup", "&cNessun backup disponibile !");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "return_backup_success", "&2Backup ripristinato con successo !");
			//FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "worldedit_not_installed", "&cIl Plugin WorldEdit non è installato in questo server !");
			//FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "worldedit_no_selection", "&cDevi prima selezionare una regione con WorldEdit !");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "hiderails_selection_pos", "&8You have selected position &e%pos%");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "selection_message_status", "&8You have &e%status% &8selection messages!");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "display_hidden_blocks", "&2Hai %hide% i blocchi nascosti per te !");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "invalid_player", "&cIl giocatore non può essere trovato");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "update_found", "&bNuovo aggiornamento disponibile !\n&o%link%");
			FileConfigurationManager.itLangConfig.set(FileConfigurationManager.msgPath + "kick_spam_hidden_block", "&cNon spammare blocchi perfavore !!");

			// Sauveguarde des modifs
			try {
				FileConfigurationManager.itLangConfig.save(FileConfigurationManager.itLangFile);
			} catch (IOException e) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + FileConfigurationManager.itLangConfig.getName().toString() + "\" !"); }
		} else {
			FileConfigurationManager.itLangConfig = YamlConfiguration.loadConfiguration(FileConfigurationManager.itLangFile);
			FileConfigurationManager.itLangConfig.options().header("Grazie a Sitieno14 per la traduzione in italiano !").copyDefaults(true);
			checkConfContains(FileConfigurationManager.itLangConfig, "sender_type_error", "&cDevi essere un giocatore per poter eseguire questo comando !");
			checkConfContains(FileConfigurationManager.itLangConfig, "player_no_enough_permission", "&cNon hai il permesso per eseguire questo comando !");
			checkConfContains(FileConfigurationManager.itLangConfig, "rail_success_change", "&2Hai rimpiazzato i binari con %blocktype% !");
			checkConfContains(FileConfigurationManager.itLangConfig, "material_type_error", "&cQuesto blocco non esiste !");
			checkConfContains(FileConfigurationManager.itLangConfig, "rail_error", "&cIl blocco da te bersagliato non è un binario !");
			checkConfContains(FileConfigurationManager.itLangConfig, "rail_success_break", "&2Hai rotto un binario nascosto !");
			checkConfContains(FileConfigurationManager.itLangConfig, "rail_success_unhide", "&2Hai mostrato i binari !");
			checkConfContains(FileConfigurationManager.itLangConfig, "water_protection_status_success_change", "&2Tu hai %status% la protezione sott'acqua nel mondo %world%");
			checkConfContains(FileConfigurationManager.itLangConfig, "invalid_worldname", "&cIl nome di questo mondo non è valido");
			checkConfContains(FileConfigurationManager.itLangConfig, "plugin_success_reloaded", "&2Plugin ricaricato con successo !");
			checkConfContains(FileConfigurationManager.itLangConfig, "water_protection_status_already", "&cLa protezione sott'acqua nel mondo %world% è già %status%");
			checkConfContains(FileConfigurationManager.itLangConfig, "no_backup", "&cNessun backup disponibile !");
			checkConfContains(FileConfigurationManager.itLangConfig, "return_backup_success", "&2Backup ripristinato con successo !");
			//checkConfContains(FileConfigurationManager.itLangConfig, "worldedit_not_installed", "&cIl Plugin WorldEdit non è installato in questo server !");
			//checkConfContains(FileConfigurationManager.itLangConfig, "worldedit_no_selection", "&cDevi prima selezionare una regione con WorldEdit !");
			checkConfContains(FileConfigurationManager.itLangConfig, "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			checkConfContains(FileConfigurationManager.itLangConfig, "hiderails_selection_pos", "&8You have selected position &e%pos%");
			checkConfContains(FileConfigurationManager.itLangConfig, "selection_message_status", "&8You have &e%status% &8selection messages!");
			checkConfContains(FileConfigurationManager.itLangConfig, "display_hidden_blocks", "&2Hai %hide% i blocchi nascosti per te !");
			checkConfContains(FileConfigurationManager.itLangConfig, "invalid_player", "&cIl giocatore non può essere trovato");
			checkConfContains(FileConfigurationManager.itLangConfig, "update_found", "&bNuovo aggiornamento disponibile !\n&o%link%");
			checkConfContains(FileConfigurationManager.itLangConfig, "kick_spam_hidden_block", "&cNon spammare blocchi perfavore !!");

			// Sauveguarde des modifs
			try {
				FileConfigurationManager.itLangConfig.save(FileConfigurationManager.itLangFile);
			} catch (IOException e) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + FileConfigurationManager.itLangConfig.getName().toString() + "\" !"); }
		}
		FileConfigurationManager.itLangConfig = YamlConfiguration.loadConfiguration(FileConfigurationManager.itLangFile);
		//
	}
}