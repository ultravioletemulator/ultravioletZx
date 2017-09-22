package org.ultraviolet.spectrum.z80;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.validator.routines.RegexValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ultraviolet.spectrum.core.Masks;
import org.ultraviolet.spectrum.fileReaders.OpReader;
import org.ultraviolet.spectrum.machines.Machine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by developer on 21/09/2017.
 */
public class OpCodesUtils {

	static final Logger logger = LoggerFactory.getLogger(OpCodesUtils.class);

	public static void f00() {
		//NOP
	}

	public static void f01(Machine machine, int par1, int par2) {
		//	LD BC,NN                ; 01 XX XX
		machine.getZ80().setRegBC((short) par2);
	}

	public static void f02(Machine machine, int par1, int par2) {
		//	LD (BC),A               ; 02
		byte content = machine.getZ80().getRegA();
		machine.getRam().setByteToAddress(par1, content);
	}

	public static void f03(Machine machine, int par1) {
		//	INC BC                  ; 03
		machine.getZ80().setRegBC((short) (machine.getZ80().getRegBC() + 1));
	}

	public static void f04(Machine machine) {
		//	INC B                   ; 04
		machine.getZ80().setRegBC((short) (machine.getZ80().getRegB() + 1));
	}

	public static void f05(Machine machine) {
		//	DEC B                   ; 05
		machine.getZ80().setRegBC((short) (machine.getZ80().getRegB() - 1));
	}

	public static void f06(Machine machine, byte par1) {
		//	LD B,N                  ; 06 XX
		machine.getZ80().setRegB(par1);
	}

	public static void f07(Machine machine) {
		//			RLCA                    ; 07
		byte val = machine.getZ80().getRegA();
		byte carry = (byte) (val & Masks.MASK_CARRY_BYTE);
		byte res = (byte) ((byte) (val << 1) & carry);
		machine.getZ80().setRegA(res);
		Z80.setFlag(machine, carry);
	}

	public static void f08(Machine machine, byte par1) {
		//	EX AF,AF'               ; 08
		// TODO
	}

	public static void f09(Machine machine) {
		//	ADD HL,BC               ; 09
		Z80 z80 = machine.getZ80();
		short hl = (short) z80.getRegHL();
		short bc = (short) z80.getRegBC();
		try {
			short res = (short) (Math.addExact(hl, bc));
			z80.setRegHL(res);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.info("Overflow HL");
			Z80.setFlag(machine, Masks.MASK_FLAG_PV);
		}
	}

	public static void f0A(Machine machine) {
		//	LD A,(BC)               ; 0A
		Z80 z80 = machine.getZ80();
		byte val = machine.getRam().getByteFromAddress(z80.getRegBC());
		z80.setRegA(val);
	}

	public static void f0B(Machine machine) {
		//	DEC BC                  ; 0B
		Z80 z80 = machine.getZ80();
		int res = 0;
		try {
			res = Math.subtractExact(z80.getRegBC(), 1);
		} catch (Exception e) {
			//			e.printStackTrace();
			logger.info("Overflow BC");
			Z80.setFlag(machine, Masks.MASK_FLAG_PV);
		}
		z80.setRegBC((short) res);
	}

	public static void f0C(Machine machine) {
		//	INC C                   ; 0C
		Z80 z80 = machine.getZ80();
		byte res = 0;
		try {
			res = (byte) Math.addExact(z80.getRegC(), 1);
		} catch (Exception e) {
			//			e.printStackTrace();
			logger.info("Overflow C");
			Z80.setFlag(machine, Masks.MASK_FLAG_PV);
		}
		z80.setRegC((byte) res);
	}

	public static void f0D(Machine machine) {
		//	DEC C                   ; 0D
		Z80 z80 = machine.getZ80();
		byte res = 0;
		try {
			res = (byte) Math.subtractExact(z80.getRegC(), 1);
		} catch (Exception e) {
			//			e.printStackTrace();
			logger.info("Underflow C");
			Z80.setFlag(machine, Masks.MASK_FLAG_PV);
		}
		z80.setRegC((byte) res);
	}

	public static void f0E(Machine machine, byte p) {
		//	LD C,N                  ; 0E XX
		Z80 z80 = machine.getZ80();
		z80.setRegC(p);
	}

	public static void f0F(Machine machine, byte p) {
		//			RRCA                    ; 0F
		Z80 z80 = machine.getZ80();
		byte c = z80.getRegA();
		byte carry = (byte) (c & Masks.MASK_CARRY_BYTE);
		byte res = (byte) ((c >> 1) & carry);
		z80.setRegA(p);
	}

	public static void f10(Machine machine, byte p) {
		//	DJNZ $+2                ; 10
		Z80 z80 = machine.getZ80();
		// TODO
	}

	public static void f11(Machine machine, byte p1, byte p2) {
		//	LD DE,NN                ; 11 XX XX
		Z80 z80 = machine.getZ80();
		z80.setRegDE(Z80.bytesToShort(p1, p2));
	}

	public static void f11(Machine machine, short p) {
		//	LD DE,NN                ; 11 XX XX
		Z80 z80 = machine.getZ80();
		z80.setRegDE(p);
	}

	public static void f12(Machine machine) {
		//	LD (DE),A               ; 12
		Z80 z80 = machine.getZ80();
		machine.getRam().setByteToAddress(z80.getRegDE(), z80.getRegA());
	}

