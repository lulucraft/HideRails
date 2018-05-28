/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.utils.checkers;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import fr.lulucraft321.hiderails.managers.HideRailsManager;

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


	/**
	 * Check distance between hiddenBlock and chunk
	 * 
	 * @param chunk
	 * @return double
	 */
	public static double checkChunkDistance(Chunk chunk) {
		World w = chunk.getWorld();
		Location loc = new Location(w, chunk.getX(), 0, chunk.getZ());
		for (Location l : HideRailsManager.chunksLocs) {
			if (l.getWorld() == chunk.getWorld()) {
				double dist = l.distance(loc);
				if (dist == 5.0D || dist == 3.0D || dist == 1.0D) {
					return l.distance(loc);
				}
			}
		}
		return 0.0D;
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