package v00s11;

public class TypeList {
	
	static TypeItem[] Types;
	static int c_Files;
	int length;
	public TypeList(String[][] TypesFound) {
		this.length = TypesFound.length;
		this.Types = new TypeItem[TypesFound.length];
		for(int x=0;x<TypesFound.length;x++) {
			this.Types[x] = TypeItem.createType(TypesFound[x][0], TypesFound[x][1], Integer.parseInt(TypesFound[x][2]), 0);
		}
	}
	public static TypeList createTypeList(String[][] TypesFound) {
		return new TypeList(TypesFound);
	}
	public TypeItem getType(int id) {
		return Types[id];
	}
	public int getLength() {
		return length;
	}
	public String getCategory(String ending) {
		String result = "";
		for(int x=0;x<Types.length;x++) {
			if (ending.equals(getType(x).getEnding())) {
				result = getType(x).getCategory();
			}
		}
		return result;
	}
	public static void setType(int id, TypeItem item) {
		Types[id] = item;
	}
	public static void sort() {
		int quantity;
		c_Files = 0;
		for(int i=0;i<Types.length;i++) {
			c_Files = c_Files + Types[i].getQuantity();
		}
		for(int pos=0;pos<Types.length;pos++) {				// pos = current position the sort algorithm is applied to
			int id = pos;									// id = id of highest quantity item this round
			int max = Types[pos].getQuantity();				// max = max quantity in this round
			for(int item=pos;item<Types.length;item++) {	// item = currently processed item id
				quantity = Types[item].getQuantity();		// quantity = quantity of current item
				if (quantity > max) {
					max = quantity;
					id = item;										// saves id of new max
				}
			}
			TypeItem backup = Types[id];			//id		->	backup
			setType(id, Types[pos]);				//pos		->	id
			setType(pos, backup);					//backup	->	pos
		}
	}
	public static void analyze() {
		System.out.println("Out of "+c_Files+" total files:");
		for(int x=0;x<Types.length;x++) {
			TypeItem temp = Types[x];
			System.out.println(temp.getQuantity()+" "+temp.getEnding().toUpperCase()+"-files ("+((100*temp.getQuantity())/c_Files)+"%)");
		}
	}

}
