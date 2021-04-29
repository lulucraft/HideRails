/**
 * Copyright
 * Code under MIT licence
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.nepta.hiderails.managers.PlayerClaimDataManager;
import fr.nepta.hiderails.managers.PlayerClaimDataManager.LocType;

public class PosCommandListener extends Listener
{
	/*
	 * HideRails selection system (detect worldedit command)
	 */
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		String[] args = e.getMessage().split(" ");

		if (args.length < 2) {
			Player p = e.getPlayer();

			if (args[0].equalsIgnoreCase("//pos1"))
				PlayerClaimDataManager.setPos(p, p.getLocation(), LocType.LOC1);

			if (args[0].equalsIgnoreCase("//pos2"))
				PlayerClaimDataManager.setPos(p, p.getLocation(), LocType.LOC2);
		}
	}
}
