package InterfaceClass;

public interface InterfaceClass1 {

	public void engine();
	public void Seat();
	public void Handle();
	public void wheels();
public static void main(String[]args) {
	//if we want to use any methodsin interface then create class for the class where we implemented the abstract methods
	
	IntefaceClass2 obj = new IntefaceClass2();
	obj.engine();
}
}
