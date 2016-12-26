package Annotations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * Annotates copy number TCGA files by referencing files in the UCSC, RefSeq, and ENSEMBL gene databases.
 * Uses Annotations/CopyNumCleaner to eliminate partially-annotated or non-annotated copy number files.
 * Uses Annotations/Renamer to rename annotated files (marked REVISED2) to their old file names.
 * Uses Annotations/LoadToMemory to add annotations.
 */
public class CopyNumAnnotator {
	//	/**
	//	 * Gets the paths of all copy number files within primaryPath.
	//	 * @param	primaryPath	the path for the primary folder containing cancer cohorts
	//	 * @return	paths		the paths of all copy number files
	//	 */
	//	public static ArrayList<String> getPaths(String primaryPath) {
	//		File primaryFolder = new File(primaryPath);
	//		ArrayList<String> paths = new ArrayList<String>(); //Return value.
	//		ArrayList<File> mainFolders = new ArrayList<File>(Arrays.asList(primaryFolder.listFiles())); //Gets all cancer cohort folders within the primary folder.
	//		for(File mainFolder:mainFolders) {
	//			ArrayList<File> cohortFolders = new ArrayList<File>(Arrays.asList(mainFolder.listFiles())); //Gets all files within a particular cancer cohort.
	//			for(File cohortFolder:cohortFolders) { 
	//				String cohortFolderPath = cohortFolder.getAbsolutePath();
	//				if(!cohortFolderPath.contains("copy_number")) { //Ignores files within a cohort that are not copy number files.
	//					continue;
	//				}
	//				ArrayList<File> copyNumFiles = new ArrayList<File>(Arrays.asList(cohortFolder.listFiles()));
	//				for(File copyNum:copyNumFiles) {
	//					paths.add(copyNum.getAbsolutePath()); //Adds all copy number files within the copy number folder (should be one only).
	//				}
	//			}
	//		}
	//		return paths;
	//	}
	//	/**
	//	 * Adds annotations to a copy number file using UCSC reference data.
	//	 * @param	annotNames		the names of the new annotations' fields
	//	 * @param	root			the file path to the reference data folder
	//	 * @param	folderPath		the file path to the TCGA data folder
	//	 * @param	newFolderPath	the file path to write annotated files to (can be same as folderPath)
	//	 * @param	refPath1		the file path to one of the relevant UCSC reference data files (hg19)
	//	 * @param	refPath2		the file path to the other relevant UCSC reference data file (hg19)
	//	 * @param	refPathAlt1		the file path to one of the relevant UCSC reference data files (hg18)
	//	 * @param	refPathAlt2		the file path to the other relevant UCSC reference data file (hg18)
	//	 * @throws	IOException
	//	 */
	//	public static void addAnnotationsUCSC(String annotNames, String root, String folderPath, String newFolderPath, String refPath1, String refPath2, String refPathAlt1, String refPathAlt2) throws IOException {
	//		ArrayList<ArrayList<String>> arr = LoadToMemory.loadToArrayListUCSC(root+refPath1, root + refPath2);
	//		ArrayList<ArrayList<String>> arrAlt = LoadToMemory.loadToArrayListUCSC(root+refPathAlt1, root + refPathAlt2); //Used with OV, LAML, COADREAD
	//		ArrayList<String> paths = getPaths(folderPath);
	//		for(String path:paths) {
	//			if(path.contains(".txt"))	{
	//				int temp = path.indexOf(folderPath)+folderPath.length();
	//				String newPath = folderPath + path.substring(temp) + ".REVISED.txt"; //Makes a new file path with the name ---.REVISED.txt, indicating it will contain UCSC annotations only
	//				if(path.contains("OV") || path.contains("COADREAD") || path.contains("LAML")) { //Only OV, LAML, and COADREAD use hg18 reference data
	//					LoadToMemory.readCopyNum(path,newPath, annotNames, arrAlt);
	//				}
	//				else {
	//					LoadToMemory.readCopyNum(path,newPath, annotNames, arr);
	//				}
	//				System.out.println(newPath);
	//			}
	//		}
	//	}
	//	/**
	//	 * Adds annotations to a copy number file using RefSeq reference data.
	//	 * @param	annotNames	the names of the new annotations' fields
	//	 * @param	root		the file path to the reference data folder
	//	 * @param	folderPath	the file path to the TCGA data folder
	//	 * @param	refPath	the file path to the relevant RefSeq reference data file (hg19)
	//	 * @param	refPathAlt	the file path to the relevant RefSeq reference data file (hg18)
	//	 * @throws	IOException
	//	 */
	//	public static void addAnnotationsRefSeq(String annotNames, String root, String folderPath, String refPath,String refPathAlt) throws IOException {
	//		ArrayList<ArrayList<String>> arr = LoadToMemory.loadToArrayListRefSeq(root+refPath);
	//		ArrayList<ArrayList<String>> arrAlt = LoadToMemory.loadToArrayListRefSeq(root+refPathAlt);  //Used with OV, LAML, COADREAD
	//		ArrayList<String> paths = getPaths(folderPath);
	//		for(String path:paths) {
	//			if(path.contains("REVISED.txt"))	{
	//				int temp = path.indexOf(".REVISED.txt"); //Gets old file's name
	//				String newPath = path.substring(0,temp)+".REVISED1.txt"; //Makes a new file path with the name ---.REVISED1.txt, indicating it will contain UCSC and RefSeq annotations only
	//				if(path.contains("OV") || path.contains("COADREAD") || path.contains("LAML")) { //Only OV, LAML, and COADREAD use hg18 reference data
	//					LoadToMemory.readCopyNum(path,newPath, annotNames, arrAlt);
	//				}
	//				else {
	//					LoadToMemory.readCopyNum(path,newPath, annotNames, arr);
	//				}
	//				System.out.println(newPath);
	//			}
	//		}
	//	}
	//	/**
	//	 * Adds annotations to a copy number file using ENSEMBL reference data.
	//	 * @param	annotNames	the names of the new annotations' fields
	//	 * @param	root		the file path to the reference data folder
	//	 * @param	folderPath	the file path to the TCGA data folder
	//	 * @param	refPath	the file path to the relevant ENSEMBL reference data file (hg19)
	//	 * @param	refPathAlt	the file path to the relevant ENSEMBL reference data file (hg18)
	//	 * @throws	IOException
	//	 */
	//	public static void addAnnotationsENSEMBL(String annotNames, String root, String folderPath, String refPath, String refPathAlt) throws IOException {
	//		ArrayList<ArrayList<String>> arr = LoadToMemory.loadToArrayListENSEMBL(root+refPath);
	//		ArrayList<ArrayList<String>> arrAlt = LoadToMemory.loadToArrayListENSEMBL(root+refPathAlt);  //Used with OV, LAML, COADREAD
	//		ArrayList<String> paths = getPaths(folderPath);
	//		for(String path:paths) {
	//			if(path.contains("REVISED1.txt"))	{
	//				int temp = path.indexOf(".REVISED1.txt"); //Gets old file's name
	//				String newPath = path.substring(0,temp)+".REVISED2.txt"; //Makes a new file path with the name ---.REVISED2.txt, indicating it will contain all annotations
	//				if(path.contains("OV") || path.contains("COADREAD") || path.contains("LAML")) { //Only OV, LAML, and COADREAD use hg18 reference data
	//					LoadToMemory.readCopyNum(path,newPath, annotNames, arrAlt);
	//				}
	//				else {
	//					LoadToMemory.readCopyNum(path,newPath, annotNames, arr);
	//				}
	//				System.out.println(newPath);
	//			}
	//		}
	//	}
	//	/**
	//	 * Uses the actual file paths to annotate a file with UCSC reference data and delete the files not up-to-date.
	//	 * @param	root			the path to the reference data folder
	//	 * @param	folderPath		the path to the TCGA data folder
	//	 * @param	newFolderPath	the file path to write annotated files to (can be same as folderPath)
	//	 * @throws	IOException
	//	 */
	//	public static void annotateUCSC(String root, String folderPath, String newFolderPath) throws IOException {
	//		String refPath1 = "UCSC_hg19_knownCanonical.txt", refPath2 = "UCSC_hg19_kgXRef.txt"; //Paths for hg19 UCSC reference data
	//		String refPathAlt1 = "UCSC_hg18_knownCanonical.txt", refPathAlt2 = "UCSC_hg18_kgXRef.txt"; //Paths for hg18 RefSeq reference data
	//		String annotNames = "UCSC_Name\tUCSC_Gene_Name"; //Sets the proper fields for UCSC annotations.
	//		addAnnotationsUCSC(annotNames,root,folderPath,newFolderPath,refPath1,refPath2,refPathAlt1,refPathAlt2);
	//		if(folderPath.equals(newFolderPath)) {
	//			CopyNumCleaner.wipe(newFolderPath, "REVISED"); //Deletes files not marked REVISED, or files with only UCSC annotations.
	//															//Only necessary if output folder is identical to input folder
	//		}
	//	}
	//	/**
	//	 * Uses the actual file paths to annotate a file with RefSeq reference data and delete the files not up-to-date.
	//	 * @param	root			the path to the reference data folder
	//	 * @param	folderPath		the path to the TCGA data folder
	//	 * @param	newFolderPath	the file path to write annotated files to (can be same as folderPath)
	//	 * @throws	IOException
	//	 */
	//	public static void annotateRefSeq(String root, String folderPath, String newFolderPath) throws IOException {
	//		String annotNames = "RefSeq_Name\tRefSeq_Gene_Name"; //Sets the proper fields for RefSeq annotations.
	//		String refPath = "RefSeq_hg19_refFlat.txt"; //Path for hg19 RefSeq reference data
	//		String refPathAlt = "RefSeq_hg18_refFlat.txt"; //Path for hg18 RefSeq reference data
	//		addAnnotationsRefSeq(annotNames,root,folderPath,refPath,refPathAlt);
	//		CopyNumCleaner.wipe(newFolderPath, "REVISED1"); //Deletes files not marked REVISED1, or files with only UCSC annotations.
	//	}
	//	/**
	//	 * Uses the actual file paths to annotate a file with ENSEMBL reference data and delete the files not up-to-date.
	//	 * @param	root			the path to the reference data folder
	//	 * @param	folderPath		the path to the TCGA data folder
	//	 * @param	newFolderPath	the file path to write annotated files to (can be same as folderPath)
	//	 * @throws	IOException
	//	 */
	//	public static void annotateENSEMBL(String root, String folderPath, String newFolderPath) throws IOException {
	//		String annotNames = "ENSEMBL_Name\tENSEMBL_Gene_Name";
	//		String refPath = "Ensembl_hg19_ensGene.txt";
	//		String refPathAlt = "Ensembl_hg18_ensGene.txt";
	//		addAnnotationsENSEMBL(annotNames,root,folderPath,refPath,refPathAlt);
	//		CopyNumCleaner.wipe(newFolderPath,"REVISED2"); //Deletes files not marked REVISED2, or files with only UCSC and RefSeq annotations.
	//	}
	//	public static void annotate(String refPath,String folderPath,String newFolderPath) throws IOException {
	//		//Warning: Code will break if the following three lines are switched around.
	//		//annotateUCSC calls addAnnotationsUCSC, which establishes where to put the annotated data
	//		//annotateUCSC(refPath,folderPath,newFolderPath);
	//		//annotateRefSeq(refPath,folderPath,newFolderPath);
	//		//annotateENSEMBL(refPath,folderPath,newFolderPath);
	//		//Renamer.runRenameCopyNum(newFolderPath); //Takes ALL copy number files marked REVISED2 in the TCGA data folder and removes the suffix.
	//	}
}
