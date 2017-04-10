import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class wavSplitting {
//	public static void main(String[] args) throws IOException{
	public static Complex[][] wavsplit(String fileName) throws IOException{
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		byte [] 	byteArray	= 	new byte[(int) file.length()];
		byte [] 	head		= 	new byte[44];
		Complex [][] data		= 	new Complex[(int) (file.length()/1024)][2048];
		int 		pointer 	= 	0;							//pointer of the byte array
		int 		line 		= 	0;
		int			element		=	0;
		fis.read(byteArray);
		fis.close();
		
		while(pointer<44){
			head[pointer]=byteArray[pointer];			//copy the head bytes to head array
			pointer++;
		}
		while(line<data.length){
			int temp = wavSplitting.getInt(byteArray, pointer);
			if (temp>32768){
				temp = temp-65536;
			}
			data[line][element] = new Complex((float)((float)temp/32768.0), 0);
			pointer+=2;
			element++;
			if(element==512){
				for(int i=512; i<2048; i++){
					data[line][i] = new Complex(0, 0);
				}
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
	
	public static int getInt(byte[] arr, int pointer) {				//convert two bytes into an int
		return arr[pointer+1]<<8 &0xFF00 | arr[pointer]&0xFF;
	}
	
}
