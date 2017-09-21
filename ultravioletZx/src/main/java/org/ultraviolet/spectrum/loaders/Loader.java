package org.ultraviolet.spectrum.loaders;

/**
 * Created by developer on 21/09/2017.
 */
public abstract class Loader {

	private byte[] content;

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public void load(byte[] content) {
		this.setContent(content);
	}

	public abstract void parseContent();
}
