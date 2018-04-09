package v00s10;

public class TypeItem {
	String ending;
	String category;
	int typeRelevance;
	int quantity;
	public TypeItem(String ending, String category, int typeRelevance, int quantity) {
		this.ending = ending;
		this.category = category;
		this.typeRelevance = typeRelevance;
		this.quantity = quantity;
	}
	public static TypeItem createType(String ending, String category, int typeRelevance, int quantity) {
		return new TypeItem(ending, category, typeRelevance, quantity);
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
}
