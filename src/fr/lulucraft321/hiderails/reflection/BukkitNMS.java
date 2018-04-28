/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.enums.ParticleName;

public class BukkitNMS
{
	public String version;

	/* BLOCKS */
	private static Class<?> block_change_class;
	private static Class<?> craftMagicNumbersClass;
	private static Class<?> block_class;
	private static Constructor<?> constructorBP;
	private static Method fromLegacyData_method;
	private static Method craftMagicNumbers_method;
	private static Field block_change_pos_field;
	private static Field block_field;

	/* PARTICLES */
	private static Class<?> packet_play_out_world_particles;
	private static Class<?> enum_particle_class;
	private static Constructor<?> packet_particles_constructor;
	private static Method particles_method;

	static {
		try {
			// ------------------------------------------------------ BLOCKS ------------------------------------------------------ //
			block_change_class = NMSClass.getNMSClass("PacketPlayOutBlockChange");
			craftMagicNumbersClass = NMSClass.getOBCClass("util.CraftMagicNumbers");
			block_class = NMSClass.getNMSClass("Block");

			constructorBP = NMSClass.getNMSClass("BlockPosition").getConstructor(double.class, double.class, double.class);

			block_change_pos_field = NMSClass.getField(block_change_class, "a");
			block_change_pos_field.setAccessible(true);

			// Get "IBlockData fromLegacy(int i)" method
			fromLegacyData_method = block_class.getMethod("fromLegacyData", int.class);
			// Get Method "getBlock(Material material)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material)
			craftMagicNumbers_method = craftMagicNumbersClass.getMethod("getBlock", Material.class);

			block_field = NMSClass.getField(block_change_class, "block");
			// ------------------------------------------------------------------------------------------------------------------- //


			// ---------------------------------------------------- PARTICLES ---------------------------------------------------- //
			packet_play_out_world_particles = NMSClass.getNMSClass("PacketPlayOutWorldParticles");
			enum_particle_class = NMSClass.getNMSClass("EnumParticle");
			try {
				packet_particles_constructor = NMSClass.getConstructor(packet_play_out_world_particles,
						enum_particle_class, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class);

				// Get Method a() in Paticle Class
				particles_method = enum_particle_class.getDeclaredMethod("a", String.class);
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			// ------------------------------------------------------------------------------------------------------------------- //
		} catch (SecurityException | NoSuchFieldException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public BukkitNMS() {
		this.version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	}


	/**
	 * Hide block for player
	 * 
	 * @param player
	 * @param material
	 * @param data
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void changeBlock(Player p, Material material, byte data, int x, int y, int z)
	{
		try {
			/*
			 * Instanciation du packet PacketPlayOutBlockChange
			 */
			Object packet = block_change_class.newInstance();

			// Set blockChange location
			Object blockPosition = constructorBP.newInstance(x, y, z);
			block_change_pos_field.set(packet, blockPosition);

			/*
			 * Replace "CraftMagicNumbers.getBlock(material).fromLegacyData(data)"
			 */
			// Invoke Method "getBlock(Material material)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material)
			Object craftMagicNumber = craftMagicNumbers_method.invoke(fromLegacyData_method, material);
			// Get final IBlockData
			Object block_data = fromLegacyData_method.invoke(craftMagicNumber, data);


			/*
			 * Set "block" field in PacketPlayOutBlockChange Class ("packet.block")
			 */
			block_field.set(packet, block_data);

			/*
			 * Send Packet to player
			 */
			sendPacket(p, packet);
		} catch (IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Summon particles at particleLoc
	 * 
	 * @param player
	 * @param particleLoc
	 * @param particleName
	 * @param amount
	 * @param speed
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static void summonParticle(Player p, Location loc, ParticleName particleName, int amount, int speed) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException
	{
		Object packet =
				NMSClass.newInstance(
						packet_particles_constructor,
						particles_method.invoke(enum_particle_class, particleName.getParticleName()),
						true,
						(float) loc.getX(), (float) loc.getY(), (float) loc.getZ(),
						0f, 0f, 0f,
						0f,
						amount,
						new int[]{speed}
						);

		sendPacket(p, packet);
	}


	/**
	 * Send packet to player
	 * 
	 * @param player
	 * @param packet
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	private static void sendPacket(Player player, Object packet) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException
	{
		Object handle = NMSClass.getMethod(player.getClass(), "getHandle").invoke(player);
		Object playerConnection = NMSClass.getField(
				handle.getClass(),
				"playerConnection").get(handle);

		NMSClass.invokeMethod(
				NMSClass.getMethod(playerConnection.getClass(), "sendPacket", NMSClass.getNMSClass("Packet")),
				playerConnection, packet);
	}
}
