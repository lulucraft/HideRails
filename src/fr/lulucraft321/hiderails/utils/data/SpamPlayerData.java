/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.utils.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class SpamPlayerData
{
	private Player player;
	private int spam = 0;
	private List<BukkitTask> tasks = new ArrayList<>();
	private BukkitTask pendingTask = null;

	public SpamPlayerData(Player player, BukkitTask t) {
		this.player = player;
		this.setSpam(1);
		this.addTask(t);
	}

	public Player getPlayer() {
		return player;
	}

	public int getSpam() {
		return spam;
	}

	public void setSpam(int spam) {
		this.spam = spam;
	}

	public List<BukkitTask> getTasks() {
		return tasks;
	}

	public void addTask(BukkitTask task) {
		this.tasks.add(task);
	}

	public BukkitTask getPendingTask() {
		return pendingTask;
	}

	public void setPendingTask(BukkitTask task) {
		this.pendingTask = task;
	}
}
