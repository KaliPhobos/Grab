package v00s17;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyThread implements Runnable {
	public void run() {
		
	}
	public CopyThread(String fullSourcePath, String fullDestPath, int ThreadID, int ItemID) throws IOException {
		copyFile(fullSourcePath, fullDestPath);
		reportBack(ThreadID, ItemID);
	}
	public void copyFile(String src, String dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(src);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}
	}
	public void reportBack(int ThreadID, int ItemID) {			// 0=sleeping
		FileManager.ItemList[ItemID][2] = "2";					// 1=working
		FileManager.ThreadList[ThreadID] = 0;					// 2=done
		/*
		 * fileManager:
		 * 	dateien sortiert in array mit zustand 0 (waiting)
		 * 	in fileManager int-array aufsetzen (4 lang)
		 * 	4 threads starten für paralleles Kopieren
		 * 	threads als 0,1,2,3 nummerieren (related threads)
		 * 
		 * copy thread:
		 * 	bekommt pfade für souce und destination übergeben
		 * 	bekommt eigene ID (0-3) und item-id übergeben
		 * 	setzt item-array[item-id] auf 1 (working)
		 * 	setzt intArray[thread-id] auf 1 (working)
		 * 	wenn fertig:
		 * 		item-liste[item-id] auf 2 (done)
		 * 		intArray[thread-id] auf 0 (waiting)
		 * 		fileManager sieht 0 im intArray
		 * 		fileManager sucht nächstes FileItem auf mode=0
		 * 		fileManager startet neuen thread mit freier ID
		 * 
		 */
	}
}
