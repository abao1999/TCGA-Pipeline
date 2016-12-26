package Downloader;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * A method that can download a file at a URL to a specific file path.
 */
public class FileDownloader {
	/**
	 * Downloads an Internet file to a specific place.
	 * @param urlStr	the URL of the file
	 * @param file		the download path
	 * @throws IOException
	 */
	public static void downloadUsingStream(String urlStr, String file) throws IOException{
		URL url = new URL(urlStr);
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		File tmp = new File(file);
		tmp.getParentFile().mkdirs();
		FileOutputStream fis = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int count=0;
		while((count = bis.read(buffer,0,1024)) != -1)
		{
			fis.write(buffer, 0, count);
		}
		fis.close();
		bis.close();
	}
	/**
	 * Downloads reference data to a specific path.
	 * @param refPath
	 */
	public static void downloadRefData(String refPath) throws IOException	{
		String[] refDataSites = {"http://hgdownload.cse.ucsc.edu/goldenPath/hg19/database/knownCanonical.txt.gz",
				"http://hgdownload.cse.ucsc.edu/goldenPath/hg19/database/kgXref.txt.gz",
				"http://hgdownload.cse.ucsc.edu/goldenPath/hg18/database/knownCanonical.txt.gz",
				"http://hgdownload.cse.ucsc.edu/goldenPath/hg18/database/kgXref.txt.gz",
				//				"http://hgdownload.cse.ucsc.edu/goldenPath/hg19/database/refFlat.txt.gz",
				//				"http://hgdownload.cse.ucsc.edu/goldenPath/hg19/database/ensGene.txt.gz",
				//				"http://hgdownload.cse.ucsc.edu/goldenPath/hg18/database/refFlat.txt.gz",
				//				"http://hgdownload.cse.ucsc.edu/goldenPath/hg18/database/ensGene.txt.gz"
		};
		List<String> refPaths = new ArrayList<String>(Arrays.asList(refDataSites));
		for(String path:refPaths) {
			String filePath = ("_" + path.substring(path.indexOf("Path/")+5, path.indexOf("/database")));
			filePath += ("_" + path.substring(path.indexOf("base/")+5, path.indexOf(".txt")));
			filePath += ".txt.gz";
			if(filePath.contains("refFlat")) {
				filePath = "RefSeq" + filePath;
			}
			else if(filePath.contains("ensGene")) {
				filePath = "Ensembl" + filePath;
			}
			else {
				filePath = "UCSC" + filePath;
			}
			downloadUsingStream(path,refPath+"/"+filePath);
		}
	}
	public static void main(String[] args) throws IOException{
		String refPath = "/Users/anthonybao/Desktop/tcga-ref-data/UCSC";
		downloadRefData(refPath);
		System.out.println("Reference data downloaded to: " + refPath);
	}
}
