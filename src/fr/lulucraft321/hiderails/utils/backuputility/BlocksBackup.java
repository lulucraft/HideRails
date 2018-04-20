/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author ProCZ
 */

package fr.lulucraft321.hiderails.utils.backuputility;

import java.util.ArrayList;
import java.util.List;

import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.lulucraft321.hiderails.enums.BackupType;
import fr.lulucraft321.hiderails.utils.MaterialData;

public class BlocksBackup
{
	private BackupType type;
	private List<String> changedBlocks = new ArrayList<>();
	private MaterialData unHideBlocksType; // Only for unhide reverse

	private Selection weSelection; // Only if replacement was done with Worldedit selection

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


	public Selection getWeSelection() {
		return weSelection;
	}

	public void setWeSelection(Selection weSelection) {
		this.weSelection = weSelection;
	}
}
