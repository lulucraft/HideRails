/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.events.RedstoneInWaterEvents;
import fr.lulucraft321.hiderails.utils.BlockChangeRunner;
import fr.lulucraft321.hiderails.utils.Checker;
import fr.lulucraft321.hiderails.utils.HiddenRail;
import fr.lulucraft321.hiderails.utils.HiddenRailsWorld;
import fr.lulucraft321.hiderails.utils.Messages;

public class HideRailsManager
{
	// List of HiddenRails per world
	public static List<HiddenRailsWorld> rails = new ArrayList<>();

	// Path to hiddenRails in HiddenRails.yml
	public static String path = "hiddenRails";

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
				HiddenRail rail = new HiddenRail(deserializeMatInSerializedLoc(loc), deserializeDataInSerializedLoc(loc));
				rail.setLocation(deserializeLoc(loc));
				hiddenRails.add(rail);
				world = rail.getLocation().getWorld();
			}

			if(world != null)
				if(hiddenRails != null)
					new HiddenRailsWorld(world, hiddenRails);
		}

		// Activation du changement des rails
		if(HideRails.getInstance().getConfig().getBoolean("hideRails")) {
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


	/*
	 * Get all connected rails
	 */
	private static List<Location> getConnectedRails(Location startLoc)
	{
		boolean checkFinished = false;
		int next = 0;
		int finishCheckCurrentBlock = 0;
		List<Location> checked = new ArrayList<>();
		final BlockFace[] faces = new BlockFace[]{ BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

		checked.add(startLoc);

		while(!checkFinished)
		{
			if(next < checked.size())
			{
				Block newBlock = (Block) checked.get(next).getBlock();

				for(BlockFace blockFace : faces)
				{
					Block newCheckBlock = newBlock.getRelative(blockFace);

					if(newCheckBlock == null) continue;
					if(checked.contains(newCheckBlock.getLocation())) continue;
					if(checked.contains(newCheckBlock)) continue;

					if(Checker.isRail(newCheckBlock))
					{
						checked.add(newCheckBlock.getLocation());
						newCheckBlock.getLocation().getWorld().spawnParticle(Particle.HEART, newCheckBlock.getLocation(), 5);

						finishCheckCurrentBlock = 0;
					} else {
						finishCheckCurrentBlock+=1;
					}

					if(finishCheckCurrentBlock == 4){
						checkFinished = true;
						next = 0;
						break;
					}
				}

				// Fin du check si toutes les faces du newBlockCheck == null
				if(finishCheckCurrentBlock == 4){
					checkFinished = true;
					next = 0;
					break;
				}
			} else {
				finishCheckCurrentBlock = 0;
				checkFinished = true;
				next = 0;
				break;
			}

			next++;
		}

		return checked;
	}

	@SuppressWarnings("deprecation")
	public static void removeRails(Player player, Block targetBlock)
	{
		List<Location> railsLocs = getConnectedRails(targetBlock.getLocation());
		HashMap<Location, Byte> railsAndData = new HashMap<>();
		String worldName = targetBlock.getWorld().getName();

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
					rail.setData(entry.getValue());

					hRails.add(hRail);
				}
			}
		}

		for(HiddenRail rail : hRails) {
			hiddenRails.delHiddenRail(rail);
		}

		saveWorld(worldName);

		MessagesManager.sendPluginMessage(player, Messages.SUCCESS_UNHIDE_RAIL);
	}


	public static void saveChangedRails(Block targetBlock, Material mat, byte data)
	{
		List<Location> railsLocs = getConnectedRails(targetBlock.getLocation());
		List<HiddenRail> railsList = new ArrayList<>();
		World world = targetBlock.getWorld();

		for(Location loc : railsLocs) {
			HiddenRail rail = new HiddenRail(mat, data);
			rail.setLocation(loc);
			railsList.add(rail);
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

	public static void saveWorld(String worldName)
	{
		HiddenRailsWorld railsWorld = getWorldHiddenRails(worldName);
		List<HiddenRail> railsList = new ArrayList<>();
		railsList = railsWorld.getHiddenRails();
		List<String> railsLocs = new ArrayList<>();
		HideRails.getInstance().getHiddenRailsConfig().options().copyDefaults(true);

		for(HiddenRail rails : railsList) {
			railsLocs.add(serialize(rails.getLocation()) + ";" + rails.getMaterial() + ";" + rails.getData());
		}

		HideRails.getInstance().getHiddenRailsConfig().set(path + "." + worldName, railsLocs);
		HideRails.getInstance().saveConfigs();
	}


	/*
	 * Serialisation
	 */
	public static String serialize(Location loc)
	{
		String splitter = ",";
		return loc.getWorld().getName() + splitter  + loc.getX() + splitter + loc.getY() + splitter + loc.getZ() + splitter + loc.getYaw() + splitter + loc.getPitch();
	}

	private static Location deserializeLoc(String loc)
	{
		String[] split = loc.split(",");
		String[] last = split[5].split(";");
		World world = Bukkit.getServer().getWorld(split[0]);
		double x = Double.parseDouble(split[1]);
		double y = Double.parseDouble(split[2]);
		double z = Double.parseDouble(split[3]);
		float yaw = Float.parseFloat(split[4]);
		float pitch = Float.parseFloat(last[0]);
		return new Location(world, x, y, z, yaw, pitch);
	}

	private static Material deserializeMatInSerializedLoc(String loc)
	{
		String[] split = loc.split(",");
		String[] last = split[5].split(";");
		return Material.getMaterial(last[1]);
	}

	private static byte deserializeDataInSerializedLoc(String loc)
	{
		String[] split = loc.split(",");
		String[] last = split[5].split(";");
		return Byte.parseByte(last[2]);
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
