package org.ultraviolet.spectrum.core;

/**
 * Created by developer on 22/09/2017.
 */
public class Masks {

	public final static byte MASK_OP1 = (byte) 0xFF000000;
	public final static byte MASK_OP2 = (byte) 0xFFFF0000;
	public final static byte MASK_OP3 = (byte) 0xFFFFFF00;
	public final static byte MASK_PAR1 = (byte) 0x00FF0000;
	public final static byte MASK_PAR2 = (byte) 0x0000FF00;
	public final static byte MASK_PAR3 = (byte) 0x000000FF;
	public final static byte MASK_CARRY_BYTE = (byte) 0x01;

	//Flag Masks
	//Bit 	7 6 5 4 3 2 	1 0
	//Pos	S Z X H X P/V	N C
	public final static byte MASK_FLAG_C = (byte) 0x01;
	public final static byte MASK_FLAG_N = (byte) 0x02;
	public final static byte MASK_FLAG_PV = (byte) 0x04;
	public final static byte MASK_FLAG_X3 = (byte) 0x08;
	public final static byte MASK_FLAG_H = (byte) 0x10;
	public final static byte MASK_FLAG_X5 = (byte) 0x20;
	public final static byte MASK_FLAG_Z = (byte) 0x40;
	public final static byte MASK_FLAG_S = (byte) 0x80;
}
