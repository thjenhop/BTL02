package bomberman.entities.character.enemy;

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.Entity;
import bomberman.entities.Message;
import bomberman.entities.bomb.Flame;
import bomberman.entities.character.Bomber;
import bomberman.entities.character.Character;
import bomberman.entities.character.enemy.ai.AI;
import bomberman.entities.tile.Grass;
import bomberman.entities.tile.Wall;
import bomberman.entities.tile.destroyable.Brick;
import bomberman.graphics.Screen;
import bomberman.graphics.Sprite;
import bomberman.level.Coordinates;

import java.awt.*;

public abstract class Enemy extends Character {

	protected int _points;
	
	protected double _speed;
	protected AI _ai;

	protected final double MAX_STEPS;
	protected final double rest;
	protected double _steps;
	
	protected int _finalAnimation = 30;
	protected Sprite _deadSprite;
	
	public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
		super(x, y, board);
		
		_points = points;
		_speed = speed;
		
		MAX_STEPS = Game.TILES_SIZE / _speed;
		rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
		_steps = MAX_STEPS;
		
		_timeAfter = 20;
		_deadSprite = dead;
	}
	
	@Override
	public void update() {
		animate();
		
		if(!_alive) {
			afterKill();
			return;
		}
		
		if(_alive)
			calculateMove();
	}
	
	@Override
	public void render(Screen screen) {
		
		if(_alive)
			chooseSprite();
		else {
			if(_timeAfter > 0) {
				_sprite = _deadSprite;
				_animate = 0;
			} else {
				_sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
			}
				
		}
			
		screen.renderEntity((int)_x, (int)_y - _sprite.SIZE, this);
	}
	
	@Override
	public void calculateMove() {
		// TODO: Tính toán hướng đi và di chuyển Enemy theo _ai và cập nhật giá trị cho _direction
		// TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không
		// TODO: sử dụng move() để di chuyển
		// TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
		
		if( _steps <= 0) {
			_direction = _ai.calculateDirection();
			_steps = MAX_STEPS;
		}
		if(_direction == 0) {	
			move(_x, _y - _speed);
		}
		if(_direction == 1) {
			move(_x + _speed, _y);
			
		}
		if(_direction == 2) {
			move(_x, _y + _speed);
		}
		if(_direction == 3) {
			move(_x - _speed, _y);
		}
		
	}
	
	@Override
	public void move(double xa, double ya) {
//		if(!_alive) return;
//		_y += ya;
//		_x += xa;
		if( !_alive) return;
		else if( this.canMove(xa, ya)) {
				_x = xa;
				_y = ya;
		}
	}
	
	@Override
	public boolean canMove(double x, double y) {
		// TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không		
		//return false;
		//return true;
    	for( int i = 0; i< 4; i++) {
    		double xt = (x + i % 2 * 14)     / Game.TILES_SIZE; //tiles_size = 16 pixels
    		double yt = (y + i / 2 * 14 -14) / Game.TILES_SIZE;
    		//System.out.println(xt+" "+ yt);
    		Entity a = _board.getEntity(xt, yt,null);
    		if( !a.collide(this)) {
    			_steps = 0;
    			return false;
    		}
    	}
		_steps -= 1;
		return true;
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
	
	@Override
	public void kill() {
		if(!_alive) return;
		_alive = false;
		
		_board.addPoints(_points);

		Message msg = new Message("+" + _points, getXMessage(), getYMessage(), 2, Color.white, 14);
		_board.addMessage(msg);
	}
	
	
	@Override
	protected void afterKill() {
		if(_timeAfter > 0) --_timeAfter;
		else {
			if(_finalAnimation > 0) --_finalAnimation;
			else
				remove();
		}
	}
	
	protected abstract void chooseSprite();
}