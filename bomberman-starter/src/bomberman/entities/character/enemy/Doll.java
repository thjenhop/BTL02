package bomberman.entities.character.enemy;

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.character.enemy.ai.AIMedium;
import bomberman.graphics.Sprite;
public class Doll extends Enemy {
	private static final double speed = Game.BOMBERSPEED *1.3;
	public Doll(int x, int y, Board board) {
		
		super(x, y, board, Sprite.doll_dead, speed, 400);
		
		_sprite = Sprite.doll_left1;
		
		_ai = new AIMedium(_board.getBomber(), this, 5);
		_direction  = _ai.calculateDirection();
	}
	
	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
			case 1:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, _animate, 60);
				else
					//_sprite = Sprite.doll_left1;
					_sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, _animate, 20);
				break;
			case 2:
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, _animate, 60);
				else
					//_sprite = Sprite.doll_left1;
					_sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, _animate, 20);
				break;
		}
	}
}
