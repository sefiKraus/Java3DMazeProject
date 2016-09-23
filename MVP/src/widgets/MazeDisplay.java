package widgets;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
/**
 * 
 * @author Krausz Sefi 305371320
 * @since 15/09/2016
 */
public abstract class MazeDisplay extends Canvas{


	
	public MazeDisplay(Composite composite, int style) {
		super(composite, style);
	
	}

	
	public abstract void setMazeData(int[][] mazeData);
	
	public abstract void moveUp();
	
	public abstract void moveDown();
	
	public abstract void moveRight();
	
	public abstract void moveLeft();
	
	public abstract void moveIn();
	
	public abstract void moveOut();
	
	public abstract GameCharacter getPlayer();
	
	public abstract Maze3d getMaze();
	
	public abstract void movePlayerTo(int y,int x,int z);
	
	public abstract  void movePlayer(int y,int x,int z);
}
