package algorithms.mazeGenerators;

import java.util.Random;

/**
 * 
 * @author sefy1
 * @since 30/07/2016
 * @category Random Maze Generator 
 */
public class SimpleMaze3dGenerator extends CommonMaze3dGenerator {

	/**
	 * @param overAllSize - (y,x,z) size of the maze
	 * @return Maze3d maze after been generated with Random path algorithm
	 */
	@Override
	public Maze3d generate(String overAllSize) {
		
		String[] parts=overAllSize.split(",", 3);
		int y=Integer.valueOf(parts[0]);
		int x=Integer.valueOf(parts[1]);
		int z=Integer.valueOf(parts[2]);
		Random movmentRequired=new Random(); // 1-6  for movement 
		if(x<1||y<1||z<1) //TODO Need to check if y,x,z should be at least 3
		{
			System.out.println("Please check that one of the following is bigger then 1 : y/x/z");
			return null;
		}
		Maze3d maze=new Maze3d(y, x, z);
		initialize(maze);	
		maze.setStartPosition();
		maze.setGoalPosition();
		Position temPos=new Position(maze.getStartPosition());
		maze.setPlayerPosition(temPos);
		while(!maze.getPlayerPosition().equals(maze.getGoalPosition()))
		{
			
			 int move=movmentRequired.nextInt(6)+1;
			if(maze.moveToDirection(move))
			{
				maze.setPositionValueByPosition(maze.getPlayerPosition(), 0);

			}
	 
		}
	
		maze.setPlayerPosition(maze.getStartPosition());
		return maze;
	}

	
	
}
