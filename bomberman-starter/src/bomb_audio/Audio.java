package bomb_audio;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import bomberman.*;

abstract class Audio {
	AudioInputStream ais;
	Clip clip = null;	
	
	public void loading_file(File file) throws Exception {
		ais = AudioSystem.getAudioInputStream(file);
		clip = AudioSystem.getClip();
        clip.open(ais);
	}
	public void play(boolean loop) {}
	
//	public void ending_sound() throws Exception {
//		File ending = new File(    Audio.class.getResource("/bomb_audio/ENDING.wav").getFile()   );
//		loading_file(ending, false);
//		clip.start();
//		length = (int) clip.getMicrosecondLength() /1000;
//		Thread.sleep(length);
//	}
//
//	public void stage_theme(int length) throws Exception{
//		File file = new File(    Audio.class.getResource("/bomb_audio/STAGE_THEME.wav").getFile()   );
//		loading_file(file, true);
//		clip.start();
//		this.length = length;
//		Thread.sleep(this.length);
//	}
//	public void bom_set() throws Exception{
//		File file = new File(    Audio.class.getResource("/bomb_audio/BOM_SET.wav").getFile()   );
//		loading_file(file, false);
//		clip.start();
//		length = (int) clip.getMicrosecondLength() /1000;
//		Thread.sleep(length);
//	}
//	public void bom_fire() throws Exception{
//		File file = new File(    Audio.class.getResource("/bomb_audio/BOM_FIRE.wav").getFile()   );
//		loading_file(file, false);
//		clip.start();
//		length = (int) clip.getMicrosecondLength() /1000;
//		Thread.sleep(length);
//	}
//	public void game_over() throws Exception {
//		File file = new File(    Audio.class.getResource("/bomb_audio/GAME_OVER.wav").getFile()   );
//		loading_file(file, false);
//		clip.start();
//		length = (int) clip.getMicrosecondLength() /1000;
//		Thread.sleep(length);
//	}
//	public void life_lost() throws Exception{
//		File file = new File(    Audio.class.getResource("/bomb_audio/LIFE_LOST.wav").getFile()   );
//		loading_file(file, false);
//		clip.start();
//		length = (int) clip.getMicrosecondLength() /1000;
//		Thread.sleep(length);
//	}
//	public void player_walk() throws Exception{
//		File file = new File(    Audio.class.getResource("/bomb_audio/PLAYER_WALK.wav").getFile()   );
//		loading_file(file, false);
//		clip.start();
//		length = (int) clip.getMicrosecondLength() /1000;
//		Thread.sleep(length);
//	}
//	public void start() throws Exception{
//		File file = new File(    Audio.class.getResource("/bomb_audio/START.wav").getFile()   );
//		loading_file(file, false);
//		clip.start();
//		length = (int) clip.getMicrosecondLength() /1000;
//		Thread.sleep(length);
//	}
//	public void item_get() throws Exception{
//		File file = new File(    Audio.class.getResource("/bomb_audio/ITEM_GET.wav").getFile()   );
//		loading_file(file, false);
//		clip.start();
//		length = (int) clip.getMicrosecondLength() /1000;
//		Thread.sleep(length);
//	}
}
