import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateContent {
	public static String tag;
	
	public static void main(String[] args) throws IOException {
		String fileDirection = "Samples";
		File[] files = new File(fileDirection).listFiles();	
		File file = new File("fileList.txt");
		file.delete();							//delete previous version
		file = new File("fileList.txt");
	    
	    file.createNewFile();
	    FileWriter fstream = new FileWriter(file, true);
	    BufferedWriter writer = new BufferedWriter(fstream);
	    showFiles(fileDirection, writer, files);
	    writer.close();
	}
	
	public static void showFiles(String fileDirection, BufferedWriter writer, File[] files) throws IOException {		//Iterate through every imitation file and return the ranking 
	    for (File file : files) {
	        if (file.isDirectory()) {
	            tag = file.getName();
	            showFiles(fileDirection, writer, file.listFiles()); // Calls same method again.
	        } else {
	        	File file2 = new File(fileDirection+"/"+file.getName());
	        	if(file2.length()>17500){
	        	 	writer.write(file.getName().toString().substring(0,6)+" ");
	        	 	writer.write(fileDirection+"/"+file.getName() + " TAG: ");
	        	 	//writer.write(" tag:"+tag);
	        	 	writer.newLine();
	        	}
	        }
	    }
	}

}
