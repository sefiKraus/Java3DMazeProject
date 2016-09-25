package widgets;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
/**
 * 
 * @author Krausz Sefi 305371320
 * @since 15/09/2016
 */
public  class MyMazeDisplayWidgets extends MazeDisplay{
	private GameCharacter player;
	private GameCharacter goal;
	private int[][] mazeData;
	private Maze3d maze;
	int currentLevel;
	private Image floorImage;
	private Image cellImage;
	private boolean won=false;
	Image victoryImage;
	/**
	 * 
	 * @param composite
	 * @param style
	 * @param maze
	 */
	public MyMazeDisplayWidgets(Composite composite, int style,Maze3d maze) {
		super(composite, style);
		this.maze=maze;
		maze.setPlayerPosition(new Position(maze.getStartPosition().getY(),maze.getStartPosition().getX(),maze.getStartPosition().getZ()));
		
		Image upArrow=new Image(null, "res/images/up.png");
		
		Image downArrow=new Image(null, "res/images/down.png");
		
		Image twoSidedArrow=new Image(null, "res/images/updown.png");
		
		this.victoryImage=new Image(null, "res/images/victory.png");
		
		
		this.player=new GameCharacter(composite, style,new Position(maze.getPlayerPosition()),"res/images/ezreal.png");
		
		this.goal=new GameCharacter(composite, style, new Position(maze.getGoalPosition()), "res/images/thresh.png");
		
		this.setMazeData(maze.getCrossSectionByY(player.getPlayerPosition().getY()));
		
		this.currentLevel=player.getPlayerPosition().getY();
		
		this.floorImage=new Image(null, "res/images/path.png");
		
		this.cellImage=new Image(null, "res/images/grass.png");
		
		this.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
			/*	e.gc.setBackground(new Color(null, 0,0,0));
				e.gc.setForeground(new Color(null, 0,0,0));*/
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
				 if(canMoveDown() && canMoveUp())
				{
					e.gc.drawImage(twoSidedArrow, 0, 0, twoSidedArrow.getBounds().width,twoSidedArrow.getBounds().height, 0, 0, w, h);

				}
				 else if(canMoveUp())
				{
					e.gc.drawImage(upArrow, 0, 0,upArrow.getBounds().width,upArrow.getBounds().height,0,0,w,h);
					
				}
				 else if(canMoveDown())
				{
					e.gc.drawImage(downArrow, 0, 0, downArrow.getBounds().width,downArrow.getBounds().height, 0, 0, w, h);
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

	/**
	 * @param y
	 * @param x
	 * @param z
	 * 
	 * This method allows the character to move inside the board
	 * 
	 */
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
				this.won=true;
				displayVictoryScreen();
			}
	}
	/**
	 * @param y
	 * @param x
	 * @param z
	 * 
	 * This method is using movePlayer method in order to check if the player can move to position(y,x,z)
	 */
	@Override
	public void movePlayerTo(int y,int x,int z)
	{
		if(maze.getPositionValueByIndex(y, x, z)==0 && !this.isDisposed())
		{
			movePlayer(y, x, z);
			redraw();
		}
	}

	/**
	 * This method is activated when the player reaches the goal position 
	 * it opens a new victory shell
	 */
	@Override
	public void displayVictoryScreen()
	{
		Shell shell = new Shell (this.getShell(),SWT.SHELL_TRIM |SWT.DOUBLE_BUFFERED);
        shell.setLayout(new GridLayout());
        

        shell.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
			     GC gc = e.gc;
	                int x = 0, y = 0;
	                
	                gc.drawImage (victoryImage, x, y);
	                gc.dispose();				
			}
		});
       shell.addDisposeListener(new DisposeListener() {
		
		@Override
		public void widgetDisposed(DisposeEvent arg0) {
			shell.dispose();
			getShell().dispose();
			
		}
	});
      //  shell.setSize(victoryImage.getBounds().width, victoryImage.getBounds().height);
       shell.setSize(victoryImage.getBounds().width, victoryImage.getBounds().height);

        shell.open ();
        
		
	}

	@Override
	public GameCharacter getPlayer() {
		return player;
	}

	@Override
	public Maze3d getMaze() {
		return maze;
	}

	@Override
	public boolean isWon() {
		return won;
	}


	public void setWon(boolean won) {
		this.won = won;
	}
	@Override
	public boolean canMoveUp()
	{
		
		if(maze.getPositionValueByIndex(player.getPlayerPosition().getY()+1, player.getPlayerPosition().getX(),player.getPlayerPosition().getZ())==0)
		{
			return true;
		}
		return false;
	}
	@Override
	public boolean canMoveDown()
	{
		if(maze.getPositionValueByIndex(player.getPlayerPosition().getY()-1, player.getPlayerPosition().getX(),player.getPlayerPosition().getZ())==0)
		{
			return true;
		}
		return false;	}
	
}
