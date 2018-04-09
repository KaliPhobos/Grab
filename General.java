package v00s07;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class General {
	public static int FileCountLines(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        System.out.println(count);
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
	public static String[] FileToStringArray(String path) {
		String[] lines = null;
		try {
			lines = new String[General.FileCountLines(path)+1];
			BufferedReader reader_FileNames = new BufferedReader(new FileReader(path));
			String line = reader_FileNames.readLine();
			int x = 0;
			while (line != null) {
				line = reader_FileNames.readLine();
				lines[x] = line+"";
				x++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}
}
