/*********************************************************
 *	File:			Control.java
 *	Date:			12/24/2016
 *	Author:			Yukun Chen
 *
 *	Description:	This class is the control of the algorithm
 *					part of the sound imitation system. Through
 *					this class we can link the calculation
 *					components together and perform the testing
 *					and implementation work. It can also be seen
 *					as the back-end program for user interface.
 ********************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Control{
	public static float[][] recordingData;
	//public static float [][] acousticData, commercialData, singleData, everydayData;
	//public static String [] acousticNames, commercialNames, singleNames, everydayNames;
	//public static String[] recordingNames;
	/*public static Map<String, String> recordingNames = new HashMap<String, String>();
	public static Map<String, String> recordingLocations = new HashMap<String, String>();
	public static Map<String, String> recordingTags = new HashMap<String, String>();
	public static Map<String, Integer> Index2Int = new HashMap<String, Integer>();
	public static String recordingName;
	public static String[] recordingIndex;*/
	public static ArrayList<file> library = new ArrayList<file>();
	public static float[][] CQTtransferMatrix, W1L1, W1L2;
	public static float[] b1L1, b1L2;
	//public static int fileNum = 20;
	public static int firstLevelNeuron = 1000;
	public static int secondLevelNeuron = 600;
	//public static String fileDirection;
	//public int catogory = 0;
	public static file[] record;
	
	public static void getVariables() throws IOException{		//function to initiate all the variables at one time
		System.out.println("Initializing System...");
		/******Data for 4 testing categories***************************
		acousticData = readTXT.getTestingFileMatrix("AcousticInstruments_2.txt", 20, 6*secondLevelNeuron);
		acousticNames = readTXT.getTestingFileName("AcousticInstruments_2.txt", 20, 6*secondLevelNeuron);
		commercialData = readTXT.getTestingFileMatrix("commercialSynthesizers_2.txt", 20, 6*secondLevelNeuron);
		commercialNames = readTXT.getTestingFileName("commercialSynthesizers_2.txt", 20, 6*secondLevelNeuron);
		singleData = readTXT.getTestingFileMatrix("singleSynthesizer_2.txt", 18, 6*secondLevelNeuron);
		singleNames = readTXT.getTestingFileName("singleSynthesizer_2.txt", 18, 6*secondLevelNeuron);
		everydayData = readTXT.getTestingFileMatrix("everyday_2.txt", 59, 6*secondLevelNeuron);
		everydayNames = readTXT.getTestingFileName("everyday_2.txt", 59, 6*secondLevelNeuron);
		recordingData = acousticData;
		recordingNames = acousticNames;
		*************************************************************/
		record = readTXT.initialize("Sound_List.txt", "Character.txt", 854);
		/*recordingIndex = readTXT.getTestingIndex("Sound_List.txt", 854);
		recordingNames = readTXT.getTestingFileName("Sound_List.txt", 854);
		recordingLocations = readTXT.getTestingFileLocation("Sound_List.txt", 854);
		recordingTags = readTXT.getTestingFileTag("Sound_List.txt", 854, recordingNames);
		recordingData = readTXT.getTestingFileMatrix("Character.txt", 854, 6*secondLevelNeuron);
		for(int i=0; i<recordingIndex.length; i++){
			Index2Int.put(recordingIndex[i], i);
		}*/
		CQTtransferMatrix = readTXT.getMatrix("TransferMatrix.txt", 72, 404);
		W1L1 = readTXT.getMatrix("W1L1_2.txt", 1440, firstLevelNeuron);
		W1L2 = readTXT.getMatrix("W1L2_2.txt", firstLevelNeuron, secondLevelNeuron);
		b1L1 = readTXT.getVector("b1L1_2.txt", firstLevelNeuron);
		b1L2 = readTXT.getVector("b1L2_2.txt", secondLevelNeuron);
	}
	
	
	public static void search(String category, String keyword){
		library = new ArrayList<file>();
		for(int i=0; i<record.length; i++){
			if(category.equals("All"))
				category="";
			if(record[i].tag.contains(category)){
				String[] keywords = keyword.split("\\s+");
				boolean contain = true;
				for(int j=0; j<keywords.length; j++){
					if(!record[i].tag.toLowerCase().contains(keywords[j].toLowerCase()))
						contain = false;
				}
				if(contain)
					library.add(record[i]);
			}
		}
	}
	
	public static ArrayList<file> getLibrary(){
		return library;
	}
	
	public static void playSound(file file){
    	PlaySound.setPlayFile(file.location);
    	try {
			MultiThread.play();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/*
	public static void setCatagory(String catagoryName){	//function to set the sound category working on
		switch (catagoryName){
			case "Commercial Synthesizers":
				recordingData = commercialData;
				recordingNames = commercialNames;
				fileDirection = "testingAudios/imitations/commercial synthesizers/";
				fileNum=20;
				break;
			case "Single Synthesizer":
				recordingData = singleData;
				recordingNames = singleNames;
				fileDirection = "testingAudios/imitations/single synthesizer/";
				fileNum=18;
				break;
			case "Everyday":
				recordingData = everydayData;
				recordingNames = everydayNames;
				fileDirection = "testingAudios/imitations/everyday/";
				fileNum=59;
				break;
			default:							//default to acoustic instruments
				recordingData = acousticData;
				recordingNames = acousticNames;
				fileDirection = "testingAudios/imitations/acoustic instruments/";
				fileNum=20;
		}
	}
	*/
	
	/*****getRank() is a function to return the rank of the correct file among all the files within the 
	 same category. It is used for testing the performance of system for this specific imitation file**/
	
	/*
	public static int getRank(String fileName, String recordingName) throws IOException{	
		java.util.Date date= new java.util.Date();
		long time1 = date.getTime();

		Complex[][] soundArray = wavSplitting.wavsplit(fileName);			//split the sound file
		float[][] CQTarray = CQT.getCQT(soundArray, CQTtransferMatrix);		//transfer it to CQT domain
		float[][] neuronArray = neuronNetwork.getneuronArray(CQTarray, W1L1, W1L2, b1L1, b1L2);
		float[] charactorArray = Charactor.getCharactor(neuronArray);		//pass through the neuron network
		float[] distanceValues = new float[recordingNames.length];			//calculate the cosine distance
		HashMap<Float, String> distanceMap = new HashMap<>();		//mapping of distance - name pair;
		HashMap<String, Integer> rankMap = new HashMap<>();			//mapping of name - rank pair;
		for(int i=0; i<recordingNames.length; i++){				//putting data into hash map
			float distance = CosineDistance.getCosionDistance(recordingData[i], charactorArray);
			distanceMap.put(distance, recordingNames[i]);
			distanceValues[i]=distance;
 			}
		java.util.Arrays.sort(distanceValues);		//sort the distance value
		for(int i=recordingNames.length-1; i>=0; i--){
			//System.out.println(distanceValues[i] + "  " + distanceMap.get(distanceValues[i])); //print out the list from the most to the least relevant result and its cosine distance
			rankMap.put(distanceMap.get(distanceValues[i]), fileNum-i);
		}
		return rankMap.get(recordingName);	//return the ranking of the imitation file inputed 
		
		//java.util.Date date2= new java.util.Date();
		//long time2 = date2.getTime();
		//double deltaTime = (time2-time1)/1000.0;
		//System.out.println("Total Execution time: " + deltaTime + "s");		//calculate the time for the whole process	
	}
	*/
	
	//Similar to getRank function, instead, the getList function returns the String array of names from the most to the least relative files
	/*
	public static String[] getList(String fileName, String catagory) throws IOException{
		setCatagory(catagory);
		java.util.Date date= new java.util.Date();
		long time1 = date.getTime();

		Complex[][] soundArray = wavSplitting.wavsplit(fileName);
		float[][] CQTarray = CQT.getCQT(soundArray, CQTtransferMatrix);
		float[][] neuronArray = neuronNetwork.getneuronArray(CQTarray, W1L1, W1L2, b1L1, b1L2);
		float[] charactorArray = Charactor.getCharactor(neuronArray);
		float[] distanceValues = new float[recordingNames.length];
		HashMap<Float, String> distanceMap = new HashMap<>();		//mapping of distance - name pair;
		String[] result = new String [recordingNames.length];
		for(int i=0; i<recordingNames.length; i++){
			float distance = CosineDistance.getCosionDistance(recordingData[i], charactorArray);
			distanceMap.put(distance, recordingNames[i]);
			distanceValues[i]=distance;
			}
		java.util.Arrays.sort(distanceValues);
		for(int i=recordingNames.length-1; i>=0; i--){
			//System.out.println(distanceValues[i] + "  " + distanceMap.get(distanceValues[i]));
			result[fileNum-1-i]=distanceMap.get(distanceValues[i]);
		}
		
		return result;
		
		//java.util.Date date2= new java.util.Date();
		//long time2 = date2.getTime();
		//double deltaTime = (time2-time1)/1000.0;
		//System.out.println("Total Execution time: " + deltaTime + "s");
	}*/
	
	public static void getList() throws IOException{
		Complex[][] soundArray = wavSplitting.wavsplit("RecordAudio.wav");
		float[][] CQTarray = CQT.getCQT(soundArray, CQTtransferMatrix);
		float[][] neuronArray = neuronNetwork.getneuronArray(CQTarray, W1L1, W1L2, b1L1, b1L2);
		float[] charactorArray = Charactor.getCharactor(neuronArray);
		float[] distanceValues = new float[record.length];
		HashMap<Float, file> distanceMap = new HashMap<>();		//mapping of distance - name pair;
		int size = record.length;
		for(int i=0; i<size; i++){
			float distance = CosineDistance.getCosionDistance(record[i].character, charactorArray);
			distanceMap.put(distance, record[i]);
			distanceValues[i]=distance;
			}
		java.util.Arrays.sort(distanceValues);
		record = new file[size];
		for(int i=0; i<size; i++){
			record[i] = distanceMap.get(distanceValues[size-i-1]);
		}
	}
	
	/*
	//The static method is the entrance of testing
	public static void main(String[] args) throws IOException{			//main method for testing
		getVariables();
		
		//CHANGE THIS LINE TO CHANGE THE TESTING CATAGORY
		setCatagory("Acoustic Instruments");			//choose a category of sound to test, selection include Acoustic Instruments, Single Synthesizer, Everyday, Commercial Synthesizers
		
		File[] files = new File(fileDirection).listFiles();	
	    showFiles(files);
	}
	
	public static void showFiles(File[] files) throws IOException {		//Iterate through every imitation file and return the ranking 
	    for (File file : files) {
	        if (file.isDirectory()) {
	            recordingName = file.getName();
	            showFiles(file.listFiles()); // Calls same method again.
	            System.out.println();;
	        } else {
	        	File file2 = new File(fileDirection+recordingName+"/"+file.getName());
	        	if(file2.length()>17500){
	        		int rank = getRank(fileDirection+recordingName+"/"+file.getName(), recordingName+".wav");
	        		System.out.print(rank+" ");
	        	}
	        }
	    }
	}
	*/
}
