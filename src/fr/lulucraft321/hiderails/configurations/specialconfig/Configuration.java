/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.configurations.specialconfig;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.lulucraft321.hiderails.reflection.BukkitNMS;

public class Configuration
{
	private ConfigurationsHandle configHandler;

	protected final static String headPrefix = "_head_";
	protected final static String headerPrefix = "_header_";
	protected final static String commPrefix = "_com_";
	protected final static String voidPrefix = "_null_";

	private int headNbr = 1;
	private int headerNbr = 1;
	private int comNbr = 1;
	private int voidNbr = 1;

	private File configFile;
	private FileConfiguration config;


	public Configuration(File f, List<String> header) {
		this.configHandler = new ConfigurationsHandle();

		this.configFile = f;
		this.config = YamlConfiguration.loadConfiguration(f);

		if (header != null)
			this.setHeader(header);
	}


	/**
	 * Set the header to configFile
	 * 
	 * @param header
	 */
	public void setHeader(List<String> header) {
		int i = 0;
		for (String line : header) {
			// First line
			if (i == (header.size()-header.size())) {
				this.set(serializeHeadPrefix(), line);
			}
			this.set(serializeHeaderPrefix(), line);
			i++;
			// End line
			if (i == header.size()) {
				this.set(serializeHeadPrefix(), line);
				this.set(serializeVoidPrefix(), Configuration.voidPrefix);
			}
		}
	}

	/**
	 * Serialize first line to header with prefix and header comment number
	 * 
	 * @return serialized
	 */
	private String serializeHeadPrefix() {
		return Configuration.headPrefix + this.headNbr++;
	}

	/**
	 * Serialize text line to header with prefix and header comment number
	 * 
	 * @return serialized
	 */
	private String serializeHeaderPrefix() {
		return Configuration.headerPrefix + this.headerNbr++;
	}


	/**
	 * Set path to value
	 * 
	 * @param path
	 * @param value
	 */
	public Configuration set(String path, Object value) {
		this.config.set(path, value);

		return this;
	}

	/**
	 * Get object (memorySection) with path
	 * 
	 * @param path
	 * @return Object
	 */
	public Object get(String path) {
		return this.config.get(path);
	}

	/**
	 * Get boolean with path
	 * 
	 * @param path
	 * @return boolean
	 */
	public boolean getBoolean(String path) {
		return this.config.getBoolean(path);
	}

	/**
	 * Check is config contains path
	 * 
	 * @param path
	 * @return boolean
	 */
	public boolean contains(String path) {
		return this.config.contains(path);
	}

	/**
	 * 
	 * 
	 * @param path
	 * @return boolean
	 */
	public boolean containsComment(String comment) {
		return readFileContent().contains(comment);
	}

	/**
	 * Get String value with path
	 * 
	 * @param path
	 * @return String
	 */
	public String getString(String path) {
		return this.config.getString(path);
	}

	/**
	 * Get Integer value with path
	 * 
	 * @param path
	 * @return int
	 */
	public int getInt(String path) {
		return this.config.getInt(path);
	}

	/**
	 * Edit value with path (without break config)
	 * 
	 * @param path
	 * @param value
	 */
	public void edit(String path, Object value) {
		String fileContext = readFileContent();
		fileContext = fileContext.replace(path + ": " + this.get(path), path + ": " + value);

		writeFileContent(fileContext);
	}

	/**
	 * Edit sub memory section with path
	 * And set path value
	 * 
	 * @param path
	 * @param value
	 */
	public void editDefault(String path, Object value) {
		String fileContext = readFileContent();
		String rm = path.replace(".", ":\n  ") + ": ";
		fileContext = fileContext.replace(rm + this.get(path), rm + value);

		writeFileContent(fileContext);
	}

	/**
	 * Add sub memorySection to memorySection
	 * 
	 * @param path
	 * @param value
	 */
	public void addDefault(String path, Object value) {
		String fileContext = readFileContent();
		String exist_path = path.split("\\.")[0];
		String exist_path_2 = path.split("\\.")[1];
		fileContext = fileContext.replace(exist_path + ":", exist_path + ":\n  " + exist_path_2 + ": " + value);

		writeFileContent(fileContext);
	}

