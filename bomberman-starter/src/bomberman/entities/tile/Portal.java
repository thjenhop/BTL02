package bomberman.entities.tile;

import bomberman.*;
import bomberman.entities.Entity;
import bomberman.entities.bomb.Flame;
import bomberman.entities.character.Bomber;
import bomberman.entities.character.Character;
import bomberman.entities.character.enemy.Enemy;
import bomberman.entities.character.enemy.Kondoria;
import bomberman.graphics.Sprite;
import bomberman.level.Coordinates;

public class Portal extends Tile {
	protected Board _board;
	public Portal(int x, int y, Sprite sprite, Board _board) {
		super(x, y, sprite);
		this._board = _board;
	}
	
	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý khi Bomber đi vào
		if( e instanceof Bomber) {
			if( _board.detectNoEnemies() ) {
				_board.nextLevel();
				return true;
			}
			else return true;
		}
		if( e instanceof Enemy) {
			return true;
		}
		if( e instanceof Flame) {
			int xE = (int)_x;
			int yE = (int)_y;
			int _width = _board.getWidth();
			for ( int i = 0; i< 4; i++) {
				_board.addCharacter( new Kondoria(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
				_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
			}
			_board.addEntity( xE + yE * _width, new Portal(xE, yE, Sprite.portal,_board));
		}
		return false;
	}

}