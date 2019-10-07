package FileHandlingConcepts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadAFile {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//inorder to handle files create file class not rerured for reading the data
//File FileName = new File("C:\\Users\\Dell\\Desktop\\TestForJava.txt");
String line;	
		//FileReader is used to read a file in read mode
FileReader ReadingFile = new FileReader("C:\\Users\\Dell\\Desktop\\TestForJava.txt");
//Buffered reader is to read the data inside the file
BufferedReader br = new BufferedReader(ReadingFile);
while((line = br.readLine())!=null){
	System.out.println(line);
}
	
	}

}
