package javaprograms;

public class PalindromeNumber {
public static int palindrome(int num) {
	//int num=161;
	int rev = 0;
	while(num>0) {
		rev=rev*10+num%10;
		num=num/10;
	}
	//System.out.println(rev);
	return rev;
}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n=162;
int rev =palindrome(n);
if (rev==n) {
	System.out.println("palindrome");
}
else {
	System.out.println("Not Plaindrome");
}
	}

}
