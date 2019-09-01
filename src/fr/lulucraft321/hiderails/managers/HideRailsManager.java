/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.managers;

import java.lang.reflect.InvocationTargetException;
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

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.commands.TabComplete;
import fr.lulucraft321.hiderails.enums.BackupType;
import fr.lulucraft321.hiderails.enums.BlockReplacementType;
import fr.lulucraft321.hiderails.enums.Messages;
import fr.lulucraft321.hiderails.enums.Version;
import fr.lulucraft321.hiderails.listeners.RedstoneInWaterListeners;
import fr.lulucraft321.hiderails.reflection.BukkitNMS;
import fr.lulucraft321.hiderails.runnables.BlockChangeRunner;
import fr.lulucraft321.hiderails.runnables.PlayerDisplayBlocks;
import fr.lulucraft321.hiderails.utils.backuputility.BlocksBackup;
import fr.lulucraft321.hiderails.utils.checkers.BlocksChecker;
import fr.lulucraft321.hiderails.utils.checkers.HideRailsSelectionChecker;
import fr.lulucraft321.hiderails.utils.data.MaterialData;
import fr.lulucraft321.hiderails.utils.data.railsdata.HiddenRail;
import fr.lulucraft321.hiderails.utils.data.railsdata.HiddenRailsWorld;
import fr.lulucraft321.hiderails.utils.selectionsystem.Cuboid;

public class HideRailsManager
{
	// List of HiddenRails per world
	public static List<HiddenRailsWorld> rails = new ArrayList<>();

	// List of players who unhide hidden blocks
	public static List<Player> displayBlocksPlayers = new ArrayList<>();
	public static List<Player> getPlayersWhoDisplayedBlocks() { return displayBlocksPlayers; }
	public static boolean isInPlayerWhoDisplayedBlocks(Player p) { return displayBlocksPlayers.contains(p); }

	// Path to hiddenRails in HiddenRails.yml
	public static final String HIDDEN_RAILS_PATH = "hiddenRails";

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

	/**
	 * Particles who spawned in hiddenBlocks if player execute /hr display command
	 */
	public static boolean hiddingBlocksParticles = true;

	/**
	 * Join plugin update message (admins only)
	 * If maj is available
	 */
	public static boolean maj_available;

	/**
	 * Enable or disable update message
	 */
	public static boolean maj = true;

	/**
	 * Max click number before kick player
	 */
	public static int max_spam_nbr = 4;

	/**
	 * If kick spam is enable
	 */
	public static boolean spam_kick = true;


	/*
	 * Enabled or Disabled hide blockType
	 */
	public static void initHideBlocksType() {
		fr.lulucraft321.hiderails.configurations.specialconfig.Configuration config = FileConfigurationManager.getConfig();

		HideRailsManager.hb = config.getBoolean("hideIronBars");
		HideRailsManager.hr = config.getBoolean("hideRails");
		HideRailsManager.hc = config.getBoolean("hideCommandBlock");
		HideRailsManager.hd = config.getBoolean("hideRedstone");
		HideRailsManager.hs = config.getBoolean("hideSigns");

		TabComplete.BLOCK_TYPE.clear();
		if (hr) {
			if (HideRails.version == Version.v1_12) TabComplete.BLOCK_TYPE.add("rails");
			if (HideRails.version == Version.v1_13 || HideRails.version == Version.v1_14) TabComplete.BLOCK_TYPE.add("rail");
		}
		if (hs) {
			if (HideRails.version == Version.v1_14) TabComplete.BLOCK_TYPE.add("oak_sign");
			else TabComplete.BLOCK_TYPE.add("sign");
		}
		if (hr) {
			TabComplete.BLOCK_TYPE.add("redstone");
		}
		if (hc) {
			if (HideRails.version == Version.v1_12) TabComplete.BLOCK_TYPE.add("command");
			if (HideRails.version == Version.v1_13 || HideRails.version == Version.v1_14) TabComplete.BLOCK_TYPE.add("command_block");
		}
		if (hb) {
			if (HideRails.version == Version.v1_12) TabComplete.BLOCK_TYPE.add("iron_fence");
			if (HideRails.version == Version.v1_13 || HideRails.version == Version.v1_14) TabComplete.BLOCK_TYPE.add("iron_bars");
		}

		HideRailsManager.maj = config.getBoolean("adminsUpdateMessage");
		HideRailsManager.hiddingBlocksParticles = config.getBoolean("hiddingBlocksParticles");
		HideRailsManager.max_spam_nbr = config.getInt("maxSpamNumber");
		HideRailsManager.spam_kick = config.getBoolean("kickSpamBlock");
	}


