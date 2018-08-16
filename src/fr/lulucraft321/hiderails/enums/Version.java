/**
 * Copyright Java Code
 * All right reserved.
 *
 * @author lulucraft321
 */

package fr.lulucraft321.hiderails.enums;

public enum Version
{
	v1_12(false), v1_13(true);

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
