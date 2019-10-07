package stringDemo;

public class CharAtDemo {
	static String Null1Value=null;
	public static void main(String[] args) throws InterruptedException {
		
		// TODO Auto-generated method stub
String Name = "Madhukar";
Thread.sleep(3000);
char aa = Name.charAt(3);
System.out.println(aa);
try {String a= Null1Value;
int num = Integer.parseInt(a);
//String Value = a+"SomeValue";
System.out.println(a.length());
//System.out.println(a);
System.out.println(Name);


}
		catch(NumberFormatException e) {
		System.out.println("Hi");
		System.out.println("error is"+e.getMessage());
		}
try {
int a=10/0;

System.out.println(a);
}
catch(ArithmeticException e){
	System.out.println("error"+e.getMessage());
}	
finally {}
}
}
	
