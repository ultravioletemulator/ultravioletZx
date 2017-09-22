package org.ultraviolet.spectrum.machines;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ultraviolet.spectrum.exceptions.MemoryException;
import org.ultraviolet.spectrum.exceptions.ZxException;
import org.ultraviolet.spectrum.loaders.Loader;
import org.ultraviolet.spectrum.loaders.TapeLoader;
import org.ultraviolet.spectrum.z80.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

/**
 * Created by developer on 21/09/2017.
 */
public abstract class Machine {

	protected Loader loader;
	protected Memory ram;
	protected Memory rom;
	protected Z80 z80;
	protected Ula ula;
	protected Stack<Byte> stack = new Stack<Byte>();
	static final Logger logger = LoggerFactory.getLogger(Machine.class);

	public Machine() {
	}

	public abstract void init() throws IOException, ZxException;

	public void runBios()
			throws InterruptedException, InvocationTargetException, IllegalAccessException, IOException {
		logger.debug("Runninng Machine Bios :" + this);
		while (true) {
			byte content1 = rom.getByteFromAddress(z80.getPc());
			byte content2 = rom.getByteFromAddress(z80.getPc() + 1);
			byte content3 = rom.getByteFromAddress(z80.getPc() + 2);
			byte[] contentArr = new byte[] { content1, content2, content3 };
			logger.debug("OpCode: " + Z80.bytesToHex(contentArr));
			OpCodesUtils.runOpCode(this, contentArr);
			z80.setPc((short) (z80.getPc() + 1));
			Thread.sleep(200);
		}
	}

	public void run()
			throws InterruptedException, InvocationTargetException, IllegalAccessException, IOException {
		logger.debug("Runninng Machine:" + this);
		runBios();
		//		while (true) {
		//			logger.debug("Running ");
		//			Thread.sleep(1000l);
		//		}
	}

	public Machine(Loader loader, Memory ram, Memory rom, Z80 z80, Ula ula) {
		this.loader = loader;
		this.ram = ram;
		this.rom = rom;
		this.z80 = z80;
		this.ula = ula;
	}

	public Loader getLoader() {
		return loader;
	}

	public void setLoader(Loader loader) {
		this.loader = loader;
	}

	public Memory getRam() {
		return ram;
	}

	public void setRam(Memory ram) {
		this.ram = ram;
	}

	public Memory getRom() {
		return rom;
	}

	public void setRom(Memory rom) {
		this.rom = rom;
	}

	public Z80 getZ80() {
		return z80;
	}

	public void setZ80(Z80 z80) {
		this.z80 = z80;
	}

	public Ula getUla() {
		return ula;
	}

	public void setUla(Ula ula) {
		this.ula = ula;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
