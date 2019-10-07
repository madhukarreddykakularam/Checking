package InheritanceConcepts;

public class Class1Inheritance {

	int a=10;
	int b=12;
	public int add() {
		
		int c = a+b;
		System.out.println("The value is"+c);
		
		return c;
		
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Class1Inheritance obj = new Class1Inheritance();
		int x = obj.add();
		System.out.println(x);
		
	}

}
