package AbstractionClass;

public abstract class AbstractionClass1GC {
public void Addressproof() 
{
System.out.println("Address proof is correct");	
}
public void PanCard()
{
	System.out.println("Pancard is correct");
}

public abstract void Aadhaarcard();

public abstract void Drivinglicence();

public static void main(String[] args) {
		// TODO Auto-generated method stub
	AbstractionClass2gc obj2 =new AbstractionClass2gc();
	obj2.Aadhaarcard();
	}

}
