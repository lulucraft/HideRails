/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.listeners;

import static fr.lulucraft321.hiderails.utils.checkers.JavaChecker.enumCheck;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPhysicsEvent;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.enums.Version;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractListener;

public class BlockPhysicListener extends AbstractListener
{
	@EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPhysic(BlockPhysicsEvent e) {
		Block b = e.getBlock();
		Material bT = b.getType();
		if (HideRails.version == Version.v1_12) {
			if (bT == enumCheck("RAILS") || bT == enumCheck("LADDER")
					|| bT == enumCheck("ACTIVATOR_RAIL") || bT == enumCheck("DETECTOR_RAIL") || bT == enumCheck("POWERED_RAIL"))
				if (e.getBlock().getRelative(BlockFace.DOWN).getType() == enumCheck("ANVIL"))
					e.setCancelled(true);
		} else if (HideRails.version == Version.v1_13 || HideRails.version == Version.v1_14) {
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
