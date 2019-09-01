/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.utils.checkers;

import static fr.lulucraft321.hiderails.utils.checkers.JavaChecker.enumCheck;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.enums.BlockReplacementType;
import fr.lulucraft321.hiderails.enums.Messages;
import fr.lulucraft321.hiderails.enums.Version;
import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.managers.MessagesManager;
import fr.lulucraft321.hiderails.utils.data.MaterialData;

public class BlocksChecker
{
	private final static BlockFace[] faces = new BlockFace[]{ BlockFace.UP, BlockFace.DOWN, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.EAST, BlockFace.WEST };

	public static boolean isMaterial(String mat)
	{
		if(mat.contains(":")) {
			String[] split = mat.split(":");
			return Material.getMaterial(split[0].toUpperCase()) != null;
		} else {
			return Material.getMaterial(mat.toUpperCase()) != null;
		}
	}

	public static boolean isRail(Block blockCheck)
	{
		final Material bType = blockCheck.getType();

		if (HideRails.version == Version.v1_12) {
			return bType == enumCheck("RAILS") ||
					bType == enumCheck("LADDER") ||
					bType == enumCheck("ACTIVATOR_RAIL") ||
					bType == enumCheck("DETECTOR_RAIL") ||
					bType == enumCheck("POWERED_RAIL");
		} else if (HideRails.version == Version.v1_13 || HideRails.version == Version.v1_14) {
			return bType == enumCheck("LEGACY_RAILS") ||
					bType == enumCheck("LEGACY_LADDER") ||
					bType == enumCheck("LEGACY_ACTIVATOR_RAIL") ||
					bType == enumCheck("LEGACY_DETECTOR_RAIL") ||
					bType == enumCheck("LEGACY_POWERED_RAIL") ||
					// api-version 1.13
					bType == enumCheck("RAIL") ||
					bType == enumCheck("LADDER") ||
					bType == enumCheck("ACTIVATOR_RAIL") ||
					bType == enumCheck("DETECTOR_RAIL") ||
					bType == enumCheck("POWERED_RAIL");
		}
		return false;
	}

	public static boolean isRail(Material matCheck)
	{
		if (HideRails.version == Version.v1_12) {
			return matCheck == enumCheck("RAILS") ||
					matCheck == enumCheck("LADDER") ||
					matCheck == enumCheck("ACTIVATOR_RAIL") ||
					matCheck == enumCheck("DETECTOR_RAIL") ||
					matCheck == enumCheck("POWERED_RAIL");
		} else if (HideRails.version == Version.v1_13 || HideRails.version == Version.v1_14) {
			return matCheck == enumCheck("LEGACY_RAILS") ||
					matCheck == enumCheck("LEGACY_LADDER") ||
					matCheck == enumCheck("LEGACY_ACTIVATOR_RAIL") ||
					matCheck == enumCheck("LEGACY_DETECTOR_RAIL") ||
					matCheck == enumCheck("LEGACY_POWERED_RAIL") ||
					// api-version 1.13
					matCheck == enumCheck("RAIL") ||
					matCheck == enumCheck("LADDER") ||
					matCheck == enumCheck("ACTIVATOR_RAIL") ||
					matCheck == enumCheck("DETECTOR_RAIL") ||
					matCheck == enumCheck("POWERED_RAIL");
		}
		return false;
	}

	public static boolean isIronBar(Block blockCheck)
	{
		final Material bType = blockCheck.getType();

		if (HideRails.version == Version.v1_12) {
			return bType == enumCheck("IRON_FENCE");
		} else if (HideRails.version == Version.v1_13 || HideRails.version == Version.v1_14) {
			return bType == enumCheck("LEGACY_IRON_FENCE") ||
					// api-version 1.13
					bType == enumCheck("IRON_BARS");
		}
		return false;
	}

	public static boolean isIronBar(Material matCheck)
	{
		if (HideRails.version == Version.v1_12) {
			return matCheck == enumCheck("IRON_FENCE");
		} else if (HideRails.version == Version.v1_13 || HideRails.version == Version.v1_14) {
			return matCheck == enumCheck("LEGACY_IRON_FENCE") ||
					// api-version 1.13
					matCheck == enumCheck("IRON_BARS");
		}
		return false;
	}

