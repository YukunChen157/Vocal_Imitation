/*********************************************************
 *	File:			neuronNetwork.java
 *	Date:			12/24/2016
 *	Author:			Yukun Chen
 *
 *	Description:	This class is to implement the neuron
 *					network. It will take 72*20=1440 elements
 *					as input. 72 is the number of CQT frequency
 *					bins. It will take 20 segments of data
 *					together to form the initial neuron. 
 *					The neuron network has three levels:
 *					1440 - 1000 - 600. So the output will
 *					be a matrix of 600*n
 ********************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class neuronNetwork {
	public static int hopSize = 1;
	public static int secondLevelNeuron = 600;
	public static float[][] getneuronArray(float[][] CQTarray, float[][] W1L1, float[][] W1L2, float[] b1L1, float[] b1L2) throws IOException{
		float[][] CQTmatrix = CQTarray;
		float[][] initialMatrix = new float[(CQTmatrix.length-20)/hopSize][1440];	//The initial matrix has size of 1440*n
		for(int i=0; i<(CQTmatrix.length-20)/hopSize; i++){
			for(int j=0; j<20; j++){
				for(int k=0; k<72; k++){
					initialMatrix[i][j*72+k]=CQTmatrix[i*hopSize+j][k];
				}
			}
		}
		float[][] neuronMatrix=new float[initialMatrix.length][secondLevelNeuron];
		for(int i=0; i<neuronMatrix.length; i++){
			float[] temp = neuronNetwork.sigmoid(Matrix.add(Matrix.multiply(initialMatrix[i], W1L1), b1L1));	//calculation to get the second level neuron
			neuronMatrix[i] = neuronNetwork.sigmoid(Matrix.add(Matrix.multiply(temp, W1L2),b1L2));				//calculation to get the third level neuron
		}
		return neuronMatrix;
	}
	
	public static float[] sigmoid(float[] input){		//sigmoid is an interval function to calculate the transfer between two neuron level
		float[] output = new float[input.length];
		for(int i=0; i<input.length; i++){
			output[i]=(float) (1/(1+Math.exp(-input[i])));
		}
		return output;
	}
}
