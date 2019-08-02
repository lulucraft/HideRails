/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.utils.data.ClaimData;
import fr.lulucraft321.hiderails.utils.selectionsystem.Cuboid;

public class PlayerClaimDataManager
{
	private static HashMap<Player, ClaimData> playersClaim = new HashMap<>();
	private static List<Player> blackListedMsgPlayers = new ArrayList<>(); // List contains player who don't want message in chat


	public static void addBlacklistedPlayer(Player p) {
		if (!isBlacklistedPlayer(p)) blackListedMsgPlayers.add(p);
	}

	public static void delBlacklistedPlayer(Player p) {
		if (isBlacklistedPlayer(p)) blackListedMsgPlayers.remove(p);
	}

	public static boolean isBlacklistedPlayer(Player p) {
		return blackListedMsgPlayers.contains(p);
	}


	public static ClaimData getPlayerClaimData(Player p) {
		return playersClaim.get(p);
	}

	/*
	 * Save selection maked by command execution
	 */
	public static void setPos(Player p, LocType locType) {
		setPos(p, p.getLocation(), locType);

		// Send message
		if (!blackListedMsgPlayers.contains(p)) MessagesManager.sendSelectPosMessage(p, locType);
	}

	public static void setPos(Player player, Location loc, LocType locType) {
		if (playersClaim.containsKey(player)) {
			ClaimData data = getPlayerClaimData(player);
			if (locType == LocType.LOC1) data.setPos1(loc);
			if (locType == LocType.LOC2) data.setPos2(loc);

			// Create cuboid is selection is completed
			Location pos1 = data.getPos1();
			Location pos2 = data.getPos2();
			if (pos1 != null && data.getPos2() != null) data.setCuboid(new Cuboid(pos1, pos2));

			playersClaim.put(player, data);
		} else {
			playersClaim.put(player, new ClaimData(locType, loc));
		}

		// Send message
		if (!blackListedMsgPlayers.contains(player)) MessagesManager.sendSelectPosMessage(player, locType);
	}


	public enum LocType {
		LOC1, LOC2;
	}
}
