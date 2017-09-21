package org.ultraviolet.spectrum.loaders;

/**
 * Created by developer on 21/09/2017.
 */
public enum FileFormats {
	TZX(FileFormats.TZX_EXTENSION), TAP(FileFormats.TAP_EXTENSION), Z80(FileFormats.Z80_EXTENSION), DSK(FileFormats.DSK_EXTENSION);
	public final static String TZX_EXTENSION = ".tzx";
	public final static String TAP_EXTENSION = ".tap";
	public final static String Z80_EXTENSION = ".z80";
	public final static String DSK_EXTENSION = ".dsk";
	private String extension;

	public String getExtension() {
		return extension;
	}

	FileFormats(String extension) {
		this.extension = extension;
	}
}

