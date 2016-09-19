package widgets;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

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
	
	
}
