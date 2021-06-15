/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.enums;

public enum Version
{
	V1_12(false), V1_13(false), V1_14(false), V1_15(false), V1_17(false);

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
