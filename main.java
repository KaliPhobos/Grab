package v00s09;


import java.awt.Color;


public class main {


	public static void main(String[] args) throws Exception {
		System.out.println("hallo");
		(new Thread(new Window())).start();
	}
	

	
	public static Slice[] getSlices(String[][] Types, int parameter) {
		Slice[] slices = new Slice[Types.length];
		for (int x=0;x<Types.length;x++) {
			slices[x] = new Slice(Integer.parseInt(Types[x][parameter]), Color.black);
			System.out.println(x + " - " + Types[x][parameter]);
		}
		return slices;
	}
	
	
	
	
	
}
