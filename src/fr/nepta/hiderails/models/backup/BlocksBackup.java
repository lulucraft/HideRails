/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.models.backup;

import java.util.ArrayList;
import java.util.List;

import fr.nepta.hiderails.enums.BackupType;
import fr.nepta.hiderails.enums.BlockReplacementType;
import fr.nepta.hiderails.models.MaterialData;
import fr.nepta.hiderails.models.selectionsystem.Cuboid;

public class BlocksBackup
{
	private BackupType type;
	private List<String> changedBlocks = new ArrayList<>();
	private MaterialData unHideBlocksType; // Only for unhide reverse

	private Cuboid hrSelection; // Only if replacement was done with HideRails selection
	private List<BlockReplacementType> blocksType = new ArrayList<>();

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

	public List<BlockReplacementType> getBlocksType() {
		return blocksType;
	}

	public void setBlocksType(List<BlockReplacementType> blocksType) {
		this.blocksType = blocksType;
	}
}
