package bomb_audio;

import java.io.File;

public class game_over extends Audio {
	File file = new File( Audio.class.getResource("/bomb_audio/audio_file/GAME_OVER.wav").getFile() );
	public game_over() {
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
