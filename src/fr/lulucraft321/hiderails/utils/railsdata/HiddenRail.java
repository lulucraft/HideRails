/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.utils.railsdata;

import org.bukkit.Location;
import org.bukkit.Material;

public class HiddenRail
{
	private Location railLoc;
	private Material material;
	private byte data;

	public HiddenRail(Material material, byte data)
	{
		setMaterial(material);
		setData(data);
	}

	public Location getLocation() {
		return railLoc;
	}

	public void setLocation(Location railLoc) {
		this.railLoc = railLoc;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public byte getData() {
		return data;
	}

	public void setData(byte data) {
		this.data = data;
	}
}
