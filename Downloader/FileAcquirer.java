package Downloader;

import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Downloads certain TCGA files from each cancer cohort (or designated folder for each cancer type).
 * The program does not download all TCGA files, but only certain files within each cohort.
 * Uses Downloader/FileConditions to make true-false checks for file type and certain keywords.
 * Uses Downloader/FileDownloader to download chosen files.
 * The files can be downloaded at: http://gdac.broadinstitute.org/runs/stddata__2016_01_28/data/
 */
public class FileAcquirer {
	/**
	 * Creates a List of all the file paths of the source code on a given web page.
	 * @param	url		the URL of a given web page
	 * @return	paths	the paths of all downloadable files on the web page
	 * @throws	Exception
	 */
	public static List<String> getFilePaths(String url) throws IOException	{
		URL site = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(site.openStream())); //Reads the HTML code for the website.
		String inputLine;
		List<String> paths = new ArrayList<String>();
		while ((inputLine = in.readLine()) != null){ //Reads each line, or file path, from the website.
			paths.add(inputLine);
		}
		in.close();
		return paths;
	}
	/**
	 * Gets the href (from HTML) for a particular file.
	 * @param	string	an element of the returned list from getFilePaths
	 * @return	newPath	the actual file path
	 */
	public static String getLink(String string){
		int index = string.indexOf("href");
		if (index == -1) { //Makes sure href is listed at all.
			return null;
		}
		else if(string.substring(index+1).indexOf("href") !=-1) {	//Makes sure href is not listed twice.
			//Acts as a workaround to circular links.
			return null;
		}
		else { //Gets the file path within the <a href = "......."></a> statement.
			String substr = string.substring(index);
			int index2 = substr.indexOf(">");
			String newPath = substr.substring(6, index2-1);
			return newPath;
		}
	}
	/**
	 * Uses recursion to search the list of files for tarballs (tar.gz files). If a particular link in the list is not
	 * a tar.gz file, the method calls itself to go to a deeper sublevel to find a tar.gz.
	 * This accounts for the fact that (on the download page), each cancer cohort has a subfolder which in turn contains all the files
	 * for the cohort.
	 * @param	list		the list of links from getList
	 * @param	urlString	the URL of the webpage
	 * @throws	Exception
	 */
	public static void searchList(List<String> list, String urlString, String downloadPath) throws IOException {
		int count = 1; //For loop counter
		for(String link: list) {
			if (FileConditions.isTarball(link) && !FileConditions.isHash(link)){ //Ignores out files with .md5 and uses files with .tar.gz
				String f = link.toLowerCase();
				//This three-part if-statement deals with files containing or lacking certain keywords
				if (!(FileConditions.isTerm("aux",f) || FileConditions.isTerm("mage", f) || FileConditions.isTerm("ffpe", f))){
					/*
					 * This large if-statement sifts through the files and picks the necessary ones.
					 */
					if(
							(FileConditions.isTerm("hg19",f) && !(FileConditions.isTerm("minus_germline",f)))
							||(FileConditions.isTerm("mutation_packager_oncotated_calls",f))
							||(FileConditions.isTerm("methylation",f) && FileConditions.isTerm("preprocess",f))
							||(FileConditions.isTerm("mir", f) && FileConditions.isTerm("gene_expression",f) && FileConditions.preferHiSeq(f))
							||(FileConditions.isTerm("rnaseq", f) && FileConditions.isTerm("isoforms",f) && !FileConditions.isTerm("normalized",f) && FileConditions.preferHiSeq(f))
							||(FileConditions.isTerm("rnaseq",f) && FileConditions.isTerm("genes",f) && !FileConditions.isTerm("normalized", f) && FileConditions.preferHiSeq(f))
							||(FileConditions.isTerm("junction_quantification",f)&& FileConditions.isTerm("rnaseq",f) && !FileConditions.isTerm("normalized", f) && FileConditions.preferHiSeq(f))
							||(FileConditions.isTerm("rppa_annotatewithgene",f))
							)	{
						URL newURL= new URL(urlString+ getLink(link));
						String subPath = newURL.getPath().substring(31); //Gets the relevant part of the file name.
						String cancer = subPath.substring(0, subPath.indexOf('/'));	//Gets the cancer abbreviation.
						String fileName = subPath.substring(subPath.lastIndexOf('/')); //Gets the file name.
						if(count <= 8){ //If fewer than eight files are downloaded from the current cohort
							System.out.println("File downloaded from: "+subPath+" to: "+fileName);
						}
						else{	//If all files are downloaded from the current cohort
							count = 1; //Reset the count
							System.out.println(cancer+" cohort done.");
						}
						count++;
						Path filePath = Paths.get(downloadPath+ "/" + cancer + fileName); //Creates a file path to download to.
						if(!Files.exists(filePath, LinkOption.NOFOLLOW_LINKS)){
							FileDownloader.downloadUsingStream(newURL.toString(), filePath.toString()); //Downloads the desired file.
						}
					}
				}
			}
			else if(!FileConditions.isHash(link) 
					&& getLink(link) != null 
					&& link.indexOf("Parent Directory")==-1){
				URL subURL = new URL(urlString + getLink(link)); //Gets the URL at a deeper sublevel.
				List<String> arr = getFilePaths(subURL.toString());
				searchList(arr, subURL.toString(),downloadPath); //Calls itself to try again.
			}
		}
	}
	public static void main(String[] args) throws IOException {
		//		String url = "http://gdac.broadinstitute.org/runs/stddata__2016_01_28/data/";
		String urlACC = "http://gdac.broadinstitute.org/runs/stddata__2016_01_28/data/ACC/";
		String downloadPath = "/Users/anthonybao/Desktop/tcga-data";
		searchList(getFilePaths(urlACC), urlACC, downloadPath);
		//		searchList(getFilePaths(url), url, downloadPath);
	}
}