package protocol;

import java.util.HashMap;

import serverProject.Model;

public interface ServerProtocol {

	public HashMap<String, Command>getCommandMap();
	
	public void fillCommandMap(Model model);
	
}
