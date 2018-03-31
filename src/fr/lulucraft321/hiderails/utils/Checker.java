/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

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
}
