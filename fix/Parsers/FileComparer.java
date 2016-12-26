package Parsers;

import java.io.*;
import java.util.*;
/**
 * Contains some methods needed to compare fields in TCGA data files.
 * Works in tandem with Parsers/CommonFields.java.
 */
public class FileComparer {
	/**
	 * Gets the fields from a file (when de-pivoted, the first line has the fields).
	 * @param filePath		the path of the file to get fields from
	 * @return dataArray	the ArrayList of fields from the file
	 * @throws Exception
	 */
	public static ArrayList<String> getFields(String filePath) throws IOException {
		String[] st;
		BufferedReader TSVFile = new BufferedReader(new FileReader(filePath)); //Reads tab-delimited file.
		ArrayList<String>dataArray;
		String dataRow = TSVFile.readLine(); //Reads first line.
		while(dataRow.contains("#")){ //skip headers
			dataRow = TSVFile.readLine();
		}
		//dataRow = TSVFile.readLine(); //Reads second line.
		//dataRow = TSVFile.readLine(); //Reads third line.
		//dataRow = TSVFile.readLine(); //Reads fourth line.

		st = dataRow.split("\t");
		dataArray = new ArrayList<String>(Arrays.asList(st)); //
		TSVFile.close();
		return dataArray;
	}
	/**
	 * Gets the paths of all TCGA data files with a certain keyword. 
	 * @param keyword	the keyword that a file must have to be included
	 * @param mainPath	the path of the target folder with all the TCGA data
	 * @return paths	the paths of all files with the keyword
	 */
	public static ArrayList<String> getPaths(String keyword, String mainPath) {
		File mainFolder = new File(mainPath);
		ArrayList<String> paths = new ArrayList<String>(); //Return value
		ArrayList<File> possibleCohorts = new ArrayList<File>(Arrays.asList(mainFolder.listFiles())); //Not all folders in the download folder may be relevant.
		ArrayList<File> cohorts = new ArrayList<File>();
		for(File file: possibleCohorts){
			//apparently, this doesn't work on mac
			if(!file.isHidden() || !file.isFile()){ //Gets actual cohorts from the list of downloads.
				cohorts.add(file);
			}
		}
		for(File cohort:cohorts) {
			if (cohort.isFile()) continue;
			ArrayList<File> foldersInCohort = new ArrayList<File>(Arrays.asList(cohort.listFiles())); //ArrayList of all folders within one cohort.
			for(File folder:foldersInCohort) {
				if (folder.isFile()) continue;
				String folderPath = folder.getAbsolutePath();
				if(folderPath.contains(keyword) && !folderPath.equals("")) {
					ArrayList<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles())); //Gets all files within one folder in a cohort.
					if(files.size() == 0) { //Checks if files contain anything.
						continue;
					}
					for(File file:files) {
						String filePath = file.getAbsolutePath();
						if(!filePath.contains("MANIFEST")){ //Ignores manifests.
							paths.add(filePath);
						}
					}
				}
			}
		}
		return paths;
	}
}