/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.enums.BlockReplacementType;
import fr.nepta.hiderails.managers.HideRailsManager;
import fr.nepta.hiderails.managers.PlayerClaimDataManager;
import fr.nepta.hiderails.models.ClaimData;
import fr.nepta.hiderails.models.selectionsystem.Cuboid;

public class HideRailsSelectionChecker
{
	public static ClaimData getHideRailsSelection(Player player)
	{
		return PlayerClaimDataManager.getPlayerClaimData(player);
	}

	private static List<Location> getAllBlocksLocationInHideRailsSelection(@Nonnull Cuboid selection)
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

	public static List<Location> getAllValidRails(@Nonnull Cuboid selection, @Nullable List<BlockReplacementType> types)
	{
		List<Location> railsLocsTemp = HideRailsSelectionChecker.getAllBlocksLocationInHideRailsSelection(selection);
		List<Location> validRailsLocs = new ArrayList<>();

		boolean rails = false;
		boolean ironBars = false;
		boolean commandBlocks = false;
		boolean redstone = false;
		boolean signs = false;

		if (types == null || types.isEmpty()) {
			rails = true;
			ironBars = true;
			commandBlocks = true;
			redstone = true;
			signs = true;
		} else {
			for (BlockReplacementType type : types) {
				switch (type) {
				case RAIL:
					if (HideRailsManager.hr) {
						rails = true;
						continue;
					}
					break;

				case IRON_BAR:
					if (HideRailsManager.hb) {
						ironBars = true;
						continue;
					}
					break;

				case COMMAND_BLOCK:
					if (HideRailsManager.hc) {
						commandBlocks = true;
						continue;
					}
					break;

				case REDSTONE:
					if (HideRailsManager.hd) {
						redstone = true;
						continue;
					}
					break;

				case SIGN:
					if (HideRailsManager.hs) {
						signs = true;
						continue;
					}
					break;

				}

			}
		}

		for (Location blockLoc : railsLocsTemp) {
			Block bl = Bukkit.getWorld(blockLoc.getWorld().getName()).getBlockAt(blockLoc);

			if (rails) {
				if (BlocksChecker.isRail(bl)) {
					validRailsLocs.add(blockLoc);
					continue;
				}
			}

			if (ironBars) {
				if (BlocksChecker.isIronBar(bl)) {
					validRailsLocs.add(blockLoc);
					continue;
				}
			}

			if (commandBlocks) {
				if (BlocksChecker.isCommandBlock(bl)) {
					validRailsLocs.add(blockLoc);
					continue;
				}
			}

			if (redstone) {
				if (BlocksChecker.isRedstone(bl)) {
					validRailsLocs.add(blockLoc);
					continue;
				}
			}

			if (signs) {
				if (BlocksChecker.isSign(bl)) {
					validRailsLocs.add(blockLoc);
					continue;
				}
			}
		}

		return validRailsLocs;
	}
}
