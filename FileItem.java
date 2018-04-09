package v00s10;

import java.io.File;

public class FileItem {
	String path;
	String name;
	String ending;
	double size;
	String sizeFormatted;
	double basicRelevance;
	double additionalRelevance;
	double totalRelevance;
	double normalizedRelevance;
	public FileItem(String path, String name, String ending, double size, String sizeFormatted, double basicRelevance, double additionalRelevance, double totalRelevance, double normalizedRelevance) {
		this.path = path;
		this.name = name;
		this.ending = ending;
		this.size = size;
		this.sizeFormatted = sizeFormatted;
		this.basicRelevance = basicRelevance;
		this.additionalRelevance = additionalRelevance;
		this.totalRelevance = totalRelevance;
		this.normalizedRelevance = normalizedRelevance;
	}
	public static FileItem createFile(String path, String name) {
		String[] temp = path.split("\\.");
		String ending = temp[temp.length-1];
		double size = new File(path).length();
		String sizeFormatted = General.FormatSize(size);
		double basicRelevance = General.getBasicRelevance(ending);
		double additionalRelevance = General.getAdditionalRelevance(size);
		double totalRelevance = (basicRelevance*additionalRelevance+100)/100;
		System.out.println("File " + name + " is of type " + Window.MyTypeList.getCategory(ending) + "(priority "+General.round(basicRelevance, 2)+"%), has a size of " + sizeFormatted + " (Additional Relevance " + General.round(additionalRelevance, 2) + "%) and got the individual Importance of " + General.round(totalRelevance, 2));
		return new FileItem(path, name, ending, size, sizeFormatted, General.round(basicRelevance, 2), General.round(additionalRelevance, 2), General.round(totalRelevance, 2), 0.0);
	}
	public String getName() {
		return this.name;
	}
	public String getPath() {
		return this.path;
	}
	public String getEnding() {
		return this.ending;
	}
	public String getSizeFormatted() {
		return this.sizeFormatted;
	}
	public double getSize() {
		return this.size;
	}
	public double getBasicRelevance() {
		return this.basicRelevance;
	}
	public double getAdditionalRelevance() {
		return this.additionalRelevance;
	}
	public double getTotalRelevance() {
		return this.totalRelevance;
	}
	public void setNormalizedRelevance(double normalizedRelevance) {
		this.normalizedRelevance = normalizedRelevance;
	}
	public double getNormalizedRelevance() {
		return this.normalizedRelevance;
	}
}
