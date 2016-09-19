package widgets;


import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public  class MyMazeDisplayWidgets extends MazeDisplay{

	private GameCharacter player;
	private GameCharacter goal;
	private int[][] mazeData;
	private Maze3d maze;
	
	
	public MyMazeDisplayWidgets(Composite composite, int style,Maze3d maze) {
		super(composite, style);
		this.maze=maze;
		maze.setPlayerPosition(new Position(maze.getStartPosition().getY(),maze.getStartPosition().getX(),maze.getStartPosition().getZ()));
		this.player=new GameCharacter(composite, style,new Position(maze.getPlayerPosition()),"res/images/ez.jpg");
		this.goal=new GameCharacter(composite, style, new Position(maze.getGoalPosition()), "res/images/thresh.jpg");
		this.setMazeData(maze.getCrossSectionByY(player.getPlayerPosition().getY()));
		System.out.println(player.getPlayerPosition().toString());
			Transform tr=new Transform(composite.getDisplay());
		tr.rotate(+90);
		
		this.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setBackground(new Color(null, 0,0,0));
				e.gc.setForeground(new Color(null, 0,0,0));
				int width=getSize().x;
				int height=getSize().y;

				int r=Math.min(width, height);

				int w=r/mazeData[0].length;
				int h=r/mazeData.length;
				
				for(int i=0;i<mazeData.length;i++)
				{
					for(int j=0;j<mazeData[i].length;j++)
					{
						int x=j*w;
						int y=i*h;
						
						if(mazeData[i][j]!=0)
						{
							e.gc.fillRectangle(x, y, w, h);
						}
					}
				}
				player.draw(w, h, e.gc);
				//goal.draw(w, h, e.gc);
			

			}
		});
		
	
	}
	

	@Override
	public void setMazeData(int[][] mazeData) {
		this.mazeData=mazeData;
	}

	@Override
	public void moveUp() {
		this.movePlayerTo(player.getY()+1,player.getX(),player.getZ());
		
	}

	@Override
	public void moveDown() {
		//player.moveDown();
		this.movePlayerTo(player.getY()-1,player.getX(),player.getZ());

	}

	@Override
	public void moveRight() {
		//player.moveRight();
		this.movePlayerTo(player.getY(),player.getX()+1,player.getZ());

	}

	@Override
	public void moveLeft() {
		
		//player.moveLeft();
		this.movePlayerTo(player.getY(),player.getX()-1,player.getZ());

	}

	@Override
	public void moveIn() {
		//player.moveIn();
		this.movePlayerTo(player.getY(),player.getX(),player.getZ()+1);

	}

	@Override
	public void moveOut() {
		//player.moveOut();
		this.movePlayerTo(player.getY(),player.getX(),player.getZ()-1);

	}

	public void movePlayer(int y,int x,int z)
	{
			if(!player.getPlayerPosition().equals(goal.getPlayerPosition()))
			{
				System.out.println(player.getPlayerPosition().toString());
				player.setY(y);
				player.setX(x);
				player.setZ(z);
				System.out.println(player.getPlayerPosition().toString());
				this.setMazeData(maze.getCrossSectionByY(player.getPlayerPosition().getY()));
				
			}
			
			if(player.getPlayerPosition().equals(goal.getPlayerPosition()))
			{
				displayVictoryScreen();
			}
	}
	
	public void movePlayerTo(int y,int x,int z)
	{
		if(maze.getPositionValueByIndex(y, x, z)==0 && !this.isDisposed())
		{
			movePlayer(y, x, z);
			redraw();
		}
	}

	public void displayVictoryScreen()
	{
		
	}
}
