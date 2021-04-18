package fr.nepta.hiderails.models.selectionsystem;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Cuboid
{
	private World w;
	private int xmax;
	private int xmin;
	private int ymax;
	private int ymin;
	private int zmax;
	private int zmin;
	private Vector vec1;
	private Vector vec2;

	//new
	public Cuboid(Cuboid cuboid)
	{
		this.w = cuboid.getWorld();
		this.vec1 = new Vector(cuboid.xmin, cuboid.ymin, cuboid.zmin);
		this.vec2 = new Vector(cuboid.xmax, cuboid.ymax, cuboid.zmax);
	}

	public Cuboid(Location l1, Location l2)
	{
		if(l1.getWorld().getName().equals(l2.getWorld().getName())){
			this.w = l1.getWorld();
			this.xmax = Math.max(l1.getBlockX(), l2.getBlockX());
			this.xmin = Math.min(l1.getBlockX(), l2.getBlockX());
			this.ymax = Math.max(l1.getBlockY(), l2.getBlockY());
			this.ymin = Math.min(l1.getBlockY(), l2.getBlockY());
			this.zmax = Math.max(l1.getBlockZ(), l2.getBlockZ());
			this.zmin = Math.min(l1.getBlockZ(), l2.getBlockZ());
			this.vec1 = new Vector(this.xmin, this.ymin, this.zmin);
			this.vec2 = new Vector(this.xmax, this.ymax, this.zmax);
		}
	}

	public Cuboid(int xmax, int xmin, int ymax, int ymin, int zmax, int zmin, World world)
	{
		this.w = world;
		this.xmax = xmax;
		this.xmin = xmin;
		this.ymax = ymax;
		this.ymin = ymin;
		this.zmax = zmax;
		this.zmin = zmin;
		this.vec1 = new Vector(this.xmin, this.ymin, this.zmin);
		this.vec2 = new Vector(this.xmax, this.ymax, this.zmax);
	}


	public Vector getVector1() {
		return this.vec1;
	}

	public Vector getVector2() {
		return this.vec2;
	}

	public World getWorld() {
		return this.w;
	}

	public void setWorld(World world) {
		this.w = world;
	}
}