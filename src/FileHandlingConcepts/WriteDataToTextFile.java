package FileHandlingConcepts;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class WriteDataToTextFile {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
FileWriter file =new FileWriter("C:\\Users\\Dell\\Desktop\\TestForJava.txt");
	BufferedWriter bw = new BufferedWriter(file);
	String data = "Welcome to Selenium123";
	bw.write(data);
	bw.close();
	
	
	}
	
	
	

}
