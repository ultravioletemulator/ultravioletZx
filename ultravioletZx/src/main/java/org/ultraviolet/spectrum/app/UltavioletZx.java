package org.ultraviolet.spectrum.app;

import org.ultraviolet.spectrum.exceptions.ZxException;
import org.ultraviolet.spectrum.machines.Spectrum48k;

/**
 * Created by developer on 21/09/2017.
 */
public class UltavioletZx {

	public static int main(String[] args) {
		try {
			initializeZx48k();
		} catch (ZxException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public static void initializeZx48k() throws ZxException {
		Spectrum48k s48k = new Spectrum48k();
		s48k.init();
	}
}
