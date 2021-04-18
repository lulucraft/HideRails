/**
 * Copyright
 * Code under MIT licence
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.listeners;

import org.bukkit.Bukkit;

import fr.nepta.hiderails.HideRails;

public class Listener implements org.bukkit.event.Listener
{
	public Listener() {
		Bukkit.getServer().getPluginManager().registerEvents(this, HideRails.getInstance());
	}
}
