/**
 * A sample program is to demonstrate how to record sound in Java
 * author: www.codejava.net
 */

import javax.sound.sampled.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
 
public class SoundRecorder implements Runnable{
    // record duration, in milliseconds
	static String status = "start";
    static final long RECORD_TIME = 3000;  // 1 minute
 
    // path of the wav file
    File wavFile = new File("RecordAudio.wav");
 
    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
 
    // the line from which audio data is captured
    TargetDataLine line;
 
    /**
     * Defines an audio format
     */
    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
 
    /**
     * Captures the sound and record into a WAV file
     */
    public void run() {
    	if(status.equals("start")){			//it will keep looping to record when the status is "start"
	        try {
	            AudioFormat format = getAudioFormat();
	            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
	 
	            // checks if system supports the data line
	            if (!AudioSystem.isLineSupported(info)) {
	                System.out.println("Line not supported");
	                System.exit(0);
	            }
	            line = (TargetDataLine) AudioSystem.getLine(info);
	            line.open(format);
	            line.start();   // start capturing
	 
	            AudioInputStream ais = new AudioInputStream(line);
	  
	            // start recording
	            AudioSystem.write(ais, fileType, wavFile);
	 
	        } catch (LineUnavailableException ex) {
	            ex.printStackTrace();
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	        }
    	}
    	else if(status.equals("stop")){
            line.stop();
            line.close();
    	}
    }
 
    /**
     * Closes the target data line to finish capturing and recording
     */
    public void finish() {
        line.stop();
        line.close();
    }
 
    /**
     * Entry to run the program
     * @return 
     * @throws InterruptedException 
     */
    
    public static void setStatus(String temp){
    	status=temp;
    }
    
    public static void main(String[] args) throws InterruptedException {
        final SoundRecorder recorder = new SoundRecorder();
 
        // creates a new thread that waits for a specified
        // of time before stopping
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(RECORD_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                recorder.finish();
            }
        });
 
        stopper.start();
 
        // start recording
        recorder.run();
    }
}