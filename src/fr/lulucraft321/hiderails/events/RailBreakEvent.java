/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.events;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.manager.HideRailsManager;
import fr.lulucraft321.hiderails.manager.MessagesManager;
import fr.lulucraft321.hiderails.utils.Checker;
import fr.lulucraft321.hiderails.utils.Messages;
import fr.lulucraft321.hiderails.utils.railsdata.HiddenRail;
import fr.lulucraft321.hiderails.utils.railsdata.HiddenRailsWorld;

public class RailBreakEvent implements Listener
{
	@EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBreak(BlockBreakEvent e)
	{
		Block b = e.getBlock();

		if(b == null) return;
		if(!Checker.isRail(b) && !Checker.isIronBar(b)) return;
		String worldName = b.getWorld().getName();
		if(!HideRails.getInstance().getHiddenRailsConfig().contains(HideRailsManager.path + "." + worldName)) return;
		if(HideRailsManager.getRailsToWorld(worldName) == null) return;

		HiddenRailsWorld world = HideRailsManager.getWorldHiddenRails(worldName);

		for(HiddenRail hRail : HideRailsManager.getRailsToWorld(worldName))
		{
			if(b.getLocation().equals(hRail.getLocation()))
			{
				world.getHiddenRails().remove(hRail);
				HideRailsManager.saveWorld(worldName);
				MessagesManager.sendPluginMessage(e.getPlayer(), Messages.SUCCESS_BREAK_RAIL);
				return;
			}
		}
	}
}
