package Presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import View.MyGuiView;
import View.View;
import protocol.Command;
import serverProject.Model;

public class Presenter implements Observer {

	Model m;
	View ui;
	
	HashMap<String, Command>commandMap;
	
	public Presenter() {

		super();
		this.commandMap=new HashMap<String,Command>();
		this.fillCommandMap();
	
	}
	
	
	public Presenter(Model model, View view) {
		super();
		this.m = model;
		this.ui = view;
		this.commandMap=new HashMap<String,Command>();
		this.fillCommandMap();	
		
	}



private void fillCommandMap() {

		commandMap.put("exit", new Command() {

			@Override
			public void doCommand(String[] args) {
				m.saveSolutionHashMapToZip();
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

		commandMap.put("get client list", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				ui.showClientList(m.handleGetClientList());
			}
		});
		commandMap.put("disconnect [^\n\r]+", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				m.handleDisconnectClient(args[1]);
			}
		});
	
		commandMap.put("display solution list", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				
				ui.showSolvedList(m.getMazeMap(),m.getSolutionMap());
				
			}
		});
	}

	
	


	@Override
	public void update(Observable o, Object arg) {

		String data=(String)arg;
		if(o==m)
		{
			switch (data) {
			case "RemovedClient":
			{
				ui.showMessage((String)m.getDataFromModel(data));
			}
				break;
			case "NewConnection":
			{
				ui.showMessage((String)m.getDataFromModel(data));
			}
			break;

			default:
				break;
			}
		}
		
		if(o==ui)
		{
			switch (data) {
			case "RemoveClient":
			{
				this.activateCommand((String)ui.getDataFromView(data));
			}
				break;
			case "Exit":
			{
				this.activateCommand((String)ui.getDataFromView(data));

			}
			break;
			case "ClientListRequest":
			{
				this.activateCommand((String)ui.getDataFromView(data));

			}
			break;
			case "MazeList":
			{
				this.activateCommand((String)ui.getDataFromView(data));

			}
			break;
			case "SaveSolutionMap":
			{
				this.activateCommand((String)ui.getDataFromView(data));

			}
			break;
			case "LoadSolutionMap":
			{
				this.activateCommand((String)ui.getDataFromView(data));

			}
			break;
			case "SolutionListRequest":
			{
				this.activateCommand((String)ui.getDataFromView(data));

			}
			break;
			default:
				break;
			}
		}
		
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
		this.setUi(new MyGuiView("Welcome Player",700,700));
		((Observable)this.ui).addObserver(this);
		this.getUi().start();

		

		}
	public Model getM() {
		return m;
	}
	public void setM(Model m) {
		this.m = m;
	}
	public View getUi() {
		return ui;
	}
	public void setUi(View ui) {
		this.ui = ui;
	}

	
	
	
}
