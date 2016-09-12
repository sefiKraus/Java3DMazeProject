package serverProject;

import java.io.BufferedReader;import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import javax.annotation.PostConstruct;

import protocol.ClientServerProtocol;

public class MyClientHandler extends CommonClientHandler{

	private Socket clientSocket;
	private Object data;
	private BufferedReader inFromClient;
	private PrintWriter outToClient;
	private Model model;
	public MyClientHandler(Socket clientSocket,Model model) {
		this.model=model;
		this.addObserver((CommonServerModel)model);
		this.clientSocket=clientSocket;
		try {
			this.inFromClient=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.outToClient=new PrintWriter(clientSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void handleClient() {
		/*
		 * line= name of method we want to activate
		 * size=amount of parameters passed
		 * string[] params= parameters passed
		 * This method using reflection pattern
		 */
		String line;
		String name;
		String[] params;
		int size;
		try {
			while(!(line=this.inFromClient.readLine()).equals("exit"))
			{
				params=line.split(",");
				name=params[0];
				size=Integer.valueOf(params[1]);
				Object[] objects=new Object[size];
				for(int i=0;i<objects.length;i++)
				{
					objects[i]=(Object)params[i+2];
				}
				model.activateMethod(name, objects);
			}
			this.closeClientHandler();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			case "Ready":
				this.outToClient.println("Ready");
				break;

			default:
				break;
			}
		}
	}

	@Override
	public void closeClientHandler() {
		try {
			this.notifyObservers(this);
			this.inFromClient.close();
			this.outToClient.close();
			this.clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	
}
