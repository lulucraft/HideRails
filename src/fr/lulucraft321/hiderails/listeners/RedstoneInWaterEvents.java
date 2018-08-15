package fr.lulucraft321.hiderails.listeners;

import java.util.EnumSet;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFromToEvent;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.enums.Version;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractEvent;

public class RedstoneInWaterEvents extends AbstractEvent
{
	public static String path = "redstoneWaterProtection";

	private static EnumSet<Material> redstoneBlocks = null;

	static {
		if (HideRails.version == Version.v1_12) {
			redstoneBlocks = EnumSet.of(Enum.valueOf(Material.class, "REDSTONE_WIRE"), new Material[] {
					Enum.valueOf(Material.class, "REDSTONE_TORCH_ON"),
					Enum.valueOf(Material.class, "REDSTONE_TORCH_OFF"),
					Enum.valueOf(Material.class, "DIODE_BLOCK_ON"),
					Enum.valueOf(Material.class, "DIODE_BLOCK_OFF"),
					Enum.valueOf(Material.class, "LEVER"),
					Enum.valueOf(Material.class, "STONE_BUTTON"),
					Enum.valueOf(Material.class, "WOOD_BUTTON"),
					Enum.valueOf(Material.class, "RAILS"),
					Enum.valueOf(Material.class, "ACTIVATOR_RAIL"),
					Enum.valueOf(Material.class, "POWERED_RAIL"),
					Enum.valueOf(Material.class, "DETECTOR_RAIL"),
					Enum.valueOf(Material.class, "SIGN"),
					Enum.valueOf(Material.class, "SIGN_POST"),
					Enum.valueOf(Material.class, "REDSTONE_COMPARATOR_ON"),
					Enum.valueOf(Material.class, "REDSTONE_COMPARATOR_OFF")
			});
		}
		else if (HideRails.version == Version.v1_13) {
			redstoneBlocks = EnumSet.of(Enum.valueOf(Material.class, "LEGACY_REDSTONE_WIRE"), new Material[] {
					Enum.valueOf(Material.class, "LEGACY_REDSTONE_TORCH_ON"),
					Enum.valueOf(Material.class, "LEGACY_REDSTONE_TORCH_OFF"),
					Enum.valueOf(Material.class, "LEGACY_DIODE_BLOCK_ON"),
					Enum.valueOf(Material.class, "LEGACY_DIODE_BLOCK_OFF"),
					Enum.valueOf(Material.class, "LEGACY_LEVER"),
					Enum.valueOf(Material.class, "LEGACY_STONE_BUTTON"),
					Enum.valueOf(Material.class, "LEGACY_WOOD_BUTTON"),
					Enum.valueOf(Material.class, "LEGACY_ACTIVATOR_RAIL"),
					Enum.valueOf(Material.class, "LEGACY_POWERED_RAIL"),
					Enum.valueOf(Material.class, "LEGACY_DETECTOR_RAIL"),
					Enum.valueOf(Material.class, "LEGACY_SIGN_POST"),
					Enum.valueOf(Material.class, "LEGACY_WALL_SIGN"),
					Enum.valueOf(Material.class, "LEGACY_REDSTONE_COMPARATOR_ON"),
					Enum.valueOf(Material.class, "LEGACY_REDSTONE_COMPARATOR_OFF")
			});
		}
	}

	@EventHandler
	public void onBlockFrom(BlockFromToEvent e)
	{
		if(HideRails.getInstance().getConfig().getBoolean(path + "." + e.getBlock().getLocation().getWorld().getName()) == true || (!HideRails.getInstance().getConfig().contains(path + "." + e.getBlock().getLocation().getWorld().getName()) && HideRails.getInstance().getConfig().getBoolean(path + "._all_") == true))
		{
			if(redstoneBlocks.contains(e.getToBlock().getType())) {
				e.setCancelled(true);
			}
		}
	}
}
