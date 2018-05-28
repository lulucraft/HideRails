/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.utils.checkers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.enums.Messages;
import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.managers.MessagesManager;

public class WorldeditChecker
{
	/*
	 * Check and get Worldedit if plugin is installed
	 */
	private static WorldEditPlugin getWorldedit(Player player)
	{
		WorldEditPlugin we = HideRails.getInstance().getWorldEdit();
		if(we == null) {
			MessagesManager.sendPluginMessage(player, Messages.WORLDEDIT_NOT_INSTALLED);
			return null;
		}

		return we;
	}

	public static Selection getWorldeditSelection(Player player)
	{
		WorldEditPlugin we = WorldeditChecker.getWorldedit(player);
		if(we != null) {
			Selection sel = we.getSelection(player);
			if(sel instanceof CuboidSelection) { // Eviter de confondre avec un cuboid (region protegee)
				return sel;
			}
		}
		return null;
	}

	private static List<Location> getAllBlocksLocationInWeSelection(Selection selection)
	{
		Location min = selection.getMinimumPoint(); // Minimum point of Worldedit Selection
		Location max = selection.getMaximumPoint(); // Maximum point of Worldedit Selection

		List<Location> railsLocsTemp = new ArrayList<>(); // Temporary storage of all blocks Location in Worldedit Selection

		for (int x = min.getBlockX(); x <= max.getBlockX(); x++)
		{
			for (int y = min.getBlockY(); y <= max.getBlockY(); y++)
			{
				for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++)
				{
					railsLocsTemp.add(new Location(min.getWorld(), x, y, z));
				}
			}
		}

		return railsLocsTemp;
	}

	public static List<Location> getAllValidRails(Selection selection, List<Material> types)
	{
		List<Location> railsLocsTemp = WorldeditChecker.getAllBlocksLocationInWeSelection(selection);
		List<Location> railsLocs = new ArrayList<>();

		boolean rails = false;
		boolean ironBars = false;
		boolean commandBlocks = false;
		boolean redstone = false;
		boolean signs = false;
		if ((types == null) || (types.isEmpty()))
		{
			rails = true;
			ironBars = true;
			commandBlocks = true;
			redstone = true;
			signs = true;
		} else {
			for (Material type : types)
			{
				if (BlocksChecker.isRail(type)) {
					rails = true;
				}
				if (BlocksChecker.isIronBar(type)) {
					ironBars = true;
				}
				if (BlocksChecker.isCommandBlock(type)) {
					commandBlocks = true;
				}
				if (BlocksChecker.isRedstone(type)) {
					redstone = true;
				}
				if (BlocksChecker.isSign(type)) {
					signs = true;
				}
			}
		}

		for (Location blockLoc : railsLocsTemp) {
			Block bl = Bukkit.getWorld(blockLoc.getWorld().getName()).getBlockAt(blockLoc);

			if (BlocksChecker.isRail(bl)) {
				if (HideRailsManager.hr) {
					if (rails)
						railsLocs.add(blockLoc);
				}
			}

			if (BlocksChecker.isIronBar(bl)) {
				if (HideRailsManager.hb) {
					if (ironBars)
						railsLocs.add(blockLoc);
				}
			}

			if (BlocksChecker.isCommandBlock(bl)) {
				if (HideRailsManager.hc) {
					if (commandBlocks)
						railsLocs.add(blockLoc);
				}
			}

			if (BlocksChecker.isRedstone(bl)) {
				if (HideRailsManager.hd) {
					if (redstone)
						railsLocs.add(blockLoc);
				}
			}

			if (BlocksChecker.isSign(bl)) {
				if (HideRailsManager.hs) {
					if (signs)
						railsLocs.add(blockLoc);
				}
			}
		}

		return railsLocs;
	}
}
