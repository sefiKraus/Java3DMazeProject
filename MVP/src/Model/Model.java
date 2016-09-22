package Model;

import java.util.HashMap;

import Presenter.Properties;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
/**
 * 
 * @author Krausz Sefi 305371320
 * @since 02/09/2016
 *
 */
public interface Model {

	public Object getDataFromModel(String required);
	public void handleDirContent(String path);
	public void handleGenerate(String name,int y,int x,int z);
	public HashMap<String, Maze3d> getMazeMap();
	public void handleSaveMaze(byte[] byteMaze,String path);
	public void handleLoadMaze(String path,String name);
	public void handleSolveMaze(String name,String algorithmUsed);
	public HashMap<Maze3d, Solution<Position>>getSolutionMap();
	public void handleExit();
	public void saveSolutionHashMapToZip();
	public void loadSolutionHashMapFromZip();
	public void handleChangeMazeStartPosition(String mazeName,Position position);
	public void handleLoadProperties(String xmlPath);
	public void handleSaveProperties(Properties prop,String xmlPath);
	
	
}
