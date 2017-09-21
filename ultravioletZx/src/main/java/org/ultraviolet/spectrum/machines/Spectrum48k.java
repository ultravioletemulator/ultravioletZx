package org.ultraviolet.spectrum.machines;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ultraviolet.spectrum.exceptions.MemoryException;
import org.ultraviolet.spectrum.exceptions.ZxException;
import org.ultraviolet.spectrum.z80.*;
import org.ultraviolet.spectrum.loaders.Loader;
import org.ultraviolet.spectrum.loaders.TapeLoader;

import java.io.File;
import java.io.IOException;

/**
 * Created by developer on 21/09/2017.
 */
public class Spectrum48k extends Machine {

	static final Logger logger = LoggerFactory.getLogger(Spectrum48k.class);

	public Spectrum48k() {
	}

	public void init() throws ZxException {
		this.z80 = new Z80();
		this.rom = new Rom(Constants.S48K_ROM);
		this.ram = new Ram(Constants.S48K_RAM);
		this.ula = new Ula();
		this.loader = new TapeLoader();
		try {
			loadBios();
		} catch (IOException e) {
			throw new ZxException(e);
		} catch (MemoryException e) {
			throw new ZxException(e);
		}
	}

	public static final String FILE_48K_BIOS = "bios/48e.rom";

	public void loadBios() throws IOException, MemoryException {
		byte[] content = FileUtils.readFileToByteArray(new File(FILE_48K_BIOS));
		this.rom.loadMem(content);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
