package v00s02;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class main {
	public static void main(String[] args) {
		String[][] Types = {						// ending, category, relevance, quantity
				{"jpg", "Image", "90", "0"},
				{"png", "Image", "100", "0"},
				{"bmp", "Image", "20", "0"},
				{"doc", "Document", "80", "0"},
				{"docx", "Document", "80", "0"},
				{"ppt", "Document", "70", "0"},
				{"pptx", "Document", "70", "0"},
				{"txt", "Document", "90", "0"},
				{"mp4", "Video", "60", "0"},
				{"mkv", "Video", "60", "0"},
				{"avi", "Video", "50", "0"},
				{"exe", "Executable", "30", "0"},
				{"zip", "Archive", "60", "0"},
				{"rar", "Archive", "60", "0"},
				{"7zip", "Archive", "60", "0"},
				{"dll", "Binary", "10", "0"}
				};
		
		/*String[] Endings = {"jpg", "png", "bmp", "doc", "docx", "ppt", "pptx", "txt", "mp4", "mkv", "avi", "exe", "zip", "rar", "7zip", "dll"};
		String[] Category = {"Image", "Image", "Image", "Document", "Document", "Document", "Document", "Document", "Video", "Video", "Video", "Executable", "Archive", "Archive", "Archive", "Binary"};
		int[] Priority = {90, 100, 20, 80, 80, 70, 70, 90, 60, 60, 50, 30, 60, 60, 60, 10};
		int[] Counter = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};*/
		
		String path = "C:\\xampp";
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		List<File> allFiles = new ArrayList<File>();

		    for (int i = 0; i < listOfFiles.length; i++) {
		    	if (listOfFiles[i].isDirectory()) {									// Enter subfolder
		    		System.out.println(path+"\\"+listOfFiles[i].getName());
		    		File[] temp = new File(path+"\\"+listOfFiles[i].getName()).listFiles();
				    for (int j = 0; j < temp.length; j++) {
				    	allFiles.add(temp[j]);										// Add file in folder to list
				    }
		    	} else {
			    	allFiles.add(listOfFiles[i]);									// Add file to list
		    	}
		    }
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
		    
		    
		    
		    int[] Importances = new int[allFiles.size()];
		    for (int i = 0; i < allFiles.size(); i++) {
		    	//System.out.println(i);
		    	String[] temp = allFiles.get(i).getName().split("\\.");
			    String Ending = temp[temp.length-1];
				for(int _x = 0;_x<Types.length;_x++) {
					if(Ending.equals(Types[_x][0])) {
			       		int Importance = (Integer.parseInt(Types[_x][2])*Integer.parseInt(Types[_x][2]))/Integer.parseInt(Types[_x][3]);
			       		Importances[i] = Importance;
			       		System.out.println("File " + allFiles.get(i).getName() + " is of type " + Types[_x][1] + "(priority "+Types[_x][2]+"%) and got the individual Importance of " + Importance);
			       	}
		    	}
		    }
		    
	}
	private static void loadFileTypes(){
		
		
	}
}
