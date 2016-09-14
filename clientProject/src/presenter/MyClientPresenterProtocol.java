package presenter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class MyClientPresenterProtocol implements ClientProtocol {

	private Presenter presenter;
	private HashMap<String, Command>commandMap;
	private Socket presenterSocket;
	private PrintWriter outToServer;
	public MyClientPresenterProtocol(Presenter presenter,Socket presenterSocket)
	{
		this.presenter=presenter;
		this.presenterSocket=presenterSocket;
		this.commandMap=new HashMap<String,Command>();
		try {
			this.presenter.outToServer=new PrintWriter(presenterSocket.getOutputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	@Override
	public HashMap<String, Command> getCommandMap() {
		return this.commandMap;
	}

	@Override
	public void fillCommandMap(Presenter presenter) {
		
	}

}
