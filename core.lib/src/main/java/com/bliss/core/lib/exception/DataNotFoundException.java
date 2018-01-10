package com.bliss.core.lib.exception;
/**
 * 
 * @author tuyenpv
 *
 */
public class DataNotFoundException extends DataException {
	private static final long serialVersionUID = 1L;
	
	public DataNotFoundException() {
		super();
	}
	
	public DataNotFoundException(String message) {
		super(message);
	}
	
	public DataNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
