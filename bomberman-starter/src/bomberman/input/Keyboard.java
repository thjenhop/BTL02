package bomberman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Tiếp nhận và xử lý các sự kiện nhập từ bàn phím
 */
public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean[120]; //120 is enough to this game
	public boolean up, down, left, right, space, esc;
	
	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		space = keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_X];
		esc  = keys[KeyEvent.VK_ESCAPE];
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

//	@Override
//	public void keyPressed(KeyEvent e) {
//		keys[e.getKeyCode()] = true;
//		
//	}
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//		keys[e.getKeyCode()] = false;
//		
//	}
	@Override
	public void keyPressed(KeyEvent e)
	{
		if( e.getKeyCode() == e.VK_ESCAPE) esc = false;
		else keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() != KeyEvent.VK_ESCAPE)
			keys[e.getKeyCode()] = false;
		else esc = true;
	}

}