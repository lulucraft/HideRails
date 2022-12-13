/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.nms;

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
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import fr.nepta.hiderails.HideRails;
import fr.nepta.hiderails.enums.EnumDirection;
import fr.nepta.hiderails.enums.Version;
import fr.nepta.hiderails.enums.particles.ParticleName_v1_12;
import fr.nepta.hiderails.enums.particles.ParticleName_v1_13;
import fr.nepta.hiderails.enums.particles.ParticleName_v1_15;
import fr.nepta.hiderails.listeners.PacketListener;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;

public class BukkitNMS
{
	static final String VERSION;

	/* General */
	private static Class<?> player_connection_class;
	private static Method handle_method;
	private static Method sendPacket_method;
	private static Method block_position_method;
	private static Field playerConnection_field;
	private static Field networkManager_field;
	private static Field channel_field;

	/* BLOCKS */
	private static Class<?> block_change_class;
	private static Class<?> craftMagicNumbersClass;
	private static Class<?> block_class;
	private static Class<?> base_block_position_class;
	private static Class<?> block_position_class;
	private static Constructor<?> constructorBP;
	private static Constructor<?> block_change_constructor;// Only for 1.17+
	private static Method fromLegacyData_method;
	private static Method craftMagicNumbers_method_with_data;
	private static Method craftMagicNumbers_method_without_data;
	//private static Method block_data_method; // Only for 1.10
	private static Method setData_method;
	private static Method get_x_base_block_position_method;
	private static Method get_y_base_block_position_method;
	private static Method get_z_base_block_position_method;
	private static Method get_direction_method;
	private static Field block_change_pos_field;
	private static Field block_field;

	private static Class<?> packet_play_out_in_use_item;
	private static Class<?> movingObjectPositionBlock_class;
	private static Method moving_object_position_block_field;

	/* PARTICLES */
	private static Class<?> packet_play_out_world_particles;
	private static Class<?> enum_particle_class;
	private static Constructor<?> packet_particles_constructor;
	private static Method particles_method;

	/* CONFIGURATION FILES */
	private static Class<?> fileUtils_class;
	private static Method readFileContent_method;
	private static Method writeFileContent_method;


