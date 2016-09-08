package Model;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
/**
 * 
 * @author Krausz sefi
 * @since 07/09/2016
 *
 */
public interface Model {

	public HashMap<String, Maze3d>getMazeMap();
	public HashMap<Maze3d, Solution<Position>>getSolutionMap();
	public void handleGenerateMaze(String name,int y,int x,int z);
	public void handleSaveMaze(String name,String path);
	public void handleLoadMaze(String path,String name);
	public void handleSolveMaze(String name,String algorithmUsed);
	public void handleExit();
}
