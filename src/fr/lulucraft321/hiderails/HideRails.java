/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.lulucraft321.hiderails.commands.HideRailsCommand;
import fr.lulucraft321.hiderails.commands.TabComplete;
import fr.lulucraft321.hiderails.events.JoinEvent;
import fr.lulucraft321.hiderails.events.RailBreakEvent;
import fr.lulucraft321.hiderails.events.RedstoneInWaterEvents;
import fr.lulucraft321.hiderails.manager.HideRailsManager;
import fr.lulucraft321.hiderails.manager.MessagesManager;
import fr.lulucraft321.hiderails.updater.SpigotUpdater;

public class HideRails extends JavaPlugin
{
	private static HideRails instance;
	public static HideRails getInstance() { return instance; }

	private static String language;
	public static String getLanguage() { return language; }


	private String path = "plugins/HideRails/Languages";
	private File langFolder = new File(path);
	private File config = new File("plugins/HideRails", "config.yml");

	private File hiddenRailsFile;
	private File frLangFile;
	private File enLangFile;

	private FileConfiguration hiddenRailsConfig;
	private FileConfiguration frLangConfig;
	private FileConfiguration enLangConfig;
	private FileConfiguration langConfig;

	public FileConfiguration getHiddenRailsConfig() { return hiddenRailsConfig; }
	public FileConfiguration getFrLangConfig() { return frLangConfig; }
	public FileConfiguration getEnLangConfig() { return enLangConfig; }
	public FileConfiguration getLangConfig() { return langConfig; }

	public final String msgPath = "messages.";

