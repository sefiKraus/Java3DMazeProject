package Model;

/**
 * @author Krausz sefi 305371320
 * @since 02/09/2016
 */


import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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

public class MyModel extends CommonModel {
	/**
	 * Saving 3D mazes and their names inside mazeMap
	 * Saving 3D mazes and their Solutions inside solutionMap
	 * Saving notifications inside notifications
	 * Using threadPool
	 */
	private HashMap<String,Maze3d> mazeMap;
	private HashMap<Maze3d,Solution<Position>> solutionMap;
	private HashMap<String, Object> notifications;
	private ExecutorService threadPool;
	String settings;

	
	public MyModel() {
		this.mazeMap=new HashMap<String,Maze3d>();
		this.solutionMap=new HashMap<Maze3d,Solution<Position>>();
		this.notifications=new HashMap<String,Object>();
		this.threadPool=Executors.newFixedThreadPool(1);
	}

	/**
	 * @param name : Maze's name
	 * @param y : Maze's Levels
	 * @param x : Maze's Rows
	 * @param z : Maze's Columns
	 * 
	 * This method using Generator methods algorithms.mazeGenerators inside myAlgo.jar
	 * using callable because the generating needs to run in a different thread
	 */
	@Override
	public void handleGenerate(String name, int y, int x, int z) {
		
		if(!(this.mazeMap.containsKey(name)))
		{
			Future<Maze3d> futureMaze=threadPool.submit(new Callable<Maze3d>(){

				@Override
				public Maze3d call() throws Exception {
					Maze3dGenerator generator=new MyMaze3dGenerator();
					String overAllSize=String.valueOf(y)+","+String.valueOf(x)+","+String.valueOf(z);
					return generator.generate(overAllSize);
				}
				
				
			});
			try {
				this.mazeMap.put(name, futureMaze.get());
				this.noteObservers("Ready",name);
			}  catch (Exception e)
			{
				e.printStackTrace();
			}

		}
		else
		{
			this.noteObservers("Error", "Cannot generate maze because Name already exists");
		}
		
	}
	/**
	 * @param byteMaze : converted 3D maze to array of bytes
	 * @param path : the path the user wants to save the maze
	 * 
	 * This method using MyCompressorOutputStream inside myAlgo.jar
	 */
	@Override
	public void handleSaveMaze(byte[] byteMaze, String path) {
		try {
			MyCompressorOutputStream compressor=new MyCompressorOutputStream(new FileOutputStream(path));
			compressor.write(byteMaze);
			compressor.close();
			noteObservers("Save","Maze successfully saved in: "+path+ "size: "+byteMaze.length);
		} catch ( IOException e) {
			this.noteObservers("Error", "Encountered problem during saving proccess");
			e.printStackTrace();
		}
	}


