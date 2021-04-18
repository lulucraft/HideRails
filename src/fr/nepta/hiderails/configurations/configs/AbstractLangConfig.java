/**
 * Copyright
 * Code under MIT licence
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.configurations.configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public abstract class AbstractLangConfig {

	private File langFile;
	private FileConfiguration langConfig;

	public AbstractLangConfig(File langFile) {
		this.langFile = langFile;
	}

	public FileConfiguration getFileConfiguration() {
		return this.langConfig;
	}

	/**
	 * Get language file
	 * 
	 * @return lang file
	 */
	public File getFile() {
		return langFile;
	}

	/**
	 * Get configuration file
	 * 
	 * @return FileConfiguration
	 */
	public FileConfiguration getConfig() {
		return langConfig;
	}

	/**
	 * Load configuration file and return it
	 * 
	 * @return FileConfiguration
	 */
	public FileConfiguration loadConfig() {
		this.langConfig = YamlConfiguration.loadConfiguration(langFile);
		return this.langConfig;
	}

	/**
	 * Save language file
	 * 
	 */
	public void save() {
		try {
			this.langConfig.save(langFile);
		} catch (IOException e) {
			System.out.println("Erreur lors de la sauveguarde du fichier de configuration \"" + langConfig.getName().toString() + "\"");
		}
	}


	public abstract void setupConfig();
}
