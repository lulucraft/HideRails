/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.utils.backuputility;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

//import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.lulucraft321.hiderails.enums.BackupType;
import fr.lulucraft321.hiderails.utils.data.MaterialData;
import fr.lulucraft321.hiderails.utils.selectionsystem.Cuboid;

public class BlocksBackup
{
	private BackupType type;
	private List<String> changedBlocks = new ArrayList<>();
	private MaterialData unHideBlocksType; // Only for unhide reverse

	private Cuboid hrSelection; // Only if replacement was done with HideRailsSystem selection
	//private Selection weSelection; // Only if replacement was done with Worldedit selection
	private List<Material> blocksType = new ArrayList<>();

	public BackupType getType() {
		return type;
	}

	public void setType(BackupType type) {
		this.type = type;
	}

	public List<String> getChangedBlocks() {
		return changedBlocks;
	}

	public void addChangedBlocks(String changedBlock) {
		this.changedBlocks.add(changedBlock);
	}

	public MaterialData getUnHideBlocksType() {
		return unHideBlocksType;
	}

	public void setUnHideBlocksType(MaterialData unHideBlocksType) {
		this.unHideBlocksType = unHideBlocksType;
	}


	public Cuboid getHrSelection() {
		return hrSelection;
	}

	public void setHrSelection(Cuboid hrSelection) {
		this.hrSelection = hrSelection;
	}

	public List<Material> getBlocksType() {
		return blocksType;
	}

	public void setBlocksType(List<Material> blocksType) {
		this.blocksType = blocksType;
	}
}
