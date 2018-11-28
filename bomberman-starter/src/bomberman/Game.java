package bomberman;

import bomberman.graphics.Screen;
import bomberman.gui.Frame;
import bomberman.gui.InfoPanel;
import bomberman.gui.PauseDialog;
import bomberman.input.Keyboard;
import bomberman.level.FileLevelLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;

import bomb_audio.*;
/**
 * Tạo vòng lặp cho game, lưu trữ một vài tham số cấu hình toàn cục,
 * Gọi phương thức render(), update() cho tất cả các entity
 */
public class Game extends Canvas {

	public static final int TILES_SIZE = 16,                        // độ rộng 1 khung ảnh
							WIDTH = TILES_SIZE * (int)(31 / 2),	    // độ rộng khung frame  240
							HEIGHT = 13 * TILES_SIZE;				// độ cao khung frame   208

	public static int SCALE = 3;
	
	public static final String TITLE = "BombermanGame";
	
	private static final int BOMBRATE = 1;
	private static final int BOMBRADIUS = 1;
	public static final double BOMBERSPEED = 1.0;
	
	public static final int TIME = 200;  // thời gian màn chơi
	public static final int POINTS = 0;
	public static final int LIVES = 3;
	
	protected static int SCREENDELAY = 3;

	protected static int bombRate = BOMBRATE;
	protected static int bombRadius = BOMBRADIUS;
	protected static double bomberSpeed = BOMBERSPEED;
	public static int _bomRate = bombRate;
	
	public static int numberOfLevel = 1; // số lượng màn chơi hiện tại
	public static int timeToPause = 0;
	public static boolean is1time = true;
	
	 
	protected int _screenDelay = SCREENDELAY;
	
	private Keyboard _input;
	private static boolean _running = false;
	private static boolean _paused = true;
	private boolean isStart = true;
	
	private Board _board;
	private Screen screen;
	private Frame _frame;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	start _start = new start();
	stage_theme st = new stage_theme();
	PauseDialog pauseDialog = new PauseDialog(null, this);
	noEnemy ne = new noEnemy();
	
	public Game(Frame frame) {
		_frame = frame;
		_frame.setTitle(TITLE);
		
		screen = new Screen(WIDTH, HEIGHT);
		_input = new Keyboard();
		
		_board = new Board(this, _input, screen);
		addKeyListener(_input);
		updateNumberOfLevels();
		
	}
	private void updateNumberOfLevels() {
		File file = new File( FileLevelLoader.class.getResource("/levels").getFile() );
		String[] y = file.list();
		numberOfLevel = y.length;
	}
	
	private void renderGame() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		_board.render(screen);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen._pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		_board.renderMessages(g);
		
		g.dispose();
		bs.show();
	}
	
	private void renderScreen() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		Graphics g = bs.getDrawGraphics();
		
		_board.drawScreen(g);

		g.dispose();
		bs.show();
	}

	private void update() {
		_input.update();
		_board.update();
	}

	
	public void start(){
//		while( isStart) {
//			_start.play(true); 
//			_board.setShow(5);
//			renderScreen();
//			_input.update();
//			if( _input.space) isStart = false; 
//		}
//		_start.stop();
		
		
		st.play(true);
		_board.setShow(2);
		_running = true;
		long  lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while(_running) { 			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}
			if(_paused) {
				if(_screenDelay <= 0) {
					_board.setShow(-1);
					_paused = false;
				}
				renderScreen();			
			} else {
				renderGame();
			}
				
			
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				_frame.setTime(_board.subtractTime());
				_frame.setPoints(_board.getPoints());
				_frame.setLives(_board.getLives());
				timer += 1000;
				_frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
				updates = 0;
				frames = 0;

				if(_board.getShow() == 2) {
					--_screenDelay;
				}
			}
			if( timeToPause < 0 ) {				
				if( _input.esc) {
					if( isPaused() ) {	
						pauseDialog.setVisible(false);
						getBoard().resume();			
						timeToPause = 200;
					}
					else {
						getBoard().pause();		
						pauseDialog.setVisible(true);
						timeToPause = 200;
					}
					_input.esc = false;
				}
			}
			else {
				if(timeToPause < -1000) timeToPause = 0;
				else timeToPause --;
			}
			if( _board.detectNoEnemies() && is1time ) {
				ne.play(false);
				is1time = false;
			}
		}
	}
	
	public static double getBomberSpeed() {
		return bomberSpeed;
	}
	
	public static int getBombRate() {
		return bombRate;
	}
	
	public static int getBombRadius() {
		return bombRadius;
	}
	
	public static void addBomberSpeed(double i) {
		bomberSpeed += i;
	}
	
	public static void addBombRadius(int i) {
		bombRadius += i;
	}
	
	public static void addBombRate(int i) {
		bombRate += i;
	}
	public static void add_bombRate(int i) {
		_bomRate += i;
	}

	public void resetScreenDelay() {
		_screenDelay = SCREENDELAY;
	}

	public Board getBoard() {
		return _board;
	}

	public boolean isPaused() {
		return _paused;
	}
	
	public void pause() {
		_paused = true;
	}
	public void resume() {
		_running = true;
		_paused = false;
	}
	public void resetPower() {
		bomberSpeed = BOMBERSPEED;
		bombRadius = BOMBRADIUS;
		bombRate = BOMBRATE;
		_bomRate = bombRate;
	}
}