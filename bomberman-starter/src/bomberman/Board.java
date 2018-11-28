package bomberman;

import bomberman.entities.Entity;
import bomberman.entities.Message;
import bomberman.entities.bomb.Bomb;
import bomberman.entities.bomb.FlameSegment;
import bomberman.entities.character.Bomber;
import bomberman.entities.character.Character;
import bomberman.exceptions.LoadLevelException;
import bomberman.graphics.IRender;
import bomberman.graphics.Screen;
import bomberman.gui.typingInfo;
import bomberman.input.Keyboard;
import bomberman.level.FileLevelLoader;
import bomberman.level.LevelLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bomb_audio.ending_sound;
import bomb_audio.game_over;
import bomb_audio.next_level;

/**
 * Quản lý thao tác điều khiển, load level, render các màn hình của game
 */
public class Board implements IRender {
	protected LevelLoader _levelLoader;
	protected Game _game;
	protected Keyboard _input;
	protected Screen _screen;
	
	public Entity[] _entities;
	public List<Character> _characters = new ArrayList<Character>();
	protected List<Bomb> _bombs = new ArrayList<Bomb>();
	private List<Message> _messages = new ArrayList<Message>();
	
	private int _screenToShow = -1; //1:gameOVER, 2:changelevel, 3:paused, 4:winGAME
	
	private int _time = Game.TIME;
	private int _points = Game.POINTS;
	private int _lives = Game.LIVES;
	
	protected typingInfo ti = new typingInfo(this);
	
	
	public Board(Game game, Keyboard input, Screen screen) {
		_game = game;
		_input = input;
		_screen = screen;
		
		loadLevel(1); //start in level 1
	}
	
	@Override
	public void update() {
		if( _game.isPaused() ) return;
		
		updateEntities();
		updateCharacters();
		updateBombs();
		updateMessages();
		detectEndGame();
		
		for (int i = 0; i < _characters.size(); i++) {
			Character a = _characters.get(i);
			if(a.isRemoved()) _characters.remove(i);
		}
	}

