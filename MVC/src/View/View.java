package View;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
/**
 * 
 * @author Krausz sefi
 * @since 07/09/2016
 *
 */
public interface View {
	public void start();
	public void showMessage(String message);
	public void showDirContent(String path);
	public void showGeneratedMaze(byte[] byteMaze);
	public void showCrossSection(byte[] byteMaze,String axis,int index,String name);
	public void showSolution(Solution<Position>solution);
	public void Exit();
	public CLI getCli();
	
}
