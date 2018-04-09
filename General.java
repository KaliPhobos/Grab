package v00s08;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class General {
	public static String[][] Types = null;
	public static FileWriter createWriter(String path) {
		FileWriter writer = null;
		try {
			File file = new File(path);
			System.out.println(file.getAbsolutePath());
			if (file.exists()==false) {
				file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writer;
	}
	public static FileReader createReader(String path) {
		FileReader reader = null;
		try {
			File file = new File(path);
			System.out.println(file.getAbsolutePath());
			if (file.exists()==false) {
				file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reader;
	}
	/*public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}*/
	public static void createFileList(FileWriter writer) {
		List<File> allFiles = loadFiles("C:\\xampp");	// "C:\\Users\\p2\\Downloads\\test"
		for (int i = 0; i < allFiles.size(); i++) {
			String path = allFiles.get(i).getAbsolutePath();
			try {
				writer.write(path);
			} catch (Exception e) {
				//e.printStackTrace();
				System.out.println("error writing");
			}
		}
	}
	
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
	public static String[] getFileNames(String path) {
		List<File> allFiles = loadFiles(path);
		String[] FullNames = new String[allFiles.size()];
		for (int i = 0; i < allFiles.size(); i++) {
			FullNames[i] = allFiles.get(i).getPath();
		}
		return FullNames;
	}
	public static List<File> loadFiles(String path) {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		List<File> allFiles = new ArrayList<File>();	// BUG: Ignores any files in top directory
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isDirectory()) {									// Enter subfolder
		   		try {
		   			File[] temp = new File(path+"\\"+listOfFiles[i].getName()).listFiles();
		   			for (int j = 0; j < temp.length; j++) {
		   				allFiles.add(temp[j]);										// Add file in folder to list
		   			}
					System.out.println("Folder " + path+"\\"+listOfFiles[i].getName());
		   		} catch (Exception e) {
		   			System.out.println("ERROR reading Folder " + path+"\\"+listOfFiles[i].getName());
		   			//e.printStackTrace();
		   		}
		   	} else {
		    	allFiles.add(listOfFiles[i]);									// Add file to list
		   	}
		}
		return allFiles;
	}
	public static String[][] loadTypes(List<File> allFiles) {
		String[][] Types = {						// ending, category, relevance, quantity
				{"jpg", "Image", "90", "0"},
				{"png", "Image", "100", "0"},
				{"gif", "Image", "30", "0"},		// Name: priority on files named IMG_???? or IMG_?????
				{"bmp", "Image", "20", "0"},		// Name: priority on files
				{"ico", "Image", "20", "0"},		// Meta: priority on 10 most recent changed files
				{"doc", "Document", "80", "0"},		// Meta: priority on any files changed after their including folder was created
				{"docx", "Document", "80", "0"},	// Size: sort by difference to 3MB (sweetspot 0.1-5MB files)
				{"ppt", "Document", "70", "0"},		// Hash: save hashes of any files: EXE, PDF, MKV, MP4, AVI, MOV
				{"pptx", "Document", "70", "0"},
				{"txt", "Document", "90", "0"},
				{"conf", "Document", "30", "0"},
				{"readme", "Document", "40", "0"},
				{"log", "Logfile", "100", "0"},
				{"bat", "Script", "60", "0"},
				{"php", "Script", "60", "0"},
				{"js", "Script", "60", "0"},
				{"html", "Script", "50", "0"},
				{"css", "Script", "50", "0"},
				{"sql", "Table", "90", "0"},
				{"mp4", "Video", "60", "0"},
				{"mkv", "Video", "60", "0"},
				{"avi", "Video", "50", "0"},
				{"exe", "Executable", "30", "0"},
				{"zip", "Archive", "60", "0"},
				{"rar", "Archive", "60", "0"},
				{"7zip", "Archive", "60", "0"},
				{"dll", "Binary", "10", "0"},
				{"ini", "Binary", "10", "0"},
				{"tmp", "Binary", "10", "0"}
				};
		for (int i = 0; i < allFiles.size()-1; i++) {
	        System.out.println("File " + allFiles.get(i).getName());
	        String[] temp = allFiles.get(i).getName().split("\\.");
	        String Ending = temp[temp.length-1];
	        for(int _x = 0;_x<Types.length;_x++) {
	        	if(Ending.equals(Types[_x][0])) {
	        		Types[_x][3] = (Integer.parseInt(Types[_x][3])+1)+"";		// Count 1 up
	        	}
	        }
	    }
		return Types;
	}
	public static String[][] GetAnalyzedData(String path) {
		List<File> allFiles = loadFiles(path);	// "C:\\Users\\p2\\Downloads\\test"
		Types = loadTypes(allFiles);
		String[][] Files = new String[allFiles.size()][7];		// FileName, FullPath, FileSize, Relevance, AdditionalRelevance, TotalRelevance, Type
		for (int i = 0; i < allFiles.size(); i++) {
			String[] temp = allFiles.get(i).getName().split("\\.");
			String Ending = temp[temp.length-1];
			boolean is_match = false;
			int Relevance = 0;
			int AdditionalRelevance = 0;
			for(int _x = 0;_x<Types.length;_x++) {						// Compare file endings
				if(Ending.toLowerCase().equals(Types[_x][0])) {			// match
					Files[i][6] = _x+"";
					is_match = true;
					int imp = Integer.parseInt(Types[_x][2]);
					Relevance = Math.round(((imp*imp)/(Integer.parseInt(Types[_x][3])+1))/10);
					//Files[i][0] = "File " + allFiles.get(i).getName() + " is of type " + Types[_x][1] + "(priority "+Types[_x][2]+"%) and got the individual Importance of " + Importance;
					//System.out.println(Files[i][0]+"\n"+Files[i][1]+"\n"+Files[i][2]+"\n"+Files[i][3]+"\n"+Files[i][4]);
				}
			}
			if (!is_match) {											// no match
				Relevance = 30;
				//System.out.println(Ending);
				Files[i][6] = "";
			}
			AdditionalRelevance = 0;
			int size = (int) allFiles.get(i).length();
			if (size<1000000) {						// less than 1MB
				AdditionalRelevance = Math.round((size/100000)*100);
			}
			if (size>1000000 && size<12000000) {	// Sweet spot 1-12MB
				AdditionalRelevance = 100;
			}
			if (size>12000000 && size<100000000) {	//large files (12-100MB)
				AdditionalRelevance = ((size-12000000)/88000000)*100;
			}
			if (size>100000000) {					// fat ones, beyond 100MB
				AdditionalRelevance = 0;
			}
			Files[i] = new String[] {allFiles.get(i).getName(), allFiles.get(i).getPath(), allFiles.get(i).length()+"", Relevance+"", AdditionalRelevance + "", Math.round(Relevance*0.7 + AdditionalRelevance*0.3)+"", Files[i][6]};
			if (Files[i][6]!="") {
				System.out.println("File " + allFiles.get(i).getName() + " is of type " + Types[Integer.parseInt(Files[i][6])][1] + "(priority "+Types[Integer.parseInt(Files[i][6])][2]+"%), has a size of " + Files[i][2] + " (Additional Relevance " + Integer.parseInt(Files[i][5]) + "%) and got the individual Importance of " + Relevance);
			} else {
				System.out.println("File " + allFiles.get(i).getName() + " is of unknown type (priority 30%%), has a size of " + Files[i][2] + " (Additional Relevance " + Integer.parseInt(Files[i][5]) + "%)  and got the individual Importance of " + Relevance);
			}
		}
		return Files;
	}

	public static String[][] OrderStringArrayBy(String[][] input, int parameter) {
		/*String[][] result = new String[input.length][input[0].length];
		for(int x=0;x<input.length;x++) {
			int max = 0;
			int num = 0;
			for(int y=0;y<input.length;y++) {
				if (input[y][parameter]!=null) {
					int res = Integer.parseInt(input[y][parameter]);
					if (res>max) {
						max = res;
						num = y;
						System.out.println("item " + num + "has relevance of " + max);
					}
				}
			}
			for(int y=0;y<input[0].length;y++) {
				result[x][y] = input[num][y];
			}
		}
		return result;*/
		for(int x=0;x<input.length;x++) {
			int max = 0;
			int num = 0;
			for(int y=x;y<input.length;y++) {
				if (input[y][parameter]!=null) {
					int res = Integer.parseInt(input[y][parameter]);
					if (res>max) {
						max = res;
						num = y;
					}
				}
			}
			//System.out.println("item " + num + "has relevance of " + max);
			String[] temponary = input[num];
			input[num] = input[x];
			input[x] = temponary;
			System.out.println("File " + input[x][1] + " is of relevance " + input[x][3]);
		}
		return input;
	}
	public static String[] GetStringArrayRow(String[][] input, int row) {
		String[] result = new String[input.length];
		for(int x=0;x<input.length;x++) {
			result[x] = input[x][row];
		}
		return result;
	}
}
