package v00s16;

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
		double relevance;
		for(int pos=0;pos<Files.length;pos++) {
			int id = pos;
			double max = getFile(pos).getTotalRelevance();
			for(int item=pos;item<Files.length;item++) {
				relevance = getFile(item).getTotalRelevance();
				if (relevance > max) {
					max = relevance;
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
	public void GuiMoveUp(int[] id) {
		for(int i=0;i<id.length;i++) {
			if (id[i]>0) {
				FileItem temp = getFile(id[i]-1);
				setFile(id[i]-1, getFile(id[i]));
				setFile(id[i], temp);
			}
		}
	}
	public void GuiMoveDown(int[] id) {
		for(int i=id.length-1;i>-1;i--) {
			if (id[i]<Files.length-1) {
				FileItem temp = getFile(id[i]+1);
				setFile(id[i]+1, getFile(id[i]));
				setFile(id[i], temp);
			}
		}
	}
	public void GuiMoveTop(int[] id) {
		for(int i=0;i<id.length;i++) {
			if (id[i]>0) {
				for(int x=(id[i]);x>i+0;x--) {		// Simply moves to #0 and following positions
					FileItem temp = getFile(x-1);	// Later implement it to be moved to (# if current job +1)
					setFile(x-1, getFile(x));		// so it will be marked as next download
					setFile(x, temp);
				}
			}
		}
	}
}