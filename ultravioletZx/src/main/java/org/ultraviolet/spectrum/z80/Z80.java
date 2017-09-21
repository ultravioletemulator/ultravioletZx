package org.ultraviolet.spectrum.z80;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ultraviolet.spectrum.machines.Spectrum48k;

/**
 * Created by developer on 21/09/2017.
 */
public class Z80 {

	//Registers
	//	A,B,C,D,E            8-bit registers
	//	AF                   16-bit register containing A and flags
	//	BC                   16-bit register containing B and C
	//	DE                   16-bit register containing D and E
	//	HL                   16-bit register used for addressing
	//	F                    8-bit Flag register
	//	I                    8-bit Interrupt Page address register
	//	IX,IY                16-bit index registers
	//	PC                   16-bit Program Counter register
	//	R                    8-bit Memory Refresh register
	//	SP                   16-bit Stack Pointer register
	//	8-bit registers A,B,C,D,E
	private byte regA;
	private byte regB;
	private byte regC;
	private byte regD;
	private byte regE;
	// 16Bits
	private byte[] regAF;
	private byte[] regBC;
	private byte[] regDE;
	private byte[] regHL;
	//	8-bit Flag register 	F
	private byte regF;
	//	8-bit Interrupt Page address register I
	private byte regI;
	//	16-bit index registers  	IX,IY
	private byte[] regIX;
	private byte[] regIY;
	//	PC                   16-bit Program Counter register
	private byte[] pc;
	//	8-bit Memory Refresh register 		R
	private byte regR;
	//	SP                   16-bit Stack Pointer register
	private byte[] sp;
	static final Logger logger = LoggerFactory.getLogger(Z80.class);

	public Z80() {
	}

	public Z80(byte regA, byte regB, byte regC, byte regD, byte regE, byte[] regAF, byte[] regBC, byte[] regDE, byte[] regHL, byte regF, byte regI, byte[] regIX, byte[] regIY, byte[] pc, byte regR, byte[] sp) {
		this.regA = regA;
		this.regB = regB;
		this.regC = regC;
		this.regD = regD;
		this.regE = regE;
		this.regAF = regAF;
		this.regBC = regBC;
		this.regDE = regDE;
		this.regHL = regHL;
		this.regF = regF;
		this.regI = regI;
		this.regIX = regIX;
		this.regIY = regIY;
		this.pc = pc;
		this.regR = regR;
		this.sp = sp;
	}

	public byte getRegA() {
		return regA;
	}

	public void setRegA(byte regA) {
		this.regA = regA;
	}

	public byte getRegB() {
		return regB;
	}

	public void setRegB(byte regB) {
		this.regB = regB;
	}

	public byte getRegC() {
		return regC;
	}

	public void setRegC(byte regC) {
		this.regC = regC;
	}

	public byte getRegD() {
		return regD;
	}

	public void setRegD(byte regD) {
		this.regD = regD;
	}

	public byte getRegE() {
		return regE;
	}

	public void setRegE(byte regE) {
		this.regE = regE;
	}

	public byte[] getRegAF() {
		return regAF;
	}

	public void setRegAF(byte[] regAF) {
		this.regAF = regAF;
	}

	public byte[] getRegBC() {
		return regBC;
	}

	public void setRegBC(byte[] regBC) {
		this.regBC = regBC;
	}

	public byte[] getRegDE() {
		return regDE;
	}

	public void setRegDE(byte[] regDE) {
		this.regDE = regDE;
	}

	public byte[] getRegHL() {
		return regHL;
	}

	public void setRegHL(byte[] regHL) {
		this.regHL = regHL;
	}

	public byte getRegF() {
		return regF;
	}

	public void setRegF(byte regF) {
		this.regF = regF;
	}

	public byte getRegI() {
		return regI;
	}

	public void setRegI(byte regI) {
		this.regI = regI;
	}

	public byte[] getRegIX() {
		return regIX;
	}

	public void setRegIX(byte[] regIX) {
		this.regIX = regIX;
	}

	public byte[] getRegIY() {
		return regIY;
	}

	public void setRegIY(byte[] regIY) {
		this.regIY = regIY;
	}

	public byte[] getPc() {
		return pc;
	}

	public void setPc(byte[] pc) {
		this.pc = pc;
	}

	public byte getRegR() {
		return regR;
	}

	public void setRegR(byte regR) {
		this.regR = regR;
	}

	public byte[] getSp() {
		return sp;
	}

	public void setSp(byte[] sp) {
		this.sp = sp;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public static byte[] intToBytes(int x) {
		byte[] bytes = new byte[4];

		for (int i = 0; x != 0; i++, x >>>= 8) {
			bytes[i] = (byte) (x & 0xFF);
		}

		return bytes;
	}

	public static int bytesToInt(byte[] arr, int off) {
		return arr[off]<<8 &0xFF00 | arr[off+1]&0xFF;
	} // end of getInt
}
