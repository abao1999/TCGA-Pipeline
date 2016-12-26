package Parsers;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Processes Oncotated files and normalizes the spaces.
 */
public class Oncotator {
	/**
	 * Reads the contents of any tab-delimited text file.
	 * Also writes the contents to a new file path while adding appended content.
	 * @param oldPath	the path of the original file
	 * @param newPath	the path of the new file
	 * @param fields	the ArrayList of fields
	 * @throws IOException
	 */
	public static void readTSV(String url, String newPath, ArrayList<String> fields) throws IOException {

		BufferedReader TSVFile = new BufferedReader(new FileReader(url));
		File newFile = new File(newPath);
		newFile.getParentFile().mkdirs();
		FileWriter writer = new FileWriter(newPath);

		String dataRow = TSVFile.readLine(); // Read first line.
		ArrayList<Integer> relCol = new ArrayList<Integer>(); //List of Relevant Columns
		//int[] relColArr = new int[fields.size()];

		int lineNumber = 0;
		while(dataRow.contains("#")){ //skip headers
			dataRow = TSVFile.readLine();
			lineNumber++;
		}
		//4th line
		String[] headerLine = dataRow.split("\t");
		int numHeaders = headerLine.length;
		int colNum = 0;
		int arrInd = 0;
		for(String header: headerLine){
			//can be replaced with binary search
			if(fields.contains(header)){
				if(colNum == numHeaders-1) writer.write(header);// not tab at the end of line
				writer.write(header+'\t');
				relCol.add(arrInd); //the relevant column index
				//relColArr[arrInd] = colNum;
				arrInd++;
			}
			colNum++;
		}

		final int colMAX = colNum;

		lineNumber++;
		writer.write(System.lineSeparator());
		String[] lineSplit;
		while ((dataRow = TSVFile.readLine()) != null){
			String dataRowAdd = dataRow + "ENDSTOP"; //Add token to the end of each line
			lineSplit = dataRowAdd.split("\t"); //split by tab. Note that split will automatically trim the string, so we added the token "ENDSTOP"
			lineSplit[lineSplit.length-1] = lineSplit[lineSplit.length-1].replaceAll("ENDSTOP", ""); //Remove the token that will have been acumulated in the end of the line
			colNum = 0;
			for(String field: lineSplit){

				if(relCol.contains(colNum)){
					if (field.equals("")) field = "NULL";

					if(colNum == numHeaders-1){ 
						writer.write(field);
					}else{
						writer.write(field+'\t');
					}

				}

				colNum++;
			}
			while(colNum < colMAX){ //if the writer did not write enough, we fill in rest col
				if (colNum == colMAX-1) writer.write("NULL");
				else writer.write("NULL\t");
				colNum++;
			}

			writer.write(System.lineSeparator());

			lineNumber++;

		}
		writer.close();
		// Close the file once all data has been read.
		TSVFile.close();

		return;
	}

	/**
	 * Initializes a Comparator class with a compare method that is not sensitive to case.
	 */
	public static Comparator<String> ALPHABETICAL_ORDER = new Comparator<String>() {
		public int compare(String str1, String str2) { //Compare method overwriting the default.
			int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
			if (res == 0) {
				res = str1.compareTo(str2);
			}
			return res;
		}
	};

	/**
	 * Depivots Oncotated files... uses ONLY the common fields, which are common among all oncotated files
	 * @param path
	 * @throws IOException
	 */
	public static void scanOnc(String parseInPath, String parseOutPath) throws IOException	{
		ArrayList<String> paths = FileComparer.getPaths("Oncotated", parseInPath);
		if(paths.size()!=0) {
			File oncotated_file = new File(paths.get(0));
			String cohort_name = oncotated_file.getParentFile().getParentFile().getName();
			String cohort_name_prefix = cohort_name + ".";
			ArrayList<File> filePaths = new ArrayList<File>(paths.stream().map(p -> new File(p)).collect(Collectors.toList()));
			ArrayList<String> newPaths = new ArrayList<String>(filePaths.stream().map(p -> parseOutPath.concat("/").concat(p.getParentFile().getParentFile().getName()).concat("/Oncotated_").concat(p.getParentFile().getParentFile().getName()).concat("/").concat(cohort_name_prefix+p.getName().substring(0, p.getName().lastIndexOf(".")+1)+"REVISED"+".txt")).collect(Collectors.toList())); 
			ArrayList<String> commons = CommonFields.getCommons(paths);
			//System.out.println(commons);
			Collections.sort(commons, ALPHABETICAL_ORDER);
			for(int i = 0; i < paths.size();i++) {
				readTSV(paths.get(i), newPaths.get(i), commons);
				System.out.println("Rewriting Oncotated file from: "+paths.get(i)+" to: "+newPaths.get(i));
			}
		}
	}

	/**
	 * Depivots Oncotated files... uses ONLY the relevant, important Oncotated fields, as listed in the method FieldFilter.getImportantOncFields()
	 * @param path
	 * @throws IOException
	 */
	public static void scanOnc2(String parseInPath, String parseOutPath) throws IOException {
		ArrayList<String> paths = FileComparer.getPaths("Oncotated", parseInPath);
		if(paths.size()!=0) {
			File oncotated_file = new File(paths.get(0));
			String cohort_name = oncotated_file.getParentFile().getParentFile().getName();
			String cohort_name_prefix = cohort_name + ".";
			ArrayList<File> filePaths = new ArrayList<File>(paths.stream().map(p -> new File(p)).collect(Collectors.toList()));
			ArrayList<String> newPaths = new ArrayList<String>(filePaths.stream().map(p -> parseOutPath.concat("/").concat(p.getParentFile().getParentFile().getName()).concat("/Oncotated_").concat(p.getParentFile().getParentFile().getName()).concat("/").concat(cohort_name_prefix+p.getName().substring(0, p.getName().lastIndexOf(".")+1)+"REVISED"+".txt")).collect(Collectors.toList())); 
			//ArrayList<String> importantOncFields = FieldFilter.getImportantOncFields();
			for(int i = 0; i < paths.size();i++) {
				if(!paths.get(i).contains(".DS_Store")) {
					OncFieldSweeper.sweepFile(paths.get(i), newPaths.get(i));
					System.out.println("Rewriting Oncotated file from: "+paths.get(i)+" to: "+newPaths.get(i));
				}
			}
		}
	}
	public static void main(String[] args) throws IOException {
		// mac test
		String parseInPath = "/Users/anthonybao/Desktop/tcga-data";
		String parseOutPath = "/Users/anthonybao/Desktop/tcga-data-revisions";
		scanOnc2(parseInPath, parseOutPath);
	}
}
