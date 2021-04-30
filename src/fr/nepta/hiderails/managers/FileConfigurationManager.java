/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.managers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.nepta.hiderails.HideRails;
import fr.nepta.hiderails.configurations.commentedconfig.Configuration;
import fr.nepta.hiderails.configurations.commentedconfig.ConfigurationsHandle;
import fr.nepta.hiderails.configurations.configs.AbstractLangConfig;
import fr.nepta.hiderails.configurations.configs.CustomLangConfig;
import fr.nepta.hiderails.configurations.configs.EnglishLangConfig;
import fr.nepta.hiderails.configurations.configs.FrenchLangConfig;
import fr.nepta.hiderails.configurations.configs.GermanLangConfig;
import fr.nepta.hiderails.configurations.configs.HungarianLangConfig;
import fr.nepta.hiderails.configurations.configs.ItalianLangConfig;
import fr.nepta.hiderails.configurations.configs.SpanishLangConfig;
import fr.nepta.hiderails.utils.JavaChecker;

public class FileConfigurationManager
{
	public static final String HIDERAILS_PATH = "plugins/HideRails";
	public static final String LANG_PATH = HIDERAILS_PATH  + "/Languages";
	public static final String MSG_PATH = "messages.";

	private static final File LANG_FOLDER = new File(LANG_PATH);
	private static final File CONF = new File(HIDERAILS_PATH, "config.yml");

	public static int viewDistance = 100;

	private static File hiddenRailsFile;
	private static File playersDataFile;

	private static String language = null;
	public static String getLanguage() { return language; }

	private static Configuration config = null;
	public static Configuration getConfig() { return config; }

	private static FileConfiguration hiddenRailsConfig = null;
	private static FileConfiguration playersDataConfig = null;
	private static AbstractLangConfig langConfig = null;

	public static FileConfiguration getHiddenRailsConfig() { return hiddenRailsConfig; }
	public static FileConfiguration getPlayersDataConfig() { return playersDataConfig; }
	public static AbstractLangConfig getLangConfig() { return langConfig; }


