/**
 * Copyright
 * Code under MIT licence
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.nms;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NMSClass
{
	private final static String VERSION = BukkitNMS.VERSION;

	/**
	 * Get net.minecraft.server class
	 * 
	 * @param className
	 * @return Class
	 */
	public static Class<?> getNMSClass(String className)
	{
		try {
			return Class.forName("net.minecraft.server." + VERSION + "." + className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get org.bukkit.craftbukkit class
	 * 
	 * @param className
	 * @return Class
	 */
	public static Class<?> getOBCClass(String className)
	{
		try {
			return Class.forName("org.bukkit.craftbukkit." + VERSION + "." + className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Get constructor of class
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
	 * Get method of class
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
	 * Get field of class
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
	 * Create an instance of class
	 * 
	 * @param class
	 * @return Object
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static Object newInstance(Class<?> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		return clazz.getDeclaredConstructor().newInstance();
	}

	/**
	 * Create an instance of constructor
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
	 * Invoke method
	 * 
	 * @param method
	 * @param methodInstance
	 * @param methodParams
	 * @return the result
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object invokeMethod(Method method, Object inst, Object... methodParams) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		return method.invoke(inst, methodParams);
	}
}