	public static void f13(Machine machine) {
		//	INC DE                  ; 13
		Z80 z80 = machine.getZ80();
		try {
			short res = (short) Math.addExact(z80.getRegDE(), 1);
			z80.setRegDE(res);
		} catch (Exception e) {
			//			e.printStackTrace();
			logger.info("Overflow DE");
			Z80.setFlag(machine, Masks.MASK_FLAG_PV);
		}
	}

	public static void f14(Machine machine) {
		//	INC D                   ; 14
		Z80 z80 = machine.getZ80();
		try {
			byte res = (byte) Math.addExact(z80.getRegD(), 1);
			z80.setRegD(res);
		} catch (Exception e) {
			//			e.printStackTrace();
			logger.info("Overflow D");
			Z80.setFlag(machine, Masks.MASK_FLAG_PV);
		}
	}

	public static void f15(Machine machine) {
		//	DEC D                   ; 15
		Z80 z80 = machine.getZ80();
		try {
			byte res = (byte) Math.subtractExact(z80.getRegD(), 1);
			z80.setRegD(res);
		} catch (Exception e) {
			//			e.printStackTrace();
			logger.info("Underflow D");
			Z80.setFlag(machine, Masks.MASK_FLAG_PV);
		}
	}

	public static void f16(Machine machine, byte p) {
		//	LD D,N                  ; 16 XX
		Z80 z80 = machine.getZ80();
		z80.setRegD(p);
	}

	public static void f17(Machine machine, byte p) {
		//			RLA                     ; 17
		Z80 z80 = machine.getZ80();
		byte carryFP = z80.getCFlag();
		byte regA = z80.getRegA();
		byte carryP = (byte) (regA & Masks.MASK_CARRY_BYTE);
		byte res = (byte) (((byte) (regA << 1)) & carryFP);
		z80.setRegA(res);
		Z80.setFlag(machine, carryP);
	}
	//	JR $+2                  ; 18
	//	ADD HL,DE               ; 19
	//	LD A,(DE)               ; 1A
	//	DEC DE                  ; 1B
	//	INC E                   ; 1C
	//	DEC E                   ; 1D
	//	LD E,N                  ; 1E XX
	//			RRA                     ; 1F
	//	JR NZ,$+2               ; 20
	//	LD HL,NN                ; 21 XX XX
	//	LD (NN),HL              ; 22 XX XX
	//	INC HL                  ; 23
	//	INC H                   ; 24
	//	DEC H                   ; 25
	//	LD H,N                  ; 26 XX
	//			DAA                     ; 27
	//	JR Z,$+2                ; 28
	//	ADD HL,HL               ; 29
	//	LD HL,(NN)              ; 2A XX XX
	//	DEC HL                  ; 2B
	//	INC L                   ; 2C
	//	DEC L                   ; 2D
	//	LD L,N                  ; 2E XX
	//			CPL                     ; 2F
	//	JR NC,$+2               ; 30
	//	LD SP,NN                ; 31 XX XX
	//	LD (NN),A               ; 32 XX XX
	//	INC SP                  ; 33
	//	INC (HL)                ; 34
	//	DEC (HL)                ; 35
	//	LD (HL),N               ; 36 XX
	//			SCF                     ; 37
	//	JR C,$+2                ; 38
	//	ADD HL,SP               ; 39
	//	LD A,(NN)               ; 3A XX XX
	//	DEC SP                  ; 3B
	//	INC A                   ; 3C
	//	DEC A                   ; 3D
	//	LD A,N                  ; 3E XX
	//			CCF                     ; 3F
	//	LD B,B                  ; 40
	//	LD B,C                  ; 41
	//	LD B,D                  ; 42
	//	LD B,E                  ; 43
	//	LD B,H                  ; 44
	//	LD B,L                  ; 45
	//	LD B,(HL)               ; 46
	//	LD B,A                  ; 47
	//	LD C,B                  ; 48
	//	LD C,C                  ; 49
	//	LD C,D                  ; 4A
	//	LD C,E                  ; 4B
	//	LD C,H                  ; 4C
	//	LD C,L                  ; 4D
	//	LD C,(HL)               ; 4E
	//	LD C,A                  ; 4F
	//	LD D,B                  ; 50
	//	LD D,C                  ; 51
	//	LD D,D                  ; 52
	//	LD D,E                  ; 53
	//	LD D,H                  ; 54
	//	LD D,L                  ; 55
	//	LD D,(HL)               ; 56
	//	LD D,A                  ; 57
	//	LD E,B                  ; 58
	//	LD E,C                  ; 59
	//	LD E,D                  ; 5A
	//	LD E,E                  ; 5B
	//	LD E,H                  ; 5C
	//	LD E,L                  ; 5D
	//	LD E,(HL)               ; 5E
	//	LD E,A                  ; 5F
	//	LD H,B                  ; 60
	//	LD H,C                  ; 61
	//	LD H,D                  ; 62
	//	LD H,E                  ; 63
	//	LD H,H                  ; 64
	//	LD H,L                  ; 65
	//	LD H,(HL)               ; 66
	//	LD H,A                  ; 67
	//	LD L,B                  ; 68
	//	LD L,C                  ; 69
	//	LD L,D                  ; 6A
	//	LD L,E                  ; 6B
	//	LD L,H                  ; 6C
	//	LD L,L                  ; 6D
	//	LD L,(HL)               ; 6E
	//	LD L,A                  ; 6F
	//	LD (HL),B               ; 70
	//	LD (HL),C               ; 71
	//	LD (HL),D               ; 72
	//	LD (HL),E               ; 73
	//	LD (HL),H               ; 74
	//	LD (HL),L               ; 75
	//	HALT                    ; 76
	//	LD (HL),A               ; 77
	//	LD A,B                  ; 78
	//	LD A,C                  ; 79
	//	LD A,D                  ; 7A
	//	LD A,E                  ; 7B
	//	LD A,H                  ; 7C
	//	LD A,L                  ; 7D
	//	LD A,(HL)               ; 7E
	//	LD A,A                  ; 7F
	//	ADD A,B                 ; 80
	//	ADD A,C                 ; 81
	//	ADD A,D                 ; 82
	//	ADD A,E                 ; 83
	//	ADD A,H                 ; 84
	//	ADD A,L                 ; 85
	//	ADD A,(HL)              ; 86
	//	ADD A,A                 ; 87
	//	ADC A,B                 ; 88
	//	ADC A,C                 ; 89
	//	ADC A,D                 ; 8A
	//	ADC A,E                 ; 8B
	//	ADC A,H                 ; 8C
	//	ADC A,L                 ; 8D
	//	ADC A,(HL)              ; 8E
	//	ADC A,A                 ; 8F
	//	SUB B                   ; 90
	//	SUB C                   ; 91
	//	SUB D                   ; 92
	//	SUB E                   ; 93
	//	SUB H                   ; 94
	//	SUB L                   ; 95
	//	SUB (HL)                ; 96
	//	SUB A                   ; 97
	//	SBC B                   ; 98
	//	SBC C                   ; 99
	//	SBC D                   ; 9A
	//	SBC E                   ; 9B
	//	SBC H                   ; 9C
	//	SBC L                   ; 9D
	//	SBC (HL)                ; 9E
	//	SBC A                   ; 9F
	//	AND B                   ; A0
	//	AND C                   ; A1
	//	AND D                   ; A2
	//	AND E                   ; A3
	//	AND H                   ; A4
	//	AND L                   ; A5
	//	AND (HL)                ; A6
	//	AND A                   ; A7
	//	XOR B                   ; A8
	//	XOR C                   ; A9
	//	XOR D                   ; AA
	//	XOR E                   ; AB
	//	XOR H                   ; AC
	//	XOR L                   ; AD
	//	XOR (HL)                ; AE

