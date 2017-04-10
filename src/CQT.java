/********************************************************************
 *	File:			CQT.java
 *	Date:			12/24/2016
 *	Author:			Yukun Chen
 *
 *	Description:	The class is to calculate the CQT matrix by the result 
 *					of FFT matrix. It will put the weight of each FFT bin 
 *					linearly into 72 CQT bins. The output will be a float
 *					matrix size of n*72
 *********************************************************************/

import java.io.IOException;

public class CQT {
	public static float[][] getCQT(Complex[][] soundArray, float[][] transferMatrix) throws IOException{
		Complex data[][] = soundArray;
		float[][] CQT = new float[data.length][72];
		Complex[][] FFTarray = new Complex[data.length][2048];
		
		for(int i=0; i<data.length; i++){
			FFTarray[i] = FFT.fft(data[i]);			//fetch the result of FFT function
			for(int j=0; j<72; j++){
				float sum = 0;
				for(int k=6; k<410; k++){
					float magnitude = FFTarray[i][k].abs();
					sum += magnitude*transferMatrix[j][k-6];	//put the result linearly into CQT bins according to transfer matrix
				}
				CQT[i][j]=(float) sum;
			}
		}
		return(CQT);
	}
}
