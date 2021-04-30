/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.listeners;

import static fr.nepta.hiderails.utils.JavaChecker.enumCheck;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPhysicsEvent;

import fr.nepta.hiderails.HideRails;
import fr.nepta.hiderails.enums.Version;

public class BlockPhysicListener extends Listener
{
	@EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPhysic(BlockPhysicsEvent e) {
		Block b = e.getBlock();
		Material bT = b.getType();
		if (HideRails.version == Version.V1_12) {
			if (bT == enumCheck("RAILS") || bT == enumCheck("LADDER")
					|| bT == enumCheck("ACTIVATOR_RAIL") || bT == enumCheck("DETECTOR_RAIL") || bT == enumCheck("POWERED_RAIL"))
				if (e.getBlock().getRelative(BlockFace.DOWN).getType() == enumCheck("ANVIL"))
					e.setCancelled(true);
		} else if (HideRails.version == Version.V1_13 || HideRails.version == Version.V1_14 || HideRails.version == Version.V1_15) {
			if (bT == enumCheck("LEGACY_RAILS") || bT == enumCheck("LEGACY_LADDER")
					|| bT == enumCheck("LEGACY_ACTIVATOR_RAIL") || bT == enumCheck("LEGACY_DETECTOR_RAIL") || bT == enumCheck("LEGACY_POWERED_RAIL"))
				if (e.getBlock().getRelative(BlockFace.DOWN).getType() == enumCheck("LEGACY_ANVIL"))
					e.setCancelled(true);
		}

		/*
		 * Disable breaking block around hidden rail if player break hiddenrail
		 */
		if (BreakBlockListener.trashList.contains(b)) {
			e.setCancelled(true);
			BreakBlockListener.trashList.remove(b);
		}
	}
}
