import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateCharacter implements Runnable{
	public static String tag;
	public static float[][] CQTtransferMatrix, W1L1, W1L2;
	public static float[] b1L1, b1L2;
	public static int firstLevelNeuron = 1000;
	public static int secondLevelNeuron = 600;
	public static int complete = 0;
	
	public void run() {
		try {
			int n = CreateMultCharacter.getIndex();
			String location = CreateMultCharacter.getLocation();
			CQTtransferMatrix = readTXT.getMatrix("TransferMatrix.txt", 72, 404);
			W1L1 = readTXT.getMatrix("W1L1_2.txt", 1440, firstLevelNeuron);
			W1L2 = readTXT.getMatrix("W1L2_2.txt", firstLevelNeuron, secondLevelNeuron);
			b1L1 = readTXT.getVector("b1L1_2.txt", firstLevelNeuron);
			b1L2 = readTXT.getVector("b1L2_2.txt", secondLevelNeuron);
			build(location, n);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    
	public static void build(String fileDirection, int n) throws IOException{			//main method for testing
		File[] files = new File(fileDirection).listFiles();	
		String fileName = "Character" + n + ".txt";
		File file = new File(fileName);
	    
	    file.createNewFile();
	    FileWriter fstream = new FileWriter(file, true);
	    BufferedWriter writer = new BufferedWriter(fstream);
	    buildFiles(fileDirection, writer, files);
	    writer.close();
	}
	
	public static void buildFiles(String fileDirection, BufferedWriter writer, File[] files) throws IOException {
	    for (File file : files) {
	        if (file.isDirectory()) {
	            tag = file.getName();
	            System.out.println(tag);
	            buildFiles(fileDirection, writer, file.listFiles()); // Calls same method again.
	        } else {
	        	File file2 = new File(fileDirection+"/"+file.getName());
	        	if(file2.length()>17500){
	        	 	writer.write(file.getName().toString().split("\\s+")[0]);
	        	 	Complex[][] soundArray = wavSplitting.wavsplit(fileDirection+"/"+file.getName());
	        		float[][] CQTarray = CQT.getCQT(soundArray, CQTtransferMatrix);
	        		float[][] neuronArray = neuronNetwork.getneuronArray(CQTarray, W1L1, W1L2, b1L1, b1L2);
	        		float[] charactorArray = Charactor.getCharactor(neuronArray);
	        		for(int i=0; i<charactorArray.length; i++){
	        			writer.write(" "+charactorArray[i]);
	        		}
	        	 	writer.newLine();
	        	 	complete++;
	        	 	System.out.println("Complete: " + complete + "  " + file.getName());
	        	}
	        }
	    }
	}

}
