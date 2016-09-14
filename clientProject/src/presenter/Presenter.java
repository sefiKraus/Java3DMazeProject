package presenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import view.ClientView;

public class Presenter implements Observer{
	
	private ClientView clientView;
	private Socket clientSocket;
	BufferedReader inFromServer,inFromClient;
	PrintWriter outToServer,outToClient;
	private HashMap<String, Command>commandMap;
	ClientProtocol clientProtocol;
	private int serverPort;
	private Properties properties;
	Thread presenterReaderThread;
	volatile boolean stop;
	 public Presenter(ClientView view,Socket clientSocket,Socket serverSocket) {
		 
		 this.properties=new Properties();
		 this.serverPort=properties.getServerPort();
		 this.clientSocket=clientSocket;
		 this.clientView=view;
		 this.serverPort=serverSocket.getPort();
		 
		 try {
			this.inFromServer=new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			this.outToServer=new PrintWriter(serverSocket.getOutputStream());
			this.inFromClient=new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			this.outToClient= new PrintWriter(this.clientSocket.getOutputStream());
			
			this.stop=true;
			
			this.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 
	 }
	 public void run()
	 {
		 this.presenterReaderThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
					String readFromClient;
				
				try {
					while(!(readFromClient=inFromClient.readLine()).equals("exit"))
					{
						//TODO: need to add methods by name
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		 
	 }
	@Override
	public void update(Observable o, Object arg) {

		if(this.clientView==o)
		{
			
		}
	}

}
