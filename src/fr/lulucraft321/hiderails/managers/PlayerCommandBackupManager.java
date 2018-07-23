/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.managers;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.enums.BackupType;
import fr.lulucraft321.hiderails.enums.BlockReplacementType;
import fr.lulucraft321.hiderails.enums.Messages;
import fr.lulucraft321.hiderails.utils.backuputility.BlocksBackup;
import fr.lulucraft321.hiderails.utils.backuputility.PlayerCommandBackup;
import fr.lulucraft321.hiderails.utils.checkers.BlocksChecker;
import fr.lulucraft321.hiderails.utils.checkers.WorldeditChecker;

public class PlayerCommandBackupManager
{
	// All backup to all players
	private static HashMap<Player, PlayerCommandBackup> playerCommandBackups = new HashMap<>();

	// Get backup command to player
	public static PlayerCommandBackup getPlayerCommandBackups(Player player)
	{
		for (Entry<Player, PlayerCommandBackup> backup : PlayerCommandBackupManager.playerCommandBackups.entrySet()) {
			if (backup.getKey().equals(player)) {
				return backup.getValue();
			}
		}

		return null;
	}

	// Get latest blocks backup to player
	public static BlocksBackup getLatestBlocksBackup(Player player)
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

	// Create a new blocks backup to player
	public static void createNewBlocksBackup(Player player, BlocksBackup blBackup)
	{
		PlayerCommandBackup backup = getPlayerCommandBackups(player);

		if (backup == null)
			backup = new PlayerCommandBackup();

		backup.addPlayerBackups(blBackup);
		playerCommandBackups.put(player, backup);
	}

	// Restore latest blocks backup
	@SuppressWarnings("deprecation")
	public static void restoreBackup(Player p)
	{
		BlocksBackup backup = getLatestBlocksBackup(p);
		Selection sel = backup.getWeSelection();
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
					if (HideRails.version == "1.12") {
						try {
							Block.class.getDeclaredMethod("setData", byte.class).invoke(bl, Byte.valueOf(bl.getData()));
						} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						//bl.setData(Byte.valueOf(bl.getData()));
					}
					state.update(true);

					i++;
				}

				if (i > 1) single = false;
				else single = true;
				HideRailsManager.removeBlocks(p, bl, false, single);
			}
			else
			{
				// Si le backup contient une worldedit selection
				for (Location blockLoc : WorldeditChecker.getAllValidRails(backup.getWeSelection(), backup.getBlocksType())) {
					bl = Bukkit.getWorld(blockLoc.getWorld().getName()).getBlockAt(blockLoc);
					BlockState state = bl.getState();
					bl.setType(state.getType());
					if (HideRails.version == "1.12") {
						try {
							Block.class.getDeclaredMethod("setData", byte.class).invoke(bl, state.getData().getData());
						} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						//bl.setData(state.getData().getData());
					}
					state.update(true);
				}

				HideRailsManager.removeSelectionBlocks(p, backup.getWeSelection(), false, backup.getBlocksType());
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

				BlockReplacementType blockType = BlocksChecker.getBlockReplacementType(p, bl);
				HideRailsManager.saveChangedBlocks(p, bl, blockType, backup.getUnHideBlocksType().getMat(), backup.getUnHideBlocksType().getData(), false, single);
			}
			else
			{
				HideRailsManager.hideSelectionBlocks(p, sel, (backup.getUnHideBlocksType().getMat().toString()+":"+backup.getUnHideBlocksType().getData()), false, backup.getBlocksType());
			}
		}

		// Supression du backup restore
		PlayerCommandBackup pBackup = getPlayerCommandBackups(p);
		pBackup.getPlayerBackups().remove(backup);
		playerCommandBackups.put(p, pBackup);

		MessagesManager.sendPluginMessage(p, Messages.RETURN_BACKUP_SUCCESS);
	}


	/*
	 * Backup replacement command
	 */
	public static void restoreBackupRails(Player p)
	{
		BlocksBackup backup = PlayerCommandBackupManager.getLatestBlocksBackup(p);

		if(backup != null) {
			if (!backup.getChangedBlocks().isEmpty()) {
				PlayerCommandBackupManager.restoreBackup(p);
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
