package Depivoters;
import java.io.*;
import java.util.*;


/**
 * Reads the file and immediately starts to write...
 */
public class Depivoter	{
	/**
	 * 
	 * @param parseInPath = Path where input files are taken from
	 * @param parseOutPath = Path where output files are sent
	 * @throws IOException
	 */
	public static void depivotAll(String parseInPath, String parseOutPath) throws IOException {
		File mainFolder = new File(parseInPath);
		ArrayList<File> cancerCollection = new ArrayList<File>(Arrays.asList(mainFolder.listFiles()));
		for(File cancer: cancerCollection)	{

			if (cancer.isFile() || cancer.isHidden()) continue;
			System.out.println(cancer.getName());

			ArrayList<File> folders = new ArrayList<File>(Arrays.asList(cancer.listFiles()));

			for(File folder:folders){
				if (folder.isFile()) continue;
				System.out.println("Starting to depivot files in " + folder.getAbsolutePath());
				ArrayList<File> file = new ArrayList<File>(Arrays.asList(folder.listFiles()));
				for(File doc: file){
					System.out.println("Depivoting: " + doc.getAbsolutePath());
					if(!doc.toString().contains("Oncotated") 
							&& !doc.toString().toLowerCase().contains("manifest") 
							&& !doc.toString().toLowerCase().contains("revised")
							&& !doc.toString().endsWith(".png")){
						if(doc.toString().contains("hg19")){ 
							String oldPath  = doc.getParentFile().getParent();
							String path = oldPath.replace(parseInPath, parseOutPath) + File.separator;
							String newPath = path.substring(0, path.lastIndexOf(File.separator));
							path = newPath + File.separator + "copy_number";
							System.out.println(path.toString());
							File filePath = new File(path);
							filePath.mkdirs();

							String oldName = doc.getName();
							String newName = oldName.substring(0, oldName.lastIndexOf('.')+1) + "REVISED" + oldName.substring(oldName.lastIndexOf('.'));

							File writePath = new File(path + File.separator + newName);
							if (writePath.exists()&& writePath.length()>filePath.length()) continue;
							DepivoterCopyNum.depivotCopyNum(doc.toString(), path + File.separator + newName);
						}
						else if(doc.toString().contains("junction")){
							String oldPath  = doc.getParentFile().getParent();
							String path = oldPath.replace(parseInPath, parseOutPath) + File.separator;
							String newPath = path.substring(0, path.lastIndexOf(File.separator));
							path = newPath + File.separator + "junction_quantification";

							System.out.println(path.toString());
							File filePath = new File(path);
							filePath.mkdirs();


							String oldName = doc.getName();
							String newName = oldName.substring(0, oldName.lastIndexOf('.')+1) + "REVISED" + oldName.substring(oldName.lastIndexOf('.'));

							File writePath = new File(path + File.separator + newName);
							if (writePath.exists()&& writePath.length()>filePath.length()) continue;
							DepivoterJunction.depivotJunction(doc.toString(), path + File.separator + newName);
						}
						else if(doc.toString().contains("meth") && !doc.toString().endsWith("num_genes.txt")){
							String oldPath  = doc.getParentFile().getParent();
							String path = oldPath.replace(parseInPath, parseOutPath) + File.separator;
							String newPath = path.substring(0, path.lastIndexOf(File.separator));
							path = newPath + File.separator + "methylation";

							System.out.println(path.toString());
							File filePath = new File(path);
							filePath.mkdirs();

							String oldName = doc.getName();
							String newName = oldName.substring(0, oldName.lastIndexOf('.')+1) + "REVISED" + oldName.substring(oldName.lastIndexOf('.'));
							File writePath = new File(path + File.separator + newName);

							if (writePath.exists()&& writePath.length()>filePath.length()) continue;
							if (oldName.contains("expr")) DepivoterMethylation.readMinCorrelation(doc.toString(), path + File.separator + newName);
							else DepivoterMethylation.depivotMethylation(doc.toString(), path + File.separator + newName);

						}
						else if(doc.toString().contains("rppa") && !doc.toString().contains("num_genes")){
							String oldPath  = doc.getParentFile().getParent();
							String path = oldPath.replace(parseInPath, parseOutPath) + File.separator;
							String newPath = path.substring(0, path.lastIndexOf(File.separator));
							path = newPath + File.separator + "rppa_Annotated_with_gene";

							System.out.println(path.toString());
							File filePath = new File(path);
							filePath.mkdirs();

							String oldName = doc.getName();
							String newName = oldName.substring(0, oldName.lastIndexOf('.')+1) + "REVISED" + oldName.substring(oldName.lastIndexOf('.'));

							File writePath = new File(path + File.separator + newName);
							if (writePath.exists()&& writePath.length()>filePath.length()) continue;
							DepivoterRPPA.depivotRPPA(doc.toString(), path + File.separator + newName);
						}
						else if(doc.toString().contains("RSEM_genes") && doc.toString().contains(".data")){
							String oldPath  = doc.getParentFile().getParent();
							String path = oldPath.replace(parseInPath, parseOutPath) + File.separator;
							String newPath = path.substring(0, path.lastIndexOf(File.separator));
							path = newPath + File.separator + "RSEM_gene_data";

							System.out.println(path.toString());
							File filePath = new File(path);
							filePath.mkdirs();

							String oldName = doc.getName();
							String newName = oldName.substring(0, oldName.lastIndexOf('.')+1) + "REVISED" + oldName.substring(oldName.lastIndexOf('.'));

							File writePath = new File(path + File.separator + newName);
							if (writePath.exists()&& writePath.length()>filePath.length()) continue;
							DepivoterGeneData.depivotGenes(doc.toString(), path + File.separator + newName);
						}
						else if(doc.toString().contains("RSEM_isoform") && doc.toString().contains(".data")){
							String oldPath  = doc.getParentFile().getParent();
							String path = oldPath.replace(parseInPath, parseOutPath) + File.separator;
							String newPath = path.substring(0, path.lastIndexOf(File.separator));
							path = newPath + File.separator + "RSEM_isoform_data";

							System.out.println(path.toString());
							File filePath = new File(path);
							filePath.mkdirs();

							String oldName = doc.getName();
							String newName = oldName.substring(0, oldName.lastIndexOf('.')+1) + "REVISED" + oldName.substring(oldName.lastIndexOf('.'));

							File writePath = new File(path + File.separator + newName);
							if (writePath.exists()&& writePath.length()>filePath.length()) continue;
							DepivoterMRNASeqIsoforms.depivotMRNASeq(doc.toString(), path + File.separator + newName);
						}
						else if(doc.toString().contains("mir") && doc.toString().contains(".data")){
							String oldPath  = doc.getParentFile().getParent();
							String path = oldPath.replace(parseInPath, parseOutPath) + File.separator;
							String newPath = path.substring(0, path.lastIndexOf(File.separator));
							path = newPath + File.separator + "miR_gene_expr";

							System.out.println(path.toString());
							File filePath = new File(path);
							filePath.mkdirs();

							String oldName = doc.getName();
							String newName = oldName.substring(0, oldName.lastIndexOf('.')+1) + "REVISED" + oldName.substring(oldName.lastIndexOf('.'));

							File writePath = new File(path + File.separator + newName);
							if (writePath.exists()&& writePath.length()>filePath.length()) continue;
							DepivoterMiRSeq.depivotMiRSeq(doc.toString(), path + File.separator + newName);
						}
						//Oncotated Files are added to new repo by Parsers/Oncotator.java
					}
				}
			}
		}
	}
	public static void main(String[] args) throws IOException {
		//		String parseInPath = "C:\\Users\\Anthony\\Desktop\\tcga-data1";
		//		String parseOutPath = "C:\\Users\\Anthony\\Desktop\\tcga-data-revisions1";
		String parseInPath = "/Users/anthonybao/Desktop/tcga-data";
		String parseOutPath = "/Users/anthonybao/Desktop/tcga-data-revisions";
		//System.out.println(System.getProperty("os.name").toLowerCase());
		if(System.getProperty("os.name").toLowerCase().contains("windows")){
			parseInPath =parseInPath.replace("/", "\\");
			parseOutPath = parseOutPath.replace("/", "\\");
		}
		depivotAll(parseInPath, parseOutPath);
	}
}