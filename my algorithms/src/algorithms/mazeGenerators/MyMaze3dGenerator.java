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
		Position temPos=new Position(maze.getStartPosition());
		maze.setPlayerPosition(temPos);
		String neighbors[];

		while(!maze.getPlayerPosition().equals(maze.getGoalPosition()))
		{
			neighbors= maze.getAllPositionAround(maze.getPlayerPosition());
			for(int i=0; i< neighbors.length;i++)
			{
				String tempString= neighbors[i];
				if(tempString!=null)
				{
					Position neighbor=new Position(tempString);
					if(!visited.contains(neighbor)&& maze.getPositionValueByPosition(neighbor)==0)
					{
						unVisited.push(neighbor);

					}
			
				
				}
			
			}
			Collections.shuffle(unVisited);

		
			if(unVisited.size()==0)
			{
				
				Position tempPos=visited.pop();
	
				maze.setPlayerPosition(tempPos);	
				break;
			}
			else 
			{
					visited.push(maze.getPlayerPosition());
					Position tempPos=unVisited.pop();
					int y1=maze.getPlayerPosition().getY()-tempPos.getY();
					int x1=maze.getPlayerPosition().getX()-tempPos.getX();
					int z1=maze.getPlayerPosition().getZ()-tempPos.getZ();
					if(x1==0 && z1==0)
					{
						Position midPosition=new Position(maze.getPlayerPosition().getY()-y1, maze.getPlayerPosition().getX(),maze.getPlayerPosition().getZ());
						maze.setPositionValueByPosition(midPosition, 0);
					}
					else if(y1==0 && (x1!=0 || z1!=0))
					{
						Position midPosition=new Position(maze.getPlayerPosition().getY(), maze.getPlayerPosition().getX()-x1/2,maze.getPlayerPosition().getZ()-z1/2);
						maze.setPositionValueByPosition(midPosition, 0);
					}
					maze.setPositionValueByPosition(tempPos, 0);
					maze.setPlayerPosition(tempPos);
					unVisited.clear();
				//	visited.push(maze.getPlayerPosition());
		
			}
			if(visited.isEmpty())
			{
				System.out.println("Error building the maze");
				break;
			}

		/*	do{
			Position tempPos=unVisited.pop();
			if(!visited.contains(tempPos))
			{
				maze.setPositionValueByPosition(tempPos, 0);
				maze.setPlayerPosition(tempPos);
				unVisited.clear();
				break;
			}
			else if( unVisited.size()==0)
			{
				maze.setPlayerPosition(visited.pop());
			}
			
			}while(!unVisited.isEmpty());*/
		
		}
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
