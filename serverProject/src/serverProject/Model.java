package serverProject;

import java.net.ServerSocket;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface Model {
	
	public HashMap<String, Maze3d> getMazeMap();
	public HashMap<String, Object> getNotificationsMap();
	public HashMap<Maze3d, Solution<Position>> getSolutionMap();
	public void loadProperties(String path);
	public Properties getServerProperties();
	public Object getData(String required);
	public void saveSolutionHashMapToZip();
	public void loadSolutionHashMapFromZip();
	public void handleSaveMaze(byte[] byteMaze,String path);
	public void handleLoadMaze(String path,String name);
	public void handleSolveMaze(String name,String algorithmUsed);
	public void handleDirContent(String path);
	public void handleGenerate(String name,int y,int x,int z);
	public void handleExit();
	public ServerSocket getServerSocket();
	public int getServerPort();
	public void activateMethod(String name,Object[] args);
	public Maze3d getMazeByName(String name);
	public void start(MyServerModel model);
	
	
	
}
