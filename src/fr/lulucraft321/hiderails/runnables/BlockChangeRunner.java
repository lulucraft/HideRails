/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.runnables;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.managers.FileConfigurationManager;
import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.reflection.BukkitNMS;
import fr.lulucraft321.hiderails.utils.checkers.BlocksChecker;
import fr.lulucraft321.hiderails.utils.data.MaterialData;
import fr.lulucraft321.hiderails.utils.data.railsdata.HiddenRail;
import fr.lulucraft321.hiderails.utils.data.railsdata.HiddenRailsWorld;

public class BlockChangeRunner extends BukkitRunnable
{
	@Override
	public void run()
	{
		for (HiddenRailsWorld hWorld : HideRailsManager.rails)
		{
			String hWorldName = hWorld.getWorldName();
			World world = HideRails.getInstance().getServer().getWorld(hWorldName);

			if (world != null)
			{
				for (Player p : world.getPlayers())
				{
					World pWorld = p.getWorld();

					if (pWorld.getName().equals(hWorldName))
					{
						// Si le joueur n'a pas desactive le masquage des blocks
						if (!HideRailsManager.isInPlayerWhoDisplayedBlocks(p))
						{
							for (HiddenRail rail : hWorld.getHiddenRails())
							{
								Location railLoc = rail.getLocation();
								World railWorld = railLoc.getWorld();

								if (railWorld != null)
								{
									if (railWorld.equals(pWorld))
									{
										if (world.getChunkAt(railLoc).isLoaded())
										{
											if (railLoc.distance(p.getLocation()) <= FileConfigurationManager.viewDistance)
											{
												MaterialData mats = new MaterialData(rail.getMaterial(), rail.getData());
												Block block = Bukkit.getServer().getWorld(railWorld.getName()).getBlockAt(railLoc);

												// Si le masquage des barreaux est active -> continue sinon return
												if (BlocksChecker.isIronBar(block)) {
													if (HideRailsManager.hb) {
														BukkitNMS.changeBlock(p, mats.getMat(), mats.getData(), railLoc.getBlockX(), railLoc.getBlockY(), railLoc.getBlockZ());
													}
												}
												// Si le masquage des rails est active -> continue sinon return
												else if (BlocksChecker.isRail(block)) {
													if (HideRailsManager.hr) {
														BukkitNMS.changeBlock(p, mats.getMat(), mats.getData(), railLoc.getBlockX(), railLoc.getBlockY(), railLoc.getBlockZ());
													}
												}
												// Si le masquage des commandBlock est active -> continue sinon return
												else if (BlocksChecker.isCommandBlock(block)) {
													if (HideRailsManager.hc) {
														BukkitNMS.changeBlock(p, mats.getMat(), mats.getData(), railLoc.getBlockX(), railLoc.getBlockY(), railLoc.getBlockZ());
													}
												}
												// Si le masquage de la redstone est active -> continue sinon return
												else if (BlocksChecker.isRedstone(block)) {
													if (HideRailsManager.hd) {
														BukkitNMS.changeBlock(p, mats.getMat(), mats.getData(), railLoc.getBlockX(), railLoc.getBlockY(), railLoc.getBlockZ());
													}
												}
												// Si le masquage des panneaux est active -> continue sinon return
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
				}
			}
		}
	}
}
