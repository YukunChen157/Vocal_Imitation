/********************************************************************
 *	File:			readTXT.java
 *	Date:			12/24/2016
 *	Author:			Yukun Chen
 *
 *	Description:	The class is used to read different preformatted
 *					txt file and return the data as Java array.
 *********************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class readTXT {
	public static file[] initialize(String fileDirection, String characterDirection, int row){
		file[] sound = new file[row];
		String [] expressions = new String[row];
		try {
            Scanner in = new Scanner(new File(fileDirection));
            int i=0;
            while (in.hasNextLine()) {
            	expressions[i] = in.nextLine();
            	i+=1;
            }
            in.close();
		} catch (FileNotFoundException e) {
            System.out.println("couldn't read file: " + fileDirection);
		} 
		
		String [] char_expressions = new String[row];
		try {
            Scanner in = new Scanner(new File(characterDirection));
            int i=0;
            while (in.hasNextLine()) {
            	char_expressions[i] = in.nextLine();
            	i+=1;
            }
            in.close();
		} catch (FileNotFoundException e) {
            System.out.println("couldn't read file: " + characterDirection);
		}
		
		for(int i=0; i<row; i++){
			file temp = new file();
			String[] name_elements = expressions[i].toString().split("\\.")[0].split("\\s+");
			String[] elements = expressions[i].split("\\s+");
			temp.index = Integer.parseInt(elements[0]);
			
			String name = "";
			for(int j=2; j<name_elements.length; j++){
				name = name+name_elements[j]+" ";
			}
			temp.name = name;
			
			String location = elements[1];
			int j=2;
			while(!elements[j].equals("TAG:")){
				location = location+" "+elements[j];
				j++;
			}
			temp.location = location;
			
			boolean hasTag = false;
			String tag = temp.name;
			for(j=2;j<elements.length;j++){
				if(hasTag)
					tag = tag+" "+elements[j];
				if(elements[j].equals("TAG:"))
					hasTag = true;
			}
			temp.tag = tag;
			
			String[] char_elements = char_expressions[i].split("\\s+");
			temp.character = new float[3600];
			for(j=0; j<char_expressions.length; j++){
				temp.character[j] = Float.parseFloat(char_elements[j+1]);
			}
			
			temp.match = 0;
			
			sound[i]=temp;
		}
		
		return sound;
	}
	//This method is to get the 600 dimension character data of the character file inputed
	//It will return the data in a 2D matrix, where row is a recordings, and column is the 600D character data
	public static float[][] getTestingFileMatrix(String name, int row, int column) throws IOException{
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
		int n=0;
		for(int i=0; i<row; i++){
			String[] elements = expressions[i].split("\\s+");
			for(int j=0; j<expressions.length; j++){
					matrix[i][j] = Float.parseFloat(elements[j+1]);
			}
		}
		return matrix;
	}
	
	//This method is to get the recording names of character file inputed
	//It will return the name in a String array
	public static String[] getTestingIndex(String name, int row) throws IOException{
		String[] Names = new String[row];
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
			Names[i] = elements[0];
		}
		return Names;
	}
	
	public static Map<String, String> getTestingFileName(String direction, int row) throws IOException{
		Map<String, String> Names = new HashMap<String, String>();
		String inputFileName = direction;
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
			String[] elements = expressions[i].toString().split("\\.")[0].split("\\s+");
			String Index = elements[0];
			String name = "";
			for(int j=2; j<elements.length; j++){
				name = name+elements[j]+" ";
			}
			Names.put(Index, name);
		}
		return Names;
	}
	
	public static Map<String, String> getTestingFileLocation(String direction, int row) throws IOException{
		Map<String, String> Locations = new HashMap<String, String>();
		String inputFileName = direction;
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
			String Index = elements[0];
			String name = elements[1];
			int j=2;
			while(!elements[j].equals("TAG:")){
				name = name+" "+elements[j];
				j++;
			}
			Locations.put(Index, name);
		}
		return Locations;
	}
	
	public static Map<String, String> getTestingFileTag(String direction, int row, Map<String, String> recordingNames) throws IOException{
		Map<String, String> Tags = new HashMap<String, String>();
		String inputFileName = direction;
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
			String Index = elements[0];
			String name = recordingNames.get(Index);
			boolean tag = false;
			for(int j=2;j<elements.length;j++){
				if(tag)
					name = name+" "+elements[j];
				if(elements[j].equals("TAG:"))
					tag = true;
			}
			Tags.put(Index, name);
		}
		return Tags;
	}
	
	//This method is to read the a 2D data file and return the data in matrix
	public static float[][] getMatrix(String inputFileName, int row, int column) throws IOException{
		float[][] transferMatrix= new float[row][column];
		String [] expressions = new String[row];
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
		for(int i=0; i<row; i++){
			String[] elements = expressions[i].split("\\s+");
			for(int j=0; j<column; j++){
				transferMatrix[i][j] = Float.parseFloat(elements[j]);
			}
		}
		return transferMatrix;
	}
	
	//This method is to read the a 1D data file and return the data in vector
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
