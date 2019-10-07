package UserDefinedMethods;

public class GCreddyUserdefinedMethods {

	
	public static void multiply(int a,int b,int c) 
	{
		int output1 = a*b*c;
		System.out.println(output1);
		if(output1>=400) {
			System.out.println("Display rank");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * GCreddyUserdefinedMethods obj = new GCreddyUserdefinedMethods(); int
		 * totalValue = obj.multiply(5, 10, 15); System.out.println(totalValue);
		 */
		GCreddyUserdefinedMethods obj1= new GCreddyUserdefinedMethods();
		obj1.multiply(5, 5, 6);
		multiply(1, 2, 3);
	}

}
