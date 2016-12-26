package Depivoters;

import java.io.*;
import java.util.*;


/**
 * Reads the file and immediately starts to write...
 */
public class DepivoterMethylation {
	/* 
	 * Transfers the contents of a TSV into an ArrayList of Strings, NOT unique to each type of file.
	 */
	//	public static void depivotMethylation(String url, String newPath) throws IOException {
	//
	//		BufferedReader TSVFile = new BufferedReader(new FileReader(url));
	//
	//		BufferedWriter writer = new BufferedWriter(new FileWriter(newPath));
	//
	//		String dataRow = TSVFile.readLine(); // Read first line.
	//		String[] columnName = dataRow.split("\t");
	//		System.out.println(dataRow);
	//		ArrayList<String> columnArray = new ArrayList<String>(Arrays.asList(columnName));
	//		columnArray.remove(0);
	//		System.out.println(columnArray);
	//		writer.write("Patient_ID\tSample" + '\t' + "Composite_Element_Ref" + '\t' + "Chromosome" + '\t' + "Probe" + '\t' + "Genomic_Coordinate");
	//		writer.write(System.lineSeparator());
	//		dataRow = TSVFile.readLine();//iterate thru headers
	//		while ((dataRow = TSVFile.readLine()) != null){
	//
	//			dataRow += "ENDSTOP";
	//			String[] lineSplit = dataRow.split("\t");
	//			int last = lineSplit.length-1; 
	//			lineSplit[last] = lineSplit[last].substring(0, lineSplit[last].lastIndexOf("ENDSTOP"));
	//
	//			ArrayList<String> data = new ArrayList<String>(Arrays.asList(lineSplit));
	//			List<String> comparison = data.subList(0,4);
	//			System.out.println(columnArray.get(4).substring(0, 12)+"\t"+columnArray.get(4).substring(13)+"\t"+String.join("\t",comparison));
	//			System.out.println(columnArray.get(5).substring(0, 12)+"\t"+columnArray.get(5).substring(13)+"\t"+String.join("\t",comparison));
	//
	////			System.out.println(columnArray.size()+"\t"+data.size());
	//			for(int i = 4; i < data.size()-1; i++){
	//				//				ERROR IN LINE BELOW: Exception in thread "main" java.lang.IndexOutOfBoundsException: Index: 80, Size: 80
	//				writer.write(columnArray.get(i).substring(0, 12)+"\t"+columnArray.get(i).substring(13)+"\t"+String.join("\t",comparison)+"\t"+ data.get(i));
	//				writer.newLine();
	//			}
	//		}
	//		writer.close();
	//		// Close the file once all data has been read.
	//		TSVFile.close();
	//
	//		return;
	//	}
	
	public static void depivotMethylation(String url, String newPath) throws IOException {

		StringTokenizer st;
		BufferedReader TSVFile = new BufferedReader(new FileReader(url));

		FileWriter writer = new FileWriter(newPath);
		String name = url.substring(url.indexOf("by_")+ 3, url.indexOf(".data"));

		String dataRow = TSVFile.readLine(); // Read first line.
		String[] columnName = dataRow.split("\t");

		String header = "Patient_ID\tSample\tComposite_Element_Ref\t" + name;

		writer.write(header);
		writer.write(System.lineSeparator());

		int lineNumber = 0;
		while (dataRow != null){
			ArrayList<String> line = new ArrayList<String>();
			boolean first = true;
			st = new StringTokenizer(dataRow,"\t");
			int col = 0;
			while(st.hasMoreElements()){
				String firstStr = st.nextElement().toString();
				String rest = "";

				if(!first && lineNumber > 1){

				}else{
					first = false;
					line.add(firstStr);
				}


				if(lineNumber > 1 && col > 0){
					//System.out.println(line.get(0));
					writer.write(columnName[col].substring(0 , 12) + '\t' + columnName[col].substring(13) + '\t' + line.get(0)+ '\t' + firstStr + rest );
					writer.write(System.lineSeparator());
				}

				col++;
			}

			dataRow = TSVFile.readLine	(); // Read next line of data.
			lineNumber++;

		}
		writer.close();
		// Close the file once all data has been read.
		TSVFile.close();

		return;
	}

	public static void readMinCorrelation(String url, String newPath) throws IOException {

		StringTokenizer st;
		BufferedReader TSVFile = new BufferedReader(new FileReader(url));

		FileWriter writer = new FileWriter(newPath);
		String name = url.substring(url.indexOf("by_")+ 3, url.indexOf(".data"));

		String dataRow = TSVFile.readLine(); // Read first line.
		String[] columnName = dataRow.split("\t");

		String header = "Patient_ID\tSample\tComposite_Element_Ref\tChromosome\tProbe\tGenomic_Coordinate\t" + name;

		writer.write(header);
		writer.write(System.lineSeparator());

		int lineNumber = 0;
		while (dataRow != null){
			ArrayList<String> line = new ArrayList<String>();
			boolean first = true;
			st = new StringTokenizer(dataRow,"\t");
			int col = 0;
			while(st.hasMoreElements()){
				String firstStr = st.nextElement().toString();
				String rest = "";

				if(!first && lineNumber > 1){

				}else if(lineNumber == 0){
					String str = firstStr;// + '\t' + st.nextElement().toString() + '\t' + st.nextElement().toString();//+ '\t' + st.nextElement().toString();
					first = false;

					line.add(str);
					System.out.println(str);

				}else if( col == 0){
					String str = firstStr + '\t' + st.nextElement().toString() + '\t' + st.nextElement().toString() + '\t' + st.nextElement().toString();
					first = false;
					line.add(str);
					System.out.println(str);

				}else{
					String str = "";
					while(st.hasMoreElements()){
						st.nextElement().toString();
					}
					first = false;
					line.add(str);
					System.out.println(str);
				}


				if(lineNumber > 1 && col > 0){
					//System.out.println(line.get(0));
					writer.write(columnName[col].substring(0 , 12) + '\t' + columnName[col].substring(13) + '\t' + line.get(0)+ '\t' + firstStr + rest );
					writer.write(System.lineSeparator());
				}
				if(col==0)col+=4;
				else col++;
			}

			dataRow = TSVFile.readLine(); // Read next line of data.
			lineNumber++;

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
	public static void main(String[] args) throws IOException{
		String inputFile = "/Users/anthonybao/Desktop/MadBob.txt";
		String outputFile = "/Users/anthonybao/Desktop/FierceJoe.txt";
		readMinCorrelation(inputFile,outputFile);
	}
}

