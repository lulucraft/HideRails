/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.runnables;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.lulucraft321.hiderails.enums.ParticleName;
import fr.lulucraft321.hiderails.managers.HideRailsManager;
import fr.lulucraft321.hiderails.reflection.BukkitNMS;
import fr.lulucraft321.hiderails.utils.railsdata.HiddenRail;
import fr.lulucraft321.hiderails.utils.railsdata.HiddenRailsWorld;

public class PlayerDisplayBlocks extends BukkitRunnable
{
	public static boolean run;

	@Override
	public void run()
	{
		for (Player p : HideRailsManager.getPlayersWhoDisplayedBlocks())
		{
			if (HideRailsManager.getPlayersWhoDisplayedBlocks().size() < 1) {
				this.cancel();
				return;
			}

			for (HiddenRailsWorld hWorld : HideRailsManager.rails)
			{
				for (HiddenRail rail : hWorld.getHiddenRails())
				{
					Location loc = rail.getLocation();
					Location loc1 = loc.add(0, 0.3, 0);
					try {
						BukkitNMS.summonParticle(p, loc1, ParticleName.VILLAGER_ANGRY, 1, 1);
						loc1.subtract(0, 0.3, 0);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