	private String readFileContent() {
		try {
			return BukkitNMS.readFileContent(this.configFile);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void writeFileContent(CharSequence fileContext) {
		try {
			BukkitNMS.writeFileContent(this.configFile, fileContext);

			this.reloadConfig();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Add line break
	 * 
	 * @return Configuration class instance
	 */
	public Configuration addEmptyLine() {
		String path = serializeVoidPrefix();

		if (!this.config.contains(path)) {
			this.set(path, Configuration.voidPrefix);
		}

		return this;
	}

	/**
	 * Serialize line break
	 * 
	 * @return serialized
	 */
	private String serializeVoidPrefix() {
		return Configuration.voidPrefix + this.voidNbr++;
	}


	/**
	 * Set and comment path
	 * 
	 * @param path
	 * @param value
	 * @param comment
	 * @return Configuration class instance
	 */
	public Configuration addCommentedPath(String path, Object value, String comment) {
		if (!this.config.contains(path)) {
			this.set(serializeCommentPrefix(), comment);
		}
		this.set(path, value);

		return this;
	}

	/**
	 * Set and multi comment path
	 * 
	 * @param path
	 * @param value
	 * @param comments
	 * @return Configuration class instance
	 */
	public Configuration addCommentedPath(String path, Object value, String[] comments) {
		if (!this.config.contains(path)) {
			for (String comment : comments) {
				// Max size per line in YAML file
				if (comment.length() >= 74) {
					String[] parts = comment.split(" ");

					StringBuilder part_1 = new StringBuilder();
					for (int i = 0; i < parts.length/2; i++) {
						part_1.append(parts[i] + " ");
					}

					StringBuilder part_2 = new StringBuilder();
					for (int i = parts.length/2; i < parts.length; i++) {
						part_2.append(parts[i] + " ");
					}
					// trim -> delete simple cote
					this.set(serializeCommentPrefix(), (part_1.toString()).trim());
					this.set(serializeCommentPrefix(), (part_2.toString()).trim());
				} else {
					this.set(serializeCommentPrefix(), comment);
				}
			}
		}
		this.set(path, value);

		return this;
	}

	/**
	 * Serialize comment with prefix and comment number
	 * 
	 * @return serialized comment
	 */
	private String serializeCommentPrefix() {
		return Configuration.commPrefix + this.comNbr++;
	}


	/**
	 * Set and comment path if config is already built
	 * 
	 * @param path
	 * @param value
	 * @param comment
	 */
	public void setCommentedPath(String path, Object value, String comment) {
		String fileContext = readFileContent();
		fileContext = fileContext + ("\n\n" + "# " + comment) + ("\n" + path + ": " + value);
		writeFileContent(fileContext);
	}

	/**
	 * Set and multi comment path if config is already built
	 * 
	 * @param path
	 * @param value
	 * @param comment
	 */
	public void setCommentedPath(String path, Object value, String[] comments) {
		String fileContext = readFileContent();
		fileContext = fileContext + "\n";

		for (String comment : comments) {
			// Max size per line in YAML file
			if (comment.length() >= 74) {
				String[] parts = comment.split(" ");

				StringBuilder part_1 = new StringBuilder();
				for (int i = 0; i < parts.length/2; i++) {
					part_1.append(parts[i] + " ");
				}

				StringBuilder part_2 = new StringBuilder();
				for (int i = parts.length/2; i < parts.length; i++) {
					part_2.append(parts[i] + " ");
				}
				// trim -> delete simple cote
				fileContext = fileContext + ("\n# " + (part_1.toString()).trim()) + "\n# " + (part_2.toString()).trim();
			} else {
				fileContext = fileContext + ("\n# " + comment.trim());
			}
		}

		// Set path and his value
		fileContext = fileContext + ("\n" + path + ": " + value);
		writeFileContent(fileContext);
	}


	/**
	 * Set path and his value without comment
	 * 
	 * @param path
	 * @param value
	 */
	public void setPath(String path, Object value) {
		String fileContext = readFileContent();
		fileContext = fileContext + ("\n\n" + path + ": " + value);
		writeFileContent(fileContext);
	}



	/**
	 * Save commented config
	 */
	private void saveConfig() {
		final String configString = this.config.saveToString();
		this.configHandler.saveConfig(configString, this.configFile);
	}



	/**
	 * Reload configurationFile (without refresh)
	 */
	public void reloadConfig() {
		this.config = YamlConfiguration.loadConfiguration(this.configFile);
	}


	/**
	 * Make and save config
	 */
	public void build() {
		saveConfig();
	}


	/**
	 * Get configuration
	 * 
	 * @return Configuration instance
	 */
	public Configuration getConfig() {
		return this;
	}

}
