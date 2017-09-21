package org.ultraviolet.spectrum;

import org.apache.commons.io.FileUtils;
import org.ultraviolet.spectrum.exceptions.MemoryException;
import org.ultraviolet.spectrum.loaders.FileFormats;
import org.ultraviolet.spectrum.loaders.Loader;
import org.ultraviolet.spectrum.z80.Rom;

import java.io.File;
import java.io.IOException;

/**
 * Created by developer on 21/09/2017.
 */
public class InitUtils {

	public static void loadRom(String romFile, Rom rom)
			throws IOException, MemoryException {
		byte[] fileContent = FileUtils.readFileToByteArray(new File(romFile));
		rom.setMem(fileContent);
	}

	public static void loadContent(String contentFile, Loader loader)
			throws IOException {
		byte[] fileContent = FileUtils.readFileToByteArray(new File(contentFile));
		String extension = contentFile.substring(contentFile.length() - 4, contentFile.length());
		switch (extension) {
		case FileFormats.Z80_EXTENSION:
			loader.load(fileContent);
			loader.parseContent();
			break;
		case FileFormats.TZX_EXTENSION:
			loader.load(fileContent);
			loader.parseContent();
			break;
		case FileFormats.TAP_EXTENSION:
			loader.load(fileContent);
			loader.parseContent();
			break;
		case FileFormats.DSK_EXTENSION:
			loader.load(fileContent);
			loader.parseContent();
			break;
		}
	}
}
