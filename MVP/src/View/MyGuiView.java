package View;

import java.awt.Scrollbar;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import widgets.MazeDisplay;
import widgets.MyMazeDisplayWidgets;

public class MyGuiView extends CommonGuiView{

	HashMap<String, Object>notifications;
	
	Button generateBtn,submitMazeData,displayBtn,
			submitMazeRequiered,saveBtn,loadBtn,dirBtn,submitDirRequiered,exitBtn,saveSubmit
			,solveBtn,submitSolveMaze;
	
	Text  mazeNameText,yAxisText,xAxisText,zAxisText,mazeName,dirPathText, messageText;
	
	Label mazeLabel,yAxisLabel,xAxisLabel, zAxisLabel,mazeNameLabel,dirPathLabel;
	
	Composite dataDisplayer,mazeForm,displayMazeForm,dirForm,saveForm,solveForm;
	
	MazeDisplay mazeDisplayer;
	
	Combo solveCombo;
	Maze3d maze;
	
	KeyListener keyListener;
	
	List mazeList,solutionList;
	
	Shell mazeShell;
	public MyGuiView(String windowTitle, int width, int height) {
		super(windowTitle, width, height);
		this.notifications=new HashMap<String,Object>();
		
	}

	@Override
	void initWidgets() {
		
		this.initLisenerts();
		shell.setLayout(new GridLayout(2,false));
		/*--------------------[This is Generate Button]--------------------*/
		 generateBtn=new Button(shell, SWT.PUSH);
		
		
		generateBtn.setLayoutData(new GridData(SWT.None,SWT.NONE,false,false,1,1));
		generateBtn.setText("Generate 3D Maze");
		
		final StackLayout generateStackLayout=new StackLayout();
		/*--------------------[This is another composite in main window]--------------------*/
		 dataDisplayer=new Composite(shell, SWT.BORDER);
		dataDisplayer.setLayout(generateStackLayout);
		dataDisplayer.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,8));
		
		
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
			solveForm.setLayout(new GridLayout(1,false));
			
			solveCombo=new Combo(solveForm, SWT.DROP_DOWN);
			solveCombo.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
			solveCombo.pack();
		
			
			submitSolveMaze=new Button(solveForm, SWT.PUSH);
			submitSolveMaze.setLayoutData(new GridData(SWT.BOTTOM,SWT.BOTTOM,false,false,1,1));
			submitSolveMaze.setText("Submit Your Request");
			submitSolveMaze.pack();
		
		/*************************************************************/
		
		
		/*--------------------[Save Maze Button]--------------------*/
		
		
		 saveBtn=new Button(shell, SWT.PUSH);
		saveBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		saveBtn.setText("Save Maze");
		
		saveForm=new Composite(dataDisplayer, SWT.BORDER);
		saveForm.setLayout(new GridLayout(1,false));
		
		mazeList=new List(saveForm, SWT.SINGLE|SWT.BORDER);
		mazeList.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
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
		
		exitBtn=new Button(shell, SWT.PUSH);
		exitBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		exitBtn.setText("Exit");
		
		//
		messageText=new Text(shell, SWT.BORDER|SWT.MULTI);
		messageText.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		
	
		
		
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
				generateStackLayout.topControl=solveForm;
				dataDisplayer.layout();
				
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
				//fd.setFilterExtensions(filterExt);
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
			mazeShell.setLayout(new GridLayout(1,false));
			//mazeShell.setSize(400,400);
			 maze=new Maze3d(byteMaze);
			 maze.printMaze();
			this.mazeDisplayer= new MyMazeDisplayWidgets(mazeShell,SWT.NONE,maze);
			//this.mazeDisplayer=new MazeDisplay(mazeShell, SWT.NONE,new Maze3d(byteMaze));
			this.mazeDisplayer.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,1));
			this.mazeDisplayer.addKeyListener(keyListener);
			this.mazeShell.setText("Maze Game");
			this.mazeShell.open();
			
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
					mazeDisplayer.moveLeft();
					System.out.println("arrow up");
				}
					break;
				case SWT.ARROW_DOWN:
				{
					mazeDisplayer.moveRight();
					System.out.println("arrow down");

				}
				break;
				case SWT.ARROW_RIGHT:
				{
					mazeDisplayer.moveIn();
					System.out.println("arrow right");

					
				}
				break;
				case SWT.ARROW_LEFT:
				{
					mazeDisplayer.moveOut();
					System.out.println("arrow left");


				}
				break;
				case SWT.PAGE_UP:
				{
					mazeDisplayer.moveUp();
					System.out.println("arrow pageup");

				}
				break;
				case SWT.PAGE_DOWN:
				{
					mazeDisplayer.moveDown();
					System.out.println("arrow pagedown");

				}
				break;
				default:
					break;
				}								
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showCrossSection(byte[] byteMaze, String axies, int index, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showSolutionList(HashMap<Maze3d, Solution<Position>> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showSolution(Solution<Position> solution) {
		// TODO Auto-generated method stub
		
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
