package javaprograms;

public class ReversingInteger {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
int numbers = 1234506;
int rev=0;
while(numbers!=0) {
	rev=rev*10+numbers % 10;
	numbers=numbers/10;
	//System.out.println(rev);

}

System.out.println(rev);
int web = 76890;
//2nd method using String Buffer
StringBuffer str=new StringBuffer(String.valueOf(web));
System.out.println(str.reverse());


	}
	
}