	/**
	 * Make all configs and check lines
	 * 
	 * @throws IOException 
	 */
	public static void setupConfigs() throws IOException
	{
		try {
			final File f = new File(HideRails.getInstance().getDataFolder(), "config.yml");
			// Create config file
			if (!f.exists()) {
				config = new ConfigurationsHandle().createConfig("config.yml",
						Arrays.asList(
								"                                                         ",
								"Plugin HideRails by Nepta_",
								"                                                         "), null);
				config.addCommentedPath("language", "EN", new String[] {
						"Language of all messages in plugin",
						"You can create your custom language (copy and paste existing language file and remake it)"
				})
				.addEmptyLine()
				.addCommentedPath("hideRails", true, "Enable block hidding")
				.addCommentedPath("hideIronBars", false, "Enable Iron bars hidding (ex: TC-HangRail hanging train)")
				.addCommentedPath("hideCommandBlock", false, "Enable Command blocks hidding (include 3 types of command blocks)")
				.addCommentedPath("hideRedstone", false, "Enable Redstone hidding (include levers, ...)")
				.addCommentedPath("hideSigns", false, "Enable Signs hidding (ex: TrainCart sign)")
				.addEmptyLine()
				.addCommentedPath("hiddingBlocksParticles", true, "Particle who spawn in hidden-blocks if player execute /hr display command")
				.addEmptyLine()
				.addCommentedPath("viewDistance", 120, "HiddenBlocks view distance")
				.addEmptyLine()
				.addCommentedPath("adminsUpdateMessage", true, "Plugin update message sent when admin player join the server")
				.addEmptyLine()
				//				.addCommentedPath("kickSpamBlock", true, new String[] { "Enable or disable player spam block kick", "If you disable it, the players can spam sending packets and that can cause bugs !" })
				//				.addEmptyLine()
				//				.addCommentedPath("maxSpamNumber", 4, new String[] { "Max block spam click number before kick player", "If number is high, bugs may increase !" })
				//				.addEmptyLine()
				.addCommentedPath("redstoneWaterProtection._all_", true, new String[] {
						"Enable or disable water-protection for each world",
						"_all_ is waterprotection for all worlds if no world is defined"
				})
				.build();
			}
			// Load config file
			else {
				config = new ConfigurationsHandle().createConfig("config.yml", null, null);
				config.reloadConfig();

				// If load failed
				if (config == null) {
					addCorruptConfig();
				}
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}


		// Create lang folder if isn't exist
		if (!LANG_FOLDER.exists())
			LANG_FOLDER.mkdirs();


		// Definission de la langue
		language = config.getString("language");
		if (language == null) {
			addCorruptConfig();
		}


		/*
		 * Check if lines exists
		 */
		if (!config.containsComment("Plugin HideRails by lulucraft321") && !config.containsComment("Plugin HideRails by Nepta_")) {
			addCorruptConfig();
		}

		// Distance entre les blocks masques et le joueur (distance d'envoie des packets)
		if (!config.contains("viewDistance")) {
			config.setCommentedPath("viewDistance", 120, "HiddenBlocks view distance");
		} else {
			String view = String.valueOf(config.get("viewDistance"));
			if (JavaChecker.isInt(view)) {
				viewDistance = Integer.parseInt(view);
			}
		}

		// Activation du message new update lorsqu'un admin rejoint le serveur
		if (!config.contains("adminsUpdateMessage")) {
			config.setCommentedPath("adminsUpdateMessage", true, "Plugin update message sent when admin player join the server");
		}

		if (!config.contains("hideRails")) {
			config.setCommentedPath("hideRails", true, "Enable rails hidding");
		}
		if (!config.contains("hideIronBars")) {
			config.setCommentedPath("hideIronBars", false, "Enable Iron bars hidding (ex: TC-HangRail hanging train)");
		}
		if (!config.contains("hideCommandBlock")) {
			config.setCommentedPath("hideCommandBlock", false, "Enable Command blocks hidding (include 3 types of command blocks)");
		}
		if (!config.contains("hideRedstone")) {
			config.setCommentedPath("hideRedstone", false, "Enable Redstone hidding (include levers, ...)");
		}
		if (!config.contains("hideSigns")) {
			config.setCommentedPath("hideSigns", false, "Enable Signs hidding (ex: TrainCart sign)");
		}

		// Particules sur les blocs masques si le joueur fait /hr display
		if (!config.contains("hiddingBlocksParticles")) {
			config.setCommentedPath("hiddingBlocksParticles", true, "Particle who spawn in hidden-blocks if player execute /hr display command");
		}

		//		// Players spam block clicks
		//		if (!config.contains("kickSpamBlock")) {
		//			config.setCommentedPath("kickSpamBlock", true, new String[] { "Enable or disable player spam block kick", "If you disable it, the players can spam sending packets and that can cause bugs !" });
		//		}
		//		if (!config.contains("maxSpamNumber")) {
		//			config.setCommentedPath("maxSpamNumber", 4, new String[] { "Max block spam click number before kick player", "If number is high, bugs may increase !" });
		//		}


		/*
		 * Load HiddenRails file
		 */
		// HiddenRails.yml
		hiddenRailsFile = new File(HIDERAILS_PATH, "HiddenRails.yml");
		if (!hiddenRailsFile.exists())
		{
			try {
				hiddenRailsFile.createNewFile();
			} catch (IOException e) {
				MessagesManager.LOGGER.warning("Error when creating the configuration file \"" + hiddenRailsConfig.getName().toString() + "\" !");
				return;
			}
		}
		hiddenRailsConfig = YamlConfiguration.loadConfiguration(hiddenRailsFile);
		//


		/*
		 * Load PlayersData file
		 */
		// PlayersData.yml
		playersDataFile = new File(HIDERAILS_PATH, "PlayersData.yml");
		if (!playersDataFile.exists())
		{
			try {
				playersDataFile.createNewFile();
			} catch (IOException e) {
				MessagesManager.LOGGER.warning("Error when creating the configuration file \"" + playersDataConfig.getName().toString() + "\" !");
				return;
			}
		}
		playersDataConfig = YamlConfiguration.loadConfiguration(playersDataFile);
		//


//		/*
//		* Init all config files
//		*/
		// Create config and load all messages
		switch (language) {
		case "FR":
			langConfig = new FrenchLangConfig();
			break;
		case "EN":
			langConfig = new EnglishLangConfig();
			break;
		case "IT":
			langConfig = new ItalianLangConfig();
			break;
		case "HU":
			langConfig = new HungarianLangConfig();
			break;
		case "ES":
			langConfig = new SpanishLangConfig();
			break;
		case "DE":
			langConfig = new GermanLangConfig();
			break;

			/*
			 * Load custom language file
			 */
		default:
			// Load custom language
			File langFile = new File(LANG_PATH + "/" + config.getString("language") + ".yml");

			// Chargement du fichier config lang
			try {
				config = new ConfigurationsHandle().createConfig("/Languages/" + config.getString("language") + ".yml", null, null).getConfig();//Arrays.asList("Custom language", "You can propose your language", "at https://www.spigotmc.org/resources/55158/")
			} catch (IOException e) {
				e.printStackTrace();
			}

			langConfig = new CustomLangConfig(langFile);
			break;
		}
		langConfig.setupConfig();// Check if all lines exist
		langConfig.save();// Save file
		langConfig.loadConfig();


		/*
		 * Load all messages according to the language
		 */
		// Definition des messages a leur langue
		MessagesManager.loadAllMessages();


		/*
		 * Init Enabled and Disabled blocksType to hide
		 */
		// Initialisation des blocks a masquer
		HideRailsManager.initHideBlocksType();


		/*
		 * Load all blacklisted players selection message
		 */
		// Initialisation des joueurs ne voulant pas recevoir le message de selection (pos1, pos2)
		HideRailsManager.initPlayersData();
	}


	/**
	 * Create backup config of corrupted config.yml (missing line)
	 * 
	 * <p>If any one line is missing in config.yml :
	 * <br>- Clone and rename old file by the current date and hour
	 * <br>- and create new file with the default parameters
	 * </p>
	 * 
	 * @throws IOException 
	 */
	private static void addCorruptConfig() throws IOException {
		File confFile = new File(CONF.getAbsolutePath());
		File confBack = new File(HIDERAILS_PATH + "/config_" + new SimpleDateFormat("yyyy-M-dd_hh.mm.ss").format(new Date()).toString() + ".yml");

		if (confFile.exists()) {
			confFile.renameTo(confBack);
		}
		setupConfigs();
	}



	/*
	 * Save all custom configuration files
	 */
	public static void saveConfigs()
	{
		try {
			hiddenRailsConfig.save(hiddenRailsFile);
		} catch (IOException e) {
			MessagesManager.LOGGER.warning("Error when saving the configuration file \"" + hiddenRailsConfig.getName() + "\"");
		}

		try {
			playersDataConfig.save(playersDataFile);
		} catch (IOException e) {
			MessagesManager.LOGGER.warning("Error when saving the configuration file \"" + playersDataConfig.getName() + "\"");
		}

		try {
			langConfig.save();
		} catch (IOException e) {
			MessagesManager.LOGGER.warning("Error when saving the configuration file \"" + langConfig.getFile().getName() + "\"");
		}
	}


	/*
	 * Verification de chaque ligne a cause du changement de version
	 */
	public static void checkConfContains(FileConfiguration langConfig, String path, String value) {
		String finPath = MSG_PATH + path;
		if(!langConfig.contains(finPath))
			langConfig.set(finPath, value);
	}

}
