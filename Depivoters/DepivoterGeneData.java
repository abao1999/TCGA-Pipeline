package Depivoters;
import java.io.*;
import java.util.*;


/**
 * Called by Depivoters/Depivoter.
 * Provides a special method to depivot gene data files.
 */
public class DepivoterGeneData {
	/**
	 * Depivots the gene data files.
	 * @param url		the path of the old file
	 * @param newPath	the new path of the depivoted file (usually same directory as pivoted file)
	 * @throws IOException
	 */
	public static void depivotGenes(String url, String newPath) throws IOException {

		StringTokenizer st;
		BufferedReader TSVFile = new BufferedReader(new FileReader(url));

		FileWriter writer = new FileWriter(newPath);

		String dataRow = TSVFile.readLine(); // Read first line.
		String[] columnName = dataRow.split("\t");

		writer.write("Patient_ID\tSample" + '\t' + "Gene_Name" + '\t' + "Gene_Number" + '\t' + "Raw_Count" + '\t' + "Scaled_Estimate" + '\t' + "Transcript_ID");
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

					rest = '\t' + st.nextElement().toString() + '\t' + st.nextElement().toString();
					line.add(firstStr);
					line.add(rest);

				}else{
					first = false;
					line.add(firstStr);
				}


				if(lineNumber > 1 && col > 0){
					//System.out.println(line.get(0));

					writer.write(columnName[col].substring(0 , 12) + '\t' + columnName[col].substring(13) + '\t' + line.get(0).replace('|','\t')+ '\t' + firstStr + rest );
					writer.write(System.lineSeparator());
				}
				if (col == 0) col++;
				else col += 3;
			}

			dataRow = TSVFile.readLine	(); // Read next line of data.
			lineNumber++;

		}
		writer.close();
		// Close the file once all data has been read.
		TSVFile.close();

		return;
	}
}


//package Depivoters;
//import java.io.*;
//import java.util.*;
//
//
///**
// * Called by Depivoters/Depivoter.
// * Provides a special method to depivot gene data files.
// */
//public class DepivoterGeneData {
//	/**
//	 * Depivots the gene data files.
//	 * @param url		the path of the old file
//	 * @param newPath	the new path of the depivoted file (usually same directory as pivoted file)
//	 * @throws IOException
//	 */
//
//	public static void depivotGenes(String url, String newPath) throws IOException {
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
//
//
//		writer.write("Patient_ID\tSample" + '\t' + "Gene_ID" + '\t' + "Gene_Number" + '\t' + "Raw_Count" + '\t' + "Scaled_Estimate" + '\t' + "Transcript_ID");
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
//			String comparison = data.remove(0);
//			for(int i = 0; i < data.size()/3; i++){
//				writer.write(columnArray.get(0).substring(0, 12)+"\t"+columnArray.get(0).substring(13)+"\t"+String.join("\t",comparison.split("\\|"))+"\t"+String.join("\t", data.subList((3*i), (3*i)+3)));
//
//				writer.newLine();
//			}
//
//
//		}
//		writer.close();
//		// Close the file once all data has been read.
//		TSVFile.close();
//
//		return;
//	}
//
//	public static void main(String[] args) throws IOException{
//		depivotGenes("C:/Users/eric/Downloads/gdac.broadinstitute.org_ACC.Merge_rnaseqv2__illuminahiseq_rnaseqv2__unc_edu__Level_3__RSEM_genes__data.Level_3.2016012800.0.0/ACC.rnaseqv2__illuminahiseq_rnaseqv2__unc_edu__Level_3__RSEM_genes__data.data.txt",
//				"C:/Users/eric/Desktop/contemplation.txt");
//	}
//}
