package Model;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import Controller.Controller;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Bfs;
import algorithms.search.CommonSearcher;
import algorithms.search.Dfs;
import algorithms.search.Searchable3dMaze;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
/**
 * 
 * @author Krausz sefi
 * @since 07/09/2016
 *
 */
public class MyModel implements Model {

	private Controller controller;
	private HashMap<String, Maze3d>mazeMap;
	private HashMap<Maze3d, Solution<Position>>solutionMap;
	private ExecutorService threadPool;
	String settings;
	
	public MyModel(Controller controller) {
		this.controller=controller;
		this.mazeMap=new HashMap<String,Maze3d>();
		this.solutionMap=new HashMap<Maze3d,Solution<Position>>();
		this.threadPool=Executors.newFixedThreadPool(1);
		
	}
	public MyModel() {
		this.mazeMap=new HashMap<String,Maze3d>();
		this.solutionMap=new HashMap<Maze3d,Solution<Position>>();
		this.threadPool=Executors.newFixedThreadPool(1);
	}
	
	/**
	 * This method generates maze by size: y,x,z, using future<Maze3d> and callable
	 * 
	 * 
	 * @param name maze name
	 * @param y 
	 * @param x
	 * @param z
	 */
	@Override
	public void handleGenerateMaze(String name, int y, int x, int z) {
		if(!this.mazeMap.containsKey(name)) //If there is a need to create a new maze
		{
		Future<Maze3d> futureMaze=this.threadPool.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				Maze3dGenerator generator=new MyMaze3dGenerator();
				String overAllSize=String.valueOf(y)+","+String.valueOf(x)+","+String.valueOf(z);
				return generator.generate(overAllSize);
			}
		});
		try
		{
			this.mazeMap.put(name, futureMaze.get());
			this.controller.sendToView("Maze: "+name+" is ready");
		}
		catch(Exception e)
		{
			this.controller.sendToView("Encountered error during generating maze: "+name);
			e.printStackTrace();
		}
		}
		else
		{
			this.controller.sendToView("Maze: "+name+" is already in database");
		}
	}
	/**
	 * This method handle saving the maze:"name" in "path"
	 * this method also using MyCompressorOutputStream class
	 * @param name
	 * @param path
	 */
	@Override
	public void handleSaveMaze(String name, String path) {
		try
		{
			if(!this.mazeMap.containsKey(name))
			{
				this.controller.sendToView("Maze: "+name+" does not exsists in data please check maze name typed");
			}
			else
			{
				MyCompressorOutputStream compressor=new MyCompressorOutputStream(new FileOutputStream(path));
				Maze3d maze=this.mazeMap.get(name);
				compressor.write(maze.toByteArray());
				compressor.close();
				this.controller.sendToView("Maze successfully saved in: "+path+ "size: "+maze.toByteArray().length);
			}
		}
		catch(Exception e)
		{
			this.controller.sendToView("Encountered error during saving maze: "+name+" in: "+path);
			e.printStackTrace();
		}
	}
	/**
	 * This method handle load maze from: "path" into maze:"name",using MyDecompressorInputStream class 
	 * @param path
	 * @param name
	 */
	@Override
	public void handleLoadMaze(String path, String name) {
		try
		{
			if(this.mazeMap.containsKey(name))
			{
				this.controller.sendToView("Maze: "+name+" already exsists in data please check maze name typed");
			}
			else
			{
				DataInputStream input= new DataInputStream(new FileInputStream(path));
				int size=input.readInt();
				input.close();
				int i=9;
				byte[] byteMaze=new byte[size+i];
				MyDecompressorInputStream decompressor=new MyDecompressorInputStream(new FileInputStream(path));
				decompressor.read(byteMaze);
				decompressor.close();
				Maze3d decompressedMaze=new Maze3d(byteMaze);
				this.mazeMap.put(name, decompressedMaze);
				this.controller.sendToView("Maze successfully loaded from: "+path);
			}
		}
		catch(Exception e)
		{
			this.controller.sendToView("Encountered error during saving maze: "+name+" in: "+path);
			e.printStackTrace();
		}
	}
	
	/**
	 * This method handles solving the maze:"name" with algorithm:"algorithmUSed"
	 * @param name
	 * @param algorithmUsed
	 */
	@Override
	public void handleSolveMaze(String name, String algorithmUsed) {
		Future<Solution<Position>>solution=null;
		if((algorithmUsed.matches("[Dd][Ff][Ss]")||(algorithmUsed.matches("[Bb][Ff][Ss]")))&&(this.mazeMap.containsKey(name)))
		{
			if(this.solutionMap.containsKey(this.mazeMap.get(name)))
			{
				this.controller.sendToView("Solution for maze: "+name+" exsists please type display "+name+" to display the solution");
			}
			else if(algorithmUsed.matches("[Dd][Ff][Ss]"))
			{
				solution=this.threadPool.submit(new Callable<Solution<Position>>() {

					@Override
					public Solution<Position> call() throws Exception {
						CommonSearcher<Position> searcher=new Dfs<Position>();
						Searchable3dMaze maze= new Searchable3dMaze(mazeMap.get(name));
						ArrayList<Position>solution= new ArrayList<Position>();
						solution=searcher.search(maze).getSolution();
						return new Solution<Position>(solution);
					}
				});
				try
				{
					this.solutionMap.put(this.mazeMap.get(name), solution.get());
					this.controller.sendToView("Solution for: "+name+" with algorithm: "+algorithmUsed+" is ready");
				}
				catch(Exception e)
				{
					this.controller.sendToView("Encountered error during solving: "+name+" with algorithm: "+algorithmUsed);
					e.printStackTrace();
				}
			}
			else if(algorithmUsed.matches("[Bb][Ff][Ss]"))
			{
				solution=this.threadPool.submit(new Callable<Solution<Position>>() {

					@Override
					public Solution<Position> call() throws Exception {
						CommonSearcher<Position> searcher=new Bfs<Position>();
						Searchable3dMaze maze= new Searchable3dMaze(mazeMap.get(name));
						ArrayList<Position>solution=new ArrayList<Position>();
						solution=searcher.search(maze).getSolution();
						return new Solution<Position>(solution);
					}
				});
				try
				{
					this.solutionMap.put(this.mazeMap.get(name), solution.get());
					this.controller.sendToView("Solution for: "+name+" with algorithm: "+algorithmUsed+" is ready");
				}
				catch(Exception e)
				{
					
					this.controller.sendToView("Encountered error during solving: "+name+" with algorithm: "+algorithmUsed);
					e.printStackTrace();
				}
			}
			
		}
		else
		{
			this.controller.sendToView("Maze: "+ name+" does not exsists in data please check name entered");
		}
		
	}
	/**
	 * This method is been used when user types "exit"
	 */
	@Override
	public void handleExit() {
		this.threadPool.shutdown();
	}

	@Override
	public HashMap<String, Maze3d> getMazeMap() {
		return this.mazeMap;
	}
	@Override
	public HashMap<Maze3d, Solution<Position>> getSolutionMap() {
		return this.solutionMap;
	}


}
