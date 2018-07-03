package log.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class TextUtils {
    public static String readFile(String path, String filter) {
        StringBuffer stringBuffer = null;
		try {
			File file = new File(path);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
			    if (filter != null && !filter.trim().equals("")) {
			        if (line.contains(filter)) {
                        stringBuffer.append(line);
                        stringBuffer.append("\n");
                    }
                } else {
			        stringBuffer.append(line);
			        stringBuffer.append("\n");
                }

			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}

	public static String getFileNameFromPath(String path) {
        if (path.contains(File.separator)) {
            return path.substring(path.lastIndexOf(File.separator) + 1);
        }
        return path;
    }
}
