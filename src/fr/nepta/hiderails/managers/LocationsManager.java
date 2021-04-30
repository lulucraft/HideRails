/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.sun.istack.internal.NotNull;

import fr.nepta.hiderails.utils.BlocksChecker;

public class LocationsManager {
	private final static BlockFace[] FACES = new BlockFace[] { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

	/**
	 * Get connected Blocks
	 * 
	 * @param startLoc
	 * @param blockType
	 * @return
	 */
//	protected static List<Location> getConnectedBlocks(Location startLoc, BlockReplacementType blockType) {
	protected static List<Location> getConnectedBlocks(Location startLoc) {
		boolean checkFinished = false;
		int next = 0;
		int finishCheckCurrentBlock = 0;
		List<Location> checked = new ArrayList<>();

		checked.add(startLoc);

		while (!checkFinished) {
			if (next < checked.size()) {
				Block newBlock = (Block) checked.get(next).getBlock();

				for (BlockFace blockFace : FACES) {
					Block newCheckBlock = newBlock.getRelative(blockFace);

					if (newCheckBlock == null) continue;
					if (checked.contains(newCheckBlock.getLocation())) continue;

//					if (blockType == BlockReplacementType.RAILS || blockType == BlockReplacementType.IRON_BARS
//							|| blockType == BlockReplacementType.COMMAND_BLOCK
//							|| blockType == BlockReplacementType.REDSTONE || blockType == BlockReplacementType.SIGN) {
						if (BlocksChecker.checkBlockIfActive(newCheckBlock)) {
							checked.add(newCheckBlock.getLocation());

							finishCheckCurrentBlock = 0;
						} else {
							finishCheckCurrentBlock += 1;
						}
//					}

					if (finishCheckCurrentBlock == 4) {
						checkFinished = true;
						next = 0;
						break;
					}
				}

				// Cancel check if all of the faces of newBlockCheck is null
				if (finishCheckCurrentBlock == 4) {
					checkFinished = true;
					next = 0;
					break;
				}
			} else {
//				finishCheckCurrentBlock = 0;
				checkFinished = true;
//				next = 0;
				break;
			}

			next++;
		}

		return checked;
	}

	/*
	 * Serialisation
	 */
	public static String serialize(@NotNull Location loc) {
		String splitter = ",";
		return loc.getWorld().getName() + splitter + loc.getX() + splitter + loc.getY() + splitter + loc.getZ();
	}

	protected static Location deserializeLoc(@NotNull String loc) {
		String[] split = loc.split(",");
		String[] last = s(split);
		World world = null;
		double x = 0.0D;
		double y = 0.0D;
		double z = 0.0D;
		float yaw = 0F;
		float pitch = 0F;

		if (split.length > 5) {
			world = Bukkit.getServer().getWorld(split[0]);
			x = Double.parseDouble(split[1]);
			y = Double.parseDouble(split[2]);
			z = Double.parseDouble(split[3]);
			yaw = Float.parseFloat(split[4]);
			pitch = Float.parseFloat(last[0]);
		} else {
			world = Bukkit.getServer().getWorld(split[0]);
			x = Double.parseDouble(split[1]);
			y = Double.parseDouble(split[2]);
			z = Double.parseDouble(last[0]);
		}
		return new Location(world, x, y, z, yaw, pitch);
	}

	protected static Material deserializeMatInSerializedLoc(@NotNull String loc) {
		String[] split = loc.split(",");
		String[] last = s(split);
		return Material.getMaterial(last[1]);
	}

	protected static byte deserializeDataInSerializedLoc(@NotNull String loc) {
		String[] split = loc.split(",");
		String[] last = s(split);
		return Byte.parseByte(last[2]);
	}

	private static String[] s(@NotNull String[] split) {
		String[] last = null;

		if (split.length > 5) {
			last = split[5].split(";"); // Old version (before 0.0.4-SNAPSHOT)
		} else {
			last = split[3].split(";");
		}
		return last;
	}
}
