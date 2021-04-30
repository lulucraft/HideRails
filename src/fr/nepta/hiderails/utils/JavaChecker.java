/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.utils;

import org.bukkit.Material;

public class JavaChecker
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


	public static String getBoolean(String input)
	{
		if(input.contains("true") || input.contains("allow") || input.contains("yes") || input.contains("enable")) {
			return "true";
		}

		if(input.contains("false") || input.contains("deny") || input.contains("no") || input.contains("disable")) {
			return "false";
		}
		return null;
	}

	public static Material enumCheck(String materialString)
	{
		return Enum.valueOf(Material.class, materialString);
	}
}