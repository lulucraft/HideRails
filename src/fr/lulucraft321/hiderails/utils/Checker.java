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
import fr.lulucraft321.hiderails.enums.Messages;
import fr.lulucraft321.hiderails.manager.HideRailsManager;
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
		return (matCheck.getType() == Material.RAILS || matCheck.getType() == Material.LADDER || matCheck.getType() == Material.ACTIVATOR_RAIL || matCheck.getType() == Material.DETECTOR_RAIL || matCheck.getType() == Material.POWERED_RAIL);
	}

	public static boolean isIronBar(Block matCheck)
	{
		return matCheck.getType() == Material.IRON_FENCE;
	}

	public static boolean isCommandBlock(Block matCheck)
	{
		return (matCheck.getType() == Material.COMMAND || matCheck.getType() == Material.COMMAND_CHAIN || matCheck.getType() == Material.COMMAND_REPEATING);
	}

	public static boolean isRedstone(Block matCheck)
	{
		return (matCheck.getType() == Material.REDSTONE_WIRE || matCheck.getType() == Material.REDSTONE || matCheck.getType() == Material.REDSTONE_BLOCK
				|| matCheck.getType() == Material.REDSTONE_COMPARATOR || matCheck.getType() == Material.REDSTONE_COMPARATOR_OFF || matCheck.getType() == Material.REDSTONE_COMPARATOR_ON
				|| matCheck.getType() == Material.REDSTONE_TORCH_OFF || matCheck.getType() == Material.REDSTONE_TORCH_ON
				|| matCheck.getType() == Material.DIODE || matCheck.getType() == Material.DIODE_BLOCK_OFF || matCheck.getType() == Material.DIODE_BLOCK_ON);
	}

	public static boolean isSign(Block matCheck)
	{
		return (matCheck.getType() == Material.SIGN || matCheck.getType() == Material.SIGN_POST || matCheck.getType() == Material.WALL_SIGN);
	}


	public static boolean checkBlockIfActive(Block newCheckBlock)
	{
		if (Checker.isRail(newCheckBlock)) {
			if (HideRailsManager.hr) {
				return true;
			}
		}

		else if (Checker.isIronBar(newCheckBlock)) {
			if (HideRailsManager.hb) {
				return true;
			}
		}

		else if (Checker.isCommandBlock(newCheckBlock)) {
			if (HideRailsManager.hc) {
				return true;
			}
		}

		else if (Checker.isRedstone(newCheckBlock)) {
			if (HideRailsManager.hd) {
				return true;
			}
		}

		else if (Checker.isSign(newCheckBlock)) {
			if (HideRailsManager.hs) {
				return true;
			}
		}

		return false;
	}


	public static BlockReplacementType getBlockReplacementType(Player player, Block targetBlock)
	{
		BlockReplacementType blockType;

		if(checkBlockIfActive(targetBlock))
		{
			blockType = BlockReplacementType.RAILS;
		}
		else if(checkBlockIfActive(targetBlock))
		{
			blockType = BlockReplacementType.IRON_BARS;
		}
		else if(checkBlockIfActive(targetBlock))
		{
			blockType = BlockReplacementType.COMMAND_BLOCK;
		}
		else if(checkBlockIfActive(targetBlock))
		{
			blockType = BlockReplacementType.REDSTONE;
		}
		else if(checkBlockIfActive(targetBlock))
		{
			blockType = BlockReplacementType.SIGN;
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
		if(input.contains("true") || input.contains("allow") || input.contains("yes")) {
			return "true";
		}

		if(input.contains("false") || input.contains("deny") || input.contains("no")) {
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

			if (Checker.isRail(bl)) {
				if (HideRailsManager.hr) {
					railsLocs.add(blockLoc);
				}
			}

			if (Checker.isIronBar(bl)) {
				if (HideRailsManager.hb) {
					railsLocs.add(blockLoc);
				}
			}

			if (Checker.isCommandBlock(bl)) {
				if (HideRailsManager.hc) {
					railsLocs.add(blockLoc);
				}
			}

			if (Checker.isRedstone(bl)) {
				if (HideRailsManager.hd) {
					railsLocs.add(blockLoc);
				}
			}

			if (Checker.isSign(bl)) {
				if (HideRailsManager.hs) {
					railsLocs.add(blockLoc);
				}
			}
		}

		return railsLocs;
	}
}
