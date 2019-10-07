package FileHandlingConcepts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PracticeSession {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
FileReader read = new FileReader("C:\\Users\\Dell\\Desktop\\TestForJava.txt");
BufferedReader br = new BufferedReader(read);
FileWriter write = new FileWriter("C:\\Users\\Dell\\Desktop\\Testing.txt");
BufferedWriter bw = new BufferedWriter(write);
 String line;
 while((line=br.readLine())!=null)
 {
	 System.out.println(line.length());
	 bw.write(line);
	 String line1 = line.substring(3, 10);
	 String latest = line1.replaceAll(line1, "1231231");
			bw.write(latest); 
			 
	 
			/*
			 * bw.write(line); bw.newLine(); //without new line it will write the data in
			 * one line only
			 */ 
	 }
 
 br.close();
 read.close();
 bw.close();
 write.close();
 
 
	}
}
