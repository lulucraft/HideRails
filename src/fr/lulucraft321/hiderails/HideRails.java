/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.lulucraft321.hiderails.commands.CommandsHandle;
import fr.lulucraft321.hiderails.commands.TabComplete;
import fr.lulucraft321.hiderails.external.metrics.MetricsLite;
import fr.lulucraft321.hiderails.external.updater.SpigotUpdater;
import fr.lulucraft321.hiderails.listeners.BlockClickEvent;
import fr.lulucraft321.hiderails.listeners.BreakBlockEvent;
import fr.lulucraft321.hiderails.listeners.JoinEvent;
import fr.lulucraft321.hiderails.listeners.RedstoneInWaterEvents;
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

		// Init and setup all custom configs
		FileConfigurationManager.setupConfigs();
		FileConfigurationManager.saveConfigs();

		registerListeners();
		registerCommands();

		// Chargement de tous les rails masques
		HideRailsManager.loadHideRails();

		// Verification MAJ
		try {
			new SpigotUpdater(this, 55158, true);
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Updater] Resource not found ! Ressource non trouvee !");
		}

		// Metrics stats
		try {
			new MetricsLite(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void registerListeners()
	{
		new BreakBlockEvent();
		new RedstoneInWaterEvents();
		new JoinEvent();
		new BlockClickEvent();
	}

	private void registerCommands()
	{
		getCommand("hiderails").setExecutor(new CommandsHandle());
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
