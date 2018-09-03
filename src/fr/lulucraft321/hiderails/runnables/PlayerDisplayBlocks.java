/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.runnables;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.enums.ParticleName_v1_12;
import fr.lulucraft321.hiderails.enums.ParticleName_v1_13;
import fr.lulucraft321.hiderails.enums.Version;
import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.reflection.BukkitNMS;
import fr.lulucraft321.hiderails.utils.data.railsdata.HiddenRail;
import fr.lulucraft321.hiderails.utils.data.railsdata.HiddenRailsWorld;

public class PlayerDisplayBlocks extends BukkitRunnable
{
	public static boolean run;
	public static int taskId;

	@Override
	public void run()
	{
		if (HideRailsManager.getPlayersWhoDisplayedBlocks().size() < 1) {
			this.cancel();
			return;
		}

		for (Player p : HideRailsManager.getPlayersWhoDisplayedBlocks())
		{
			Location pLoc = p.getLocation();
			World pW = pLoc.getWorld();
			for (HiddenRailsWorld hWorld : HideRailsManager.rails)
			{
				final Iterator<HiddenRail> it = hWorld.getHiddenRails().iterator();
				while (it.hasNext())
				{
					HiddenRail rail = it.next();
					Location railLoc = rail.getLocation();

					if (pW.equals(railLoc.getWorld()))
					{
						if (pLoc.distance(railLoc) < 100)
						{
							Location loc = railLoc;

							// Remove deleted HiddenRails with worldedit (//set 0)
							Block block = Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(loc);
							if (block != null && block.getType() == Material.AIR) {
								it.remove();
							}

							try {
								Location loc1 = loc.add(0, 0.3, 0);
								if (HideRails.version == Version.v1_12) BukkitNMS.summonParticle(p, loc1, ParticleName_v1_12.VILLAGER_ANGRY, 1, 1);
								else if (HideRails.version == Version.v1_13) BukkitNMS.summonParticle(p, loc1, ParticleName_v1_13.VILLAGER_ANGRY, 1, 1);
								loc1.subtract(0, 0.3, 0);
							} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
}
