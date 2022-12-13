/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.managers;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.HideRails;
import fr.nepta.hiderails.enums.BackupType;
import fr.nepta.hiderails.enums.Messages;
import fr.nepta.hiderails.enums.Version;
import fr.nepta.hiderails.models.backup.BlocksBackup;
import fr.nepta.hiderails.models.backup.PlayerCommandBackup;
import fr.nepta.hiderails.models.selectionsystem.Cuboid;
import fr.nepta.hiderails.nms.BukkitNMS;
import fr.nepta.hiderails.utils.HideRailsSelectionChecker;

public class PlayerCommandBackupManager
{
	// All backup of all players
	private static HashMap<Player, PlayerCommandBackup> playerCommandBackups = new HashMap<>();

	// Get command backup of player
	public static PlayerCommandBackup getPlayerCommandBackups(@Nonnull Player player)
	{
		for (Entry<Player, PlayerCommandBackup> backup : playerCommandBackups.entrySet()) {
			if (backup.getKey().equals(player)) {
				return backup.getValue();
			}
		}

		return null;
	}

	/**
	 * Get latest blocks backup to player
	 * 
	 * @param player
	 * @return BlocksBackup
	 */
	public static BlocksBackup getLatestBlocksBackup(@Nonnull Player player)
	{
		PlayerCommandBackup backup = getPlayerCommandBackups(player);

		if (backup != null) {
			if (backup.getPlayerBackups().size() > 0) {
				BlocksBackup back = backup.getPlayerBackups().get(backup.getPlayerBackups().size()-1);

				if (back != null) {
					return back;
				}
			}
		}

		return null;
	}

	/**
	 *  Create a new blocks backup for the player
	 *  
	 * @param player
	 * @param blockBackup
	 */
	public static void createNewBlocksBackup(@Nonnull Player player, @Nonnull BlocksBackup blockBackup)
	{
		PlayerCommandBackup backup = getPlayerCommandBackups(player);

		if (backup == null)
			backup = new PlayerCommandBackup();

		backup.addPlayerBackups(blockBackup);
		playerCommandBackups.put(player, backup);
	}

	/**
	 * Restore the latest blocks backup
	 * 
	 * @param player
	 */
	@SuppressWarnings("deprecation")
	public static void restoreBackup(@Nonnull Player player)
	{
		BlocksBackup backup = getLatestBlocksBackup(player);
		Cuboid sel = backup.getHrSelection();
		Block bl = null;

		boolean single;
		int i = 0;

		if (backup.getType() == BackupType.HIDE)
		{
			if (sel == null)
			{
				for (String block : backup.getChangedBlocks()) {
					Location deserLoc = LocationsManager.deserializeLoc(block);
					bl = Bukkit.getWorld(deserLoc.getWorld().getName()).getBlockAt(deserLoc);
					BlockState state = bl.getState();
					bl.setType(bl.getType());
					if (HideRails.version == Version.V1_12) {
						try {
							BukkitNMS.setData(bl, bl.getData());
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						//bl.setData(Byte.valueOf(bl.getData()));
					}
					state.update(true);

					i++;
				}

				if (i > 1) single = false;
				else single = true;
				HideRailsManager.removeBlocks(player, bl, false, single);
			}
			else
			{
				// If the backup that contain an HideRails selection
				for (Location blockLoc : HideRailsSelectionChecker.getAllValidRails(backup.getHrSelection(), backup.getBlocksType())) {
					bl = Bukkit.getWorld(blockLoc.getWorld().getName()).getBlockAt(blockLoc);
					BlockState state = bl.getState();
					bl.setType(state.getType());
					if (HideRails.version == Version.V1_12) {
						try {
							BukkitNMS.setData(bl, state.getData().getData());
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						//bl.setData(state.getData().getData());
					}
					state.update(true);
				}

				HideRailsManager.removeSelectionBlocks(player, backup.getHrSelection(), false, backup.getBlocksType());
			}
		}

		else if (backup.getType() == BackupType.UNHIDE)
		{
			if (sel == null)
			{
				for (String block : backup.getChangedBlocks()) {
					Location deserLoc = LocationsManager.deserializeLoc(block);
					bl = Bukkit.getWorld(deserLoc.getWorld().getName()).getBlockAt(deserLoc);

					i++;
				}

				if (i > 1) single = false;
				else single = true;

				HideRailsManager.saveChangedBlocks(player, bl, null, backup.getUnHideBlocksType().getMat(), backup.getUnHideBlocksType().getData(), false, single);
			}
			else
			{
				HideRailsManager.hideSelectionBlocks(player, sel, (backup.getUnHideBlocksType().getMat().toString()+":"+backup.getUnHideBlocksType().getData()), false, backup.getBlocksType());
			}
		}

		// Remove the restored backup
		PlayerCommandBackup pBackup = getPlayerCommandBackups(player);
		pBackup.getPlayerBackups().remove(backup);
		playerCommandBackups.put(player, pBackup);

		MessagesManager.sendPluginMessage(player, Messages.RETURN_BACKUP_SUCCESS);
	}


	/**
	 * Restore player backup by command execution
	 * 
	 * @param p
	 */
	public static void restoreBackupRails(Player p)
	{
		BlocksBackup backup = getLatestBlocksBackup(p);

		if(backup != null) {
			if (!backup.getChangedBlocks().isEmpty()) {
				restoreBackup(p);
			} else {
				// If no block is to be restore
				PlayerCommandBackup pBackup = getPlayerCommandBackups(p);
				pBackup.getPlayerBackups().remove(backup);
				playerCommandBackups.put(p, pBackup);
				restoreBackupRails(p);
			}
		} else {
			MessagesManager.sendPluginMessage(p, Messages.NO_BACKUP);
		}
	}
}
