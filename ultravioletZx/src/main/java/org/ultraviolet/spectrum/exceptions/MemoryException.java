package org.ultraviolet.spectrum.exceptions;

/**
 * Created by developer on 21/09/2017.
 */
public class MemoryException extends ZxException {

	public MemoryException() {
	}

	public MemoryException(String message) {
		super(message);
	}

	public MemoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public MemoryException(Throwable cause) {
		super(cause);
	}

	public MemoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
