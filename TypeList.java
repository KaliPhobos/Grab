package v00s17;

import java.util.ArrayList;
import java.util.List;

public class TypeList {
	static List<TypeItem> Types;
	static String analizedData;
	static int c_Files;
	int length;
	public TypeList(String[][] TypesFound) {
		this.length = TypesFound.length;
		this.Types = new ArrayList<TypeItem>();
		for(int x=0;x<TypesFound.length;x++) {
			this.Types.add(x, TypeItem.createType(TypesFound[x][0], TypesFound[x][1], Integer.parseInt(TypesFound[x][2]), 0, 0));
		}
	}
	public static TypeList createTypeList(String[][] TypesFound) {		// Using StringArray as it's dimensions won't be changed later on
		return new TypeList(TypesFound);
	}
	public TypeItem getType(int id) {
		return Types.get(id);
	}
	public int getLength() {
		return length;
	}
	public String getCategory(String ending) {
		String result = "";
		for(int x=0;x<Types.size();x++) {	
			if (ending.equals(getType(x).getEnding())) {
				result = getType(x).getCategory();
			}
		}
		return result;
	}
	public static void setType(int id, TypeItem item) {
		Types.set(id,  item);
	}
	public static void sort() {
		int quantity;
		c_Files = 0;
		for(int i=0;i<Types.size();i++) {
			c_Files = c_Files + Types.get(i).getQuantity();
		}
		for(int pos=0;pos<Types.size();pos++) {					// pos = current position the sort algorithm is applied to
			int id = pos;										// id = id of highest quantity item this round
			int max = Types.get(pos).getQuantity();				// max = max quantity in this round
			for(int item=pos;item<Types.size();item++) {		// item = currently processed item id
				quantity = Types.get(item).getQuantity();		// quantity = quantity of current item
				if (quantity > max) {
					max = quantity;
					id = item;									// saves id of new max
				}
			}
			TypeItem backup = Types.get(id);		//id		->	backup
			setType(id, Types.get(pos));			//pos		->	id
			setType(pos, backup);					//backup	->	pos
		}
	}
	public static String analize() {
		analizedData = "Out of "+c_Files+" total files:";
		for(int x=0;x<Types.size();x++) {
			TypeItem temp = Types.get(x);
			String substring = null;
			int quantity = temp.getQuantity();
			if (quantity>0) {
				if (quantity==1) {
					substring = "";
				} else {
					substring = "s";
				}
				analizedData+="\n"+temp.getQuantity()+" "+temp.getEnding().toUpperCase()+"-file"+substring+" ("+((100*temp.getQuantity())/c_Files)+"%) with a total of "+General.FormatSize((double) temp.getSize());
			}
		}
		System.out.println(analizedData);
		return analizedData;
	}
}
