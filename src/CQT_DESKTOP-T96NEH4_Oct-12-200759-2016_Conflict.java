import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.sql.Timestamp;
import java.util.Date;

public class CQT {
	public static float[][] getCQT(String fileName) throws IOException{
		java.util.Date date= new java.util.Date();
		long time1 = date.getTime();
		float[][] transferMatrix = CQT.getMatrix();
		Complex data[][] = wavSplitting.wavsplit(fileName);
		float[][] CQT = new float[data.length][72];
		Complex[][] FFTarray = new Complex[data.length][2048];
		for(int i=0; i<data.length; i++){
			FFTarray[i] = FFT.fft(data[i]);
			for(int j=0; j<72; j++){
				float sum = 0;
				for(int k=6; k<410; k++){
					float magnitude = FFTarray[i][k].abs();
					sum += magnitude*transferMatrix[j][k-6];
				}
				CQT[i][j]=(float) sum;
			}
		}
		java.util.Date date2= new java.util.Date();
		long time2 = date2.getTime();
		return(CQT);
		/****************testing output
		for(int j=0; j<72; j++){		
			for(int i=0; i<data.length; i++){
				System.out.print(CQT[i][j]+" ");
			}
			System.out.println(";");
		}
		double deltaTime = (time2-time1)/1000.0;
		System.out.println("Execution time: " + deltaTime + "s");*/
	}
	
	public static float[][] getMatrix() throws IOException{
		float[][] transferMatrix= new float[72][404];
		String inputFileName = "TransferMatrix.txt";
		String [] expressions = new String[72];
		try {															//scan the input file line by line and store it as a string array
            Scanner in = new Scanner(new File(inputFileName));
            int i=0;
            while (in.hasNextLine()) {
            	expressions[i] = in.nextLine();
            	i+=1;
            }
            in.close();
		} catch (FileNotFoundException e) {
            System.out.println("couldn't read file: " + inputFileName);
		}
		for(int i=0; i<72; i++){
			String[] elements = expressions[i].split("\\s+");
			for(int j=0; j<404; j++){
				transferMatrix[i][j] = Float.parseFloat(elements[j]);
			}
		}
		return transferMatrix;
	}
}
