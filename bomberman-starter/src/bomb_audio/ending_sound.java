package bomb_audio;

import java.io.File;

public class ending_sound extends Audio {
	File file = new File( Audio.class.getResource("/bomb_audio/audio_file/ENDING.wav").getFile() );
	public ending_sound() {
		try {
			super.loading_file(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void play(boolean loop) {
		clip.setFramePosition(0);
		if( loop) {		
			clip.loop(clip.LOOP_CONTINUOUSLY);
		}
		else clip.loop(0);
	}
}
