/********************************************************************
 *	File:			transferMatrix.java
 *	Date:			12/24/2016
 *	Author:			Yukun Chen
 *
 *	Description:	The class is to create the matrix to transfer FFT
 *					matrix to CQT matrix. There are 72 rows, corresponding
 *					to the 72 bins in CQT array. There are 404 columns,
 *					corresponding to the number of frequency we are interested
 *					in.
 *********************************************************************/

import java.io.*;
public class transferMatrix {

   public static void main(String args[])throws IOException {
      File file = new File("TransferMatrix.txt");
      
      file.createNewFile();
      
      FileWriter fstream = new FileWriter(file, true);
      BufferedWriter writer = new BufferedWriter(fstream);
      
      for(float i=0; i<72; i++){
		  float CQT_leftBound = (float)(50.0*Math.pow(2, i/12));
		  float CQT_rightBound = (float)(50.0*Math.pow(2, (i+1)/12));
    	  for(float j=6; j<410; j++){					//only bound the period from 46.9Hz to 3203Hz
    		  float FFT_leftBound = (float)(16000.0/2048.0*j);
    		  float FFT_rightBound = (float)(16000.0/2048.0*(j+1));
    		  if(FFT_rightBound<CQT_leftBound || FFT_leftBound>CQT_rightBound){
    			  writer.write("0");
    			  writer.write(' ');
    		  }
    		  else if(FFT_leftBound<CQT_leftBound && FFT_rightBound<CQT_rightBound){
    			  writer.write(String.valueOf((FFT_rightBound-CQT_leftBound)/(FFT_rightBound-FFT_leftBound)));
    			  writer.write(' ');
    		  }
    		  else if(FFT_leftBound>CQT_leftBound && FFT_rightBound>CQT_rightBound){
    			  writer.write(String.valueOf((CQT_rightBound-FFT_leftBound)/(FFT_rightBound-FFT_leftBound)));
    			  writer.write(' ');
    		  }
    		  else if(FFT_leftBound<CQT_leftBound && FFT_rightBound>CQT_rightBound){
    			  writer.write(String.valueOf((CQT_rightBound-CQT_leftBound)/(FFT_rightBound-FFT_leftBound)));
    			  writer.write(' ');
    		  }
    		  else{
    			  writer.write("1");
    			  writer.write(' ');
    		  }
    	  }
    	  writer.newLine();
      }
      writer.close();    
   }
}