package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface ClientView {

	public void start();
	
	public void showDirContent(String path);
	public void showMessage(String message);
	public void showGeneratedMaze(byte[] byteMaze);
	public Object getDataFromView(String s);
	public void showGeneratedMazeName(String name);
	public void showExit();
	public void showCrossSection(byte[] byteMaze,String axies,int index,String name);
	public void showSolutionList(HashMap<Maze3d,Solution<Position>>map);
	public void showSolution(Solution<Position>solution);

}
