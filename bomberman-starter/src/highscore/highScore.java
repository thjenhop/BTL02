package highscore;

public class highScore implements Comparable<highScore> {
	String name;
	String point;
	int int_point;
	public highScore(String name, String point) {
		this.name = name;
		this.point = point;
		this.int_point = Integer.parseInt(this.point);
	}
	public int getPoint() {
		return int_point;
	}
	@Override
	public int compareTo(highScore o) {
		return Integer.compare(this.getPoint(), o.getPoint() );
	}
	public String toString() {
		return name + "    "+ point;
	}
	
}
