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
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.BlockChangeRunner;
import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.enums.BackupType;
import fr.lulucraft321.hiderails.enums.BlockReplacementType;
import fr.lulucraft321.hiderails.events.RedstoneInWaterEvents;
import fr.lulucraft321.hiderails.utils.Checker;
import fr.lulucraft321.hiderails.utils.HiddenRail;
import fr.lulucraft321.hiderails.utils.HiddenRailsWorld;
import fr.lulucraft321.hiderails.utils.MaterialData;
import fr.lulucraft321.hiderails.utils.Messages;
import fr.lulucraft321.hiderails.utils.backuputility.BlocksBackup;

public class HideRailsManager
{
	// List of HiddenRails per world
	public static List<HiddenRailsWorld> rails = new ArrayList<>();

	// Path to hiddenRails in HiddenRails.yml
	public static String path = "hiddenRails";

	public static boolean hb;
	public static boolean hr;

	/*
	 * Config
	 */
	public static void loadHideRails()
	{
		Configuration config = HideRails.getInstance().getHiddenRailsConfig();

		if(config.getConfigurationSection(path) == null) return;

		HideRailsManager.rails.clear();

		for(String keys : config.getConfigurationSection(path).getKeys(false))
		{
			List<HiddenRail> hiddenRails = new ArrayList<>();
			World world = null;

			for(String loc : config.getStringList(path + "." + keys))
			{
				HiddenRail rail = new HiddenRail(LocationsManager.deserializeMatInSerializedLoc(loc), LocationsManager.deserializeDataInSerializedLoc(loc));
				rail.setLocation(LocationsManager.deserializeLoc(loc));
				hiddenRails.add(rail);
				world = rail.getLocation().getWorld();
			}

			if(world != null)
				if(hiddenRails != null)
					new HiddenRailsWorld(world, hiddenRails);
		}

		// Activation du changement des rails ou des barreaux
		if(hr || hb) {
			new BlockChangeRunner().runTaskTimer(HideRails.getInstance(), 20 * HideRails.getInstance().getConfig().getInt("hideRails.time"), 100L);
		}
	}


	public static HiddenRailsWorld getWorldHiddenRails(String worldName)
	{
		for(HiddenRailsWorld worldHiddenRails : rails) {
			if(worldHiddenRails.getWorldName().equalsIgnoreCase(worldName)) {
				return worldHiddenRails;
			}
		}
		return null;
	}

	public static List<HiddenRail> getRailsToWorld(String worldName)
	{
		HiddenRailsWorld world = getWorldHiddenRails(worldName);

		if(world != null) {
			if(!world.getHiddenRails().isEmpty()) {
				return world.getHiddenRails();
			}
		}
		return null;
	}


	@SuppressWarnings("deprecation")
	public static void removeBlocks(Player player, Block targetBlock, boolean backup, boolean single)
	{
		BlockReplacementType blockType = Checker.getBlockReplacementType(player, targetBlock);

		List<Location> railsLocs = null;
		if(!single)
			railsLocs = LocationsManager.getConnectedBlocks(targetBlock.getLocation(), blockType);
		else
			railsLocs = Arrays.asList(targetBlock.getLocation());
		HashMap<Location, Byte> railsAndData = new HashMap<>();
		String worldName = targetBlock.getWorld().getName();

		// Creation d'un nouveau backup
		BlocksBackup bBackup = new BlocksBackup();
		bBackup.setType(BackupType.UNHIDE);

		for(Location rail : railsLocs) {
			railsAndData.put(rail, Bukkit.getWorld(worldName).getBlockAt(rail).getData());
		}

		HiddenRailsWorld hiddenRails = getWorldHiddenRails(worldName);
		List<HiddenRail> hRails = new ArrayList<>();

		if(hiddenRails == null) return;
		if(hiddenRails.getHiddenRails().isEmpty()) return;

		for(HiddenRail hRail : hiddenRails.getHiddenRails())
		{
			for(Entry<Location, Byte> entry : railsAndData.entrySet())
			{
				if(entry.getKey().equals(hRail.getLocation()))
				{
					Block rail = Bukkit.getWorld(worldName).getBlockAt(entry.getKey());

					rail.setType(rail.getType());
					rail.setData(entry.getValue().byteValue());
					rail.getState().update(true);

					hRails.add(hRail);
				}
			}
		}

		for(HiddenRail rail : hRails) {
			hiddenRails.delHiddenRail(rail);

			// Sauveguarde des blocs changés dans le nouveau backup pour pouvoir retourner en arriere apres /hiderails undo
			bBackup.addChangedBlocks(LocationsManager.serialize(rail.getLocation()));
			bBackup.setUnHideBlocksType(new MaterialData(rail.getMaterial(), rail.getData()));
		}

		if(backup) {
			// Save the new blocksBackup to player
			PlayerCommandBackupManager.createNewBlocksBackup(player, bBackup);
		}

		saveWorld(worldName);

		MessagesManager.sendPluginMessage(player, Messages.SUCCESS_UNHIDE_RAIL);
	}


