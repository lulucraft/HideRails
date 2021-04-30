/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.managers;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import fr.nepta.hiderails.listeners.PacketListener;
import fr.nepta.hiderails.nms.BukkitNMS;

public class PacketListenerManager {

	private static Map<Player, PacketListener> injectedPlayers = new HashMap<>();

	/**
	 * Inject a PacketListener to the player
	 * 
	 * @param player
	 */
	public static void injectPacketListener(Player player) {
		PacketListener packetListener = new PacketListener();

		try {
			BukkitNMS.injectChannelPipeline(player, packetListener);
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchFieldException | SecurityException | NoSuchMethodException e) {
			e.printStackTrace();
			return;
		}

		if (!injectedPlayers.containsKey(player)) {
			injectedPlayers.put(player, packetListener);
		}
	}

	/**
	 * Remove PacketListener from player
	 * 
	 * @param player
	 */
	public static void removePacketListener(Player player) {
		if (injectedPlayers.containsKey(player)) {
			try {
				BukkitNMS.removeChannelPipeline(player, injectedPlayers.get(player));
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
				return;
			}

			injectedPlayers.remove(player);
		}
	}

	/**
	 * @return the injectedPlayers
	 */
	public Map<Player, PacketListener> getInjectedPlayers() {
		return injectedPlayers;
	}
}
