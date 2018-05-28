/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.utils.checkers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.enums.BlockReplacementType;
import fr.lulucraft321.hiderails.enums.Messages;
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
		return (blockCheck.getType() == Material.RAILS) || 
				(blockCheck.getType() == Material.LADDER) || 
				(blockCheck.getType() == Material.ACTIVATOR_RAIL) || 
				(blockCheck.getType() == Material.DETECTOR_RAIL) || 
				(blockCheck.getType() == Material.POWERED_RAIL);
	}

	public static boolean isRail(Material matCheck)
	{
		return (matCheck == Material.RAILS) || (matCheck == Material.LADDER) || (matCheck == Material.ACTIVATOR_RAIL) || (matCheck == Material.DETECTOR_RAIL) || (matCheck == Material.POWERED_RAIL);
	}

	public static boolean isIronBar(Block blockCheck)
	{
		return blockCheck.getType() == Material.IRON_FENCE;
	}

	public static boolean isIronBar(Material matCheck)
	{
		return matCheck == Material.IRON_FENCE;
	}

	public static boolean isCommandBlock(Block blockCheck)
	{
		return (blockCheck.getType() == Material.COMMAND) || (blockCheck.getType() == Material.COMMAND_CHAIN) || (blockCheck.getType() == Material.COMMAND_REPEATING);
	}

	public static boolean isCommandBlock(Material matCheck)
	{
		return (matCheck == Material.COMMAND) || (matCheck == Material.COMMAND_CHAIN) || (matCheck == Material.COMMAND_REPEATING);
	}

	public static boolean isRedstone(Block blockCheck)
	{
		return (blockCheck.getType() == Material.REDSTONE_WIRE) || (blockCheck.getType() == Material.REDSTONE) || (blockCheck.getType() == Material.REDSTONE_BLOCK) || 
				(blockCheck.getType() == Material.REDSTONE_COMPARATOR) || (blockCheck.getType() == Material.REDSTONE_COMPARATOR_OFF) || (blockCheck.getType() == Material.REDSTONE_COMPARATOR_ON) || 
				(blockCheck.getType() == Material.REDSTONE_TORCH_OFF) || (blockCheck.getType() == Material.REDSTONE_TORCH_ON) || 
				(blockCheck.getType() == Material.DIODE) || (blockCheck.getType() == Material.DIODE_BLOCK_OFF) || (blockCheck.getType() == Material.DIODE_BLOCK_ON) || 
				(blockCheck.getType() == Material.LEVER);
	}

	public static boolean isRedstone(Material matCheck)
	{
		return (matCheck == Material.REDSTONE_WIRE) || (matCheck == Material.REDSTONE) || (matCheck == Material.REDSTONE_BLOCK) || 
				(matCheck == Material.REDSTONE_COMPARATOR) || (matCheck == Material.REDSTONE_COMPARATOR_OFF) || (matCheck == Material.REDSTONE_COMPARATOR_ON) || 
				(matCheck == Material.REDSTONE_TORCH_OFF) || (matCheck == Material.REDSTONE_TORCH_ON) || 
				(matCheck == Material.DIODE) || (matCheck == Material.DIODE_BLOCK_OFF) || (matCheck == Material.DIODE_BLOCK_ON) || 
				(matCheck == Material.LEVER);
	}

	public static boolean isSign(Block blockCheck)
	{
		return (blockCheck.getType() == Material.SIGN) || (blockCheck.getType() == Material.SIGN_POST) || (blockCheck.getType() == Material.WALL_SIGN);
	}

	public static boolean isSign(Material matCheck)
	{
		return (matCheck == Material.SIGN) || (matCheck == Material.SIGN_POST) || (matCheck == Material.WALL_SIGN);
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


	@SuppressWarnings("deprecation")
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
				mat = Material.getMaterial(Integer.parseInt(split[0]));
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
			if(Checker.isInt(input)) {
				mat = Material.getMaterial(Integer.parseInt(input));
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
