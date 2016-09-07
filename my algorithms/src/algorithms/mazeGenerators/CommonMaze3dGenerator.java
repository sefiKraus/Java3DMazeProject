package algorithms.mazeGenerators;
/**
 * 
 * @author Krausz Sefi 305371320
 * @since 30/07/2016
 * @category CommonMaze3dGenerator Class
 *
 */
public abstract class CommonMaze3dGenerator implements Maze3dGenerator {

	@Override
	public abstract Maze3d generate(String overAllSize);

	@Override
	public String measureAlgorithmTime(String overAllSize) {
		double startTime, endTime;
		String difference;
		startTime=System.currentTimeMillis();
		this.generate(overAllSize);
		endTime=System.currentTimeMillis()-startTime;
		difference=String.valueOf(endTime);
		return difference;
	}
/**
 *  This method initializing the maze with walls
 * @param maze
 */
	public void initialize(Maze3d maze)
	{
		for(int y=0; y<maze.getSizeY();y++)
		{
			for(int x=0;x<maze.getSizeX();x++)
			{
				for(int z=0;z<maze.getSizeZ();z++)
				{
					maze.setPositionValueByIndex(y, x, z, 1);
					
				}
			
			}
		
		}
	}
}

