package pizza.main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {
	final static Charset ENCODING = StandardCharsets.UTF_8;

	// For smaller files

	/**
	 * Note: the javadoc of Files.readAllLines says it's intended for small
	 * files. But its implementation uses buffering, so it's likely good even
	 * for fairly large files.
	 */
	static List<String> read(String aFileName) throws IOException {
		Path path = Paths.get(aFileName);
		return Files.readAllLines(path, ENCODING);
	}
}
