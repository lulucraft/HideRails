/**
 * Copyright
 * Code under MIT license
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
	 * @throws IOException 
	 */
	public void save() throws IOException {
		this.langConfig.save(langFile);
	}


	/**
	 * Define config sections and default values
	 */
	public abstract void setupConfig();
}
