/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.enums;

public enum BlockReplacementType {
	RAIL, IRON_BAR, COMMAND_BLOCK, REDSTONE, SIGN;

	public static BlockReplacementType getBlockReplacementType(String input) {
		for (BlockReplacementType brt : values()) {
			if (brt.name().equalsIgnoreCase(input.toUpperCase()))
				return brt;
		}
		return null;
	}
}
