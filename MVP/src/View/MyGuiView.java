package View;


import java.awt.SecondaryLoop;
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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.junit.internal.builders.SuiteMethodBuilder;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import widgets.MazeDisplay;
import widgets.MyMazeDisplayWidgets;

public class MyGuiView extends CommonGuiView{

	HashMap<String, Object>notifications;
	Button generateBtn,submitMazeData,displayBtn,
			submitMazeRequiered,saveBtn,loadBtn,dirBtn,submitDirRequiered,exitBtn,saveSubmit
			,solveBtn,submitSolveMaze,displaySolutionBtn,submitSolution;
	
	Text  mazeNameText,yAxisText,xAxisText,zAxisText,mazeName,dirPathText, messageText;
	
	Label mazeLabel,yAxisLabel,xAxisLabel, zAxisLabel,mazeNameLabel
			,dirPathLabel,solveMazeLabel,solveWithAlgo,displaySolutionFor;
	
	Composite dataDisplayer,mazeForm,displayMazeForm,dirForm,saveForm,solveForm,displaySolutionForm;
	
	MazeDisplay mazeDisplayer;
	
	HashMap<String, Solution<Position>>mazeSolMap;
	Maze3d maze;
	
	KeyListener keyListener;
	
	List mazeList,solutionList,solveMazeList,algoList;
	
	Shell mazeShell;
	
