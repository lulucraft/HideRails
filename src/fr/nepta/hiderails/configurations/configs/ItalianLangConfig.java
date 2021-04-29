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

public class ItalianLangConfig extends AbstractLangConfig
{
	public ItalianLangConfig() {
		super(new File(FileConfigurationManager.LANG_PATH, "IT.yml"));
	}

	@Override
	public void setupConfig()
	{
		// IT.yml
		File itLangFile = getFile();
		FileConfiguration itLangConfig;

		if(!itLangFile.exists())
		{
			try {
				itLangFile.createNewFile();
			} catch (IOException e) {
				MessagesManager.LOGGER.warning("Error when creating file \"" + itLangFile.getName() + "\" !");
				return;
			}

			itLangConfig = loadConfig();
			itLangConfig.options().header("Grazie a Sitieno14 per la traduzione in italiano !").copyDefaults(true);
			itLangConfig.set(MSG_PATH + "sender_type_error", "&cDevi essere un giocatore per poter eseguire questo comando !");
			itLangConfig.set(MSG_PATH + "player_no_enough_permission", "&cNon hai il permesso per eseguire questo comando !");
			itLangConfig.set(MSG_PATH + "rail_success_change", "&2Hai rimpiazzato i binari con %blocktype% !");
			itLangConfig.set(MSG_PATH + "material_type_error", "&cQuesto blocco non esiste !");
			itLangConfig.set(MSG_PATH + "rail_error", "&cIl blocco da te bersagliato non è un binario !");
			itLangConfig.set(MSG_PATH + "rail_success_break", "&2Hai rotto un binario nascosto !");
			itLangConfig.set(MSG_PATH + "rail_success_unhide", "&2Hai mostrato i binari !");
			itLangConfig.set(MSG_PATH + "water_protection_status_success_change", "&2Tu hai %status% la protezione sott'acqua nel mondo %world%");
			itLangConfig.set(MSG_PATH + "invalid_worldname", "&cIl nome di questo mondo non è valido");
			itLangConfig.set(MSG_PATH + "plugin_success_reloaded", "&2Plugin ricaricato con successo !");
			itLangConfig.set(MSG_PATH + "water_protection_status_already", "&cLa protezione sott'acqua nel mondo %world% è già %status%");
			itLangConfig.set(MSG_PATH + "no_backup", "&cNessun backup disponibile !");
			itLangConfig.set(MSG_PATH + "return_backup_success", "&2Backup ripristinato con successo !");
			//itLangConfig.set(MSG_PATH + "worldedit_not_installed", "&cIl Plugin WorldEdit non è installato in questo server !");
			//itLangConfig.set(MSG_PATH + "worldedit_no_selection", "&cDevi prima selezionare una regione con WorldEdit !");
			itLangConfig.set(MSG_PATH + "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			itLangConfig.set(MSG_PATH + "hiderails_selection_pos", "&8You have selected position &e%pos%");
			itLangConfig.set(MSG_PATH + "selection_message_status", "&8You have &e%status% &8selection messages!");
			itLangConfig.set(MSG_PATH + "display_hidden_blocks", "&2Hai %hide% i blocchi nascosti per te !");
			itLangConfig.set(MSG_PATH + "invalid_player", "&cIl giocatore non può essere trovato");
			itLangConfig.set(MSG_PATH + "update_found", "&bNuovo aggiornamento disponibile !\n&o%link%");
			itLangConfig.set(MSG_PATH + "kick_spam_hidden_block", "&cNon spammare blocchi perfavore !!");
		} else {
			itLangConfig = loadConfig();
			itLangConfig.options().header("Grazie a Sitieno14 per la traduzione in italiano !").copyDefaults(true);
			checkConfContains(itLangConfig, "sender_type_error", "&cDevi essere un giocatore per poter eseguire questo comando !");
			checkConfContains(itLangConfig, "player_no_enough_permission", "&cNon hai il permesso per eseguire questo comando !");
			checkConfContains(itLangConfig, "rail_success_change", "&2Hai rimpiazzato i binari con %blocktype% !");
			checkConfContains(itLangConfig, "material_type_error", "&cQuesto blocco non esiste !");
			checkConfContains(itLangConfig, "rail_error", "&cIl blocco da te bersagliato non è un binario !");
			checkConfContains(itLangConfig, "rail_success_break", "&2Hai rotto un binario nascosto !");
			checkConfContains(itLangConfig, "rail_success_unhide", "&2Hai mostrato i binari !");
			checkConfContains(itLangConfig, "water_protection_status_success_change", "&2Tu hai %status% la protezione sott'acqua nel mondo %world%");
			checkConfContains(itLangConfig, "invalid_worldname", "&cIl nome di questo mondo non è valido");
			checkConfContains(itLangConfig, "plugin_success_reloaded", "&2Plugin ricaricato con successo !");
			checkConfContains(itLangConfig, "water_protection_status_already", "&cLa protezione sott'acqua nel mondo %world% è già %status%");
			checkConfContains(itLangConfig, "no_backup", "&cNessun backup disponibile !");
			checkConfContains(itLangConfig, "return_backup_success", "&2Backup ripristinato con successo !");
			//checkConfContains(itLangConfig, "worldedit_not_installed", "&cIl Plugin WorldEdit non è installato in questo server !");
			//checkConfContains(itLangConfig, "worldedit_no_selection", "&cDevi prima selezionare una regione con WorldEdit !");
			checkConfContains(itLangConfig, "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			checkConfContains(itLangConfig, "hiderails_selection_pos", "&8You have selected position &e%pos%");
			checkConfContains(itLangConfig, "selection_message_status", "&8You have &e%status% &8selection messages!");
			checkConfContains(itLangConfig, "display_hidden_blocks", "&2Hai %hide% i blocchi nascosti per te !");
			checkConfContains(itLangConfig, "invalid_player", "&cIl giocatore non può essere trovato");
			checkConfContains(itLangConfig, "update_found", "&bNuovo aggiornamento disponibile !\n&o%link%");
			checkConfContains(itLangConfig, "kick_spam_hidden_block", "&cNon spammare blocchi perfavore !!");
		}
	}
}