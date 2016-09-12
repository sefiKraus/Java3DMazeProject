package protocol;

import java.util.HashMap;

public class MyClientServerProtocol implements ClientServerProtocol {

	HashMap<String, Command> commandMap;
	
	public MyClientServerProtocol(HashMap<String, Command>commandMap) {
		this.commandMap=commandMap;
	}

	@Override
	public HashMap<String, Command> getCommandMap() {
		return this.commandMap;
	}

}
