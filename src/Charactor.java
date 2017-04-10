/*********************************************************
 *	File:			Charactor.java
 *	Date:			12/24/2016
 *	Author:			Yukun Chen
 *
 *	Description:	This class is used to collect the 
 *					characteristic data from the neural
 *					network matrix. It will compare the 
 *					first, second... to 100th group and 
 *					collect max, min, mean, median, std 
 *					and IR for each group. The output will
 *					be a 600d float vector  
 ********************************************************/

import java.util.Arrays;

public class Charactor {
	public static int secondLevelNeuron = 600;
	
	public static float[] getCharactor(float[][] neuralArray){			//used the neural network array as input
		float[] charactorArray = new float[secondLevelNeuron*6];
		float[][] flippedNeuralArray = Matrix.transpose(neuralArray);	//transpose the matrix to line up the first, second...100th group
		for(int i=0; i<secondLevelNeuron; i++){
			float[] set = flippedNeuralArray[i];
			java.util.Arrays.sort(set);					//for each group, get the following data
			charactorArray[i]     = set[0];				//get max
			charactorArray[secondLevelNeuron+i] = set[set.length-1];	//get min
			float mean = getMean(set);
			charactorArray[2*secondLevelNeuron+i] = mean;				//get mean
			charactorArray[3*secondLevelNeuron+i] = getMedian(set);		//get median
			charactorArray[4*secondLevelNeuron+i] = getStd(set, mean);	//get standard deviation
			charactorArray[5*secondLevelNeuron+i] = getIR(set);			//get internal range
		}
		return charactorArray;
	}
	
	public static float getMean(float[] data){
		float mean = 0;
		for(int i=0; i<data.length; i++){
			mean += data[i];
		}
		mean /= (float)data.length;
		return mean;
	}
	
	public static float getMedian(float[] data){
		float median;
		if(data.length%2==0){
			median = (data[data.length/2-1]+data[data.length/2])/2;
		}
		else{
			median = data[data.length/2];
		}
		return median;
	}
	
	public static float getStd(float[] data, float mean){
		float std=0;
		for(int i=0; i<data.length; i++){
			std += Math.pow(data[i]-mean, 2);
		}
		std = (float) Math.pow(std, 0.5);
		return std;
	}
	
	public static float getIR(float[] data){
		/*float IR;
		int highBound, lowBound;
		if(data.length%2==0){
			highBound = data.length/2-1;
			lowBound = data.length/2;
		}
		else{
			highBound = data.length/2;
			lowBound = highBound;
		}
		IR = getMedian(Arrays.copyOfRange(data, highBound+1, data.length-1))-getMedian(Arrays.copyOfRange(data, 0, lowBound-1));
		return IR;*/
		//System.out.println(data.length);
		return data[data.length/4]-data[data.length*3/4];
	}
}
