package View;


import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import Model.PropertiesXmlHandler;
import Presenter.Properties;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import widgets.MazeDisplay;
import widgets.MyMazeDisplayWidgets;
/**
 * 
 * @author Krausz Sefi 305371320
 * @since 10/09/2016
 */
public class MyGuiView extends CommonGuiView{

	HashMap<String, Object>notifications;
	Button generateBtn,submitMazeData,displayBtn,
			submitMazeRequiered,saveSubmit
			,solveBtn,submitSolveMaze,displaySolutionBtn,submitSolution;
	
	Text  mazeNameText,yAxisText,xAxisText,zAxisText,mazeName,dirPathText;
	
	MessageBox messageBox;
	
	
	Label mazeLabel,yAxisLabel,xAxisLabel, zAxisLabel,mazeNameLabel
			,dirPathLabel,solveMazeLabel,solveWithAlgo,displaySolutionFor;
	
	Composite dataDisplayer,mazeForm,displayMazeForm,dirForm,saveForm,
				solveForm,displaySolutionForm;
	
	MazeDisplay mazeDisplayer;
	private MouseWheelListener mouseWheelListener;

	HashMap<String, Solution<Position>>mazeSolMap;
	Maze3d maze;
	
	KeyListener keyListener;
	
	List mazeList,solutionList,solveMazeList,algoList;
	
	Shell mazeShell;
	
	Timer timer;
	
	TimerTask myTimerTask;
	
	Menu menuBar,fileMenu,aboutMenu,propertiesMenu;
	MenuItem fileMenuHeader,aboutMenuHeader,propertiesMenuHeader;
	MenuItem saveFileItem,loadFileItem,exitFromGame,openPropertiesItem,saveSolutionMap,loadSolutionMap;
	