	@Override
	public void handleLoadMaze(String path, String name) {
		try{
			if(!(this.mazeMap.containsKey(name)))
			{
				
			DataInputStream in=new DataInputStream(new FileInputStream(path));
			int size=in.readInt();
			int i=9;
			in.close();
			MyDecompressorInputStream decompressor=new MyDecompressorInputStream(new FileInputStream(path));
			byte[] byteMaze=new byte[size+i];
			decompressor.read(byteMaze);
			decompressor.close();
			Maze3d decompressedMaze=new Maze3d(byteMaze);
			this.mazeMap.put(name, decompressedMaze);
			this.noteObservers("Loaded","Maze successfully loaded from: "+path);
			}
			else
			{
				this.noteObservers("Error", "Maze: "+name+" already loaded");
			}
		}catch(Exception e)
		{
			this.noteObservers("Error", "Encountered problem during loading proccess");
			e.printStackTrace();
		}

		
	}
	@Override
	public void handleSolveMaze(String name, String algorithmUsed) {
		Future<Solution<Position>> solution=null;
		
		if((algorithmUsed.matches("[Dd][Ff][Ss]")||algorithmUsed.matches("[Bb][Ff][Ss]"))&& this.mazeMap.containsKey(name))
		{
			if(this.solutionMap.containsKey(this.mazeMap.get(name)))
			{
				this.noteObservers("Solved already",name);
				
			}
			else if(algorithmUsed.matches("[Dd][Ff][Ss]"))
			{
				solution=this.threadPool.submit(new Callable<Solution<Position>>() {

					@Override
					public Solution<Position> call() throws Exception {
						CommonSearcher<Position> searcher=new Dfs<Position>();
						Searchable3dMaze searchable3dMaze=new Searchable3dMaze(mazeMap.get(name));
						ArrayList<Position> solution=new ArrayList<Position>();
						solution=searcher.search(searchable3dMaze).getSolution();
						return new Solution<Position>(solution);
					}
				});
				try{
					this.solutionMap.put(this.mazeMap.get(name),solution.get());
					this.noteObservers("Solve", "Solution for: "+name+" with algorithm: "+algorithmUsed+" is ready");
				}catch(Exception e)
				{
					this.noteObservers("Error","Encountered problem during solving: "+name+" with algorithm: "+algorithmUsed);
					e.printStackTrace();
				}
			}
			else if(algorithmUsed.matches("[Bb][Ff][Ss]"))
			{
				solution=this.threadPool.submit(new Callable<Solution<Position>>() {

					@Override
					public Solution<Position> call() throws Exception {
						
						CommonSearcher<Position> searcher=new Bfs<Position>();
						Searchable3dMaze searchable3dMaze=new Searchable3dMaze(mazeMap.get(name));
						ArrayList<Position> solution=new ArrayList<Position>();
						solution=searcher.search(searchable3dMaze).getSolution();
						return new Solution<Position>(solution);
					}
				});
				try
				{
					this.solutionMap.put(this.mazeMap.get(name),solution.get());
					this.noteObservers("Solve", "Solution for: "+name+" with algorithm: "+algorithmUsed+" is ready");
				}
				catch(Exception e)
				{
					this.noteObservers("Error","Encountered problem during solving: "+name+" with algorithm: "+algorithmUsed);
					e.printStackTrace();
				}
			}
				
		}
		else
		{
			this.noteObservers("Error","Error solving maze with algorithm: "+algorithmUsed+" please enter valid algorithm");
		}
	}
	
	@Override
	public void handleExit() {
		this.threadPool.shutdown();
	
	}




	public void noteObservers(String s,Object data)
	{
		this.notifications.put(s,data);
		this.setChanged();
		this.notifyObservers(s);
	}
	
	

	@Override
	public void handleDirContent(String path) {
			
	}

	@Override
	public Object getDataFromModel(String required) {
		return notifications.get(required);
	}

	@Override
	public HashMap<String, Maze3d> getMazeMap() {
	
		return this.mazeMap;
	}

	@Override
	public HashMap<Maze3d, Solution<Position>> getSolutionMap() {
		return this.solutionMap;
	}

	
	
	@Override
	public void saveSolutionHashMapToZip() {
		HashMap<byte[],Solution<Position>> cashed=new HashMap<byte[],Solution<Position>>();
		Iterator<Maze3d>itr=solutionMap.keySet().iterator();
		while(itr.hasNext())
		{
			Maze3d maze=itr.next();
			try {
				cashed.put(maze.toByteArray(), solutionMap.get(maze));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fileOut=new FileOutputStream("solutions.zip");
			GZIPOutputStream zipOut=new GZIPOutputStream(fileOut);
			ObjectOutputStream objOut=new ObjectOutputStream(zipOut);
			objOut.writeObject(cashed);
			objOut.flush();
			objOut.close();
			fileOut.close();
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void loadSolutionHashMapFromZip() {
		HashMap<byte[], Solution<Position>> cashed=new HashMap<byte[],Solution<Position>>();
		try
		{
		FileInputStream fileIn=new FileInputStream("solutions.zip");
		GZIPInputStream zipIn=new GZIPInputStream(fileIn);
		ObjectInputStream objIn=new ObjectInputStream(zipIn);
		cashed=(HashMap)objIn.readObject();
		objIn.close();
		fileIn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		Set set=cashed.entrySet();
		Iterator itr=set.iterator();
		while(itr.hasNext())
		{
			
			Map.Entry entry=(Map.Entry)itr.next(); 
			try {
				this.solutionMap.put(new Maze3d((byte[])entry.getKey()),(Solution<Position>)entry.getValue());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}





	
}
