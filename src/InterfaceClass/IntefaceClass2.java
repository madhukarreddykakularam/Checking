package InterfaceClass;

public class IntefaceClass2 implements InterfaceClass1{

	@Override
	public void engine() {
		// TODO Auto-generated method stub
		System.out.println("Engines");
	}

	@Override
	public void Seat() {
		// TODO Auto-generated method stub
		System.out.println("Seat");
	}

	@Override
	public void Handle() {
		// TODO Auto-generated method stub
		System.out.println("Handle");
	}

	@Override
	public void wheels() {
		// TODO Auto-generated method stub
		System.out.println("wheels");
	}
public static void main(String[]args) {
	IntefaceClass2 obj = new IntefaceClass2();
	obj.engine();
}
}

