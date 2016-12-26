package Parsers;

import java.io.*;
import java.util.*;
/**
 * Deletes previous versions of a revised file (marked REVISED).
 */
public class Cleaner {
	/**
	 * Gets the paths for all files 
	 * @param folderPath	the path of the target folder
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String> getPaths(String folderPath) throws IOException {
		File folder = new File(folderPath);
		ArrayList<String> paths = new ArrayList<String>();
		if (folder.isDirectory())	{
			ArrayList<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles()));
			for(File item:files) {
				paths.add(item.getAbsolutePath());
			}
		}
		else	{
			paths.add(folderPath);
		}
		return paths;
	}
	/**
	 * Finds the number of instances of the target keyword within a folder's name.
	 * @param folder	the target folder path
	 * @param keyword	the target keyword
	 * @return count	number of instances
	 * @throws Exception
	 */
	public static int getOccurrences(String str, String target)	{
		int lastIndex = 0;
		int count = 0;
		while(lastIndex != -1){

			lastIndex = str.indexOf(target,lastIndex);

			if(lastIndex != -1){
				count ++;
				lastIndex += target.length();
			}
		}
		return count;

	}
	/**
	 * Finds the highest number of instances of the target keyword within a folder's files' names.
	 * @param folder	the target folder path
	 * @param keyword	the target keyword
	 * @return count	number of instances
	 * @throws Exception
	 */
	public static int findRevisions(String folder, String keyword) throws IOException	{
		int count = 0;
		for(String file:getPaths(folder)) {
			File f = new File(file);
			int occurrences = getOccurrences(f.getName(), keyword);
			if (occurrences > count)	{
				count = occurrences;
			}
		}
		return count;
	}
	/**
	 * Wipes all files in a directory with a certain keyword.
	 * @param directory	the target directory
	 * @param keyword	the target keyword
	 * @throws Exception
	 */
	public static void wipe(String directory, String keyword) throws IOException	{
		ArrayList<String> folders = getPaths(directory);
		for(String folder:folders) {
			ArrayList<String> nextFolders = getPaths(folder);
			for(String nextFolder: nextFolders)	{
				int count = findRevisions(nextFolder,keyword);
				ArrayList<String> files = getPaths(nextFolder);
				for(String file:files) {
					System.out.println("Scanning around: "+file);
					File f = new File(file);
					if (getOccurrences(f.getName(), keyword) < count)	{
						f.delete();
					}
				}
			}
		}
	}
	/**
	 * Renames old files into new files
	 * @param oldName
	 * @param newName
	 */
	public static void rename(String oldName, String newName) throws IOException	{
		// File (or directory) with old name
		File file1 = new File(oldName);
		// File (or directory) with new name
		File file2 = new File(newName);
		// Rename file (or directory)
		boolean success = file1.renameTo(file2);
		if (!success) {
			System.out.println("error");
		}
	}
	/**
	 * Gets rid of all .REVISED from files names
	 * For example, a file called fileName.REVISED.REVISED.REVISED becomes fileName
	 * @param folderPath
	 * @throws Exception
	 */
	public static void reduceFileName(String folderPath) throws IOException {
		File folder = new File(folderPath);
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles()));
		for (File item:files) {
			if (!item.isDirectory() && item.getName().contains("REVISED")){

				String oldName = item.getAbsolutePath();
				String newName = oldName.substring(0, oldName.indexOf(".REVISED")).concat(".txt");
				rename(oldName,newName);
			}
			else if(!item.isDirectory()){}
			else{
				reduceFileName(item.getAbsolutePath());
			}
		}
	}
}
