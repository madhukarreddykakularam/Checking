package InheritanceConcepts;

public class Class2Inheritance extends Class1Inheritance {
	
	
	  int a=20; int b=42;
	 

	
	
	
	
	  public int add() {
	  
	  int c = a+b; System.out.println(c); return c; }
	 
	 
	 
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Class2Inheritance obj2 = new Class2Inheritance();
		Class1Inheritance obj11 = new Class1Inheritance ();
		obj11.add();
		obj2.add();
		
		
	}

}
