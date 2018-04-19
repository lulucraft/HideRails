package fr.lulucraft321.hiderails.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;

public class NMSClass
{
	private static String version;

	static {
		version = new BukkitNMS().version;
	}

	/**
	 * 
	 * @param className
	 * @return Class
	 */
	public static Class<?> getNMSClass(String className)
	{
		try {
			return Class.forName("net.minecraft.server." + version + "." + className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param className
	 * @return Class
	 */
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


	/**
	 * 
	 * @param clazz -> class who contains constructor
	 * @param constructorParams -> parameters of constructor
	 * @return Constructor
	 * 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... constructorParams) throws NoSuchMethodException, SecurityException
	{
		return clazz.getDeclaredConstructor(constructorParams);
	}

	/**
	 * 
	 * @param clazz
	 * @param methodName
	 * @param methodParams
	 * @return Method
	 * 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... methodParams) throws NoSuchMethodException, SecurityException
	{
		return clazz.getDeclaredMethod(methodName, methodParams);
	}

	/**
	 * 
	 * @param clazz
	 * @param field
	 * @return Field
	 * 
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static Field getField(Class<?> clazz, String field) throws NoSuchFieldException, SecurityException
	{
		return clazz.getDeclaredField(field);
	}



	/**
	 * 
	 * @param constructor
	 * @param initArgs
	 * @return Object
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object newInstance(Constructor<?> constructor, Object... initArgs) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		return constructor.newInstance(initArgs);
	}

	/**
	 * 
	 * @param method
	 * @param methodName
	 * @param methodParams
	 * @return
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object invokeMethod(Method method, Object methodName, Object methodParams) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		return method.invoke(methodName, methodParams);
	}
}
