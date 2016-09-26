package serverProject;

import java.io.BufferedReader;import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

import protocol.MyClientHandlerProtocol;
import protocol.ServerProtocol;

public class MyClientHandler extends CommonClientHandler{
	volatile boolean stop;
	private Socket clientSocket;
	private Object data;
	private BufferedReader inFromClient;
	private PrintWriter outToClient;
	private Model model;
	private Thread clientHandlerThread;
	private ServerProtocol clientHandlerProtocol;
	
	
	public MyClientHandler(Socket clientSocket,Model model) {
		this.model=model;
		this.clientSocket=clientSocket;
		this.addObserver((CommonServerModel)model);
		try {
			this.inFromClient=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.outToClient=new PrintWriter(clientSocket.getOutputStream());
			this.clientHandlerProtocol=new MyClientHandlerProtocol(this.model,this.clientSocket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.stop=true;
		this.run();
	}

	public void run() {
		this.clientHandlerThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				String line;
				
				try {
					while(!(line=inFromClient.readLine()).equals("exit"))
					{

						for (String string : clientHandlerProtocol.getCommandMap().keySet()) {
							if(line.matches(string))
							{
								String[] params=line.split(" ");
								clientHandlerProtocol.getCommandMap().get(string).doCommand(params);

							}
						}
					}
					closeClientHandler();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		});
		this.clientHandlerThread.start();
	
	}


	@Override
	public Object getData() {
		return this.data;
	}

	@Override
	public Socket getClientSocket(String ip, int port) {
		return this.clientSocket;
	}

	@Override
	public void update(Observable o, Object obj) {
		String data=(String)obj;
		if(o==this.model)
		{
			switch (data) {
			case "disconnect":
			{
				this.closeClientHandler();
			}
				break;

			default:
				break;
			}
		}
	}

	@Override
	public void closeClientHandler() {
		/*try {
			this.data="exit";
			this.setChanged();
			this.notifyObservers(this);
			this.inFromClient.close();
			this.outToClient.close();
			this.clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		this.data="exit";
		this.setChanged();
		this.notifyObservers(this);
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public BufferedReader getInFromClient() {
		return inFromClient;
	}

	public void setInFromClient(BufferedReader inFromClient) {
		this.inFromClient = inFromClient;
	}

	public PrintWriter getOutToClient() {
		return outToClient;
	}

	public void setOutToClient(PrintWriter outToClient) {
		this.outToClient = outToClient;
	}

	
}
