/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.listeners;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPhysicsEvent;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractEvent;

public class BlockPhysicEvent extends AbstractEvent
{
	@EventHandler
	public void onBlockPhysic(BlockPhysicsEvent e) {
		Material bT = e.getBlock().getType();
		if (HideRails.version == "1.12") {
			if (bT == Enum.valueOf(Material.class, "RAILS") || bT == Enum.valueOf(Material.class, "LADDER")
					|| bT == Enum.valueOf(Material.class, "ACTIVATOR_RAIL") || bT == Enum.valueOf(Material.class, "DETECTOR_RAIL") || bT == Enum.valueOf(Material.class, "POWERED_RAIL"))
				if (e.getBlock().getRelative(BlockFace.DOWN).getType() == Enum.valueOf(Material.class, "ANVIL"))
					e.setCancelled(true);
		} else if (HideRails.version == "1.13") {
			if (bT == Enum.valueOf(Material.class, "LEGACY_RAILS") || bT == Enum.valueOf(Material.class, "LEGACY_LADDER")
					|| bT == Enum.valueOf(Material.class, "LEGACY_ACTIVATOR_RAIL") || bT == Enum.valueOf(Material.class, "LEGACY_DETECTOR_RAIL") || bT == Enum.valueOf(Material.class, "LEGACY_POWERED_RAIL"))
				if (e.getBlock().getRelative(BlockFace.DOWN).getType() == Enum.valueOf(Material.class, "LEGACY_ANVIL"))
					e.setCancelled(true);
		}
	}
}
