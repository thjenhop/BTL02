package bomb_audio;

import java.io.File;

public class life_lost extends Audio {
	File file = new File( Audio.class.getResource("/bomb_audio/audio_file/LIFE_LOST.wav").getFile() );
	public life_lost() {
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
