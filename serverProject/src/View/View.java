package View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import serverProject.ClientHandler;

public interface View {

	public void start();
	public void showMessage(String message);
	public void showExit();
	public void showMazeList(Set<String> set);
	public void disconnectClient(String clientPort);
	public void showClientList(ArrayList<ClientHandler>clientList);
	public Object getDataFromView(String s);
	public void showSolvedList(HashMap<String, Maze3d>mazeMap,  HashMap<Maze3d, Solution<Position>> solutionMap);
}
