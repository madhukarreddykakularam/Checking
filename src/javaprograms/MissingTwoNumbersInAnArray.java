package javaprograms;

import java.util.HashSet;
import java.util.Set;
import java.util.*; 

public class MissingTwoNumbersInAnArray {

	
	  
	
	static void findTwoMissingNumbers(int arr[], int n)  
	{  
	    // Create a boolean vector of size n+1 and  
	    // mark all present elements of arr[] in it.  
	    boolean []mark = new boolean[n+1];  
	    for (int i = 0; i < n-2; i++)  
	        mark[arr[i]] = true;  
	  
	    // Print two unmarked elements  
	    System.out.println("Two Missing Numbers are");  
	    for (int i = 1; i <= n; i++)  
	    if (! mark[i])  
	        System.out.print(i + " ");  
	  
	    System.out.println(); 
	}  
	  
	// Driver code 
	public static void main(String[] args)  
	{ 
	    int arr[] = {1, 3, 5, 6};  
	  
	    // Range of numbers is 2 plus size of array  
	    int n = 2 + arr.length;  
	  
	    findTwoMissingNumbers(arr, n);  
	} 
	} 

