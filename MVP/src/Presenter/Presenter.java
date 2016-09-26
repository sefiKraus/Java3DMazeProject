package Presenter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.widgets.Display;

import Model.Model;
import Model.PropertiesXmlHandler;
import View.CommonGuiView;
import View.MyGuiView;
import View.MyView;
import View.View;
import algorithms.mazeGenerators.Position;
/**
 * @author Krausz Sefi 305371320
 * @since 02/09/2016
 */
public class Presenter implements Observer{

	private View ui;
	private Model m;
	HashMap<String, Command> commandMap;
	
	
	public Presenter() {
		super();
		this.commandMap=new HashMap<String,Command>();
		this.fillCommandMap();
	}
	
	public Presenter(View ui, Model m) {
		super();
		this.ui = ui;
		this.m = m;
		this.commandMap = new HashMap<String,Command>();
		this.fillCommandMap();
	}

	/**
	 * Initializing the Command Map with Protocol commands
	 */
	private void fillCommandMap() {
		
		commandMap.put("dir [^\n\r]+",new Command() {

			@Override
			public void doCommand(String[] args) {
				ui.showDirContent(args[1]);
				
			}
		});
		
		commandMap.put("generate 3d maze +[^\n\r]+ [0-100]+ [0-100]+ [0-100]+",new Command() {

			@Override
			public void doCommand(String[] args) {
				m.handleGenerate(args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]),Integer.parseInt(args[6]));
			}
		});
		commandMap.put("display (?!cross section by)(?!solution)(?!solution list)[^\n\r]+", new Command() {

			@Override
			public void doCommand(String[] args) {
				try {
					if(m.getMazeMap().containsKey(args[1]))
					{
						ui.showGeneratedMaze(((m.getMazeMap().get(args[1]))).toByteArray());

					}
					else
					{
						ui.showMessage("Error displaying requiered maze please check for correct name");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		commandMap.put("display cross section by [XYZxyz] [0-9]+ for [^\n\r]+",new Command() {

			@Override
			public void doCommand(String[] args) {
				try {
					ui.showCrossSection(m.getMazeMap().get(args[7]).toByteArray(), args[4], Integer.parseInt(args[5]), args[7]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
			}
		});
		commandMap.put("save maze [^\n\r]+ [^\n\r]+",new Command() {

			@Override
			public void doCommand(String[] args) {
				try {
					m.handleSaveMaze(m.getMazeMap().get(args[2]).toByteArray(),args[3]);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		commandMap.put("load maze [^\n\r]+ [^\n\r]+", new Command() {

			@Override
			public void doCommand(String[] args) {
				m.handleLoadMaze(args[2], args[3]);
				
			}
		});
		commandMap.put("solve [^\n\r]+ [^\n\r]+", new Command() {

			@Override
			public void doCommand(String[] args) {
				m.handleSolveMaze(args[1],args[2]);
			}
		});
		commandMap.put("display solution (?!list)[^\n\r]+",new Command() {

			@Override
			public void doCommand(String[] args) {
			ui.showSolution(args[2],m.getSolutionMap().get(m.getMazeMap().get(args[2])));
			}
		});
		commandMap.put("display solution list", new Command() {

			@Override
			public void doCommand(String[] args) {
				ui.showSolutionList(m.getMazeMap(),m.getSolutionMap());
			}
		});
		commandMap.put("exit", new Command() {

			@Override
			public void doCommand(String[] args) {
				ui.showExit();
				m.handleExit();
			}
		});
		commandMap.put("save solution map", new Command() {

			@Override
			public void doCommand(String[] args) {
				m.saveSolutionHashMapToZip();
			}
		});
		commandMap.put("load solution map", new Command() {

			@Override
			public void doCommand(String[] args) {
				m.loadSolutionHashMapFromZip();
			}
		});
		commandMap.put("open properties", new Command() {

			@Override
			public void doCommand(String[] args) {
				
			}
		});
		commandMap.put("get maze list", new Command() {

			@Override
			public void doCommand(String[] args) {
				ui.showMazeList(m.getMazeMap().keySet());
			}
		});
		commandMap.put("help",new Command() {

			@Override
			public void doCommand(String[] args) {
				ui.showMessage("----------------------------------------------");
				ui.showMessage("dir <path>");
				ui.showMessage("generate 3d maze <maze's name> <y size(Levels)> <x size(Rows)> <z size(Columns)>");
				ui.showMessage("display <maze's name>");
				ui.showMessage("display cross section by <X|Y|Z> <index> for <maze's name>");
				ui.showMessage("save maze <maze's name> <file name | file path>");
				ui.showMessage("load maze <file name| file path> <maze's name>");
				ui.showMessage("solve <maze's name> <Bfs | Dfs>");
				ui.showMessage("display solution <maze's name>");
				ui.showMessage("Type save solution map to save the solutions");
				ui.showMessage("Type load solution map to load the solutions");
				ui.showMessage("display solution list");
				ui.showMessage("In order to change to GUI type gui");
				ui.showMessage("Type exit to close the game");
				ui.showMessage("----------------------------------------------");

			}
		});
	
	}

	@Override
	public void update(Observable o, Object obj) {

		String data=(String)obj;
		if(o==m)
		{
			switch (data) {
				
			case ("Ready"):
			{
				ui.showGeneratedMazeName((String) m.getDataFromModel(data));
			}
				break;
			case ("Error"):
			{
				ui.showMessage((String)m.getDataFromModel(data));
			}
			break;
			case("Message"):
			{
				ui.showMessage((String)m.getDataFromModel(data));
			}
			break;
			case("Save"):
			{
				ui.showMessage((String)m.getDataFromModel(data));
			}
			break;
			case("Loaded"):
			{
				ui.showMessage((String)m.getDataFromModel(data));
				
			}
			break;
			case("Solve"):
			{
				ui.showMessage((String)m.getDataFromModel(data));
				
			}
			break;
			case("Solved already"):
			{
				ui.showSolution((String)m.getDataFromModel(data),(m.getSolutionMap().get(m.getMazeMap().get(m.getDataFromModel(data)))));
			}
			break;
			case ("SaveSolutionList"):
			{
				ui.showMessage((String)m.getDataFromModel(data));
			}
			break;
			default:
				break;
			}
		}
		else if(o==ui)
		{
			switch (data) {
			case ("CLI"):
			{
				String command=(String)ui.getDataFromView("");
				if(command.equals("gui"))
				{
					try {
						Properties tempProperties=PropertiesXmlHandler.getPropertiesInstance();
						tempProperties.setGUI(true);
						PropertiesXmlHandler.writeProperties(tempProperties, "res/properties.xml");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ui.showMessage("Please restart game");
				}
				else
				{

				for (String s : this.commandMap.keySet()) {
					if(command.matches(s))
					{
						String[] arguments=command.split(" ");
						this.commandMap.get(s).doCommand(arguments);
					}

				}		
				}

			}
				break;
			case("GenerateMaze"):
			{
				this.activateCommand((String)ui.getDataFromView("GenerateMaze"));
				
				
			}
			break;
			case ("LoadSolutionList"):
			{
				this.activateCommand((String)ui.getDataFromView("LoadSolutionList"));

			}
			break;
			case("DisplayMaze"):
			{
				this.activateCommand((String)ui.getDataFromView("DisplayMaze"));
			}
			break;
			case("MazeList"):
			{
				this.activateCommand((String)ui.getDataFromView("MazeList"));
			}
			break;
			case("SaveMaze"):
			{
				this.activateCommand((String)ui.getDataFromView("SaveMaze"));
			}
			break;
			case("LoadMaze"):
			{
				this.activateCommand((String)ui.getDataFromView("LoadMaze"));
			}
			break;
			case("Solve"):
			{
				this.activateCommand((String)ui.getDataFromView("Solve"));
			}
			break;
			case("SolutionListRequest"):
			{
				this.activateCommand((String)ui.getDataFromView("SolutionListRequest"));
				
			}
			break;
			case("SolveRequest"):
			{
				String string=(String)ui.getDataFromView("SolveRequest");
				String[] args=string.split(" ");

				m.handleChangeMazeStartPosition(args[0],new Position(args[1]));
				this.activateCommand("solve "+args[0]+" bfs");
			}
			break;
			case("Exit"):
			{
			//	this.activateCommand("save solution map");
				m.handleExit();
			}
			break;
			case("AutoSolution"):
			{
				ui.showAutoSolution((String)ui.getDataFromView("AutoSolution"),m.getMazeMap().get((String)ui.getDataFromView("AutoSolution")));
			}
			break;
			case("saveSettings"):
			{
				
				//m.handleSaveProperties(prop, xmlPath);
			}
			break;
			case ("SaveSolutionMap"):
			{
				this.activateCommand((String)ui.getDataFromView("SaveSolutionMap"));
			}
			break;
			case ("LoadSolutionMap"):
			{
				this.activateCommand((String)ui.getDataFromView("LoadSolutionMap"));
			}
			break;
			default:
				break;
			}
		}
	}

	public View getUi() {
		return ui;
	}

	public void setUi(View ui) {
		this.ui = ui;
	}

	public Model getM() {
		return m;
	}

	public void setM(Model m) {
		this.m = m;
	}

	public HashMap<String, Command> getCommandMap() {
		return commandMap;
	}

	public void setCommandMap(HashMap<String, Command> commandMap) {
		this.commandMap = commandMap;
	}

	public void activateCommand(String command)
	{
		for (String s : this.commandMap.keySet()) {
			
			if(command.matches(s))
			{
				String[] arguments=command.split(" ");
				this.commandMap.get(s).doCommand(arguments);
			}
		}
		
	}
	
	public void initViewFromPresenter(String xmlPath)
	{
		m.handleLoadProperties(xmlPath);

		
		if(this.ui!=null)
		{
			
			try {
				if(!PropertiesXmlHandler.getPropertiesInstance().isGUI() && 
						this.ui.getClass().getName().matches("[Mm][Yy][Gg][Uu][Ii][Vv][Ii][Ee][Ww]")){
					
					((CommonGuiView)this.ui).getDisplay().dispose();
					this.switchToCLI();
					
				}
				else if(PropertiesXmlHandler.getPropertiesInstance().isGUI() && 
						this.ui.getClass().getName().matches("[Mm][Yy][Vv][Ii][Ee][Ww]"))
				{
					this.switchToGui();
				}
					
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				if(PropertiesXmlHandler.getPropertiesInstance().isGUI())
				{
					switchToGui();
				}
				else
				{
					switchToCLI();
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void switchToCLI()
	{
		this.setUi(new MyView());
		((Observable)this.ui).addObserver(this);
		((MyView)this.ui).getCli().addObserver((Observer)this.ui);
		this.getUi().start();

	}
	
	public void switchToGui()
	{
		this.setUi(new MyGuiView("Welcome Player",1500,900));
		((Observable)this.ui).addObserver(this);
		this.getUi().start();

	}
}
