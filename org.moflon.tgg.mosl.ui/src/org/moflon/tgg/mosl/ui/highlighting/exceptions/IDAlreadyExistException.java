package org.moflon.tgg.mosl.ui.highlighting.exceptions;

public class IDAlreadyExistException extends Exception {

	private static final long serialVersionUID = -5362543762900026184L;
	
	public IDAlreadyExistException(){
		super("This ID already exist and IDs must be unique. Choose another ID, please");
	}

}