	/**
	 * Load all HiddenBlocks
	 */
	public static void loadHideRails()
	{
		Configuration config = FileConfigurationManager.getHiddenRailsConfig();

		if (config.getConfigurationSection(HIDDEN_RAILS_PATH) == null) return;

		HideRailsManager.rails.clear();

		for (String keys : config.getConfigurationSection(HIDDEN_RAILS_PATH).getKeys(false))
		{
			List<HiddenRail> hiddenRails = new ArrayList<>();
			World world = null;

			for (String loc : config.getStringList(HIDDEN_RAILS_PATH + "." + keys))
			{
				Material mat = LocationsManager.deserializeMatInSerializedLoc(loc);
				Byte data = LocationsManager.deserializeDataInSerializedLoc(loc);
				HiddenRail rail = null;

				if (HideRails.version == Version.v1_12) {
					// Adapt material name to good versions
					String matName = mat.name().replace("LEGACY_", "");
					mat = Material.matchMaterial(matName);

					rail = new HiddenRail(mat, data);
				}

				rail = new HiddenRail(mat, data);
				Location dLoc = LocationsManager.deserializeLoc(loc);
				rail.setLocation(dLoc);
				hiddenRails.add(rail);
				world = rail.getLocation().getWorld();
			}

			if (world != null)
				if (hiddenRails != null)
					new HiddenRailsWorld(world, hiddenRails);
		}

		// Activation du changement des rails, des barreaux, des commandBlock ou de la redstone
		if (hr || hb || hc || hd || hs) {
			new BlockChangeRunner().runTaskTimer(HideRails.getInstance(), 20L * HideRails.getInstance().getConfig().getInt("hideRails.time"), 100L);
		}
	}


	/**
	 * Load players data
	 */
	public static void initPlayersData()
	{
		Configuration config = FileConfigurationManager.getPlayersDataConfig();

		// Load players who don't want selection messages
		for (String keys : config.getKeys(false))
		{
			if (!config.getBoolean(keys + ".selectionMessage")) PlayerClaimDataManager.addBlacklistedPlayer(Bukkit.getPlayer(keys));
		}
	}


	/**
	 * Get hiddenRailsWorld class who contains HiddenRails, with name of world
	 * 
	 * @param worldName
	 * @return HiddenRailsWorld
	 */
	public static HiddenRailsWorld getWorldHiddenRails(String worldName)
	{
		for (HiddenRailsWorld worldHiddenRails : rails) {
			if (worldHiddenRails.getWorldName().equalsIgnoreCase(worldName)) {
				return worldHiddenRails;
			}
		}
		return null;
	}


	/**
	 * Get list of all hiddenBlocks with name of world
	 * 
	 * @param worldName
	 * @return List of HiddenRails
	 */
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


	/**
	 * Get hiddenBlock with location
	 * 
	 * @param location
	 * @return HiddenRail
	 */
	public static HiddenRail getHiddenRail(Location location)
	{
		if (location == null) return null;
		List<HiddenRail> hRails = getRailsToWorld(location.getWorld().getName());
		if (hRails == null) return null;

		for (HiddenRail hRail : hRails) {
			if (hRail.getLocation().equals(location)) {
				return hRail;
			}
		}

		return null;
	}



	/**
	 * Water redstone protection
	 * 
	 * @param player
	 * @param worldName
	 * @param boolean
	 */
	public static void setWaterProtection(Player p, String worldName, boolean b)
	{
		final String path = RedstoneInWaterListeners.PATH_POINT + worldName;

		if (FileConfigurationManager.getConfig().contains(path))
		{
			if (FileConfigurationManager.getConfig().getBoolean(path) != b)
			{
				FileConfigurationManager.getConfig().editDefault(path, b, "redstoneWaterProtection:");
				MessagesManager.sendChangeStatusMessage(p, Messages.SUCCESS_CHANGE_WATER_PROTECTION_STATUS, worldName, b);
			} else {
				MessagesManager.sendAlreadyStatusMessage(p, Messages.WATER_PROTECTION_STATUS_ALREADY, worldName, b);
			}
		} else {
			FileConfigurationManager.getConfig().addDefault(path, b);
			MessagesManager.sendChangeStatusMessage(p, Messages.SUCCESS_CHANGE_WATER_PROTECTION_STATUS, worldName, b);
		}
		FileConfigurationManager.getConfig().reloadConfig();
	}



