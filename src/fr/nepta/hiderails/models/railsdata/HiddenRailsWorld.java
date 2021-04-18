/**
 * Copyright
 * Code under MIT licence
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.models.railsdata;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;

import fr.nepta.hiderails.managers.HideRailsManager;

public class HiddenRailsWorld
{
	private World world;
	private String worldName;

	private List<HiddenRail> hiddenRails = new ArrayList<>();

	public HiddenRailsWorld(World world)
	{
		this.world = world;
		this.worldName = world.getName();
		HideRailsManager.rails.add(this);
	}

	public HiddenRailsWorld(World world, List<HiddenRail> rails)
	{
		this.world = world;
		this.worldName = world.getName();
		this.hiddenRails = rails;
		HideRailsManager.rails.add(this);
	}

	public World getWorld() {
		return world;
	}

	public String getWorldName() {
		return worldName;
	}

	public List<HiddenRail> getHiddenRails() {
		return hiddenRails;
	}

	public void addHiddenRails(HiddenRail hiddenRail) {
		this.hiddenRails.add(hiddenRail);
	}

	public void delHiddenRail(HiddenRail hRail) {
		this.hiddenRails.remove(hRail);
	}
}