	/**
	 * 
	 * @param windowTitle
	 * @param width
	 * @param height
	 */
	public MyGuiView(String windowTitle, int width, int height) {
		super(windowTitle, width, height);
		this.notifications=new HashMap<String,Object>();
		this.mazeSolMap=new HashMap<String, Solution<Position>>();
		
	}
/**
 * Initiating every widget inside the gui including labels, texts, buttons, menus 
 */
	@Override
	void initWidgets() {
		
		this.initLisenerts();
		shell.setLayout(new GridLayout(2,false));
		
		shell.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				notifications.put("Exit", " ");
				setChanged();
				notifyObservers("Exit");
				shell.dispose();
			}
		});
		
		//shell.setFullScreen(true);
		/*--------------------[This is Menu Bar]--------------------*/
		menuBar=new Menu(shell,SWT.BAR);
		
		/*--[File Header and items]--*/
		fileMenuHeader=new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");
		
		fileMenu=new Menu(shell,SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);
		
		saveFileItem=new MenuItem(fileMenu, SWT.PUSH);
		saveFileItem.setText("&Save Maze");
		
		loadFileItem=new MenuItem(fileMenu, SWT.PUSH);
		loadFileItem.setText("&Load Maze");
		
		saveSolutionMap=new MenuItem(fileMenu, SWT.PUSH);
		saveSolutionMap.setText("&Save Solution Map");
		
		loadSolutionMap=new MenuItem(fileMenu, SWT.PUSH);
		loadSolutionMap.setText("&Load Solution Map");
		
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
		
		/*--------------------[This is Generate Button]--------------------*/
		 generateBtn=new Button(shell, SWT.PUSH);
		generateBtn.setLayoutData(new GridData(SWT.None,SWT.NONE,false,false,1,1));
		generateBtn.setText("Generate 3D Maze");


		/*--------------------[This is another composite in main window]--------------------*/
		 dataDisplayer=new Composite(shell, SWT.BORDER);
		dataDisplayer.setLayout(StackLayout);
		dataDisplayer.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,4));
		dataDisplayer.setBackgroundImage(backImage);
		
		
		/*--------------------[Maze Properties form after pressing Generate 3D maze button]--------------------*/
			 mazeForm=new Composite(dataDisplayer,SWT.BORDER);
			mazeForm.setLayout(new GridLayout(2,false));
			mazeForm.setBackgroundImage(backImage);
			
			 mazeLabel=new Label(mazeForm, SWT.NONE); //Maze Name
			mazeLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			mazeLabel.setText("Enter Maze Name Here:");
			mazeLabel.pack();
			
			
			 mazeNameText=new Text(mazeForm, SWT.BORDER|SWT.SINGLE);
			mazeNameText.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
			mazeNameText.pack();
			
			 yAxisLabel=new Label(mazeForm, SWT.NONE);  //Y properties
			yAxisLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			yAxisLabel.setText("Enter Y (Levels):");
			yAxisLabel.pack();
			
			 yAxisText=new Text(mazeForm, SWT.BORDER|SWT.SINGLE);
			yAxisText.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
			yAxisText.pack();
			
			xAxisLabel=new Label(mazeForm, SWT.NONE); //X properties
			xAxisLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			xAxisLabel.setText("Enter X (Rows):");
			xAxisLabel.pack();
			
			 xAxisText=new Text(mazeForm, SWT.BORDER|SWT.SINGLE);
			xAxisText.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
			xAxisText.pack();
			
			 zAxisLabel=new Label(mazeForm, SWT.NONE);//Z properties
			zAxisLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			zAxisLabel.setText("Enter Z (Columns):");
			zAxisLabel.pack();
			
			 zAxisText=new Text(mazeForm, SWT.BORDER|SWT.SINGLE);
			zAxisText.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
			zAxisText.pack();
			
			
		    submitMazeData=new Button(mazeForm, SWT.PUSH);// submit button
			submitMazeData.setLayoutData(new GridData(SWT.BOTTOM,SWT.BOTTOM,false,false,1,1));
			submitMazeData.setText("Submit Your Maze Properties");
			submitMazeData.pack();
			
			
		

	/*****************************************************************************************/
		
		/*--------------------[Display Maze Button]--------------------*/

		 displayBtn=new Button(shell, SWT.PUSH);
		displayBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		displayBtn.setText("Display Maze");
		
		 displayMazeForm=new Composite(dataDisplayer, SWT.BORDER);
		displayMazeForm.setLayout(new GridLayout(2,false));
		displayMazeForm.setBackgroundImage(backImage);
		mazeNameLabel=new Label(displayMazeForm, SWT.NONE);
		mazeNameLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		mazeNameLabel.setText("Enter Requiered Maze Name:");
		mazeNameLabel.pack();
		
		 mazeName=new Text(displayMazeForm, SWT.SINGLE|SWT.BORDER);
		mazeName.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
		mazeName.pack();
		
		 submitMazeRequiered=new Button(displayMazeForm, SWT.PUSH);// submit button
		submitMazeRequiered.setLayoutData(new GridData(SWT.BOTTOM,SWT.BOTTOM,false,false,1,1));
		submitMazeRequiered.setText("Submit Your Maze Name");
		submitMazeRequiered.pack();
		
		
		
		/*--------------------[Solve Maze Button]--------------------*/
			solveBtn=new Button(shell, SWT.PUSH);
			solveBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			solveBtn.setText("Solve Maze");
			
			solveForm=new Composite(dataDisplayer, SWT.BORDER);
			solveForm.setLayout(new GridLayout(2,false));
			solveForm.setBackgroundImage(backImage);
			solveMazeLabel=new Label(solveForm, SWT.NONE);
			solveMazeLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			solveMazeLabel.setText("Choose Maze To Solve: ");
			solveMazeLabel.pack();
			
			solveMazeList=new List(solveForm, SWT.V_SCROLL|SWT.READ_ONLY);
			solveMazeList.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
			solveMazeList.pack();
			
			solveWithAlgo=new Label(solveForm, SWT.NONE);
			solveWithAlgo.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			solveWithAlgo.setText("Choose Algorithm: ");
			solveWithAlgo.pack();
			
			algoList=new List(solveForm, SWT.DROP_DOWN|SWT.READ_ONLY);
			algoList.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
			algoList.pack();
		
			
			submitSolveMaze=new Button(solveForm, SWT.PUSH);
			submitSolveMaze.setLayoutData(new GridData(SWT.BOTTOM,SWT.BOTTOM,false,false,1,1));
			submitSolveMaze.setText("Submit Your Request");
			submitSolveMaze.pack();
		
		/*************************************************************/
		
			/*--------------------[Display Solution Button]--------------------*/
				displaySolutionBtn=new Button(shell, SWT.PUSH);
				displaySolutionBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
				displaySolutionBtn.setText("Display Solutions");
				
				displaySolutionForm=new Composite(dataDisplayer, SWT.BORDER);
				displaySolutionForm.setLayout(new GridLayout(2,false));
				displaySolutionForm.setBackgroundImage(backImage);
				displaySolutionFor=new Label(displaySolutionForm, SWT.NONE);
				displaySolutionFor.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
				displaySolutionFor.setText("Choose a maze to solve: ");
				displaySolutionFor.pack();
				
				solutionList=new List(displaySolutionForm, SWT.READ_ONLY|SWT.V_SCROLL);
				solutionList.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
				solutionList.pack();
				
				submitSolution=new Button(displaySolutionForm, SWT.PUSH);
				submitSolution.setLayoutData(new GridData(SWT.BOTTOM,SWT.BOTTOM,false,false,1,1));
				submitSolution.setText("Submit Your Solution Request");
				submitSolution.pack();
				
			
			/*************************************************************/


		/*--------------------[Save Maze Form]--------------------*/
		
		saveForm=new Composite(dataDisplayer, SWT.BORDER);
		saveForm.setLayout(new GridLayout(1,false));
		saveForm.setBackgroundImage(backImage);
		mazeList=new List(saveForm, SWT.SINGLE|SWT.BORDER|SWT.V_SCROLL);
		mazeList.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		mazeList.pack();
		
		saveSubmit=new Button(saveForm, SWT.PUSH);
		saveSubmit.setLayoutData(new GridData(SWT.BOTTOM,SWT.BOTTOM,false,false,1,1));
		saveSubmit.setText("Submit Your Required Maze");
		saveSubmit.pack();
		
		
		/*--------------------[Message Box]--------------------*/
		messageBox=new MessageBox(shell, SWT.ICON_INFORMATION|SWT.YES);
		
		/*notifications.put("LoadSolutionList","load solution map");
		setChanged();
		notifyObservers("LoadSolutionList");
		notifications.put("SolutionListRequest","display solution list");
		setChanged();
		notifyObservers("SolutionListRequest");*/
		/*********************Listeners handle*********************/

		
		openPropertiesItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					Shell propShell=new Shell(shell, SWT.APPLICATION_MODAL|SWT.DIALOG_TRIM);
					propShell.setLayout(new GridLayout(2,false));
					propShell.setBackgroundImage(new Image(null, "res/images/ezWall.png"));
					propShell.setText("My Properties");
					propShell.addDisposeListener(new DisposeListener() {
						
						@Override
						public void widgetDisposed(DisposeEvent arg0) {
							propShell.dispose();
						}
					});
					
					String[] data=PropertiesXmlHandler.getPropertiesInstance().getPropertiesAndValues();
					Label[] labels=new Label[data.length];
				
					HashMap<String,Object> stringMap=new HashMap<String,Object>();
					HashMap<String, String>typeMap=new HashMap<String,String>();
					for(int i=0;i<data.length;i++)
					{
						StringBuilder builder=new StringBuilder(data[i]);
						String[] value=builder.toString().split(" ");
						labels[i]=new Label(propShell,SWT.NONE);
						labels[i].setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
						labels[i].setText(value[0]);
						if(!value[2].equals("Boolean"))
						{
							Text text=new Text(propShell, SWT.BORDER);
							text.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
							text.setText(value[1].toString());
							stringMap.put(value[0],text);
						}
						else
						{
							Combo combo=new Combo(propShell, SWT.READ_ONLY);
							combo.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
							String[] items={"true","false"};
							combo.setItems(items);
							combo.setText(value[1]);
							stringMap.put(value[0], combo);
						}
						typeMap.put(value[0], value[2]);

					}
				
					Button submitProperties=new Button(propShell, SWT.PUSH);
					submitProperties.setLayoutData(new GridData(SWT.BOTTOM,SWT.BOTTOM,false,false,1,1));
					submitProperties.setText("Submit Your Changes");
					
					
					submitProperties.addSelectionListener(new SelectionListener() {
						
						@Override
						public void widgetSelected(SelectionEvent arg0) {
						
							
							Iterator<String> labelItr=stringMap.keySet().iterator();
							
							String labelName=null;
							String labelType=null;
							Object obj;

							String value=null;
							Method[] methods;
							try {
								Properties prop=PropertiesXmlHandler.getPropertiesInstance();
								methods = prop.getClass().getDeclaredMethods();
					
							while(labelItr.hasNext())
							{
								labelName=labelItr.next();
								
								labelType=typeMap.get(labelName);
								
								
								obj=stringMap.get(labelName);
								if(obj.getClass().getSimpleName().equals("Text"))
								{
									Text text=(Text)stringMap.get(labelName);
									value=text.getText();
								}
								if(obj.getClass().getSimpleName().equals("Combo"))
								{
									Combo combo=(Combo)stringMap.get(labelName);
									value=combo.getText();
								}
								
								
								StringBuilder builder=new StringBuilder();
								
								if(labelType.matches("[Bb]oolean"))
								{
									builder.append("set"+labelName);
									obj=Boolean.parseBoolean(value);
									
								}
								else if(labelType.matches("[Ss]tring"))
								{
									builder.append("set"+labelName);
									obj=(String)value;
								}
								else if(labelType.matches("[Ii]nteger")|| labelType.matches("[Ii]nt"))
								{
									builder.append("set"+labelName);
									obj=Integer.valueOf(value);
								}
								for(int i=0;i<methods.length;i++)
								{
									String[] splitedMethodName=methods[i].getName().split(" ");
									String shoretedName=splitedMethodName[splitedMethodName.length-1];
									if(shoretedName.matches(builder.toString())|| shoretedName.toLowerCase().matches(builder.toString().toLowerCase()))
									{
											try {
												//Object obj1=Properties.class.newInstance();
												methods[i].invoke(prop,obj);
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
							//Saving properties
								PropertiesXmlHandler.writeProperties(prop, "res/properties.xml");
							} catch (SecurityException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							showMessage("Please restart game for changes to take place");
							propShell.dispose();
						}
						
						@Override
						public void widgetDefaultSelected(SelectionEvent arg0) {
							// TODO Auto-generated method stub
							
						}
					});
					
					propShell.open();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		generateBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				StackLayout.topControl = mazeForm;
			        dataDisplayer.layout();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			
				
				
				
			}
		});
		submitMazeData.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(Integer.valueOf(yAxisText.getText())>3 &&Integer.valueOf(xAxisText.getText())>3 &&Integer.valueOf(zAxisText.getText())>3)
				{
					String mazeData="generate 3d maze "+mazeNameText.getText()+" "+yAxisText.getText()+" "+xAxisText.getText()+" "+zAxisText.getText();
					notifications.put("GenerateMaze", mazeData);
					setChanged();
					notifyObservers("GenerateMaze");
				}
				else
				{
					showMessage("Please enter correct sizes each size should be at least 10");
				}
				
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		displayBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			
				StackLayout.topControl = displayMazeForm;
			        dataDisplayer.layout();				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		submitMazeRequiered.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				String mazeToDsiplay=mazeName.getText();
				notifications.put("DisplayMaze","display "+mazeToDsiplay);
				setChanged();
				notifyObservers("DisplayMaze");
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		solveBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				notifications.put("MazeList", "get maze list");
				setChanged();
				notifyObservers("MazeList");
				StackLayout.topControl=solveForm;
				dataDisplayer.layout();	
				StringBuilder builder=new StringBuilder();
				for (String listElement : mazeList.getItems()) {
					if(!mazeSolMap.containsKey(listElement))
					{
						builder.append(listElement+" ");
					}
				}
				String[] list=builder.toString().split(" ");
				solveMazeList.setItems(list);
				String[] algorithms={"BFS","DFS"};
				algoList.setItems(algorithms);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		submitSolveMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(solveMazeList.getSelectionCount()>0 && algoList.getSelectionCount()>0)
				{
					String mazeName=solveMazeList.getSelection()[0].toString();
					String algoName=algoList.getSelection()[0].toString();

						
					notifications.put("Solve", "solve "+mazeName+" "+algoName);
					setChanged();
					notifyObservers("Solve");
					solveMazeList.remove(mazeName);
					dataDisplayer.redraw();
					
					
				}

			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		displaySolutionBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				notifications.put("SolutionListRequest","display solution list");
				setChanged();
				notifyObservers("SolutionListRequest");
				StackLayout.topControl=displaySolutionForm;
				dataDisplayer.layout();
/*				StringBuilder builder=new StringBuilder();
				for (String name : mazeSolMap.keySet()) {
					builder.append(name+" ");
				}
				String[] items=builder.toString().split(" ");
				solutionList.setItems(items);*/
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		submitSolution.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				String mazeName=solutionList.getSelection()[0];

					if(mazeSolMap.containsKey(mazeName))
					{
	
						notifications.put("AutoSolution",mazeName);
						setChanged();
						notifyObservers("AutoSolution");
						
				}

					
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//saveBtn.addSelectionListener(new SelectionListener() {
		saveFileItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				notifications.put("MazeList", "get maze list");
				setChanged();
				notifyObservers("MazeList");
				StackLayout.topControl=saveForm;
				
				dataDisplayer.layout();
				
			
				
			

			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		saveSubmit.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(mazeList.getSelection().length>0)
				{
					String item=mazeList.getSelection()[0];
					FileDialog fd=new FileDialog(shell,SWT.SAVE);
					  fd.setText("Save");
				        fd.setFilterPath("");
				        String[] filterExt = { "*.txt", "*.doc", ".rtf", "*.*" };
				        fd.setFilterExtensions(filterExt);
				        String selected = fd.open();
				        if(selected!=null)
				        {
				        	notifications.put("SaveMaze","save maze "+item+" "+selected);
				        	setChanged();
				        	notifyObservers("SaveMaze");
				        	
				        }
				}

				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//loadBtn.addSelectionListener(new SelectionListener() {
		loadFileItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog fd=new FileDialog(shell,SWT.OPEN);
				fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = { "*.txt", "*.java", ".xml", "*.*" };
				fd.setFilterExtensions(filterExt);
				String selected = fd.open();
				if(selected!=null)
				{
				String fullMazeName=fd.getFileName();
				int index=fullMazeName.indexOf(".");
				String mazeName=fullMazeName.substring(0, index);
			
					notifications.put("LoadMaze", "load maze "+selected+" "+mazeName);
					setChanged();
					notifyObservers("LoadMaze");
			
				}

			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		saveSolutionMap.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				notifications.put("SaveSolutionMap", "save solution map");
				setChanged();
				notifyObservers("SaveSolutionMap");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		loadSolutionMap.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				notifications.put("LoadSolutionMap","load solution map");
				setChanged();
				notifyObservers("LoadSolutionMap");

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
	public void showDirContent(String path) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * @param message
	 * This method displaying messages to the player
	 */
	@Override
	public void showMessage(String message) {
		if(message!=null)
		{
		this.messageBox.setText("New Message Received");
		messageBox.setMessage(message);
		messageBox.open();
		}
	}

	/**
	 * @param byte[] byteMaze
	 * This methods receives 3D maze converted to byte array, and it displays the maze
	 */
	@Override
	public void showGeneratedMaze(byte[] byteMaze) {
		
		try {
			mazeShell=new Shell(shell,SWT.SHELL_TRIM);
			mazeShell.setLayout(new GridLayout(1,false));
			 mazeShell.setBackgroundImage(new Image(null,"res/images/ezWall.png"));
			 maze=new Maze3d(byteMaze);
			 Button helpSolveBtn=new Button(mazeShell, SWT.PUSH);
			 helpSolveBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			 helpSolveBtn.setText("Press here to solve the maze");
			 
			 
			this.mazeDisplayer= new MyMazeDisplayWidgets(mazeShell,SWT.DOUBLE_BUFFERED,maze);
			this.mazeDisplayer.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,1));
			
			
			this.mazeDisplayer.addKeyListener(keyListener);
			this.mazeDisplayer.addMouseWheelListener(mouseWheelListener);
			String name=mazeName.getText();
			this.mazeShell.setText(name);
			
			
			this.mazeShell.open();
				mazeDisplayer.setFocus();
			helpSolveBtn.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					String name=mazeShell.getText();
					Position tempPosition=mazeDisplayer.getPlayer().getPlayerPosition();
					notifications.put("SolveRequest", name+" "+tempPosition.toString());
					setChanged();
					notifyObservers("SolveRequest");
					notifications.put("SolutionListRequest","display solution list");
					setChanged();
					notifyObservers("SolutionListRequest");
					showSolution(name, mazeSolMap.get(name));
					helpSolveBtn.setVisible(false);
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * This method initializing the listeners
	 * 
	 * Also added Zoom in/Zoom out
	 */
	public void initLisenerts()
	{
		keyListener=new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.keyCode) {
				case SWT.ARROW_UP:
				{
					mazeDisplayer.moveIn();
				}
					break;
				case SWT.ARROW_DOWN:
				{
					mazeDisplayer.moveOut();

				}
				break;
				case SWT.ARROW_RIGHT:
				{
					mazeDisplayer.moveRight();

					
				}
				break;
				case SWT.ARROW_LEFT:
				{
					mazeDisplayer.moveLeft();


				}
				break;
				case SWT.PAGE_UP:
				{
					mazeDisplayer.moveUp();

				}
				break;
				case SWT.PAGE_DOWN:
				{
					mazeDisplayer.moveDown();

				}
				break;
				default:
					break;
				}								
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				
			}
		};
		this.mouseWheelListener= new MouseWheelListener() {
			
			@Override
			public void mouseScrolled(MouseEvent e) {
				
				if((e.stateMask & SWT.CTRL)!=0)
				{
					mazeDisplayer.setSize(mazeDisplayer.getSize().x + e.count, mazeDisplayer.getSize().y + e.count);
				}
				
			}
		};
	}

	@Override
	public Object getDataFromView(String s) {
		
		return this.notifications.get(s);
		
	}

	@Override
	public void showGeneratedMazeName(String name) {
		
		this.showMessage(name+" is ready");
		
	}

	@Override
	public void showExit() {
		
	}

	@Override
	public void showCrossSection(byte[] byteMaze, String axies, int index, String name) {
		// TODO Auto-generated method stub
		
	}
