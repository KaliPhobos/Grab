package v00s16;

public class FileManager implements Runnable {
	public static int[] ThreadList = new int[4];
	public static String[][] ItemList = null;
	public void run() {
		// nothing to do here
	}
	public FileManager() {
		// create Array of fileItems and status codes
		// start CopyThread threads
		System.out.println("FileManager ready");
	}
	
	public void prepare(FileItem[] Files) {
		ItemList = prepareItemList(Files);
		ThreadList[0] = 0;
		ThreadList[1] = 0;
		ThreadList[2] = 0;
		ThreadList[3] = 0;
		
	}
	
	public String[][] prepareItemList(FileItem[] Files) {		// Just copies File Information unsorted!
		String[][] result = new String[Files.length][3];		// Sort algorithms only implemented in JList
		FileItem temp = null;
		for(int x=0;x<Files.length;x++) {
			temp = Files[x];
			result[x][0] = temp.getPath() + temp.getName() + temp.getEnding();
			result[x][1] = changePath(temp.getPath()) + temp.getName() + temp.getEnding();
			result[x][2] = "0";
		}
		return result;
	}
	public String changePath(String Path) {
		String result = "";
		// replace drive letter
		return result;
	}
}
