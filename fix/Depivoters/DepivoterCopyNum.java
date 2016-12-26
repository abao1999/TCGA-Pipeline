package Depivoters;

import java.io.*;
import java.util.*;

/**
 * Called by Depivoters/Depivoter.
 * Provides a special method to depivot copy number files.
 */
public class DepivoterCopyNum {
	/**
	 * Depivots the copy number files.
	 * @param url		the path of the old file
	 * @param newPath	the new path of the depivoted file (usually same directory as pivoted file)
	 * @throws IOException
	 */
	public static void depivotCopyNum(String url, String newPath) throws IOException {
		StringTokenizer st;
		BufferedReader TSVFile = new BufferedReader(new FileReader(url));
		FileWriter writer = new FileWriter(newPath);
		String dataRow = TSVFile.readLine(); // Read first line.
		writer.write("Patient_ID\tSample\tChromosome\tStart\tEnd\tNum_Probes\tSegment_Mean");
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

				if(!first && lineNumber >= 1){

					rest = '\t' + st.nextElement().toString() + '\t' + st.nextElement().toString() 
							+ '\t'+st.nextElement().toString()+'\t'+st.nextElement().toString();
					//+ '\t' + st.nextElement().toString();
					line.add(firstStr);
					line.add(rest);

				}
				else	{
					first = false;
					line.add(firstStr);
				}
				if(lineNumber > 1 && col > 0){
					writer.write(line.get(0).substring(0,12) + '\t' + line.get(0).substring(13)+ '\t' + firstStr + rest );
					writer.write(System.lineSeparator());
				}
				if (col == 0)	{
					col++;
				}
				else	{
					col ++;
				}
			}
			dataRow = TSVFile.readLine(); // Read next line of data.
			lineNumber++;
		}
		writer.close();
		// Close the file once all data has been read.
		TSVFile.close();
		return;
	}
}


/**
 * Below is attempt to convert to split... didn't work... wasted too much time on it
 */
//package Depivoters;
//
//import java.io.*;
//import java.util.*;
//
///**
// * Called by Depivoters/Depivoter.
// * Provides a special method to depivot copy number files.
// */
//public class DepivoterCopyNum {
//	/**
//	 * Depivots the copy number files.
//	 * @param url		the path of the old file
//	 * @param newPath	the new path of the depivoted file (usually same directory as pivoted file)
//	 * @throws IOException
//	 */
//	public static void depivotCopyNum(String url, String newPath) throws IOException {
//		BufferedReader TSVFile = new BufferedReader(new FileReader(url));
//
//		FileWriter writer = new FileWriter(newPath);
//
//		String dataRow = TSVFile.readLine(); // Read first line.
//		dataRow = TSVFile.readLine();
//		writer.write("Patient_ID\tSample\tChromosome\tStart\tEnd\tNum_Probes\tSegment_Mean");
//		writer.write(System.lineSeparator());
//		int lineNumber = 0;
//		while (dataRow != null)	{
//			boolean first = true;
//			ArrayList<String> line = new ArrayList<String>();
//			String dataRowActual = dataRow + "ENDSTOP";
//			String[] splitLine = dataRowActual.split("\t");
//			splitLine[splitLine.length-1] = splitLine[splitLine.length-1].replaceAll("ENDSTOP","");
//			writer.write(String.join("\t", splitLine));
//			writer.write(System.lineSeparator());
//			int col = 0;
//			for(int i=0; i<splitLine.length; i++)	{
//				String firstStr = splitLine[0];
//				String rest = "";
//				if(!first && lineNumber >= 1)	{
//					rest = '\t' + splitLine[1] + '\t' + splitLine[2] 
//							+ '\t'+splitLine[3]+'\t'+splitLine[4];
//					//+ '\t' + st.nextElement().toString();
//					line.add(firstStr);
//					line.add(rest);
//
//				}
//				else	{
//					first = false;
//					line.add(firstStr);
//				}
//				if(lineNumber > 1 && col > 0)	{
//					writer.write(line.get(0).substring(0,12) + '\t' + line.get(0).substring(13)+ '\t' + firstStr + rest );
//					writer.write(System.lineSeparator());
//				}
//				col ++;
//			}
//			lineNumber++;
//			dataRow = TSVFile.readLine(); // Read next line of data.
//		}
//		writer.close();
//		// Close the file once all data has been read.
//		TSVFile.close();
//		return;
//	}
//}
