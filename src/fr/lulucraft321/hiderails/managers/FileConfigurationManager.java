package fr.lulucraft321.hiderails.managers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.configurations.configs.English;
import fr.lulucraft321.hiderails.configurations.configs.French;
import fr.lulucraft321.hiderails.configurations.configs.Hongrois;
import fr.lulucraft321.hiderails.configurations.configs.Italian;
import fr.lulucraft321.hiderails.configurations.configs.Spanish;
import fr.lulucraft321.hiderails.configurations.specialconfig.Configuration;
import fr.lulucraft321.hiderails.configurations.specialconfig.ConfigurationsHandle;
import fr.lulucraft321.hiderails.utils.checkers.Checker;

public class FileConfigurationManager
{
	public static String language;
	public static String getLanguage() { return language; }

	public static String path = "plugins/HideRails/Languages";
	private static File langFolder = new File(path);
	private static File conf = new File("plugins/HideRails", "config.yml");

	public static final String msgPath = "messages.";
	public static int viewDistance = 100;

	private static File hiddenRailsFile;
	protected static File frLangFile;
	protected static File enLangFile;
	protected static File itLangFile;
	protected static File huLangFile;
	protected static File esLangFile;
	protected static File langFile;

	private static Configuration config = null;
	public static Configuration getConfig() { return config; }

	protected static FileConfiguration hiddenRailsConfig = null;

	protected static FileConfiguration frLangConfig;
	protected static FileConfiguration enLangConfig;
	protected static FileConfiguration itLangConfig;
	protected static FileConfiguration huLangConfig;
	protected static FileConfiguration esLangConfig;
	private static FileConfiguration langConfig;

	public static FileConfiguration getHiddenRailsConfig() { return hiddenRailsConfig; }
	public static FileConfiguration getLangConfig() { return langConfig; }


