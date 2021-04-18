package fr.nepta.hiderails.listeners;

import static fr.nepta.hiderails.utils.JavaChecker.enumCheck;

import java.util.EnumSet;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockFromToEvent;

import fr.nepta.hiderails.HideRails;
import fr.nepta.hiderails.configurations.commentedconfig.Configuration;
import fr.nepta.hiderails.enums.Version;
import fr.nepta.hiderails.managers.FileConfigurationManager;

public class RedstoneInWaterListeners extends Listener
{
	public static final String PATH_POINT = "redstoneWaterProtection" + ".";

	private static EnumSet<Material> redstoneBlocks = null;

	static {
		/*
		 * 1.12
		 */
		if (HideRails.version == Version.V1_12) {
			redstoneBlocks = EnumSet.of(enumCheck("REDSTONE_WIRE"), new Material[] {
					enumCheck("REDSTONE_TORCH_ON"),
					enumCheck("REDSTONE_TORCH_OFF"),
					enumCheck("DIODE_BLOCK_ON"),
					enumCheck("DIODE_BLOCK_OFF"),
					enumCheck("LEVER"),
					enumCheck("STONE_BUTTON"),
					enumCheck("WOOD_BUTTON"),
					enumCheck("RAILS"),
					enumCheck("ACTIVATOR_RAIL"),
					enumCheck("POWERED_RAIL"),
					enumCheck("DETECTOR_RAIL"),
					enumCheck("SIGN"),
					enumCheck("SIGN_POST"),
					enumCheck("REDSTONE_COMPARATOR_ON"),
					enumCheck("REDSTONE_COMPARATOR_OFF")
			});
		}
		/*
		 * 1.13 / 1.14
		 */
		else if (HideRails.version == Version.V1_13 || HideRails.version == Version.V1_14 || HideRails.version == Version.V1_15) {
			redstoneBlocks = EnumSet.of(enumCheck("LEGACY_REDSTONE_WIRE"), new Material[] {
					enumCheck("LEGACY_REDSTONE_TORCH_ON"),
					enumCheck("LEGACY_REDSTONE_TORCH_OFF"),
					enumCheck("LEGACY_DIODE_BLOCK_ON"),
					enumCheck("LEGACY_DIODE_BLOCK_OFF"),
					enumCheck("LEGACY_LEVER"),
					enumCheck("LEGACY_STONE_BUTTON"),
					enumCheck("LEGACY_WOOD_BUTTON"),
					enumCheck("LEGACY_RAILS"),
					enumCheck("LEGACY_ACTIVATOR_RAIL"),
					enumCheck("LEGACY_POWERED_RAIL"),
					enumCheck("LEGACY_DETECTOR_RAIL"),
					enumCheck("LEGACY_SIGN_POST"),
					enumCheck("LEGACY_WALL_SIGN"),
					enumCheck("LEGACY_REDSTONE_COMPARATOR_ON"),
					enumCheck("LEGACY_REDSTONE_COMPARATOR_OFF")
			});

			EnumSet<Material> enumSet = null;
			// api-version 1.13
			if (HideRails.version == Version.V1_13) {
				enumSet = EnumSet.of(enumCheck("REDSTONE_TORCH"), new Material[] {
						enumCheck("REDSTONE_WALL_TORCH"),
						enumCheck("REDSTONE"),
						enumCheck("REDSTONE_WIRE"),
						enumCheck("COMPARATOR"),
						enumCheck("REPEATER"),
						enumCheck("LEVER"),
						enumCheck("STONE_BUTTON"),
						enumCheck("OAK_BUTTON"),
						enumCheck("SPRUCE_BUTTON"),
						enumCheck("BIRCH_BUTTON"),
						enumCheck("JUNGLE_BUTTON"),
						enumCheck("ACACIA_BUTTON"),
						enumCheck("DARK_OAK_BUTTON"),
						enumCheck("RAIL"),
						enumCheck("ACTIVATOR_RAIL"),
						enumCheck("POWERED_RAIL"),
						enumCheck("DETECTOR_RAIL"),
						enumCheck("SIGN"),
						enumCheck("WALL_SIGN")
				});
			}
			// api-version 1.14
			else if (HideRails.version == Version.V1_14 || HideRails.version == Version.V1_15) {
				enumSet = EnumSet.of(enumCheck("REDSTONE_TORCH"), new Material[] {
						enumCheck("REDSTONE_WALL_TORCH"),
						enumCheck("REDSTONE"),
						enumCheck("REDSTONE_WIRE"),
						enumCheck("COMPARATOR"),
						enumCheck("REPEATER"),
						enumCheck("LEVER"),
						enumCheck("STONE_BUTTON"),
						enumCheck("OAK_BUTTON"),
						enumCheck("SPRUCE_BUTTON"),
						enumCheck("BIRCH_BUTTON"),
						enumCheck("JUNGLE_BUTTON"),
						enumCheck("ACACIA_BUTTON"),
						enumCheck("DARK_OAK_BUTTON"),
						enumCheck("RAIL"),
						enumCheck("ACTIVATOR_RAIL"),
						enumCheck("POWERED_RAIL"),
						enumCheck("DETECTOR_RAIL"),
						enumCheck("OAK_SIGN"),
						enumCheck("OAK_WALL_SIGN"),
						enumCheck("SPRUCE_SIGN"),
						enumCheck("SPRUCE_WALL_SIGN"),
						enumCheck("ACACIA_SIGN"),
						enumCheck("ACACIA_WALL_SIGN"),
						enumCheck("BIRCH_SIGN"),
						enumCheck("BIRCH_WALL_SIGN"),
						enumCheck("DARK_OAK_SIGN"),
						enumCheck("DARK_OAK_WALL_SIGN"),
						enumCheck("JUNGLE_SIGN"),
						enumCheck("JUNGLE_WALL_SIGN"),
				});
			}

			for (Material mat : enumSet)
				redstoneBlocks.add(mat);
		}
	}

	@EventHandler (priority = EventPriority.HIGH)
	public void onBlockFrom(BlockFromToEvent e)
	{
		World w = e.getBlock().getLocation().getWorld();
		Configuration c = FileConfigurationManager.getConfig();
		String PATH_WORLD = PATH_POINT + w.getName();

		if((!c.contains(PATH_WORLD) && c.getBoolean(PATH_POINT + "_all_") == true) || (c.contains(PATH_WORLD) && c.getBoolean(PATH_WORLD) == true)) {
			if(redstoneBlocks.contains(e.getToBlock().getType())) {
				e.setCancelled(true);
			}
		}
	}
}
