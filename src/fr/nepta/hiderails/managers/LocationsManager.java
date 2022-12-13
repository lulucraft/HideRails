/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.managers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import fr.nepta.hiderails.enums.BlockReplacementType;
import fr.nepta.hiderails.utils.BlocksChecker;

public class LocationsManager {
	private final static BlockFace[] FACES = new BlockFace[] { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

	/**
	 * Get connected Blocks
	 * 
	 * @param startLoc
	 * @param blockType
	 * @return connected blocks locations
	 */
	protected static List<Location> getConnectedBlocks(Location startLoc, List<BlockReplacementType> types) {
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

					for (BlockReplacementType type : (types != null ? (BlockReplacementType[]) types.toArray() : BlockReplacementType.values())) {
						if ((type == BlockReplacementType.RAIL && BlocksChecker.checkBlockIfActive(newCheckBlock))
								|| (type == BlockReplacementType.IRON_BAR && BlocksChecker.checkBlockIfActive(newCheckBlock))
								|| (type == BlockReplacementType.COMMAND_BLOCK && BlocksChecker.checkBlockIfActive(newCheckBlock))
								|| (type == BlockReplacementType.REDSTONE && BlocksChecker.checkBlockIfActive(newCheckBlock))
								|| (type == BlockReplacementType.SIGN && BlocksChecker.checkBlockIfActive(newCheckBlock))) {
							checked.add(newCheckBlock.getLocation());

							finishCheckCurrentBlock = 0;
						} else {
							finishCheckCurrentBlock += 1;
						}
					}

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
				//finishCheckCurrentBlock = 0;
				checkFinished = true;
				//next = 0;
				break;
			}

			next++;
		}

		return checked;
	}


	/*
	 * Serialisation
	 */
	/**
	 * Serialize location
	 * 
	 * @param loc
	 * @return serialized location
	 */
	public static String serialize(@Nonnull Location loc) {
		String splitter = ",";
		return loc.getWorld().getName() + splitter + loc.getX() + splitter + loc.getY() + splitter + loc.getZ();
	}

	/**
	 * Deserialize string location
	 * 
	 * @param loc
	 * @return location
	 */
	protected static Location deserializeLoc(@Nonnull String loc) {
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

	protected static Material deserializeMatInSerializedLoc(@Nonnull String loc) {
		String[] split = loc.split(",");
		String[] last = s(split);
		return Material.getMaterial(last[1]);
	}

	protected static byte deserializeDataInSerializedLoc(@Nonnull String loc) {
		String[] split = loc.split(",");
		String[] last = s(split);
		return Byte.parseByte(last[2]);
	}

	private static String[] s(@Nonnull String[] split) {
		String[] last = null;

		if (split.length > 5) {
			last = split[5].split(";"); // Old version (before 0.0.4-SNAPSHOT)
		} else {
			last = split[3].split(";");
		}
		return last;
	}
}
