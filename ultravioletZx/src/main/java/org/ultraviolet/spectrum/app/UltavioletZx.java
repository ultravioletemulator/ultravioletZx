package org.ultraviolet.spectrum.app;

import org.ultraviolet.spectrum.machines.Spectrum48k;

/**
 * Created by developer on 21/09/2017.
 */
public class UltavioletZx {

	public static void main(String[] args) {
		initializeZx48k();
	}

	public static void initializeZx48k() {
		Spectrum48k s48k = new Spectrum48k();
		s48k.init();
	}
}
