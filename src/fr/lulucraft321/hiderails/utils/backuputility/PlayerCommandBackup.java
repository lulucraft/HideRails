/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author ProCZ
 */

package fr.lulucraft321.hiderails.utils.backuputility;

import java.util.ArrayList;
import java.util.List;

public class PlayerCommandBackup
{
	private List<BlocksBackup> playerBackups = new ArrayList<>();

	public List<BlocksBackup> getPlayerBackups() {
		return playerBackups;
	}

	public void addPlayerBackups(BlocksBackup playerBackups) {
		this.playerBackups.add(playerBackups);
	}
}
