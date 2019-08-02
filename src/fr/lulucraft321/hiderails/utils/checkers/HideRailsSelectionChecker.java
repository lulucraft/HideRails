/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.utils.checkers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.managers.PlayerClaimDataManager;
import fr.lulucraft321.hiderails.utils.data.ClaimData;
import fr.lulucraft321.hiderails.utils.selectionsystem.Cuboid;

public class HideRailsSelectionChecker
{
	public static ClaimData getHideRailsSelection(Player player)
	{
		ClaimData cd = PlayerClaimDataManager.getPlayerClaimData(player);

		return cd;
	}

	private static List<Location> getAllBlocksLocationInWeSelection(Cuboid selection)
	{
		Location p1 = selection.getVector1().toLocation(selection.getWorld()); // Minimum point of HideRails Selection
		Location p2 = selection.getVector2().toLocation(selection.getWorld()); // Maximum point of HideRails Selection

		List<Location> railsLocsTemp = new ArrayList<>(); // Temporary storage of all blocks Location in Worldedit Selection

		for (int x = p1.getBlockX(); x <= p2.getBlockX(); x++)
		{
			for (int y = p1.getBlockY(); y <= p2.getBlockY(); y++)
			{
				for (int z = p1.getBlockZ(); z <= p2.getBlockZ(); z++)
				{
					railsLocsTemp.add(new Location(p1.getWorld(), x, y, z));
				}
			}
		}

		return railsLocsTemp;
	}

	public static List<Location> getAllValidRails(Cuboid selection, List<Material> types)
	{
		List<Location> railsLocsTemp = HideRailsSelectionChecker.getAllBlocksLocationInWeSelection(selection);
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
