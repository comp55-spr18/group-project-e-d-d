import acm.graphics.*;
import acm.program.GraphicsProgram;

public abstract class BaseActor implements Actor {
	private int x;
	private int y;
	private GImage sprite;
	
	//basic getters for the coordinates and for the sprite
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public GImage getSprite() { return this.sprite; }
	
	//The consequent setters
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setSprite(GImage sprite) { this.sprite = sprite; }
	
}
