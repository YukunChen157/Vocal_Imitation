import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class neuralNetwork {
	public static int hopSize = 1;
	public static String fileName = "sound.wav";
	
	public static void main(String[] args) throws IOException{
		java.util.Date date= new java.util.Date();
		long time1 = date.getTime();
		float[][] W1L1 = neuralNetwork.getMatrix("W1L1.txt", 1440, 500);
		float[][] W1L2 = neuralNetwork.getMatrix("W1L2.txt", 500, 100);
		float[]	 b1L1 = neuralNetwork.getVector("b1L1.txt", 500);
		float[]	 b1L2 = neuralNetwork.getVector("b1L2.txt", 100);
		float[][] CQTmatrix = CQT.getCQT(fileName);
		float[][] initialMatrix = new float[(CQTmatrix.length-20)/hopSize][1440];
		for(int i=0; i<(CQTmatrix.length-20)/hopSize; i++){
			for(int j=0; j<20; j++){
				for(int k=0; k<72; k++){
					initialMatrix[i][j*72+k]=CQTmatrix[i*hopSize+j][k];
				}
			}
		}
		float[][] neuralMatrix=new float[initialMatrix.length][100];
		for(int i=0; i<neuralMatrix.length; i++){
			float[] temp = neuralNetwork.sigmoid(Matrix.add(Matrix.multiply(initialMatrix[i], W1L1), b1L1));
			neuralMatrix[i] = neuralNetwork.sigmoid(Matrix.add(Matrix.multiply(temp, W1L2),b1L2));
		}
		java.util.Date date2= new java.util.Date();
		long time2 = date2.getTime();
		for(int i=0; i<neuralMatrix.length; i++){
			for(int j=0; j<100; j++){
				System.out.print(neuralMatrix[i][j]+" ");
			}
			System.out.println();
		}
		double deltaTime = (time2-time1)/1000.0;
		System.out.println("Execution time: " + deltaTime + "s");
	}
	
	public static float[] sigmoid(float[] input){
		float[] output = new float[input.length];
		for(int i=0; i<input.length; i++){
			output[i]=(float) (1/(1+Math.exp(-input[i])));
		}
		return output;
	}
	public static float[][] getMatrix(String name, int row, int column) throws IOException{
		float[][] matrix= new float[row][column];
		String inputFileName = name;
		String [] expressions = new String[row];
		try {
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
		for(int i=0; i<row; i++){
			String[] elements = expressions[i].split("\\s+");
			for(int j=0; j<column; j++){
				matrix[i][j] = Float.parseFloat(elements[j]);
			}
		}
		return matrix;
	}
	
	public static float[] getVector(String name, int row) throws IOException{
		float[] vector= new float[row];
		String inputFileName = name;
		try {
            Scanner in = new Scanner(new File(inputFileName));
            int i=0;
            while (in.hasNextLine()) {
            	vector[i] = Float.parseFloat(in.nextLine());
            	i+=1;
            }
            in.close();
		} catch (FileNotFoundException e) {
            System.out.println("couldn't read file: " + inputFileName);
		}
		return vector;
	}
}
