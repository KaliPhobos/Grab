package v00s03;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class main {
	public static void main(String[] args) {
		List<File> allFiles = loadFiles("C:\\xampp");	// "C:\\Users\\p2\\Downloads\\test"
		String[][] Types = loadTypes(allFiles);
		String[][] Files = new String[allFiles.size()][5];		// FileName, FullPath, FileSize, Relevance, AdditionalRelevance
		for (int i = 0; i < allFiles.size(); i++) {
			String[] temp = allFiles.get(i).getName().split("\\.");
			String Ending = temp[temp.length-1];
			boolean is_match = false;
			for(int _x = 0;_x<Types.length;_x++) {						// Compare file endings
				if(Ending.toLowerCase().equals(Types[_x][0])) {			// match
					is_match = true;
					int imp = Integer.parseInt(Types[_x][2]);
					int Importance = sqr(imp)/(Integer.parseInt(Types[_x][3])+1);
					System.out.println("File " + allFiles.get(i).getName() + " is of type " + Types[_x][1] + "(priority "+Types[_x][2]+"%) and got the individual Importance of " + Importance);
					Files[i] = new String[] {allFiles.get(i).getName(), allFiles.get(i).getPath(), allFiles.get(i).length()+"", Importance+"", "0"};
					//System.out.println(Files[i][0]+"\n"+Files[i][1]+"\n"+Files[i][2]+"\n"+Files[i][3]+"\n"+Files[i][4]);
				}
			}
			if (!is_match) {											// no match
				System.out.println(Ending);
			}
		}
	}
	
	
	public static int sqr(int a) {
		return a*a;
	}
	public static String[][] loadTypes(List<File> allFiles) {
		String[][] Types = {						// ending, category, relevance, quantity
				{"jpg", "Image", "90", "0"},
				{"png", "Image", "100", "0"},
				{"gif", "Image", "30", "0"},
				{"bmp", "Image", "20", "0"},
				{"ico", "Image", "20", "0"},
				{"doc", "Document", "80", "0"},
				{"docx", "Document", "80", "0"},
				{"ppt", "Document", "70", "0"},
				{"pptx", "Document", "70", "0"},
				{"txt", "Document", "90", "0"},
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
				{"tmp", "Binary", "10", "0"},
				{"conf", "Config", "30", "0"}
				};
		
		/*
		 * Name: priority on files named IMG_???? or IMG_?????
		 * Name: priority on files
		 * Meta: priority on 10 most recent changed files
		 * Meta: priority on any files changed after their including folder was created
		 * Size: sort by difference to 3MB (sweetspot 0.1-5MB files)
		 * Hash: save hashes of any files: EXE, PDF, MKV, MP4, AVI, MOV
		 */
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
}
