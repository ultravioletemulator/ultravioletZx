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
//	public final static byte MASK_CARRY_BYTE = (byte) 0x01;
	public final static byte MASK_CARRY_BYTE_LSB = (byte) 0x01;
	public final static byte MASK_CARRY_BYTE_MSB = (byte) 0x80;
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
	//
	// Bit Masks
	//
	public final static byte MASK_BIT_0 = (byte) 0x01;
	public final static byte MASK_BIT_1 = (byte) 0x02;
	public final static byte MASK_BIT_2 = (byte) 0x04;
	public final static byte MASK_BIT_3 = (byte) 0x08;
	public final static byte MASK_BIT_4 = (byte) 0x10;
	public final static byte MASK_BIT_5 = (byte) 0x20;
	public final static byte MASK_BIT_6 = (byte) 0x40;
	public final static byte MASK_BIT_7 = (byte) 0x80;
	//
	public final static byte MASK_BIT_0_N = (byte) 0xFE;
	public final static byte MASK_BIT_1_N = (byte) 0xFD;
	public final static byte MASK_BIT_2_N = (byte) 0xFB;
	public final static byte MASK_BIT_3_N = (byte) 0xF7;
	public final static byte MASK_BIT_4_N = (byte) 0xEF;
	public final static byte MASK_BIT_5_N = (byte) 0xDF;
	public final static byte MASK_BIT_6_N = (byte) 0xBF;
	public final static byte MASK_BIT_7_N = (byte) 0x7F;
}
