package FileHandlingConcepts;

import java.io.File;
import java.io.IOException;

public class CreateTextfile {

	public static void main(String[] args)throws Exception{
		// TODO Auto-generated method stub
File textobject = new File("C:\\Users\\Dell\\Desktop\\TestFile.xlsx");

try {
	textobject.createNewFile();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}


textobject.delete();
	}

}
