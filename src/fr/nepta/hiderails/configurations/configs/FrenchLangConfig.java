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

public class FrenchLangConfig extends AbstractLangConfig
{
	public FrenchLangConfig() {
		super(new File(FileConfigurationManager.LANG_PATH, "FR.yml"));
	}

	@Override
	public void setupConfig()
	{
		// FR.yml
		File frLangFile = getFile();
		FileConfiguration frLangConfig;

		if(!frLangFile.exists())
		{
			try {
				frLangFile.createNewFile();
			} catch (IOException e) {
				MessagesManager.LOGGER.warning("Erreur lors de la creation du fichier de configuration \"" + frLangFile.getName() + "\" !");
				return;
			}

			frLangConfig = loadConfig();
			frLangConfig.options().header("Language by Nepta_").copyDefaults(true);
			frLangConfig.set(MSG_PATH + "sender_type_error", "&cSeul un joueur peut executer cette commande !");
			frLangConfig.set(MSG_PATH + "player_no_enough_permission", "&cVous n'avez pas la permission d'éxécuter cette commande !");
			frLangConfig.set(MSG_PATH + "rail_success_change", "&2Vous avez remplacé les rails par %blocktype%");
			frLangConfig.set(MSG_PATH + "material_type_error", "&cCe bloc n'existe pas !");
			frLangConfig.set(MSG_PATH + "rail_error", "&cLe bloc que vous visez n'est pas un rail !");
			frLangConfig.set(MSG_PATH + "rail_success_break", "&2Vous avez cassé un rail masqué !");
			frLangConfig.set(MSG_PATH + "rail_success_unhide", "&2Vous avez fait re apparaitre les rails !");
			frLangConfig.set(MSG_PATH + "water_protection_status_success_change", "&2Vous avez %status% la protection de la redstone sous l'eau pour le monde %world%");
			frLangConfig.set(MSG_PATH + "invalid_worldname", "&cMonde invalide !");
			frLangConfig.set(MSG_PATH + "plugin_success_reloaded", "&2Plugin rechargé avec succès !");
			frLangConfig.set(MSG_PATH + "water_protection_status_already", "&cLa protection de la redstone et des rails sous l'eau dans le monde %world% est déja définit sur %status%");
			frLangConfig.set(MSG_PATH + "no_backup", "&cAucune sauvegarde disponible !");
			frLangConfig.set(MSG_PATH + "return_backup_success", "&2Sauvegarde restorée avec succès !");
			//frLangConfig.set(MSG_PATH + "worldedit_not_installed", "&cLe plugin worldedit n'est pas installé sur le serveur !");
			//frLangConfig.set(MSG_PATH + "worldedit_no_selection", "&cVous devez d'abord sélectionner une région avec worldedit !");
			frLangConfig.set(MSG_PATH + "hiderails_no_selection", "&cVous devez d'abord sélectionner une region avec la hache en bois !");
			frLangConfig.set(MSG_PATH + "hiderails_selection_pos", "&8Vous avez sélectionné la position &e%pos%");
			frLangConfig.set(MSG_PATH + "selection_message_status", "&8Vous avez &e%status% &8les messages de sélection !");
			frLangConfig.set(MSG_PATH + "display_hidden_blocks", "&2Vous avez %hide% le masquage des blocs pour vous !");
			frLangConfig.set(MSG_PATH + "invalid_player", "&cLe joueur n'est pas connecté/n'existe pas !");
			frLangConfig.set(MSG_PATH + "update_found", "&bUne nouvelle mise à jour est disponible !\n&o%link%");
			frLangConfig.set(MSG_PATH + "kick_spam_hidden_block", "&cTu veux bien arrêter de spam sur les blocs svp ??!!");
		} else {
			frLangConfig = loadConfig();
			frLangConfig.options().header("Language by Nepta_").copyDefaults(true);
			checkConfContains(frLangConfig, "sender_type_error", "&cSeul un joueur peut executer cette commande !");
			checkConfContains(frLangConfig, "player_no_enough_permission", "&cVous n'avez pas la permission d'éxécuter cette commande !");
			checkConfContains(frLangConfig, "rail_success_change", "&2Vous avez remplacé les rails par %blocktype%");
			checkConfContains(frLangConfig, "material_type_error", "&cCe bloc n'existe pas !");
			checkConfContains(frLangConfig, "rail_error", "&cLe bloc que vous visez n'est pas un rail !");
			checkConfContains(frLangConfig, "rail_success_break", "&2Vous avez cassé un rail masqué !");
			checkConfContains(frLangConfig, "rail_success_unhide", "&2Vous avez fait re apparaitre les rails !");
			checkConfContains(frLangConfig, "water_protection_status_success_change", "&2Vous avez %status% la protection de la redstone sous l'eau pour le monde %world%");
			checkConfContains(frLangConfig, "invalid_worldname", "&cMonde invalide !");
			checkConfContains(frLangConfig, "plugin_success_reloaded", "&2Plugin rechargé avec succès !");
			checkConfContains(frLangConfig, "water_protection_status_already", "&cLa protection de la redstone et des rails sous l'eau dans le monde %world% est déja définit sur %status%");
			checkConfContains(frLangConfig, "no_backup", "&cAucune sauvegarde disponible !");
			checkConfContains(frLangConfig, "return_backup_success", "&cSauvegarde restorée avec succès !");
			//checkConfContains(frLangConfig, "worldedit_not_installed", "&cLe plugin worldedit n'est pas installé sur le serveur !");
			//checkConfContains(frLangConfig, "worldedit_no_selection", "&cVous devez d'abord sélectionner une région avec worldedit !");
			checkConfContains(frLangConfig, "hiderails_no_selection", "&cVous devez d'abord sélectionner une region avec la hache en bois !");
			checkConfContains(frLangConfig, "hiderails_selection_pos", "&8Vous avez sélectionné la position &e%pos%");
			checkConfContains(frLangConfig, "selection_message_status", "&8Vous avez &e%status% &8les messages de sélection !");
			checkConfContains(frLangConfig, "display_hidden_blocks", "&2Vous avez %hide% le masquage des blocs pour vous !");
			checkConfContains(frLangConfig, "invalid_player", "&cLe joueur n'est pas connecté/n'existe pas !");
			checkConfContains(frLangConfig, "update_found", "&bUne nouvelle mise à jour est disponible !\n&o%link%");
			checkConfContains(frLangConfig, "kick_spam_hidden_block", "&cTu veux bien arrêter de spam sur les blocs svp ??!!");
		}
	}
}