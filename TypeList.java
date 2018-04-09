package v00s09;

public class TypeList {
	
	TypeItem[] Types;
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
}
