package bomberman.entities.tile.item;

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.character.enemy.Balloon;
import bomberman.entities.character.enemy.Doll;
import bomberman.entities.character.enemy.Kondoria;
import bomberman.entities.character.enemy.Oneal;
import bomberman.entities.tile.Grass;
import bomberman.entities.tile.Tile;
import bomberman.graphics.Sprite;
import bomberman.level.Coordinates;

public abstract class Item extends Tile {
	protected boolean isRemoved = false;
	Board _board;
	public Item(int x, int y, Sprite sprite, Board b) {
		super(x, y, sprite);
		_board = b;
	}
	public void addEnemy(int type, int numbers) {
		int xE = (int)_x;
		int yE = (int)_y;
		int _width = _board.getWidth();
		for( int i = 0; i< numbers; i++) {
			switch(type) {
				case 1:{// add ballon
					_board.addCharacter( new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
					_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
				}break;
				case 2: {// add Oneal
					_board.addCharacter( new Oneal(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
					_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
				}break;
				case 3:{ // add doll
					_board.addCharacter( new Doll(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
					_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
				}break;
				case 4:{ // add kondoria
					_board.addCharacter( new Kondoria(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
					_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
				}break;
			}
		}
	}
	
}