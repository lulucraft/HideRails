/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.nepta.hiderails.models;

import org.bukkit.Location;

import fr.nepta.hiderails.managers.PlayerClaimDataManager;
import fr.nepta.hiderails.models.selectionsystem.Cuboid;

public class ClaimData
{
	private Location loc1, loc2;
	private Cuboid cuboid;

	public ClaimData(PlayerClaimDataManager.LocType locType, Location loc) {
		if (locType == PlayerClaimDataManager.LocType.LOC1)
			setPos1(loc);
		if (locType == PlayerClaimDataManager.LocType.LOC2)
			setPos2(loc);
	}

	public Location getPos1() {
		return loc1;
	}

	public void setPos1(Location loc1) {
		this.loc1 = loc1;
	}

	public Location getPos2() {
		return loc2;
	}

	public void setPos2(Location loc2) {
		this.loc2 = loc2;
	}

	public Cuboid getCuboid() {
		return cuboid;
	}

	public void setCuboid(Cuboid cuboid) {
		this.cuboid = cuboid;
	}
}
