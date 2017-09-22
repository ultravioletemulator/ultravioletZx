package org.ultraviolet.spectrum.screen;

import sun.awt.image.PixelConverter;

import java.awt.image.BufferedImage;
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

	//Screen
	private BufferedImage screen;

	public void setPixelColor(int x, int y, int color, boolean bright) {
		String colorStr = colorMap.getProperty(color + "_" + bright);
		int r = Integer.valueOf(colorStr.substring(1, 3), 16);
		int g = Integer.valueOf(colorStr.substring(3, 5), 16);
		int b = Integer.valueOf(colorStr.substring(5, 7), 16);
		int rgb = r + g + b;
		screen.setRGB(x, y, rgb);
	}

	public int getPixelColor(int x, int y) {
		return screen.getRGB(x, y);
	}
}
