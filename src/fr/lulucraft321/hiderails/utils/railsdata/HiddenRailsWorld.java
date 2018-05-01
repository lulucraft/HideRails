/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.utils.railsdata;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;

import fr.lulucraft321.hiderails.managers.HideRailsManager;

public class HiddenRailsWorld
{
	private World world;
	private String worldName;

	private List<HiddenRail> hiddenRails = new ArrayList<>();

	public HiddenRailsWorld(World world)
	{
		this.setWorld(world);
		this.worldName = world.getName();
		HideRailsManager.rails.add(this);
	}

	public HiddenRailsWorld(World world, List<HiddenRail> rails)
	{
		this.setWorld(world);
		this.worldName = world.getName();
		this.setHiddenRails(rails);
		HideRailsManager.rails.add(this);
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public String getWorldName() {
		return worldName;
	}

	public void setHiddenRails(List<HiddenRail> hiddenRails) {
		this.hiddenRails = hiddenRails;
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