	static {
		/*
		 * Init version
		 */
		VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		if (VERSION.contains("1_13")) HideRails.version = Version.V1_13;
		else if (VERSION.contains("1_14")) HideRails.version = Version.V1_14;
		else if (VERSION.contains("1_15") || VERSION.contains("1_16")) HideRails.version = Version.V1_15;
		else if (VERSION.contains("1_17")) HideRails.version = Version.V1_17;
		else HideRails.version = Version.V1_12;

		if (HideRails.version == Version.V1_12 && VERSION.contains("1_8")) {
			Version.V1_12.setOldVersion(true);
		}

		/*
		 * Init packets classes, fields and methods
		 */
		try {
			// Replace : ((CraftPlayer) player).getHandle()
			handle_method = NMSClass.getMethod(NMSClass.getOBCClass("entity.CraftPlayer"), "getHandle");

			if (HideRails.version == Version.V1_17) {
				player_connection_class = NMSClass.getNMSClass("network.PlayerConnection");

				// Replace : ((CraftPlayer) player).getHandle().c
				playerConnection_field = NMSClass.getField(NMSClass.getNMSClass("level.EntityPlayer"), "b");

				// Replace : ((CraftPlayer) player).getHandle().playerConnection.a
				networkManager_field = player_connection_class.getDeclaredField("a");

				// Replace : ((CraftPlayer) player).getHandle().playerConnection.networkManager.k
				channel_field = NMSClass.getNMNClass("NetworkManager").getDeclaredField("k");

				// Replace : ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
				sendPacket_method = NMSClass.getMethod(player_connection_class, "sendPacket", NMSClass.getNMNClass("protocol.Packet"));


				// ----------------------------------------------------- BLOCKS ------------------------------------------------------ //
				block_change_class = NMSClass.getNMNClass("protocol.game.PacketPlayOutBlockChange");
				block_position_class = NMSClass.getNMCClass("BlockPosition");

				block_field = NMSClass.getField(block_change_class, "b");

				constructorBP = block_position_class.getConstructor(double.class, double.class, double.class);

				block_change_constructor = block_change_class.getConstructor(block_position_class, NMSClass.getNMWClass("level.block.state.IBlockData"));

				/* PacketPlayInUseItem */
				packet_play_out_in_use_item = NMSClass.getNMNClass("protocol.game.PacketPlayInUseItem");
				movingObjectPositionBlock_class = NMSClass.getNMWClass("phys.MovingObjectPositionBlock");
				base_block_position_class = NMSClass.getNMCClass("BaseBlockPosition");
				// ------------------------------------------------------------------------------------------------------------------- //

				// ---------------------------------------------------- PARTICLES ---------------------------------------------------- //
				packet_play_out_world_particles = NMSClass.getNMNClass("protocol.game.PacketPlayOutWorldParticles");
				// ------------------------------------------------------------------------------------------------------------------- //

			} else {
				player_connection_class = NMSClass.getNMSClass("PlayerConnection");

				// Replace : ((CraftPlayer) player).getHandle().playerConnection
				playerConnection_field = NMSClass.getField(NMSClass.getNMSClass("EntityPlayer"), "playerConnection");

				// Replace : ((CraftPlayer) player).getHandle().playerConnection.networkManager
				networkManager_field = player_connection_class.getDeclaredField("networkManager");

				// Replace : ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel
				channel_field = NMSClass.getNMSClass("NetworkManager").getDeclaredField("channel");

				// Replace : ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
				sendPacket_method = NMSClass.getMethod(player_connection_class, "sendPacket", NMSClass.getNMSClass("Packet"));


				// ----------------------------------------------------- BLOCKS ------------------------------------------------------ //
				block_change_class = NMSClass.getNMSClass("PacketPlayOutBlockChange");
				block_class = NMSClass.getNMSClass("Block");
				block_position_class = NMSClass.getNMSClass("BlockPosition");

				block_field = NMSClass.getField(block_change_class, "block");

				constructorBP = block_position_class.getConstructor(double.class, double.class, double.class);

				/* PacketPlayInUseItem */
				packet_play_out_in_use_item = NMSClass.getNMSClass("PacketPlayInUseItem");
				movingObjectPositionBlock_class = NMSClass.getNMSClass("MovingObjectPositionBlock");
				base_block_position_class = NMSClass.getNMSClass("BaseBlockPosition");
				// ------------------------------------------------------------------------------------------------------------------- //

				// ---------------------------------------------------- PARTICLES ---------------------------------------------------- //
				packet_play_out_world_particles = NMSClass.getNMSClass("PacketPlayOutWorldParticles");
				// ------------------------------------------------------------------------------------------------------------------- //

			}


			// ----------------------------------------------------- BLOCKS ------------------------------------------------------ //
			craftMagicNumbersClass = NMSClass.getOBCClass("util.CraftMagicNumbers");

			block_change_pos_field = NMSClass.getField(block_change_class, "a");
			block_change_pos_field.setAccessible(true);


			if (HideRails.version == Version.V1_12) {
				// Get "IBlockData fromLegacy(int i)" method
				fromLegacyData_method = NMSClass.getMethod(block_class, "fromLegacyData", int.class);

				// Get Method "getBlock(Material material)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material)
				craftMagicNumbers_method_with_data = NMSClass.getMethod(craftMagicNumbersClass, "getBlock", Material.class);

				// Replace Block.setData(byte data);
				setData_method = Block.class.getDeclaredMethod("setData", byte.class);
			} else if (HideRails.version == Version.V1_13 || HideRails.version == Version.V1_14 || HideRails.version == Version.V1_15 || HideRails.version == Version.V1_17) {
				if (HideRails.version == Version.V1_14) {
					// Get "IBlockData getBlockData()" method
					//block_data_method = NMSClass.getMethod(block_class, "getBlockData", null);

					// Get Method "getBlock(Material material)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material)
					craftMagicNumbers_method_without_data = NMSClass.getMethod(craftMagicNumbersClass, "getBlock", Material.class);
				}

				// Get Method "getBlock(Material material, byte data)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material, data)
				craftMagicNumbers_method_with_data = NMSClass.getMethod(craftMagicNumbersClass, "getBlock", Material.class, byte.class);
			}


			/* PacketPlayInUseItem */
			// Replace method : MovingObjectPositionBlock c()
			moving_object_position_block_field = NMSClass.getMethod(packet_play_out_in_use_item, "c", (Class<?>) null);

			// Replace method : BlockPosition getBlockPosition()
			block_position_method = movingObjectPositionBlock_class.getDeclaredMethod("getBlockPosition", (Class<?>) null);

			// Replace method : int getX()
			get_x_base_block_position_method = base_block_position_class.getDeclaredMethod("getX", (Class<?>) null);
			// Replace method : int getY()
			get_y_base_block_position_method = base_block_position_class.getDeclaredMethod("getY", (Class<?>) null);
			// Replace method : int getZ()
			get_z_base_block_position_method = base_block_position_class.getDeclaredMethod("getZ", (Class<?>) null);

			// Replace method : EnumDirection getDirection()
			get_direction_method = movingObjectPositionBlock_class.getDeclaredMethod("getDirection", (Class<?>) null);
			// ------------------------------------------------------------------------------------------------------------------- //


			// ---------------------------------------------------- PARTICLES ---------------------------------------------------- //
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
			if (HideRails.version == Version.V1_14 || HideRails.version == Version.V1_15 || HideRails.version == Version.V1_17)
				fileUtils_class = Class.forName("org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils");
			else
				fileUtils_class = Class.forName("org.apache.commons.io.FileUtils");

			readFileContent_method = NMSClass.getMethod(fileUtils_class, "readFileToString", File.class, Charset.class); // params : File.class, Charset.class / return : String.class
			writeFileContent_method = NMSClass.getMethod(fileUtils_class, "write", File.class, CharSequence.class, Charset.class); // params : File.class, CharSequence.class, Charset.class / return : void
			// ------------------------------------------------------------------------------------------------------------------- //
		} catch (SecurityException | NoSuchFieldException | NoSuchMethodException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public BukkitNMS() {}


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
	public static void changeBlock(Player player, Material material, byte data, int x, int y, int z)
	{
		if (material == null) { // If the material is LEGACY or invalid in HiddenRails config file (Only for older versions than 1.14)
			System.err.println("[HideRails] <ERROR> THE 'HiddenRails.yml' FILE THAT CONTAINS INVALID BLOCKS, YOU MUST UPDATE THE 'LEGAGY' BLOCKS OR DELETE THE FILE!!");
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
			 * Replace "CraftMagicNumbers.getBlock(material).fromLegacyData(data)"
			 */
			// Get final IBlockData
			if (HideRails.version == Version.V1_12) {
				// Invoke Method "getBlock(Material material)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material)
				Object craftMagicNumber = NMSClass.invokeMethod(craftMagicNumbers_method_with_data, fromLegacyData_method, material);
				block_data = NMSClass.invokeMethod(fromLegacyData_method, craftMagicNumber, data);
			} else if (HideRails.version == Version.V1_13 || HideRails.version == Version.V1_14 || HideRails.version == Version.V1_15 || HideRails.version == Version.V1_17) {
				// Invoke Method "getBlock(Material material)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material)
				//Object craftMagicNumber = NMSClass.invokeMethod(craftMagicNumbers_method, null, material);

				// replace "block_data = CraftMagicNumbers.getBlock(material).getBlockData();"
				//Object iMe = NMSClass.invokeMethod(craftMagicNumbers_method, craftMagicNumber, material);
				//block_data = NMSClass.invokeMethod(block_data_method, iMe, null);

				// Invoke Method "getBlock(Material material, byte data)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material, data)
				// replace "block_data = CraftMagicNumbers.getBlock(material, data);"
				block_data = NMSClass.invokeMethod(craftMagicNumbers_method_with_data, null, material, data);
			}

			// Set blockChange location
			Object blockPosition = NMSClass.newInstance(constructorBP, x, y, z);

			// Instantiation of PacketPlayOutBlockChange packet
			if (HideRails.version != Version.V1_17) {
				packet = NMSClass.newInstance(block_change_class);

				block_change_pos_field.set(packet, blockPosition);

				// Set "block" field in PacketPlayOutBlockChange Class ("packet.block")
				block_field.set(packet, block_data);
			} else {
				// Replace constructor : public net.minecraft.network.protocol.game.PacketPlayOutBlockChange(net.minecraft.core.BlockPosition, net.minecraft.world.level.block.state.IBlockData)
				packet = NMSClass.newInstance(block_change_constructor, blockPosition, block_data);
			}


			// Send Packet to player
			sendPacket(player, packet);
		}
		catch (InvocationTargetException e)
		{
			/* If material is LEGACY or invalid */
			if (HideRails.version == Version.V1_13 || HideRails.version == Version.V1_15 || HideRails.version == Version.V1_17) {
				System.err.println("[HideRails] <ERROR> THE 'HiddenRails.yml' FILE THAT CONTAINS INVALID BLOCKS, YOU MUST UPDATE THE BLOCK'" + baseMatName + "' OR DELETE THE FILE!!");
			} else if (HideRails.version == Version.V1_14) {
				/* Try without data */
				try {
					// Invoke Method "getBlock(Material material)" in CraftMagicNumbers Class : Replace CraftMagicNumbers.getBlock(material)
					// replace "block_data = CraftMagicNumbers.getBlock(material).getBlockData();"
					block_data = NMSClass.invokeMethod(craftMagicNumbers_method_without_data, null, material);

					/* Set "block" field in PacketPlayOutBlockChange Class ("packet.block") */
					block_field.set(packet, block_data);

					/* Send Packet to player */
					sendPacket(player, packet);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e1) {
					System.err.println("[HideRails] <ERROR> THE 'HiddenRails.yml' FILE THAT CONTAINS INVALID BLOCKS, YOU MUST UPDATE THE BLOCK'" + baseMatName + "' OR DELETE THE FILE!!");
				}
			} else {
				e.printStackTrace();
			}
		}
		catch (IllegalArgumentException | IllegalAccessException | InstantiationException | NoSuchMethodException | SecurityException | NoSuchFieldException e)
		{
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
	@SuppressWarnings("unchecked")
	public static void summonParticle(Player player, Location loc, Object particleName, int amount, int speed) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException
	{
		// If the version is 1.8
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
			sendPacket(player, packet);
		} else if (HideRails.version == Version.V1_13 || HideRails.version == Version.V1_14) {
			player.spawnParticle(Particle.valueOf(((Enum<ParticleName_v1_13>) particleName).name().toUpperCase()), loc.getX(), loc.getY(), loc.getZ(), 0, 0d, 0d, 0d, amount);
		} else if (HideRails.version == Version.V1_15 || HideRails.version == Version.V1_17) {
			player.spawnParticle(Particle.valueOf(((Enum<ParticleName_v1_15>) particleName).name().toUpperCase()), loc.getX(), loc.getY(), loc.getZ(), 0, 0d, 0d, 0d, amount);
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
		// Stop console errors (pending player connection)
		if (!player.isOnline()) return;

		NMSClass.invokeMethod(sendPacket_method, getPlayerConnection(player), packet);
	}


	/**
	 * Get PlayerConnection of player
	 * 
	 * @param player
	 * @return PlayerConnection object
	 * 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static Object getPlayerConnection(Player player) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		return playerConnection_field.get(handle_method.invoke(player));
	}


	/**
	 * Get channel pipeline of player
	 * 
	 * @param player
	 * @return the pipeline of player
	 * 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static ChannelPipeline getChannelPipeline(Player player) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Object networkManager = networkManager_field.get(getPlayerConnection(player));

		return ((Channel) channel_field.get(networkManager)).pipeline();
	}

	/**
	 * Inject packet listener in channel pipeline of player
	 * 
	 * @param player
	 * 
	 * @param packetListener
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws NoSuchMethodException 
	 */
	public static void injectChannelPipeline(Player player, PacketListener packetListener) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, SecurityException, NoSuchMethodException
	{
		getChannelPipeline(player).addBefore("packet_handler", player.getName(), packetListener);
	}


	/**
	 * Remove channel pipeline of player
	 * 
	 * @param player
	 * @param packetListener
	 * 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void removeChannelPipeline(Player player, PacketListener packetListener) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		ChannelPipeline cp = getChannelPipeline(player);
		String pName = player.getName();

		if (cp.get(pName) != null)
			cp.remove(pName);
	}


	/**
	 * Get PacketPlayOutInUseItem class
	 * 
	 * @return PacketPlayInUseItem class
	 */
	public static Class<?> getPacketPlayOutInUseItem() {
		return packet_play_out_in_use_item;
	}


	/**
	 * 
	 * 
	 * @param ppoiui
	 * 
	 * @return the packet data
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static Object getMovingObjectPositionBlock(Object ppoiui) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return NMSClass.invokeMethod(moving_object_position_block_field, ppoiui, (Class<?>) null);
	}

	/**
	 * Get value of BlockPosition field of PacketPlayOutInUseItem packet.
	 * <p>The {@code mopb} param is an instance of MovingObjectPositionBlock.</p>
	 * 
	 * @param mopb
	 * @return BlockPosition
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Object getBlockPosition(Object mopb) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return block_position_method.invoke(mopb, (Class<?>) null);
	}

	// Lazy kikoolol method :D
	/*public static int getXBlockPosition(Object ppoiui) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object bp = getBlockPosition(ppoiui);
		return Integer.parseInt(
				bp.toString()
				.replace("BlockPosition{", "")
				.replace("}", "")
				.replace("x=", "")
				.replace("y=", "")
				.replace("z=", "")
				.split(", ")[0]);
	}*/

	/**
	 * Get X position from BlockPosition
	 * 
	 * @param blockPosition
	 * @return x block pos
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static int getXBlockPosition(Object blockPosition) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object x = get_x_base_block_position_method.invoke(blockPosition, (Class<?>) null);
		return Integer.parseInt(x.toString());
	}

	/**
	 * Get Y position from BlockPosition
	 * 
	 * @param blockPosition
	 * @return y block pos
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static int getYBlockPosition(Object blockPosition) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object y = get_y_base_block_position_method.invoke(blockPosition, (Class<?>) null);
		return Integer.parseInt(y.toString());
	}

	/**
	 * Get Z position from BlockPosition or BaseBlockPosition (his superclass)
	 * 
	 * @param blockPosition
	 * @return z block pos
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static int getZBlockPosition(Object blockPosition) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object z = get_z_base_block_position_method.invoke(blockPosition, (Class<?>) null);
		return Integer.parseInt(z.toString());
	}

	/**
	 * Get direction from MovingObjectPositionBlock
	 * 
	 * @param movingObjectPositionBlock
	 * @return EnumDirection
	 * 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static EnumDirection getMovingObjectPositionBlockDirection(Object movingObjectPositionBlock) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object dir = get_direction_method.invoke(movingObjectPositionBlock, (Class<?>) null);
		// Convert net.minecraft.server.EnumDirection to fr.nepta.hiderails.enums.EnumDirection
		return EnumDirection.valueOf(dir.toString().toUpperCase());
	}


	/**
	 * Set block data
	 * This method is only for 1.12 versions and it used to avoid
	 * warnings in a code (other versions doesn't have this method)
	 * (Not NMS)
	 * 
	 * @param block
	 * @param data
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void setData(Block block, byte data) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// Replace : Block.class.getDeclaredMethod("setData", byte.class).invoke(block, data);
		setData_method.invoke(block, data);
	}
}
