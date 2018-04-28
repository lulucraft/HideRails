/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.enums.BackupType;
import fr.lulucraft321.hiderails.enums.BlockReplacementType;
import fr.lulucraft321.hiderails.events.RedstoneInWaterEvents;
import fr.lulucraft321.hiderails.reflection.BukkitNMS;
import fr.lulucraft321.hiderails.runnables.BlockChangeRunner;
import fr.lulucraft321.hiderails.runnables.PlayerDisplayBlocks;
import fr.lulucraft321.hiderails.utils.Checker;
import fr.lulucraft321.hiderails.utils.MaterialData;
import fr.lulucraft321.hiderails.utils.Messages;
import fr.lulucraft321.hiderails.utils.backuputility.BlocksBackup;
import fr.lulucraft321.hiderails.utils.railsdata.HiddenRail;
import fr.lulucraft321.hiderails.utils.railsdata.HiddenRailsWorld;

public class HideRailsManager
{
	// List of HiddenRails per world
	public static List<HiddenRailsWorld> rails = new ArrayList<>();

	// List of players who unhide hidden blocks
	private static List<Player> displayBlocksPlayers = new ArrayList<>();
	public static List<Player> getPlayersWhoDisplayedBlocks() { return displayBlocksPlayers; }
	public static boolean isInPlayerWhoDisplayedBlocks(Player p) { return displayBlocksPlayers.contains(p); }

	// Path to hiddenRails in HiddenRails.yml
	public static String path = "hiddenRails";

	/**
	 * HideIronBars enable
	 */
	public static boolean hb;
	/**
	 * HideRails enable
	 */
	public static boolean hr;
	/**
	 * HideCommandsBlock enable
	 */
	public static boolean hc;
	/**
	 * HideRedstone enable
	 */
	public static boolean hd;
	/**
	 * HideSigns enable
	 */
	public static boolean hs;


	/*
	 * Enabled or Disabled hide blockType
	 */
	public static void initHideBlocksType() {
		HideRailsManager.hb = HideRails.getInstance().getConfig().getBoolean("hideIronBars");
		HideRailsManager.hr = HideRails.getInstance().getConfig().getBoolean("hideRails");
		HideRailsManager.hc = HideRails.getInstance().getConfig().getBoolean("hideCommandBlock");
		HideRailsManager.hd = HideRails.getInstance().getConfig().getBoolean("hideRedstone");
		HideRailsManager.hs = HideRails.getInstance().getConfig().getBoolean("hideSigns");
	}


	/*
	 * Load all HiddenRails
	 */
	public static void loadHideRails()
	{
		Configuration config = HideRails.getInstance().getHiddenRailsConfig();

		if (config.getConfigurationSection(path) == null) return;

		HideRailsManager.rails.clear();

		for (String keys : config.getConfigurationSection(path).getKeys(false))
		{
			List<HiddenRail> hiddenRails = new ArrayList<>();
			World world = null;

			for (String loc : config.getStringList(path + "." + keys))
			{
				HiddenRail rail = new HiddenRail(LocationsManager.deserializeMatInSerializedLoc(loc), LocationsManager.deserializeDataInSerializedLoc(loc));
				rail.setLocation(LocationsManager.deserializeLoc(loc));
				hiddenRails.add(rail);
				world = rail.getLocation().getWorld();
			}

			if (world != null)
				if (hiddenRails != null)
					new HiddenRailsWorld(world, hiddenRails);
		}

		// Activation du changement des rails, des barreaux, des commandBlock ou de la redstone
		if (hr || hb || hc || hd || hs) {
			new BlockChangeRunner().runTaskTimer(HideRails.getInstance(), 20 * HideRails.getInstance().getConfig().getInt("hideRails.time"), 100L);
		}
	}


	public static HiddenRailsWorld getWorldHiddenRails(String worldName)
	{
		for (HiddenRailsWorld worldHiddenRails : rails) {
			if (worldHiddenRails.getWorldName().equalsIgnoreCase(worldName)) {
				return worldHiddenRails;
			}
		}
		return null;
	}

	public static List<HiddenRail> getRailsToWorld(String worldName)
	{
		HiddenRailsWorld world = getWorldHiddenRails(worldName);

		if (world != null) {
			if (!world.getHiddenRails().isEmpty()) {
				return world.getHiddenRails();
			}
		}
		return null;
	}


