package Parsers;

import java.util.*;

import java.io.*;
/**
 * Sweeps Oncotated call package files and removes undesired fields.
 * Uses code from Annotations/OncAnnotator.java and Annotations/Renamer.java
 */
public class OncFieldSweeper {
	/**
	 * Returns the indices of fields we desire to keep in the field line in an oncotated file.
	 * @param fields 		the ArrayList of fields that we need to keep
	 * @param fileFields	the ArrayList of fields in a particular file
	 * @return an ArrayList of field indices within a file, signifying the locations of each of the fields we need to keep
	 */
	public static ArrayList<Integer> getIndices(ArrayList<String> importantOncFields, ArrayList<String> allOncFields) {
		ArrayList<Integer> indicesOfImportantOncFields = new ArrayList<Integer>();
		for(String oncField: allOncFields)	{
			//			System.out.println(oncField);
			if(importantOncFields.contains(oncField))	{
				indicesOfImportantOncFields.add(allOncFields.indexOf(oncField));
			}
		}
		return indicesOfImportantOncFields;
	}
	/**
	 * Takes out the unwanted fields in a particular oncotated file.
	 * Also wipes the file at the old path.
	 * @param oldPath the path of the target oncotated call package file
	 * @param newPath the path of the new file (most likely a variant of oldPath)
	 */
	public static void sweepFile(String parseInPath, String parseOutPath) throws IOException {
		BufferedReader TSVFile = new BufferedReader(new FileReader(parseInPath));
		File newFile = new File(parseOutPath);
		newFile.getParentFile().mkdirs();
		PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter(parseOutPath)));

		//ArrayList<String> paths = FileComparer.getPaths("Oncotated", parseInPath);
		//ArrayList<String> commonOncFields = CommonFields.getCommons(paths);

		//reads importantOncFields as field names in heading, first line
		String dataRow = TSVFile.readLine();
		String[] headerLine = dataRow.split("\t");
		int numHeaders = headerLine.length;
		while(dataRow.contains("#")){ //skip headers
			dataRow = TSVFile.readLine();
		}
		ArrayList<String> importantOncFields = FieldFilter.getImportantOncFields();
		//System.out.println(importantOncFields);
		int colNum=0;
		for(String header:headerLine)	{
			if(importantOncFields.contains(header))	{
				if(colNum == numHeaders-1) p.print(header);// not tab at the end of line
				p.print(header+'\t');			
			}
			colNum++;
		}
		//ArrayList<String> allOncFields = new ArrayList<String>(Arrays.asList(fieldLine.split("\t"))); //Gets the first line as an ArrayList.
		ArrayList<String> allOncFields = FileComparer.getFields(parseInPath);
		//System.out.println(allOncFields);

		ArrayList<Integer> indices = getIndices(importantOncFields, allOncFields);
		//System.out.println(indices);
		//dataRow = TSVFile.readLine(); //Starts on second line, which is the first line of data.
		while(dataRow != null) {
			ArrayList<String> dataLineList = new ArrayList<String>(Arrays.asList(dataRow.split("\t"))); //Gets each line as an ArrayList
			String sweptLine = "";
			for(int k = 0; k < dataLineList.size(); k++) { //Checks if the current data point corresponds to a field we want to keep.
				if(indices.contains(k)) {
					sweptLine += (dataLineList.get(k)+"\t"); //Adds the wanted data point to a tab-delimited string.
				}
			}
			p.println(sweptLine); //Adds the line to a new file.
			dataRow = TSVFile.readLine();
		}
		TSVFile.close();
		p.close();
	}
	public static void main(String[] args) throws IOException {
		String oldPath = "/Users/anthonybao/Desktop/Onc.txt";
		String newPath = "/Users/anthonybao/Desktop/newOnc.txt";
		sweepFile(oldPath,newPath);
	}
}