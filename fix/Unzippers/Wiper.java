package Unzippers;

import java.io.*;
import java.util.*;
/**
 * Gets all the file paths within the TCGA data folder, wipes the tar.gz and tar files, and
 * leaves the useable folders untouched.
 */
public class Wiper {
	/**
	 * Gets all files within the target folder.
	 * @param folderPath	the path of the target folder
	 * @return paths		the paths of all files within the target folder
	 */
	public static ArrayList<String> getPaths(String folderPath) {
		File folder = new File(folderPath);
		ArrayList<String> paths = new ArrayList<String>();
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles()));
		for(File item:files) {
			if(!item.isHidden()) paths.add(item.getAbsolutePath()); //Gets rid of hidden files
		}
		return paths;
	}
	/**
	 * Wipes all files with certain file types within a target folder.
	 * @param directory	the target folder 
	 * @param ext		one file extension to filter out and remove
	 * @param ext2		another file extension to filter out and remove
	 */
	public static void wipe(String directory, String ext, String ext2)	{
		ArrayList<String> folders = getPaths(directory);
		for(String folder:folders) {
			ArrayList<String> files = getPaths(folder);
			for(String file:files) {

				if (file.endsWith(ext) || file.endsWith(ext2)) {
					File f = new File(file);
					f.delete();
				}
			}
		}
	}
	/**
	 * Wipes all files in the target folder with file type .tar.gz or .tar
	 * @param args
	 */
	public static void main(String[] args) {
		String ext = ".tar";
		String ext2 = ".gz";
		//		String dir = "/Users/anthonybao/Desktop/tcga-data";
		//		wipe(dir,ext,ext2);
		String refPath = "/Users/anthonybao/Desktop/tcga-ref-data";
		wipe(refPath,ext,ext2);
		System.out.println("Finished wiping");
		System.out.println("All reference data files are now .txt files");
	}
}