package View;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import Controller.Controller;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
/**
 * 
 * @author Krausz sefi
 * @since 07/09/2016
 *
 */
public class MyView implements View {

	 CLI cli;
	private Controller controller;
	
	
	public MyView(Controller controller) {
		this.controller=controller;
	this.cli=new CLI(new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out),controller.getCommandMap());
		
	}

	/**
	 * This method displaying byteMaze after converting is from array of byte to Maze3d object
	 * @param byte[] byteMaze
	 */
	@Override
	public void showGeneratedMaze(byte[] byteMaze) {
		try {
			Maze3d maze=new Maze3d(byteMaze);
			this.showMessage("----------------------------------------------");
			this.showMessage("start Position: "+ maze.getStartPosition().toString());
			this.showMessage("Goal Position: "+ maze.getGoalPosition().toString());
			this.showMessage("----------------------------------------------");
			
			for(int y=0;y<maze.getSizeY();y++)
			{
				this.showMessage("-----------------------[Level: "+y+"]-----------------------");
				for(int x=0;x<maze.getSizeX();x++)
				{
				
					for(int z=0;z<maze.getSizeZ();z++)
					{
						System.out.print(maze.getPositionValueByIndex(y, x, z)+",");
					}
					this.showMessage("");
				}
				this.showMessage("--------------------------------------------------------");			
		}
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * This method displaying all files and directories in path
	 * @param path
	 */
	@Override
	public void showDirContent(String path) {
		File dest= new File(path);
		try
		{
			this.showMessage("--------------------------------------------------------");
			String[] pathDetails=dest.list();
			for (String s : pathDetails) {
				this.showMessage(s);
			}
			this.showMessage("--------------------------------------------------------");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * This method displaying cross section by axis index for byteMaze after converting is to Maze3d object with name :"name"
	 * @param byte[] byteMaze
	 * @param String axis
	 * @param int index
	 * @param String name
	 */
	@Override
	public void showCrossSection(byte[] byteMaze, String axis, int index,String name) {
		try {
			Maze3d maze=new Maze3d(byteMaze);
			int[][] maze2D;
			switch (axis.toLowerCase()) {
			case "x":
			{
				if(index<0 || index>maze.getSizeX()-1)
				{
					this.showMessage("Index is out of bounds");

				}
				else
				{
					this.showMessage("Displaying cross section by X the index is: "+index+" for maze: "+name);
					this.showMessage("----------------------------------------------");

					maze2D=maze.getCrossSectionByX(index);
					for(int y=0;y<maze2D.length;y++)
					{
						for(int z=0;z<maze2D[0].length;z++)
						{
							System.out.print(maze2D[y][z]+",");

						}
						System.out.println();

					
					}
					this.showMessage("----------------------------------------------");

				}
			}
			break;
			case "y":
			{
				if(index<0 || index>maze.getSizeY()-1)
				{
					this.showMessage("Index is out of bounds");

				}
				else
				{
					this.showMessage("Displaying cross section by Y the index is: "+index+" for maze: "+name);
					this.showMessage("----------------------------------------------");
					maze2D=maze.getCrossSectionByY(index);
					for(int x=0;x<maze2D.length;x++)
					{
						for(int z=0;z<maze2D[0].length;z++)
						{
							System.out.print(maze2D[x][z]+",");

						}
						System.out.println();

					}

				}
				this.showMessage("----------------------------------------------");

			}
			break;
			case "z":
			{
				if(index<0 || index>maze.getSizeZ()-1)
				{
					this.showMessage("Index is out of bounds");

				}
				else
				{
					this.showMessage("Displaying cross section by Z the index is: "+index+" for maze: "+name);
					this.showMessage("----------------------------------------------");
					maze2D=maze.getCrossSectionByZ(index);
					for(int y=0;y<maze2D.length;y++)
					{
						for(int x=0;x<maze2D[0].length;x++)
						{
							System.out.print(maze2D[y][x]+",");

						}
						System.out.println();

					}
				}
				this.showMessage("----------------------------------------------");

			}
			break;
			default:
				this.showMessage("Error displaying cross section by: "+axis+" please enter correct axis for more information type help");
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method displaying the solution for maze name given in cli
	 * @param Solution<Position> solution
	 */
	@Override
	public void showSolution(Solution<Position> solution) {
		if(solution!=null)
		{
			ArrayList<Position>pointList=solution.getSolution();
			for (Position position : pointList) {
				System.out.print("("+position.toString()+") ");
			}
			System.out.println();
		}		
	}
	@Override
	public void Exit() {
		this.cli.setFlag(false);
	}
	@Override
	public void showMessage(String message) {
		this.cli.getOut().println(message);
		this.cli.getOut().flush();
	}




	@Override
	public void start() {
		this.cli.start();
	}

	public void setCli(CLI cli) {
		this.cli = cli;
	}
	public Controller getController() {
		return controller;
	}
	public void setController(Controller controller) {
		this.controller = controller;
	}
	public CLI getCli()
	{
		return this.cli;
	}




	
}
