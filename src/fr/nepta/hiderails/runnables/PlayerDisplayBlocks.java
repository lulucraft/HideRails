/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.runnables;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.nepta.hiderails.HideRails;
import fr.nepta.hiderails.enums.Version;
import fr.nepta.hiderails.enums.particles.ParticleName_v1_12;
import fr.nepta.hiderails.enums.particles.ParticleName_v1_13;
import fr.nepta.hiderails.enums.particles.ParticleName_v1_15;
import fr.nepta.hiderails.managers.HideRailsManager;
import fr.nepta.hiderails.models.railsdata.HiddenRail;
import fr.nepta.hiderails.models.railsdata.HiddenRailsWorld;
import fr.nepta.hiderails.nms.BukkitNMS;

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
				Iterator<HiddenRail> it = hWorld.getHiddenRails().iterator();
				while (it.hasNext())
				{
					HiddenRail rail = it.next();
					Location railLoc = rail.getLocation();

					if (!pW.equals(railLoc.getWorld())) {
						continue;
					}

					if (pLoc.distance(railLoc) < 100)
					{
						Location loc = railLoc;

						// Remove deleted HiddenRails from selection (//set 0)
						Block block = Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(loc);
						if (block != null && block.getType() == Material.AIR) {
							it.remove();
						}

						try {
							Location loc1 = loc.add(0, 0.3, 0);
							if (HideRails.version == Version.V1_12) BukkitNMS.summonParticle(p, loc1, ParticleName_v1_12.VILLAGER_ANGRY, 1, 1);
							else if (HideRails.version == Version.V1_13 || HideRails.version == Version.V1_14) BukkitNMS.summonParticle(p, loc1, ParticleName_v1_13.VILLAGER_ANGRY, 1, 1);
							else if (HideRails.version == Version.V1_15 || HideRails.version == Version.V1_17) BukkitNMS.summonParticle(p, loc1, ParticleName_v1_15.VILLAGER_ANGRY, 1, 1);
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
