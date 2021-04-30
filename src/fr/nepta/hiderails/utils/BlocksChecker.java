/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.utils;

import static fr.nepta.hiderails.utils.JavaChecker.enumCheck;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.HideRails;
import fr.nepta.hiderails.enums.BlockReplacementType;
import fr.nepta.hiderails.enums.Messages;
import fr.nepta.hiderails.enums.Version;
import fr.nepta.hiderails.managers.HideRailsManager;
import fr.nepta.hiderails.managers.MessagesManager;
import fr.nepta.hiderails.models.MaterialData;

public class BlocksChecker
{
	private final static BlockFace[] FACES = new BlockFace[]{ BlockFace.UP, BlockFace.DOWN, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.EAST, BlockFace.WEST };

	public static boolean isMaterial(String input)
	{
		if (input.contains(":")) {
			String[] split = input.split(":");
			return Material.getMaterial(split[0].toUpperCase()) != null;
		} else {
			return Material.getMaterial(input.toUpperCase()) != null;
		}
	}

	/**
	 * Check if the block is a rail
	 * 
	 * @param block
	 * @return
	 */
	public static boolean isRail(Block block) {
		return isRail(block.getType());
	}

	/**
	 * Check if the material is a rail
	 * 
	 * @param matCheck
	 * @return is a rail
	 */
	public static boolean isRail(Material matCheck)
	{
		if (HideRails.version == Version.V1_12) {
			return matCheck == enumCheck("RAILS") ||
					matCheck == enumCheck("LADDER") ||
					matCheck == enumCheck("ACTIVATOR_RAIL") ||
					matCheck == enumCheck("DETECTOR_RAIL") ||
					matCheck == enumCheck("POWERED_RAIL");
		} else if (HideRails.version == Version.V1_13 || HideRails.version == Version.V1_14 || HideRails.version == Version.V1_15) {
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

	/**
	 * Check if the block is an iron bar
	 * 
	 * @param block
	 * @return is iron bar
	 */
	public static boolean isIronBar(Block block) {
		return isIronBar(block.getType());
	}

	/**
	 * Check if the material is an iron bar
	 * 
	 * @param matCheck
	 * @return is iron bar
	 */
	public static boolean isIronBar(Material matCheck)
	{
		if (HideRails.version == Version.V1_12) {
			return matCheck == enumCheck("IRON_FENCE");
		} else if (HideRails.version == Version.V1_13 || HideRails.version == Version.V1_14 || HideRails.version == Version.V1_15) {
			return matCheck == enumCheck("LEGACY_IRON_FENCE") ||
					// api-version 1.13
					matCheck == enumCheck("IRON_BARS");
		}
		return false;
	}

	/**
	 * Check if the block is a command block
	 * 
	 * @param block
	 * @return is command block
	 */
	public static boolean isCommandBlock(Block block) {
		return isCommandBlock(block.getType());
	}

	/**
	 * Check if the material is a command block type
	 * 
	 * @param matCheck
	 * @return is command block type
	 */
	public static boolean isCommandBlock(Material matCheck)
	{
		if (HideRails.version == Version.V1_12) {
			// for 1.8 version
			if (Version.V1_12.isOldVersion())
				return matCheck == enumCheck("COMMAND");
			else
				return matCheck == enumCheck("COMMAND") || matCheck == enumCheck("COMMAND_CHAIN") || matCheck == enumCheck("COMMAND_REPEATING");
		} else if (HideRails.version == Version.V1_13 || HideRails.version == Version.V1_14 || HideRails.version == Version.V1_15) {
			return matCheck == enumCheck("LEGACY_COMMAND") || matCheck == enumCheck("LEGACY_COMMAND_CHAIN") || matCheck == enumCheck("LEGACY_COMMAND_REPEATING") ||
					// api-version 1.13
					matCheck == enumCheck("COMMAND_BLOCK") || matCheck == enumCheck("CHAIN_COMMAND_BLOCK") || matCheck == enumCheck("REPEATING_COMMAND_BLOCK");
		}
		return false;
	}

	/**
	 * Check if block is the redstone
	 * 
	 * @param block
	 * @return is redstone
	 */
	public static boolean isRedstone(Block block) {
		return isRedstone(block.getType());
	}

	/**
	 * Check if the material is a redstone type
	 * 
	 * @param matCheck
	 * @return is redstone type
	 */
	public static boolean isRedstone(Material matCheck)
	{
		if (HideRails.version == Version.V1_12) {
			return matCheck == enumCheck("REDSTONE_WIRE") || matCheck == enumCheck("REDSTONE") || matCheck == enumCheck("REDSTONE_BLOCK") ||
					matCheck == enumCheck("REDSTONE_COMPARATOR") || matCheck == enumCheck("REDSTONE_COMPARATOR_OFF") || matCheck == enumCheck("REDSTONE_COMPARATOR_ON") ||
					matCheck == enumCheck("REDSTONE_TORCH_OFF") || matCheck == enumCheck("REDSTONE_TORCH_ON") ||
					matCheck == enumCheck("DIODE_BLOCK_OFF") || matCheck == enumCheck("DIODE_BLOCK_ON") ||
					matCheck == enumCheck("LEVER");
		} else if (HideRails.version == Version.V1_13 || HideRails.version == Version.V1_14 || HideRails.version == Version.V1_15) {
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

	/**
	 * Check if block is a Sign
	 * 
	 * @param block
	 * @return block is a sign
	 */
	public static boolean isSign(Block block) {
		return isSign(block.getType());
	}

	/**
	 * Check if material is a Sign type
	 * 
	 * @param matCheck
	 * @return is a sign
	 */
	public static boolean isSign(Material matCheck)
	{
		if (HideRails.version == Version.V1_12) {
			return matCheck == enumCheck("SIGN") || matCheck == enumCheck("SIGN_POST") || matCheck == enumCheck("WALL_SIGN");
		} else if (HideRails.version == Version.V1_13) {
			return matCheck == enumCheck("LEGACY_SIGN_POST") || matCheck == enumCheck("LEGACY_WALL_SIGN") ||
					// api-version 1.13
					matCheck == enumCheck("SIGN") || matCheck == enumCheck("WALL_SIGN");
		} else if (HideRails.version == Version.V1_14 || HideRails.version == Version.V1_15) {
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
	 * Check if the block is solid or breakable by block physic
	 * 
	 * @param block
	 * @return is solid
	 */
	public static boolean isSolid(Block block) {
		if (isRail(block) || isSign(block)) {
			return false;
		}

		if (isCommandBlock(block)) {
			return true;
		}

		if (isRedstone(block)) {
			if (block.getType() == Material.REDSTONE_BLOCK) {
				return true;
			} else {
				return false;
			}
		}

		return true;
	}


	/**
	 * Get all hidden blocks around the block
	 * 
	 * @param block
	 * @return block List
	 */
	public static List<Block> getBlockFaceHiddenBlocks(Block block)
	{
		if (block != null) {
			List<Block> blocks = new ArrayList<>();
			for (BlockFace blockFace : FACES) {
				Block b = block.getRelative(blockFace);

				if (b != null && b.getType() != null) {
					if (isRail(b) || isRedstone(b) || isCommandBlock(b) || isSign(b)) {
						Location loc = b.getLocation();
						if (loc != null) {
							if (HideRailsManager.getHiddenRail(loc) != null) {
								if (!blocks.contains(b))
									blocks.add(b);
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
	 * Get all hidden signs around the block
	 * 
	 * @param block
	 * @return Block
	 */
	public static Block getBlockFaceHiddenSign(Block block)
	{
		if (block != null) {
			for (BlockFace blockFace : FACES) {
				Block b = block.getRelative(blockFace);

				if (b != null && b.getType() != null) {
					if (isSign(b)) {
						Location loc = b.getLocation();
						if (loc != null) {
							if (HideRailsManager.getHiddenRail(loc) != null) {
								return block;
							}
						}
					}
				}
			}
		}

		return null;
	}


	/**
	 * Get MaterialData of serialized data
	 * 
	 * @param p
	 * @param input
	 * @return MaterialData
	 */
	public static MaterialData getMatData(Player p, String input)
	{
		Material mat = null;
		byte data = 0;

		// Material with data
		if (input.contains(":")) {
			String[] split = input.split(":");

			// Material with ID
			if (JavaChecker.isInt(split[0]) && JavaChecker.isInt(split[1])) {
				if (HideRails.version == Version.V1_12) {
					try {
						mat = (Material) Material.class.getDeclaredMethod("getMaterial", int.class).invoke(Material.class, Integer.parseInt(split[0]));
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
							| NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
					//mat = Material.getMaterial(Integer.parseInt(split[0]));
				}
				data = getMatData(split[1]);
			} else {
				// Material with NAME
				if (isMaterial(input)) {
					mat = Material.getMaterial(split[0].toUpperCase());
					data = getMatData(split[1]);
				}
			}
		}
		else
		{
			// Material with ID
			if (JavaChecker.isInt(input)) {
				if (HideRails.version == Version.V1_12) {
					try {
						mat = (Material) Material.class.getDeclaredMethod("getMaterial", int.class).invoke(Material.class, Integer.parseInt(input));
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
							| NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
				}
				data = 0;
			} else {
				// Material with NAME
				if(isMaterial(input)) {
					mat = Material.getMaterial(input.toUpperCase());
					if (mat.name().contains("LEGACY_")) mat = null; // Added to remove console error (invalid block) with the new version of the changeBlock packet (accept data)
					data = 0;
				}
			}
		}

		if (mat == null || mat.name().contains("LEGACY_")) { // Check LEGACY to remove console error (invalid block) with the new version of the changeBlock packet (accept data)
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

	/**
	 * Check if the hiding of a block is enabled
	 * 
	 * @param newCheckBlock
	 * @return
	 */
	public static boolean checkBlockIfActive(Block block)
	{
		if (HideRailsManager.hr && isRail(block)) {
			return true;
		} else if (HideRailsManager.hb && isIronBar(block)) {
			return true;
		} else if (HideRailsManager.hc && isCommandBlock(block)) {
			return true;
		} else if (HideRailsManager.hd && isRedstone(block)) {
			return true;
		} else if (HideRailsManager.hs && isSign(block)) {
			return true;
		}

		return false;
	}

	public static BlockReplacementType getBlockReplacementType(Player player, Block targetBlock)
	{
		BlockReplacementType blockType = null;

		if (HideRailsManager.hr && isRail(targetBlock)) {
			blockType = BlockReplacementType.RAIL;
		} else if (HideRailsManager.hb && isIronBar(targetBlock)) {
			blockType = BlockReplacementType.IRON_BAR;
		} else if (HideRailsManager.hc && isCommandBlock(targetBlock)) {
			blockType = BlockReplacementType.COMMAND_BLOCK;
		} else if (HideRailsManager.hd && isRedstone(targetBlock)) {
			blockType = BlockReplacementType.REDSTONE;
		} else if (HideRailsManager.hs && isSign(targetBlock)) {
			blockType = BlockReplacementType.SIGN;
		}
		// If the Material is invalid
		else {
			MessagesManager.sendPluginMessage(player, Messages.RAIL_ERROR);
		}

		System.err.println(blockType);
		return blockType;
	}
}
