package presenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import view.ClientView;

public class Presenter implements Observer{


	private Properties properties;
	private Socket clientSocket;
	private ServerSocket serverSocket;
	BufferedReader inFromClientSocket;
	PrintWriter outToClientSocket;
	BufferedReader inFromServerSocket;
	PrintWriter outToServerSocket;
	ClientView view;
	Thread presenterThreadReader;
	public Presenter(ClientView view) {
		
		this.properties=new Properties();
		this.view=view;
		try {
			this.clientSocket=new Socket(this.properties.getServerIp(),this.properties.getServerPort());
			this.outToClientSocket=new PrintWriter(this.clientSocket.getOutputStream());
			this.inFromClientSocket=new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void run()
	{
		
	}
	public void fillCommandMap()
	{
		
	}
	@Override
	public void update(Observable o, Object arg) {
		String data=(String)arg;
		
		if(this.view==o)
		{
			switch (data) {
			case ("CLI"):
			{
				
			}
				break;

			default:
				break;
			}
		}
		
	}
	
}
