package v00s18;


import java.awt.Color;

public class main {
	public static Window MainWin;
	public static void main(String[] args) throws Exception {
		String path = "C:\\";	//"/Users/p2";
		MainWin = new Window(path);
		(new Thread(MainWin)).start();
		System.out.println("main done ...");
	}
	

	
	
	/*
	 * running ...
	 */
	
	
	
	
	
}