	public static boolean isCommandBlock(Block blockCheck)
	{
		final Material bType = blockCheck.getType();

		if (HideRails.version == Version.v1_12) {
			// for 1.8 version
			if (Version.v1_12.isOldVersion())
				return bType == enumCheck("COMMAND");
			else
				return bType == enumCheck("COMMAND") || bType == enumCheck("COMMAND_CHAIN") || bType == enumCheck("COMMAND_REPEATING");
		} else if (HideRails.version == Version.v1_13 || HideRails.version == Version.v1_14) {
			return bType == enumCheck("LEGACY_COMMAND") || bType == enumCheck("LEGACY_COMMAND_CHAIN") || bType == enumCheck("LEGACY_COMMAND_REPEATING") ||
					// api-version 1.13
					bType == enumCheck("COMMAND_BLOCK") || bType == enumCheck("CHAIN_COMMAND_BLOCK") || bType == enumCheck("REPEATING_COMMAND_BLOCK");
		}
		return false;
	}

	public static boolean isCommandBlock(Material matCheck)
	{
		if (HideRails.version == Version.v1_12) {
			// for 1.8 version
			if (Version.v1_12.isOldVersion())
				return matCheck == enumCheck("COMMAND");
			else
				return matCheck == enumCheck("COMMAND") || matCheck == enumCheck("COMMAND_CHAIN") || matCheck == enumCheck("COMMAND_REPEATING");
		} else if (HideRails.version == Version.v1_13 || HideRails.version == Version.v1_14) {
			return matCheck == enumCheck("LEGACY_COMMAND") || matCheck == enumCheck("LEGACY_COMMAND_CHAIN") || matCheck == enumCheck("LEGACY_COMMAND_REPEATING") ||
					// api-version 1.13
					matCheck == enumCheck("COMMAND_BLOCK") || matCheck == enumCheck("CHAIN_COMMAND_BLOCK") || matCheck == enumCheck("REPEATING_COMMAND_BLOCK");
		}
		return false;
	}

	public static boolean isRedstone(Block blockCheck)
	{
		final Material bType = blockCheck.getType();

		if (HideRails.version == Version.v1_12) {
			return bType == enumCheck("REDSTONE_WIRE") || bType == enumCheck("REDSTONE") || bType == enumCheck("REDSTONE_BLOCK") ||
					bType == enumCheck("REDSTONE_COMPARATOR") || bType == enumCheck("REDSTONE_COMPARATOR_OFF") || bType == enumCheck("REDSTONE_COMPARATOR_ON") ||
					bType == enumCheck("REDSTONE_TORCH_OFF") || bType == enumCheck("REDSTONE_TORCH_ON") ||
					bType == enumCheck("DIODE_BLOCK_OFF") || bType == enumCheck("DIODE_BLOCK_ON") ||
					bType == enumCheck("LEVER");
		} else if (HideRails.version == Version.v1_13 || HideRails.version == Version.v1_14) {
			return bType == enumCheck("LEGACY_REDSTONE_WIRE") || bType == enumCheck("LEGACY_REDSTONE") || bType == enumCheck("LEGACY_REDSTONE_BLOCK") ||
					bType == enumCheck("LEGACY_REDSTONE_TORCH_OFF") || bType == enumCheck("LEGACY_REDSTONE_TORCH_ON") ||
					bType == enumCheck("LEGACY_DIODE_BLOCK_OFF") || bType == enumCheck("LEGACY_DIODE_BLOCK_ON") ||
					bType == enumCheck("REPEATER") || bType == enumCheck("LEGACY_REDSTONE_COMPARATOR_ON") || bType == enumCheck("LEGACY_REDSTONE_COMPARATOR_OFF") ||
					bType == enumCheck("LEGACY_LEVER") ||
					// api-version 1.13
					bType == enumCheck("REDSTONE_WIRE") || bType == enumCheck("REDSTONE") || bType == enumCheck("REDSTONE_BLOCK") ||
					bType == enumCheck("REDSTONE_TORCH") || bType == enumCheck("REDSTONE_WALL_TORCH") ||
					bType == enumCheck("REPEATER") || bType == enumCheck("COMPARATOR") ||
					bType == enumCheck("LEVER");
		}
		return false;
	}

