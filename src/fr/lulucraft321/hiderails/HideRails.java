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

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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


	//
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

	public void setupConfig()
	{
		if(!config.exists())
		{
			saveDefaultConfig();
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
			frLangConfig.set("messages.sender_type_error", "&cSeul un joueur peut executer cette commande !");
			frLangConfig.set("messages.player_no_enough_permission", "&cVous n'avez pas la permission d'éxécuter cette commande !");
			frLangConfig.set("messages.rail_success_change", "&2Vous avez remplacé les rails par %blocktype%");
			frLangConfig.set("messages.material_type_error", "&cCe bloc n'existe pas !");
			frLangConfig.set("messages.rail_error", "&cLe bloc que vous visez n'est pas un rail !");
			frLangConfig.set("messages.rail_success_break", "&2Vous avez cassé un rail masqué !");
			frLangConfig.set("messages.rail_success_unhide", "&2Vous avez fait re apparaitre les rails !");
			frLangConfig.set("messages.water_protection_status_success_change", "&2Vous avez %status% la protection de la redstone sous l'eau pour le monde %world%");
			frLangConfig.set("messages.invalid_worldname", "&cMonde invalide");
			frLangConfig.set("messages.plugin_success_reloaded", "&2Plugin rechargé avec succès !");
			try {
				frLangConfig.save(frLangFile);
			} catch (IOException e1) {
				System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + frLangConfig.getName().toString() + "\" !");
			}
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
			enLangConfig.set("messages.sender_type_error", "&cYou must be a player to execute this command !");
			enLangConfig.set("messages.player_no_enough_permission", "&cYou do not have permission to execute this command !");
			enLangConfig.set("messages.rail_success_change", "&2You have replaced the rails with %blocktype% !");
			enLangConfig.set("messages.material_type_error", "&cThis bloc does not exist !");
			enLangConfig.set("messages.rail_error", "&cThe target block is not a rail !");
			enLangConfig.set("messages.rail_success_break", "&2You have broken a hidden rail !");
			enLangConfig.set("messages.rail_success_unhide", "&2You have displayed the rails !");
			enLangConfig.set("messages.water_protection_status_success_change", "&2You have %status% the under-water protection in %world%");
			enLangConfig.set("messages.invalid_worldname", "&cThis world name is invalid");
			enLangConfig.set("messages.plugin_success_reloaded", "&2Plugin successfully reloaded");
			try {
				enLangConfig.save(enLangFile);
			} catch (IOException e1) {
				System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + enLangConfig.getName().toString() + "\" !");
			}
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

	public FileConfiguration getHiddenRailsConfig() { return hiddenRailsConfig; }
	public FileConfiguration getFrLangConfig() { return frLangConfig; }
	public FileConfiguration getEnLangConfig() { return enLangConfig; }
	public FileConfiguration getLangConfig() { return langConfig; }
	//


	@Override
	public void onEnable()
	{
		instance = this;
		language = getConfig().getString("language");

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
}
