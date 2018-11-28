package bomberman.entities.character;

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.Entity;
import bomberman.entities.bomb.Bomb;
import bomberman.entities.bomb.Flame;
import bomberman.entities.bomb.FlameSegment;
import bomberman.entities.character.enemy.Enemy;
import bomberman.entities.tile.Grass;
import bomberman.entities.tile.Wall;
import bomberman.entities.tile.destroyable.Brick;
import bomberman.entities.tile.item.FlameItem;
import bomberman.entities.tile.item.Item;
import bomberman.graphics.Screen;
import bomberman.graphics.Sprite;
import bomberman.input.Keyboard;
import bomberman.level.Coordinates;
import bomberman.level.LevelLoader;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import bomberman.entities.*;

import bomb_audio.bom_set;
import bomb_audio.life_lost;
import bomb_audio.player_walk;


public class Bomber extends Character {
    private List<Bomb> _bombs;
    protected Keyboard _input;
    player_walk pw = new player_walk();
    bom_set     bs = new bom_set();
    life_lost lf = new life_lost();
    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    protected int _timeBetweenPutBombs = 0;

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
    }

    @Override
    public void update(){
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }

        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;

        animate();

        calculateMove();

        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber
     */
    private void detectPlaceBomb() {
        // TODO: kiểm tra xem phím điều khiển đặt bom có được gõ và giá trị _timeBetweenPutBombs, Game.getBombRate() có thỏa mãn hay không
        // TODO:  Game.getBombRate() sẽ trả về số lượng bom có thể đặt liên tiếp tại thời điểm hiện tại
        // TODO: _timeBetweenPutBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị trí trong 1 khoảng thời gian quá ngắn
        // TODO: nếu 3 điều kiện trên thỏa mãn thì thực hiện đặt bom bằng placeBomb()
        // TODO: sau khi đặt, nhớ giảm số lượng Bomb Rate và reset _timeBetweenPutBombs về 0
    	
    	if( _input.space && _timeBetweenPutBombs < 0 && Game.getBombRate() >0) {
    		int x = getXTile();
    		int y = getYTile();
    		placeBomb(x, y);
    		Game.addBombRate(-1); // value = -1; but set -1 for testing
    		_timeBetweenPutBombs = 20;
    	}
    }

    protected void placeBomb(int x, int y) {
        // TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
    	_board.addBomb(new Bomb(x, y, _board));
    	bs.play(false);
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if ( b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
        if (!_alive) return;
        _alive = false;
        lf.play(false);
        _board.addLives(-1);
        
        if( Game.getBombRate() != Game._bomRate) {
        	Game.addBombRate( -Game.getBombRate());
        	Game.addBombRate(Game._bomRate);
        }
        _timeBetweenPutBombs = 0;
        Message msg = new Message("-1 LIVE", getXMessage(), getYMessage(), 2, Color.white, 14);
		_board.addMessage(msg);
    }

    @Override
    protected void afterKill() {
    	
        if (_timeAfter > 0) --_timeAfter;
        else {
        	if( _board.getLives() == 0) _board.endGame();
        	else {
        		_board.loadLevel(_board.getLevels());
        	}
        }
    }

    @Override
    protected void calculateMove() {
        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
    	
    	double step = Game.getBomberSpeed();
    	if( _input.down || _input.left ||_input.right|| _input.up) {
    		//System.out.print("ksdfl  \n");
	    	if( _input.down ) { //coordinate : pixel
	    		_direction =2;
	    		move(this._x,this._y+step);
	    	}
	    	
	    	if( _input.up) {
	    		_direction = 0;
	    		move(this._x, this._y -step);
	    	}
	    	
	    	if( _input.right) {
	    		_direction = 1;
	    		move(this._x+step, this._y);
	    	}
	    	
	    	if( _input.left) {
	    		_direction = 3;
	    		move(this._x-step, this._y);
	    	} 
	    	_moving = true;
	    	pw.play(false);
    	}
    	else _moving = false;    	
    }

    @Override
    public boolean canMove(double x, double y) {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không	
    	for( int i = 0; i< 4; i++) {
    		double xt = (x + i % 2 * 12)     / Game.TILES_SIZE; //tiles_size = 16 pixels
    		double yt = (y + i / 2 * 13 -14) / Game.TILES_SIZE;
 //   		System.out.println(xt+" "+ yt);
    		Entity a = _board.getEntity(xt, yt,null);
    		if( !a.collide(this)) return false;
    	}
		
		return true;
    }

    @Override
    public void move(double xa, double ya) {
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không và thực hiện thay đổi tọa độ _x, _y
        // TODO: nhớ cập nhật giá trị _direction sau khi di chuyển
    	if( this.canMove(xa,ya)) {
    		this._x = xa;
    		this._y = ya;
    	}
    	
    }

    @Override
    public boolean collide(Entity e) { // va chạm
        // TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Enemy
    	
    	if (e instanceof Flame ) {
    		kill();
    		return false;
    	}
    	if(e instanceof Enemy) {
    		kill();
    		return true;
    	}
        return true;
    }

    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
}