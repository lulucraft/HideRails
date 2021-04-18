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

public class SpanishLangConfig extends AbstractLangConfig
{
	public SpanishLangConfig() {
		super(new File(FileConfigurationManager.LANG_PATH, "ES.yml"));
	}

	@Override
	public void setupConfig()
	{
		// ES.yml
		File esLangFile = getFile();
		FileConfiguration esLangConfig;

		if(!esLangFile.exists())
		{
			try {
				esLangFile.createNewFile();
			} catch (IOException e) {
				System.out.println("Error when creating file \"" + esLangFile.getName() + "\" !");
				return;
			}

			esLangConfig = loadConfig();
			esLangConfig.options().header("Gracias a zuhir por la traducción en español !").copyDefaults(true);
			esLangConfig.set(MSG_PATH + "sender_type_error", "&cDebes ser un jugador para poder ejecutar ese comando.");
			esLangConfig.set(MSG_PATH + "player_no_enough_permission", "&cNo tienes permiso para ejecutar ese comando.");
			esLangConfig.set(MSG_PATH + "rail_success_change", "&2Has reemplazado los railes por %blocktype%.");
			esLangConfig.set(MSG_PATH + "material_type_error", "&cEse bloque no existe.");
			esLangConfig.set(MSG_PATH + "rail_error", "&cEl bloque al que miras no es un rail.");
			esLangConfig.set(MSG_PATH + "rail_success_break", "&2Has destruido un rail oculto.");
			esLangConfig.set(MSG_PATH + "rail_success_unhide", "&2Has hecho visibles los railes.");
			esLangConfig.set(MSG_PATH + "water_protection_status_success_change", "&2La proteccion bajo el agua ahora es %status% en el mundo %world%.");
			esLangConfig.set(MSG_PATH + "invalid_worldname", "&cEse mundo no existe.");
			esLangConfig.set(MSG_PATH + "plugin_success_reloaded", "&2Plugin recargado correctamente.");
			esLangConfig.set(MSG_PATH + "water_protection_status_already", "&cLa proteccion bajo el agua em el mundo %world% ya es %status%.");
			esLangConfig.set(MSG_PATH + "no_backup", "&cNo hay copias de seguridad disponibles.");
			esLangConfig.set(MSG_PATH + "return_backup_success", "&2Copia de seguridad reestablecida correctamente.");
			//esLangConfig.set(MSG_PATH + "worldedit_not_installed", "&cWorldedit no esta instalado.");
			//esLangConfig.set(MSG_PATH + "worldedit_no_selection", "&cPrimero debes seleccionar la region con Worldedit.");
			esLangConfig.set(MSG_PATH + "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			esLangConfig.set(MSG_PATH + "hiderails_selection_pos", "&8You have selected position &e%pos%");
			esLangConfig.set(MSG_PATH + "selection_message_status", "&8You have &e%status% &8selection messages!");
			esLangConfig.set(MSG_PATH + "display_hidden_blocks", "&2Tienes los bloques invisibles en %hide%.");
			esLangConfig.set(MSG_PATH + "invalid_player", "&cEl jugador no existe.");
			esLangConfig.set(MSG_PATH + "update_found", "&bNueva actualizacion disponible !\n&o%link%");
			esLangConfig.set(MSG_PATH + "kick_spam_hidden_block", "&cNo spamees bloques por favor !!");
		} else {
			esLangConfig = loadConfig();
			esLangConfig.options().header("Gracias a zuhir por la traducción en español !").copyDefaults(true);
			checkConfContains(esLangConfig, "sender_type_error", "&cYou must be a player to execute this command !");
			checkConfContains(esLangConfig, "player_no_enough_permission", "&cNo tienes permiso para ejecutar ese comando.");
			checkConfContains(esLangConfig, "rail_success_change", "&2Has reemplazado los railes por %blocktype%.");
			checkConfContains(esLangConfig, "material_type_error", "&cEse bloque no existe.");
			checkConfContains(esLangConfig, "rail_error", "&cEl bloque al que miras no es un rail.");
			checkConfContains(esLangConfig, "rail_success_break", "&2Has destruido un rail oculto.");
			checkConfContains(esLangConfig, "rail_success_unhide", "&2Has hecho visibles los railes.");
			checkConfContains(esLangConfig, "water_protection_status_success_change", "&2La proteccion bajo el agua ahora es %status% en el mundo %world%.");
			checkConfContains(esLangConfig, "invalid_worldname", "&cEse mundo no existe.");
			checkConfContains(esLangConfig, "plugin_success_reloaded", "&2Plugin recargado correctamente.");
			checkConfContains(esLangConfig, "water_protection_status_already", "&cLa proteccion bajo el agua em el mundo %world% ya es %status%.");
			checkConfContains(esLangConfig, "no_backup", "&cNo hay copias de seguridad disponibles.");
			checkConfContains(esLangConfig, "return_backup_success", "&2Copia de seguridad reestablecida correctamente.");
			//checkConfContains(esLangConfig, "worldedit_not_installed", "&cWorldedit no esta instalado.");
			//checkConfContains(esLangConfig, "worldedit_no_selection", "&cPrimero debes seleccionar la region con Worldedit.");
			checkConfContains(esLangConfig, "hiderails_no_selection", "&cYou must first select region with wooden-axe!");
			checkConfContains(esLangConfig, "hiderails_selection_pos", "&8You have selected position &e%pos%");
			checkConfContains(esLangConfig, "selection_message_status", "&8You have &e%pos% &8selection messages!");
			checkConfContains(esLangConfig, "display_hidden_blocks", "&2Tienes los bloques invisibles en %hide%.");
			checkConfContains(esLangConfig, "invalid_player", "&cEl jugador no existe.");
			checkConfContains(esLangConfig, "update_found", "&bNueva actualizacion disponible !\n&o%link%");
			checkConfContains(esLangConfig, "kick_spam_hidden_block", "&cNo spamees bloques por favor !!");
		}
		// Save file
		save();
	}
}