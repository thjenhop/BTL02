package bomberman.entities.character.enemy;


import bomberman.Board;
import bomberman.Game;
import bomberman.entities.Entity;
import bomberman.entities.bomb.Flame;
import bomberman.entities.character.Bomber;
import bomberman.entities.character.enemy.ai.AIMedium;
import bomberman.graphics.Sprite;

public class Oneal extends Enemy {
	private static final double speed = Game.BOMBERSPEED;
	public Oneal(int x, int y, Board board) {
		super(x, y, board, Sprite.oneal_dead, speed, 200);
		
		_sprite = Sprite.oneal_left1;
		
		_ai = new AIMedium(_board.getBomber(), this, 10);
		_direction  = _ai.calculateDirection();
	}
	
	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
			case 1:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, 60);
				else
					_sprite = Sprite.oneal_left1;
				break;
			case 2:
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, 60);
				else
					_sprite = Sprite.oneal_left1;
				break;
		}
	}
	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý va chạm với Flame
		// TODO: xử lý va chạm với Bomber
		if(e instanceof Bomber) {
			((Bomber) e).kill();
			return false;
		}
		if(e instanceof Flame) {
			kill();
			return false;
		}
		return true;
	}
}