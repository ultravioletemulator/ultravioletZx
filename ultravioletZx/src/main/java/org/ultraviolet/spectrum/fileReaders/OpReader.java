package org.ultraviolet.spectrum.fileReaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ultraviolet.spectrum.z80.OpCodesUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by developer on 22/09/2017.
 */
public class OpReader {

	static final Logger logger = LoggerFactory.getLogger(OpReader.class);
	private static final String OP_MAP_FILE = "/development/ideaProjects/Spectrum/ultravioletZx/src/test/resources/core/op.map";

	public static Map<String, String> read() throws IOException {
		Map<String, String> props = new HashMap<>();
		String line;
		try (
				InputStream fis = new FileInputStream(OP_MAP_FILE);
				InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
				BufferedReader br = new BufferedReader(isr);
		) {
			while ((line = br.readLine()) != null) {
				processLine(line, props);
			}
		}
		return props;
	}

	public static void processLine(String line, Map<String, String> props) {
		logger.debug("line: " + line);
		String[] op = line.split(";");
		String codePars = op[1];
		String codeParsClean =codePars.replaceAll(" ", "");
		String code = codeParsClean.replaceAll("X", "");
		props.put(code, codeParsClean);
	}
}
