package UserDefinedMethods;

import java.util.Random;

public class BiultinMethods {
	public static void main(String[]args) {
		int aaaa = 123;
		Integer abc=new Integer(aaaa);
		System.out.println(abc.equals(123));
int a = -10;
double d= 14.44;
double e=16.66;
double y=33.3567;
Double z= d;
Integer x=a;
System.out.println(x.equals(5));
System.out.println(z.equals(14.44));
System.out.println(Math.abs(a));
System.out.println(Math.abs(-11.33));
System.out.println(Math.min(d, a));
System.out.println(Math.max(d, a));
System.out.println(Math.random());
System.out.println(Math.round(y));
// to crate any random number with particular number of digits then

Random Rand = new Random();
int randomnumber = Rand.nextInt((1000000-999000)+1)+999000;
String asdf = String.valueOf(randomnumber);
System.out.println(randomnumber);






	}

}
