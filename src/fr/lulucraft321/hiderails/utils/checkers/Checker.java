/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.utils.checkers;

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
}