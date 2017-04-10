/*********************************************************
 *	File:			MultiThread.java
 *	Date:			12/24/2016
 *	Author:			Yukun Chen
 *
 *	Description:	THIS IS THE ENTRANCE OF THE WHOLE PROGRAM!!!
 *					This class is used to divide the components
 *					into three categories: userUnterface, sound
 *					player and sound recorder and run them
 *					individually. By doing this, we are enable
 *					to play/record the sound meanwhile still
 *					getting respond from the UI.
 ********************************************************/

import java.util.ArrayList;

public class MultiThread {
	public static Thread recordThread;			//initiate the thread for the recorder
	public static Thread InterfaceThread = new Thread(new UserInterface());		//initiate the thread for the user interface
	public static Thread playThread;			//initiate the thread for sound player
	public static boolean firstTime = true;		//variable used to detect whether its the first time running the program
	public static void main(String[] args){
		InterfaceThread.start();			//start the interface
	}
	
	public static void startRecord(){;		//when start record is triggered
		SoundRecorder.setStatus("start");	//set status of recorder to make it not jumping out of the loop
		recordThread = new Thread(new SoundRecorder());		//start a new record thread
		recordThread.start();
	}
	
	public static void stopRecord() throws InterruptedException{	//when stop record is triggered
		SoundRecorder.setStatus("stop");	//set status to stop to make it jump out of the loop
		recordThread.run();
		recordThread.interrupt();		//interrupt the thread
	}
	
	@SuppressWarnings("deprecation")
	public static void play() throws InterruptedException{	//When play sound is triggered
		if(firstTime){		//if it's the first time to play a sound, start a thread and play it
			playThread = new Thread(new PlaySound());
			playThread.start();
			firstTime=false;
		}
		else{
			playThread.stop();		//if not the first time, stop the previous thread and play at a new thread
			PlaySound.setExit(false);
			playThread.sleep(10);
			playThread = new Thread(new PlaySound());
			playThread.start();
		}	
	}
	
	public static void stop(){	//when user choose to stop playing a sound
		if(!firstTime){
			playThread.stop();
			PlaySound.setExit(false);	//stop the thread by setting variable to false
		}
	}
}
