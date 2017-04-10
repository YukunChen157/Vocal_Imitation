/*********************************************************
 *	File:			wavSplitting.java
 *	Date:			12/24/2016
 *	Author:			Yukun Chen
 *
 *	Description:	This class is used to split the wav file
 *					into float matrix. Each column contains
 *					2048 elements, which includes 512 actual
 *					elements from the file and 1536 zero
 *					padding added at the end. The default 
 *					hopsize is 420 (about 26.25ms).
 ********************************************************/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class wavSplitting {
	public static int hopSize = 420;			//Set the hop size
	
	public static Complex[][] wavsplit(String fileName) throws IOException{
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		byte [] 	byteArray	= 	new byte[(int) file.length()];
		byte [] 	head		= 	new byte[44];
		Complex [][] data		= 	new Complex[(int) ((file.length()-228)/840)][2048];
		int 		pointer 	= 	0;							//pointer of the byte array
		int 		line 		= 	0;
		int			element		=	0;
		fis.read(byteArray);
		fis.close();
		
		while(pointer<44){				//discard the file description header of wav file
			head[pointer]=byteArray[pointer];
			pointer++;
		}
		while(line<data.length){		//read a point of data (2 bytes) and convert into float between -1 to 1
			int temp = wavSplitting.getInt(byteArray, pointer);
			if (temp>32768){
				temp = temp-65536;
			}
			data[line][element] = new Complex((float)((float)temp/32768.0), 0);
			pointer+=2;			
			element++;
			if(element==512){				//adding 1536 zero padding elements at the end of each segment
				for(int i=512; i<2048; i++){
					data[line][i] = new Complex(0, 0);
				}
				pointer -= (512-hopSize)*2;
				element = 0;
				line++;
			}
		}
		/******************************testing*************************************
		System.out.println(line);
		int sum = 0;
		for(int i=0; i<line; i++){
			for(int j=0; j<2048; j++){
				System.out.print(data[i][j].re()+" ");
			}
			System.out.println();
		}
		for(int i=0; i<2048; i++){
			if(data[41][i]!=null)
				sum++;
		}
		System.out.println(sum);
		**************************************************************************/
		return data;
	}
	
	public static int getInt(byte[] arr, int pointer) {				//convert two bytes into an unsigned int
		return arr[pointer+1]<<8 &0xFF00 | arr[pointer]&0xFF;
	}
	
}
