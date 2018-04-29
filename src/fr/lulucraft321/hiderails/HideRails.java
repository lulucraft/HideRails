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
import fr.lulucraft321.hiderails.external.metrics.Metrics;
import fr.lulucraft321.hiderails.external.updater.SpigotUpdater;
import fr.lulucraft321.hiderails.manager.HideRailsManager;
import fr.lulucraft321.hiderails.manager.MessagesManager;

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
	private File itLangFile;

	private FileConfiguration hiddenRailsConfig;
	private FileConfiguration frLangConfig;
	private FileConfiguration enLangConfig;
	private FileConfiguration itLangConfig;
	private FileConfiguration langConfig;

	public FileConfiguration getHiddenRailsConfig() { return hiddenRailsConfig; }
	public FileConfiguration getFrLangConfig() { return frLangConfig; }
	public FileConfiguration getEnLangConfig() { return enLangConfig; }
	public FileConfiguration getItLangConfig() { return itLangConfig; }
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

		// Particules sur les blocs masques si le joueur fait /hr display
		if(!configConf.contains("hiddingBlocksParticles")) {
			getConfig().set("hiddingBlocksParticles", true);
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
			frLangConfig.options().header("Language by lulucraft321").copyDefaults(true);
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
			frLangConfig.set(msgPath + "invalid_player", "&cLe joueur n'est pas connecté/n'existe pas !");

			// Sauveguarde des modifs
			try {
				frLangConfig.save(frLangFile);
			} catch (IOException e1) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + frLangConfig.getName().toString() + "\" !"); }
		} else {
			frLangConfig = YamlConfiguration.loadConfiguration(frLangFile);
			frLangConfig.options().header("Language by lulucraft321").copyDefaults(true);
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
			checkConfContains(frLangConfig, "invalid_player", "&cLe joueur n'est pas connecté/n'existe pas !");

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
			enLangConfig.options().header("Translation by lulucraft321").copyDefaults(true);
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
			enLangConfig.set(msgPath + "invalid_player", "&cThe player cannot be found !");

			// Sauveguarde des modifs
			try {
				enLangConfig.save(enLangFile);
			} catch (IOException e1) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + enLangConfig.getName().toString() + "\" !"); }
		} else {
			enLangConfig = YamlConfiguration.loadConfiguration(enLangFile);
			enLangConfig.options().header("Translation by lulucraft321").copyDefaults(true);
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
			checkConfContains(enLangConfig, "invalid_player", "&cThe player cannot be found !");

			// Sauveguarde des modifs
			try {
				enLangConfig.save(enLangFile);
			} catch (IOException e1) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + enLangConfig.getName().toString() + "\" !"); }
		}
		enLangConfig = YamlConfiguration.loadConfiguration(enLangFile);
		//


		// IT.yml
		itLangFile = new File(path, "IT.yml");
		if(!itLangFile.exists())
		{
			try {
				itLangFile.createNewFile();
			} catch (IOException e) {
				System.out.println("Erreur lors de la creation du fichier de configuration \"" + itLangConfig.getName().toString() + "\" !");
				return;
			}

			itLangConfig = YamlConfiguration.loadConfiguration(itLangFile);
			itLangConfig.options().header("Grazie a Sitieno14 per la traduzione in italiano").copyDefaults(true);
			itLangConfig.set(msgPath + "sender_type_error", "&cDevi essere un giocatore per poter eseguire questo comando !");
			itLangConfig.set(msgPath + "player_no_enough_permission", "&cNon hai il permesso per eseguire questo comando !");
			itLangConfig.set(msgPath + "rail_success_change", "&2Hai rimpiazzato i binari con %blocktype% !");
			itLangConfig.set(msgPath + "material_type_error", "&cQuesto blocco non esiste !");
			itLangConfig.set(msgPath + "rail_error", "&cIl blocco da te bersagliato non è un binario !");
			itLangConfig.set(msgPath + "rail_success_break", "&2Hai rotto un binario nascosto !");
			itLangConfig.set(msgPath + "rail_success_unhide", "&2Hai mostrato i binari !");
			itLangConfig.set(msgPath + "water_protection_status_success_change", "&2Tu hai %status% la protezione sott'acqua nel mondo %world%");
			itLangConfig.set(msgPath + "invalid_worldname", "&cIl nome di questo mondo non è valido");
			itLangConfig.set(msgPath + "plugin_success_reloaded", "&2Plugin ricaricato con successo !");
			itLangConfig.set(msgPath + "water_protection_status_already", "&cLa protezione sott'acqua nel mondo %world% è già %status%");
			itLangConfig.set(msgPath + "no_backup", "&cNessun backup disponibile !");
			itLangConfig.set(msgPath + "return_backup_success", "&2Backup ripristinato con successo !");
			itLangConfig.set(msgPath + "worldedit_not_installed", "&cIl Plugin WorldEdit non è installato in questo server !");
			itLangConfig.set(msgPath + "worldedit_no_selection", "&cDevi prima selezionare una regione con WorldEdit !");
			itLangConfig.set(msgPath + "display_hidden_blocks", "&2Hai %hide% i blocchi nascosti per te !");
			itLangConfig.set(msgPath + "invalid_player", "&cIl giocatore non può essere trovato");

			// Sauveguarde des modifs
			try {
				itLangConfig.save(itLangFile);
			} catch (IOException e1) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + itLangConfig.getName().toString() + "\" !"); }
		} else {
			itLangConfig = YamlConfiguration.loadConfiguration(itLangFile);
			itLangConfig.options().header("Grazie a Sitieno14 per la traduzione in italiano").copyDefaults(true);
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
			checkConfContains(itLangConfig, "worldedit_not_installed", "&cIl Plugin WorldEdit non è installato in questo server !");
			checkConfContains(itLangConfig, "worldedit_no_selection", "&cDevi prima selezionare una regione con WorldEdit !");
			checkConfContains(itLangConfig, "display_hidden_blocks", "&2Hai %hide% i blocchi nascosti per te !");
			checkConfContains(itLangConfig, "invalid_player", "&cIl giocatore non può essere trovato");

			// Sauveguarde des modifs
			try {
				itLangConfig.save(itLangFile);
			} catch (IOException e1) { System.err.println("Erreur lors de la sauveguarde du fichier de configuration \"" + itLangConfig.getName().toString() + "\" !"); }
		}
		itLangConfig = YamlConfiguration.loadConfiguration(itLangFile);
		//


		if(!language.equals("FR") && !language.equals("EN") && !language.equals("IT"))
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
		} else if(language.equalsIgnoreCase("IT")) {
			langConfig = itLangConfig;
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

		try {
			itLangConfig.save(itLangFile);
		} catch (IOException e) {
			System.out.println("Erreur lors de la sauveguarde du fichier de configuration \"" + itLangConfig.getName().toString() + "\"");
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
			getLogger().warning("[Updater] Resource not found ! Ressource non trouvee !");
		}

		// Metrics stats
		Metrics metrics = new Metrics(this);
		metrics.addCustomChart(new Metrics.SimplePie("used_language", () -> getLanguage() ));
		metrics.addCustomChart(new Metrics.SingleLineChart("online_players", () -> Bukkit.getServer().getOnlinePlayers().size() ));
		metrics.addCustomChart(new Metrics.SingleLineChart("offline_players", () -> Bukkit.getServer().getOfflinePlayers().length ));
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
