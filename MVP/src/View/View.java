package View;


import java.util.HashMap;
import java.util.Set;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
/**
 * @author Krausz Sefi 305371320
 * @since 02/09/2016
 */
public interface View {

	public void start();
	
	public void showDirContent(String path);
	public void showMessage(String message);
	public void showGeneratedMaze(byte[] byteMaze);
	public Object getDataFromView(String s);
	public void showGeneratedMazeName(String name);
	public void showExit();
	public void showCrossSection(byte[] byteMaze,String axies,int index,String name);
	public void showSolutionList(HashMap<String, Maze3d>mazeMap,HashMap<Maze3d,Solution<Position>>solutionMap);
	public void showSolution(String mazeName,Solution<Position>solution);
	public void showMazeList(Set<String> set);
	public void showAutoSolution(String mazeName,Maze3d maze);
	
}
