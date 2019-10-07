package javaprograms;

public class MissingNumberInAnArray {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
int a [] = {0,1,2,3,4,5,7};
int b=0;
for(int i=a.length-1;i>=0;i--)
{
	 b= b+a[i];
	 System.out.println(b);
}
int sum=0;

for(int j=0;j<=7;j++) {
	sum=sum+j;
	System.out.println(sum);
	
}
	int missingNumber= sum-b;
	System.out.println(missingNumber);
	}

}