	public static void fAF(Machine machine) {
		//	XOR A                   ; AF
		Z80 z80 = machine.getZ80();
		byte acc = z80.getRegA();
		byte res = (byte) (acc ^ acc);
		z80.setRegA(res);
	}

	//	OR B                    ; B0
	//	OR C                    ; B1
	//	OR D                    ; B2
	//	OR E                    ; B3
	//	OR H                    ; B4
	//	OR L                    ; B5
	//	OR (HL)                 ; B6
	//	OR A                    ; B7
	//	CP B                    ; B8
	//	CP C                    ; B9
	//	CP D                    ; BA
	//	CP E                    ; BB
	//	CP H                    ; BC
	//	CP L                    ; BD
	//	CP (HL)                 ; BE
	//	CP A                    ; BF
	//	RET NZ                  ; C0
	//	POP BC                  ; C1
	//	JP NZ,$+3               ; C2
	//	JP $+3                  ; C3
	//	CALL NZ,NN              ; C4 XX XX
	//	PUSH BC                 ; C5
	//	ADD A,N                 ; C6 XX
	//	RST 0                   ; C7
	//	RET Z                   ; C8
	//			RET                     ; C9
	//	JP Z,$+3                ; CA
	//	RLC B                   ; CB 00
	//	RLC C                   ; CB 01
	//	RLC D                   ; CB 02
	//	RLC E                   ; CB 03
	//	RLC H                   ; CB 04
	//	RLC L                   ; CB 05
	//	RLC (HL)                ; CB 06
	//	RLC A                   ; CB 07
	//	RRC B                   ; CB 08
	//	RRC C                   ; CB 09
	//	RRC D                   ; CB 0A
	//	RRC E                   ; CB 0B
	//	RRC H                   ; CB 0C
	//	RRC L                   ; CB 0D
	//	RRC (HL)                ; CB 0E
	//	RRC A                   ; CB 0F
	//	RL  B                   ; CB 10
	//	RL  C                   ; CB 11
	//	RL  D                   ; CB 12
	//	RL  E                   ; CB 13
	//	RL  H                   ; CB 14
	//	RL  L                   ; CB 15
	//	RL  (HL)                ; CB 16
	//	RL  A                   ; CB 17
	//	RR  B                   ; CB 18
	//	RR  C                   ; CB 19
	//	RR  D                   ; CB 1A
	//	RR  E                   ; CB 1B
	//	RR  H                   ; CB 1C
	//	RR  L                   ; CB 1D
	//	RR  (HL)                ; CB 1E
	//	RR  A                   ; CB 1F
	//	SLA B                   ; CB 20
	//	SLA C                   ; CB 21
	//	SLA D                   ; CB 22
	//	SLA E                   ; CB 23
	//	SLA H                   ; CB 24
	//	SLA L                   ; CB 25
	//	SLA (HL)                ; CB 26
	//	SLA A                   ; CB 27
	//	SRA B                   ; CB 28
	//	SRA C                   ; CB 29
	//	SRA D                   ; CB 2A
	//	SRA E                   ; CB 2B
	//	SRA H                   ; CB 2C
	//	SRA L                   ; CB 2D
	//	SRA (HL)                ; CB 2E
	//	SRA A                   ; CB 2F
	//	SRL B                   ; CB 38
	//	SRL C                   ; CB 39
	//	SRL D                   ; CB 3A
	//	SRL E                   ; CB 3B
	//	SRL H                   ; CB 3C
	//	SRL L                   ; CB 3D
	//	SRL (HL)                ; CB 3E
	//	SRL A                   ; CB 3F
	//	BIT 0,B                 ; CB 40
	//	BIT 0,C                 ; CB 41
	//	BIT 0,D                 ; CB 42
	//	BIT 0,E                 ; CB 43
	//	BIT 0,H                 ; CB 44
	//	BIT 0,L                 ; CB 45
	//	BIT 0,(HL)              ; CB 46
	//	BIT 0,A                 ; CB 47
	//	BIT 1,B                 ; CB 48
	//	BIT 1,C                 ; CB 49
	//	BIT 1,D                 ; CB 4A
	//	BIT 1,E                 ; CB 4B
	//	BIT 1,H                 ; CB 4C
	//	BIT 1,L                 ; CB 4D
	//	BIT 1,(HL)              ; CB 4E
	//	BIT 1,A                 ; CB 4F
	//	BIT 2,B                 ; CB 50
	//	BIT 2,C                 ; CB 51
	//	BIT 2,D                 ; CB 52
	//	BIT 2,E                 ; CB 53
	//	BIT 2,H                 ; CB 54
	//	BIT 2,L                 ; CB 55
	//	BIT 2,(HL)              ; CB 56
	//	BIT 2,A                 ; CB 57
	//	BIT 3,B                 ; CB 58
	//	BIT 3,C                 ; CB 59
	//	BIT 3,D                 ; CB 5A
	//	BIT 3,E                 ; CB 5B
	//	BIT 3,H                 ; CB 5C
	//	BIT 3,L                 ; CB 5D
	//	BIT 3,(HL)              ; CB 5E
	//	BIT 3,A                 ; CB 5F
	//	BIT 4,B                 ; CB 60
	//	BIT 4,C                 ; CB 61
	//	BIT 4,D                 ; CB 62
	//	BIT 4,E                 ; CB 63
	//	BIT 4,H                 ; CB 64
	//	BIT 4,L                 ; CB 65
	//	BIT 4,(HL)              ; CB 66
	//	BIT 4,A                 ; CB 67
	//	BIT 5,B                 ; CB 68
	//	BIT 5,C                 ; CB 69
	//	BIT 5,D                 ; CB 6A
	//	BIT 5,E                 ; CB 6B
	//	BIT 5,H                 ; CB 6C
	//	BIT 5,L                 ; CB 6D
	//	BIT 5,(HL)              ; CB 6E
	//	BIT 5,A                 ; CB 6F
	//	BIT 6,B                 ; CB 70
	//	BIT 6,C                 ; CB 71
	//	BIT 6,D                 ; CB 72
	//	BIT 6,E                 ; CB 73
	//	BIT 6,H                 ; CB 74
	//	BIT 6,L                 ; CB 75
	//	BIT 6,(HL)              ; CB 76
	//	BIT 6,A                 ; CB 77
	//	BIT 7,B                 ; CB 78
	//	BIT 7,C                 ; CB 79
	//	BIT 7,D                 ; CB 7A
	//	BIT 7,E                 ; CB 7B
	//	BIT 7,H                 ; CB 7C
	//	BIT 7,L                 ; CB 7D
	//	BIT 7,(HL)              ; CB 7E
	//	BIT 7,A                 ; CB 7F
	//	RES 0,B                 ; CB 80
	//	RES 0,C                 ; CB 81
	//	RES 0,D                 ; CB 82
	//	RES 0,E                 ; CB 83
	//	RES 0,H                 ; CB 84
	//	RES 0,L                 ; CB 85
	//	RES 0,(HL)              ; CB 86
	//	RES 0,A                 ; CB 87
	//	RES 1,B                 ; CB 88
	//	RES 1,C                 ; CB 89
	//	RES 1,D                 ; CB 8A
	//	RES 1,E                 ; CB 8B
	//	RES 1,H                 ; CB 8C
	//	RES 1,L                 ; CB 8D
	//	RES 1,(HL)              ; CB 8E
	//	RES 1,A                 ; CB 8F
	//	RES 2,B                 ; CB 90
	//	RES 2,C                 ; CB 91
	//	RES 2,D                 ; CB 92
	//	RES 2,E                 ; CB 93
	//	RES 2,H                 ; CB 94
	//	RES 2,L                 ; CB 95
	//	RES 2,(HL)              ; CB 96
	//	RES 2,A                 ; CB 97
	//	RES 3,B                 ; CB 98
	//	RES 3,C                 ; CB 99
	//	RES 3,D                 ; CB 9A
	//	RES 3,E                 ; CB 9B
	//	RES 3,H                 ; CB 9C
	//	RES 3,L                 ; CB 9D
	//	RES 3,(HL)              ; CB 9E
	//	RES 3,A                 ; CB 9F
	//	RES 4,B                 ; CB A0
	//	RES 4,C                 ; CB A1
	//	RES 4,D                 ; CB A2
	//	RES 4,E                 ; CB A3
	//	RES 4,H                 ; CB A4
	//	RES 4,L                 ; CB A5
	//	RES 4,(HL)              ; CB A6
	//	RES 4,A                 ; CB A7
	//	RES 5,B                 ; CB A8
	//	RES 5,C                 ; CB A9
	//	RES 5,D                 ; CB AA
	//	RES 5,E                 ; CB AB
	//	RES 5,H                 ; CB AC
	//	RES 5,L                 ; CB AD
	//	RES 5,(HL)              ; CB AE
	//	RES 5,A                 ; CB AF
	//	RES 6,B                 ; CB B0
	//	RES 6,C                 ; CB B1
	//	RES 6,D                 ; CB B2
	//	RES 6,E                 ; CB B3
	//	RES 6,H                 ; CB B4
	//	RES 6,L                 ; CB B5
	//	RES 6,(HL)              ; CB B6
	//	RES 6,A                 ; CB B7
	//	RES 7,B                 ; CB B8
	//	RES 7,C                 ; CB B9
	//	RES 7,D                 ; CB BA
	//	RES 7,E                 ; CB BB
	//	RES 7,H                 ; CB BC
	//	RES 7,L                 ; CB BD
	//	RES 7,(HL)              ; CB BE
	//	RES 7,A                 ; CB BF
	//	SET 0,B                 ; CB C0
	//	SET 0,C                 ; CB C1
	//	SET 0,D                 ; CB C2
	//	SET 0,E                 ; CB C3
	//	SET 0,H                 ; CB C4
	//	SET 0,L                 ; CB C5
	//	SET 0,(HL)              ; CB C6
	//	SET 0,A                 ; CB C7
	//	SET 1,B                 ; CB C8
	//	SET 1,C                 ; CB C9
	//	SET 1,D                 ; CB CA
	//	SET 1,E                 ; CB CB
	//	SET 1,H                 ; CB CC
	//	SET 1,L                 ; CB CD
	//	SET 1,(HL)              ; CB CE
	//	SET 1,A                 ; CB CF
	//	SET 2,B                 ; CB D0
	//	SET 2,C                 ; CB D1
	//	SET 2,D                 ; CB D2
	//	SET 2,E                 ; CB D3
	//	SET 2,H                 ; CB D4
	//	SET 2,L                 ; CB D5
	//	SET 2,(HL)              ; CB D6
	//	SET 2,A                 ; CB D7
	//	SET 3,B                 ; CB D8
	//	SET 3,C                 ; CB D9
	//	SET 3,D                 ; CB DA
	//	SET 3,E                 ; CB DB
	//	SET 3,H                 ; CB DC
	//	SET 3,L                 ; CB DD
	//	SET 3,(HL)              ; CB DE
	//	SET 3,A                 ; CB DF
	//	SET 4,B                 ; CB E0
	//	SET 4,C                 ; CB E1
	//	SET 4,D                 ; CB E2
	//	SET 4,E                 ; CB E3
	//	SET 4,H                 ; CB E4
	//	SET 4,L                 ; CB E5
	//	SET 4,(HL)              ; CB E6
	//	SET 4,A                 ; CB E7
	//	SET 5,B                 ; CB E8
	//	SET 5,C                 ; CB E9
	//	SET 5,D                 ; CB EA
	//	SET 5,E                 ; CB EB
	//	SET 5,H                 ; CB EC
	//	SET 5,L                 ; CB ED
	//	SET 5,(HL)              ; CB EE
	//	SET 5,A                 ; CB EF
	//	SET 6,B                 ; CB F0
	//	SET 6,C                 ; CB F1
	//	SET 6,D                 ; CB F2
	//	SET 6,E                 ; CB F3
	//	SET 6,H                 ; CB F4
	//	SET 6,L                 ; CB F5
	//	SET 6,(HL)              ; CB F6
	//	SET 6,A                 ; CB F7
	//	SET 7,B                 ; CB F8
	//	SET 7,C                 ; CB F9
	//	SET 7,D                 ; CB FA
	//	SET 7,E                 ; CB FB
	//	SET 7,H                 ; CB FC
	//	SET 7,L                 ; CB FD
	//	SET 7,(HL)              ; CB FE
	//	SET 7,A                 ; CB FF
	//	CALL Z,NN               ; CC XX XX
	//	CALL NN                 ; CD XX XX
	//	ADC A,N                 ; CE XX
	//	RST 8H                  ; CF
	//	RET NC                  ; D0
	//	POP DE                  ; D1
	//	JP NC,$+3               ; D2
	//	OUT (N),A               ; D3 XX
	//	CALL NC,NN              ; D4 XX XX
	//	CALL NC,NN              ; D4 XX XX
	//	PUSH DE                 ; D5
	//	SUB N                   ; D6 XX
	//	RST 10H                 ; D7
	//	RET C                   ; D8
	//			EXX                     ; D9
	//	JP C,$+3                ; DA
	//	IN A,(N)                ; DB XX
	//	CALL C,NN               ; DC XX XX
	//	ADD IX,BC               ; DD 09
	//	ADD IX,DE               ; DD 19
	//	LD IX,NN                ; DD 21 XX XX
	//	LD (NN),IX              ; DD 22 XX XX
	//	INC IX                  ; DD 23
	//	ADD IX,IX               ; DD 29
	//	LD IX,(NN)              ; DD 2A XX XX
	//	DEC IX                  ; DD 2B
	//	INC (IX+N)              ; DD 34 XX
	//	DEC (IX+N)              ; DD 35 XX
	//	LD (IX+N),N             ; DD 36 XX XX
	//	ADD IX,SP               ; DD 39
	//	LD B,(IX+N)             ; DD 46 XX
	//	LD C,(IX+N)             ; DD 4E XX
	//	LD D,(IX+N)             ; DD 56 XX
	//	LD E,(IX+N)             ; DD 5E XX
	//	LD H,(IX+N)             ; DD 66 XX
	//	LD L,(IX+N)             ; DD 6E XX
	//	LD (IX+N),B             ; DD 70 XX
	//	LD (IX+N),C             ; DD 71 XX
	//	LD (IX+N),D             ; DD 72 XX
	//	LD (IX+N),E             ; DD 73 XX
	//	LD (IX+N),H             ; DD 74 XX
	//	LD (IX+N),L             ; DD 75 XX
	//	LD (IX+N),A             ; DD 77 XX
	//	LD A,(IX+N)             ; DD 7E XX
	//	ADD A,(IX+N)            ; DD 86 XX
	//	ADC A,(IX+N)            ; DD 8E XX
	//	SUB (IX+N)              ; DD 96 XX
	//	SBC A,(IX+N)            ; DD 9E XX
	//	AND (IX+N)              ; DD A6 XX
	//	XOR (IX+N)              ; DD AE XX
	//	OR (IX+N)               ; DD B6 XX
	//	CP (IX+N)               ; DD BE XX
	//	RLC (IX+N)              ; DD CB XX 06
	//	RRC (IX+N)              ; DD CB XX 0E
	//	RL (IX+N)               ; DD CB XX 16
	//	RR (IX+N)               ; DD CB XX 1E
	//	SLA (IX+N)              ; DD CB XX 26
	//	SRA (IX+N)              ; DD CB XX 2E
	//	BIT 0,(IX+N)            ; DD CB XX 46
	//	BIT 1,(IX+N)            ; DD CB XX 4E
	//	BIT 2,(IX+N)            ; DD CB XX 56
	//	BIT 3,(IX+N)            ; DD CB XX 5E
	//	BIT 4,(IX+N)            ; DD CB XX 66
	//	BIT 5,(IX+N)            ; DD CB XX 6E
	//	BIT 6,(IX+N)            ; DD CB XX 76
	//	BIT 7,(IX+N)            ; DD CB XX 7E
	//	RES 0,(IX+N)            ; DD CB XX 86
	//	RES 1,(IX+N)            ; DD CB XX 8E
	//	RES 2,(IX+N)            ; DD CB XX 96
	//	RES 3,(IX+N)            ; DD CB XX 9E
	//	RES 4,(IX+N)            ; DD CB XX A6
	//	RES 5,(IX+N)            ; DD CB XX AE
	//	RES 6,(IX+N)            ; DD CB XX B6
	//	RES 7,(IX+N)            ; DD CB XX BE
	//	SET 0,(IX+N)            ; DD CB XX C6
	//	SET 1,(IX+N)            ; DD CB XX CE
	//	SET 2,(IX+N)            ; DD CB XX D6
	//	SET 3,(IX+N)            ; DD CB XX DE
	//	SET 4,(IX+N)            ; DD CB XX E6
	//	SET 5,(IX+N)            ; DD CB XX EE
	//	SET 6,(IX+N)            ; DD CB XX F6
	//	SET 7,(IX+N)            ; DD CB XX FE
	//	POP IX                  ; DD E1
	//	EX (SP),IX              ; DD E3
	//	PUSH IX                 ; DD E5
	//	JP (IX)                 ; DD E9
	//	LD SP,IX                ; DD F9
	//	SBC A,N                 ; DE XX
	//	RST 18H                 ; DF
	//	RET PO                  ; E0
	//	POP HL                  ; E1
	//	JP PO,$+3               ; E2
	//	EX (SP),HL              ; E3
	//	CALL PO,NN              ; E4 XX XX
	//	PUSH HL                 ; E5
	//	AND N                   ; E6 XX
	//	RST 20H                 ; E7
	//	RET PE                  ; E8
	//	JP (HL)                 ; E9
	//	JP PE,$+3               ; EA
	//	EX DE,HL                ; EB
	//	CALL PE,NN              ; EC XX XX
	//	IN B,(C)                ; ED 40
	//	OUT (C),B               ; ED 41
	//	SBC HL,BC               ; ED 42
	//	LD (NN),BC              ; ED 43 XX XX
	//	NEG                     ; ED 44
	//	RETN                    ; ED 45
	//	IM 0                    ; ED 46
	//	LD I,A                  ; ED 47
	//	IN C,(C)                ; ED 48
	//	OUT (C),C               ; ED 49
	//	ADC HL,BC               ; ED 4A
	//	LD BC,(NN)              ; ED 4B XX XX
	//			RETI                    ; ED 4D
	//	IN D,(C)                ; ED 50
	//	OUT (C),D               ; ED 51
	//	SBC HL,DE               ; ED 52
	//	LD (NN),DE              ; ED 53 XX XX
	//	IM 1                    ; ED 56
	//	LD A,I                  ; ED 57
	//	IN E,(C)                ; ED 58
	//	OUT (C),E               ; ED 59
	//	ADC HL,DE               ; ED 5A
	//	LD DE,(NN)              ; ED 5B XX XX
	//	IM 2                    ; ED 5E
	//	IN H,(C)                ; ED 60
	//	OUT (C),H               ; ED 61
	//	SBC HL,HL               ; ED 62
	//	RRD                     ; ED 67
	//	IN L,(C)                ; ED 68
	//	OUT (C),L               ; ED 69
	//	ADC HL,HL               ; ED 6A
	//			RLD                     ; ED 6F
	//	SBC HL,SP               ; ED 72
	//	LD (NN),SP              ; ED 73 XX XX
	//	IN A,(C)                ; ED 78
	//	OUT (C),A               ; ED 79
	//	ADC HL,SP               ; ED 7A
	//	LD SP,(NN)              ; ED 7B XX XX
	//			LDI                     ; ED A0
	//	CPI                     ; ED A1
	//	INI                     ; ED A2
	//	OUTI                    ; ED A3
	//	LDD                     ; ED A8
	//	CPD                     ; ED A9
	//	IND                     ; ED AA
	//	OUTD                    ; ED AB
	//	LDIR                    ; ED B0
	//	CPIR                    ; ED B1
	//	INIR                    ; ED B2
	//	OTIR                    ; ED B3
	//	LDDR                    ; ED B8
	//	CPDR                    ; ED B9
	//	INDR                    ; ED BA
	//	OTDR                    ; ED BB
	//	XOR N                   ; EE XX
	//	RST 28H                 ; EF
	//	RET P                   ; F0
	//	POP AF                  ; F1
	//	JP P,$+3                ; F2
	public static void fF3(Machine machine) {
		//			DI                      ; F3
		Z80 z80 = machine.getZ80();
		byte ints = z80.getRegI();
		byte res = (byte) (ints & Masks.MASK_BIT_1_N & Masks.MASK_BIT_2_N);
		z80.setRegI(res);
	}

