package v00s01;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class main {
	public static void main(String[] args) {
		String[] Endings = {"jpg", "png", "bmp", "doc", "docx", "ppt", "pptx", "txt", "mp4", "mkv", "avi", "exe", "zip", "rar", "7zip", "dll"};
		String[] Category = {"Image", "Image", "Image", "Document", "Document", "Document", "Document", "Document", "Video", "Video", "Video", "Executable", "Archive", "Archive", "Archive", "Binary"};
		int[] Priority = {90, 100, 20, 80, 80, 70, 70, 90, 60, 60, 50, 30, 60, 60, 60, 10};
		int[] Counter = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
		String path = "C:\\xampp";
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		List<File> allFiles = new ArrayList<File>();

		    for (int i = 0; i < listOfFiles.length; i++) {
		    	if (listOfFiles[i].isDirectory()) {
		    		System.out.println(path+"\\"+listOfFiles[i].getName());
		    		File[] temp = new File(path+"\\"+listOfFiles[i].getName()).listFiles();
				    for (int j = 0; j < temp.length; j++) {
				    	//listOfFiles[listOfFiles.length] = temp[j];
				    	allFiles.add(temp[j]);
				    }
		    	} else {
			    	allFiles.add(listOfFiles[i]);
		    	}
		    }
		    for (int i = 0; i < allFiles.size()-1; i++) {
		        System.out.println("File " + allFiles.get(i).getName());
		        String[] temp = allFiles.get(i).getName().split("\\.");
		        String Ending = temp[temp.length-1];
		        for(int _x = 0;_x<Endings.length;_x++) {
		        	if(Ending.equals(Endings[_x])) {
		        		Counter[_x]++;
		        	}
		        }
		    }
		    
		    
		    
		    int[] Importances = new int[allFiles.size()];
		    for (int i = 0; i < allFiles.size(); i++) {
		    	String[] temp = allFiles.get(i).getName().split("\\.");
			    String Ending = temp[temp.length-1];
				for(int _x = 0;_x<Endings.length;_x++) {
					if(Ending.equals(Endings[_x])) {
			       		int Importance = (Priority[_x]*Priority[_x])/Counter[_x];
			       		Importances[i] = Importance;
			       		System.out.println("File " + allFiles.get(i).getName() + " is of type " + Category[_x] + "(priority "+Priority[_x]+"%) and got the individual Importance of " + Importance);
			       	}
		    	}
		    }
		    
	}
	private static void loadFileTypes(){
		
		
	}
}
