package Unzippers;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
/**
 * Unzips a tar.gz into a tar file. 
 * Note: All files downloaded from Downloader/FileAcquirer are tar.gz files.
 */
public class GZipFile	{
	/**
	 * Gets the paths for all non-hidden items in the target folder
	 * @param folderPath	path of the target folder (contains tar.gz files with TCGA data)
	 * @return paths		the paths for all files in the target folder
	 */
	public static ArrayList<String> getPaths(String folderPath)	{
		File folder = new File(folderPath); //Initiate a File object
		ArrayList<String> paths = new ArrayList<String>(); //Create an ArrayList String to store the file paths.
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles())); //Gets all files within a folder
		for(File item:files) {
			if(!item.isHidden()) paths.add(item.getAbsolutePath()); //Only adds nonhidden items
		}
		return paths;
	}
	/**
	 * Unzips a particular tar.gz into a tar file.
	 * @param folderPath path of the tarball to unzip
	 * @throws IndexOutOfBoundsException is necessary because FPPP only has 1 file in it
	 */
	public static void gUnzip(String folderPath) throws IndexOutOfBoundsException	{
		byte[] buffer = new byte[1024]; // initialize a buffer
		ArrayList<String> folderPaths = getPaths(folderPath); //gets cohorts
		for(String folder:folderPaths) {
			ArrayList<String> inputPaths = getPaths(folder); //gets all zipped tarball files.
			for(String input:inputPaths) {
				System.out.println(input);
				String output = input.substring(0, input.indexOf("gz")-1); //creates a name for the unzipped file
				try{ //Attempts to unzip unless it throws an error
					GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(input)); 
					FileOutputStream out = new FileOutputStream(output);
					int len;
					while ((len = gzis.read(buffer)) > 0) {
						out.write(buffer, 0, len); //standard read then write procedure

					}
					gzis.close(); //close the reader
					out.close(); //close the writer
					System.out.println("Finished unzipping to tar: "+input); //Allows for debugging.
				}catch(IOException ex){
					ex.printStackTrace(); 		  
				}
			}
		}
	}
	public static void main(String[] args) {
		//		String folderPath = "/Users/anthonybao/Desktop/tcga-data";
		//		gUnzip(folderPath);
		String refPath = "/Users/anthonybao/Desktop/tcga-ref-data";
		gUnzip(refPath);
	}
}