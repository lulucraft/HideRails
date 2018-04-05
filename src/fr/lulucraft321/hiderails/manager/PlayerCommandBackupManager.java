/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author ProCZ
 */

package fr.lulucraft321.hiderails.manager;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.utils.Messages;
import fr.lulucraft321.hiderails.utils.backuputility.BackupType;
import fr.lulucraft321.hiderails.utils.backuputility.BlocksBackup;
import fr.lulucraft321.hiderails.utils.backuputility.PlayerCommandBackup;

public class PlayerCommandBackupManager
{
	// All backup to all players
	private static HashMap<Player, PlayerCommandBackup> playerCommandBackups = new HashMap<>();

	// Get backup command to player
	public static PlayerCommandBackup getPlayerCommandBackups(Player player)
	{
		for(Entry<Player, PlayerCommandBackup> backup : PlayerCommandBackupManager.playerCommandBackups.entrySet()) {
			if(backup.getKey().equals(player)) {
				return backup.getValue();
			}
		}

		return null;
	}

	// Get latest blocks backup to player
	public static BlocksBackup getLatestBlocksBackup(Player player)
	{
		PlayerCommandBackup backup = getPlayerCommandBackups(player);

		if(backup != null)
		{
			if(backup.getPlayerBackups().size() > 0)
			{
				BlocksBackup back = backup.getPlayerBackups().get(backup.getPlayerBackups().size()-1);

				if(back != null)
				{
					return back;
				}
			}
		}

		return null;
	}

	// Create a new blocks backup to player
	public static void createNewBlocksBackup(Player player, BlocksBackup blBackup)
	{
		PlayerCommandBackup backup = getPlayerCommandBackups(player);

		if(backup == null)
			backup = new PlayerCommandBackup();

		backup.addPlayerBackups(blBackup);
		playerCommandBackups.put(player, backup);
	}

	// Restore latest blocks backup
	@SuppressWarnings("deprecation")
	public static void restoreBackup(Player p)
	{
		BlocksBackup backup = getLatestBlocksBackup(p);

		if(backup.getType() == BackupType.HIDE)
		{
			for(String block : backup.getChangedBlocks()) {
				Location deserLoc = HideRailsManager.deserializeLoc(block);
				Block bl = Bukkit.getWorld(deserLoc.getWorld().getName()).getBlockAt(deserLoc);
				bl.setTypeIdAndData(bl.getTypeId(), bl.getData(), true);
				HideRailsManager.removeRails(p, bl, false);
			}
		}

		else if(backup.getType() == BackupType.UNHIDE)
		{
			Block bl = null;
			for(String block : backup.getChangedBlocks()) {
				Location deserLoc = HideRailsManager.deserializeLoc(block);
				bl = Bukkit.getWorld(deserLoc.getWorld().getName()).getBlockAt(deserLoc);
			}

			HideRailsManager.saveChangedRails(p, bl, backup.getUnHideBlocksType().getMat(), backup.getUnHideBlocksType().getData(), false);
		}

		// Supression du backup restore
		PlayerCommandBackup pBackup = getPlayerCommandBackups(p);
		pBackup.getPlayerBackups().remove(backup);
		playerCommandBackups.put(p, pBackup);

		MessagesManager.sendPluginMessage(p, Messages.RETURN_BACKUP_SUCCESS);
	}
}
