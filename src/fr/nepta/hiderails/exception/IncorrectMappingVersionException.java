/**
 * Copyright
 * Code under MIT license
 * 
 * @author Nepta_
 */
package fr.nepta.hiderails.exception;

public class IncorrectMappingVersionException extends Exception {

	private static final long serialVersionUID = -3422592907262012330L;

	public IncorrectMappingVersionException() {
		super("Incompatible with the old mapping version of spigot packages");
	}
}
