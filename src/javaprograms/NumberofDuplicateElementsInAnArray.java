package javaprograms;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class NumberofDuplicateElementsInAnArray {

	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
String array []= {"java","c","java","ruby","python","ruby"};

	//second method correct method to show
Set<String> set= new HashSet<String>();
int count=0;
for(int i=0;i<array.length;i++) {
if(set.add(array[i])==false) {
	count=++count;
	System.out.println(count);
	System.out.println(array[i]);
}
}

}


	}
	


