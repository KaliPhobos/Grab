package v00s18;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

public class General {
	
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
	public static void createFileList(FileWriter writer, String _path) {
		
		/*Object[] result = loadFiles(_path);
		List<File> allFiles = (List<File>) result[0];*/
		List<File> allFiles = loadFiles(_path);				// "C:\\Users\\p2\\Downloads\\test"
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
	
	/*public static int FileCountLines(String filename) throws IOException {
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
	}*/
	/*public static String[] FileToStringArray(String path) {
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
	}*/
	public static String[][] getFileNames(String path) {
		/*Object[] result = loadFiles(path);
		List<File> allFiles = (List<File>) result[0];*/
		List<File> allFiles = loadFiles(path);
		String[][] FullNames = new String[allFiles.size()][2];
		for (int i = 0; i < allFiles.size(); i++) {
			FullNames[i][0] = allFiles.get(i).getPath();
			FullNames[i][1] = allFiles.get(i).getName();
		}
		return FullNames;
	}
	public static List<File> loadFiles(String path) {
		int c_folders = 0;
		List<File> allFiles = new ArrayList<File>();			// Creates Lists of all Files and Folders found
		List<String> folders = new ArrayList<String>();			// Then recursively applies function to any found subfolders again
		folders.add(path);										// Returns a list of any files
		System.out.println("Adding new folder to To-Do-List: "+path);
		if(path.endsWith("\\")) {
			folders.add(path.substring(0, path.length()-1));
			System.out.println("Adding new folder to To-Do-List: "+path.substring(0, path.length()-1));
		} else {
			folders.add(path+"\\");
			System.out.println("Adding new folder to To-Do-List: "+path+"\\");
		}
		while (folders.isEmpty()==false) {
			c_folders++;
			System.out.println("Taking care of item "+c_folders+"/"+folders.size()+" - "+path);
			path = folders.get(0);				// Enforce id 0 each run - others will move up after it's deleted
			System.out.println("Adding new folder to To-Do-List: "+path);
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();
			if(listOfFiles==null) {
				System.out.println("NULL files");
			} else {
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isDirectory() && path.endsWith("\\")) {									// Enter subfolder
				   		try {
				   			File[] temp = new File(path+listOfFiles[i].getName()).listFiles();
							System.out.println(" subfolder: "+listOfFiles[i].getPath());
							for (int j = 0; j < temp.length; j++) {
								if (!temp[j].isDirectory()) {							// No folders will show up in Results
									allFiles.add(temp[j]);								// Add file in folder to list
									System.out.println(" new file: "+temp[j].getPath());
								} else {
									System.out.println("Adding new folder to To-Do-List: "+temp[j].getPath());
						   			folders.add(temp[j].getPath());
								}
					   			main.MainWin.setStatusText(allFiles.size()+" files ("+folders.size()+" left)");
				   			}
				   		} catch (Exception e) {
				   			System.out.println(" ERROR reading from " + path+listOfFiles[i].getName());
				   			folders.add(path+"\\"+listOfFiles[i].getName());
				   		}
				   	} else {
				    	allFiles.add(listOfFiles[i]);									// Add file to list
						System.out.println(" new file: "+listOfFiles[i].getPath());
				   	}
				}
			}	// not null files
			folders.remove(0);		// Remove first item in the line
		}
		if (allFiles.size()==0) {
			System.out.println("ERROR - No files were found, please check input parameters again");
		}
		return allFiles;
	}
	
	public static TypeList loadTypes(List<File> allFiles) {
		TypeList Types = TypeList.createTypeList(new String[][] {						// ending, category, relevance, quantity
			{"vcf", "Contacts", "100"},
			{"jpg", "Image", "90"},
			{"jpeg", "Image", "90"},
			{"png", "Image", "100"},
			{"gif", "Image", "30"},			// Name: priority on files named IMG_???? or IMG_?????
			{"bmp", "Image", "20"},			// Name: priority on files
			{"ico", "Image", "20"},			// Meta: priority on 10 most recent changed files
			{"doc", "Document", "80"},		// Meta: priority on any files changed after their including folder was created
			{"docx", "Document", "80"},
			{"ppt", "Document", "70"},		// Hash: save hashes of any files: EXE, PDF, MKV, MP4, AVI, MOV
			{"pages", "Document", "80"},
			{"pptx", "Document", "70"},
			{"txt", "Document", "90"},
			{"odt", "Document", "90"},
			{"conf", "Document", "38"},
			{"readme", "Document", "40"},
			{"pdf", "Document", "50"},
			{"xlsx", "Document", "60"},
			{"one", "Document", "99"},			// HAVE TO CHANGE FROM STRING ARRAY TO ARRAYLIST
			{"obj", "Document", "50"},
			{"xml", "Document", "60"},
			{"sql", "Document", "90"},
			{"log", "Logfile", "100"},
			{"bat", "Script", "60"},
			{"php", "Script", "60"},
			{"js", "Script", "60"},
			{"html", "Script", "50"},
			{"htm", "Script", "50"},
			{"css", "Script", "50"},
			{"mp4", "Video", "63"},
			{"mkv", "Video", "58"},
			{"m4v", "Video", "58"},
			{"avi", "Video", "50"},
			{"flv", "Video", "50"},
			{"wav", "Audio", "70"},
			{"mp3", "Audio", "75"},
			{"exe", "Executable", "30"},
			{"dmg", "Executable", "30"},
			{"msi", "Executable", "30"},
			{"zip", "Archive", "60"},
			{"rar", "Archive", "60"},
			{"tar", "Archive", "60"},
			{"7zip", "Archive", "60"},
			{"tar.gz", "Archive", "60"},
			{"iso", "Archive", "70"},
			{"dll", "Binary", "8"},
			{"ini", "Binary", "12"},
			{"tmp", "Binary", "10"},
			{"ttf", "Font", "20"},
			{"fon", "Font", "20"}
		});
		for (int i = 0; i < allFiles.size(); i++) {
	        String[] temp = allFiles.get(i).getName().split("\\.");
	        String Ending = temp[temp.length-1];
	        for(int _x=0;_x<Types.getLength();_x++) {
	        	if(Ending.equals(Types.getType(_x).getEnding())) {
	        		Types.getType(_x).increaseQuantity(1);
	        		Types.getType(_x).increaseSize(allFiles.get(i).length()); 	// SHOULD ADD SIZE NOT LENGTH OF PATH
	        		//Types.getType(_x).setQuantity((Integer.parseInt(Types[_x][3])+1));		// Count 1 up
	        	}
	        }
	    }
		return Types;
	}
	public static double getBasicRelevance(String ending) {
		double result = 0;
		boolean is_match = false;
		for(int _x = 0;_x<Window.MyTypeList.getLength();_x++) {											// Compare file endings
			if(ending.toLowerCase().equals(Window.MyTypeList.getType(_x).getEnding())) {				// match
				is_match = true;
				double _quantity = Window.MyTypeList.getType(_x).getQuantity();
				double _typeRelevance = Window.MyTypeList.getType(_x).getTypeRelevance();
				result = ((_typeRelevance*_typeRelevance)/Math.sqrt(Math.max(1.0, _quantity)))/100.0;	// gedeckelt auf 100% - evtl unsaubere ergebnisse?			######################################
				System.out.println(_quantity + " - " + _typeRelevance + " = " + result);
			}
		}
		if (!is_match) {																	// no match
			result = 5;											// Basic relevance assigned to any unknown file type
			boolean is_known = false;
			for(int x=0;x<TypeList.Types.size();x++) {
				if(TypeList.Types.get(x).getEnding().equals(ending)) {
					TypeList.Types.get(x).increaseQuantity(1);
					is_known = true;
					break;
				}
			}
			if (!is_known) {
				TypeList.Types.add(TypeItem.createType(ending, "Unknown", 5, 0, 0));
				// Only adds item entrance to TYPES element used for .analize()
				// Will NOT count up related stats such as total combined file size per type
			}
		}
		return result;
	}
	public static double getAdditionalRelevance(double size) {
		double additionalRelevance = 0.0;
		if(size==0.0) {										// empty file
			additionalRelevance = 20.0;
		}
		if (size>0 && size<500000.0) {						// less than 0.5MB
			additionalRelevance = (size/500000.0)*40.0+60.0;
		}
		if (size>500000.0 && size<12000000.0) {	// Sweet spot 0.5-12MB
			additionalRelevance = 99.9;
		}
		if (size>12000000.0 && size<100000000.0) {	//large files (12-100MB)
			additionalRelevance = 100.0-(((size-12000000.0)/88000000.0)*70.0);
		}
		if (size>100000000.0 && size<1000000000) {			// fat ones, (100MB-1GB)
			additionalRelevance = 30.0-(((size-100000000.0)/900000000.0)*20.0);
		}
		if (size>1000000000.0) {						// too fat ones, beyond 1GB
			additionalRelevance = 10.0;
		}
		//System.out.println(size+"	 -->	"+additionalRelevance);
		return additionalRelevance;
	}
	public static int getNameRelevance(String filename, String ending, String category) {	// never called
		int result = 50;			// average
		if (filename.startsWith("._")) {
			result = result -40;
		}
		if (category.equals("Image") && filename.startsWith("IMG_")) {
			result = result +40;
		}
		if ((filename+ending).equals("readme.txt")) {
			result = result -20;
		}
		if (filename.contains("pass")) {
			result = result +40;
		}
		
		return result;
	}
	public static String FormatSize(double size) {
		String result;
		if (size<1000) {
			result = (int) size+" Byte";
		} else if(size<1000000) {
			result = (((double) Math.round(size/10))/100)+"KB";
		} else if(size<1000000000) {
			result = (((double) Math.round(size/10000))/100)+"MB";
		} else if(size<1000000000000.0) {
			result = (((double) Math.round(size/10000000))/100)+"GB";
		} else if(size<1000000000000000.0) {
			result = (((double) Math.round(size/10000000000.0))/100)+"TB";
		} else if(size<1000000000000000000.0) {
			result = (((double) Math.round(size/10000000000000.0))/100)+"PB";
		} else {
			result = "fuck you";
		}
		return result;
	}
	public static String[][] OrderStringArrayBy(String[][] input, int parameter) {
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
			String[] temponary = input[num];
			input[num] = input[x];
			input[x] = temponary;
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
	public static double round(double value, int length) {
		// return a double but only with a given number of decimals
		double result = value;
		if ((result+"").equals("Infinity")) {			// INFINITY
			System.out.println("infinity");
		} else {
			for(int x=0;x<length;x++) {				// shift comma <--
				result=result*10.0;
			}
			String b = result+"";						// double --> string
			b = b.substring(0, b.indexOf("."));		// remove decimals
			if(result>=Integer.MAX_VALUE) {			// OVERFlOW
				System.out.println("overflow");
			}
			int a = Integer.parseInt(b);			//String --> int			no string-->double method available?
			result = (double) a;						// int --> double
			for(int x=0;x<length;x++) {
				result=result/10.0;						// shift comma -->
			}
			//System.out.println(value + "	-->	"+ b +"	-->	" + result);
		}
		return result;
	}
}
