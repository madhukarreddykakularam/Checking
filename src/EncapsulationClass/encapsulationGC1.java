package EncapsulationClass;

public class encapsulationGC1 {

	private String name= "Test Automation";
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String newName)
	{
		name=newName;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		encapsulationGC1 obj = new encapsulationGC1();
		System.out.println(obj.getName());
		//System.out.println(obj.setName("Madhu"));
	}

}
