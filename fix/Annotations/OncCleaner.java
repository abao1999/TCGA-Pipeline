package Annotations;
import java.io.File;
import java.util.ArrayList;
/**
 * Cleans partially annotated or non-annotated Oncotated files in the target folder, by
 * sifting through cancer cohort folders (folders with files corresponding to one cancer type 
 * or one group of cancer types).
 * Works in tandem with Annotations/CopyNumAnnotator.java.
 */
public class OncCleaner {
	/**
	 * Examines a folder and deletes all files without a specific keyword.
	 * @param directory	the folder to examine
	 * @param keyword	the keyword that protects against deletion
	 */
	public static void wipe(String directory, String keyword)	{
		ArrayList<String> filePaths = OncAnnotator.getPaths(directory);
		for(String path:filePaths) {
			File f = new File(path);
			if (!f.getAbsolutePath().contains(keyword)) { //Deletes a file without the keyword
				f.delete();
			}
		}
	}
}
