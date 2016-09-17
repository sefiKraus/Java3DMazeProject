package View;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import widgets.MazeFormWidget;

public class MyGuiView extends CommonGuiView implements View{

	HashMap<String, Object>notifications;
	static int pageNum= -1;
	public MyGuiView(String windowTitle, int width, int height) {
		super(windowTitle, width, height);
	}

	@Override
	void initWidgets() {
		
		shell.setLayout(new GridLayout(2,false));
		/*--------------------[This is Generate Button]--------------------*/
		Button generateBtn=new Button(shell, SWT.PUSH);
		
		
		generateBtn.setLayoutData(new GridData(SWT.None,SWT.NONE,false,false,1,1));
		generateBtn.setText("Generate 3D Maze");
		
		final StackLayout generateStackLayout=new StackLayout();
		/*--------------------[This is another composite in main window]--------------------*/
		Composite dataDisplayer=new Composite(shell, SWT.BORDER);
		dataDisplayer.setLayout(generateStackLayout);
		dataDisplayer.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,6));
		
		
		/*--------------------[Maze Properties form after pressing Generate 3D maze btn]--------------------*/
			final Composite mazeForm=new Composite(dataDisplayer,SWT.BORDER);
			mazeForm.setLayout(new GridLayout(2,false));
			
			
			Label mazeLabel=new Label(mazeForm, SWT.NONE); //Maze Name
			mazeLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			mazeLabel.setText("Enter Maze Name Here:");
			mazeLabel.pack();
			
			
			Text mazeNameText=new Text(mazeForm, SWT.BORDER|SWT.SINGLE);
			mazeNameText.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
			mazeNameText.pack();
			
			Label yAxisLabel=new Label(mazeForm, SWT.NONE);  //Y properties
			yAxisLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			yAxisLabel.setText("Enter Y (Levels):");
			yAxisLabel.pack();
			
			Text yAxisText=new Text(mazeForm, SWT.BORDER|SWT.SINGLE);
			yAxisText.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
			yAxisText.pack();
			
			Label xAxisLabel=new Label(mazeForm, SWT.NONE); //X properties
			xAxisLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			xAxisLabel.setText("Enter X (Rows):");
			xAxisLabel.pack();
			
			Text xAxisText=new Text(mazeForm, SWT.BORDER|SWT.SINGLE);
			xAxisText.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
			xAxisText.pack();
			
			Label zAxisLabel=new Label(mazeForm, SWT.NONE);//Z properties
			zAxisLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
			zAxisLabel.setText("Enter Z (Columns):");
			zAxisLabel.pack();
			
			Text zAxisText=new Text(mazeForm, SWT.BORDER|SWT.SINGLE);
			zAxisText.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
			zAxisText.pack();
			
			
			final Button submitMazeData=new Button(mazeForm, SWT.PUSH);// submit button
			submitMazeData.setLayoutData(new GridData(SWT.BOTTOM,SWT.BOTTOM,false,false,1,1));
			submitMazeData.setText("Submit Your Maze Properties");
			submitMazeData.pack();
			
			
		
		/*--------------------[This is another composite in main window]--------------------*/

		
		final Text test=new Text(dataDisplayer, SWT.MULTI);
		test.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,6));
		test.pack();
		
	/*****************************************************************************************/
		
		/*--------------------[Display Maze Button]--------------------*/

		Button displayBtn=new Button(shell, SWT.PUSH);
		displayBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		displayBtn.setText("Display Maze");
		
		final Composite displayMazeForm=new Composite(dataDisplayer, SWT.BORDER);
		displayMazeForm.setLayout(new GridLayout(2,false));
		
		Label mazeNameLabel=new Label(displayMazeForm, SWT.NONE);
		mazeNameLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		mazeNameLabel.setText("Enter Requiered Maze Name:");
		mazeNameLabel.pack();
		
		Text mazeName=new Text(displayMazeForm, SWT.SINGLE|SWT.BORDER);
		mazeName.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
		mazeName.pack();
		
		final Button submitMazeRequiered=new Button(displayMazeForm, SWT.PUSH);// submit button
		submitMazeRequiered.setLayoutData(new GridData(SWT.BOTTOM,SWT.BOTTOM,false,false,1,1));
		submitMazeRequiered.setText("Submit Your Maze Properties");
		submitMazeRequiered.pack();
		
		
		
		/*--------------------[Solve Maze Button]--------------------*/
		
		
		
		
		
		/*************************************************************/
		
		
		/*--------------------[Save Maze Button]--------------------*/
		
		
		Button saveBtn=new Button(shell, SWT.PUSH);
		saveBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		saveBtn.setText("Save Maze");
		
		/*--------------------[Load Maze Button]--------------------*/
		Button loadBtn=new Button(shell, SWT.PUSH);
		loadBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		loadBtn.setText("Load Maze");
		
		
		
		/*--------------------[Directory Content Button]--------------------*/

		Button dirBtn=new Button(shell, SWT.PUSH);
		dirBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		dirBtn.setText("Display Dir Files");
		
		final Composite dirForm=new Composite(dataDisplayer, SWT.BORDER);
		dirForm.setLayout(new GridLayout(2,false));
		
		Label dirPathLabel=new Label(dirForm, SWT.NONE);
		dirPathLabel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		dirPathLabel.setText("Enter Requiered Path:");
		dirPathLabel.pack();
		
		Text dirPathText=new Text(dirForm, SWT.BORDER|SWT.SINGLE);
		dirPathText.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,1,1));
		dirPathText.pack();
		
		final Button submitDirRequiered=new Button(dirForm, SWT.PUSH);// submit button
		submitDirRequiered.setLayoutData(new GridData(SWT.BOTTOM,SWT.BOTTOM,false,false,1,1));
		submitDirRequiered.setText("Submit Your Dir Path");
		submitDirRequiered.pack();
		
		
		
		
		
		
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
		
		saveBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				FileDialog fd=new FileDialog(shell,SWT.SAVE);
				  fd.setText("Save");
			        fd.setFilterPath("");
			        String[] filterExt = { "*.txt", "*.doc", ".rtf", "*.*" };
			        fd.setFilterExtensions(filterExt);
			        String selected = fd.open();
			        System.out.println(selected);

			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		loadBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				FileDialog fd=new FileDialog(shell,SWT.OPEN);
				fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = { "*.txt", "*.java", ".xml", "*.*" };
				fd.setFilterExtensions(filterExt);
				String selected = fd.open();

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
		
		
		
	}

	@Override
	public void showGeneratedMaze(byte[] byteMaze) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getDataFromView(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showGeneratedMazeName(String name) {
		// TODO Auto-generated method stub
		
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

}
