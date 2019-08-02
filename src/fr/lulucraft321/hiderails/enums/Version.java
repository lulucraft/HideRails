/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author Nepta_
 */

package fr.lulucraft321.hiderails.enums;

public enum Version
{
	v1_12(false), v1_13(false), v1_14(false);

	private boolean oldVersion;

	private Version(boolean oldVersion) {
		this.oldVersion = oldVersion;
	}

	public void setOldVersion(boolean b) {
		this.oldVersion = b;
	}

	public boolean isOldVersion() {
		return this.oldVersion;
	}
}
