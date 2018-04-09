package v00s09;

public class FileList {
	static FileItem[] Files;
	public FileList(String[][] FilesFound) {
		this.Files = new FileItem[FilesFound.length];
		for(int x=0;x<FilesFound.length;x++) {
			this.Files[x] = FileItem.createFile(FilesFound[x][0], FilesFound[x][1]);
		}
	}
	public static int getLength() {
		return Files.length;
	}
	public static FileItem getFile(int id) {
		return Files[id];
	}
	public static FileList createFileList(String[][] FilesFound) {
		return new FileList(FilesFound);
	}
	public static void setFile(int id, FileItem item) {
		Files[id] = item;
	}
	public static void sort() {
		for(int pos=0;pos<Files.length;pos++) {
			int id = 0;
			double max = getFile(pos).getTotalRelevance();
			for(int item=pos;item<Files.length;item++) {
				if (getFile(item).getTotalRelevance() > max) {
					max = getFile(item).getTotalRelevance();
					id = item;										// saves id of new max
				}
			}
			FileItem backup = getFile(id);			//id		->	backup
			setFile(id, getFile(pos));				//pos		->	id
			setFile(pos, backup);					//backup	->	pos
		}
	}
	public static void normalize() {
		double factor = 100.0/getFile(1).getTotalRelevance();		// 1 instead of 0 to fix bug with first item being wrong
		for(int pos=0;pos<Files.length;pos++) {
			FileItem temp = getFile(pos);
			temp.setNormalizedRelevance(temp.getTotalRelevance()*factor);
		}
	}
}