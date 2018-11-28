package bomberman.entities.tile.item;

import bomb_audio.item_get;
import bomberman.Board;
import bomberman.Game;
import bomberman.entities.Entity;
import bomberman.entities.bomb.Flame;
import bomberman.entities.character.Bomber;
import bomberman.graphics.Sprite;

public class SpeedItem extends Item {
	item_get ig = new item_get();
	
	public SpeedItem(int x, int y, Sprite sprite, Board b) {
		super(x, y, sprite, b);
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý Bomber ăn Item
		if( e instanceof Bomber) {
			if( !isRemoved) {
				this.remove();
				isRemoved = true;
				Game.addBomberSpeed(0.5);
				ig.play(false);
			}
			return true;
		}
		if( e instanceof Flame) {
			super.addEnemy(3, 2); // thêm 2 doll
			return false;
		}
		return false;
	}
}