package protocol;

import java.util.HashMap;

import serverProject.Model;

public class MyServerModelProtocol implements ServerProtocol {

	private Model model;
	private HashMap<String, Command>commandMap;
	
	
	public MyServerModelProtocol(Model model) {
		this.model = model;
		this.commandMap=new HashMap<String,Command>();
		this.fillCommandMap(this.model);
	}

	@Override
	public HashMap<String, Command> getCommandMap() {
		return this.commandMap;
	}

	@Override
	public void fillCommandMap(Model model) {
		
	
	}

}
