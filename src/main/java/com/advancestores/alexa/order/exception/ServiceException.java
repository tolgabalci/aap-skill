/**
 * $Id$
 */
package com.advancestores.alexa.order.exception;

/**
 * Exception thrown for any unhandled runtime exceptions
 * 
 * @author $Author$ (last modified by)
 * @version $Revision$
 */
public class ServiceException extends RuntimeException {

	/**
	 * {@inheritDoc}
	 */
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * {@inheritDoc}
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * {@inheritDoc}
	 */
	public ServiceException(Throwable cause) {
		super(cause);
	}
}