	public static boolean isRedstone(Material matCheck)
	{
		if (HideRails.version == Version.v1_12) {
			return matCheck == enumCheck("REDSTONE_WIRE") || matCheck == enumCheck("REDSTONE") || matCheck == enumCheck("REDSTONE_BLOCK") ||
					matCheck == enumCheck("REDSTONE_COMPARATOR") || matCheck == enumCheck("REDSTONE_COMPARATOR_OFF") || matCheck == enumCheck("REDSTONE_COMPARATOR_ON") ||
					matCheck == enumCheck("REDSTONE_TORCH_OFF") || matCheck == enumCheck("REDSTONE_TORCH_ON") ||
					matCheck == enumCheck("DIODE_BLOCK_OFF") || matCheck == enumCheck("DIODE_BLOCK_ON") ||
					matCheck == enumCheck("LEVER");
		} else if (HideRails.version == Version.v1_13 || HideRails.version == Version.v1_14) {
			return matCheck == enumCheck("LEGACY_REDSTONE_WIRE") || matCheck == enumCheck("LEGACY_REDSTONE") || matCheck == enumCheck("LEGACY_REDSTONE_BLOCK") ||
					matCheck == enumCheck("LEGACY_REDSTONE_TORCH_OFF") || matCheck == enumCheck("LEGACY_REDSTONE_TORCH_ON") ||
					matCheck == enumCheck("LEGACY_DIODE_BLOCK_OFF") || matCheck == enumCheck("LEGACY_DIODE_BLOCK_ON") ||
					matCheck == enumCheck("REPEATER") || matCheck == enumCheck("LEGACY_REDSTONE_COMPARATOR_ON") || matCheck == enumCheck("LEGACY_REDSTONE_COMPARATOR_OFF") ||
					matCheck == enumCheck("LEGACY_LEVER") ||
					// api-version 1.13
					matCheck == enumCheck("REDSTONE_WIRE") || matCheck == enumCheck("REDSTONE") || matCheck == enumCheck("REDSTONE_BLOCK") ||
					matCheck == enumCheck("REDSTONE_TORCH") || matCheck == enumCheck("REDSTONE_WALL_TORCH") ||
					matCheck == enumCheck("REPEATER") || matCheck == enumCheck("COMPARATOR") ||
					matCheck == enumCheck("LEVER");
		}
		return false;
	}

	public static boolean isSign(Block blockCheck)
	{
		final Material bType = blockCheck.getType();

		if (HideRails.version == Version.v1_12) {
			return bType == enumCheck("SIGN") || bType == enumCheck("SIGN_POST") || bType == enumCheck("WALL_SIGN");
		} else if (HideRails.version == Version.v1_13) {
			return bType == enumCheck("LEGACY_SIGN_POST") || bType == enumCheck("LEGACY_WALL_SIGN") ||
					// api-version 1.13
					bType == enumCheck("SIGN") || bType == enumCheck("WALL_SIGN");
		} else if (HideRails.version == Version.v1_14) {
			return bType == enumCheck("LEGACY_SIGN_POST") || bType == enumCheck("LEGACY_WALL_SIGN") ||
					// api-version 1.14
					bType == enumCheck("OAK_SIGN") ||
					bType == enumCheck("OAK_WALL_SIGN") ||
					bType == enumCheck("SPRUCE_SIGN") ||
					bType == enumCheck("SPRUCE_WALL_SIGN") ||
					bType == enumCheck("ACACIA_SIGN") ||
					bType == enumCheck("ACACIA_WALL_SIGN") ||
					bType == enumCheck("BIRCH_SIGN") ||
					bType == enumCheck("BIRCH_WALL_SIGN") ||
					bType == enumCheck("DARK_OAK_SIGN") ||
					bType == enumCheck("DARK_OAK_WALL_SIGN") ||
					bType == enumCheck("JUNGLE_SIGN") ||
					bType == enumCheck("JUNGLE_WALL_SIGN");
		}
		return false;
	}

	public static boolean isSign(Material matCheck)
	{
		if (HideRails.version == Version.v1_12) {
			return matCheck == enumCheck("SIGN") || matCheck == enumCheck("SIGN_POST") || matCheck == enumCheck("WALL_SIGN");
		} else if (HideRails.version == Version.v1_13) {
			return matCheck == enumCheck("LEGACY_SIGN_POST") || matCheck == enumCheck("LEGACY_WALL_SIGN") ||
					// api-version 1.13
					matCheck == enumCheck("SIGN") || matCheck == enumCheck("WALL_SIGN");
		} else if (HideRails.version == Version.v1_14) {
			return matCheck == enumCheck("LEGACY_SIGN_POST") || matCheck == enumCheck("LEGACY_WALL_SIGN") ||
					// api-version 1.14
					matCheck == enumCheck("OAK_SIGN") ||
					matCheck == enumCheck("OAK_WALL_SIGN") ||
					matCheck == enumCheck("SPRUCE_SIGN") ||
					matCheck == enumCheck("SPRUCE_WALL_SIGN") ||
					matCheck == enumCheck("ACACIA_SIGN") ||
					matCheck == enumCheck("ACACIA_WALL_SIGN") ||
					matCheck == enumCheck("BIRCH_SIGN") ||
					matCheck == enumCheck("BIRCH_WALL_SIGN") ||
					matCheck == enumCheck("DARK_OAK_SIGN") ||
					matCheck == enumCheck("DARK_OAK_WALL_SIGN") ||
					matCheck == enumCheck("JUNGLE_SIGN") ||
					matCheck == enumCheck("JUNGLE_WALL_SIGN");
		}
		return false;
	}


