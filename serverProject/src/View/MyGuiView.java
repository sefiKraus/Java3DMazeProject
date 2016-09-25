package View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import serverProject.ClientHandler;

public class MyGuiView extends CommonGuiView{

	private HashMap<String, Maze3d>mazeMap;
	private HashMap<String, Object>notifications;
	private ArrayList<ClientHandler> clientList;
	HashMap<String, Solution<Position>>mazeSolMap;

	Composite dataDisplayer,clientForm,mazeForm,solvedForm;
	
	
	Menu menuBar,fileMenu,aboutMenu,propertiesMenu;
	MenuItem fileMenuHeader,aboutMenuHeader,propertiesMenuHeader;
	MenuItem saveFileItem,loadFileItem,exitFromGame,openPropertiesItem;
	
	Label clientListLabel,mazeLabel,solvedMazeLabel,mazeNameLabel;
	
	List clientPortList,mazeNameList,solvedMazeList;
	
	
	MessageBox messageBox;

	Button displayClientListBtn,displaySolvedListBtn,displayMazeListBtn,kickClientBtn;
	
	public MyGuiView(String windowTitle, int width, int height) {
		super(windowTitle, width, height);
		this.notifications=new HashMap<String,Object>();
		this.clientList=new ArrayList<ClientHandler>();
		this.mazeMap=new HashMap<String,Maze3d>();
		this.mazeSolMap=new HashMap<String, Solution<Position>>();

		
	}
	
	
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2,false));
		shell.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				notifications.put("exit"," ");
				setChanged();
				notifyObservers("exit");
				shell.dispose();
			}
		});
		
		
		/*--------------------[This is Menu Bar]--------------------*/
		menuBar=new Menu(shell,SWT.BAR);
		
		/*--[File Header and items]--*/
		fileMenuHeader=new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");
		
		fileMenu=new Menu(shell,SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);
		
		saveFileItem=new MenuItem(fileMenu, SWT.PUSH);
		saveFileItem.setText("&Save solutions");
		
		loadFileItem=new MenuItem(fileMenu, SWT.PUSH);
		loadFileItem.setText("&Load solutions");
		
		exitFromGame=new MenuItem(fileMenu, SWT.PUSH);
		exitFromGame.setText("&Exit");
		
		exitFromGame.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.dispose();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {				
			}
		});
		
		/*--[Properties Header and items]--*/
		
		propertiesMenuHeader=new MenuItem(menuBar, SWT.CASCADE);
		propertiesMenuHeader.setText("&Properties");
		
		propertiesMenu=new Menu(shell,SWT.DROP_DOWN);
		propertiesMenuHeader.setMenu(propertiesMenu);
		
		openPropertiesItem=new MenuItem(propertiesMenu, SWT.PUSH);
		openPropertiesItem.setText("My Properties");
		
		
		/*--[About Header and items]--*/

		aboutMenuHeader=new MenuItem(menuBar, SWT.PUSH);
		aboutMenuHeader.setText("&About");
	
		aboutMenuHeader.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageBox info=new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
				info.setText("About Information");
				info.setMessage("First Name: Sefi\nLast Name: Krausz\nID: 305371320\nEmail: sefik1600@gmail.com");
				info.open();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
			
		shell.setMenuBar(menuBar);
		super.getShell().setBackgroundImage(new Image(null, "res/images/backGround.png"));
		Image backImage=new Image(null, "res/images/ezWall.png");
		/*------------------------------------------------------------------*/

		final StackLayout StackLayout=new StackLayout();

		/*--------------------[This is display client list Button]--------------------*/
		displayClientListBtn=new Button(shell, SWT.PUSH);
		displayClientListBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		displayClientListBtn.setText("Open Client List Connected");
			
		/*--------------------[This is another composite in main window]--------------------*/
			dataDisplayer=new Composite(shell, SWT.BORDER);
			dataDisplayer.setLayout(StackLayout);
			dataDisplayer.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,4));
			dataDisplayer.setBackgroundImage(backImage);
			
		/*--------------------[This is display maze list Button]--------------------*/
			displayMazeListBtn=new Button(shell, SWT.PUSH);
			displayMazeListBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			displayMazeListBtn.setText("Open Maze List");
			
			/*--------------------[This is display solution list Button]--------------------*/
			displaySolvedListBtn=new Button(shell, SWT.PUSH);
			displaySolvedListBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			displaySolvedListBtn.setText("Open Solved List");	
			
		/*---------------------------[Creating Forms]----------------------------*/
			
			/*----------------------[connected client form]----------------------*/
			
			clientForm=new Composite(dataDisplayer, SWT.BORDER);
			clientForm.setLayout(new GridLayout(2,false));
			clientForm.setBackgroundImage(backImage);
			
			clientListLabel=new Label(clientForm, SWT.NONE);
			clientListLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			clientListLabel.setText("Client List");
			clientListLabel.pack();
			
			clientPortList=new List(clientForm, SWT.DROP_DOWN|SWT.READ_ONLY);
			clientPortList.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
			clientPortList.pack();
			
			kickClientBtn=new Button(clientForm, SWT.PUSH);
			kickClientBtn.setLayoutData(new GridData(SWT.BOTTOM,SWT.BOTTOM,false,false,1,1));
			kickClientBtn.setText("Kick Client");
			kickClientBtn.pack();
			
			
			/*----------------------[Maze List form]----------------------*/

			mazeForm=new Composite(dataDisplayer, SWT.BORDER);
			mazeForm.setLayout(new GridLayout(2,false));
			mazeForm.setBackgroundImage(backImage);

			
			mazeLabel=new Label(mazeForm, SWT.NONE);
			mazeLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			mazeLabel.setText("Maze List");
			mazeLabel.pack();
			
			mazeNameList=new List(mazeForm, SWT.DROP_DOWN|SWT.READ_ONLY);
			mazeNameList.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
			mazeNameList.pack();
			
			/*----------------------[Solved List form]----------------------*/
			
			solvedForm=new Composite(dataDisplayer, SWT.BORDER);
			solvedForm.setLayout(new GridLayout(2,false));
			solvedForm.setBackgroundImage(backImage);

			solvedMazeLabel=new Label(solvedForm, SWT.NONE);
			solvedMazeLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			solvedMazeLabel.setText("Solved Maze List");
			solvedMazeLabel.pack();
			
			solvedMazeList=new List(solvedForm, SWT.DROP_DOWN|SWT.READ_ONLY);
			solvedMazeList.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
			solvedMazeList.pack();
			
			
			/*********************Listeners handle*********************/
			
			displayClientListBtn.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					StackLayout.topControl=clientForm;
					dataDisplayer.layout();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			displayMazeListBtn.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					notifications.put("MazeList", "get maze list");
					setChanged();
					notifyObservers("MazeList");
					StackLayout.topControl=mazeForm;
					dataDisplayer.layout();
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			displaySolvedListBtn.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					notifications.put("SolutionListRequest","display solution list");
					setChanged();
					notifyObservers("SolutionListRequest");
					StackLayout.topControl=solvedForm;
					dataDisplayer.layout();	
					StringBuilder builder=new StringBuilder();
					for (String name : mazeSolMap.keySet()) {
						builder.append(name+" ");
					}
					String[] items=builder.toString().split(" ");
					solvedMazeList.setItems(items);
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
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

	}


	@Override
	public void showMazeList(Set<String> set) {
		if(!set.isEmpty())
		{

		Iterator<String>itr=set.iterator();
		StringBuilder builder=new StringBuilder();
		while(itr.hasNext())
		{
			builder.append(itr.next()+" ");
		}
		mazeNameList.removeAll();
		mazeNameList.setItems(builder.toString().split(" "));		
		
	}
	}


	@Override
	public void disconnectClient(String clientPort) {
		
	}


	@Override
	public void showClientList(ArrayList<ClientHandler>clientList) {
		
		Iterator<ClientHandler> clientItr=clientList.iterator();
		StringBuilder builder=new StringBuilder();
		while(clientItr.hasNext())
		{
			builder.append(clientItr.next().getClientSocket().toString()+" ");
		}
		String[] splited=builder.toString().split(" ");
		clientPortList.setItems(splited);
	}


	@Override
	public Object getDataFromView(String s) {
		return this.notifications.get(s);
	}


	@Override
	public void showSolvedList(HashMap<String, Maze3d>mazeMap,  HashMap<Maze3d, Solution<Position>> solutionMap) {
		Iterator<Maze3d>mazeItr=solutionMap.keySet().iterator();
		while(mazeItr.hasNext())
		{
			Maze3d maze=mazeItr.next();
			for (String mazeName : mazeMap.keySet()) {
				if(maze.equals(mazeMap.get(mazeName))&& !mazeSolMap.containsKey(mazeName))
				{
					mazeSolMap.put(mazeName, solutionMap.get(maze));
					
				}
			}
			
		}
		StringBuilder builder=new StringBuilder();
		solvedMazeList.removeAll();
		for (String mazeName : mazeSolMap.keySet()) {
			builder.append(mazeName + " ");
		}
		String[] items=builder.toString().split(" ");
		solvedMazeList.setItems(items);
		
	}

}
