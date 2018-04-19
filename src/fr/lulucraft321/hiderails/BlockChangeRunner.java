/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.lulucraft321.hiderails.manager.HideRailsManager;
import fr.lulucraft321.hiderails.reflection.BukkitNMS;
import fr.lulucraft321.hiderails.utils.HiddenRail;
import fr.lulucraft321.hiderails.utils.HiddenRailsWorld;
import fr.lulucraft321.hiderails.utils.MaterialData;

public class BlockChangeRunner extends BukkitRunnable
{
	public void run()
	{
		for(HiddenRailsWorld hWorld : HideRailsManager.rails)
		{
			World world = HideRails.getInstance().getServer().getWorld(hWorld.getWorldName());

			if(world != null)
			{
				for(HiddenRail rail : hWorld.getHiddenRails())
				{
					MaterialData mats = new MaterialData(rail.getMaterial(), rail.getData());
					List<Player> players = world.getPlayers();

					for(Player p : players)
					{
						Location railLoc = rail.getLocation();
						Block b = Bukkit.getServer().getWorld(rail.getLocation().getWorld().getName()).getBlockAt(railLoc);

						// Si le masquage des barreaux est active -> continue sinon return
						if(b.getType() == Material.IRON_FENCE) {
							if(HideRailsManager.hb) {
								BukkitNMS.changeBlock(p, mats.getMat(), mats.getData(), railLoc.getBlockX(), railLoc.getBlockY(), railLoc.getBlockZ());
							}
						}
						// Si le masquage des rails est active -> continue sinon return
						else if(b.getType() == Material.RAILS || b.getType() == Material.ACTIVATOR_RAIL || b.getType() == Material.DETECTOR_RAIL || b.getType() == Material.POWERED_RAIL) {
							if(HideRailsManager.hr) {
								BukkitNMS.changeBlock(p, mats.getMat(), mats.getData(), railLoc.getBlockX(), railLoc.getBlockY(), railLoc.getBlockZ());
							}
						}
					}
				}
			}
		}
	}
}
