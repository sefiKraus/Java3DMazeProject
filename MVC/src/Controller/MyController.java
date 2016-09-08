package Controller;

import java.io.IOException;
import java.util.HashMap;

import Model.Model;
import View.View;
/**
 * 
 * @author Krausz sefi
 * @since 07/09/2016
 */
public class MyController implements Controller {

	private Model model; 
	private View view;
	HashMap<String, Command>commandMap;
	
	public MyController(Model model,View view) {
		super();
		this.model=model;
		this.view=view;
		this.commandMap=new HashMap<String,Command>();
		this.fillcommandMap(this.commandMap);
	}
	public MyController() {
		super();
		this.commandMap=new HashMap<String,Command>();
		this.fillcommandMap(this.commandMap);
	}
	
	/**
	 * This method fills the command map using Regex
	 * @param commandMap
	 */
	public void fillcommandMap(HashMap<String,Command> commandMap)
	{
		commandMap.put("dir [^\n\r]+", new Command() {

			@Override
			public void doCommand(String[] args) {
				view.showDirContent(args[1]);
			}
		});
		commandMap.put("generate 3d maze +[^\n\r]+ [0-50]+ [0-50]+ [0-50]+", new Command() {
			@Override
			public void doCommand(String[] args) {
				model.handleGenerateMaze(args[3],Integer.parseInt(args[4]),Integer.parseInt(args[5]),Integer.parseInt(args[6]));
			}
		});
		commandMap.put("display (?!cross section by)(?!solution)[^\n\r]+",new Command() {

			@Override
			public void doCommand(String[] args) {
				try {
					view.showGeneratedMaze(model.getMazeMap().get(args[1]).toByteArray());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		commandMap.put("display cross section by [XYZxyz] [0-50]+ for [^\n\r]+", new Command() {

			@Override
			public void doCommand(String[] args) {
				try {
					view.showCrossSection(model.getMazeMap().get(args[7]).toByteArray(),args[4], Integer.parseInt(args[5]),args[7]);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		commandMap.put("save maze [^\n\r]+ [^\n\r]+", new Command() {

			@Override
			public void doCommand(String[] args) {
				model.handleSaveMaze(args[2],args[3]);
			}
		});
		commandMap.put("load maze [^\n\r]+ [^\n\r]+", new Command() {

			@Override
			public void doCommand(String[] args) {
				model.handleLoadMaze(args[2],args[3]);
			}
		});
		commandMap.put("solve [^\n\r]+ [^\n\r]+", new Command() {

			@Override
			public void doCommand(String[] args) {
				model.handleSolveMaze(args[1],args[2]);
			}
		});
		commandMap.put("display solution [^\n\r]+", new Command() {

			@Override
			public void doCommand(String[] args) {
				view.showSolution(model.getSolutionMap().get(model.getMazeMap().get(args[2])));
			}
		});
		commandMap.put("exit", new Command() {

			@Override
			public void doCommand(String[] args) {
				
			}
		});
		commandMap.put("exit", new Command() {

			@Override
			public void doCommand(String[] args) {
				model.handleExit();
				
			}
		});
		commandMap.put("help", new Command() {

			@Override
			public void doCommand(String[] args) {
				view.showMessage("----------------------------------------------");
				view.showMessage("dir <path>");
				view.showMessage("generate 3d maze <maze's name> <y size(Levels)> <x size(Rows)> <z size(Columns)>");
				view.showMessage("display <maze's name>");
				view.showMessage("display cross section by <X|Y|Z> <index> for <maze's name>");
				view.showMessage("save maze <maze's name> <file name | file path>");
				view.showMessage("load maze <file name| file path> <maze's name>");
				view.showMessage("solve <maze's name> <Bfs | Dfs>");
				view.showMessage("display solution <maze's name>");
				view.showMessage("Type exit to close the game");
				view.showMessage("");
				view.showMessage("");
				view.showMessage("----------------------------------------------");				
			}
		});
	}
	
	
	
	
	
	@Override
	public HashMap<String, Command> getCommandMap() {
		return this.commandMap;
	}

	@Override
	public Model getModel() {
		return this.model;
	}

	@Override
	public View getView() {
		return this.view;
	}

	@Override
	public void setView(View view) {
		this.view=view;
	}

	@Override
	public void setModel(Model model) {
		this.model=model;
	}

	@Override
	public void setCommandMap(HashMap<String, Command> map) {
		this.commandMap=map;
	}

	@Override
	public void sendToView(String string) {
		this.view.showMessage(string);
	}

}
