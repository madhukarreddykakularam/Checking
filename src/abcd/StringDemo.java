package abcd;

public class StringDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
String Name = "Madhu";
String Name1 = "Madhukar";
boolean a =Name.startsWith("M");
System.out.println(a);
boolean ba =Name1.endsWith("kar");
System.out.println(ba);
boolean ssssa =Name1.contains("kar");
System.out.println(ssssa);


String FIrstName = "Madhu-kar -reddy-kakularam";
String[] Mad =FIrstName.split("-");
String z=Mad[1];
System.out.println(z);
for (int i=0;i<Mad.length;i++) {
	String afterTrim=Mad[i].trim();
	//System.out.println(Mad[i]);
	if(afterTrim.equals("kar")) {
		System.out.println(Mad[i]);
		break;
	}
}
	}

}
