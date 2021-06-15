/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.nms;

import static fr.nepta.hiderails.nms.BukkitNMS.VERSION;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import fr.nepta.hiderails.HideRails;
import fr.nepta.hiderails.enums.Version;
import fr.nepta.hiderails.exception.IncorrectMappingVersionException;

public class NMSClass
{
	/**
	 * Get net.minecraft.network class
	 * <br>Only for 1.17+
	 * 
	 * @param className
	 * @return Class
	 */
	public static Class<?> getNMNClass(String className)
	{
		try {
			if (HideRails.version == Version.V1_17) {
				return Class.forName("net.minecraft.network." + className);				
			} else {
				throw new IncorrectMappingVersionException();//"Method incompatible with the current version"
			}
		} catch (IncorrectMappingVersionException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get net.minecraft.world class
	 * <br>Only for 1.17+
	 * 
	 * @param className
	 * @return Class
	 */
	public static Class<?> getNMWClass(String className)
	{
		try {
			if (HideRails.version == Version.V1_17) {
				return Class.forName("net.minecraft.world." + className);				
			} else {
				throw new IncorrectMappingVersionException();//"Method incompatible with the current version"
			}
		} catch (IncorrectMappingVersionException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get net.minecraft.core class
	 * <br>Only for 1.17+
	 * 
	 * @param className
	 * @return Class
	 */
	public static Class<?> getNMCClass(String className)
	{
		try {
			if (HideRails.version == Version.V1_17) {
				return Class.forName("net.minecraft.core." + className);				
			} else {
				throw new IncorrectMappingVersionException();//"Method incompatible with the current version"
			}
		} catch (IncorrectMappingVersionException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get net.minecraft.server class
	 * 
	 * @param className
	 * @return Class
	 */
	public static Class<?> getNMSClass(String className)
	{
		try {
			if (HideRails.version == Version.V1_17) {
				return Class.forName("net.minecraft.server." + className);				
			} else {
				return Class.forName("net.minecraft.server." + VERSION + "." + className);				
			}
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
	 * @param clazz -> class that contains constructor
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