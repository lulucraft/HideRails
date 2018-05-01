/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.managers;

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

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.managers.config.English;
import fr.lulucraft321.hiderails.managers.config.French;
import fr.lulucraft321.hiderails.managers.config.Hongrois;
import fr.lulucraft321.hiderails.managers.config.Italian;

public class FileConfigurationManager
{
	public static String language;
	public static String getLanguage() { return language; }

	public static String path = "plugins/HideRails/Languages";
	private static File langFolder = new File(path);
	private static File config = new File("plugins/HideRails", "config.yml");

	private static File hiddenRailsFile;
	protected static File frLangFile;
	protected static File enLangFile;
	protected static File itLangFile;
	protected static File huLangFile;

	protected static FileConfiguration hiddenRailsConfig = null;

	protected static FileConfiguration frLangConfig;
	protected static FileConfiguration enLangConfig;
	protected static FileConfiguration itLangConfig;
	protected static FileConfiguration huLangConfig;

	private static FileConfiguration langConfig;

	public static FileConfiguration getHiddenRailsConfig() { return hiddenRailsConfig; }
	public static FileConfiguration getLangConfig() { return langConfig; }

	public final static String msgPath = "messages.";

	public static void setupConfig()
	{
		FileConfigurationManager.language = HideRails.getInstance().getConfig().getString("language");

		if(!config.exists())
		{
			HideRails.getInstance().saveDefaultConfig();
		}

		FileConfiguration configConf = YamlConfiguration.loadConfiguration(config);
		if(!configConf.contains("hideRails")) {
			HideRails.getInstance().getConfig().set("hideRails", true);
			HideRails.getInstance().saveConfig();
		}
		if(!configConf.contains("hideIronBars")) {
			HideRails.getInstance().getConfig().set("hideIronBars", false);
			HideRails.getInstance().saveConfig();
		}
		if(!configConf.contains("hideCommandBlock")) {
			HideRails.getInstance().getConfig().set("hideCommandBlock", false);
			HideRails.getInstance().saveConfig();
		}
		if(!configConf.contains("hideRedstone")) {
			HideRails.getInstance().getConfig().set("hideRedstone", false);
			HideRails.getInstance().saveConfig();
		}
		if(!configConf.contains("hideSigns")) {
			HideRails.getInstance().getConfig().set("hideSigns", false);
			HideRails.getInstance().saveConfig();
		}

		// Particules sur les blocs masques si le joueur fait /hr display
		if(!configConf.contains("hiddingBlocksParticles")) {
			HideRails.getInstance().getConfig().set("hiddingBlocksParticles", true);
			HideRails.getInstance().saveConfig();
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

		// Init all config files
		//new English();
		English.setupENConfig();
		//new French();
		French.setupFRConfig();
		//new Hongrois();
		Hongrois.setupHUConfig();
		//new Italian();
		Italian.setupITConfig();
		//

		if(!language.equals("FR") && !language.equals("EN") && !language.equals("IT") && !language.equals("HU"))
		{
			// Load custom language
			File langFile = new File(path + "/" + HideRails.getInstance().getConfig().getString("language") + ".yml");
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
					HideRails.getInstance().getConfig().load(reader);
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
		} else if(language.equalsIgnoreCase("HU")) {
			langConfig = huLangConfig;
		}
		//

		// Definition des messages a leur langue
		MessagesManager.loadAllMessages();
	}

	/*
	 * Verification de chaque ligne a cause du changement de version
	 */
	protected static void checkConfContains(FileConfiguration langConfig, String path, String value) {
		String finPath = msgPath + path;
		if(!langConfig.contains(finPath))
			langConfig.set(finPath, value);
	}


	/*
	 * Save all custom config files
	 */
	public static void saveConfigs()
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

		try {
			huLangConfig.save(huLangFile);
		} catch (IOException e) {
			System.out.println("Erreur lors de la sauveguarde du fichier de configuration \"" + huLangConfig.getName().toString() + "\"");
		}
	}
	//
}
