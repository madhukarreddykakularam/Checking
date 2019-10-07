package javaprograms;

public class PrimeNumber {

	public static boolean prime(int num) {
		if (num<=1) {
		return false;
		}
		for(int i=2;i<num;i++) {
			if(num%i==0) {
				return false;
			}
		}
		
		return true;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
int no;
for(no=0;no<50;no++) {
		System.out.println("the number" +no +prime(no));}
		
	}

}
