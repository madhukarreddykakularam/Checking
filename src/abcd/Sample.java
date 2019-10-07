package abcd;

import java.util.Arrays;

public class Sample {
int a=1000; int b=1200;

	public static void main(String[]args) {
		String a = "abcdefghijklmnopq";
		String b = "SELENIUM";
		System.out.println(a.substring(1, 4));
		System.out.println(b.substring(2, 4));
		System.out.println();
	double f= 13.1333339;
	double z= -10;
	Double e=f;
	System.out.println(Double.compare(f, e));
	System.out.println(e.equals(5.45));
	System.out.println(Math.abs(f));
	System.out.println(Math.abs(f));
	
	char ch ='z';
	char ch1 = '1';
	System.out.println(Character.isUpperCase(ch1));
	System.out.println(Character.isLetter(ch));
	System.out.println(Character.isLetter(ch1));
	System.out.println(Character.isLetter('b'));
	System.out.println(Character.isLetter('*'));
	
	int j [] = {1,2,3};
	System.out.println(j.length);
	for(int i=0;i<j.length;i++) {
	System.out.println(j[i]);
	}
	
	String [] Str1 = {"UFT","Selenium","TExt","Notes"};
	
	System.out.println(Arrays.toString(Str1));
	System.out.println(Arrays.asList(Str1).contains("TExt"));
	
	
	
	
	
	
	}	
}
