package fr.lulucraft321.hiderails.utils.selectionsystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Cuboid implements Iterable<Block>, Cloneable, ConfigurationSerializable
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
		this.xmax = cuboid.getXmax();
		this.xmin = cuboid.getXmin();
		this.ymax = cuboid.getYmax();
		this.ymin = cuboid.getYmin();
		this.zmax = cuboid.getZmax();
		this.zmin = cuboid.getZmin();
		this.vec1 = new Vector(this.xmin, this.ymin, this.zmin);
		this.vec2 = new Vector(this.xmax, this.ymax, this.zmax);
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

	public Cuboid(Map<String, Object> map)
	{
		this.xmax = (Integer) map.get("xmax");
		this.xmin = (Integer) map.get("xmin");
		this.ymax = (Integer) map.get("ymax");
		this.ymin = (Integer) map.get("ymin");
		this.zmax = (Integer) map.get("zmax");
		this.zmin = (Integer) map.get("zmin");
		this.vec1 = new Vector(this.xmin, this.ymin, this.zmin);
		this.vec2 = new Vector(this.xmax, this.ymax, this.zmax);
		this.w = Bukkit.getServer().getWorld(map.get("world").toString());
	}

	public int getXmax() {
		return this.xmax;
	}

	public int getXmin() {
		return this.xmin;
	}

	public int getYmax() {
		return this.ymax;
	}

	public int getYmin() {
		return this.ymin;
	}

	public int getZmax() {
		return this.zmax;
	}

	public int getZmin() {
		return this.zmin;
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

	public void setXmax(int xmax) {
		this.xmax = xmax;
	}

	public void setXmin(int xmin) {
		this.xmin = xmin;
	}

	public void setYmax(int ymax) {
		this.ymax = ymax;
	}

	public void setYmin(int ymin) {
		this.ymin = ymin;
	}

	public void setZmax(int zmax) {
		this.zmax = zmax;
	}

	public void setZmin(int zmin) {
		this.zmin = zmin;
	}

	public void setWorld(World world) {
		this.w = world;
	}


	@Override
	public Iterator<Block> iterator() {
		return new CuboidIterator(new Cuboid(this.xmax, this.xmin, this.ymax, this.ymin, this.zmax, this.zmin, this.w));
	}


	public boolean hasPlayerInside(Player player) {
		final Location loc = player.getLocation();
		if (loc.toVector().isInAABB(this.vec1, this.vec2)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasBlockInside(Block block) {
		final Location loc = block.getLocation();
		if (loc.toVector().isInAABB(this.vec1, this.vec2)) {
			return true;
		}
		else {
			return false;
		}
	}


	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("world", getWorld().toString());
		map.put("xmax", getXmax());
		map.put("xmin", getXmin());
		map.put("ymax", getYmax());
		map.put("ymin", getYmin());
		map.put("zmax", getZmax());
		map.put("zmin", getZmin());
		return map;
	}

	//-----------------------------------------------------

	public class CuboidIterator implements Iterator<Block>
	{
		Cuboid cci;
		World wci;
		int baseX;
		int baseY;
		int baseZ;
		int sizeX;
		int sizeY;
		int sizeZ;
		private int x, y, z;
		ArrayList<Block> blocks;
		Map<Location, Material> blocks2;
		ArrayList<Location> blocks3;

		public CuboidIterator(Cuboid c)
		{
			this.cci = c;
			this.wci = c.getWorld();
			baseX = getXmin();
			baseY = getYmin();
			baseZ = getZmin();
			sizeX = Math.abs(getXmax() - getXmin()) + 1;
			sizeY = Math.abs(getYmax() - getYmin()) + 1;
			sizeZ = Math.abs(getZmax() - getZmin()) + 1;
			x = y = z = 0;
		}

		public Cuboid getCuboid() {
			return cci;
		}

		public boolean hasNext() {
			return x < sizeX && y < sizeY && z < sizeZ;
		}

		public Block next() {
			Block b = w.getBlockAt(baseX + x, baseY + y, baseZ + z);
			if (++x >= sizeX) {
				x = 0;
				if (++y >= sizeY) {
					y = 0;
					++z;
				}
			}
			return b;
		}

		public void remove() {
			// nop
		}

		public Map<Location, Material> getBlockAtLocations() {
			blocks2 = new HashMap<Location, Material>();
			for(int x = cci.getXmin(); x <= cci.getXmax(); x++){
				for(int y = cci.getYmin(); y <= cci.getYmax(); y++){
					for(int z = cci.getZmin(); z <= cci.getZmax(); z++){
						blocks2.put(new Location(cci.getWorld(), x, y, z), getWorld().getBlockAt(x, y, z).getType());
					}
				}
			}
			return blocks2;
		}

		public Collection<Location> getLocations() {
			blocks3 = new ArrayList<Location>();
			for(int x = cci.getXmin(); x <= cci.getXmax(); x++){
				for(int y = cci.getYmin(); y <= cci.getYmax(); y++){
					for(int z = cci.getZmin(); z <= cci.getZmax(); z++){
						blocks3.add(new Location(wci, x, y, z));
					}
				}
			}
			return blocks3;
		}

		public Collection<Block> iterateBlocks() {
			blocks = new ArrayList<Block>();
			for(int x = cci.getXmin(); x <= cci.getXmax(); x++){
				for(int y = cci.getYmin(); y <= cci.getYmax(); y++){
					for(int z = cci.getZmin(); z <= cci.getZmax(); z++){
						blocks.add(cci.getWorld().getBlockAt(x, y, z));
					}
				}
			}
			return blocks;
		}

	}

	//-----------------------------------------------------

	@Override
	public Cuboid clone() throws CloneNotSupportedException {
		return new Cuboid(this);
	}
}