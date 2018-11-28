package bomberman.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.LayeredEntity;
import bomberman.entities.bomb.Bomb;
import bomberman.entities.character.Bomber;
import bomberman.entities.character.enemy.Balloon;
import bomberman.entities.character.enemy.Doll;
import bomberman.entities.character.enemy.Kondoria;
import bomberman.entities.character.enemy.Oneal;
import bomberman.entities.tile.Grass;
import bomberman.entities.tile.Portal;
import bomberman.entities.tile.Wall;
import bomberman.entities.tile.destroyable.Brick;
import bomberman.entities.tile.item.BombItem;
import bomberman.entities.tile.item.FlameItem;
import bomberman.entities.tile.item.SpeedItem;
import bomberman.exceptions.LoadLevelException;
import bomberman.graphics.Screen;
import bomberman.graphics.Sprite;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map ;
	
	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) {
		// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
		try {
		String _level_string = String.valueOf(level);
		File file = new File( FileLevelLoader.class.getResource("/levels/level"+_level_string+".txt").getFile() );
		FileInputStream fi = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fi, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        
        String first_line = br.readLine();
        String[] x = first_line.split(" ");
        
        _level = Integer.parseInt(x[0]);
        _height = Integer.parseInt(x[1]); // độ cao - số hàng 13
        _width = Integer.parseInt(x[2]); // độ rộng - số cột 31
        
        _map = new char[_height][_width];
        
        for( int i = 0; i<_height; i++) {
        	String tmp = br.readLine();
        	for ( int j = 0; j< tmp.length(); j++) {
        		 _map[i][j] = tmp.charAt(j);
        	}
        }        
        
        br.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
        
	}

	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
		// thêm Wall

		
		for ( int y = 0; y < _height ; y++) {
			for ( int x = 0 ; x < _width; x++) {
				switch( _map[y][x]) {
					case '#': addWall(x, y);break;// wall_not destroyable
					case '*': addBrick(x,y);break;// brick destroyable
					case 'x': addPortal(x,y);break;// portal
					case ' ': addGrass(x,y);break; // grass
						
					case 'p': addBomber(x,y);break;// bomber/ player
					case '1': addEnemy(x, y, 1);break;// balloon _monster
					case '2': addEnemy(x, y, 2);break;// oneal _monster
					case '3': addEnemy(x, y, 3);break;// doll_monster
					case '4': addEnemy(x, y, 4);break;// doll_monster
						
					case 'b': addItem(x, y, 1);break; // bomber
					case 'f': addItem(x, y, 2);break;// flame
					case 's': addItem(x, y, 3);break;// speed

				}
			}
		}

	}
	private void addGrass(int x, int y) {
		int xB = x, yB = y;
		int pos = xB + yB * _width;
		_board.addEntity(pos, new Grass(xB, yB, Sprite.grass));
	}
	private void addWall(int x , int y) {
		int xB = x, yB = y;
		int pos = xB + yB * _width;
		_board.addEntity(pos, new Wall(xB, yB, Sprite.wall));
	}
	private void addBomber(int x, int y) {
		int xBomber = x, yBomber = y;
		_board.addCharacter( new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board) );
		Screen.setOffset(0, 0);
		_board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));
	}
	private void addEnemy(int x, int y, int type) {
		int xE = x, yE = y;
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
			case 4:{
				_board.addCharacter( new Kondoria(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
				_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
			}break;
		}
	}
	private void addBrick(int x, int y) {		
		int xB = x, yB = y;
		_board.addEntity(xB + yB * _width,
				new LayeredEntity(xB, yB,
					new Grass(xB, yB, Sprite.grass),
					new Brick(xB, yB, Sprite.brick)
				)
		);
	}
		
	private void addItem(int x, int y, int type) {
		int xB = x, yB = y;
		int pos = xB + yB * _width;
		if( type == 1) {  // add bomb
			_board.addEntity(
					pos, new LayeredEntity(xB, yB, 
						new Grass(xB, yB, Sprite.grass),
						new BombItem(xB, yB, Sprite.powerup_bombs,_board),
						new Brick(xB, yB, Sprite.brick)
					)
			);
		}
		else if(type == 2) {  // add flame
			_board.addEntity(
					pos, new LayeredEntity(xB, yB, 
						new Grass(xB, yB, Sprite.grass),
						new FlameItem(xB, yB, Sprite.powerup_flames, _board),
						new Brick(xB, yB, Sprite.brick)
					)
			);
		}
		else { // type == 3 // add speed 
			_board.addEntity(
					pos, new LayeredEntity(xB, yB, 
						new Grass(xB, yB, Sprite.grass),
						new SpeedItem(xB, yB, Sprite.powerup_speed,_board),
						new Brick(xB, yB, Sprite.brick)
					)
			);
		}
	}
	private void addPortal(int x, int y) {
		int xB = x, yB = y;
		int pos = xB + yB * _width;
		_board.addEntity(
				pos, new LayeredEntity(xB, yB, 
					 new Portal(xB, yB, Sprite.portal,_board),
					 new Brick(xB, yB, Sprite.brick)
				)
		);
	}

}
