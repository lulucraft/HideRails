/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.reflection;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import fr.lulucraft321.hiderails.HideRails;
import fr.lulucraft321.hiderails.enums.Version;
import fr.lulucraft321.hiderails.enums.particles.ParticleName_v1_12;
import fr.lulucraft321.hiderails.enums.particles.ParticleName_v1_13;
import fr.lulucraft321.hiderails.enums.particles.ParticleName_v1_15;

public class BukkitNMS
{
	public final String VERSION;

	/* General */
	private static Class<?> packet_class;

	/* BLOCKS */
	private static Class<?> block_change_class;
	private static Class<?> craftMagicNumbersClass;
	private static Class<?> block_class;
	private static Constructor<?> constructorBP;
	private static Method fromLegacyData_method;
	private static Method craftMagicNumbers_method_with_data;
	private static Method craftMagicNumbers_method_without_data;
	//private static Method block_data_method; // Only for 1.10
	private static Field block_change_pos_field;
	private static Field block_field;

	/* PARTICLES */
	private static Class<?> packet_play_out_world_particles;
	private static Class<?> enum_particle_class;
	private static Constructor<?> packet_particles_constructor;
	private static Method particles_method;

	/* CONFIGURATIONS */
	private static Class<?> fileUtils_class;
	private static Method readFileContent_method;
	private static Method writeFileContent_method;


