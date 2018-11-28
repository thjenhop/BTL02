package bomberman.entities.character.enemy.ai;

import java.util.Random;

import bomberman.entities.character.Bomber;
import bomberman.entities.character.enemy.Enemy;

public class AIMedium extends AI {
	Bomber _bomber;
	Enemy _e;
	int _range;
	public AIMedium(Bomber bomber, Enemy e, int range) {
		_bomber = bomber;
		_e = e;
		_range = range;
	}

	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đường đi
		
		double x_bomber = _bomber.getX();
		double y_bomber = _bomber.getY();
		double x_enemy  = _e.getX();
		double y_enemy  = _e.getY();
				
		double distance = Math.sqrt(Math.pow((x_bomber - x_enemy),2) + Math.pow((y_bomber - y_enemy),2));
		if( distance >= _range * 16 * Math.sqrt(2)){ // đường chéo ô vuông 10 x 10, 1 ô rộng 16 pixels
			// giống aiLow
			return new AILow().calculateDirection();
		}
		else {
			int ver = random.nextInt(2);
			
			if( ver == 1) { // chiều ngang
				if( x_bomber < x_enemy) return 3;
				else if( x_bomber > x_enemy) return 1;
				else return y(y_bomber, y_enemy);
			}
			else {// theo chiều dọc
				if( y_bomber < y_enemy) return 0;
				else if( y_bomber > y_enemy) return 2;
				else return x(x_bomber, x_enemy);
			}
		}
	}
	private int x( double x_bomber,double x_enemy) {
		if( x_bomber < x_enemy) return 3;
		else return 1;
	}
	private int y(double y_bomber,double y_enemy) {
		if( y_bomber < y_enemy) return 0;
		else return 2;
	}
}