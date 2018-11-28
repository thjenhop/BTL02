package bomberman.entities.character.enemy;

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.Entity;
import bomberman.entities.bomb.Flame;
import bomberman.entities.character.Bomber;
import bomberman.entities.character.enemy.ai.AILow;
import bomberman.entities.character.enemy.ai.AIMedium;
import bomberman.graphics.Sprite;

public class Kondoria extends Enemy{
	private static final double speed = Game.BOMBERSPEED /2;
	public Kondoria(int x, int y, Board board) {
		super(x, y, board, Sprite.kondoria_dead, speed, 500);
		
		_sprite = Sprite.kondoria_left1;
		
//		_ai = new AILow();
//		_direction = _ai.calculateDirection();	
		_ai = new AIMedium(_board.getBomber(), this, 3);
		_direction  = _ai.calculateDirection();
	}

	@Override
	protected void chooseSprite() {
		switch(_direction) {
		case 0:
		case 1:
			if(_moving)
				_sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, _animate, 60);
			else
				_sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, _animate, 20);
			break;
		case 2:
		case 3:
			if(_moving)
				_sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, _animate, 60);
			else
				_sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, _animate, 20);
			break;
		}
	}
}
