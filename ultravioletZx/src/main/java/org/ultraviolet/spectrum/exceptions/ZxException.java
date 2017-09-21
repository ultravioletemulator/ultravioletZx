package org.ultraviolet.spectrum.exceptions;

/**
 * Created by developer on 21/09/2017.
 */
public class ZxException extends Exception {

	public ZxException() {
	}

	public ZxException(String message) {
		super(message);
	}

	public ZxException(String message, Throwable cause) {
		super(message, cause);
	}

	public ZxException(Throwable cause) {
		super(cause);
	}

	public ZxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
