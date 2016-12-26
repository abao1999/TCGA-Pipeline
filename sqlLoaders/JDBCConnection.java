package sqlLoaders;

import java.io.*;
import java.util.*;
//STEP 1. Import required packages
import java.sql.*;

public class JDBCConnection {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "password";

	public static void main(String[] args) throws IOException {
		
		FileSearcher k = new FileSearcher("\\\\sii-nas3/Data/tcga-data-revisions");
		k.addFiles();
		System.out.println();
		BufferedWriter wrt = new BufferedWriter( new FileWriter( "C:/Users/localadmin/Desktop/fun.txt"));
		k.addTypes();
		for(int i = 0; i < k.files.size(); i++){
			wrt.write(k.files.get(i) + "\t" + k.fileTypes.get(i) + "\t" + k.rCounts);
			wrt.newLine();
		}
		wrt.close();
		
		
		/*Connection conn = null;
		Statement stmt = null;
		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			//STEP 4: Execute a query
			System.out.println("Creating database...");
			stmt = conn.createStatement();

			String sql = "CREATE DATABASE STUDENTS";
			stmt.executeUpdate(sql);
			System.out.println("Database created successfully...");
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try
		System.out.println("Goodbye!");
		*/
	}//end main
}//end JDBCExample
