package org.ultraviolet.spectrum.z80;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by developer on 21/09/2017.
 */
public class Rom extends Memory {
	//16k = 16384
	static final Logger logger = LoggerFactory.getLogger(Rom.class);

	public Rom(int size) {
		super(size);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
