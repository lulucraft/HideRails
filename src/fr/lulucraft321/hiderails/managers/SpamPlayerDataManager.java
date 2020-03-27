/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.utils.data.SpamPlayerData;

public class SpamPlayerDataManager
{
	private static List<SpamPlayerData> playersData = new ArrayList<>();

	/**
	 * Get spam data of player
	 * 
	 * @param player
	 * @return
	 */
	public static SpamPlayerData getSpamPlayerData(Player player) {
		for (SpamPlayerData data : playersData) {
			if (data.getPlayer() == player) {
				return data;
			}
		}
		return null;
	}


	/**
	 * Get player clicks number
	 * 
	 * @param player
	 * @return int
	 */
	public static int getSpamNumber(Player player) {
		SpamPlayerData data = getSpamPlayerData(player);
		if (data != null) {
			return data.getSpam();
		}
		return 0;
	}


	/**
	 * Add spam task to player
	 * 
	 * @param player
	 * @param task
	 */
	public static void addPlayerSpamTask(Player player, BukkitTask task) {
		SpamPlayerData data = getSpamPlayerData(player);
		if (data != null) {
			data.getTasks().add(task);
			data.setSpam(data.getSpam()+1);
		} else {
			playersData.add(new SpamPlayerData(player, task));
		}
	}

	/**
	 * Delete player spam data
	 * 
	 * @param player
	 */
	public static void delPlayer(Player player) {
		SpamPlayerData data = getSpamPlayerData(player);
		if (data != null) {
			for (BukkitTask task : data.getTasks()) {
				if (task.getOwner() == HideRails.getInstance())
					Bukkit.getScheduler().cancelTask(task.getTaskId());
			}
			playersData.remove(data);
		}
	}


	/**
	 * Get deletion task to player
	 * 
	 * @param player
	 * @return
	 */
	public static BukkitTask getPendingTask(Player player) {
		SpamPlayerData data = getSpamPlayerData(player);
		if (data != null)
			data.getPendingTask();

		return null;
	}

	/**
	 * Set deletion task to player
	 * 
	 * @param player
	 * @param bukkitTask
	 */
	public static void setPendingTask(Player player, BukkitTask t) {
		SpamPlayerData data = getSpamPlayerData(player);
		if (data != null)
			data.setPendingTask(t);
	}
}
