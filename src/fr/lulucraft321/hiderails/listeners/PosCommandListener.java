/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.lulucraft321.hiderails.managers.PlayerClaimDataManager;
import fr.lulucraft321.hiderails.managers.PlayerClaimDataManager.LocType;
import fr.lulucraft321.hiderails.utils.abstractclass.AbstractListener;

public class PosCommandListener extends AbstractListener
{
	/*
	 * HideRails selection system (detect worldedit command)
	 */
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e)
	{
		String[] args = e.getMessage().split(" ");

		if (args.length < 2) {
			final Player p = e.getPlayer();

			if (args[0].equalsIgnoreCase("//pos1"))
				PlayerClaimDataManager.setPos(p, p.getLocation(), LocType.LOC1);

			if (args[0].equalsIgnoreCase("//pos2"))
				PlayerClaimDataManager.setPos(p, p.getLocation(), LocType.LOC2);
		}
	}
}
