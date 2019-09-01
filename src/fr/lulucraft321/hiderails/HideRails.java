/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import fr.lulucraft321.hiderails.commands.CommandsHandle;
import fr.lulucraft321.hiderails.commands.TabComplete;
import fr.lulucraft321.hiderails.enums.Version;
import fr.lulucraft321.hiderails.external.metrics.Metrics;
import fr.lulucraft321.hiderails.external.updater.SpigotUpdater;
import fr.lulucraft321.hiderails.listeners.BlockClickListener;
import fr.lulucraft321.hiderails.listeners.BlockPhysicListener;
import fr.lulucraft321.hiderails.listeners.BreakBlockListener;
import fr.lulucraft321.hiderails.listeners.JoinListener;
import fr.lulucraft321.hiderails.listeners.PosCommandListener;
import fr.lulucraft321.hiderails.listeners.RedstoneInWaterListeners;
import fr.lulucraft321.hiderails.managers.FileConfigurationManager;
import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.reflection.BukkitNMS;

public class HideRails extends JavaPlugin
{
	private static HideRails instance;
	public static HideRails getInstance() { return instance; }

	public static Version version;

	@Override
	public void onEnable()
	{
		instance = this;

		// Init this.version and all class
		new BukkitNMS();

		// Init and setup all custom configs
		FileConfigurationManager.setupConfigs();
		FileConfigurationManager.saveConfigs();

		registerListeners();
		registerCommands();

		// Chargement de tous les rails masques
		HideRailsManager.loadHideRails();

		// Verification MAJ
		try {
			new SpigotUpdater(this, 55158, false);
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Updater] Resource not found ! Ressource non trouvee !");
		}

		// Metrics stats
		new Metrics(this);
	}

	private void registerListeners()
	{
		new BreakBlockListener();
		new RedstoneInWaterListeners();
		new JoinListener();
		new BlockClickListener();
		new BlockPhysicListener();
		new PosCommandListener();
	}

	private void registerCommands()
	{
		getCommand("hiderails").setExecutor(new CommandsHandle());
		getCommand("hiderails").setTabCompleter(new TabComplete());
	}
}