	/**
	 * Display hidden blocks (admin only)
	 * 
	 * @param player
	 */
	public static void displayBlocks(Player p)
	{
		if (HideRailsManager.displayBlocksPlayers.contains(p)) {
			HideRailsManager.displayBlocksPlayers.remove(p);

			if (PlayerDisplayBlocks.run && HideRailsManager.displayBlocksPlayers.isEmpty()) {
				PlayerDisplayBlocks.run = false;
				Bukkit.getScheduler().cancelTask(PlayerDisplayBlocks.taskId);
			}
		} else {
			HideRailsManager.displayBlocksPlayers.add(p);

			// Spawn particle for see hidden blocks for others players
			if (!PlayerDisplayBlocks.run) {
				// If particles enabled
				if (HideRailsManager.hiddingBlocksParticles) {
					PlayerDisplayBlocks.taskId = new PlayerDisplayBlocks().runTaskTimer(HideRails.getInstance(), 1L, 32L).getTaskId();
					PlayerDisplayBlocks.run = true;
				}
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
					b.getState().update(true);
				}
			}
		}

		MessagesManager.sendDisplayChangeMessage(p, Messages.DISPLAY_HIDDEN_BLOCKS, HideRailsManager.displayBlocksPlayers.contains(p));
	}



	/**
	 * UnHide hiddenBlocks with command
	 * 
	 * @param player
	 * @param targetBlock
	 * @param backup
	 * @param single
	 */
	@SuppressWarnings("deprecation")
	public static void removeBlocks(Player player, Block targetBlock, boolean backup, boolean single)
	{
		if (!BlocksChecker.isRail(targetBlock) && !BlocksChecker.isIronBar(targetBlock) && !BlocksChecker.isCommandBlock(targetBlock) && !BlocksChecker.isRedstone(targetBlock) && !BlocksChecker.isSign(targetBlock)) {
			MessagesManager.sendPluginMessage(player, Messages.RAIL_ERROR);
			return;
		}

		BlockReplacementType blockType = BlocksChecker.getBlockReplacementType(player, targetBlock);

		List<Location> railsLocs = null;
		if (!single) {
			railsLocs = LocationsManager.getConnectedBlocks(targetBlock.getLocation(), blockType);
		} else {
			railsLocs = Arrays.asList(targetBlock.getLocation());
		}
		HashMap<Location, Byte> railsAndData = new HashMap<>();
		String worldName = targetBlock.getWorld().getName();

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
					if (HideRails.version == Version.v1_12) {
						try {
							Block.class.getDeclaredMethod("setData", byte.class).invoke(rail, entry.getValue().byteValue());
						} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						//rail.setData(entry.getValue().byteValue());
					}
					state.update(true);

					hRails.add(hRail);
				}
			}
		}

		// Creation d'un nouveau backup
		BlocksBackup bBackup = new BlocksBackup();
		bBackup.setType(BackupType.UNHIDE);

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


	/**
	 * Save all hiddenBlocks (with command)
	 * 
	 * @param player
	 * @param input
	 * @param backup
	 * @param single
	 */
	public static void saveChangedBlocks(Player player, String input, boolean backup, boolean single)
	{
		Block targetBlock = player.getTargetBlock((Set<Material>) null, 25);

		if (!BlocksChecker.isRail(targetBlock) && !BlocksChecker.isIronBar(targetBlock) && !BlocksChecker.isCommandBlock(targetBlock) && !BlocksChecker.isRedstone(targetBlock) && !BlocksChecker.isSign(targetBlock)) {
			MessagesManager.sendPluginMessage(player, Messages.RAIL_ERROR);
			return;
		}

		e(player, targetBlock, BlocksChecker.getBlockReplacementType(player, targetBlock), input, backup, single);
	}

	private static void e(Player player, Block targetBlock, BlockReplacementType blockType, String input, boolean backup, boolean single)
	{
		MaterialData matData = BlocksChecker.getMatData(player, input);
		byte data = matData.getData();
		Material mat = matData.getMat();

		if (mat != null)
			HideRailsManager.saveChangedBlocks(player, targetBlock, blockType, mat, data, backup, single);
	}


	/**
	 * Hide one or more blocks (with command)
	 * 
	 * @param player
	 * @param targetBlock
	 * @param blockType
	 * @param mat
	 * @param data
	 * @param backup
	 * @param single
	 */
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
		hiddenRails = FileConfigurationManager.getHiddenRailsConfig().getStringList(HIDDEN_RAILS_PATH + "." + worldName);
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

				// If version is not 1.8
				if (!HideRails.version.isOldVersion()) {
					// Spawn particle for see where is replacement
					if (blockType == BlockReplacementType.RAILS) world.spawnParticle(Particle.HEART, loc, 5);
					else if (blockType == BlockReplacementType.IRON_BARS) world.spawnParticle(Particle.VILLAGER_ANGRY, loc, 5);
					else if (blockType == BlockReplacementType.COMMAND_BLOCK) world.spawnParticle(Particle.VILLAGER_ANGRY, loc, 5);
					else if (blockType == BlockReplacementType.REDSTONE) world.spawnParticle(Particle.VILLAGER_ANGRY, loc, 5);
					else if (blockType == BlockReplacementType.SIGN) world.spawnParticle(Particle.VILLAGER_ANGRY, loc, 5);
				}
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

		// Save all hiddenBlocks
		saveWorld(worldName);
	}



	/**
	 * Hide blocks with HideRails selection
	 * 
	 * @param player
	 * @param selection
	 * @param input
	 * @param backup
	 * @param types 
	 */
	public static void hideSelectionBlocks(Player player, Cuboid selection, String input, boolean backup, List<Material> types)
	{
		MaterialData matData = BlocksChecker.getMatData(player, input);
		byte data = matData.getData();
		Material mat = matData.getMat();

		if (mat != null)
			HideRailsManager.hideSelectionBlocks(player, selection, mat, data, backup, types);
	}

	private static void hideSelectionBlocks(Player player, Cuboid selection, Material mat, byte data, boolean backup, List<Material> types)
	{
		World world = selection.getWorld();
		String worldName = world.getName(); // Name of blocks replacement world

		List<Location> railsLocs = HideRailsSelectionChecker.getAllValidRails(selection, types); // Definitly Locations of blocks replacement

		// Creation d'un nouveau backup
		BlocksBackup bBackup = new BlocksBackup();
		bBackup.setType(BackupType.HIDE);
		bBackup.setBlocksType(types);
		bBackup.setHrSelection(selection);

		// Stockage de tous les rails serialisés du monde worldName
		List<String> hiddenRails = new ArrayList<>();
		hiddenRails = FileConfigurationManager.getHiddenRailsConfig().getStringList(HIDDEN_RAILS_PATH + "." + worldName);
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

				// If version is not 1.8
				if (!HideRails.version.isOldVersion()) {
					// Spawn particle for see where is replacement
					world.spawnParticle(Particle.BARRIER, loc, 6);
				}
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

		// Save all HiddenBlocks
		saveWorld(worldName);
	}



	/**
	 * Remove hiddenBlock with HideRails selection
	 * 
	 * @param player
	 * @param selection
	 * @param backup
	 * @param types 
	 */
	@SuppressWarnings("deprecation")
	public static void removeSelectionBlocks(Player player, Cuboid selection, boolean backup, List<Material> types)
	{
		List<Location> railsLocs = HideRailsSelectionChecker.getAllValidRails(selection, types);

		HashMap<Location, Byte> railsAndData = new HashMap<>();
		String worldName = selection.getWorld().getName();

		// Creation d'un nouveau backup
		BlocksBackup bBackup = new BlocksBackup();
		bBackup.setType(BackupType.UNHIDE);
		bBackup.setBlocksType(types);
		bBackup.setHrSelection(selection);

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
					if (HideRails.version == Version.v1_12) {
						try {
							Block.class.getDeclaredMethod("setData", byte.class).invoke(rail, entry.getValue().byteValue());
						} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						//rail.setData(entry.getValue().byteValue());
					}
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

		// Save all hiddenBlocks
		saveWorld(worldName);

		MessagesManager.sendPluginMessage(player, Messages.SUCCESS_UNHIDE_RAIL);
	}



	/**
	 * Save all changed blocks and changed rails in world "worldName"
	 * 
	 * @param worldName
	 */
	public static void saveWorld(String worldName)
	{
		HiddenRailsWorld railsWorld = getWorldHiddenRails(worldName);
		List<HiddenRail> railsList = new ArrayList<>();
		railsList = railsWorld.getHiddenRails();
		List<String> railsLocs = new ArrayList<>();
		FileConfigurationManager.getHiddenRailsConfig().options().copyDefaults(true);

		for (HiddenRail rails : railsList) {
			railsLocs.add(LocationsManager.serialize(rails.getLocation()) + ";" + rails.getMaterial() + ";" + rails.getData());
		}

		FileConfigurationManager.getHiddenRailsConfig().set(HIDDEN_RAILS_PATH + "." + worldName, railsLocs);
		FileConfigurationManager.saveConfigs();
	}
}
