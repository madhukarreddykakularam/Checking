package FileHandlingConcepts;

import java.io.File;

public class FileHandlingdemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//create a folder
		String path = "C:\\Users\\Dell\\Desktop\\Check";
		File fileobject=new File(path);
		boolean a = fileobject.exists();
//check the existence
		if (a==true) {
			System.out.println("folder exists");
		}else
		{
		fileobject.mkdir();
		System.out.println("file created");
		}
//Delete folder
		fileobject.delete();
	}
	
}
