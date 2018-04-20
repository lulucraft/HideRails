/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.utils;

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
import fr.lulucraft321.hiderails.enums.BlockReplacementType;
import fr.lulucraft321.hiderails.manager.MessagesManager;

public class Checker
{
	public static boolean isInt(String check)
	{
		try {
			Integer.parseInt(check);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean isMaterial(String mat)
	{
		if(mat.contains(":")) {
			String[] split = mat.split(":");
			return Material.getMaterial(split[0].toUpperCase()) != null;
		} else {
			return Material.getMaterial(mat.toUpperCase()) != null;
		}
	}

	public static boolean isRail(Block matCheck)
	{
		return (matCheck.getType() == Material.RAILS || matCheck.getType() == Material.LADDER || matCheck.getType() == Material.DETECTOR_RAIL || matCheck.getType() == Material.POWERED_RAIL);
	}

	public static boolean isIronBar(Block matCheck)
	{
		return matCheck.getType() == Material.IRON_FENCE;
	}

	public static BlockReplacementType getBlockReplacementType(Player player, Block targetBlock)
	{
		BlockReplacementType blockType;

		if(Checker.isRail(targetBlock))
		{
			blockType = BlockReplacementType.RAILS;
		}
		else if(Checker.isIronBar(targetBlock))
		{
			blockType = BlockReplacementType.IRON_BARS;
		}
		// If Material is not valid
		else {
			MessagesManager.sendPluginMessage(player, Messages.RAIL_ERROR);
			return null;
		}

		return blockType;
	}


	@SuppressWarnings("deprecation")
	public static MaterialData getMatData(Player p, String input)
	{
		Material mat = null;
		byte data = 0;

		// Material with data
		if(input.contains(":"))
		{
			String[] split = input.split(":");

			// Material with ID
			if(isInt(split[0]) && isInt(split[1]))
			{
				mat = Material.getMaterial(Integer.parseInt(split[0]));
				data = getMatData(split[1]);
			}
			else
			{
				// Material with NAME
				if(isMaterial(input)) {
					mat = Material.getMaterial(split[0].toUpperCase());
					data = getMatData(split[1]);
				}
			}
		}
		else
		{
			// Material with ID
			if(isInt(input))
			{
				mat = Material.getMaterial(Integer.parseInt(input));
				data = 0;
			}
			else
			{
				// Material with NAME
				if(isMaterial(input)) {
					mat = Material.getMaterial(input.toUpperCase());
					data = 0;
				}
			}
		}

		if(mat == null) {
			MessagesManager.sendPluginMessage(p, Messages.MATERIAL_TYPE_ERROR);
		} else {
			MessagesManager.sendRailChangeMessage(p, Messages.SUCCESS_CHANGE_RAIL, mat.name());
		}

		return new MaterialData(mat, data);
	}

	private static byte getMatData(String input)
	{
		byte data = 0;

		try {
			data = Byte.valueOf(input).byteValue();
		} catch (Exception e){
			data = 0;
		}

		return data;
	}

	public static String getBoolean(String input)
	{
		if(input.contains("true") || input.contains("allow")) {
			return "true";
		}

		if(input.contains("false") || input.contains("deny")) {
			return "false";
		}
		return null;
	}


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
		WorldEditPlugin we = Checker.getWorldedit(player);
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

	public static List<Location> getAllValidRails(Selection selection)
	{
		List<Location> railsLocsTemp = Checker.getAllBlocksLocationInWeSelection(selection);
		List<Location> railsLocs = new ArrayList<>();

		for (Location blockLoc : railsLocsTemp) {
			Block bl = Bukkit.getWorld(blockLoc.getWorld().getName()).getBlockAt(blockLoc);

			if (Checker.isRail(bl) || Checker.isIronBar(bl)) {
				railsLocs.add(blockLoc);
			}
		}

		return railsLocs;
	}
}
