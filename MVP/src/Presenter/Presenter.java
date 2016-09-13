package Presenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import Model.Model;
import View.View;
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
		
		commandMap.put("generate 3d maze +[^\n\r]+ [0-50]+ [0-50]+ [0-50]+",new Command() {

			@Override
			public void doCommand(String[] args) {
				m.handleGenerate(args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]),Integer.parseInt(args[6]));
			}
		});
		commandMap.put("display (?!cross section by)(?!solution)(?!solution list)[^\n\r]+", new Command() {

			@Override
			public void doCommand(String[] args) {
				try {
					ui.showGeneratedMaze(((m.getMazeMap().get(args[1]))).toByteArray());
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
			ui.showSolution(m.getSolutionMap().get(m.getMazeMap().get(args[2])));
			}
		});
		commandMap.put("display solution list", new Command() {

			@Override
			public void doCommand(String[] args) {
				ui.showSolutionList(m.getSolutionMap());
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
				ui.showMessage("Type exit to close the game");
				ui.showMessage("display solution list");
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
				ui.showSolution((m.getSolutionMap().get(m.getMazeMap().get(m.getDataFromModel(data)))));
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
				for (String s : this.commandMap.keySet()) {
					if(command.matches(s))
					{
						String[] arguments=command.split(" ");
						this.commandMap.get(s).doCommand(arguments);
					}

						
				}

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


}
