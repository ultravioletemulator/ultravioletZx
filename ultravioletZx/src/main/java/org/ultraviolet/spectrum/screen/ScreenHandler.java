package org.ultraviolet.spectrum.screen;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by developer on 21/09/2017.
 */
public class ScreenHandler {

	///https://en.wikipedia.org/wiki/ZX_Spectrum_graphic_modes
	Properties colorMap = new Properties();
	private final String FILE_COLOR_MAP = "core/colors.properties";

	private void loadColorMap() throws IOException {
		colorMap.load(new FileInputStream(FILE_COLOR_MAP));
	}
}
