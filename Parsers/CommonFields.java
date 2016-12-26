package Parsers;
import java.util.*;
import java.io.*;

/**
 * Reads and compares fields between Oncotated files of the same type in each cancer cohort.
 * Returns the fields that are common among all Oncotated files.
 */

public class CommonFields {
	/**
	 * Gets all the common fields between all files.
	 * @param filePaths		the paths of all files to compare fields between
	 * @return commonFields	the fields common to all files
	 * @throws Exception
	 */
	public static ArrayList<String> getCommons(ArrayList<String> filePaths) throws IOException {
		ArrayList<ArrayList<String>> allFields = new ArrayList<ArrayList<String>>();
		ArrayList<String> commonFields = new ArrayList<String>();
		for(String filePath:filePaths) {
			ArrayList<String> fields = FileComparer.getFields(filePath);
			allFields.add(fields); //Generates a two-dimensional matrix of fields from all files.
		}
		boolean check = true;
		for(String field:allFields.get(0)) { //If a field in the first file is in all files, then it is added (this is sufficient)
			for(int i = 1; i < allFields.size(); i++) { //Nested for-loop processes the matrix of allFields
				check = allFields.get(i).contains(field);
				if(!check) { //If a file lacks a field, the next field is checked.
					break;
				}
			}
			if(check) { //If all files have a field, it is added to the list.
				commonFields.add(field);
			}
		}
		return commonFields;
	}
	public static void main(String[] args)	throws IOException{
		String parseInPath = "/Users/anthonybao/Desktop/tcga-data";
		ArrayList<String> paths = FileComparer.getPaths("Oncotated", parseInPath);
		ArrayList<String> commons = getCommons(paths);
		System.out.println(commons);

	}
}
