package serverProject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import protocol.Command;
import protocol.ServerProtocol;

public class MyServerModel extends CommonServerModel {

	volatile boolean stop;
	private HashMap<String, Maze3d> mazeMap;
	private HashMap<String, Object> notifications;
	private HashMap<Maze3d, Solution<Position>> solutionMap;
	volatile int connectedClients=0;
	volatile int connectedClientsAllowed=0;
	Properties properties;
	private ArrayList<ClientHandler> clientList;
	private ServerSocket modelSocket;
	private int modelPort;
	private Thread modelThread;
	PrintWriter outToAdmin;
	BufferedReader inFromAdmin;
	ServerProtocol clientHandlerProtocol;
	ExecutorService threadPool;
	public MyServerModel() {
		try {
			//this.properties=PropertiesXmlHandler.getPropertiesInstance();
			this.properties=new Properties();
			this.mazeMap=new HashMap<String,Maze3d>();
			this.solutionMap=new HashMap<Maze3d,Solution<Position>>();
			this.notifications=new HashMap<String,Object>();
			this.modelSocket= new ServerSocket(properties.getServerPort());
			this.modelPort=properties.getServerPort();
			this.connectedClientsAllowed=properties.getAmountOfClients();
			this.threadPool=Executors.newFixedThreadPool(this.properties.getAmountOfClients());
			this.clientList=new ArrayList<ClientHandler>(properties.getAmountOfClients());
			this.stop=true;
			this.inFromAdmin=new BufferedReader(new InputStreamReader(System.in));
			this.run(this);
			
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		for (ClientHandler clientHandler : clientList) {
			if(clientHandler==o)
			{
				String data=(String)arg;
				switch (data) {
				case "exit":
				{
					this.clientList.remove(clientHandler);
					this.connectedClients--;
				}
					break;

				default:
					break;
				}
			}
		}
	}


	public void run(MyServerModel model) {
/*		this.modelThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
			String commandFromAdmin;
			String[] params;
			try {
				while(!(commandFromAdmin=inFromAdmin.readLine()).equals("exit"))
				{
					System.out.println(commandFromAdmin);
				}
				inFromAdmin.close();
				stop=false;
				handleExit();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
		});
		this.modelThread.start();*/
		this.modelThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(stop)
				{
					try {
						Socket someClient=modelSocket.accept();
						if(someClient!=null && !clientList.contains(someClient) && connectedClients+1<=connectedClientsAllowed)
						{
							ClientHandler tempClient=new MyClientHandler(someClient,model);
							clientList.add(tempClient);		
							connectedClients++;
							tempClient.getOutToClient().println("New Connection Established with the server");
							tempClient.getOutToClient().flush();
							
						}
						else
						{
							PrintWriter outToClient=new PrintWriter(someClient.getOutputStream());
							outToClient.println("Server is busy please try again later");
							outToClient.flush();
							outToClient.close();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				model.handleExit();
			}
		});
		this.modelThread.start();
	}
	@Override
	public HashMap<String, Maze3d> getMazeMap() {
		return this.mazeMap;
	}

	@Override
	public HashMap<Maze3d, Solution<Position>> getSolutionMap() {
		return this.solutionMap;
	}

	@Override
	public HashMap<String, Object> getNotifications() {
		return this.notifications;
	}

	@Override
	public ServerSocket getServerSocket() {
		return this.modelSocket;
	}

	@Override
	public int getServerPort() {
		return this.modelPort;
	}

	@Override
	public void handleGenerate(Socket clientSocket,String name, int y, int x, int z) {
		
		try {
			PrintWriter outToClient=new PrintWriter(clientSocket.getOutputStream());

			if(!(this.mazeMap.containsKey(name)))
			{
				Future<Maze3d> futureMaze=threadPool.submit(new Callable<Maze3d>(){

					@Override
					public Maze3d call() throws Exception {
						Maze3dGenerator generator=new MyMaze3dGenerator();
						String overAllSize=String.valueOf(y)+","+String.valueOf(x)+","+String.valueOf(z);
						return generator.generate(overAllSize);
					}
					
					
				});
				try {
					this.mazeMap.put(name, futureMaze.get());
					//this.noteObservers("Ready",name);
					outToClient.println("Ready "+name+" has been generated please type: display "+name+" when you ready");
					outToClient.flush();
				}  catch (Exception e)
				{
					e.printStackTrace();
				}

			}
			else
			{
				outToClient.println("Error during generating requiered maze");
				outToClient.flush();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void handleSolveMaze(Socket clientSocket,String name, String algorithmUsed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleExit() {
		for (ClientHandler clientHandler : clientList) {
			clientHandler.closeClientHandler();
		}
		this.clientList.clear();
		connectedClients=0;
		this.saveSolutionHashMapToZip();
		try {
			this.modelSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void saveSolutionHashMapToZip() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadSolutionHashMapFromZip() {
		// TODO Auto-generated method stub
		
	}


}
