package fr.lulucraft321.hiderails.external.metrics;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.zip.GZIPOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitTask;

public class MetricsLite
{
	@SuppressWarnings("unused")
	private static final int REVISION = 7;
	@SuppressWarnings("unused")
	private static final String BASE_URL = "http://report.mcstats.org";
	@SuppressWarnings("unused")
	private static final String REPORT_URL = "/plugin/%s";
	@SuppressWarnings("unused")
	private static final int PING_INTERVAL = 15;
	private final Plugin plugin;
	private final YamlConfiguration configuration;
	private final File configurationFile;
	private final String guid;
	private final boolean debug;
	private final Object optOutLock = new Object();
	private volatile BukkitTask task = null;

	public MetricsLite(Plugin plugin)
			throws IOException
	{
		if (plugin == null) {
			throw new IllegalArgumentException("Plugin cannot be null");
		}
		this.plugin = plugin;

		this.configurationFile = getConfigFile();
		this.configuration = YamlConfiguration.loadConfiguration(this.configurationFile);

		this.configuration.addDefault("opt-out", Boolean.valueOf(false));
		this.configuration.addDefault("guid", UUID.randomUUID().toString());
		this.configuration.addDefault("debug", Boolean.valueOf(false));
		if (this.configuration.get("guid", null) == null)
		{
			this.configuration.options().header("http://mcstats.org").copyDefaults(true);
			this.configuration.save(this.configurationFile);
		}
		this.guid = this.configuration.getString("guid");
		this.debug = this.configuration.getBoolean("debug", false);
	}

	public boolean start()
	{
		synchronized (this.optOutLock)
		{
			if (isOptOut()) {
				return false;
			}
			if (this.task != null) {
				return true;
			}
			this.task = this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(this.plugin, new Runnable()
			{
				private boolean firstPost = true;

				public void run()
				{
					try
					{
						synchronized (MetricsLite.this.optOutLock)
						{
							if ((MetricsLite.this.isOptOut()) && (MetricsLite.this.task != null))
							{
								MetricsLite.this.task.cancel();
								MetricsLite.this.task = null;
							}
						}
						MetricsLite.this.postPlugin(!this.firstPost);

						this.firstPost = false;
					}
					catch (IOException e)
					{
						if (MetricsLite.this.debug) {
							Bukkit.getLogger().log(Level.INFO, "[Metrics] " + e.getMessage());
						}
					}
				}
			}, 0L, 18000L);

			return true;
		}
	}

