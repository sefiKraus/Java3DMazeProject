package View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import serverProject.ClientHandler;

public class MyGuiView extends CommonGuiView{

	private HashMap<String, Maze3d>mazeMap;
	private HashMap<String, Object>notifications;
	private ArrayList<ClientHandler> clientList;
	
	Composite dataDisplayer,clientForm,mazeForm;
	
	
	public MyGuiView(String windowTitle, int width, int height) {
		super(windowTitle, width, height);
		this.notifications=new HashMap<String,Object>();
		this.clientList=new ArrayList<ClientHandler>();
		this.mazeMap=new HashMap<String,Maze3d>();
		
	}
	
	
	@Override
	void initWidgets() {
		
	}


	@Override
	public void start() {
		
		this.run();
	}


	


	@Override
	public void showMessage(String message) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void showExit() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void showMazeList(Set<String> set) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void disconnectClient(String clientPort) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void showClientList(ArrayList<ClientHandler>clientList) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object getDataFromView(String s) {
		return null;
	}

}
