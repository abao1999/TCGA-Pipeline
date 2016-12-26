package Annotations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Renames files in a target folder.
 * Works in tandem with OncAnnotator and CopyNumAnnotator to rename REVISED2 (fully annotated) files to their original names.
 */
public class Renamer {
	/** 
	 * Replaces a file at an old file path with a copy at a new file path
	 * @param oldPath	the old path of the file
	 * @param newPath	the new path of the file
	 */
	public static void rename(String oldPath, String newPath)	{
		File file1 = new File(oldPath); //File (or directory) with old name
		File file2 = new File(newPath); //File (or directory) with new name
		boolean success = file1.renameTo(file2); //Rename file (or directory)
		if (!success) {
			System.out.println("error"); //Check if file renaming was successful
		}
	}
	//	/**
	//	 * Renames all copy number files with the phrase "REVISED2", or renames them back to their old names.
	//	 * @param folderPath	the TCGA data folder
	//	 * @throws IOException
	//	 */
	//	public static void runRenameCopyNum(String folderPath) throws IOException {
	//		ArrayList<String> paths = CopyNumAnnotator.getPaths(folderPath); //Gets the paths of the Oncotated files in the TCGA data folder
	//		for (String path:paths) {
	//			File item = new File(path);
	//			if (!item.isDirectory() && item.getName().contains("REVISED2")){ //Renames REVISED2 files to get rid of the REVISED2 suffix.
	//				String oldName = item.getAbsolutePath();
	//				String newName = oldName.substring(0, oldName.indexOf(".REVISED2")).concat(".txt");
	//				rename(oldName,newName);
	//			}
	//			else if(!item.isDirectory()){} 
	//			else{
	//				runRenameCopyNum(item.getAbsolutePath()); //If the file path is a directory, the method is run again
	//			}
	//		}
	//	}
	/**
	 * Renames all Oncotated files with the phrase "REVISED", or renames them back to their old names.
	 * @param folderPath	the TCGA data folder
	 * @throws IOException
	 */
	public static void runRenameOnc(String folderPath) throws IOException {
		ArrayList<String> paths = OncAnnotator.getPaths(folderPath); //Gets the paths of the Oncotated files in the TCGA data folder
		for (String path:paths) {
			File item = new File(path);
			if (!item.isDirectory() && item.getName().contains("REVISED")){ //Renames REVISED files to get rid of the REVISED suffix.
				String oldName = item.getAbsolutePath();
				String newName = oldName.substring(0, oldName.indexOf(".REVISED")).concat(".txt");
				rename(oldName,newName);
			}
			else if(!item.isDirectory()){} 
			else{
				runRenameOnc(item.getAbsolutePath()); //If the file path is a directory, the method is run again
			}
		}
	}
}