	/**
	 * Make all configs and check lines
	 */
	public static void setupConfigs()
	{
		try {
			final File f = new File(HideRails.getInstance().getDataFolder(), "config.yml");
			// Create config file
			if (!f.exists()) {
				FileConfigurationManager.config = new ConfigurationsHandle().createConfig("config.yml",
						Arrays.asList(
								"                                                         ",
								"Plugin HideRails by lulucraft321",
								"                                                         "), null);
				FileConfigurationManager.config.addCommentedPath("language", "EN", new String[] {
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
				.addCommentedPath("kickSpamBlock", true, new String[] { "Enable or disable player spam block kick", "If you disable it, the players can spam sending packets and that can cause bugs !" })
				.addEmptyLine()
				.addCommentedPath("maxSpamNumber", 4, new String[] { "Max block spam click number before kick player", "If number is high, bugs may increase !" })
				.addEmptyLine()
				.addCommentedPath("redstoneWaterProtection._all_", true, new String[] {
						"Enable or disable water-protection for each world",
						"_all_ is waterprotection for all worlds if no world is defined"
				})
				.build();
			}
			// Load config file
			else {
				FileConfigurationManager.config = new ConfigurationsHandle().createConfig("config.yml", null, null);
				FileConfigurationManager.config.reloadConfig();

				// If load failed
				if (FileConfigurationManager.config == null) {
					FileConfigurationManager.addCorruptConfig();
				}
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}


		// Create lang folder if isn't exist
		if (!langFolder.exists())
			langFolder.mkdirs();


		// Definission de la langue
		FileConfigurationManager.language = FileConfigurationManager.config.getString("language");
		if (language == null) {
			addCorruptConfig();
		}


		/*
		 * Check if lines exists
		 */
		if (!FileConfigurationManager.config.containsComment("Plugin HideRails by lulucraft321")) {
			addCorruptConfig();
		}

		// Distance entre les blocks masques et le joueur (distance d'envoie des packets)
		if (!FileConfigurationManager.config.contains("viewDistance")) {
			FileConfigurationManager.config.setCommentedPath("viewDistance", 120, "HiddenBlocks view distance");
		} else {
			String view = String.valueOf(FileConfigurationManager.config.get("viewDistance"));
			if (Checker.isInt(view)) {
				FileConfigurationManager.viewDistance = Integer.parseInt(view);
			}
		}

		// Activation du message new update lorsqu'un admin rejoint le serveur
		if (!FileConfigurationManager.config.contains("adminsUpdateMessage")) {
			FileConfigurationManager.config.setCommentedPath("adminsUpdateMessage", true, "Plugin update message sent when admin player join the server");
		}

		if (!FileConfigurationManager.config.contains("hideRails")) {
			FileConfigurationManager.config.setCommentedPath("hideRails", true, "Enable rails hidding");
		}
		if (!FileConfigurationManager.config.contains("hideIronBars")) {
			FileConfigurationManager.config.setCommentedPath("hideIronBars", false, "Enable Iron bars hidding (ex: TC-HangRail hanging train)");
		}
		if (!FileConfigurationManager.config.contains("hideCommandBlock")) {
			FileConfigurationManager.config.setCommentedPath("hideCommandBlock", false, "Enable Command blocks hidding (include 3 types of command blocks)");
		}
		if (!FileConfigurationManager.config.contains("hideRedstone")) {
			FileConfigurationManager.config.setCommentedPath("hideRedstone", false, "Enable Redstone hidding (include levers, ...)");
		}
		if (!FileConfigurationManager.config.contains("hideSigns")) {
			FileConfigurationManager.config.setCommentedPath("hideSigns", false, "Enable Signs hidding (ex: TrainCart sign)");
		}

		// Particules sur les blocs masques si le joueur fait /hr display
		if (!FileConfigurationManager.config.contains("hiddingBlocksParticles")) {
			FileConfigurationManager.config.setCommentedPath("hiddingBlocksParticles", true, "Particle who spawn in hidden-blocks if player execute /hr display command");
		}

		// Players spam block clicks
		if (!FileConfigurationManager.config.contains("kickSpamBlock")) {
			FileConfigurationManager.config.setCommentedPath("kickSpamBlock", true, new String[] { "Enable or disable player spam block kick", "If you disable it, the players can spam sending packets and that can cause bugs !" });
		}
		if (!FileConfigurationManager.config.contains("maxSpamNumber")) {
			FileConfigurationManager.config.setCommentedPath("maxSpamNumber", 4, new String[] { "Max block spam click number before kick player", "If number is high, bugs may increase !" });
		}


		/*
		 * Load HiddenRails file
		 */
		// HiddenRails.yml
		hiddenRailsFile = new File("plugins/HideRails", "HiddenRails.yml");
		if (!hiddenRailsFile.exists())
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


		/*
		 * Init all config files
		 */
		new English().setupConfig();
		new French().setupConfig();
		new Hongrois().setupConfig();
		new Italian().setupConfig();
		new Spanish().setupConfig();


		/*
		 * Load custom language file
		 */
		if (!language.equals("FR") && !language.equals("EN") && !language.equals("IT") && !language.equals("HU") && !language.equals("ES"))
		{
			// Load custom language
			File langFile = new File(path + "/" + FileConfigurationManager.config.getString("language") + ".yml");

			// Chargement du fichier config lang
			try {
				FileConfigurationManager.config = new ConfigurationsHandle().createConfig("/Languages/" + FileConfigurationManager.config.getString("language") + ".yml", Arrays.asList("Custom language", "You can propose your language", "at https://www.spigotmc.org/resources/55158/ "), null).getConfig();
			} catch (IOException e) {
				e.printStackTrace();
			}

			langConfig = YamlConfiguration.loadConfiguration(langFile);
			//
		}

		// Definition du language
		if (language.equalsIgnoreCase("FR")) {
			langConfig = frLangConfig;
		} else if(language.equalsIgnoreCase("EN")) {
			langConfig = enLangConfig;
		} else if(language.equalsIgnoreCase("IT")) {
			langConfig = itLangConfig;
		} else if(language.equalsIgnoreCase("HU")) {
			langConfig = huLangConfig;
		} else if(language.equalsIgnoreCase("ES")) {
			langConfig = esLangConfig;
		}
		//


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
	}


	/**
	 * Create backup config to corrupted config.yml (missing line)
	 */
	// Si il manque des lignes de config dans le fichier config.yml
	// -> Clonage et renommage de l'ancien par la date et l'heure
	// + recreation d'un fichier config avec les parametres par defaut
	private static void addCorruptConfig() {
		File confFile = new File(conf.getAbsolutePath());
		File confBack = new File("plugins/HideRails/config_" + new SimpleDateFormat("yyyy-M-dd_hh.mm.ss").format(new Date()).toString() + ".yml");

		if (confFile.exists()) {
			confFile.renameTo(confBack);
		}
		setupConfigs();
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

		try {
			esLangConfig.save(esLangFile);
		} catch (IOException e) {
			System.out.println("Erreur lors de la sauveguarde du fichier de configuration \"" + esLangConfig.getName().toString() + "\"");
		}
	}


	/*
	 * Verification de chaque ligne a cause du changement de version
	 */
	protected static void checkConfContains(FileConfiguration langConfig, String path, String value) {
		String finPath = msgPath + path;
		if(!langConfig.contains(finPath))
			langConfig.set(finPath, value);
	}

}
