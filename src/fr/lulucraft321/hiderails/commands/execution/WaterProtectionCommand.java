/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.commands.execution;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.enums.Messages;
import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.managers.MessagesManager;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractCommand;
import fr.lulucraft321.hiderails.utils.checkers.JavaChecker;

public class WaterProtectionCommand extends AbstractCommand
{
	public WaterProtectionCommand() {
		super("waterprotection", "hiderails.waterprotection", 3, Arrays.asList("waterprotect", "protectionwater", "protectwater"));
	}

	/*
	 * Change waterprotection status in world
	 */
	@Override
	public void execute(CommandSender sender, Command cmd, String[] args) {
		final Player p = (Player) sender;
		List<World> world = Bukkit.getWorlds();
		String worldInput = String.valueOf(args[1]);

		// Change redstone protection status for one world
		if (world.contains(Bukkit.getServer().getWorld(worldInput))) {
			// args[2] == boolean value
			String bInput = JavaChecker.getBoolean(args[2].toLowerCase().toString());

			if (bInput == null) {
				MessagesManager.sendHelpPluginMessage(p);
			} else {
				HideRailsManager.setWaterProtection(p, worldInput, Boolean.parseBoolean(bInput));
			}
		}
		// Change redstone protection status for all worlds
		else if (worldInput.equalsIgnoreCase("all") || worldInput.equalsIgnoreCase("_all_")) {
			String bInput = JavaChecker.getBoolean(args[2].toLowerCase().toString());

			HideRailsManager.setWaterProtection(p, (worldInput.equalsIgnoreCase("all") ? "_all_" : worldInput), Boolean.parseBoolean(bInput));
		}
		// Error of world input
		else {
			MessagesManager.sendPluginMessage(p, Messages.INVALID_WORLDNAME);
		}
	}
}