	/**
	 * Check if block is solid or breakable with blockphysic
	 * 
	 * @param block
	 * @return
	 */
	public static boolean isSolid(Block block) {
		boolean b = true;

		if (BlocksChecker.isRail(block) || BlocksChecker.isSign(block)) {
			return false;
		}

		if (BlocksChecker.isCommandBlock(block)) {
			return true;
		}

		if (BlocksChecker.isRedstone(block)) {
			if (block.getType() == Material.REDSTONE_BLOCK) {
				return true;
			} else {
				return false;
			}
		}

		return b;
	}


	/**
	 * Get all hiddenBlocks around block
	 * 
	 * @param block
	 * @return List<Block>
	 */
	public static List<Block> getBlockFaceHiddenBlocks(Block b)
	{
		if (b != null) {
			List<Block> blocks = new ArrayList<>();
			for (BlockFace blockFace : faces) {
				Block block = b.getRelative(blockFace);

				if (block != null && block.getType() != null) {
					if (BlocksChecker.isRail(block) || BlocksChecker.isRedstone(block) || BlocksChecker.isCommandBlock(block) || BlocksChecker.isSign(block)) {
						Location a = new Location(block.getLocation().getWorld(), block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ());
						if (a != null) {
							if (HideRailsManager.getHiddenRail(a) != null) {
								if (!blocks.contains(block))
									blocks.add(block);
							}
						}
					}
				}
			}
			return blocks;
		}
		return null;
	}


	/**
	 * Get all hiddenSigns around block
	 * 
	 * @param block
	 * @return Block
	 */
	public static Block getBlockFaceHiddenSign(Block b)
	{
		if (b != null) {
			for (BlockFace blockFace : faces) {
				Block block = b.getRelative(blockFace);

				if (block != null && block.getType() != null) {
					if (BlocksChecker.isSign(block)) {
						Location a = new Location(block.getLocation().getWorld(), block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ());
						if (a != null) {
							if (HideRailsManager.getHiddenRail(a) != null) {
								return block;
							}
						}
					}
				}
			}
		}

		return null;
	}


	public static MaterialData getMatData(Player p, String input)
	{
		Material mat = null;
		byte data = 0;

		// Material with data
		if(input.contains(":"))
		{
			String[] split = input.split(":");

			// Material with ID
			if(JavaChecker.isInt(split[0]) && JavaChecker.isInt(split[1])) {
				if (HideRails.version == Version.v1_12) {
					try {
						mat = (Material) Material.class.getDeclaredMethod("getMaterial", int.class).invoke(Material.class, Integer.parseInt(split[0]));
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
							| NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
					//mat = Material.getMaterial(Integer.parseInt(split[0]));
				}
				data = getMatData(split[1]);
			}
			else {
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
			if (JavaChecker.isInt(input)) {
				if (HideRails.version == Version.v1_12) {
					try {
						mat = (Material) Material.class.getDeclaredMethod("getMaterial", int.class).invoke(Material.class, Integer.parseInt(input));
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
							| NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
				}
				data = 0;
			}
			else {
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

	public static boolean checkBlockIfActive(Block newCheckBlock)
	{
		if (BlocksChecker.isRail(newCheckBlock)) {
			if (HideRailsManager.hr) {
				return true;
			}
		}

		else if (BlocksChecker.isIronBar(newCheckBlock)) {
			if (HideRailsManager.hb) {
				return true;
			}
		}

		else if (BlocksChecker.isCommandBlock(newCheckBlock)) {
			if (HideRailsManager.hc) {
				return true;
			}
		}

		else if (BlocksChecker.isRedstone(newCheckBlock)) {
			if (HideRailsManager.hd) {
				return true;
			}
		}

		else if (BlocksChecker.isSign(newCheckBlock)) {
			if (HideRailsManager.hs) {
				return true;
			}
		}

		return false;
	}

	public static BlockReplacementType getBlockReplacementType(Player player, Block targetBlock)
	{
		BlockReplacementType blockType;

		if(checkBlockIfActive(targetBlock)) {
			blockType = BlockReplacementType.RAILS;
		}
		else if(checkBlockIfActive(targetBlock)) {
			blockType = BlockReplacementType.IRON_BARS;
		}
		else if(checkBlockIfActive(targetBlock)) {
			blockType = BlockReplacementType.COMMAND_BLOCK;
		}
		else if(checkBlockIfActive(targetBlock)) {
			blockType = BlockReplacementType.REDSTONE;
		}
		else if(checkBlockIfActive(targetBlock)) {
			blockType = BlockReplacementType.SIGN;
		}
		// If Material is not valid
		else {
			MessagesManager.sendPluginMessage(player, Messages.RAIL_ERROR);
			return null;
		}

		return blockType;
	}
}
