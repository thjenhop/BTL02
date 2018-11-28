package bomb_audio;

import java.io.File;

public class stage_theme extends Audio{
	File file = new File( Audio.class.getResource("/bomb_audio/audio_file/STAGE_THEME_2.wav").getFile() );
	public stage_theme() {
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
	
	public void stop() {
		clip.stop();
	}
}
