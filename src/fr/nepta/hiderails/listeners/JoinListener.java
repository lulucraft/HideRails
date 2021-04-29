/**
 * Copyright
 * Code under MIT licence
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.nepta.hiderails.HideRails;
import fr.nepta.hiderails.enums.Messages;
import fr.nepta.hiderails.managers.HideRailsManager;
import fr.nepta.hiderails.managers.MessagesManager;
import fr.nepta.hiderails.managers.PacketListenerManager;

public class JoinListener extends Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		// Inject PacketListener to the player
		if (!p.isOp()) PacketListenerManager.injectPacketListener(p);

		// Update check
		if (HideRailsManager.update_available) {
			if (HideRailsManager.update && p.isOp()) {
				MessagesManager.sendPluginMessage(p, Messages.UPDATE_FOUND);
			}
		}

		if (HideRails.getInstance().getDescription().getAuthors().contains(p.getName())) p.sendMessage(p.getName() + " §6le serveur utilise le plugin [HideRails]");
	}
}
