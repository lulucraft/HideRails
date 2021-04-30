/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.enums;

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
	HIDERAILS_NO_SELECTION("vous devez d'abord selectionner une region avec la hache"),
	HIDERAILS_SELECTION_POS("vous avez selectionne la position avec succes"),
	CHANGE_HIDERAILS_SELECTION_MESSAGE_STATUS("vous avez definit l'activation des messages de selection avec succes"),
	DISPLAY_HIDDEN_BLOCKS("vous avez %status% le masquage des rails seulement poour vous"),
	INVALID_PLAYER("le joueur entré est invalide"),
	UPDATE_FOUND("une mise à jour du plugin est disponible !"),
	KICK_SPAM_BLOCK("arrête de spam sur les blocs svp");

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
