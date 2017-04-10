import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class CDfileSelect {

	public static void main(String[] args) throws IOException {
		String inputFileName = "SelectedCD.txt";
		try {
			Scanner in = new Scanner(new File(inputFileName));
            while (in.hasNextLine()) {
            	String expressions = in.nextLine();
            	String[] elements = expressions.split("\\s+");
            	File folder = new File("CDs/"+elements[0]);
            	File[] listOfFiles = folder.listFiles();
            	for (int i = 0; i < listOfFiles.length; i++) {
            		String TwoChar = listOfFiles[i].getName().substring(0, Math.min(listOfFiles[i].getName().length(), 2));
            		String TwoChars;
            		if(elements[1].length()==1){
            			TwoChars = elements[1]+" ";
            		}
            		else{
            			TwoChars = elements[1];
            		}
            		String fileName;
            		if(listOfFiles[i].getName().charAt(1)==' '){
            			fileName = elements[0]+"0"+listOfFiles[i].getName();
            		}
            		else{
            			fileName = elements[0]+listOfFiles[i].getName();
            		}
            		
            		if(TwoChar.equals(TwoChars)){
            			System.out.println("Copying: "+listOfFiles[i]);
            			File source = listOfFiles[i];
            			File dest = new File("Samples/"+fileName);
            			copyFile(source, dest);
            		}
            	}
            }
            in.close();
		} catch (FileNotFoundException e) {
            System.out.println("couldn't read file");
		}
	}
	
	private static void copyFile(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        int i=0;
	        while ((length = is.read(buffer)) > 0 && i<2000) {		//trim the file
	            os.write(buffer, 0, length);
	            i++;
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
	
}
