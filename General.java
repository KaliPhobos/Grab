package v00s12;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
	/*public static Object[] loadFiles(String path) {
		int totalCount = 1;
		int backupCount = 0;
		int backupCount2 = 0;
		DefaultMutableTreeNode[] treeNodes = new DefaultMutableTreeNode[1000];
		treeNodes[0] = new DefaultMutableTreeNode("root");*/
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		List<File> allFiles = new ArrayList<File>();	// BUG: Ignores any files in top directory
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isDirectory()) {									// Enter subfolder
		   		try {
		   			//File[] temp = new File(path+"\\"+listOfFiles[i].getName()).listFiles();
		   			File[] temp = new File(path+"/"+listOfFiles[i].getName()).listFiles();
					System.out.println("folder: "+listOfFiles[i].getPath());
					/*treeNodes[totalCount] = new DefaultMutableTreeNode(listOfFiles[i].getName()+" --");
					backupCount2 = backupCount;
					backupCount = totalCount;*/
					for (int j = 0; j < temp.length; j++) {
						System.out.println("file: "+temp[i].getPath());
						if (!temp[j].isDirectory()) {							// No folders will show up in Results
							allFiles.add(temp[j]);								// Add file in folder to list
						}
						/*treeNodes[totalCount] = new DefaultMutableTreeNode(listOfFiles[i].getName());
						treeNodes[backupCount].add(treeNodes[totalCount]);*/
		   			}
					//treeNodes[backupCount2].add(treeNodes[backupCount]);												// guess I fucked something up here

		   		} catch (Exception e) {
		   			System.out.println("ERROR reading Folder " + path+"/"+listOfFiles[i].getName());
		   		}
		   		//totalCount++;
		   	} else {
		    	allFiles.add(listOfFiles[i]);									// Add file to list
				System.out.println("file: "+listOfFiles[i].getPath());
				/*treeNodes[totalCount] = new DefaultMutableTreeNode(listOfFiles[i].getName());
				treeNodes[0].add(treeNodes[totalCount]);
				totalCount++;*/
		   	}
		}
		return allFiles;
		//return new Object[] {allFiles, treeNodes[0]};
	}
	public static TypeList loadTypes(List<File> allFiles) {
		TypeList Types = TypeList.createTypeList(new String[][] {						// ending, category, relevance, quantity
			{"jpg", "Image", "90"},
			{"jpeg", "Image", "90"},
			{"png", "Image", "100"},
			{"gif", "Image", "30"},			// Name: priority on files named IMG_???? or IMG_?????
			{"bmp", "Image", "20"},			// Name: priority on files
			{"ico", "Image", "20"},			// Meta: priority on 10 most recent changed files
			{"doc", "Document", "80"},		// Meta: priority on any files changed after their including folder was created
			{"docx", "Document", "80"},		// Size: sort by difference to 3MB (sweetspot 0.1-5MB files)
			{"ppt", "Document", "70"},		// Hash: save hashes of any files: EXE, PDF, MKV, MP4, AVI, MOV
			{"pages", "Document", "80"},
			{"pptx", "Document", "70"},
			{"txt", "Document", "90"},
			{"odt", "Document", "90"},
			{"conf", "Document", "30"},
			{"readme", "Document", "40"},
			{"pdf", "Document", "50"},
			{"xlsx", "Document", "60"},
			{"one", "Document", "90"},
			{"obj", "Document", "50"},
			{"log", "Logfile", "100"},
			{"bat", "Script", "60"},
			{"php", "Script", "60"},
			{"js", "Script", "60"},
			{"html", "Script", "50"},
			{"css", "Script", "50"},
			{"sql", "Table", "90"},
			{"mp4", "Video", "60"},
			{"mkv", "Video", "60"},
			{"avi", "Video", "50"},
			{"flv", "Video", "50"},
			{"wav", "Audio", "70"},
			{"mp3", "Audio", "70"},
			{"exe", "Executable", "30"},
			{"dmg", "Executable", "30"},
			{"msi", "Executable", "30"},
			{"zip", "Archive", "60"},
			{"rar", "Archive", "60"},
			{"7zip", "Archive", "60"},
			{"tar.gz", "Archive", "60"},
			{"iso", "Archive", "70"},
			{"dll", "Binary", "10"},
			{"ini", "Binary", "10"},
			{"tmp", "Binary", "10"}
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
		}
		return result;
	}
	public static double getAdditionalRelevance(double size) {
		double additionalRelevance = 0.0;
		if(size==0.0) {										// empty file
			additionalRelevance = 10.0;
		}
		if (size>0 && size<500000.0) {						// less than 0.5MB
			additionalRelevance = (size/500000.0)*50.0+50.0;
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
