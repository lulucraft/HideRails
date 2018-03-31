/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.reflection;

import org.bukkit.Bukkit;

public class NMSClass
{
	public static Class<?> getNMSClass(String className)
	{
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Class<?> getOBCClass(String className)
	{
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("org.bukkit.craftbukkit." + version + "." + className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
