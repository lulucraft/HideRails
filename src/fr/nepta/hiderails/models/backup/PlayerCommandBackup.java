/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.models.backup;

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