	public boolean isOptOut()
	{
		synchronized (this.optOutLock)
		{
			try
			{
				this.configuration.load(getConfigFile());
			}
			catch (IOException ex)
			{
				if (this.debug) {
					Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.getMessage());
				}
				return true;
			}
			catch (InvalidConfigurationException ex)
			{
				if (this.debug) {
					Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.getMessage());
				}
				return true;
			}
			return this.configuration.getBoolean("opt-out", false);
		}
	}

	public void enable()
			throws IOException
	{
		synchronized (this.optOutLock)
		{
			if (isOptOut())
			{
				this.configuration.set("opt-out", Boolean.valueOf(false));
				this.configuration.save(this.configurationFile);
			}
			if (this.task == null) {
				start();
			}
		}
	}

	public void disable() throws IOException {
		synchronized (this.optOutLock) {
			if (!(isOptOut())) {
				this.configuration.set("opt-out", Boolean.valueOf(true));
				this.configuration.save(this.configurationFile);
			}

			if (this.task != null) {
				this.task.cancel();
				this.task = null;
			}
		}
	}

	public File getConfigFile() {
		File pluginsFolder = this.plugin.getDataFolder().getParentFile();

		return new File(new File(pluginsFolder, "PluginMetrics"), "config.yml");
	}

	private int getOnlinePlayers() {
		try {
			Method onlinePlayerMethod = Server.class.getMethod("getOnlinePlayers", new Class[0]);
			if (onlinePlayerMethod.getReturnType().equals(Collection.class)) {
				return ((ByteArrayOutputStream) onlinePlayerMethod.invoke(Bukkit.getServer(), new Object[0])).size();
			}
			return ((Player[]) (Player[]) onlinePlayerMethod.invoke(Bukkit.getServer(), new Object[0])).length;
		} catch (Exception ex) {
			if (this.debug) {
				Bukkit.getLogger().log(Level.INFO,
						new StringBuilder().append("[Metrics] ").append(ex.getMessage()).toString());
			}
		}

		return 0;
	}

	private void postPlugin(boolean isPing) throws IOException {
		PluginDescriptionFile description = this.plugin.getDescription();
		String pluginName = description.getName();
		boolean onlineMode = Bukkit.getServer().getOnlineMode();
		String pluginVersion = description.getVersion();
		String serverVersion = Bukkit.getVersion();
		int playersOnline = getOnlinePlayers();

		StringBuilder json = new StringBuilder(1024);
		json.append('{');

		appendJSONPair(json, "guid", this.guid);
		appendJSONPair(json, "plugin_version", pluginVersion);
		appendJSONPair(json, "server_version", serverVersion);
		appendJSONPair(json, "players_online", Integer.toString(playersOnline));

		String osname = System.getProperty("os.name");
		String osarch = System.getProperty("os.arch");
		String osversion = System.getProperty("os.version");
		String java_version = System.getProperty("java.version");
		int coreCount = Runtime.getRuntime().availableProcessors();

		if (osarch.equals("amd64")) {
			osarch = "x86_64";
		}

		appendJSONPair(json, "osname", osname);
		appendJSONPair(json, "osarch", osarch);
		appendJSONPair(json, "osversion", osversion);
		appendJSONPair(json, "cores", Integer.toString(coreCount));
		appendJSONPair(json, "auth_mode", (onlineMode) ? "1" : "0");
		appendJSONPair(json, "java_version", java_version);

		if (isPing) {
			appendJSONPair(json, "ping", "1");
		}

		json.append('}');

		URL url = new URL(new StringBuilder().append("http://report.mcstats.org")
				.append(String.format("/plugin/%s", new Object[] { urlEncode(pluginName) })).toString());
		URLConnection connection;
		if (isMineshafterPresent())
			connection = url.openConnection(Proxy.NO_PROXY);
		else {
			connection = url.openConnection();
		}

		byte[] uncompressed = json.toString().getBytes();
		byte[] compressed = gzip(json.toString());

		connection.addRequestProperty("User-Agent", "MCStats/7");
		connection.addRequestProperty("Content-Type", "application/json");
		connection.addRequestProperty("Content-Encoding", "gzip");
		connection.addRequestProperty("Content-Length", Integer.toString(compressed.length));
		connection.addRequestProperty("Accept", "application/json");
		connection.addRequestProperty("Connection", "close");

		connection.setDoOutput(true);

		if (this.debug) {
			System.out.println(new StringBuilder().append("[Metrics] Prepared request for ").append(pluginName)
					.append(" uncompressed=").append(uncompressed.length).append(" compressed=")
					.append(compressed.length).toString());
		}

		OutputStream os = connection.getOutputStream();
		os.write(compressed);
		os.flush();

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String response = reader.readLine();

		os.close();
		reader.close();

		if ((response == null) || (response.startsWith("ERR")) || (response.startsWith("7"))) {
			if (response == null)
				response = "null";
			else if (response.startsWith("7")) {
				response = response.substring((response.startsWith("7,")) ? 2 : 1);
			}

			throw new IOException(response);
		}
	}

	public static byte[] gzip(String input) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream gzos = null;
		try {
			gzos = new GZIPOutputStream(baos);
			gzos.write(input.getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (gzos != null)
				try {
					gzos.close();
				} catch (IOException localIOException3) {
				}
		}
		return baos.toByteArray();
	}

	private boolean isMineshafterPresent()
	{
		try
		{
			Class.forName("mineshafter.MineServer");
			return true;
		}
		catch (Exception e) {}
		return false;
	}

	private static void appendJSONPair(StringBuilder json, String key, String value)
			throws UnsupportedEncodingException
	{
		boolean isValueNumeric = false;
		try
		{
			if ((value.equals("0")) || (!value.endsWith("0")))
			{
				Double.parseDouble(value);
				isValueNumeric = true;
			}
		}
		catch (NumberFormatException e)
		{
			isValueNumeric = false;
		}
		if (json.charAt(json.length() - 1) != '{') {
			json.append(',');
		}
		json.append(escapeJSON(key));
		json.append(':');
		if (isValueNumeric) {
			json.append(value);
		} else {
			json.append(escapeJSON(value));
		}
	}

	private static String escapeJSON(String text)
	{
		StringBuilder builder = new StringBuilder();

		builder.append('"');
		for (int index = 0; index < text.length(); index++)
		{
			char chr = text.charAt(index);
			switch (chr)
			{
				case '"': 
				case '\\': 
					builder.append('\\');
					builder.append(chr);
					break;
				case '\b': 
					builder.append("\\b");
					break;
				case '\t': 
					builder.append("\\t");
					break;
				case '\n': 
					builder.append("\\n");
					break;
				case '\r': 
					builder.append("\\r");
					break;
				default: 
					if (chr < ' ')
					{
						String t = "000" + Integer.toHexString(chr);
						builder.append("\\u" + t.substring(t.length() - 4));
					}
					else
					{
						builder.append(chr);
					}
					break;
			}
		}
		builder.append('"');

		return builder.toString();
	}

	private static String urlEncode(String text)
			throws UnsupportedEncodingException
	{
		return URLEncoder.encode(text, "UTF-8");
	}
}