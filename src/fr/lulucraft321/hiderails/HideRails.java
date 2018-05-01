/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails;

import java.io.IOException;

import org.bukkit.Bukkit;
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
import fr.lulucraft321.hiderails.managers.FileConfigurationManager;
import fr.lulucraft321.hiderails.managers.HideRailsManager;

public class HideRails extends JavaPlugin
{
	private static HideRails instance;
	public static HideRails getInstance() { return instance; }

	@Override
	public void onEnable()
	{
		instance = this;

		// Init Enabled and Disabled blocksType to hide
		HideRailsManager.initHideBlocksType();

		FileConfigurationManager.setupConfig();
		FileConfigurationManager.saveConfigs();

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
		metrics.addCustomChart(new Metrics.SimplePie("used_language", () -> FileConfigurationManager.getLanguage() ));
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
