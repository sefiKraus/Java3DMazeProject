package widgets;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Position;
/**
 * 
 * @author Krausz Sefi 305371320
 * @since 15/09/2016
 */
public class GameCharacter extends Canvas{
	
	 Position playerPosition;
	 Image playerImage;
	
	/**
	 * 
	 * @param composite
	 * @param style
	 * @param position
	 * @param imagePath
	 * 
	 * 
	 */
	public GameCharacter(Composite composite, int style,Position position,String imagePath) {
		super(composite, style);
		this.playerPosition=position;
		this.playerImage=new Image(null, imagePath);
		

	}
	/**
	 * 
	 * @param cellWidth
	 * @param cellHeight
	 * @param gc
	 */
	public void draw(int cellWidth,int cellHeight,GC gc)
	{
		gc.drawImage(this.playerImage, 0, 0	, this.playerImage.getBounds().width, (this.playerImage.getBounds().height), this.playerPosition.getZ()*cellWidth, this.playerPosition.getX()*cellHeight, cellWidth,cellHeight);
	}
	public void moveUp()
	{
		this.playerPosition.setY(this.playerPosition.getY()+1);
	}
	
	public void moveDown()
	{
		this.playerPosition.setY(this.playerPosition.getY()-1);
	}
	
	public void moveRight()
	{
		this.playerPosition.setX(this.playerPosition.getX()+1);
	}
	public void moveLeft()
	{
		this.playerPosition.setX(this.playerPosition.getX()-1);
	}
	
	public void moveIn()
	{
		this.playerPosition.setZ(this.playerPosition.getZ()+1);
	}
	
	public void moveOut()
	{
		this.playerPosition.setZ(this.playerPosition.getZ()-1);
	}

	
	public Position getPlayerPosition() {
		return playerPosition;
	}

	public void setPlayerPosition(Position playerPosition) {
		this.playerPosition = playerPosition;
	}

	public Image getPlayerImage() {
		return playerImage;
	}

	public void setPlayerImage(Image playerImage) {
		this.playerImage = playerImage;
	}

	public void setX(int x)
	{
		this.playerPosition.setX(x);
	}
	
	public void setY(int y)
	{
		this.playerPosition.setY(y);
	}
	public void setZ(int z)
	{
		this.playerPosition.setZ(z);
	}
	
	public int getX()
	{
		return this.playerPosition.getX();
	}
	public int getY()
	{
		return this.playerPosition.getY();
		
	}
	public int getZ()
	{
		return this.playerPosition.getZ();
	}
	
	
	
	
}
