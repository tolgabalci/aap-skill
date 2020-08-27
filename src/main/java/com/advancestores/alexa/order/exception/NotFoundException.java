/**
 * $Id$
 */
package com.advancestores.alexa.order.exception;

/**
 * Exception raised in instances where a particular resource cannot be found within the service.
 * 
 * @author $Author$ (last modified by)
 * @version $Revision$
 */
public class NotFoundException extends RuntimeException {
	public NotFoundException(String message) {
		super(message);
	}

}
