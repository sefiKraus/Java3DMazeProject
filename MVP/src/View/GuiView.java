package View;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;

import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class GuiView extends CommonGuiView{

	public GuiView(String windowTitle, int width, int height) {
		super(windowTitle, width, height);
	}

	
	@Override
	public void start() {
		this.run();
	}
	@Override
	public void initWidgets()
	{
		shell.setLayout(new GridLayout(2,false));

		Button generateBtn=new Button(this.shell, SWT.PUSH);
		generateBtn.setText("Generate 3d Maze");
		generateBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		
		Text dataDisplayer=new Text(this.shell,SWT.BORDER);
		dataDisplayer.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,6));

		Button displayMazeBtn=new Button(this.shell, SWT.PUSH);
		displayMazeBtn.setText("Display Maze");
		displayMazeBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		
		Button solveMazeBtn=new Button(this.shell, SWT.PUSH);
		solveMazeBtn.setText("Solve Maze");
		solveMazeBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));

		Button showDirDataBtn=new Button(this.shell, SWT.PUSH);
		showDirDataBtn.setText("Show directory files");
		showDirDataBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));

		Button saveMazeBtn=new Button(this.shell, SWT.PUSH);
		saveMazeBtn.setText("Save Maze");
		saveMazeBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));

		Button loadMazeBtn=new Button(this.shell,SWT.PUSH);
		loadMazeBtn.setText("Load Maze");
		loadMazeBtn.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		
	
	}
	@Override
	public void showDirContent(String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showMessage(String message) {
		// TODO Auto-generated method stub
		
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
	public void showSolution(Solution<Position> solution) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void showSolutionList(HashMap<Maze3d, Solution<Position>> map) {
		// TODO Auto-generated method stub
		
	}

}
