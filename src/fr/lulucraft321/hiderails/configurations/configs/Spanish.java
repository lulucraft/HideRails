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

public class Spanish extends FileConfigurationManager implements AbstractLangConfig
{
	@Override
	public void setupConfig()
	{
		// ES.yml
		FileConfigurationManager.esLangFile = new File(FileConfigurationManager.LANG_PATH, "ES.yml");
		if(!FileConfigurationManager.esLangFile.exists())
		{
			try {
				FileConfigurationManager.esLangFile.createNewFile();
			} catch (IOException e) {
				System.out.println("Erreur lors de la creation du fichier de configuration \"" + FileConfigurationManager.esLangConfig.getName().toString() + "\" !");
				return;
			}

			FileConfigurationManager.esLangConfig = YamlConfiguration.loadConfiguration(FileConfigurationManager.esLangFile);
			FileConfigurationManager.esLangConfig.options().header("Gracias a zuhir por la traducción en español !").copyDefaults(true);
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "sender_type_error", "&cDebes ser un jugador para poder ejecutar ese comando.");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "player_no_enough_permission", "&cNo tienes permiso para ejecutar ese comando.");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "rail_success_change", "&2Has reemplazado los railes por %blocktype%.");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "material_type_error", "&cEse bloque no existe.");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "rail_error", "&cEl bloque al que miras no es un rail.");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "rail_success_break", "&2Has destruido un rail oculto.");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "rail_success_unhide", "&2Has hecho visibles los railes.");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "water_protection_status_success_change", "&2La proteccion bajo el agua ahora es %status% en el mundo %world%.");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "invalid_worldname", "&c&cEse mundo no existe.");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "plugin_success_reloaded", "&2Plugin recargado correctamente.");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "water_protection_status_already", "&cLa proteccion bajo el agua em el mundo %world% ya es %status%.");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "no_backup", "&cNo hay copias de seguridad disponibles.");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "return_backup_success", "&2Copia de seguridad reestablecida correctamente.");
			//FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "worldedit_not_installed", "&cWorldedit no esta instalado.");
			//FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "worldedit_no_selection", "&cPrimero debes seleccionar la region con Worldedit.");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "hiderails_selection_pos", "&8You have selected position &e%pos%");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "selection_message_status", "&8You have &e%status% &8selection messages!");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "display_hidden_blocks", "&2Tienes los bloques invisibles en %hide%.");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "invalid_player", "&cEl jugador no existe.");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "update_found", "&bNueva actualizacion disponible !\n&o%link%");
			FileConfigurationManager.esLangConfig.set(FileConfigurationManager.msgPath + "kick_spam_hidden_block", "&cNo spamees bloques por favor !!");

			// Sauveguarde des modifs
			try {
				FileConfigurationManager.esLangConfig.save(FileConfigurationManager.esLangFile);
			} catch (IOException e1) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + FileConfigurationManager.esLangConfig.getName().toString() + "\" !"); }
		} else {
			FileConfigurationManager.esLangConfig = YamlConfiguration.loadConfiguration(FileConfigurationManager.esLangFile);
			FileConfigurationManager.esLangConfig.options().header("Gracias a zuhir por la traducción en español !").copyDefaults(true);
			checkConfContains(FileConfigurationManager.esLangConfig, "sender_type_error", "&cYou must be a player to execute this command !");
			checkConfContains(FileConfigurationManager.esLangConfig, "player_no_enough_permission", "&cNo tienes permiso para ejecutar ese comando.");
			checkConfContains(FileConfigurationManager.esLangConfig, "rail_success_change", "&2Has reemplazado los railes por %blocktype%.");
			checkConfContains(FileConfigurationManager.esLangConfig, "material_type_error", "&cEse bloque no existe.");
			checkConfContains(FileConfigurationManager.esLangConfig, "rail_error", "&cEl bloque al que miras no es un rail.");
			checkConfContains(FileConfigurationManager.esLangConfig, "rail_success_break", "&2Has destruido un rail oculto.");
			checkConfContains(FileConfigurationManager.esLangConfig, "rail_success_unhide", "&2Has hecho visibles los railes.");
			checkConfContains(FileConfigurationManager.esLangConfig, "water_protection_status_success_change", "&2La proteccion bajo el agua ahora es %status% en el mundo %world%.");
			checkConfContains(FileConfigurationManager.esLangConfig, "invalid_worldname", "&c&cEse mundo no existe.");
			checkConfContains(FileConfigurationManager.esLangConfig, "plugin_success_reloaded", "&2Plugin recargado correctamente.");
			checkConfContains(FileConfigurationManager.esLangConfig, "water_protection_status_already", "&cLa proteccion bajo el agua em el mundo %world% ya es %status%.");
			checkConfContains(FileConfigurationManager.esLangConfig, "no_backup", "&cNo hay copias de seguridad disponibles.");
			checkConfContains(FileConfigurationManager.esLangConfig, "return_backup_success", "&2Copia de seguridad reestablecida correctamente.");
			//checkConfContains(FileConfigurationManager.esLangConfig, "worldedit_not_installed", "&cWorldedit no esta instalado.");
			//checkConfContains(FileConfigurationManager.esLangConfig, "worldedit_no_selection", "&cPrimero debes seleccionar la region con Worldedit.");
			checkConfContains(FileConfigurationManager.esLangConfig, "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			checkConfContains(FileConfigurationManager.esLangConfig, "hiderails_selection_pos", "&8You have selected position &e%pos%");
			checkConfContains(FileConfigurationManager.esLangConfig, "selection_message_status", "&8You have &e%pos% &8selection messages!");
			checkConfContains(FileConfigurationManager.esLangConfig, "display_hidden_blocks", "&2Tienes los bloques invisibles en %hide%.");
			checkConfContains(FileConfigurationManager.esLangConfig, "invalid_player", "&cEl jugador no existe.");
			checkConfContains(FileConfigurationManager.esLangConfig, "update_found", "&bNueva actualizacion disponible !\n&o%link%");
			checkConfContains(FileConfigurationManager.esLangConfig, "kick_spam_hidden_block", "&cNo spamees bloques por favor !!");

			// Sauveguarde des modifs
			try {
				FileConfigurationManager.esLangConfig.save(FileConfigurationManager.esLangFile);
			} catch (IOException e) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + FileConfigurationManager.esLangConfig.getName().toString() + "\" !"); }
		}
		FileConfigurationManager.esLangConfig = YamlConfiguration.loadConfiguration(FileConfigurationManager.esLangFile);
		//
	}
}