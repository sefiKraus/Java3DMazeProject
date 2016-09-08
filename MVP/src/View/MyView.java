package View;
/**
 * @author Krausz Sefi 305371320
 * @since 02/09/2016
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MyView extends CommonView implements Observer,View {

	 CLI cli;
	 private String command;
	 
	 public MyView() {
	 this.cli=new CLI(new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out));
	 this.cli.addObserver(this);
	 }
	
	@Override
	public void start() {
		new Thread(this.cli).start();
	}

	/**
	 * 
	 * Print file's and folder's name in path
	 */
	@Override
	public void showDirContent(String path) {
			File dest=new File(path);
			try{
				this.showMessage("--------------------------------------------------------");
				String[] pathDetails=dest.list();
				for (String s : pathDetails) {
					this.showMessage(s);
				}
				this.showMessage("--------------------------------------------------------");

			}
			catch(NullPointerException n)
			{
				this.showMessage("File or directory not found please check again");
			}
	}
	
	/**
	 * 
	 * Displaying the maze requested by the user
	 * @param byteMaze - Compressed 3D Maze
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
			
		} catch (IOException e) {
			this.showMessage("Error generating the maze");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This update method receives commands from CLI and notifying the Presenter
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		if(o==cli)
		{
			this.command=(String)arg;
			this.setChanged();
			System.out.println("myView recived: "+this.command);
			this.notifyObservers("CLI");
		}
	}
	
	@Override
	public void showCrossSection(byte[] byteMaze, String axies, int index,String name) {
		try {
			Maze3d maze=new Maze3d(byteMaze);
			int[][] maze2D;
			switch (axies.toLowerCase()) {
			case "x":
			{
				if(index<0 || index> maze.getSizeX()-1)
				{
					this.showMessage("Index is out of bounds");
				}
				else
				{
					this.showMessage("Displaying cross section by X the index is: "+index+" for maze: "+name);
					maze2D= maze.getCrossSectionByX(index);
					for(int y=0;y<maze2D.length;y++)
					{
						for(int z=0;z<maze2D[0].length;z++)
						{
							System.out.print(maze2D[y][z]+",");
						}
						System.out.println();
					}
					
				}
			}
			break;
			case "y":
			{
				if(index<0 || index> maze.getSizeY()-1)
				{
					this.showMessage("Index is out of bounds");

				}
				else
				{
					this.showMessage("Displaying cross section by Y the index is: "+index+" for maze: "+name);
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
			}
				break;
			case "z":
			{
				if(index<0 || index> maze.getSizeZ()-1)
				{
					this.showMessage("Index is out of bounds");

				}
				else
				{
					this.showMessage("Displaying cross section by Z the index is: "+index+" for maze: "+name);
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
			}
				break;

			default:
				this.showMessage("Error displaying Cross section please type correct axies <X|Y|Z> or <x|y|z>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	@Override
	public void showGeneratedMazeName(String name) {
		this.showMessage("Maze "+ name+" is ready");
	}
/**
 * This method using Solution<T> , T= Position from myAlgo.jar
 * @param solution: solution recived from the model 
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

	/**
	 * Displaying message to the user
	 * @param message
	 */
	@Override
	public void showMessage(String message) {
		this.cli.getOut().println(message);
		this.cli.getOut().flush();
	}
	@Override
	public void showExit() {
		this.cli.setFlag(false);
	}

	public CLI getCli() {
		return cli;
	}

	public void setCli(CLI cli) {
		this.cli = cli;
		cli.addObserver(this);
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
		this.hasChanged();
		this.notifyObservers(this.command);
	}

	@Override
	public Object getDataFromView(String s) {
		return command;
	}

/*	@Override
	public void showSolutionList(HashMap<Maze3d, Solution<Position>> map) {
		if(!map.isEmpty())
		{
			Iterator<Maze3d> itr=map.keySet().iterator();
			while(itr.hasNext())
			{
				Maze3d maze=itr.next();
				try {
					this.showGeneratedMaze(maze.toByteArray());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.showSolution(map.get(maze));
			}
		}
	}*/





	

	

}
