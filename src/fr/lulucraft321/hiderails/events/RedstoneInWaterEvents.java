package fr.lulucraft321.hiderails.events;

import java.util.EnumSet;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import fr.lulucraft321.hiderails.HideRails;

public class RedstoneInWaterEvents implements Listener
{
	public static String path = "redstoneWaterProtection";

	private EnumSet<Material> redstoneBlocks = EnumSet.of(Material.REDSTONE_WIRE, new Material[] {
			Material.REDSTONE_TORCH_ON,
			Material.REDSTONE_TORCH_OFF,
			Material.DIODE_BLOCK_OFF,
			Material.DIODE_BLOCK_ON,
			Material.LEVER,
			Material.STONE_BUTTON,
			Material.WOOD_BUTTON,
			Material.RAILS,
			Material.ACTIVATOR_RAIL,
			Material.POWERED_RAIL,
			Material.DETECTOR_RAIL,
			Material.SIGN,
			Material.SIGN_POST,
			Material.REDSTONE_BLOCK,
			Material.REDSTONE_COMPARATOR_ON,
			Material.REDSTONE_COMPARATOR_OFF
	});

	@EventHandler
	public void onBlockFrom(BlockFromToEvent e)
	{
		if(HideRails.getInstance().getConfig().getBoolean(path + "." + e.getBlock().getLocation().getWorld().getName()) == true || (!HideRails.getInstance().getConfig().contains(path + "." + e.getBlock().getLocation().getWorld().getName()) && HideRails.getInstance().getConfig().getBoolean(path + "._all_") == true))
		{
			if(this.redstoneBlocks.contains(e.getToBlock().getType())) {
				e.setCancelled(true);
			}
		}
	}
}
