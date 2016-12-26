package Unzippers;

import java.io.*;

public class Unzipper {
	/**
	 * Manages GZipFile.java, Untar.java, and Wiper.java
	 * @param downloadPath
	 * @throws IOException
	 */
	public static void getTextFiles(String downloadPath) throws IOException	{
		GZipFile.gUnzip(downloadPath);
		System.out.println("All .tar.gz files unzipped to .tar");
		Untar.untarAll(downloadPath);
		System.out.println("All .tar files unzipped to folders");
		System.out.println("Deleting .tar.gz and .tar files");
		String ext = ".tar";
		String ext2 = ".gz";
		Wiper.wipe(downloadPath, ext, ext2);
		System.out.println("Old redundant file versions have been successfully deleted");
		System.out.println("All files are now in .txt format");
	}
	public static void main(String[] args) throws IOException	{
		String downloadPath = "/Users/anthonybao/Desktop/tcga-data";
		getTextFiles(downloadPath);
	}
}
