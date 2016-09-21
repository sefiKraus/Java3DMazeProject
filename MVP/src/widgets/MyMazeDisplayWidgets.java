package widgets;


import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public  class MyMazeDisplayWidgets extends MazeDisplay{

	private GameCharacter player;
	private GameCharacter goal;
	private int[][] mazeData;
	private Maze3d maze;
	int currentLevel;
	private Image floorImage;
	private Image cellImage;
	
	public MyMazeDisplayWidgets(Composite composite, int style,Maze3d maze) {
		super(composite, style);
		this.maze=maze;
		maze.setPlayerPosition(new Position(maze.getStartPosition().getY(),maze.getStartPosition().getX(),maze.getStartPosition().getZ()));
		this.player=new GameCharacter(composite, style,new Position(maze.getPlayerPosition()),"res/images/ezreal.png");
		this.goal=new GameCharacter(composite, style, new Position(maze.getGoalPosition()), "res/images/thresh.png");
		this.setMazeData(maze.getCrossSectionByY(player.getPlayerPosition().getY()));
		this.currentLevel=player.getPlayerPosition().getY();
		this.floorImage=new Image(null, "res/images/path.png");
		this.cellImage=new Image(null, "res/images/grass.png");
		this.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setBackground(new Color(null, 0,0,0));
				e.gc.setForeground(new Color(null, 0,0,0));
				int width=getSize().x;
				int height=getSize().y;
				Transform transform=new Transform(getDisplay());
				int r=Math.min(width, height);

				int w=r/mazeData[0].length;
				int h=r/mazeData.length;
				 
					transform.translate(50+w, 50+h);
					transform.rotate(-45);
				for(int i=0;i<mazeData.length;i++)
				{
					for(int j=0;j<mazeData[i].length;j++)
					{
						int x=j*w;
						int y=i*h;
						
						if(mazeData[i][j]!=0)
						{
							
							e.gc.drawImage(cellImage, 0, 0,cellImage.getBounds().width,cellImage.getBounds().height,
									y,x,w,h);

						}
						else
						{
							e.gc.drawImage(floorImage,0,0,floorImage.getBounds().width,floorImage.getBounds().height
									,y,x,w,h);
						}
					}
				}
				player.draw(w, h, e.gc);
				if(currentLevel==goal.getY())
				{
					goal.draw(w, h, e.gc);
							
				}
			

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
		this.movePlayerTo(player.getY()-1,player.getX(),player.getZ());

	}

	@Override
	public void moveRight() {
		this.movePlayerTo(player.getY(),player.getX()+1,player.getZ());

	}

	@Override
	public void moveLeft() {
		
		this.movePlayerTo(player.getY(),player.getX()-1,player.getZ());

	}

	@Override
	public void moveIn() {
		this.movePlayerTo(player.getY(),player.getX(),player.getZ()+1);

	}

	@Override
	public void moveOut() {
		this.movePlayerTo(player.getY(),player.getX(),player.getZ()-1);

	}
	@Override
	public void movePlayer(int y,int x,int z)
	{
			if(!player.getPlayerPosition().equals(goal.getPlayerPosition()))
			{
				player.setY(y);
				player.setX(x);
				player.setZ(z);
				this.setMazeData(maze.getCrossSectionByY(player.getPlayerPosition().getY()));
				currentLevel=player.getY();
				
			}
			
			if(player.getPlayerPosition().equals(goal.getPlayerPosition()))
			{
				displayVictoryScreen();
			}
	}
	@Override
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

	@Override
	public GameCharacter getPlayer() {
		return player;
	}

	@Override
	public Maze3d getMaze() {
		return maze;
	}
	
	
}
