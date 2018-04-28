/**
 * Copyright Java Code
 * All right reserved.
 *
 */

package fr.lulucraft321.hiderails.utils;

public enum Messages
{
	SENDER_TYPE_ERROR("tu n'es pas un joueur"),
	PLAYER_NO_ENOUGH_PERMISSION("tu n'as pas la permission pour cela"),
	MATERIAL_TYPE_ERROR("bloc invalide"),
	SUCCESS_CHANGE_RAIL("rail remplace avec succes"),
	RAIL_ERROR("le bloc cible n'est pas un rail ou des barreaux"),
	SUCCESS_BREAK_RAIL("rail supprime avec succes"),
	INVALID_WORLDNAME("ce monde n'existe pas"),
	WATER_PROTECTION_STATUS_ALREADY("la redstone est deja activee sous l'eau"),
	SUCCESS_CHANGE_WATER_PROTECTION_STATUS("l'activation de la redstone sur la map a ete definit avec succes"),
	SUCCESS_RELOAD("plugin recharge avec success"),
	SUCCESS_UNHIDE_RAIL("rail affiche avec succes"),
	NO_BACKUP("aucune sauveguarde"),
	RETURN_BACKUP_SUCCESS("backup restaure avec succes !"),
	WORLDEDIT_NOT_INSTALLED("le plugin worldedit n'est pas installé sur le serveur !"),
	WORLDEDIT_NO_SELECTION("vous devez d'abord sélectionner un region avec worldedit !"),
	DISPLAY_HIDDEN_BLOCKS("vous avez %status% le masquage des rails seulement poour vous");

	private String message;

	private Messages(String message)
	{
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
