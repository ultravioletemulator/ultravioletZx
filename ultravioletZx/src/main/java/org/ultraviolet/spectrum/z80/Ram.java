package org.ultraviolet.spectrum.z80;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by developer on 21/09/2017.
 */
public class Ram extends Memory {

	static final Logger logger = LoggerFactory.getLogger(Ram.class);
	//	16K or 48k
	// 16K = 16384
	//  48k = 49152

	public Ram(int size) {
		super(size);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
