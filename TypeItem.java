package v00s12;

public class TypeItem {
	String ending;
	String category;
	int typeRelevance;
	int quantity;
	long size;
	public TypeItem(String ending, String category, int typeRelevance, int quantity, long size) {
		this.ending = ending;
		this.category = category;
		this.typeRelevance = typeRelevance;
		this.quantity = quantity;
		this.size = size;
	}
	public static TypeItem createType(String ending, String category, int typeRelevance, int quantity, long size) {
		return new TypeItem(ending, category, typeRelevance, quantity, size);
	}
	public String getEnding() {
		return this.ending;
	}
	public String getCategory() {
		return this.category;
	}
	public int getTypeRelevance() {
		return this.typeRelevance;
	}
	public int getQuantity() {
		return this.quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void increaseQuantity(int value) {
		this.quantity = quantity+value;
	}
	public long getSize() {
		return this.size;
	}
	public void increaseSize(long value) {
		this.size = size+value;
	}
}
