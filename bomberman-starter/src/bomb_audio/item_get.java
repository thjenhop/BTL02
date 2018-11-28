package bomb_audio;

import java.io.File;

public class item_get extends Audio {
	File file = new File( Audio.class.getResource("/bomb_audio/audio_file/ITEM_GET.wav").getFile() );
	public item_get() {
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
