package ArraysDemo;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
ArrayList obj1= new ArrayList();
obj1.add(1234567);
obj1.add('M');
obj1.add("Selenium WebDriver");
obj1.add("Selenium WebDriver1");
System.out.println(obj1);
System.out.println(obj1.get(2));
int listSize=obj1.size();
//using for loop
for(int i=0;i<listSize;i++) {
	System.out.println("list is" +obj1.get(i));
}
//using enhanced for loop
for(Object abc:obj1) 
{
	System.out.println(abc);
	
}
///using iterators
Iterator itr = obj1.iterator();
while(itr.hasNext()) 
{
	Object value= itr.next();
	System.out.println("Values in iteration are" +value);
	
	}
	}

}
