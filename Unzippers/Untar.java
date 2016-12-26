package Unzippers;

import java.io.*;
import java.util.*;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
/**
 * Unzips a tar file into an unzipped folder.
 * Works after Unzippers/GZipFile and before Unzippers/Wiper.
 * Note the dependency on Apache Commons.
 */
public class Untar {
	/**
	 * Gets the paths of all files in the target folder.
	 * @param folderPath	the path of the target folder
	 * @return paths		the paths of all files in the target folder
	 */
	private static ArrayList<String> getPaths(String folderPath) {
		File folder = new File(folderPath); //initializes a File object
		ArrayList<String> paths = new ArrayList<String>();
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles())); //Finds the Files subFiles
		for(File item:files) {
			if(!item.isHidden())paths.add(item.getAbsolutePath()); // Add the subfiles' paths to an ArrayList
		}
		return paths;
	}
	/**
	 * Unzips a single file within the target folder
	 * @param tarFile	the path of one file
	 * @param directory	the path of the target folder
	 * @return result	the unzipped tar file
	 * @throws IOException
	 */
	private static ArrayList<String> unTar(File tarFile, File directory) throws IOException {
		ArrayList<String> result = new ArrayList<String>(); 
		InputStream inputStream = new FileInputStream(tarFile); //create a File Input Stream for the untar
		TarArchiveInputStream in = new TarArchiveInputStream(inputStream); //uses the inputStream to make a tarInputStream
		TarArchiveEntry entry; //for
		while ((entry = in.getNextTarEntry()) != null) { //reads each tar entry
			if (entry.isDirectory()) {
				continue;
			}
			File curfile = new File(directory, entry.getName()); //create a newFile in the directory with the name of the entry
			File parent = curfile.getParentFile();
			if (!parent.exists()) { //creates the neccessary folders in levels above the File entry to store in a file system
				parent.mkdirs();
			}
			OutputStream out = new FileOutputStream(curfile); //write the file with the File Output Stream
			IOUtils.copy(in, out); //copies the file in tar to the location where the file will be stored
			out.close();

			result.add(entry.getName()); // the file is stored in an ArrayList that records all the untarred files
		}
		in.close();
		return result;
	}
	/**
	 * Unzips all tar files within the target folder.
	 * @param folderPath	the target folder
	 * @throws IOException
	 */
	public static void untarAll(String folderPath) throws IOException {// replacement for the mainmethod where the program will search for detar all tar files
		ArrayList<String> paths = getPaths(folderPath); // Enter first level of the directory
		ArrayList<ArrayList<String>> items= new ArrayList<ArrayList<String>>();
		for(String cohortPath:paths) {
			ArrayList<String> filePaths = getPaths(cohortPath); // Enter the second level of the directory
			for(String filePath:filePaths) {
				if (!filePath.endsWith(".tar")) { //Will only detar tar files
					continue;
				}
				File file = new File(filePath);
				File directory = new File(cohortPath);
				items.add(unTar(file,directory)); //Adds the untarred files to a greater list of untarred files
				System.out.println("Finished unzipping: "+filePath);
			}
		}
	}
	/**
	 * Runs the program.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//		String folderPath = "/Users/anthonybao/Desktop/tcga-data";
		//		gUnzip(folderPath);
		String refPath = "/Users/anthonybao/Desktop/tcga-ref-data";
		untarAll(refPath);
		System.out.println("Finished un-taring");
	}
}