	/*
	 * Water redstone protection
	 */
	public static void setWaterProtection(Player p, String worldName, boolean b)
	{
		String path = RedstoneInWaterEvents.path + "." + worldName;

		if (HideRails.getInstance().getConfig().contains(path))
		{
			if (HideRails.getInstance().getConfig().getBoolean(path) != b)
			{
				HideRails.getInstance().getConfig().set(path, b);
				MessagesManager.sendChangeStatusMessage(p, Messages.SUCCESS_CHANGE_WATER_PROTECTION_STATUS, worldName, b);
			} else {
				MessagesManager.sendAlreadyStatusMessage(p, Messages.WATER_PROTECTION_STATUS_ALREADY, worldName, b);
			}
		} else {
			HideRails.getInstance().getConfig().set(path, b);
			MessagesManager.sendChangeStatusMessage(p, Messages.SUCCESS_CHANGE_WATER_PROTECTION_STATUS, worldName, b);
		}
		HideRails.getInstance().saveConfig();
	}


	/*
	 * Display hidden blocks (admin only)
	 */
	public static void displayBlocks(Player p)
	{
		if (HideRailsManager.displayBlocksPlayers.contains(p)) {
			HideRailsManager.displayBlocksPlayers.remove(p);
		} else {
			HideRailsManager.displayBlocksPlayers.add(p);

			// Spawn particle for see hidden blocks for others players
			if (!PlayerDisplayBlocks.run) {
				new PlayerDisplayBlocks().runTaskTimer(HideRails.getInstance(), 1L, 32L);
				PlayerDisplayBlocks.run = true;
			}

			// Unhide hiddenBlocks only for player
			String worldName = p.getWorld().getName();
			for (HiddenRailsWorld hWorld : HideRailsManager.rails)
			{
				for (HiddenRail rail : hWorld.getHiddenRails())
				{
					Block b = Bukkit.getWorld(worldName).getBlockAt(rail.getLocation());
					Location loc = b.getLocation();
					@SuppressWarnings("deprecation")
					MaterialData mats = new MaterialData(b.getType(), b.getData());
					BukkitNMS.changeBlock(p, mats.getMat(), mats.getData(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
				}
			}
		}

		MessagesManager.sendDisplayChangeMessage(p, Messages.DISPLAY_HIDDEN_BLOCKS, HideRailsManager.displayBlocksPlayers.contains(p));
	}


	/*
	 * UnHide Rails with command
	 */
	@SuppressWarnings("deprecation")
	public static void removeBlocks(Player player, Block targetBlock, boolean backup, boolean single)
	{
		if (!Checker.isRail(targetBlock) && !Checker.isIronBar(targetBlock) && !Checker.isCommandBlock(targetBlock) && !Checker.isRedstone(targetBlock) && !Checker.isSign(targetBlock)) {
			MessagesManager.sendPluginMessage(player, Messages.RAIL_ERROR);
			return;
		}

		BlockReplacementType blockType = Checker.getBlockReplacementType(player, targetBlock);

		List<Location> railsLocs = null;
		if (!single) {
			railsLocs = LocationsManager.getConnectedBlocks(targetBlock.getLocation(), blockType);
		} else {
			railsLocs = Arrays.asList(targetBlock.getLocation());
		}
		HashMap<Location, Byte> railsAndData = new HashMap<>();
		String worldName = targetBlock.getWorld().getName();

		// Creation d'un nouveau backup
		BlocksBackup bBackup = new BlocksBackup();
		bBackup.setType(BackupType.UNHIDE);

		for (Location rail : railsLocs) {
			railsAndData.put(rail, Bukkit.getWorld(worldName).getBlockAt(rail).getState().getData().getData());
		}

		HiddenRailsWorld hiddenRails = getWorldHiddenRails(worldName);
		List<HiddenRail> hRails = new ArrayList<>();

		if (hiddenRails == null) return;
		if (hiddenRails.getHiddenRails().isEmpty()) return;

		for (HiddenRail hRail : hiddenRails.getHiddenRails())
		{
			for (Entry<Location, Byte> entry : railsAndData.entrySet())
			{
				if (entry.getKey().equals(hRail.getLocation()))
				{
					Block rail = Bukkit.getWorld(worldName).getBlockAt(entry.getKey());
					BlockState state = rail.getState();

					rail.setType(rail.getType());
					rail.setData(entry.getValue().byteValue());
					state.update(true);

					hRails.add(hRail);
				}
			}
		}

		for (HiddenRail rail : hRails) {
			hiddenRails.delHiddenRail(rail);

			// Sauveguarde des blocs changés dans le nouveau backup pour pouvoir retourner en arriere apres /hiderails undo
			bBackup.addChangedBlocks(LocationsManager.serialize(rail.getLocation()));
			bBackup.setUnHideBlocksType(new MaterialData(rail.getMaterial(), rail.getData()));
		}

		if (backup) {
			// Save the new blocksBackup to player
			PlayerCommandBackupManager.createNewBlocksBackup(player, bBackup);
		}

		saveWorld(worldName);

		MessagesManager.sendPluginMessage(player, Messages.SUCCESS_UNHIDE_RAIL);
	}


	public static void saveChangedBlocks(Player player, String input, boolean backup, boolean single)
	{
		Block targetBlock = player.getTargetBlock((Set<Material>) null, 25);

		if (!Checker.isRail(targetBlock) && !Checker.isIronBar(targetBlock) && !Checker.isCommandBlock(targetBlock) && !Checker.isRedstone(targetBlock) && !Checker.isSign(targetBlock)) {
			MessagesManager.sendPluginMessage(player, Messages.RAIL_ERROR);
			return;
		}

		e(player, targetBlock, Checker.getBlockReplacementType(player, targetBlock), input, backup, single);
	}

	private static void e(Player player, Block targetBlock, BlockReplacementType blockType, String input, boolean backup, boolean single)
	{
		MaterialData matData = Checker.getMatData(player, input);
		byte data = matData.getData();
		Material mat = matData.getMat();

		if (mat != null)
			HideRailsManager.saveChangedBlocks(player, targetBlock, blockType, mat, data, backup, single);
	}

	protected static void saveChangedBlocks(Player player, Block targetBlock, BlockReplacementType blockType, Material mat, byte data, boolean backup, boolean single)
	{
		List<Location> railsLocs = null;
		if (!single)
			railsLocs = LocationsManager.getConnectedBlocks(targetBlock.getLocation(), blockType);
		else
			railsLocs = Arrays.asList(targetBlock.getLocation());
		List<HiddenRail> railsList = new ArrayList<>();
		World world = targetBlock.getWorld();
		String worldName = world.getName();

		// Creation d'un nouveau backup
		BlocksBackup bBackup = new BlocksBackup();
		bBackup.setType(BackupType.HIDE);

		// Stockage de tous les rails serialisés du monde worldName
		List<String> hiddenRails = new ArrayList<>();
		hiddenRails = HideRails.getInstance().getHiddenRailsConfig().getStringList(path + "." + worldName);
		List<Location> hiddenRailsLocs = new ArrayList<>();

		for (String locStr : hiddenRails) {
			hiddenRailsLocs.add(LocationsManager.deserializeLoc(locStr));
		}

		for (Location loc : railsLocs) {
			if (!hiddenRailsLocs.contains(loc)) { // Eviter les doublons dans le fichier config
				HiddenRail rail = new HiddenRail(mat, data);
				rail.setLocation(loc);
				railsList.add(rail);

				// Sauveguarde des blocs changés dans le nouveau backup pour pouvoir retourner en arriere apres /hiderails undo
				bBackup.addChangedBlocks(LocationsManager.serialize(loc));

				// Spawn particle for see where is replacement
				if (blockType == BlockReplacementType.RAILS) world.spawnParticle(Particle.HEART, loc, 5);
				else if (blockType == BlockReplacementType.IRON_BARS) world.spawnParticle(Particle.VILLAGER_ANGRY, loc, 5);
				else if (blockType == BlockReplacementType.COMMAND_BLOCK) world.spawnParticle(Particle.VILLAGER_ANGRY, loc, 5);
				else if (blockType == BlockReplacementType.REDSTONE) world.spawnParticle(Particle.VILLAGER_ANGRY, loc, 5);
				else if (blockType == BlockReplacementType.SIGN) world.spawnParticle(Particle.VILLAGER_ANGRY, loc, 5);
			}
		}

		if (backup) {
			// Save the new blocksBackup to player
			PlayerCommandBackupManager.createNewBlocksBackup(player, bBackup);
		}

		HiddenRailsWorld railsWorld = null;
		if (getWorldHiddenRails(worldName) == null)
		{
			railsWorld = new HiddenRailsWorld(world);
		} else {
			railsWorld = getWorldHiddenRails(worldName);
		}
		for (int i = 0; i < railsList.size(); i++) railsWorld.addHiddenRails(railsList.get(i));

		saveWorld(worldName);
	}




	/*
	 * Hide blocks with Worldedit selection
	 */
	public static void hideSelectionBlocks(Player player, Selection selection, String input, boolean backup)
	{
		MaterialData matData = Checker.getMatData(player, input);
		byte data = matData.getData();
		Material mat = matData.getMat();

		if (mat != null)
			HideRailsManager.hideSelectionBlocks(player, selection, mat, data, backup);
	}

	private static void hideSelectionBlocks(Player player, Selection selection, Material mat, byte data, boolean backup)
	{
		World world = selection.getMinimumPoint().getWorld(); // Get world of blocks
		String worldName = world.getName(); // Name of blocks replacement world

		List<Location> railsLocs = Checker.getAllValidRails(selection); // Definitly Locations of blocks replacement

		// Creation d'un nouveau backup
		BlocksBackup bBackup = new BlocksBackup();
		bBackup.setType(BackupType.HIDE);
		bBackup.setWeSelection(selection);

		// Stockage de tous les rails serialisés du monde worldName
		List<String> hiddenRails = new ArrayList<>();
		hiddenRails = HideRails.getInstance().getHiddenRailsConfig().getStringList(path + "." + worldName);
		List<Location> hiddenRailsLocs = new ArrayList<>();

		for (String locStr : hiddenRails) {
			hiddenRailsLocs.add(LocationsManager.deserializeLoc(locStr));
		}

		// Final list of HiddenRail
		List<HiddenRail> railsList = new ArrayList<>();

		// Create HiddenRail with theirs data
		for (Location loc : railsLocs) {
			if(!hiddenRailsLocs.contains(loc)) {
				HiddenRail rail = new HiddenRail(mat, data);
				rail.setLocation(loc);
				railsList.add(rail);

				// Sauveguarde des blocs changés dans le nouveau backup pour pouvoir retourner en arriere apres /hiderails undo
				bBackup.addChangedBlocks(LocationsManager.serialize(loc));

				// Spawn particle for see where is replacement
				world.spawnParticle(Particle.BARRIER, loc, 6);
			}
		}

		if (backup) {
			// Save the new blocksBackup to player
			PlayerCommandBackupManager.createNewBlocksBackup(player, bBackup);
		}

		HiddenRailsWorld railsWorld = null;
		if (getWorldHiddenRails(worldName) == null) {
			railsWorld = new HiddenRailsWorld(world);
		} else {
			railsWorld = getWorldHiddenRails(worldName);
		}
		// Add all created HiddenRail HiddenRailsWorld
		for (int i = 0; i < railsList.size(); i++) railsWorld.addHiddenRails(railsList.get(i));

		// Save all HiddenRail
		saveWorld(worldName);
	}


	@SuppressWarnings("deprecation")
	public static void removeSelectionBlocks(Player player, Selection selection, boolean backup)
	{
		List<Location> railsLocs = Checker.getAllValidRails(selection);

		HashMap<Location, Byte> railsAndData = new HashMap<>();
		String worldName = selection.getMinimumPoint().getWorld().getName();

		// Creation d'un nouveau backup
		BlocksBackup bBackup = new BlocksBackup();
		bBackup.setType(BackupType.UNHIDE);
		bBackup.setWeSelection(selection);

		for (Location rail : railsLocs) {
			railsAndData.put(rail, Bukkit.getWorld(worldName).getBlockAt(rail).getState().getData().getData());
		}

		HiddenRailsWorld hiddenRails = getWorldHiddenRails(worldName);
		List<HiddenRail> hRails = new ArrayList<>();

		if (hiddenRails == null) return;
		if (hiddenRails.getHiddenRails().isEmpty()) return;

		for (HiddenRail hRail : hiddenRails.getHiddenRails())
		{
			for (Entry<Location, Byte> entry : railsAndData.entrySet())
			{
				if (entry.getKey().equals(hRail.getLocation()))
				{
					Block rail = Bukkit.getWorld(worldName).getBlockAt(entry.getKey());
					BlockState state = rail.getState();
					rail.setType(rail.getType());
					rail.setData(entry.getValue().byteValue());
					state.update(true);

					hRails.add(hRail);
				}
			}
		}

		for (HiddenRail rail : hRails) {
			hiddenRails.delHiddenRail(rail);

			// Sauveguarde des blocs changés dans le nouveau backup pour pouvoir retourner en arriere apres /hiderails undo
			bBackup.addChangedBlocks(LocationsManager.serialize(rail.getLocation()));
			bBackup.setUnHideBlocksType(new MaterialData(rail.getMaterial(), rail.getData()));
		}

		if (backup) {
			// Save the new blocksBackup to player
			PlayerCommandBackupManager.createNewBlocksBackup(player, bBackup);
		}

		saveWorld(worldName);

		MessagesManager.sendPluginMessage(player, Messages.SUCCESS_UNHIDE_RAIL);
	}




	/*
	 * Save all changed blocks and changed rails in world "worldName"
	 */
	public static void saveWorld(String worldName)
	{
		HiddenRailsWorld railsWorld = getWorldHiddenRails(worldName);
		List<HiddenRail> railsList = new ArrayList<>();
		railsList = railsWorld.getHiddenRails();
		List<String> railsLocs = new ArrayList<>();
		HideRails.getInstance().getHiddenRailsConfig().options().copyDefaults(true);

		for (HiddenRail rails : railsList) {
			railsLocs.add(LocationsManager.serialize(rails.getLocation()) + ";" + rails.getMaterial() + ";" + rails.getData());
		}

		HideRails.getInstance().getHiddenRailsConfig().set(path + "." + worldName, railsLocs);
		HideRails.getInstance().saveConfigs();
	}
}
