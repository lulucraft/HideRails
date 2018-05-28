/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.utils.data;

import org.bukkit.Material;

public class MaterialData
{
	private Material mat;
	private byte data;

	public MaterialData(Material mat, byte data)
	{
		setMat(mat);
		setData(data);
	}

	public Material getMat() {
		return mat;
	}

	public void setMat(Material mat) {
		this.mat = mat;
	}

	public byte getData() {
		return data;
	}

	public void setData(byte data) {
		this.data = data;
	}
}
