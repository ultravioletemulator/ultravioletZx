package org.ultraviolet.spectrum.z80;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ultraviolet.spectrum.exceptions.MemoryException;

/**
 * Created by developer on 21/09/2017.
 */
public class Memory {

	static final Logger logger = LoggerFactory.getLogger(Memory.class);
	//
	protected byte[] mem;
	protected int size;

	public Memory() {
	}

	public Memory(int size) {
		this.size = size;
	}

	public byte[] getMem() {
		return mem;
	}

	public void setMem(byte[] mem) throws MemoryException {
		if (mem != null && mem.length <= size) {
			this.mem = mem;
		} else {
			throw new MemoryException("Memory chunk too big. Does not fit into " + this.size + " memory");
		}
	}

	public void loadMem(byte[] content) throws MemoryException {
		setMem(content);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte getByteFromAddress(int addr) {
		byte res = mem[addr];
		return res;
	}

	public void setByteToAddress(int addr, byte content) {
		mem[addr] = content;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
