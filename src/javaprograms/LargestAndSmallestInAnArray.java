package javaprograms;

public class LargestAndSmallestInAnArray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
int a[]= {1,3,100,-50};
int largest=a[0];
int smallest=a[0];
//for loop we are taking from 1 because we already used 0
for(int i=1;i<a.length;i++) {
	if(a[i]>largest) {
		largest=a[i];
	}
	else if(a[i]<smallest){
		smallest=a[i];
		
	}
	
}
System.out.println(smallest);
System.out.println(largest);
	}

}
