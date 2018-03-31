package fr.lulucraft321.hiderails.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.manager.HideRailsManager;
import fr.lulucraft321.hiderails.reflection.NMSClass;

public class BlockChangeRunner extends BukkitRunnable
{
	private static Field block_change_pos_field;

	static {
		try {
			block_change_pos_field = NMSClass.getNMSClass("PacketPlayOutBlockChange").getDeclaredField("a");//PacketPlayOutBlockChange.class.getDeclaredField("a");
			block_change_pos_field.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}

	private void changeBlock(Player p, Material material, byte data, int x, int y, int z)
	{
		try {
			/*
			 * Instanciation du packet PacketPlayOutBlockChange
			 */
			Object packet = NMSClass.getNMSClass("PacketPlayOutBlockChange").newInstance();

			// Set blockChange location
			Class<?> blockPositionClass = NMSClass.getNMSClass("BlockPosition");
			Constructor<?> constructorBP = blockPositionClass.getConstructor(double.class, double.class, double.class);
			Object blockPosition = constructorBP.newInstance(x, y, z);
			block_change_pos_field.set(packet, blockPosition);

			/*
			 * Replace "CraftMagicNumbers.getBlock(material).fromLegacyData(data)"
			 */
			Class<?> craftMagicNumbersClass = NMSClass.getOBCClass("util.CraftMagicNumbers");
			Class<?> blockClass = NMSClass.getNMSClass("Block");

			// Get "IBlockData fromLegacy(int i)" method
			Object fromLegacyData = blockClass.getMethod("fromLegacyData", int.class);
			// Method "getBlock(Material material)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material)
			Object craftMagicNumber = craftMagicNumbersClass.getMethod("getBlock", Material.class).invoke(fromLegacyData, material);
			// Get final IBlockData
			Object block_data = blockClass.getMethod("fromLegacyData", int.class).invoke(craftMagicNumber, data);


			/*
			 * Set "block" field in PacketPlayOutBlockChange Class ("packet.block")
			 */
			Field field = packet.getClass().getDeclaredField("block");
			field.set(packet, block_data);

			/*
			 * Send Packet
			 */
			Object handle = p.getClass().getMethod("getHandle").invoke(p);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", NMSClass.getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch (IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	public void run()
	{
		for(HiddenRailsWorld hWorld : HideRailsManager.rails)
		{
			World world = HideRails.getInstance().getServer().getWorld(hWorld.getWorldName());

			if(world != null)
			{
				for(HiddenRail rail : hWorld.getHiddenRails())
				{
					MaterialData mats = new MaterialData(rail.getMaterial(), rail.getData());
					List<Player> players = world.getPlayers();

					for(Player p : players)
					{
						Location railLoc = rail.getLocation();
						changeBlock(p, mats.getMat(), mats.getData(), railLoc.getBlockX(), railLoc.getBlockY(), railLoc.getBlockZ());
					}
				}
			}
		}
	}
}
