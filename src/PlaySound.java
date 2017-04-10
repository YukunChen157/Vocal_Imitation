/*********************************************************
 *	File:			playSound.java
 *	Date:			12/24/2016
 *	Author:			Yukun Chen
 *
 *	Description:	This class is used to play the sound
 *					of the wav file inputted
 ********************************************************/

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class PlaySound implements Runnable{
	private final static int BUFFER_SIZE = 128000;
    private static File soundFile;
    private static AudioInputStream audioStream;
    private static AudioFormat audioFormat;
    private static SourceDataLine sourceLine;
    private static String strFilename;
    private static volatile boolean exit = false;
    
    public static void setPlayFile(String filename){
    	strFilename = filename;
    }
    
	public void run(){
		while(!exit){		//if exit is false, keep looping to play the sound
	        try {
	            soundFile = new File(strFilename);
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.exit(1);
	        }
	
	        try {
	            audioStream = AudioSystem.getAudioInputStream(soundFile);
	        } catch (Exception e){
	            e.printStackTrace();
	            System.exit(1);
	        }
	
	        audioFormat = audioStream.getFormat();
	
	        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
	        try {
	            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
	            sourceLine.open(audioFormat);
	        } catch (LineUnavailableException e) {
	            e.printStackTrace();
	            System.exit(1);
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.exit(1);
	        }
	
	        sourceLine.start();
	
	        int nBytesRead = 0;
	        byte[] abData = new byte[BUFFER_SIZE];
	        while (nBytesRead != -1) {
	            try {
	                nBytesRead = audioStream.read(abData, 0, abData.length);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            if (nBytesRead >= 0) {
	                @SuppressWarnings("unused")
	                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
	            }
	        }
	
	        sourceLine.drain();
	        sourceLine.close();
	        exit=true;
		}
    }
	
	public void stop(){	
        exit = true;
    }
	
	public static void setExit(boolean quit){
		exit=quit;
	}
}
