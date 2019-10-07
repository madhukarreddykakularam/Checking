package ArraysDemo;

public class ArrayConcepts {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String FullName = "Madhu-kar -reddy-kakularam";
		
		
	String[] FirstName = FullName.split("-");
	int strght = FirstName.length;
	System.out.println(strght);
	String AnyName= FirstName[1];
	for (String SomeName:FirstName) {
		System.out.println(SomeName);
	//}
	for(int i=0;i<FirstName.length;i++) {
		System.out.println(FirstName[i]);
	}
	}
//printing multidimensional array using enhanced for loop
 int [] [] array2={ {2,1,6,8}, {10,12,14,16}};{
 for(int[] evenNumber:array2)
 
 {
	for(int num:evenNumber)
	{
	 System.out.println(array2[1][2]);break;}break;
 } 
 }
}
} 