	//	CALL P,NN               ; F4 XX XX
	//	PUSH AF                 ; F5
	//	OR N                    ; F6 XX
	//	RST 30H                 ; F7
	//	RET M                   ; F8
	//	LD SP,HL                ; F9
	//	JP M,$+3                ; FA
	//			EI                      ; FB
	//	CALL M,NN               ; FC XX XX
	//	ADD IY,BC               ; FD 09
	//	ADD IY,DE               ; FD 19
	//	LD IY,NN                ; FD 21 XX XX
	//	LD (NN),IY              ; FD 22 XX XX
	//	INC IY                  ; FD 23
	//	ADD IY,IY               ; FD 29
	//	LD IY,(NN)              ; FD 2A XX XX
	//	DEC IY                  ; FD 2B
	//	INC (IY+N)              ; FD 34 XX
	//	DEC (IY+N)              ; FD 35 XX
	//	LD (IY+N),N             ; FD 36 XX XX
	//	ADD IY,SP               ; FD 39
	//	LD B,(IY+N)             ; FD 46 XX
	//	LD C,(IY+N)             ; FD 4E XX
	//	LD D,(IY+N)             ; FD 56 XX
	//	LD E,(IY+N)             ; FD 5E XX
	//	LD H,(IY+N)             ; FD 66 XX
	//	LD L,(IY+N)             ; FD 6E XX
	//	LD (IY+N),B             ; FD 70 XX
	//	LD (IY+N),C             ; FD 71 XX
	//	LD (IY+N),D             ; FD 72 XX
	//	LD (IY+N),E             ; FD 73 XX
	//	LD (IY+N),H             ; FD 74 XX
	//	LD (IY+N),L             ; FD 75 XX
	//	LD (IY+N),A             ; FD 77 XX
	//	LD A,(IY+N)             ; FD 7E XX
	//	ADD A,(IY+N)            ; FD 86 XX
	//	ADC A,(IY+N)            ; FD 8E XX
	//	SUB (IY+N)              ; FD 96 XX
	//	SBC A,(IY+N)            ; FD 9E XX
	//	AND (IY+N)              ; FD A6 XX
	//	XOR (IY+N)              ; FD AE XX
	//	OR (IY+N)               ; FD B6 XX
	//	CP (IY+N)               ; FD BE XX
	//	RLC (IY+N)              ; FD CB XX 06
	//	RRC (IY+N)              ; FD CB XX 0E
	//	RL (IY+N)               ; FD CB XX 16
	//	RR (IY+N)               ; FD CB XX 1E
	//	SLA (IY+N)              ; FD CB XX 26
	//	SRA (IY+N)              ; FD CB XX 2E
	//	BIT 0,(IY+N)            ; FD CB XX 46
	//	BIT 1,(IY+N)            ; FD CB XX 4E
	//	BIT 2,(IY+N)            ; FD CB XX 56
	//	BIT 3,(IY+N)            ; FD CB XX 5E
	//	BIT 4,(IY+N)            ; FD CB XX 66
	//	BIT 5,(IY+N)            ; FD CB XX 6E
	//	BIT 6,(IY+N)            ; FD CB XX 76
	//	BIT 7,(IY+N)            ; FD CB XX 7E
	//	RES 0,(IY+N)            ; FD CB XX 86
	//	RES 1,(IY+N)            ; FD CB XX 8E
	//	RES 2,(IY+N)            ; FD CB XX 96
	//	RES 3,(IY+N)            ; FD CB XX 9E
	//	RES 4,(IY+N)            ; FD CB XX A6
	//	RES 5,(IY+N)            ; FD CB XX AE
	//	RES 6,(IY+N)            ; FD CB XX B6
	//	RES 7,(IY+N)            ; FD CB XX BE
	//	SET 0,(IY+N)            ; FD CB XX C6
	//	SET 1,(IY+N)            ; FD CB XX CE
	//	SET 2,(IY+N)            ; FD CB XX D6
	//	SET 3,(IY+N)            ; FD CB XX DE
	//	SET 4,(IY+N)            ; FD CB XX E6
	//	SET 5,(IY+N)            ; FD CB XX EE
	//	SET 6,(IY+N)            ; FD CB XX F6
	//	SET 7,(IY+N)            ; FD CB XX FE
	//	POP IY                  ; FD E1
	//	EX (SP),IY              ; FD E3
	//	PUSH IY                 ; FD E5
	//	JP (IY)                 ; FD E9
	//	LD SP,IY                ; FD F9
	//	CP N                    ; FE XX
	//	RST 38H                 ; FF
	//
	//
	public final static String RX_NO_PAR = "[0-9a-fA-F][0-9a-fA-F]XXXXXX";
	public final static String RX_1_PAR_HEX = "[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]XXXX";
	public final static String RX_1_PAR = "[0-9a-fA-F][0-9a-fA-F][0-9a-zA-Z][0-9a-zA-Z]XXXX";
	public final static String RX_2_PAR_HEX = "[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]XX";
	public final static String RX_2_PAR = "[0-9a-fA-F][0-9a-fA-F][0-9a-zA-Z][0-9a-zA-Z][0-9a-zA-Z][0-9a-zA-Z]XX";
	public final static String RX_3_PAR_HEX = "[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]";
	public final static String RX_3_PAR = "[0-9a-fA-F][0-9a-fA-F][0-9a-zA-Z][0-9a-zA-Z][0-9a-zA-Z][0-9a-zA-Z][0-9a-zA-Z][0-9a-zA-Z]";
	public final static String RX_1_PAR_3OP_HEX = "[0-9a-fA-F][0-9a-fA-F][Xx][Xx][0-9a-fA-F][0-9a-zA-Z]";
	private static RegexValidator rxNoparValidator = new RegexValidator(RX_NO_PAR);
	private static RegexValidator rx1parHexValidator = new RegexValidator(RX_1_PAR_HEX);
	private static RegexValidator rx1parValidator = new RegexValidator(RX_1_PAR);
	private static RegexValidator rx2parHexValidator = new RegexValidator(RX_2_PAR_HEX);
	private static RegexValidator rx2parValidator = new RegexValidator(RX_2_PAR);
	private static RegexValidator rx3parHexValidator = new RegexValidator(RX_3_PAR_HEX);
	private static RegexValidator rx3parValidator = new RegexValidator(RX_3_PAR);
	//
	private static Map<String, String> opMap;

