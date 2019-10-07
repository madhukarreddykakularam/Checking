package javaprograms;

public class ReversingString {
	public static void main(String[]args) {
		
	String name= "Madhukar Reddy";
String revName = "";

for(int i =name.length()-1;i>=0;i--) {
	revName = revName+name.charAt(i);
	
}
System.out.println(revName);

//another method using string buffer class

StringBuffer sr = new StringBuffer(name);
StringBuffer rev=sr.reverse();
System.out.println(rev);

	}
	
	
}
