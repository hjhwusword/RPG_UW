package Music;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.*;

import util.Arguments;

public final class MusicPlayer implements Runnable, Observer {
	private String soundFile;
	private boolean repeat;
	
	public MusicPlayer(String soundFile, boolean repeat) {
		Arguments.isNotEmptyOrWhitespace(soundFile);
		this.soundFile = soundFile;
		this.repeat = repeat;
	}
	
	@Override
	public void run() {
	    try {
	    	do {
	    		AudioInputStream stream = AudioSystem.getAudioInputStream(new File(soundFile));
		    	AudioFormat format = stream.getFormat();
		    	if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) { 
		    		System.out.println("Not PCM_SIGNED");
		    		format = new AudioFormat( 
		    				AudioFormat.Encoding.PCM_SIGNED, 
	                        format.getSampleRate(), 
	                        format.getSampleSizeInBits()*2, 
	                        format.getChannels(), 
	                        format.getFrameSize()*2, 
	                        format.getFrameRate(), 
	                        true);        // big endian 
	                stream = AudioSystem.getAudioInputStream(format, stream); 
	            } 
	         
	            // Create line 
	            SourceDataLine.Info info = new DataLine.Info( 
	                SourceDataLine.class, stream.getFormat(), 
	                ((int)stream.getFrameLength()*format.getFrameSize()));
	            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info); 
	            line.open(stream.getFormat()); 
	            line.start(); 
	         
	            // Continuously read and play chunks of audio 
	            int numRead = 0; 
	            byte[] buf = new byte[line.getBufferSize()]; 
	            while ((numRead = stream.read(buf, 0, buf.length)) >= 0) { 
	                int offset = 0; 
	                while (offset < numRead) { 
	                    offset += line.write(buf, offset, numRead-offset); 
	                } 
	            } 
	            line.drain(); 
	            line.stop();
	    	}while (repeat);
	    }catch (Exception e) { 
            e.printStackTrace(); 
            System.exit(1); 
	    }
	    System.out.println("Sound: " + soundFile +" finished playing");
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Thread running " + this.soundFile + "Received Signal");		
	}
}
