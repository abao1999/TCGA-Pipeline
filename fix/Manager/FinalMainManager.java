package Manager;

import java.io.*;
import java.net.URL;
import java.util.*;
import Annotations.*;
import Depivoters.*;
import Downloader.*;
import Parsers.*;
import Unzippers.*;
/**
 * Main manager for all the code.
 *
 */
public class FinalMainManager {
	private static Scanner scanner = new Scanner(System.in);
	private static Scanner scanner2 = new Scanner(System.in);
	private static Scanner scanner2_1 = new Scanner(System.in);
	public static ArrayList<String> getCohorts() throws IOException	{
		ArrayList<String> cohort = new ArrayList<String>();
		Collections.addAll(cohort,
				"ACC",
				"BRCA",
				"CESC",
				"CHOL",
				"COAD",
				"COADREAD",
				"DLBC",
				"ESCA",
				"FPPP",
				"GBM",
				"GBMLGG",
				"HNSC",
				"KICH",
				"KIPAN",
				"KIRC",
				"KIRP",
				"LAML",
				"LGG",
				"LIHC",
				"LUAD",
				"LUSC",
				"MESO",
				"OV",
				"PAAD",
				"PCPG",
				"PRAD",
				"READ",
				"SARC",
				"SKCM",
				"STAD",
				"STES",
				"TGCT",
				"THCA",
				"THYM",
				"UCEC",
				"UCS",
				"UVM"
				);
		return cohort;
	}
	public static void performTask1(String downloadSite, String downloadPath, String parseInPath, String parseOutPath) throws IOException {
		//		//Gets download path for TCGA data.
		//		//Downloads TCGA data to the specified location.
		//		System.out.println("Please enter the number corresponding to your choice.\n"
		//				+ "1: Download all TCGA data.\n"
		//				+ "2: Download a specific cancer cohort.");
		//		int downloadChoice = scanner2.nextInt();
		//		if(downloadChoice==1) {
		//			List<String> downloadPaths = FileAcquirer.getFilePaths(downloadSite);
		//			System.out.println("Downloading Cancer Genome Atlas data from <a href=\"" + downloadSite + "\"</a> to " + downloadPath);
		//			FileAcquirer.searchList(downloadPaths,downloadSite,downloadPath);
		//			System.out.println("Downloading process finished");
		//		}
		//		//in future add choice to download several
		//		else if(downloadChoice==2)	{
		//			ArrayList<String> cohorts = getCohorts();
		//			System.out.println("Please enter the cancer cohort you want to download. Available cohorts are:");
		//			System.out.println(cohorts);
		//			String cohortDownloadChoice = scanner2_1.nextLine().toLowerCase().trim();
		//			if(cohorts.contains(cohortDownloadChoice.toUpperCase()))	{
		//				downloadSite += cohortDownloadChoice.toUpperCase().trim() + "/";
		//				List<String> downloadPaths = FileAcquirer.getFilePaths(downloadSite);
		//				System.out.println("Downloading Cancer Genome Atlas data from <a href=\"" + downloadSite + "\"</a> to " + downloadPath);
		//				FileAcquirer.searchList(downloadPaths,downloadSite,downloadPath);
		//				System.out.println("Downloading process finished: Downloaded " + cohortDownloadChoice.toUpperCase() + " cohort");
		//			}
		//			else {
		//				System.out.println(cohortDownloadChoice + " is not a valid cancer cohort");
		//				performTask1(downloadSite, downloadPath, parseInPath, parseOutPath);
		//			}
		//		}
		//		else {
		//			System.out.println("Try again");
		//			performTask1(downloadSite, downloadPath, parseInPath, parseOutPath);
		//		}
		//		//Unzips all files and converts important ones to .txt files
		//		System.out.println("Starting unzipping process");
		//		Unzipper.getTextFiles(downloadPath);
		//Reverses the pivoted format of the TCGA data
		System.out.println("Starting depivoting process");
		if(System.getProperty("os.name").toLowerCase().contains("windows")){
			parseInPath =parseInPath.replace("/", "\\");
			parseOutPath = parseOutPath.replace("/", "\\");
		}
		Depivoter.depivotAll(parseInPath, parseOutPath);
		System.out.println("Depivoting finished");
		System.out.println("Cleaning out unnecessary files");
		Cleaner.wipe(parseOutPath, ".REVISED");
		System.out.println("Finished cleaning files");
	}
	public static void performTask2(String refPath, String parseInPath, String parseOutPath) throws IOException	{
		if(!parseOutPath.equals(parseInPath)) { //Makes new directory for parsed data if necessary.
			new File(parseOutPath).mkdir();
		}
		//Starts parsing data.
		//		System.out.println("Starting Oncotated file processing");
		//		Oncotator.scanOnc2(parseInPath,parseOutPath);
		//		System.out.println("Finished Oncotated file processing");
		//Re-writes the data files.
		System.out.println("Starting to re-write all files");
		Oncotator2.runOncotator2(parseOutPath);
		System.out.println("Finished re-writing all files");
		if(parseInPath.equals(parseOutPath)) {
			System.out.println("Cleaning out unnecessary files");
			Cleaner.wipe(refPath, ".REVISED");
			System.out.println("Finished cleaning files");
		}

	}
	public static void performTask3(String refPath, String annotInPath, String annotOutPath) throws IOException	{
		//Downloads reference data
		//Gets download path for reference data.
		//		new File(refPath).mkdir();
		//Quick patch... ok since we only want UCSC ref data anyways...
		FileDownloader.downloadRefData(refPath+"/UCSC");
		System.out.println("Reference data downloaded");
		//Unzips reference data.
		System.out.println("Starting unzipping process");
		Unzipper.getTextFiles(refPath);
		System.out.println("All reference data unzipped");
		//		new File(annotOutPath).mkdir();
		//Annotates files using reference data.
		System.out.println("Annotating Oncotated files");
		//In the future, could add 3rd field in this method, annotOutPath
		OncAnnotator.annotate(refPath + "/UCSC",annotInPath);
		System.out.println("Finished annotating Oncotated files");
		System.out.println("Annotating copy number files");
		//		//In the future, could add 3rd field in this method, annotOutPath
		//		CopyNumAnnotator.annotate(refPath,annotInPath); 
		//		System.out.println("Finished annotating copy number files");
		System.out.println("Task finished, TCGA data fully processed.");
	}
	/**
	 * Manages all the code.
	 * @param args
	 * @throws IOException
	 */ 
	public static void main(String[] args) throws IOException {
		//fall-back if config.properties file is missing
		//args = new String[]{"hi", "bye", "die"};
		//Gets the configurations file path.
		String downloadPath = "";
		String refPath = "";
		String parseOutPath = "";
		String downloadSite = "";
		Properties prop = new Properties();
		try {
			//Production Config
			File jarPath=new File(FinalMainManager.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String propertiesPath=jarPath.getParent();
			//			//Development/Testing Config
			//			String propertiesPath = "/Users/anthonybao/Desktop/JavaWorkspace/tcga/src";
			propertiesPath+="/config.properties";
			System.out.println("config.properties file path: " + propertiesPath);
			prop.load(new FileInputStream(propertiesPath));
			downloadPath = prop.getProperty("downloadPath");
			refPath = prop.getProperty("refPath");
			parseOutPath = prop.getProperty("parseOutPath");
			downloadSite = prop.getProperty("downloadSite");
			System.out.println("TCGA data will be downloaded from: " + downloadSite);
			System.out.println("Data files will be downloaded into: " + downloadPath);
			System.out.println("Reference data will be downloaded into: " + refPath);
			System.out.println("Revised TCGA data files will be written into: " + parseOutPath);
		}
		catch(FileNotFoundException e) { //Command-line fallback if the config file is missing
			downloadPath = args[0];
			refPath = args[1];
			parseOutPath = args[2];
			downloadSite = args[3];
		}
		catch(IndexOutOfBoundsException e2) {
			System.out.println("Error with entering config file overrides");
			System.out.println("Make sure all seven file paths were entered properly");
		}
		//Finds the website to download TCGA data from.
		//Default option is the website the authors used to test the code.
		System.out.println("Reminder: Before executing, please verify that the config.properties file is properly set up.");
		System.out.println("Please enter 'start' to begin: ");
		String response1 = scanner.nextLine().toLowerCase().trim();
		if(response1.equals("start"))	{
			//Acquisition Task: download data from downloadSite and convert files into .txt files
			performTask1(downloadSite, downloadPath, downloadPath, parseOutPath);
			//Parser Task: Rewrite oncotator files, modify files to add Cohort, Data_Type, File_Name fields, and to normalize
			performTask2(downloadPath, downloadPath, parseOutPath);
			//			//Annotation Task: Reference UCSC reference data to add annotations to oncotator files
			//			performTask3(refPath, parseOutPath, parseOutPath);	
		}
		else {
			System.out.println("Please configure your config.properties file");
			main(args);
		}
	}
}
/**
 * OPEN ISSUES:
 */