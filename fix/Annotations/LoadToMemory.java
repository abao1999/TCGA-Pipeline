package Annotations;

import java.io.*;
import java.util.*;
/**
 * Searches files for values that are necessary for cross-referencing with UCSC, RefSeq, and ENSEMBL.
 * Contains methods that read copy number and Oncotated TCGA files, then add appropriate annotations.
 * Works in tandem with OncAnnotator and CopyNumAnnotator to add annotations to Oncotated and copy number files, respectively.
 */
public class LoadToMemory {
	//	/**
	//	 * Loads values that are necessary to cross-reference with a file in the ENSEMBL database.
	//	 * @param filePath	the path of the file with the values
	//	 * @return values	the necessary values in a two-dimensional ArrayList of Strings
	//	 * @throws IOException
	//	 */
	//	public static ArrayList<ArrayList<String>> loadToArrayListENSEMBL(String filePath) throws IOException {
	//		BufferedReader reader = new BufferedReader(new FileReader(filePath)); //Reads from the input file.
	//		reader.readLine(); //Skips the first line, which has field names.
	//		ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
	//		String line;
	//		while((line = reader.readLine()) != null){
	//			String[] tabular = line.split("\t"); //Turns the current line into a String array.
	//			String[] entry = new String[5]; //Creates a small String array meant to hold the five relevant values.
	//			//The five relevant values and their field names in the file.
	//			entry[0] = tabular[2];  //chrom
	//			entry[1] = tabular[4];  //txStart
	//			entry[2] = tabular[5];  //txEnd
	//			entry[3] = tabular[1];  //name
	//			entry[4] = tabular[12]; //name2
	//			ArrayList<String> result = new ArrayList<String>(Arrays.asList(entry));
	//			values.add(result); //Adds an extra row to the 2D matrix of values.
	//		}
	//		reader.close(); //Stops using the input file.
	//		return values;
	//	}
	//	/**
	//	 * Loads values that are necessary to cross-reference with a file in the RefSeq database.
	//	 * @param filePath	the path of the file with the values
	//	 * @return values	the necessary values
	//	 * @throws IOException
	//	 */
	//	public static ArrayList<ArrayList<String>> loadToArrayListRefSeq(String filePath) throws IOException {
	//		BufferedReader reader = new BufferedReader(new FileReader(filePath)); //Reads from the input file.
	//		reader.readLine(); //Skips the first line, which has field names.
	//		ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
	//		String line;
	//		while((line = reader.readLine()) != null){
	//			String[] tabular = line.split("\t"); //Turns the current line into a String array.
	//			String[] entry = new String[5]; //Creates a small String array meant to hold the five relevant values.
	//			//The five relevant values and their field names in the file.
	//			entry[0] = tabular[2];  //chrom
	//			entry[1] = tabular[4];  //txStart
	//			entry[2] = tabular[5];  //txEnd
	//			entry[3] = tabular[1];  //name
	//			entry[4] = tabular[0];  //gene name
	//			ArrayList<String> result = new ArrayList<String>(Arrays.asList(entry));
	//			values.add(result); //Adds an extra row to the 2D matrix of values.
	//		}
	//		reader.close(); //Stops using the input file.
	//		return values;
	//	}
	/**
	 * Loads values that are necessary to cross-reference with two files in the UCSC database.
	 * @param filePath1	the path of UCSCknownCanonical
	 * @param filePath2	the path of USCSkgXRef
	 * @return values	the necessary values
	 * @throws IOException
	 */
	public static ArrayList<ArrayList<String>> loadToArrayListUCSC(String filePath1, String filePath2) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath1)); //Reads from the first input file.
		reader.readLine(); //Skips the first line, which has field names.
		ArrayList<ArrayList<String>> values1 = new ArrayList<ArrayList<String>>(); // Create 2-D ArrayList to store info found in UCSCCanonical
		String line;
		while((line = reader.readLine()) != null){
			String[] tabular = line.split("\t"); //Turns the current line into a String array.
			String[] entry = new String[4]; //Creates a small String array meant to hold the four relevant values.
			//The four relevant values and their field names in the file.
			entry[0] = tabular[0];  //chrom
			entry[1] = tabular[1];  //chromStart
			entry[2] = tabular[2];  //chromEnd
			entry[3] = tabular[4];  //transcript
			ArrayList<String> result = new ArrayList<String>(Arrays.asList(entry));
			values1.add(result); //Adds an extra row to the 2D matrix of values.
		}
		reader.close(); //Stops using the first input file.
		//------------------------------------------------------------\\
		reader = new BufferedReader(new FileReader(filePath2)); //Reads from the second input file.
		reader.readLine(); //Skips the first line, which has field names.
		Map<String,String> values2 = new HashMap<String,String>(); // Create a hashmap to store the UCSC id, geneName
		while((line = reader.readLine()) != null){	//Loop through the UCSCkgXRef to find sore gene id geneName pairs
			String[] tabular = line.split("\t"); 	//Turns the current line into a String array.
			values2.put(tabular[0],tabular[4]); 	//Enters KeyValue pair for UCSC id, UCSC geneName into an array

		}

		String search;
		ArrayList<ArrayList<String>> values = values1; //Initialize a final 2-D ArrayList to join the UCSCknownCanonical data with UCSC kgXRef

		ArrayList<String> value;
		for(int i = 0;i < values.size();i++){
			value = values.get(i);
			search = value.get(value.size()-1);
			value.add(values2.get(search));
			values.set(i, value);
		}
		reader.close(); //End of using knownCanonical file
		return values;
	}
	/*
	 * Reads from the copy number file.
	 * Confirmed to work.
	 */
	//	public static void readCopyNum(String url, String newPath, String newHeader, ArrayList<ArrayList<String>> refTable) throws IOException {
	//		BufferedReader TSVFile = new BufferedReader(new FileReader(url));
	//		File newFile = new File(newPath);
	//		newFile.getParentFile().mkdirs();
	//		FileWriter writer = new FileWriter(newPath);
	//
	//		String dataRow = TSVFile.readLine(); // Read first line.
	//		//int[] relColArr = new int[fields.size()];
	//
	//		System.out.println(dataRow);
	//		if (dataRow.charAt(dataRow.length()-1)=='\t') {
	//			writer.write(dataRow+newHeader);
	//
	//		}
	//		else {
	//			writer.write(dataRow+"\t"+newHeader); //header
	//		}
	//		writer.write(System.lineSeparator());
	//
	//		String[] lineSplit;
	//		//System.out.println((relCol));
	//		//System.out.println(relCol.size());
	//		//Arrays.sort(relCol);
	//
	//		while ((dataRow=TSVFile.readLine()) != null){
	//			lineSplit = dataRow.split("\t");
	//
	//			boolean reached = false;
	//			boolean notPassed = true;
	//
	//			String chrom = "chr"+lineSplit[2];
	//			int start;
	//			try{
	//				start = Integer.parseInt(lineSplit[3]);
	//			}catch(NumberFormatException e){ //in case if it is in scientific notation
	//				int divide = lineSplit[3].indexOf("e");
	//				int stem = Integer.parseInt(lineSplit[3].substring(0, divide));
	//				int x10 = Integer.parseInt(lineSplit[3].substring(divide + 1));
	//				start = (int) (stem * Math.pow(10, x10));
	//			}
	//			int end = Integer.parseInt(lineSplit[4]);
	//
	//			ArrayList<ArrayList<String>> pipes = new ArrayList<ArrayList<String>>();
	//
	//			for(ArrayList<String> entry: refTable){
	//
	//				int txStart = Integer.parseInt(entry.get(1));
	//				int txEnd = Integer.parseInt(entry.get(2));
	//				if(chrom.equals(entry.get(0)) && (
	//						(start <= txEnd && start >= txStart)||
	//						(start <= txEnd && end >= txEnd)||
	//						(end <= txEnd && end >= txStart))){
	//					ArrayList<String> names = new ArrayList<String>();
	//					names.add(entry.get(3));
	//					names.add(entry.get(4));
	//					pipes.add(names);
	//					reached = true;
	//					notPassed = false;
	//				}else if(!(reached || notPassed)){
	//					break;
	//				}else{
	//					reached = false;
	//				}
	//			}
	//
	//
	//			String name= "";
	//			String geneName = "";
	//			for(int i = 0; i < pipes.size();i++){
	//				name += pipes.get(i).get(0)+"|";
	//				geneName += pipes.get(i).get(1)+"|";
	//			}
	//			if (dataRow.charAt(dataRow.length()-1)=='\t') {
	//				try{
	//					writer.write(dataRow + name.substring(0, name.length()-1) + "\t" + geneName.substring(0, geneName.length()-1));
	//				}catch(StringIndexOutOfBoundsException e){
	//					writer.write(dataRow + name + "\t" + geneName);
	//				}
	//			}
	//			else {
	//				try{
	//					writer.write(dataRow + "\t" + name.substring(0, name.length()-1) + "\t" + geneName.substring(0, geneName.length()-1));
	//				}catch(StringIndexOutOfBoundsException e){
	//					writer.write(dataRow + "\t" + name + "\t" + geneName);
	//				}
	//			}
	//
	//			writer.write(System.lineSeparator());
	//		}
	//		// Close the file once all data has been read.
	//		TSVFile.close();
	//		writer.close();
	//
	//
	//		return;
	//	}
	/* 
	 * Reads from the Oncotated file.
	 * Confirmed to work.
	 */
	public static void readOnc(String url, String newPath, String newHeader, ArrayList<ArrayList<String>> refTable) throws IOException {
		BufferedReader TSVFile = new BufferedReader(new FileReader(url));
		File newFile = new File(newPath);
		newFile.getParentFile().mkdirs();
		FileWriter writer = new FileWriter(newPath);

		String dataRow = TSVFile.readLine(); // Read first line.
		//int[] relColArr = new int[fields.size()];

		System.out.println(dataRow);
		if (dataRow.charAt(dataRow.length()-1)=='\t') {
			writer.write(dataRow+newHeader);

		}
		else {
			writer.write(dataRow+"\t"+newHeader); //header
		}
		writer.write(System.lineSeparator());

		String[] lineSplit;
		//System.out.println((relCol));
		//System.out.println(relCol.size());
		//Arrays.sort(relCol);

		while ((dataRow=TSVFile.readLine()) != null){
			lineSplit = dataRow.split("\t");

			boolean reached = false;
			boolean notPassed = true;
			//ONLY IF we used Parsers/FieldFilter.java to use only importantOncFields in oncotator files
			String chrom = "chr"+lineSplit[3];
			int start = Integer.parseInt(lineSplit[4]);
			int end = Integer.parseInt(lineSplit[5]);
			////IF we want to use all common fields in oncotator files, then use these settings (indexes + 1)
			//String chrom = "chr"+lineSplit[4];
			//int start = Integer.parseInt(lineSplit[5]);
			//int end = Integer.parseInt(lineSplit[6]);

			ArrayList<ArrayList<String>> pipes = new ArrayList<ArrayList<String>>();

			for(ArrayList<String> entry: refTable){

				int txStart = Integer.parseInt(entry.get(1));
				int txEnd = Integer.parseInt(entry.get(2));
				if(chrom.equals(entry.get(0)) && (
						(start <= txEnd && start >= txStart)||
						(start <= txEnd && end >= txEnd)||
						(end <= txEnd && end >= txStart))){
					ArrayList<String> names = new ArrayList<String>();
					names.add(entry.get(3));
					names.add(entry.get(4));
					pipes.add(names);
					reached = true;
					notPassed = false;
				}else if(!(reached || notPassed)){
					break;
				}else{
					reached = false;
				}
			}


			String name= "";
			String geneName = "";
			for(int i = 0; i < pipes.size();i++){
				name += pipes.get(i).get(0)+"|";
				geneName += pipes.get(i).get(1)+"|";
			}
			if (dataRow.charAt(dataRow.length()-1)=='\t') {
				try{
					writer.write(dataRow + name.substring(0, name.length()-1) + "\t" + geneName.substring(0, geneName.length()-1));
				}catch(StringIndexOutOfBoundsException e){
					writer.write(dataRow + name + "\t" + geneName);
				}
			}
			else {
				try{
					writer.write(dataRow + "\t" + name.substring(0, name.length()-1) + "\t" + geneName.substring(0, geneName.length()-1));
				}catch(StringIndexOutOfBoundsException e){
					writer.write(dataRow + "\t" + name + "\t" + geneName);
				}
			}

			writer.write(System.lineSeparator());
		}
		// Close the file once all data has been read.
		TSVFile.close();
		writer.close();


		return;
	}
}
