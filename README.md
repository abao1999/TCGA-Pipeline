# TCGA_Pipeline_Final
This is the final version of TCGA_Pipeline.
Just need to make it more usable with command line stuff, fix issues and bugs if there are any that show up

Normalized Level 2 TCGA Data

TCGA data was acquired from: http://gdac.broadinstitute.org/runs/stddata__2016_01_28/data/

##TO-DO LIST
* Command line options

##INSTRUCTIONS
* **IMPORTANT: The config.properties file must be configured before running the TCGA_Final.jar**

**1. Download tcga.zip**
* Unzip this file to get a folder called tcga with tcga.jar and config.properties in it
         
**2. Modify config.properties**
* The variables in the config.properties file must be modified to match your desired download path, reference data path, parse output path, and download site. The default values for these variables are included in the config.properties file as shown below:
* **(WARNING: If the order of these config file fields is scrambled, then Manager/FinalMainManager MAY FAIL.)**
   
```
      downloadPath=/Users/ThisUser/Documents/tcga-data
      refPath=/Users/ThisUser/Documents/tcga-ref-data
      parseOutPath=/Users/ThisUser/Documents/tcga-data-revisions
      downloadSite=http//gdac.broadinstitute.org/runs/stddata__2016_01_28/data/
```
* Description of variables:
   * downloadPath is the path of the destination directory for data downloaded from downloadSite.
   * refPath is the path to the reference data directory.
   * parseOutPath is the parse output path, where all revised ouput files will be written into.
   * downloadSite is the website that the data is downloaded from.

**3. Run the Jar File**
* After configuring the config.properties file, cd to the folder tcga and run the JAR file on terminal
      * `$ java -jar tcga.jar`
         * The JAR file will reference config.properties, so do not attempt to move/rename config.properties or the JAR file. Renaming the folder name from tcga to something else is fine
         
## EXECUTION PROCEDURE
* All code is run under Manager/FinalMainManager.java in the following sequence.
* Acquisition Task
  * Description of Task:
    * Download the TCGA data, put it into a directory, and get the .txt text files from it
  * Downloader/FileAcquirer.java
    * Referenced:
       * Downloader/FileConditions.java
       * Downloader/FileDownloader.java
  * Unzippers/Unzipper.java
    * Referenced:
      * Unzippers/GZipFile.java
      * Unzippers/Untar.java
      * Unzippers/Wiper.java
  * Depivoters/Depivoter.java
    * Referenced:
       * Depivoters/DepivoterCopyNum.java
       * Depivoters/DepivoterGeneData.java
       * Depivoters/DepivoterJunction.java
       * Depivoters/DepivoterMethylation.java
       * Depivoters/DepivoterMiRSeq.java
       * Depivoters/DepivoterMRNASeqIsoforms.java
       * Depivoters/DepivoterRPPA.java
* Parsing Task
   * Description of Task
      * We want to add Cohort, Data_Type, File_Name fields to all the files in order to make them more understandable
   * Parsers/Oncotator.java
      * Referenced:
         * Parsers/FieldFilter.java
         * Used to reference Parsers/CommonFields.java but not anymore
   * Parsers/Oncotator2.java
      * Referenced:
         * Used to reference Parsers/CommonFields.java but not anymore
      * addCohorts
      * addDataType
         * Data_Type mapping:
            * copy_number --> CN
            * junction_quantification --> JnctQuant
            * methylation --> Methylation
            * miR_gene_expr --> miRexpr
            * Oncotated --> SNP
            * rppa_Annotated_with_gene --> RPPA
            * RSEM_gene_data --> RSEM_gene
            * RSEM_isoform_data --> RSEM_isoform
            * clinical --> Clinical
      * addFileName
   * Parsers/Cleaner.java
* Annotation Task
   * Description of Task:
      * Look at extract of gene and transcript data of TCGA data
      * We have many files with different field names for the same type of data and we want to extract this data
      * For all data types where we have
         * chr+pos
         * chr+start+end
      * Use the hg
         * hg18: `OV`, `COAD`/`READ`/`LAML`
         * hg19: all others
      * Reference data sets:
         * UCSC --> `UCSC_hgXX_knownCanonical`, `UCSC_hgXX_knownGene`, `UCSC_hgXX_kgXRef`
      * Cross reference among all the reference data files we want to merge the following data fields in an arraylist (separately):
         * geneName
   * Annotations/OncAnnotator.java
      * Referenced:
         * Annotations/OncCleaner.java
         * Annotations/LoadToMemory.java
           * UCSC
           * New fields are created in Oncotator files. These fields and data in the fields are from reference data ex: UCSC_name, UCSC_gene_name
         * Annotations/Renamer.java

##STRUCTURE
* Main class is Manager/FinalMainManager.java
  * Referenced:
    * All classes in the repository
* Configuration file is config.properties
  * Contains default user input values
    * Download path, directory path, tasks, etc...
* Jar file is tcga.jar, which is located inside tcga.zip
* File tree visual representation:
  * **BOLDED Names are `main classes` that reference all other classes in its folder**
  *  ~~Striken-Through Names are classes that are no longer needed, but kept anyways~~

TCGA_Pipeline_Final
<br>|---- Annotations
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- ~~CopyNumAnnotator.java~~
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- ~~CopyNumCleaner.java~~
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- LoadToMemory.java
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- **OncAnnotator.java**
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- OncCleaner.java
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- Renamer.java
<br>|---- Depivoters
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- **Depivoter.java**
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- DepivoterCopyNum.java
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- DepivoterGeneData.java
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- DepivoterJunction.java
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- DepivoterMRNASeqIsoforms.java
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- DepivoterMethylation.java
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- DepivoterMiRSeq.java
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- DepivoterRPPA.java
<br>|---- Downloader
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- **FileAcquirer.java**
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- FileConditions.java
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- FileDownloader.java
<br>|---- Manager
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- **FinalMainManager.java**
<br>|---- Parsers
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- Cleaner.java
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- ~~CommonFields.java~~
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- FieldFilter.java
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- FileComparer.java
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- OncFieldSweeper.java
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- **Oncotator.java**
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- **Oncotator2.java**
<br>|---- Unzippers
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- GZipFile.java
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- Untar.java
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- **Unzipper.java**
<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|---- Wiper.java

##DEPENDENCIES
* Java
* commons-compress-1.12-bin
   * https://commons.apache.org/proper/commons-compress/download_compress.cgi
      * download the `commons-compress-1.12-bin.zip`
