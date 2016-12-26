package Depivoters;

import java.io.*;
import java.util.*;

/**
 * Reads the file and immediately starts to write...
 */
public class DepivoterJunction {
	/* 
	 * Transfers the contents of a TSV into an ArrayList of Strings, NOT unique to each type of file.
	 */
	public static void depivotJunction(String url, String newPath) throws IOException {

		BufferedReader TSVFile = new BufferedReader(new FileReader(url));

		BufferedWriter writer = new BufferedWriter(new FileWriter(newPath));

		String dataRow = TSVFile.readLine(); // Read first line.
		String[] columnName = dataRow.split("\t");
		System.out.println(dataRow);
		ArrayList<String> columnArray = new ArrayList<String>(Arrays.asList(columnName));
		columnArray.remove(0);


		writer.write("Patient_ID\tSample" + '\t' + "junction" + '\t' + "Raw_Counts");
		writer.write(System.lineSeparator());
		dataRow = TSVFile.readLine();//iterate thru headers
		while ((dataRow = TSVFile.readLine()) != null){

			dataRow += "ENDSTOP";
			String[] lineSplit = dataRow.split("\t");
			int last = lineSplit.length-1; 
			lineSplit[last] = lineSplit[last].substring(0, lineSplit[last].lastIndexOf("ENDSTOP"));

			ArrayList<String> data = new ArrayList<String>(Arrays.asList(lineSplit));
			String comparison = data.remove(0);
			System.out.println(comparison);
			for(int i = 0; i < data.size(); i++){
				writer.write(columnArray.get(i).substring(0, 12)+"\t"+columnArray.get(i).substring(13)+"\t"+String.join("\t",comparison)+"\t"+ data.get(i));

				writer.newLine();
			}


		}
		writer.close();
		// Close the file once all data has been read.
		TSVFile.close();

		return;
	}

	/*
	 * Takes all files in a given folder and
	 * Assumes that all files are text.
	 */

	/*
	 * The links serve to test the private methods. The final program will contain links to all cohorts' files for isoforms.
	 */
	public static void main(String[] args) throws IOException {
		String inputFile = "C:/Users/Anthony/Desktop/JoeJunc1.txt";
		String outputFile = "C:/Users/Anthony/Desktop/MadBobJunc1.txt";
		depivotJunction(inputFile,outputFile);
	}
}
