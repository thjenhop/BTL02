package bomb_audio;

import java.io.File;

public class noEnemy extends Audio{
	File file = new File( Audio.class.getResource("/bomb_audio/audio_file/GOAL.wav").getFile() );
	public noEnemy() {
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
