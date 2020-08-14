package com.kervinCortez.Project.negocio.exception;

public class InvalidFormatEmailException extends RuntimeException{

	private static final long serialVersionUID = -8037564800889176455L;

	public InvalidFormatEmailException() {
		super();
	}

	public InvalidFormatEmailException(final String message) {
		super(message);
	}
	
}
