package Parsers;
import java.util.*;
import java.io.*;
import java.util.ArrayList;

public class FieldFilter {
	public static ArrayList<String> getImportantOncFields() throws IOException {
		ArrayList<String> importantFields = new ArrayList<String>();
		importantFields.add("Hugo_Symbol");
		importantFields.add("Entrez_Gene_Id");
		importantFields.add("NCBI_Build");
		importantFields.add("Chromosome");
		importantFields.add("Start_position");
		importantFields.add("End_position");
		importantFields.add("Strand");
		importantFields.add("Variant_Classification");
		importantFields.add("Variant_Type");
		importantFields.add("Reference_Allele");
		importantFields.add("Tumor_Seq_Allele1");
		importantFields.add("Tumor_Seq_Allele2");
		importantFields.add("Tumor_Sample_Barcode");
		importantFields.add("Matched_Norm_Sample_Barcode");
		importantFields.add("Mutation_Status");
		importantFields.add("Sequencing_Phase");
		importantFields.add("Sequence_Source");
		importantFields.add("Validation_Method");
		importantFields.add("Score");
		importantFields.add("BAM_file");
		importantFields.add("Sequencer");
		importantFields.add("Tumor_Sample_UUID");
		importantFields.add("Matched_Norm_Sample_UUID");
		importantFields.add("Protein_Change");
		importantFields.add("Cohort");
		importantFields.add("Data_Type");
		importantFields.add("File_Name");
		importantFields.add("UCSC_Gene_Name");
		return importantFields;
	}
	public static void main(String[] args) throws IOException {
		System.out.println(getImportantOncFields());
	}
}