	static {
		try {
			opMap = OpReader.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String loadOpFromMap(String op) throws IOException {
		final Iterator<String> keyIterator = opMap.keySet().iterator();
		Set<String> keyset = opMap.keySet();
		Set<String> keyset2 = keyset.stream().filter(line -> line != null && line.startsWith("" + op.substring(0, 2))).collect(Collectors.toSet());
		//Checkeamos el primer valor del stream de bytes
		if (keyset2.size() == 1) {
			return (String) keyset2.toArray()[0];
		} else {
			Set<String> keyset3 = keyset2.stream().filter(line -> line != null && line.startsWith("" + op.substring(0, 4))).collect(Collectors.toSet());
			if (keyset3.size() == 1) {
				return (String) keyset2.toArray()[0];
			} else {
				//DD CB XX 06
				Set<String> keyset4 = keyset3.stream().filter(line -> line != null && line.startsWith("" + op.substring(0, 6))).collect(Collectors.toSet());
				if (keyset3.size() == 1) {
					return (String) keyset2.toArray()[0];
				}
				//TODO
			}
		}
		return "f00";
	}

	static OpCodesUtils decoder = new OpCodesUtils();

	public static void runOpCode(Machine machine, byte[] ops)
			throws InvocationTargetException, IllegalAccessException, IOException {
		//min 4 bit op max 4+4+4
		byte op0 = ops[0];
		byte op1 = ops[1];
		byte op2 = ops[2];
		// TODO
		// TODO
		// Maskarak konprobatu.
		// TODO
		// TODO
		String str = Z80.bytesToHex(new byte[] { op0 });
		String decodedOp = loadOpFromMap(str);
		logger.debug("decodedOp prop:" + str + " op:" + decodedOp);
		String opFull = opMap.get(decodedOp);
		int numPars = StringUtils.countMatches(opFull, "XX");
		Class decoderClass = OpCodesUtils.class;
		Method method = null;
		switch (numPars) {
		case 0:
			method = MethodUtils.getMatchingMethod(decoderClass, "f" + decodedOp, Machine.class);
			method.invoke(decoder, machine);
			break;
		case 1:
			int par1 = op1 & Masks.MASK_PAR1;
			String strPar1 = null;
			method = MethodUtils.getMatchingAccessibleMethod(decoderClass, "f" + decodedOp, Machine.class, Integer.class);
			method.invoke(decoder, machine, par1);
			break;
		case 2:
			break;
		}
	}
}