	public static void saveChangedBlocks(Player player, String input, boolean backup, boolean single)
	{
		Block targetBlock = player.getTargetBlock((Set<Material>) null, 25);

		e(player, targetBlock, Checker.getBlockReplacementType(player, targetBlock), input, backup, single);
	}

	private static void e(Player player, Block targetBlock, BlockReplacementType blockType, String input, boolean backup, boolean single)
	{
		String in = input;
		MaterialData matData = Checker.getMatData(player, in);
		byte data = matData.getData();
		Material mat = matData.getMat();

		if(mat != null)
			HideRailsManager.saveChangedBlocks(player, targetBlock, blockType, mat, data, backup, single);
	}

	protected static void saveChangedBlocks(Player player, Block targetBlock, BlockReplacementType blockType, Material mat, byte data, boolean backup, boolean single)
	{
		List<Location> railsLocs = null;
		if(!single)
			railsLocs = LocationsManager.getConnectedBlocks(targetBlock.getLocation(), blockType);
		else
			railsLocs = Arrays.asList(targetBlock.getLocation());
		List<HiddenRail> railsList = new ArrayList<>();
		World world = targetBlock.getWorld();

		// Creation d'un nouveau backup
		BlocksBackup bBackup = new BlocksBackup();
		bBackup.setType(BackupType.HIDE);

		for(Location loc : railsLocs) {
			HiddenRail rail = new HiddenRail(mat, data);
			rail.setLocation(loc);
			railsList.add(rail);

			// Sauveguarde des blocs changés dans le nouveau backup pour pouvoir retourner en arriere apres /hiderails undo
			bBackup.addChangedBlocks(LocationsManager.serialize(loc));
		}

		if(backup) {
			// Save the new blocksBackup to player
			PlayerCommandBackupManager.createNewBlocksBackup(player, bBackup);
		}

		HiddenRailsWorld railsWorld = null;
		if(getWorldHiddenRails(world.getName()) == null)
		{
			railsWorld = new HiddenRailsWorld(world);
		} else {
			railsWorld = getWorldHiddenRails(world.getName());
		}
		for(int i = 0; i < railsList.size(); i++) railsWorld.addHiddenRails(railsList.get(i));

		saveWorld(world.getName());
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

		for(HiddenRail rails : railsList) {
			railsLocs.add(LocationsManager.serialize(rails.getLocation()) + ";" + rails.getMaterial() + ";" + rails.getData());
		}

		HideRails.getInstance().getHiddenRailsConfig().set(path + "." + worldName, railsLocs);
		HideRails.getInstance().saveConfigs();
	}



	/*
	 * Water redstone protection
	 */
	public static void setWaterProtection(Player p, String worldName, boolean b)
	{
		String path = RedstoneInWaterEvents.path + "." + worldName;

		if(HideRails.getInstance().getConfig().contains(path))
		{
			if(HideRails.getInstance().getConfig().getBoolean(path) != b)
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
}