	static {
		packet_class = NMSClass.getNMSClass("Packet");
		try {
			// ----------------------------------------------------- BLOCKS ------------------------------------------------------ //
			block_change_class = NMSClass.getNMSClass("PacketPlayOutBlockChange");
			craftMagicNumbersClass = NMSClass.getOBCClass("util.CraftMagicNumbers");
			block_class = NMSClass.getNMSClass("Block");

			constructorBP = NMSClass.getNMSClass("BlockPosition").getConstructor(double.class, double.class, double.class);

			block_change_pos_field = NMSClass.getField(block_change_class, "a");
			block_change_pos_field.setAccessible(true);

			if (HideRails.version == Version.V1_12) {
				// Get "IBlockData fromLegacy(int i)" method
				fromLegacyData_method = NMSClass.getMethod(block_class, "fromLegacyData", int.class);

				// Get Method "getBlock(Material material)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material)
				craftMagicNumbers_method_with_data = NMSClass.getMethod(craftMagicNumbersClass, "getBlock", Material.class);
			} else if (HideRails.version == Version.V1_13 || HideRails.version == Version.V1_14 || HideRails.version == Version.V1_15) {
				if (HideRails.version == Version.V1_14) {
					// Get "IBlockData getBlockData()" method
					//block_data_method = NMSClass.getMethod(block_class, "getBlockData", null);

					// Get Method "getBlock(Material material)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material)
					craftMagicNumbers_method_without_data = NMSClass.getMethod(craftMagicNumbersClass, "getBlock", Material.class);
				}

				// Get Method "getBlock(Material material, byte data)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material, data)
				craftMagicNumbers_method_with_data = NMSClass.getMethod(craftMagicNumbersClass, "getBlock", Material.class, byte.class);
			}

			block_field = NMSClass.getField(block_change_class, "block");
			// ------------------------------------------------------------------------------------------------------------------- //


			// ---------------------------------------------------- PARTICLES ---------------------------------------------------- //
			packet_play_out_world_particles = NMSClass.getNMSClass("PacketPlayOutWorldParticles");
			if (HideRails.version == Version.V1_12 && !Version.V1_12.isOldVersion()) {
				enum_particle_class = NMSClass.getNMSClass("EnumParticle");
				try {
					packet_particles_constructor = NMSClass.getConstructor(packet_play_out_world_particles,
							enum_particle_class, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class);

					// Get Method a() in Paticle Class
					particles_method = NMSClass.getMethod(enum_particle_class, "a", String.class);
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
			} else if (HideRails.version == Version.V1_13) {
				enum_particle_class = NMSClass.getNMSClass("ParticleParam");
				try {
					packet_particles_constructor = NMSClass.getConstructor(packet_play_out_world_particles,
							enum_particle_class, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class);
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
			}
			// ------------------------------------------------------------------------------------------------------------------- //


			// -------------------------------------------------- CONFIGURATIONS ------------------------------------------------- //
			if (HideRails.version == Version.V1_14 || HideRails.version == Version.V1_15) fileUtils_class = Class.forName("org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils");
			else fileUtils_class = Class.forName("org.apache.commons.io.FileUtils");
			readFileContent_method = NMSClass.getMethod(fileUtils_class, "readFileToString", File.class, Charset.class); // params : File.class, Charset.class / return : String.class
			writeFileContent_method = NMSClass.getMethod(fileUtils_class, "write", File.class, CharSequence.class, Charset.class); // params : File.class, CharSequence.class, Charset.class / return : void
			// ------------------------------------------------------------------------------------------------------------------- //
		} catch (SecurityException | NoSuchFieldException | NoSuchMethodException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public BukkitNMS() {
		VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		if (VERSION.contains("1_13")) HideRails.version = Version.V1_13;
		else if (VERSION.contains("1_14")) HideRails.version = Version.V1_14;
		else if (VERSION.contains("1_15")) HideRails.version = Version.V1_15;
		else if (VERSION.contains("1_16")) HideRails.version = Version.V1_15;
		else HideRails.version = Version.V1_12;

		if (HideRails.version == Version.V1_12) {
			if (VERSION.contains("1_8")) {
				Version.V1_12.setOldVersion(true);
			}
		}
	}


	/**
	 * Read file content
	 * 
	 * @param configFile
	 * @return fileContent
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static String readFileContent(File configFile) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		return (String) NMSClass.invokeMethod(readFileContent_method, "readFileToString", configFile, StandardCharsets.UTF_8);
	}

	/**
	 * Write file content
	 * 
	 * @param configFile
	 * @param fileContext
	 * @return String
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static String writeFileContent(File configFile, CharSequence fileContext) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		return (String) NMSClass.invokeMethod(writeFileContent_method, "write", configFile, fileContext, StandardCharsets.UTF_8);
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
		if (material == null) { // If material is LEGACY or invalid in HiddenRails config file (Only for older versions than 1.14)
			System.err.println("[HideRails] <ERREUR> VOTRE FICHIER 'HiddenRails.yml' CONTIENT DES BLOCKS INVALIDES, VEUILLEZ METTRE A JOUR LES BLOCKS 'LEGAGY' OU SUPPRIMER VOTRE CONFIG !!");
			return;
		}

		String baseMatName = material.name();

		// Adapt material name to good versions
		String matName = baseMatName.replace("LEGACY_", "");
		material = Material.matchMaterial(matName);

		Object packet = null;
		Object block_data = null;
		try {
			/*
			 * Instanciation du packet PacketPlayOutBlockChange
			 */
			packet = NMSClass.newInstance(block_change_class);

			// Set blockChange location
			Object blockPosition = NMSClass.newInstance(constructorBP, x, y, z);
			block_change_pos_field.set(packet, blockPosition);

			/*
			 * Replace "CraftMagicNumbers.getBlock(material).fromLegacyData(data)"
			 */
			// Get final IBlockData

			if (HideRails.version == Version.V1_12) {
				// Invoke Method "getBlock(Material material)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material)
				Object craftMagicNumber = NMSClass.invokeMethod(craftMagicNumbers_method_with_data, fromLegacyData_method, material);
				block_data = NMSClass.invokeMethod(fromLegacyData_method, craftMagicNumber, data);
			} else if (HideRails.version == Version.V1_13 || HideRails.version == Version.V1_14 || HideRails.version == Version.V1_15) {
				// Invoke Method "getBlock(Material material)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material)
				//Object craftMagicNumber = NMSClass.invokeMethod(craftMagicNumbers_method, null, material);

				// replace "block_data = CraftMagicNumbers.getBlock(material).getBlockData();"
				//Object iMe = NMSClass.invokeMethod(craftMagicNumbers_method, craftMagicNumber, material);
				//block_data = NMSClass.invokeMethod(block_data_method, iMe, null);

				// Invoke Method "getBlock(Material material, byte data)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material, data)
				// replace "block_data = CraftMagicNumbers.getBlock(material, data);"
				block_data = NMSClass.invokeMethod(craftMagicNumbers_method_with_data, null, material, data);
			}

			/*
			 * Set "block" field in PacketPlayOutBlockChange Class ("packet.block")
			 */
			block_field.set(packet, block_data);

			/*
			 * Send Packet to player
			 */
			sendPacket(p, packet);
		}
		catch (IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e)
		{
			if (e instanceof InvocationTargetException) {
				/* If material is LEGACY or invalid */
				if (HideRails.version == Version.V1_13) {
					System.err.println("[HideRails] <ERREUR> VOTRE FICHIER 'HiddenRails.yml' CONTIENT DES BLOCKS INVALIDES, VEUILLEZ METTRE A JOUR LE BLOCK '" + baseMatName + "' OU SUPPRIMER VOTRE CONFIG !!!");
				} else if (HideRails.version == Version.V1_14) {
					/* Try without data */
					try {
						// Invoke Method "getBlock(Material material)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material)
						// replace "block_data = CraftMagicNumbers.getBlock(material).getBlockData();"
						block_data = NMSClass.invokeMethod(craftMagicNumbers_method_without_data, null, material);

						/* Set "block" field in PacketPlayOutBlockChange Class ("packet.block") */
						block_field.set(packet, block_data);

						/* Send Packet to player */
						sendPacket(p, packet);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e1) {
						if (e instanceof InvocationTargetException)
							System.err.println("[HideRails] <ERREUR> VOTRE FICHIER 'HiddenRails.yml' CONTIENT DES BLOCKS INVALIDES, VEUILLEZ METTRE A JOUR LE BLOCK '" + baseMatName + "' OU SUPPRIMER VOTRE CONFIG !!!");
						else
							e.printStackTrace();
					}
				} else if (HideRails.version == Version.V1_15) {
					if (e instanceof InvocationTargetException)
						System.err.println("[HideRails] <ERREUR> VOTRE FICHIER 'HiddenRails.yml' CONTIENT DES BLOCKS INVALIDES, VEUILLEZ METTRE A JOUR LE BLOCK '" + baseMatName + "' OU SUPPRIMER VOTRE CONFIG !!!");
					else
						e.printStackTrace();
				} else {
					e.printStackTrace();
				}
				//System.err.println("\nERREUR : " + e.getClass() + "\nCAUSE : " + e.getCause());
			} else {
				e.printStackTrace();
			}
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
	@SuppressWarnings("unchecked")
	public static void summonParticle(Player p, Location loc, Object particleName, int amount, int speed) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException
	{
		// If version is 1.8
		if (HideRails.version.isOldVersion()) return;

		if (HideRails.version == Version.V1_12) {
			Object packet =
					NMSClass.newInstance(
							packet_particles_constructor,
							NMSClass.invokeMethod(particles_method, enum_particle_class, ((ParticleName_v1_12) particleName).getParticleName()),
							true,
							(float) loc.getX(), (float) loc.getY(), (float) loc.getZ(),
							0f, 0f, 0f,
							0f,
							amount,
							new int[]{speed}
							);
			sendPacket(p, packet);
		} else if (HideRails.version == Version.V1_13 || HideRails.version == Version.V1_14) {
			p.spawnParticle(Particle.valueOf(((Enum<ParticleName_v1_13>) particleName).name().toUpperCase()), loc.getX(), loc.getY(), loc.getZ(), 0, 0d, 0d, 0d, amount);
		} else if (HideRails.version == Version.V1_15) {
			p.spawnParticle(Particle.valueOf(((Enum<ParticleName_v1_15>) particleName).name().toUpperCase()), loc.getX(), loc.getY(), loc.getZ(), 0, 0d, 0d, 0d, amount);
		}
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
		// Stop console errors (pending player connection)
		if (!player.isOnline())
			return;
		Object playerConnection = NMSClass.getField(
				handle.getClass(),
				"playerConnection").get(handle);

		NMSClass.invokeMethod(
				NMSClass.getMethod(playerConnection.getClass(), "sendPacket", packet_class),
				playerConnection, packet);
	}
}