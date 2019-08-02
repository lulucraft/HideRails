/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.utils.checkers;

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
		if(input.contains("true") || input.contains("allow") || input.contains("yes")) {
			return "true";
		}

		if(input.contains("false") || input.contains("deny") || input.contains("no")) {
			return "false";
		}
		return null;
	}

	public static Material enumCheck(String materialString)
	{
		return Enum.valueOf(Material.class, materialString);
	}
}