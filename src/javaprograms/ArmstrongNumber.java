package javaprograms;

public class ArmstrongNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

for(int a=1;a<600;a++) {
	String value = String.valueOf(a);
int mul=0;
int r=0;
int temp=a;
int lenght=value.length();
while(temp!=0) {
	r=temp%10;
	temp=temp/10;
	mul=mul+(r*r*r);
		
	
}
if(mul==a) {
	System.out.println("armstrong"+a);
}
}
	}

}
