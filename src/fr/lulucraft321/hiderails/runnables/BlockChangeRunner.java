/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.runnables;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.manager.HideRailsManager;
import fr.lulucraft321.hiderails.reflection.BukkitNMS;
import fr.lulucraft321.hiderails.utils.MaterialData;
import fr.lulucraft321.hiderails.utils.railsdata.HiddenRail;
import fr.lulucraft321.hiderails.utils.railsdata.HiddenRailsWorld;

public class BlockChangeRunner extends BukkitRunnable
{
	public void run()
	{
		for (HiddenRailsWorld hWorld : HideRailsManager.rails)
		{
			World world = HideRails.getInstance().getServer().getWorld(hWorld.getWorldName());

			if (world != null)
			{
				for (HiddenRail rail : hWorld.getHiddenRails())
				{
					MaterialData mats = new MaterialData(rail.getMaterial(), rail.getData());
					List<Player> players = world.getPlayers();

					for (Player p : players)
					{
						// Si le joueur n'a pas desactive le masquage des blocks
						if (!HideRailsManager.isInPlayerWhoDisplayedBlocks(p))
						{
							Location railLoc = rail.getLocation();
							Block b = Bukkit.getServer().getWorld(rail.getLocation().getWorld().getName()).getBlockAt(railLoc);
							Material blockType = b.getType();

							// Si le masquage des barreaux est active -> continue sinon return
							if (blockType == Material.IRON_FENCE) {
								if (HideRailsManager.hb) {
									BukkitNMS.changeBlock(p, mats.getMat(), mats.getData(), railLoc.getBlockX(), railLoc.getBlockY(), railLoc.getBlockZ());
								}
							}
							// Si le masquage des rails est active -> continue sinon return
							else if (blockType == Material.RAILS || blockType == Material.ACTIVATOR_RAIL || blockType == Material.DETECTOR_RAIL || blockType == Material.POWERED_RAIL || blockType == Material.LADDER) {
								if (HideRailsManager.hr) {
									BukkitNMS.changeBlock(p, mats.getMat(), mats.getData(), railLoc.getBlockX(), railLoc.getBlockY(), railLoc.getBlockZ());
								}
							}
							// Si le masquage des commandBlock est active -> continue sinon return
							else if (blockType == Material.COMMAND || blockType == Material.COMMAND_CHAIN || blockType == Material.COMMAND_REPEATING) {
								if (HideRailsManager.hc) {
									BukkitNMS.changeBlock(p, mats.getMat(), mats.getData(), railLoc.getBlockX(), railLoc.getBlockY(), railLoc.getBlockZ());
								}
							}
							// Si le masquage de la redstone est active -> continue sinon return
							else if (blockType == Material.REDSTONE || blockType == Material.REDSTONE_WIRE || blockType == Material.REDSTONE_BLOCK || blockType == Material.REDSTONE_COMPARATOR
									|| blockType == Material.REDSTONE_COMPARATOR_OFF || blockType == Material.REDSTONE_COMPARATOR_ON || blockType == Material.REDSTONE_TORCH_OFF
									|| blockType == Material.REDSTONE_TORCH_ON || blockType == Material.DIODE || blockType == Material.DIODE_BLOCK_OFF || blockType == Material.DIODE_BLOCK_ON) {
								if (HideRailsManager.hd) {
									BukkitNMS.changeBlock(p, mats.getMat(), mats.getData(), railLoc.getBlockX(), railLoc.getBlockY(), railLoc.getBlockZ());
								}
							}
							// Si le masquage des panneaux est active -> continue sinon return
							else if (blockType == Material.SIGN || blockType == Material.SIGN_POST || blockType == Material.WALL_SIGN) {
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
