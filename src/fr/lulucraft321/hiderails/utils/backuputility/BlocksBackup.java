/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author ProCZ
 */

package fr.lulucraft321.hiderails.utils.backuputility;

import java.util.ArrayList;
import java.util.List;

import fr.lulucraft321.hiderails.utils.MaterialData;

public class BlocksBackup
{
	private BackupType type;
	private List<String> changedBlocks = new ArrayList<>();
	private MaterialData unHideBlocksType; // Only for unhide reverse

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
}
