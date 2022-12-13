/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.runnables;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.nepta.hiderails.HideRails;
import fr.nepta.hiderails.managers.FileConfigurationManager;
import fr.nepta.hiderails.managers.HideRailsManager;
import fr.nepta.hiderails.models.MaterialData;
import fr.nepta.hiderails.models.railsdata.HiddenRail;
import fr.nepta.hiderails.models.railsdata.HiddenRailsWorld;
import fr.nepta.hiderails.nms.BukkitNMS;
import fr.nepta.hiderails.utils.BlocksChecker;

public class BlockChangeRunner extends BukkitRunnable
{
	@Override
	public void run()
	{
		for (HiddenRailsWorld hWorld : HideRailsManager.rails)
		{
			String hWorldName = hWorld.getWorldName();
			World world = HideRails.getInstance().getServer().getWorld(hWorldName);

			if (world == null) {
				continue;
			}

			for (Player p : world.getPlayers())
			{
				World pWorld = p.getWorld();

				if (!pWorld.getName().equals(hWorldName) || HideRailsManager.isInPlayerWhoDisplayedBlocks(p)) {
					// If the world where the player is, doesn't match with the one of HiddenRailsWorld
					// or If the player has disabled the blocks hiding
					continue;
				}

				for (HiddenRail rail : hWorld.getHiddenRails())
				{
					Location railLoc = rail.getLocation();
					World railWorld = railLoc.getWorld();

					if (railWorld == null || !railWorld.equals(pWorld)) {
						// If the world where the player is, doesn't match with the one of hidden rail
						continue;
					}

					if (world.getChunkAt(railLoc).isLoaded())
					{
						if (railLoc.distance(p.getLocation()) <= FileConfigurationManager.viewDistance)
						{
							MaterialData mats = new MaterialData(rail.getMaterial(), rail.getData());
							Block block = Bukkit.getServer().getWorld(railWorld.getName()).getBlockAt(railLoc);

							// If the iron bars hiding is enabled
							if (BlocksChecker.isIronBar(block)) {
								if (HideRailsManager.hb) {
									BukkitNMS.changeBlock(p, mats.getMat(), mats.getData(), railLoc.getBlockX(), railLoc.getBlockY(), railLoc.getBlockZ());
								}
							}
							// If the rails hiding is enabled
							else if (BlocksChecker.isRail(block)) {
								if (HideRailsManager.hr) {
									BukkitNMS.changeBlock(p, mats.getMat(), mats.getData(), railLoc.getBlockX(), railLoc.getBlockY(), railLoc.getBlockZ());
								}
							}
							// If the command blocks hiding is enabled
							else if (BlocksChecker.isCommandBlock(block)) {
								if (HideRailsManager.hc) {
									BukkitNMS.changeBlock(p, mats.getMat(), mats.getData(), railLoc.getBlockX(), railLoc.getBlockY(), railLoc.getBlockZ());
								}
							}
							// If the redstone hiding is enabled
							else if (BlocksChecker.isRedstone(block)) {
								if (HideRailsManager.hd) {
									BukkitNMS.changeBlock(p, mats.getMat(), mats.getData(), railLoc.getBlockX(), railLoc.getBlockY(), railLoc.getBlockZ());
								}
							}
							// If the signs hiding is enabled
							else if (BlocksChecker.isSign(block)) {
								if (HideRailsManager.hs) {
									BukkitNMS.changeBlock(p, mats.getMat(), mats.getData(), railLoc.getBlockX(), railLoc.getBlockY(), railLoc.getBlockZ());
								}
							}
						}
					}
				}
			}
		}
	}
}
