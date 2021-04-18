/**
 *  
 * MIT License
 * 
 * Copyright (c) 2021 Nepta_
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @author Nepta_
 */
package fr.nepta.hiderails;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import fr.nepta.hiderails.commands.CommandsHandle;
import fr.nepta.hiderails.commands.TabComplete;
import fr.nepta.hiderails.commands.execution.DisplayCommand;
import fr.nepta.hiderails.commands.execution.DisplayOtherCommand;
import fr.nepta.hiderails.commands.execution.HideCommand;
import fr.nepta.hiderails.commands.execution.HideSelectionBlockTypeCommand;
import fr.nepta.hiderails.commands.execution.HideSelectionCommand;
import fr.nepta.hiderails.commands.execution.HideSingleCommand;
import fr.nepta.hiderails.commands.execution.ReloadCommand;
import fr.nepta.hiderails.commands.execution.RestoreBackupCommand;
import fr.nepta.hiderails.commands.execution.SelectionMessageCommand;
import fr.nepta.hiderails.commands.execution.UnHideCommand;
import fr.nepta.hiderails.commands.execution.UnHideSelectionBlockTypeCommand;
import fr.nepta.hiderails.commands.execution.UnHideSelectionCommand;
import fr.nepta.hiderails.commands.execution.UnHideSingleCommand;
import fr.nepta.hiderails.commands.execution.WaterProtectionCommand;
import fr.nepta.hiderails.enums.Version;
import fr.nepta.hiderails.external.metrics.Metrics;
import fr.nepta.hiderails.external.updater.SpigotUpdater;
import fr.nepta.hiderails.listeners.BlockClickListener;
import fr.nepta.hiderails.listeners.BlockPhysicListener;
import fr.nepta.hiderails.listeners.BreakBlockListener;
import fr.nepta.hiderails.listeners.JoinListener;
import fr.nepta.hiderails.listeners.PosCommandListener;
import fr.nepta.hiderails.listeners.RedstoneInWaterListeners;
import fr.nepta.hiderails.managers.FileConfigurationManager;
import fr.nepta.hiderails.managers.HideRailsManager;
import fr.nepta.hiderails.packets.BukkitNMS;

public class HideRails extends JavaPlugin
{
	private static HideRails instance;
	public static HideRails getInstance() { return instance; }

	public static Version version;

	@Override
	public void onEnable()
	{
		instance = this;

		// Init packets
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

		new DisplayCommand();
		new DisplayOtherCommand();
		new HideCommand();
		new HideSelectionBlockTypeCommand();
		new HideSelectionCommand();
		new HideSingleCommand();
		new ReloadCommand();
		new RestoreBackupCommand();
		new SelectionMessageCommand();
		new UnHideCommand();
		new UnHideSelectionCommand();
		new UnHideSelectionBlockTypeCommand();
		new UnHideSelectionBlockTypeCommand();
		new UnHideSingleCommand();
		new WaterProtectionCommand();
	}
}
