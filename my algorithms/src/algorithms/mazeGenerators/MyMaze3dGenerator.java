package algorithms.mazeGenerators;


import java.util.Collections;

import java.util.Stack;

/**
 * 
 * @author sefy1
 * @since 30/07/2016
 * @category DFS Maze Generator 
 */
public class MyMaze3dGenerator extends CommonMaze3dGenerator {
/**
 * @param overAllSize - (y,x,z) size of the maze
 * @return Maze3d maze after been generated with DFS algorithm
 */
	@Override
	public Maze3d generate(String overAllSize) {
		String[] parts=overAllSize.split(",", 3);
		int y=Integer.valueOf(parts[0]);
		int x=Integer.valueOf(parts[1]);
		int z=Integer.valueOf(parts[2]);
		Stack<Position> visited=new Stack<Position>();
		Stack<Position> unVisited= new Stack<Position>();

	
		
		if(x<1||y<1||z<1)
		{
			System.out.println("Please check that one of the following is bigger then 1 : y/x/z");
			return null;
		}
		if(x%2==0)
		{
			x+=1;
		}
		if(z%2==0)
		{
			z+=1;
		}
		
		Maze3d maze=new Maze3d(y, x, z);
		initialize(maze);	
		this.initiateOddIndexValues(maze);
		maze.setStartPosition();
		maze.setGoalPosition();
		maze.setPlayerPosition(new Position(maze.getStartPosition().getY(),maze.getStartPosition().getX(),maze.getStartPosition().getZ()));
		String neighbors[];
		Position pos;
		do
		{
			if(!visited.contains(maze.getPlayerPosition()))
			{	
			visited.push(maze.getPlayerPosition());
			}
			neighbors=maze.getAllPositionAround(maze.getPlayerPosition());
			if(neighbors.length>0)
			{
				for(int i=0;i<neighbors.length;i++)
				{
					if(neighbors[i]!=null)
					{
						pos=new Position(neighbors[i]);
						if(!unVisited.contains(pos))
						{
							unVisited.push(pos);
						}
					}
				}
				Collections.shuffle(unVisited);
				Position moveTo=unVisited.pop();
				unVisited.clear();
				if(maze.getPlayerPosition().getY()>moveTo.getY())
				{
					maze.moveDown();
					maze.setPositionValueByPosition(maze.getPlayerPosition(), 0);
				}
				else if(maze.getPlayerPosition().getY()<moveTo.getY())
				{
					maze.moveUp();
					maze.setPositionValueByPosition(maze.getPlayerPosition(), 0);
				}
				else if(maze.getPlayerPosition().getX()>moveTo.getX())
				{
					maze.moveLeft();
					maze.setPositionValueByPosition(maze.getPlayerPosition(), 0);
					maze.moveLeft();
					maze.setPositionValueByPosition(maze.getPlayerPosition(), 0);
				}
				else if(maze.getPlayerPosition().getX()<moveTo.getX())
				{
					maze.moveRight();
					maze.setPositionValueByPosition(maze.getPlayerPosition(), 0);
					maze.moveRight();
					maze.setPositionValueByPosition(maze.getPlayerPosition(), 0);
				}
				else if(maze.getPlayerPosition().getZ()>moveTo.getZ())
				{
					maze.moveOut();
					maze.setPositionValueByPosition(maze.getPlayerPosition(), 0);
					maze.moveOut();
					maze.setPositionValueByPosition(maze.getPlayerPosition(), 0);
				}
				else if(maze.getPlayerPosition().getZ()<moveTo.getZ())
				{
					maze.moveIn();
					maze.setPositionValueByPosition(maze.getPlayerPosition(), 0);
					maze.moveIn();
					maze.setPositionValueByPosition(maze.getPlayerPosition(), 0);

				}
			}
			else
			{
				maze.setPlayerPosition(visited.pop());
			}
			
			
		}while(!maze.getPlayerPosition().equals(maze.getGoalPosition()));
		maze.setPlayerPosition(maze.getStartPosition());
		

		return maze;
	}
/** 
 * @param maze 
 * This method sets every odd index with zero in order to create a perfect maze
 */
	public void initiateOddIndexValues(Maze3d maze)
	{
		for(int y=1;y<maze.getSizeY()-1;y++)
		{
			for(int x=1;x<maze.getSizeX()-1;x++)
			{
				for(int z=1;z<maze.getSizeZ()-1;z++)
				{
					if(x%2!=0 && z%2!=0)
					{
						maze.setPositionValueByIndex(y, x, z, 0);
					}
				}
			}
		}
	}
	
}
