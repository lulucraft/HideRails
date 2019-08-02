package fr.lulucraft321.hiderails.listeners;

import static fr.lulucraft321.hiderails.utils.checkers.JavaChecker.enumCheck;

import java.util.EnumSet;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFromToEvent;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.enums.Version;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractListener;

public class RedstoneInWaterListeners extends AbstractListener
{
	public static String path = "redstoneWaterProtection";

	private static EnumSet<Material> redstoneBlocks = null;

	static {
		if (HideRails.version == Version.v1_12) {
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
		else if (HideRails.version == Version.v1_13 || HideRails.version == Version.v1_14) {
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
		}
	}

	@EventHandler
	public void onBlockFrom(BlockFromToEvent e)
	{
		World w = e.getBlock().getLocation().getWorld();
		FileConfiguration c = HideRails.getInstance().getConfig();

		if(c.getBoolean(path + "." + w.getName()) == true || (!c.contains(path + "." + w.getName()) && c.getBoolean(path + "._all_") == true)) {
			if(redstoneBlocks.contains(e.getToBlock().getType())) {
				e.setCancelled(true);
			}
		}
	}
}
