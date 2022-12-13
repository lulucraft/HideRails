/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.managers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.HideRails;
import fr.nepta.hiderails.enums.BackupType;
import fr.nepta.hiderails.enums.BlockReplacementType;
import fr.nepta.hiderails.enums.Messages;
import fr.nepta.hiderails.enums.Version;
import fr.nepta.hiderails.listeners.RedstoneInWaterListeners;
import fr.nepta.hiderails.models.MaterialData;
import fr.nepta.hiderails.models.backup.BlocksBackup;
import fr.nepta.hiderails.models.railsdata.HiddenRail;
import fr.nepta.hiderails.models.railsdata.HiddenRailsWorld;
import fr.nepta.hiderails.models.selectionsystem.Cuboid;
import fr.nepta.hiderails.nms.BukkitNMS;
import fr.nepta.hiderails.runnables.BlockChangeRunner;
import fr.nepta.hiderails.runnables.PlayerDisplayBlocks;
import fr.nepta.hiderails.utils.BlocksChecker;
import fr.nepta.hiderails.utils.HideRailsSelectionChecker;

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
	 * Join message (admins only)
	 * If update is available
	 */
	public static boolean update_available;

	/**
	 * Enable or disable update message
	 */
	public static boolean update = true;

	/**
	 * Max click number before kick player
	 */
	//	public static int max_spam_nbr = 4;

	/**
	 * If kick spam is enable
	 */
	//	public static boolean spam_kick = true;


	/*
	 * Enabled or Disabled hide blockType
	 */
	public static void initHideBlocksType() {
		fr.nepta.hiderails.configurations.commentedconfig.Configuration config = FileConfigurationManager.getConfig();

		hb = config.getBoolean("hideIronBars");
		hr = config.getBoolean("hideRails");
		hc = config.getBoolean("hideCommandBlock");
		hd = config.getBoolean("hideRedstone");
		hs = config.getBoolean("hideSigns");

		update = config.getBoolean("adminsUpdateMessage");
		hiddingBlocksParticles = config.getBoolean("hiddingBlocksParticles");
		//		max_spam_nbr = config.getInt("maxSpamNumber");
		//		spam_kick = config.getBoolean("kickSpamBlock");
	}


	/**
	 * Load all hidden blocks
	 */
	public static void loadHideRails()
	{
		Configuration config = FileConfigurationManager.getHiddenRailsConfig();

		if (config.getConfigurationSection(HIDDEN_RAILS_PATH) == null) return;

		rails.clear();

		for (String keys : config.getConfigurationSection(HIDDEN_RAILS_PATH).getKeys(false))
		{
			List<HiddenRail> hiddenRails = new ArrayList<>();
			World world = null;

			for (String loc : config.getStringList(HIDDEN_RAILS_PATH + "." + keys))
			{
				Material mat = LocationsManager.deserializeMatInSerializedLoc(loc);
				Byte data = LocationsManager.deserializeDataInSerializedLoc(loc);
				HiddenRail rail = null;

				if (HideRails.version == Version.V1_12) {
					if (mat == null) {
						System.err.println("[HideRails] <ERROR> THE 'HiddenRails.yml' FILE THAT CONTAINS INVALID BLOCKS, YOU MUST UPDATE THE 'LEGAGY' BLOCKS OR DELETE THE FILE!!");
						continue;
					}
					// Adapt material name to the good versions
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

		// Activation of rails, iron bars, command blocks and redstone hiding
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
	 * Get hiddenRailsWorld object that contains HiddenRails, with name of world
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
	 * Get hidden block from location
	 * 
	 * @param location
	 * @return HiddenRail
	 */
	public static HiddenRail getHiddenRail(@Nullable Location location)
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
	 * Redstone protection in water
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
		if (displayBlocksPlayers.contains(p)) {
			displayBlocksPlayers.remove(p);

			if (PlayerDisplayBlocks.run && displayBlocksPlayers.isEmpty()) {
				PlayerDisplayBlocks.run = false;
				Bukkit.getScheduler().cancelTask(PlayerDisplayBlocks.taskId);
			}
		} else {
			displayBlocksPlayers.add(p);

			// Spawn particle to see hidden blocks for others players
			if (!PlayerDisplayBlocks.run) {
				// If the particles are enabled
				if (hiddingBlocksParticles) {
					PlayerDisplayBlocks.taskId = new PlayerDisplayBlocks().runTaskTimer(HideRails.getInstance(), 1L, 32L).getTaskId();
					PlayerDisplayBlocks.run = true;
				}
			}

			// Un-hide hidden blocks only for player
			String worldName = p.getWorld().getName();
			for (HiddenRailsWorld hWorld : rails)
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

		MessagesManager.sendDisplayChangeMessage(p, Messages.DISPLAY_HIDDEN_BLOCKS, displayBlocksPlayers.contains(p));
	}



	/**
	 * Un hide hidden blocks by command
	 * 
	 * @param player
	 * @param targetBlock
	 * @param backup
	 * @param single
	 */
	@SuppressWarnings("deprecation")
	public static void removeBlocks(Player player, @Nonnull Block targetBlock, boolean backup, boolean single)
	{
		if (!BlocksChecker.isRail(targetBlock) && !BlocksChecker.isIronBar(targetBlock) && !BlocksChecker.isCommandBlock(targetBlock) && !BlocksChecker.isRedstone(targetBlock) && !BlocksChecker.isSign(targetBlock)) {
			MessagesManager.sendPluginMessage(player, Messages.RAIL_ERROR);
			return;
		}

		List<Location> railsLocs = null;
		if (!single)
			railsLocs = LocationsManager.getConnectedBlocks(targetBlock.getLocation(), null);
		else
			railsLocs = Arrays.asList(targetBlock.getLocation());

		HashMap<Location, Byte> railsAndData = new HashMap<>();
		String worldName = targetBlock.getWorld().getName();
		HiddenRailsWorld hiddenRails = getWorldHiddenRails(worldName);

		if (hiddenRails == null) return;
		if (hiddenRails.getHiddenRails().isEmpty()) return;

		for (Location rail : railsLocs) {
			railsAndData.put(rail, Bukkit.getWorld(worldName).getBlockAt(rail).getState().getData().getData());
		}

		List<HiddenRail> hRails = new ArrayList<>();

		for (HiddenRail hRail : hiddenRails.getHiddenRails()) {
			for (Entry<Location, Byte> entry : railsAndData.entrySet()) {
				Location loc = entry.getKey();

				if (loc.equals(hRail.getLocation())) {
					Block block = Bukkit.getWorld(worldName).getBlockAt(loc);
					BlockState state = block.getState();

					block.setType(block.getType());
					if (HideRails.version == Version.V1_12) {
						try {
							BukkitNMS.setData(block, entry.getValue().byteValue());
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						//block.setData(entry.getValue().byteValue());
					}
					state.update(true);

					hRails.add(hRail);
				}
			}
		}

		// Create new backup
		BlocksBackup bBackup = null;
		if (backup) {
			bBackup = new BlocksBackup();
			bBackup.setType(BackupType.UNHIDE);
		}

		for (HiddenRail rail : hRails) {
			hiddenRails.delHiddenRail(rail);

			if (backup) {
				// Save updated blocks in new backup to allow to go back after execution of /hiderails undo
				bBackup.addChangedBlocks(LocationsManager.serialize(rail.getLocation()));
				bBackup.setUnHideBlocksType(new MaterialData(rail.getMaterial(), rail.getData()));
			}
		}

		if (backup) {
			// Save the new blocksBackup to player
			PlayerCommandBackupManager.createNewBlocksBackup(player, bBackup);
		}

		// Save all hidden blocks (and remove the broken blocks)
		saveWorld(worldName);

		MessagesManager.sendPluginMessage(player, Messages.SUCCESS_UNHIDE_RAIL);
	}


	/**
	 * Save all hidden blocks (with command)
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

		e(player, targetBlock, null, input, backup, single);
	}

	private static void e(Player player, @Nonnull Block targetBlock, List<BlockReplacementType> types, String input, boolean backup, boolean single)
	{
		MaterialData matData = BlocksChecker.getMatData(player, input);
		Material mat = matData.getMat();

		if (mat != null)
			saveChangedBlocks(player, targetBlock, types, mat, matData.getData(), backup, single);
	}


	/**
	 * Hide one or many blocks (with command)
	 * 
	 * @param player
	 * @param targetBlock
	 * @param blockType
	 * @param mat
	 * @param data
	 * @param backup
	 * @param single
	 */
	protected static void saveChangedBlocks(Player player, @Nonnull Block targetBlock, List<BlockReplacementType> types, Material mat, byte data, boolean backup, boolean single)
	{
		List<Location> railsLocs = null;
		if (!single)
			railsLocs = LocationsManager.getConnectedBlocks(targetBlock.getLocation(), types);
		else
			railsLocs = Arrays.asList(targetBlock.getLocation());
		List<HiddenRail> railsList = new ArrayList<>();
		World world = targetBlock.getWorld();
		String worldName = world.getName();

		// Create new backup
		BlocksBackup bBackup = null;
		if (backup) {
			bBackup = new BlocksBackup();
			bBackup.setType(BackupType.HIDE);
		}

		// Store all serialized rails of 'worldName'
		List<String> hiddenRails = new ArrayList<>();
		hiddenRails = FileConfigurationManager.getHiddenRailsConfig().getStringList(HIDDEN_RAILS_PATH + "." + worldName);
		List<Location> hiddenRailsLocs = new ArrayList<>();

		for (String locStr : hiddenRails) {
			hiddenRailsLocs.add(LocationsManager.deserializeLoc(locStr));
		}

		for (Location loc : railsLocs) {
			if (!hiddenRailsLocs.contains(loc)) { // Avoid duplicates in the configuration file
				HiddenRail rail = new HiddenRail(mat, data);
				rail.setLocation(loc);
				railsList.add(rail);

				if (backup) {
					// Save updated blocks in new backup to back after /hiderails undo
					bBackup.addChangedBlocks(LocationsManager.serialize(loc));
				}

				// If version is not 1.8
				if (!HideRails.version.isOldVersion()) {
					// Spawn particle to see the block to hide
					world.spawnParticle(Particle.HEART, loc, 5);
				}
			}
		}

		if (backup) {
			// Save the new blocksBackup for player
			PlayerCommandBackupManager.createNewBlocksBackup(player, bBackup);
		}

		HiddenRailsWorld railsWorld = null;
		if (getWorldHiddenRails(worldName) == null) {
			railsWorld = new HiddenRailsWorld(world);
		} else {
			railsWorld = getWorldHiddenRails(worldName);
		}
		for (int i = 0; i < railsList.size(); i++)
			railsWorld.addHiddenRails(railsList.get(i));

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
	public static void hideSelectionBlocks(Player player, Cuboid selection, String input, boolean backup, List<BlockReplacementType> types)
	{
		MaterialData matData = BlocksChecker.getMatData(player, input);
		Material mat = matData.getMat();

		if (mat != null)
			hideSelectionBlocks(player, selection, mat, matData.getData(), backup, types);
	}

	private static void hideSelectionBlocks(Player player, @Nonnull Cuboid selection, Material mat, byte data, boolean backup, List<BlockReplacementType> types)
	{
		World world = selection.getWorld();
		String worldName = world.getName(); // Name of blocks replacement world

		List<Location> railsLocs = HideRailsSelectionChecker.getAllValidRails(selection, types); // Definitly Locations of blocks replacement

		// Create new backup
		BlocksBackup bBackup = null;
		if (backup) {
			bBackup = new BlocksBackup();
			bBackup.setType(BackupType.HIDE);
			bBackup.setBlocksType(types);
			bBackup.setHrSelection(selection);
		}

		// Storage of all serialized rails in world 'worldName'
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

				if (backup) {
					// Save updated blocks in new backup to back after /hiderails undo
					bBackup.addChangedBlocks(LocationsManager.serialize(loc));
				}

				// If version is not 1.8
				if (!HideRails.version.isOldVersion()) {
					// Spawn particle for see where is replacement
					world.spawnParticle(Particle.BARRIER, loc, 6);
				}
			}
		}

		if (backup) {
			// Save the new blocksBackup for player
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

		// Save all hidden blocks
		saveWorld(worldName);
	}



	/**
	 * Remove hidden blocks from HideRails selection
	 * 
	 * @param player
	 * @param selection
	 * @param backup
	 * @param types 
	 */
	@SuppressWarnings("deprecation")
	public static void removeSelectionBlocks(Player player, @Nonnull Cuboid selection, boolean backup, List<BlockReplacementType> types)
	{
		List<Location> railsLocs = HideRailsSelectionChecker.getAllValidRails(selection, types);

		HashMap<Location, Byte> railsAndData = new HashMap<>();
		String worldName = selection.getWorld().getName();

		// Create new backup
		BlocksBackup bBackup = null;
		if (backup) {
			bBackup = new BlocksBackup();
			bBackup.setType(BackupType.UNHIDE);
			bBackup.setBlocksType(types);
			bBackup.setHrSelection(selection);
		}

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
					Block block = Bukkit.getWorld(worldName).getBlockAt(entry.getKey());
					BlockState state = block.getState();
					block.setType(block.getType());
					if (HideRails.version == Version.V1_12) {
						try {
							BukkitNMS.setData(block, entry.getValue().byteValue());
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						//block.setData(entry.getValue().byteValue());
					}
					state.update(true);

					hRails.add(hRail);
				}
			}
		}

		for (HiddenRail rail : hRails) {
			hiddenRails.delHiddenRail(rail);

			if (backup) {
				// Save updated blocks in new backup to back after /hiderails undo
				bBackup.addChangedBlocks(LocationsManager.serialize(rail.getLocation()));
				bBackup.setUnHideBlocksType(new MaterialData(rail.getMaterial(), rail.getData()));
			}
		}

		if (backup) {
			// Save the new blocksBackup for player
			PlayerCommandBackupManager.createNewBlocksBackup(player, bBackup);
		}

		// Save all hidden blocks
		saveWorld(worldName);

		MessagesManager.sendPluginMessage(player, Messages.SUCCESS_UNHIDE_RAIL);
	}



	/**
	 * Save all changed blocks and changed rails in world "worldName"
	 * 
	 * @param worldName
	 */
	public static void saveWorld(@Nonnull String worldName)
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