	Timer timer;
	TimerTask myTimerTask;
	public MyGuiView(String windowTitle, int width, int height) {
		super(windowTitle, width, height);
		this.notifications=new HashMap<String,Object>();
		this.mazeSolMap=new HashMap<String, Solution<Position>>();
		
	}

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
			}
		});
		/*--------------------[This is Generate Button]--------------------*/
		 generateBtn=new Button(shell, SWT.PUSH);
		
		
		generateBtn.setLayoutData(new GridData(SWT.None,SWT.NONE,false,false,1,1));
		generateBtn.setText("Generate 3D Maze");
		
		final StackLayout generateStackLayout=new StackLayout();
		/*--------------------[This is another composite in main window]--------------------*/
		 dataDisplayer=new Composite(shell, SWT.BORDER);
		dataDisplayer.setLayout(generateStackLayout);
		dataDisplayer.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,9));
		
		
		/*--------------------[Maze Properties form after pressing Generate 3D maze btn]--------------------*/
			 mazeForm=new Composite(dataDisplayer,SWT.BORDER);
			mazeForm.setLayout(new GridLayout(2,false));
			
			
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
			
			solveMazeLabel=new Label(solveForm, SWT.NONE);
			solveMazeLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			solveMazeLabel.setText("Choose Maze To Solve: ");
			solveMazeLabel.pack();
			
			solveMazeList=new List(solveForm, SWT.DROP_DOWN|SWT.READ_ONLY);
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
				
				displaySolutionFor=new Label(displaySolutionForm, SWT.NONE);
				displaySolutionFor.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
				displaySolutionFor.setText("Choose a maze to solve: ");
				displaySolutionFor.pack();
				
				solutionList=new List(displaySolutionForm, SWT.READ_ONLY|SWT.DROP_DOWN);
				solutionList.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
				solutionList.pack();
				
				submitSolution=new Button(displaySolutionForm, SWT.PUSH);
				submitSolution.setLayoutData(new GridData(SWT.BOTTOM,SWT.BOTTOM,false,false,1,1));
				submitSolution.setText("Submit Your Solution Request");
				submitSolution.pack();
				
			
			/*************************************************************/


		/*--------------------[Save Maze Button]--------------------*/
		
		
		 saveBtn=new Button(shell, SWT.PUSH);
		saveBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		saveBtn.setText("Save Maze");
		
		saveForm=new Composite(dataDisplayer, SWT.BORDER);
		saveForm.setLayout(new GridLayout(1,false));
		
		mazeList=new List(saveForm, SWT.SINGLE|SWT.BORDER);
		mazeList.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		mazeList.pack();
		
		saveSubmit=new Button(saveForm, SWT.PUSH);
		saveSubmit.setLayoutData(new GridData(SWT.BOTTOM,SWT.BOTTOM,false,false,1,1));
		saveSubmit.setText("Submit Your Required Maze");
		saveSubmit.pack();
		
		/*--------------------[Load Maze Button]--------------------*/
		 loadBtn=new Button(shell, SWT.PUSH);
		loadBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		loadBtn.setText("Load Maze");
		
		
		
		/*--------------------[Directory Content Button]--------------------*/

		 dirBtn=new Button(shell, SWT.PUSH);
		dirBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		dirBtn.setText("Display Dir Files");
		
		 dirForm=new Composite(dataDisplayer, SWT.BORDER);
		dirForm.setLayout(new GridLayout(2,false));
		
		dirPathLabel=new Label(dirForm, SWT.NONE);
		dirPathLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		dirPathLabel.setText("Enter Requiered Path:");
		dirPathLabel.pack();
		
		dirPathText=new Text(dirForm, SWT.BORDER|SWT.SINGLE);
		dirPathText.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
		dirPathText.pack();
		
		submitDirRequiered=new Button(dirForm, SWT.PUSH);// submit button
		submitDirRequiered.setLayoutData(new GridData(SWT.BOTTOM,SWT.BOTTOM,false,false,1,1));
		submitDirRequiered.setText("Submit Your Dir Path");
		submitDirRequiered.pack();
		
		
		/*--------------------[Exit Button]--------------------*/

		exitBtn=new Button(shell, SWT.PUSH);
		exitBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		exitBtn.setText("Exit");
		
		
		/*--------------------[Message Box]--------------------*/
		messageText=new Text(shell, SWT.BORDER|SWT.MULTI|SWT.READ_ONLY);
		messageText.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,1));
		
	
		
		
		
		
		
		/*********************Listeners handle*********************/
		
		
		generateBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				  generateStackLayout.topControl = mazeForm;
			        dataDisplayer.layout();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			
				
				
				
			}
		});
		submitMazeData.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				String mazeData="generate 3d maze "+mazeNameText.getText()+" "+yAxisText.getText()+" "+xAxisText.getText()+" "+zAxisText.getText();
				System.out.println(mazeData);
				notifications.put("GenerateMaze", mazeData);
				setChanged();
				notifyObservers("GenerateMaze");
				
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		displayBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			
				  generateStackLayout.topControl = displayMazeForm;
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
				generateStackLayout.topControl=solveForm;
				dataDisplayer.layout();
				solveMazeList.setItems(mazeList.getItems());
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
				if(solveMazeList.getSelection()[0].length()>0 && algoList.getSelection()[0].length()>0)
				{
					String mazeName=solveMazeList.getSelection()[0];
					String algoName=algoList.getSelection()[0];
					notifications.put("Solve", "solve "+mazeName+" "+algoName);
					setChanged();
					notifyObservers("Solve");
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
				generateStackLayout.topControl=displaySolutionForm;
				dataDisplayer.layout();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		submitSolution.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String mazeName=solveMazeList.getSelection()[0];
				showSolution(mazeName,mazeSolMap.get(mazeName));
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		saveBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				notifications.put("MazeList", "get maze list");
				setChanged();
				notifyObservers("MazeList");
				generateStackLayout.topControl=saveForm;
				
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
		
		
		loadBtn.addSelectionListener(new SelectionListener() {
			
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
		
		dirBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				generateStackLayout.topControl=dirForm;
				dataDisplayer.layout();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		exitBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				notifications.put("Exit", " ");
				setChanged();
				notifyObservers("Exit");
				shell.dispose();
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

	@Override
	public void showMessage(String message) {
		this.messageText.setText(message);
	}

	@Override
	public void showGeneratedMaze(byte[] byteMaze) {
		
		try {
			mazeShell=new Shell(shell,SWT.SHELL_TRIM);
			mazeShell.setLayout(new GridLayout(2,false));
			mazeShell.setSize(shell.getBounds().width,shell.getBounds().height);
			 maze=new Maze3d(byteMaze);
			 maze.printMaze();
			 Button helpSolveBtn=new Button(mazeShell, SWT.PUSH);
			 helpSolveBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			 helpSolveBtn.setText("Press here to solve the maze");
		
			 
			this.mazeDisplayer= new MyMazeDisplayWidgets(mazeShell,SWT.NONE,maze);
			this.mazeDisplayer.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,2));
			this.mazeDisplayer.addKeyListener(keyListener);
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
	
	public void initLisenerts()
	{
		keyListener=new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.keyCode) {
				case SWT.ARROW_UP:
				{
					mazeDisplayer.moveOut();
				}
					break;
				case SWT.ARROW_DOWN:
				{
					mazeDisplayer.moveIn();

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

	@Override
	public void showSolutionList(HashMap<String, Maze3d>mazeMap,  HashMap<Maze3d, Solution<Position>> solutionMap) {
			
		Iterator<Maze3d> itr=solutionMap.keySet().iterator();
		
		while(itr.hasNext())
		{
			Maze3d maze=itr.next();
			for (String mazeName : mazeMap.keySet()) {
				if(maze.equals(mazeMap.get(mazeName)))
				{
					solutionList.add(mazeName);
					this.mazeSolMap.put(mazeName, solutionMap.get(maze));
				}
			}
		}
		
	}

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
				}, 0, 150);
			}
		});
	}

	@Override
	public void showMazeList(Set<String> set) {
		
		Iterator<String>itr=set.iterator();
		StringBuilder builder=new StringBuilder();
		while(itr.hasNext())
		{
			builder.append(itr.next()+" ");
		}
		mazeList.setItems(builder.toString().split(" "));
	}

}
