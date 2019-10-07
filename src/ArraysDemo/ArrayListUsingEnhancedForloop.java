package ArraysDemo;

import java.util.ArrayList;
import java.util.Scanner;

public class ArrayListUsingEnhancedForloop {
static String Null1Value=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	ArrayList<String> obj1=new ArrayList<String>();
	obj1.add("Madhu");
	obj1.add("Kar");
	obj1.add("Reddy");
	obj1.add("Kakularam");
	for(String well:obj1) {
		System.out.println(well);
	}
String a= Null1Value;
//String Value = a+"SomeValue";
System.out.println(a.length());
}
}