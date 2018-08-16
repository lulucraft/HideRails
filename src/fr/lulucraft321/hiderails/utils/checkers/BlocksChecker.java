/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.utils.checkers;

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
		if (HideRails.version == Version.v1_12) {
			return blockCheck.getType() == Enum.valueOf(Material.class, "RAILS") ||
					blockCheck.getType() == Enum.valueOf(Material.class, "LADDER") ||
					blockCheck.getType() == Enum.valueOf(Material.class, "ACTIVATOR_RAIL") ||
					blockCheck.getType() == Enum.valueOf(Material.class, "DETECTOR_RAIL") ||
					blockCheck.getType() == Enum.valueOf(Material.class, "POWERED_RAIL");
		} else if (HideRails.version == Version.v1_13) {
			return blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_RAILS") ||
					blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_LADDER") ||
					blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_ACTIVATOR_RAIL") ||
					blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_DETECTOR_RAIL") ||
					blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_POWERED_RAIL");
		}
		return false;
	}

	public static boolean isRail(Material matCheck)
	{
		if (HideRails.version == Version.v1_12) {
			return matCheck == Enum.valueOf(Material.class, "RAILS") ||
					matCheck == Enum.valueOf(Material.class, "LADDER") ||
					matCheck == Enum.valueOf(Material.class, "ACTIVATOR_RAIL") ||
					matCheck == Enum.valueOf(Material.class, "DETECTOR_RAIL") ||
					matCheck == Enum.valueOf(Material.class, "POWERED_RAIL");
		} else if (HideRails.version == Version.v1_13) {
			return matCheck == Enum.valueOf(Material.class, "LEGACY_RAILS") ||
					matCheck == Enum.valueOf(Material.class, "LEGACY_LADDER") ||
					matCheck == Enum.valueOf(Material.class, "LEGACY_ACTIVATOR_RAIL") ||
					matCheck == Enum.valueOf(Material.class, "LEGACY_DETECTOR_RAIL") ||
					matCheck == Enum.valueOf(Material.class, "LEGACY_POWERED_RAIL");
		}
		return false;
	}

	public static boolean isIronBar(Block blockCheck)
	{
		if (HideRails.version == Version.v1_12) {
			return blockCheck.getType() == Enum.valueOf(Material.class, "IRON_FENCE");
		} else if (HideRails.version == Version.v1_13) {
			return blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_IRON_FENCE");
		}
		return false;
	}

	public static boolean isIronBar(Material matCheck)
	{
		if (HideRails.version == Version.v1_12) {
			return matCheck == Enum.valueOf(Material.class, "IRON_FENCE");
		} else if (HideRails.version == Version.v1_13) {
			return matCheck == Enum.valueOf(Material.class, "LEGACY_IRON_FENCE");
		}
		return false;
	}

	public static boolean isCommandBlock(Block blockCheck)
	{
		if (HideRails.version == Version.v1_12) {
			// for 1.8 version
			if (Version.v1_12.isOldVersion())
				return blockCheck.getType() == Enum.valueOf(Material.class, "COMMAND");
			else
				return blockCheck.getType() == Enum.valueOf(Material.class, "COMMAND") || blockCheck.getType() == Enum.valueOf(Material.class, "COMMAND_CHAIN") || blockCheck.getType() == Enum.valueOf(Material.class, "COMMAND_REPEATING");
		} else if (HideRails.version == Version.v1_13) {
			return blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_COMMAND") || blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_COMMAND_CHAIN") || blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_COMMAND_REPEATING");
		}
		return false;
	}

	public static boolean isCommandBlock(Material matCheck)
	{
		if (HideRails.version == Version.v1_12) {
			// for 1.8 version
			if (Version.v1_12.isOldVersion())
				return matCheck == Enum.valueOf(Material.class, "COMMAND");
			else
				return matCheck == Enum.valueOf(Material.class, "COMMAND") || matCheck == Enum.valueOf(Material.class, "COMMAND_CHAIN") || matCheck == Enum.valueOf(Material.class, "COMMAND_REPEATING");
		} else if (HideRails.version == Version.v1_13) {
			return matCheck == Enum.valueOf(Material.class, "LEGACY_COMMAND") || matCheck == Enum.valueOf(Material.class, "LEGACY_COMMAND_CHAIN") || matCheck == Enum.valueOf(Material.class, "LEGACY_COMMAND_REPEATING");
		}
		return false;
	}

	public static boolean isRedstone(Block blockCheck)
	{
		if (HideRails.version == Version.v1_12) {
			return blockCheck.getType() == Enum.valueOf(Material.class, "REDSTONE_WIRE") || blockCheck.getType() == Enum.valueOf(Material.class, "REDSTONE") || blockCheck.getType() == Enum.valueOf(Material.class, "REDSTONE_BLOCK") ||
					blockCheck.getType() == Enum.valueOf(Material.class, "REDSTONE_COMPARATOR") || blockCheck.getType() == Enum.valueOf(Material.class, "REDSTONE_COMPARATOR_OFF") || blockCheck.getType() == Enum.valueOf(Material.class, "REDSTONE_COMPARATOR_ON") ||
					blockCheck.getType() == Enum.valueOf(Material.class, "REDSTONE_TORCH_OFF") || blockCheck.getType() == Enum.valueOf(Material.class, "REDSTONE_TORCH_ON") ||
					blockCheck.getType() == Enum.valueOf(Material.class, "DIODE_BLOCK_OFF") || blockCheck.getType() == Enum.valueOf(Material.class, "DIODE_BLOCK_ON") ||
					blockCheck.getType() == Enum.valueOf(Material.class, "LEVER");
		} else if (HideRails.version == Version.v1_13) {
			return blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_REDSTONE_WIRE") || blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_REDSTONE") || blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_REDSTONE_BLOCK") ||
					blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_REDSTONE_TORCH_OFF") || blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_REDSTONE_TORCH_ON") ||
					blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_DIODE_BLOCK_OFF") || blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_DIODE_BLOCK_ON") ||
					blockCheck.getType() == Enum.valueOf(Material.class, "REPEATER") || blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_REDSTONE_COMPARATOR_ON") || blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_REDSTONE_COMPARATOR_OFF") ||
					blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_LEVER");
		}
		return false;
	}

	public static boolean isRedstone(Material matCheck)
	{
		if (HideRails.version == Version.v1_12) {
			return matCheck == Enum.valueOf(Material.class, "REDSTONE_WIRE") || matCheck == Enum.valueOf(Material.class, "REDSTONE") || matCheck == Enum.valueOf(Material.class, "REDSTONE_BLOCK") ||
					matCheck == Enum.valueOf(Material.class, "REDSTONE_COMPARATOR") || matCheck == Enum.valueOf(Material.class, "REDSTONE_COMPARATOR_OFF") || matCheck == Enum.valueOf(Material.class, "REDSTONE_COMPARATOR_ON") ||
					matCheck == Enum.valueOf(Material.class, "REDSTONE_TORCH_OFF") || matCheck == Enum.valueOf(Material.class, "REDSTONE_TORCH_ON") ||
					matCheck == Enum.valueOf(Material.class, "DIODE_BLOCK_OFF") || matCheck == Enum.valueOf(Material.class, "DIODE_BLOCK_ON") ||
					matCheck == Enum.valueOf(Material.class, "LEVER");
		} else if (HideRails.version == Version.v1_13) {
			return matCheck == Enum.valueOf(Material.class, "LEGACY_REDSTONE_WIRE") || matCheck == Enum.valueOf(Material.class, "LEGACY_REDSTONE") || matCheck == Enum.valueOf(Material.class, "LEGACY_REDSTONE_BLOCK") ||
					matCheck == Enum.valueOf(Material.class, "LEGACY_REDSTONE_TORCH_OFF") || matCheck == Enum.valueOf(Material.class, "LEGACY_REDSTONE_TORCH_ON") ||
					matCheck == Enum.valueOf(Material.class, "LEGACY_DIODE_BLOCK_OFF") || matCheck == Enum.valueOf(Material.class, "LEGACY_DIODE_BLOCK_ON") ||
					matCheck == Enum.valueOf(Material.class, "REPEATER") || matCheck == Enum.valueOf(Material.class, "LEGACY_REDSTONE_COMPARATOR_ON") || matCheck == Enum.valueOf(Material.class, "LEGACY_REDSTONE_COMPARATOR_OFF") ||
					matCheck == Enum.valueOf(Material.class, "LEGACY_LEVER");
		}
		return false;
	}

	public static boolean isSign(Block blockCheck)
	{
		if (HideRails.version == Version.v1_12) {
			return blockCheck.getType() == Enum.valueOf(Material.class, "SIGN") || blockCheck.getType() == Enum.valueOf(Material.class, "SIGN_POST") || blockCheck.getType() == Enum.valueOf(Material.class, "WALL_SIGN");
		} else if (HideRails.version == Version.v1_13) {
			return blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_SIGN_POST") || blockCheck.getType() == Enum.valueOf(Material.class, "LEGACY_WALL_SIGN");
		}
		return false;
	}

	public static boolean isSign(Material matCheck)
	{
		if (HideRails.version == Version.v1_12) {
			return matCheck == Enum.valueOf(Material.class, "SIGN") || matCheck == Enum.valueOf(Material.class, "SIGN_POST") || matCheck == Enum.valueOf(Material.class, "WALL_SIGN");
		} else if (HideRails.version == Version.v1_13) {
			return matCheck == Enum.valueOf(Material.class, "LEGACY_SIGN_POST") || matCheck == Enum.valueOf(Material.class, "LEGACY_WALL_SIGN");
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
			if(Checker.isInt(split[0]) && Checker.isInt(split[1])) {
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
			if (Checker.isInt(input)) {
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
