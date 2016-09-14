package serverProject;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface Model {
	public HashMap<String, Maze3d> getMazeMap();
	public HashMap<Maze3d,Solution<Position>> getSolutionMap();
	public HashMap<String, Object> getNotifications();
	public ServerSocket getServerSocket();
	public int getServerPort();
	public void handleGenerate(Socket clientSocket,String name,int y,int x,int z);
	public void handleSolveMaze(Socket clientSocket,String name,String algorithmUsed);
	public void handleExit();
	public void saveSolutionHashMapToZip();
	public void loadSolutionHashMapFromZip();
	
}
