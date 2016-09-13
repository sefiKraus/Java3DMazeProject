package serverProject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MyServerModel extends CommonServerModel{

	
	
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, Maze3d> getMazeMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Object> getNotificationsMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<Maze3d, Solution<Position>> getSolutionMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadProperties(String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Properties getServerProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getData(String required) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveSolutionHashMapToZip() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadSolutionHashMapFromZip() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleSaveMaze(byte[] byteMaze, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleLoadMaze(String path, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleSolveMaze(String name, String algorithmUsed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleDirContent(String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleGenerate(String name, int y, int x, int z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleExit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServerSocket getServerSocket() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void activateMethod(String name,Object[] args) {
		for (Method method : this.getClass().getDeclaredMethods()) {
			  if(method.getName().toString().equals(name)&&method.isAccessible())
			  {
				  try {
					method.invoke(name, args);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
		}		
	}

	@Override
	public Maze3d getMazeByName(String name) {
		for (String string : this.getMazeMap().keySet()) {
			if(string.equals(name))
			{
				this.notifyObservers("Display maze");
				return this.getMazeMap().get(string);
			}
		}
		return null;
	}

}
