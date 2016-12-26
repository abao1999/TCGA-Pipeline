package Parsers;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Reads a file and re-writes it with some annotations.
 * Uses Parsers/CommonFields.java.
 */
public class Oncotator2 {
	/**
	 * Reads the contents of any tab-delimited text file.
	 * Also writes the contents to a new file path while adding appended content.
	 * @param oldPath		the path of the original file
	 * @param newPath		the path of the new file
	 * @param appendedList	the list of appended content
	 * @throws IOException
	 */
	public static void readTSV(String oldPath, String newPath, String[] appendedList) throws IOException {
		BufferedReader TSVFile = new BufferedReader(new FileReader(oldPath));
		File newFile = new File(newPath);
		newFile.getParentFile().mkdirs();
		FileWriter writer = new FileWriter(newPath);

		String dataRow = TSVFile.readLine(); // Read first line.
		//int[] relColArr = new int[fields.size()];

		System.out.println(dataRow);
		if (dataRow.charAt(dataRow.length()-1)=='\t') {
			writer.write(dataRow+appendedList[0]+"\t"+appendedList[2]+"\t"+appendedList[4]);
		}
		else {
			writer.write(dataRow+"\t"+appendedList[0]+"\t"+appendedList[2]+"\t"+appendedList[4]);
		}
		writer.write(System.lineSeparator());
		dataRow = TSVFile.readLine(); // Read next line of data.

		//System.out.println((relCol));
		//System.out.println(relCol.size());
		//Arrays.sort(relCol);
		while (dataRow != null){
			//ArrayList<String> line = new ArrayList<String>();
			if (dataRow.charAt(dataRow.length()-1)=='\t') {
				writer.write(dataRow+appendedList[1]+"\t"+appendedList[3]+"\t"+appendedList[5]);
			}
			else {
				writer.write(dataRow+"\t"+appendedList[1]+"\t"+appendedList[3]+"\t"+appendedList[5]);
			}
			writer.write(System.lineSeparator());
			dataRow = TSVFile.readLine(); // Read next line of data.
		}
		writer.close();
		// Close the file once all data has been read.
		TSVFile.close();

		return;
	}
	/**
	 * Adds all the book-keeping columns: Cohort, Data_Type, File_Name
	 */
	public static void addBookKeepingColumns(ArrayList<String> paths, String typeOfData) throws IOException	{
		//List of the Cohort, collected into a list
		List<String> cohorts = paths
				.stream()
				.map(p -> new File(p)
						.getParentFile().getParentFile()
						.getName())
				.collect(Collectors.toList());
		//List of the Data_Type, collected into a list
		List<String> dataType = paths
				.stream()
				.map( p -> typeOfData)
				.collect(Collectors.toList());
		//List of the File_Name, collected into a list
		List<String> fileName = paths.stream()
				.map( p -> new File(p).getName().substring(0, new File(p).getName().indexOf("REVISED")-1)).collect(Collectors.toList());
		//List of new file paths
		ArrayList<String> newpaths = new ArrayList<String>(paths.stream()	//Generates the new paths for files based on the original paths 
				.map(p -> p.substring(0, p.lastIndexOf(".")+1)
						.concat("REVISED")
						.concat(".txt"))
				.collect(Collectors.toList()));

		String[] options = new String[6]; //Creates a simple data structure to store what to append to each file
		options[0] = "Cohort";
		//options[1] is the cohort value
		options[2] = "Data_Type";
		//options[3] is the data type value
		options[4] = "File_Name";
		//options[5] is the file name value

		for( int i = 0; i < paths.size();i++){
			options[1] = cohorts.get(i);
			//readTSV(paths.get(i),newpaths.get(i),options);
			options[3] = dataType.get(i);
			//readTSV(paths.get(i),newpaths.get(i),options);
			options[5] = fileName.get(i);
			readTSV(paths.get(i),newpaths.get(i),options);
			System.out.println("Writing file from: "+paths.get(i)+" to: "+newpaths.get(i));
		}
	}
	public static void addBookKeepingColumnsToAll(String path) throws IOException {
		String typeOfData;
		ArrayList<String> paths;
		paths = FileComparer.getPaths("junction_quantification", path);
		typeOfData = "JnctQuant";
		addBookKeepingColumns(paths, typeOfData);
		System.out.println("Finished re-writing junction quantification files");
		paths = FileComparer.getPaths("copy_number", path);
		typeOfData = "CN";
		addBookKeepingColumns(paths, typeOfData);
		System.out.println("Finished re-writing copy number files");
		paths = FileComparer.getPaths("methylation", path);
		typeOfData = "Methylation";
		addBookKeepingColumns(paths, typeOfData);
		System.out.println("Finished re-writing methylation files");
		paths = FileComparer.getPaths("Oncotated", path);
		typeOfData = "SNP";
		addBookKeepingColumns(paths, typeOfData);
		System.out.println("Finished re-writing Oncotated files");
		paths = FileComparer.getPaths("miR_gene_expr", path);
		typeOfData = "miRexpr";
		addBookKeepingColumns(paths, typeOfData);
		System.out.println("Finished re-writing miRNA gene expression files");
		paths = FileComparer.getPaths("rppa", path);
		typeOfData = "RPPA";
		addBookKeepingColumns(paths, typeOfData);
		System.out.println("Finished re-writing RPPA files");
		paths = FileComparer.getPaths("RSEM_gene_data", path);
		typeOfData = "RSEM_gene";
		addBookKeepingColumns(paths, typeOfData);
		System.out.println("Finished RSEM gene data files");
		paths = FileComparer.getPaths("RSEM_isoform_data", path);
		typeOfData = "RSEM_isoform";
		addBookKeepingColumns(paths, typeOfData);
		System.out.println("Finished re-writing RSEM gene data files");
	}
	
	public static void runOncotator2(String path) throws IOException {
		addBookKeepingColumnsToAll(path);
		Cleaner.wipe(path, ".REVISED");
		Cleaner.reduceFileName(path);
	}

	public static void main(String[] args) throws IOException {
		// mac test ===========================================
		String path = "/Users/anthonybao/Desktop/tcga-data-revisions";
		runOncotator2(path);
		System.out.println("FINISHED");
	}
}