package bomb_audio;

import java.io.File;

public class player_walk extends Audio{
	File file = new File( Audio.class.getResource("/bomb_audio/audio_file/PLAYER_WALK.wav").getFile() );
	int cooldown = 0;
	public player_walk() {
		try {
			super.loading_file(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void play(boolean loop) {
		if( cooldown <= 0) {
			clip.setFramePosition(0);
			if( loop) {		
				clip.loop(clip.LOOP_CONTINUOUSLY);
			}
			else clip.loop(0);
			cooldown = 2;
		}
		else cooldown --;
		
	}
}