	@Override
	public void render(Screen screen) {
		if( _game.isPaused() ) return;
		
		//only render the visible part of screen
		int x0 = Screen.xOffset >> 4; //tile precision, -> left X
		int x1 = (Screen.xOffset + screen.getWidth() + Game.TILES_SIZE) / Game.TILES_SIZE; // -> right X
		//             0         +    240             +    16            / 16
		int y0 = Screen.yOffset >> 4;
		int y1 = (Screen.yOffset + screen.getHeight()) / Game.TILES_SIZE; //render one tile plus to fix black margins
		//            0          +   208               / 16
		
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				int pos = x + y * _levelLoader.getWidth();
				_entities[pos].render(screen);

			}
		}

		
		renderBombs(screen);
		renderCharacter(screen);
		
	}
	
	public void nextLevel() {
		if( _levelLoader.getLevel() == _game.numberOfLevel) winGame();
		else {
			getGame().st.stop();
			next_level nl =  new next_level();
			nl.play(false);
			addLives(1);
			loadLevel(_levelLoader.getLevel() + 1);
			Game.is1time = true;
			getGame().st.play(true);
		}
	}
	
	public void loadLevel(int level) {
		_time = Game.TIME;
		_screenToShow = 2;
		_game.resetScreenDelay();
		_game.pause();
		_characters.clear();
		_bombs.clear();
		_messages.clear();
		
		try {
			_levelLoader = new FileLevelLoader(this, level);
			_entities = new Entity[_levelLoader.getHeight() * _levelLoader.getWidth() ];
			
			_levelLoader.createEntities();
		} catch (LoadLevelException e) {
			endGame();
		}
	}
	
	protected void detectEndGame() {
		if(_time <= 0)
			endGame();
	}

	public void endGame() {
		_screenToShow = 1;
		_game.resetScreenDelay();
		_game.pause();
		_game.st.stop();
		game_over go = new game_over();
		go.play(false);
		int c = JOptionPane.showOptionDialog(this.getGame(), "Play again", "Game Over", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if( c == JOptionPane.YES_OPTION) {
			loadLevel(1);
			getGame().resetPower();
			this.resetLives();
			getGame().st.play(true);
		}
	}
	
	public void winGame() {
		_screenToShow = 4;
		_game.resetScreenDelay();
		_game.pause();
		_game.st.stop();
		ti.setVisible(true);
		ending_sound es = new ending_sound();
		es.play(false);
	}
	
	public boolean detectNoEnemies() {
		int total = 0;
		for (int i = 0; i < _characters.size(); i++) {
			if(_characters.get(i) instanceof Bomber == false)
				++total;
		}
		
		return total == 0;
	}
	
	public void drawScreen(Graphics g) {
		switch (_screenToShow) {
			case 1:
				_screen.drawEndGame(g, _points);
				break;
			case 2:
				_screen.drawChangeLevel(g, _levelLoader.getLevel());
				break;
			case 3:
				_screen.drawPaused(g);
				break;
			case 4:
				_screen.drawWinGame(g, _points);
				break;
			case 5:
				_screen.drawStart(g);
				break;
		}
	}
	
	public Entity getEntity(double x, double y, Character m) {
		
		Entity res = null;
		
		res = getFlameSegmentAt((int)x, (int)y);
		if( res != null) return res;
		
		res = getBombAt(x, y);
		if( res != null) return res;
		
		res = getCharacterAtExcluding((int)x, (int)y);
		if( res != null) return res;
		
		res = getEntityAt((int)x, (int)y);
		
		return res;
	}
	
	public List<Bomb> getBombs() {
		return _bombs;
	}
	
	public Bomb getBombAt(double x, double y) {
		Iterator<Bomb> bs = _bombs.iterator();
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			if(b.getX() == (int)x && b.getY() == (int)y)
				return b;
		}
		
		return null;
	}

	public Bomber getBomber() {
		Iterator<Character> itr = _characters.iterator();
		
		Character cur;
		while(itr.hasNext()) {
			cur = itr.next();
			
			if(cur instanceof Bomber)
				return (Bomber) cur;
		}
		
		return null;
	}
	
	public Character getCharacterAtExcluding(int x, int y) {
		Iterator<Character> itr = _characters.iterator();
		
		Character cur;
		while(itr.hasNext()) {
			cur = itr.next();
			if(cur.getXTile() == x && cur.getYTile() == y) {
				return cur;
			}
				
		}
		
		return null;
	}
	
	public FlameSegment getFlameSegmentAt(int x, int y) {
		Iterator<Bomb> bs = _bombs.iterator();
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			
			FlameSegment e = b.flameAt(x, y);
			if(e != null) {
				return e;
			}
		}
		
		return null;
	}
	
	public Entity getEntityAt(double x, double y) {
		// x, y là vị trí theo tile
		return _entities[(int)x + (int)y * _levelLoader.getWidth()];
	}
	
	public void addEntity(int pos, Entity e) {
		_entities[pos] = e;
	}
	
	public void addCharacter(Character e) {
		_characters.add(e);
	}
	
	public void addBomb(Bomb e) {
		_bombs.add(e);
	}
	
	public void addMessage(Message e) {
		_messages.add(e);
	}

	protected void renderCharacter(Screen screen) {
		Iterator<Character> itr = _characters.iterator();
		
		while(itr.hasNext())
			itr.next().render(screen);
	}
	
	protected void renderBombs(Screen screen) {
		Iterator<Bomb> itr = _bombs.iterator();
		
		while(itr.hasNext())
			itr.next().render(screen);
	}
	
	public void renderMessages(Graphics g) {
		Message m;
		for (int i = 0; i < _messages.size(); i++) {
			m = _messages.get(i);
			
			g.setFont(new Font("Arial", Font.PLAIN, m.getSize()));
			g.setColor(m.getColor());
			g.drawString(m.getMessage(), (int)m.getX() - Screen.xOffset  * Game.SCALE, (int)m.getY());
		}
	}
	
	protected void updateEntities() {
		if( _game.isPaused() ) return;
		for (int i = 0; i < _entities.length; i++) {
			_entities[i].update();
		}
	}
	
	protected void updateCharacters() {
		if( _game.isPaused() ) return;
		Iterator<Character> itr = _characters.iterator();
		
		while(itr.hasNext() && !_game.isPaused())
			itr.next().update();
	}
	
	protected void updateBombs() {
		if( _game.isPaused() ) return;
		Iterator<Bomb> itr = _bombs.iterator();
		
		while(itr.hasNext())
			itr.next().update();
	}
	
	protected void updateMessages() {
		if( _game.isPaused() ) return;
		Message m;
		int left;
		for (int i = 0; i < _messages.size(); i++) {
			m = _messages.get(i);
			left = m.getDuration();
			
			if(left > 0) 
				m.setDuration(--left);
			else
				_messages.remove(i);
		}
	}

	public int subtractTime() {
		if(_game.isPaused())
			return this._time;
		else
			return this._time--;
	}

	public Keyboard getInput() {
		return _input;
	}

	public LevelLoader getLevel() {
		return _levelLoader;
	}

	public Game getGame() {
		return _game;
	}

	public int getShow() {
		return _screenToShow;
	}

	public void setShow(int i) {
		_screenToShow = i;
	}

	public int getTime() {
		return _time;
	}

	public int getPoints() {
		return _points;
	}

	public void addPoints(int points) {
		this._points += points;
	}
	
	public int getWidth() {
		return _levelLoader.getWidth();
	}

	public int getHeight() {
		return _levelLoader.getHeight();
	}
	
	public int getLives() {
		return this._lives;
	}
	public void addLives(int i) {
		this._lives += i;
	}
	public int getLevels() {
		return _levelLoader.getLevel();
	}
	
	public void pause() {
		_game.resetScreenDelay();
		if(_screenToShow <= 0)
			_screenToShow = 3;
		_game.pause();
	}
	public void resume() {
		_game.resetScreenDelay();
		_screenToShow = -1;
		_game.resume();
	}
	public void resetLives() {
		this._lives = 3;
		this._points = 0;
	}
}