	public void setupConfig()
	{
		if(!config.exists())
		{
			saveDefaultConfig();
		}

		FileConfiguration configConf = YamlConfiguration.loadConfiguration(config);
		if(!configConf.contains("hideRails")) {
			getConfig().set("hideRails", true);
			saveConfig();
		}
		if(!configConf.contains("hideIronBars")) {
			getConfig().set("hideIronBars", false);
			saveConfig();
		}
		if(!configConf.contains("hideCommandBlock")) {
			getConfig().set("hideCommandBlock", false);
			saveConfig();
		}
		if(!configConf.contains("hideRedstone")) {
			getConfig().set("hideRedstone", false);
			saveConfig();
		}
		if(!configConf.contains("hideSigns")) {
			getConfig().set("hideSigns", false);
			saveConfig();
		}

		if(!langFolder.exists())
			langFolder.mkdirs();

		// HiddenRails.yml
		hiddenRailsFile = new File("plugins/HideRails", "HiddenRails.yml");
		if(!hiddenRailsFile.exists())
		{
			try {
				hiddenRailsFile.createNewFile();
			} catch (IOException e) {
				System.out.println("Erreur lors de la creation du fichier de configuration \"" + hiddenRailsConfig.getName().toString() + "\" !");
				return;
			}
		}
		hiddenRailsConfig = YamlConfiguration.loadConfiguration(hiddenRailsFile);
		//

		// FR.yml
		frLangFile = new File(path, "FR.yml");
		if(!frLangFile.exists())
		{
			try {
				frLangFile.createNewFile();
			} catch (IOException e) {
				System.out.println("Erreur lors de la creation du fichier de configuration \"" + frLangConfig.getName().toString() + "\" !");
				return;
			}

			frLangConfig = YamlConfiguration.loadConfiguration(frLangFile);
			frLangConfig.set(msgPath + "sender_type_error", "&cSeul un joueur peut executer cette commande !");
			frLangConfig.set(msgPath + "player_no_enough_permission", "&cVous n'avez pas la permission d'éxécuter cette commande !");
			frLangConfig.set(msgPath + "rail_success_change", "&2Vous avez remplacé les rails par %blocktype%");
			frLangConfig.set(msgPath + "material_type_error", "&cCe bloc n'existe pas !");
			frLangConfig.set(msgPath + "rail_error", "&cLe bloc que vous visez n'est pas un rail !");
			frLangConfig.set(msgPath + "rail_success_break", "&2Vous avez cassé un rail masqué !");
			frLangConfig.set(msgPath + "rail_success_unhide", "&2Vous avez fait re apparaitre les rails !");
			frLangConfig.set(msgPath + "water_protection_status_success_change", "&2Vous avez %status% la protection de la redstone sous l'eau pour le monde %world%");
			frLangConfig.set(msgPath + "invalid_worldname", "&cMonde invalide !");
			frLangConfig.set(msgPath + "plugin_success_reloaded", "&2Plugin rechargé avec succès !");
			frLangConfig.set(msgPath + "water_protection_status_already", "&cLa protection de la redstone et des rails sous l'eau dans le monde %world% est déja définit sur %status%");
			frLangConfig.set(msgPath + "no_backup", "&cAucune sauvegarde disponible !");
			frLangConfig.set(msgPath + "return_backup_success", "&2Sauvegarde restorée avec succès !");
			frLangConfig.set(msgPath + "worldedit_not_installed", "&cLe plugin worldedit n'est pas installé sur le serveur !");
			frLangConfig.set(msgPath + "worldedit_no_selection", "&cVous devez d'abord sélectionner une région avec worldedit !");
			frLangConfig.set(msgPath + "display_hidden_blocks", "&2Vous avez %hide% le masquage des blocks pour vous !");

			// Sauveguarde des modifs
			try {
				frLangConfig.save(frLangFile);
			} catch (IOException e1) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + frLangConfig.getName().toString() + "\" !"); }
		} else {
			frLangConfig = YamlConfiguration.loadConfiguration(frLangFile);
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
			checkConfContains(frLangConfig, "worldedit_not_installed", "&cLe plugin worldedit n'est pas installé sur le serveur !");
			checkConfContains(frLangConfig, "worldedit_no_selection", "&cVous devez d'abord sélectionner une région avec worldedit !");
			checkConfContains(frLangConfig, "display_hidden_blocks", "&2Vous avez %hide% le masquage des blocks pour vous !");

			// Sauveguarde des modifs
			try {
				frLangConfig.save(frLangFile);
			} catch (IOException e1) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + frLangConfig.getName().toString() + "\" !"); }
		}
		frLangConfig = YamlConfiguration.loadConfiguration(frLangFile);
		//

		// EN.yml
		enLangFile = new File(path, "EN.yml");
		if(!enLangFile.exists())
		{
			try {
				enLangFile.createNewFile();
			} catch (IOException e) {
				System.out.println("Erreur lors de la creation du fichier de configuration \"" + enLangConfig.getName().toString() + "\" !");
				return;
			}

			enLangConfig = YamlConfiguration.loadConfiguration(enLangFile);
			enLangConfig.set(msgPath + "sender_type_error", "&cYou must be a player to execute this command !");
			enLangConfig.set(msgPath + "player_no_enough_permission", "&cYou do not have permission to execute this command !");
			enLangConfig.set(msgPath + "rail_success_change", "&2You have replaced the rails with %blocktype% !");
			enLangConfig.set(msgPath + "material_type_error", "&cThis bloc does not exist !");
			enLangConfig.set(msgPath + "rail_error", "&cThe target block is not a rail !");
			enLangConfig.set(msgPath + "rail_success_break", "&2You have broken a hidden rail !");
			enLangConfig.set(msgPath + "rail_success_unhide", "&2You have displayed the rails !");
			enLangConfig.set(msgPath + "water_protection_status_success_change", "&2You have %status% the under-water protection in %world%");
			enLangConfig.set(msgPath + "invalid_worldname", "&cThis world name is invalid !");
			enLangConfig.set(msgPath + "plugin_success_reloaded", "&2Plugin successfully reloaded");
			enLangConfig.set(msgPath + "water_protection_status_already", "&cThe underwater protection in %world% is already %status%");
			enLangConfig.set(msgPath + "no_backup", "&cNo backup available !");
			enLangConfig.set(msgPath + "return_backup_success", "&2successfully restored backup !");
			enLangConfig.set(msgPath + "worldedit_not_installed", "&cWorldedit plugin is not installed on this server !");
			enLangConfig.set(msgPath + "worldedit_no_selection", "&cYou must first select region with Worldedit !");
			enLangConfig.set(msgPath + "display_hidden_blocks", "&2You have %hide% the hidden blocks for you !");

			// Sauveguarde des modifs
			try {
				enLangConfig.save(enLangFile);
			} catch (IOException e1) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + enLangConfig.getName().toString() + "\" !"); }
		} else {
			enLangConfig = YamlConfiguration.loadConfiguration(enLangFile);
			checkConfContains(enLangConfig, "sender_type_error", "&cYou must be a player to execute this command !");
			checkConfContains(enLangConfig, "player_no_enough_permission", "&cYou do not have permission to execute this command !");
			checkConfContains(enLangConfig, "rail_success_change", "&2You have replaced the rails with %blocktype% !");
			checkConfContains(enLangConfig, "material_type_error", "&cThis bloc does not exist !");
			checkConfContains(enLangConfig, "rail_error", "&cThe target block is not a rail !");
			checkConfContains(enLangConfig, "rail_success_break", "&2You have broken a hidden rail !");
			checkConfContains(enLangConfig, "rail_success_unhide", "&2You have displayed the rails !");
			checkConfContains(enLangConfig, "water_protection_status_success_change", "&2You have %status% the under-water protection in %world%");
			checkConfContains(enLangConfig, "invalid_worldname", "&cThis world name is invalid !");
			checkConfContains(enLangConfig, "plugin_success_reloaded", "&2Plugin successfully reloaded");
			checkConfContains(enLangConfig, "water_protection_status_already", "&cThe underwater protection in %world% is already %status%");
			checkConfContains(enLangConfig, "no_backup", "&cNo backup available !");
			checkConfContains(enLangConfig, "return_backup_success", "&2successfully restored backup !");
			checkConfContains(enLangConfig, "worldedit_not_installed", "&cWorldedit plugin is not installed on this server !");
			checkConfContains(enLangConfig, "worldedit_no_selection", "&cYou must first select region with Worldedit !");
			checkConfContains(enLangConfig, "display_hidden_blocks", "&2You have %hide% the hidden blocks for you !");

			// Sauveguarde des modifs
			try {
				enLangConfig.save(enLangFile);
			} catch (IOException e1) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + enLangConfig.getName().toString() + "\" !"); }
		}
		enLangConfig = YamlConfiguration.loadConfiguration(enLangFile);
		//


		if(!language.equals("FR") && !language.equals("EN"))
		{
			// Load custom language
			File langFile = new File(path + "/" + getConfig().getString("language") + ".yml");
			InputStream inputStream = null;
			BufferedReader reader = null;

			try {
				inputStream = new FileInputStream(langFile);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

			// Chargement du fichier config lang
			try {
				reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			try {
				try {
					getConfig().load(reader);
				} catch (InvalidConfigurationException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			langConfig = YamlConfiguration.loadConfiguration(langFile);
			//
		}

		// Definition du language
		if(language.equalsIgnoreCase("FR")) {
			langConfig = frLangConfig;
		} else if(language.equalsIgnoreCase("EN")) {
			langConfig = enLangConfig;
		}
		//

		// Definition des messages a leur langue
		MessagesManager.loadAllMessages();
		//
	}

	/*
	 * Verification de chaque ligne a cause du changement de version
	 */
	private void checkConfContains(FileConfiguration langConfig, String path, String value) {
		String finPath = msgPath + path;
		if(!langConfig.contains(finPath))
			langConfig.set(finPath, value);
	}


	public void saveConfigs()
	{
		try {
			hiddenRailsConfig.save(hiddenRailsFile);
		} catch (IOException e) {
			System.out.println("Erreur lors de la sauveguarde du fichier de configuration \"" + hiddenRailsConfig.getName().toString() + "\"");
		}

		try {
			frLangConfig.save(frLangFile);
		} catch (IOException e) {
			System.out.println("Erreur lors de la sauveguarde du fichier de configuration \"" + frLangConfig.getName().toString() + "\"");
		}

		try {
			enLangConfig.save(enLangFile);
		} catch (IOException e) {
			System.out.println("Erreur lors de la sauveguarde du fichier de configuration \"" + enLangConfig.getName().toString() + "\"");
		}
	}
	//


	@Override
	public void onEnable()
	{
		instance = this;
		language = getConfig().getString("language");

		// Init Enabled and Disabled blocksType to hide
		HideRailsManager.initHideBlocksType();

		setupConfig();
		saveConfigs();

		registerEvents();
		registerCommands();

		// Chargement de tous les rails masques
		HideRailsManager.loadHideRails();

		// Verification MAJ
		try {
			new SpigotUpdater(this, 55158, true);
		} catch (IOException e) {
			System.err.println("Resource not found ! Ressource non trouvee !");
		}
		//
	}

	private void registerEvents()
	{
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new RailBreakEvent(), this);
		pm.registerEvents(new RedstoneInWaterEvents(), this);
		pm.registerEvents(new JoinEvent(), this);
	}

	private void registerCommands()
	{
		getCommand("hiderails").setExecutor(new HideRailsCommand());
		getCommand("hiderails").setTabCompleter(new TabComplete());
	}


	/*
	 * GetWordedit if plugin is installed
	 */
	public WorldEditPlugin getWorldEdit() {
		Plugin we = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		if(we instanceof WorldEditPlugin) return (WorldEditPlugin) we;
		else return null;
	}
}
