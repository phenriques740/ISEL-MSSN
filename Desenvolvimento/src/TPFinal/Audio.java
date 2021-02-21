package TPFinal;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {
	
	private Clip clip;
	private AudioInputStream audioIn;

	public Audio(String soundFile) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
		File f = new File("./" + soundFile);
	    audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
	    this.clip = AudioSystem.getClip();
	    this.clip.open(audioIn);
		
	}
	
	public void startAudio() throws LineUnavailableException, IOException {
	    this.clip.start();
	}
	
	public void stopAudio() {
		this.clip.stop();
		this.clip.close();
	}
	
	public boolean isPlaying() {
		return this.clip.isRunning();
	}
	
	
}










