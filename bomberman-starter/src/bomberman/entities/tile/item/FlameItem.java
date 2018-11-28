package bomberman.entities.tile.item;

import bomb_audio.item_get;
import bomberman.Board;
import bomberman.Game;
import bomberman.entities.Entity;
import bomberman.entities.bomb.Flame;
import bomberman.entities.character.Bomber;
import bomberman.entities.character.enemy.Balloon;
import bomberman.entities.tile.Grass;
import bomberman.graphics.Sprite;
import bomberman.level.Coordinates;

public class FlameItem extends Item {
	item_get ig = new item_get();
	public FlameItem(int x, int y, Sprite sprite, Board _b) {
		super(x, y, sprite,_b);
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý Bomber ăn Item
		if( e instanceof Bomber) {
			if( !isRemoved) {
				this.remove();
				isRemoved = true;
				Game.addBombRadius(1);
				ig.play(false);
			}
			return true;
		}
		if( e instanceof Flame) {
			super.addEnemy(1, 2); // thêm 2 balloon
			super.addEnemy(2, 1); // thêm 1 oneal
			return false;
		}
		return false;
	}

}