package sqlLoaders;

import java.io.*;
import java.util.*;
import java.util.stream.*;



public class FileSearcher {

	String rootPath;
	List<String> files;
	List<String> fileTypes;
	List<String> rCounts;

	public FileSearcher(String path){
		this.rootPath = path;
		this.files = new ArrayList<String>();
		this.fileTypes = new ArrayList<String>();
		this.rCounts = new ArrayList<String>();
	}

	public List<String> addFiles(){

		File rootFile = new File(rootPath);
		File[] fileArr = rootFile.listFiles();

		if(fileArr==null) return new ArrayList<String>();

		else{
			List<File> fileList = Arrays.asList(fileArr);
			List<File> someList = new ArrayList<File>();
			List<File> endList = new ArrayList<File>();
			System.out.println(Arrays.asList(fileList.get(1).listFiles()));
			fileList.stream().map(file -> Arrays.asList(file.listFiles())).collect(Collectors.toList()).forEach(someList::addAll);
			System.out.println();
			someList.stream().map(f -> Arrays.asList(f.listFiles())).collect(Collectors.toList()).forEach(endList::addAll);

			List<String> fileLists = endList.stream().map(p -> p.toString()).collect(Collectors.toList());
			//List<String> isFileList = groups.get(true);
			//List<String> notFileList = groups.get(false);
			//if (notFileList.size() = 0)  return new ArrayList<String>();
			//List<List<String>> returns = notFileList.stream.map().collect()

			this.files = fileLists;
			return fileLists;
		}

	}

	public void writeFiles(String dest) throws IOException{
		BufferedWriter wrt = new BufferedWriter( new FileWriter(dest));
		for(String path: this.files){
			wrt.write(path);
			wrt.newLine();
		}
		wrt.close();

	}
	
	public List<String> addRcounts() throws IOException{
		List<String> counts = new ArrayList<String>();
		for(String f: files){
			LineNumberReader  lnr = new LineNumberReader(new FileReader(f));
			lnr.skip(Long.MAX_VALUE);
			counts.add(Integer.toString(lnr.getLineNumber() + 1)); //Add 1 because line index starts at 0
			// Finally, the LineNumberReader object should be closed to prevent resource leak
			lnr.close();
		}
		return counts;
	}
	
	public List<String> addTypes(){

		ArrayList<String> fileProps = new ArrayList<String>();
		for(String str : files){

			if(str.contains("hg19")){ 
				fileProps.add("CN");
			}
			else if(str.contains("junction")){
				fileProps.add("JnctQuant");

			}
			else if(str.contains("meth")){
				
				if (str.contains("expr")) fileProps.add("Methylation 1");
					
				else fileProps.add("Methylation");;

			}
			else if(str.contains("rppa") && !str.contains("num_genes")){
				fileProps.add("RPPA");

			}
			else if(str.contains("RSEM_genes") && str.contains(".data")){
				fileProps.add("RSEM_gene");

			}
			else if(str.contains("RSEM_isoform") && str.contains(".data")){
				fileProps.add("RSEM_isoform");

			}
			else if(str.contains("mir") && str.contains(".data")){
				fileProps.add("miRexpr");

			}
			else{
				fileProps.add("SNP");

			}
		}
		if (fileProps.size()!=this.files.size()){
			throw new EmptyStackException();
		}
		this.fileTypes = fileProps;
		return fileProps;

	}

	public void printFiles(){
		System.out.println(files);
	}
}
