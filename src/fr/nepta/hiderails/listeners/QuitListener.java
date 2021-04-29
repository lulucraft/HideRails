/**
 * @author Nepta_
 *
 */
package fr.nepta.hiderails.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.nepta.hiderails.managers.PacketListenerManager;

public class QuitListener extends Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		PacketListenerManager.removePacketListener(e.getPlayer());
	}
}