/**
 * @param HashMap<String, Maze3d>mazeMap
 * @param HashMap<Maze3d, Solution<Position>> solutionMap
 * 	
 * 	This method creates HashMap<String, Solution<Position>>mazeSolMap by merging   mazeMap and solutionMap
 * 	by that the player is able to see which mazes can be solved
 * 
 * 	please notice that if the maze was solved before it won't appear again
 * 	 
 * 
 */
	@Override
	public void showSolutionList(HashMap<String, Maze3d>mazeMap,  HashMap<Maze3d, Solution<Position>> solutionMap) {
			
		Iterator<Maze3d>mazeItr=solutionMap.keySet().iterator();
		//Iterator<String>mazeMapStringItr=mazeMap.keySet().iterator();
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
		solutionList.removeAll();
		for (String mazeName : mazeSolMap.keySet()) {
			builder.append(mazeName + " ");
		}
		String[] items=builder.toString().split(" ");
		solutionList.setItems(items);

	}

	/**
	 * @param String name
	 * @param Solution<Position> solution
	 * 
	 * This method is activated when the player wants to display solution 
	 */
	@Override
	public void showSolution(String name,Solution<Position> solution) {
		timer=new Timer();
		ArrayList<Position> stateList=solution.getSolution();
		Iterator<Position>itr=stateList.iterator();
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				mazeDisplayer.setFocus();
				timer.scheduleAtFixedRate(new TimerTask() {
					
					@Override
					public void run() {
						if(itr.hasNext() && !mazeDisplayer.isDisposed())
						{
							display.syncExec(new Runnable() {
								
								@Override
								public void run() {
									Position pos=itr.next();
									mazeDisplayer.movePlayerTo(pos.getY(), pos.getX(), pos.getZ());

								}
							});
						}
						else
						{
							if(myTimerTask!=null)
							{
								myTimerTask.cancel();
							}
							if(timer!=null)
							{
								timer.cancel();
							}
						}
					}
				}, 0, 200);
			}
		});
	}

	
	/**
	 * @param Set<String> set
	 * 
	 */
	@Override
	public void showMazeList(Set<String> set) {
		
		Iterator<String>itr=set.iterator();
		StringBuilder builder=new StringBuilder();
		while(itr.hasNext())
		{
			builder.append(itr.next()+" ");
		}
		mazeList.removeAll();
		mazeList.setItems(builder.toString().split(" "));
	}

	
	/**
	 * @paramString mazeName
	 * @param Maze3d maze
	 * 
	 * This method allows the player to use the hint button in order to solve the maze
	 */
	@Override
	public void showAutoSolution(String mazeName, Maze3d maze) {
		mazeShell=new Shell(shell, SWT.SHELL_TRIM);
		mazeShell.setLayout(new GridLayout(1,false));
		mazeShell.setBackgroundImage(new Image(null, "res/images/ezWall.png"));
		//mazeShell.setSize(shell.getBounds().width,shell.getBounds().height);
		mazeShell.setText(mazeName);
		mazeDisplayer=new MyMazeDisplayWidgets(mazeShell, SWT.DOUBLE_BUFFERED, maze);
		mazeDisplayer.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,1));
		mazeShell.open();
		this.showSolution(mazeName, mazeSolMap.get(mazeName));

		
	}

}
