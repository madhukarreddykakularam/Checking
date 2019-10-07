package javaprograms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HowToFindDuplicateElementsInArray {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
String array []= {"java","c","java","ruby","python","ruby"};
int k=0;
for(int i=0;i<array.length;i++) {
	for(int j=i+1;j<array.length;j++) {
		if(array[i].contains(array[j])) {
			
				System.out.println(array[i]);
			}
		}
	}
	//second method correct method to show
Set<String> set= new HashSet<String>();
for(int i=0;i<array.length;i++) {
if(set.add(array[i])==false) {
	System.out.println(array[i]);
}
}
///usong HasMap
Map<String, Integer> map= new HashMap<String, Integer>();
for(String name:array) {
	Integer count =map.get(array);
	if(count==null) {
		map.put(name, 1);
	}
	else {
		map.put(name, ++count);
	}
	
	Set<Entry<String, Integer>> hashs = map.entrySet();
	for(Entry<String, Integer> entry:hashs) {
		if(entry.getValue()>1) {
			System.out.println(entry.getValue());
			System.out.println(entry.getKey());
		}
	}
	
	
	 
}


	}
	}


