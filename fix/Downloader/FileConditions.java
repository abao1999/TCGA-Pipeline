package Downloader;
/**
 * Models a series of conditions that are necessary to filter files in Downloader/FileAcquirer.
 */
public class FileConditions {
	/**
	 * Checks if the current line of the list has tar.gz.
	 * @param	line	the name of the current line
	 * @return	boolean	true if line contains .tar.gz, false otherwise
	 */
	public static boolean isTarball(String line){
		return (line.indexOf(".tar.gz") > -1);
	}
	/**
	 * Checks if the current line of the list has md5.
	 * @param	line	the name of the current line
	 * @return	boolean	true if line contains .md5, false otherwise
	 */
	public static boolean isHash(String line){
		return (line.indexOf(".md5") > -1);
	}
	/**
	 * Checks if the current line of the list contains the term.
	 * @param	line	the name of the current line
	 * @return	boolean	true if line contains term, false otherwise
	 */
	public static boolean isTerm(String term, String line){
		return (line.indexOf(term) > -1);
	}
	/**
	 * Checks whether the hiSeq file is preferred over the non-hiSeq counterpart.
	 * @param	line	the name of the current line
	 * @return	boolean	true if line contains .tar.gz, false otherwise
	 */
	public static boolean preferHiSeq(String line){
		if (line.indexOf("illumina") != -1){
			if(line.indexOf("illuminaga") > 0){
				return false; //hiSeq is not preferred over non-hiSeq
			}
			else if (line.indexOf("hiseq")!= -1){
				return true; //hiSeq is preferred
			}
		}
		return true; //else, hiSeq is preferred
	}
}
