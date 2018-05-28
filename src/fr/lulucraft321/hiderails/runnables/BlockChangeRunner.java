/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.runnables;

import java.util.ArrayList;
import java.util.List;

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
		List<Player> players = new ArrayList<>();

		for (HiddenRailsWorld hWorld : HideRailsManager.rails)
		{
			World world = HideRails.getInstance().getServer().getWorld(hWorld.getWorldName());

			if (world != null)
			{
				players.clear();
				players.addAll(world.getPlayers());

				for (Player p : players)
				{
					if (p.getWorld().getName().equals(hWorld.getWorldName()))
					{
						// Si le joueur n'a pas desactive le masquage des blocks
						if (!HideRailsManager.isInPlayerWhoDisplayedBlocks(p))
						{
							for (HiddenRail rail : hWorld.getHiddenRails())
							{
								Location railLoc = rail.getLocation();

								if (railLoc.getWorld() != null)
								{
									MaterialData mats = new MaterialData(rail.getMaterial(), rail.getData());
									Block block = Bukkit.getServer().getWorld(rail.getLocation().getWorld().getName()).getBlockAt(railLoc);

									if (railLoc.getWorld().equals(p.getWorld()))
									{
										if (world.getChunkAt(railLoc).isLoaded())
										{
											if (railLoc.distance(p.getLocation()) <= FileConfigurationManager.viewDistance)
											{
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
