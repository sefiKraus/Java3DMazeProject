package serverProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Bfs;
import algorithms.search.CommonSearcher;
import algorithms.search.Dfs;
import algorithms.search.Searchable3dMaze;
import algorithms.search.Solution;
import protocol.ServerProtocol;

public class MyServerModel extends CommonServerModel {

	volatile boolean stop;
	volatile int connectedClients=0;
	volatile int connectedClientsAllowed=0;
	
	
	private HashMap<String, Maze3d> mazeMap;
	private HashMap<String, Object> notifications;
	private HashMap<Maze3d, Solution<Position>> solutionMap;

	//private ArrayList<ClientHandler> clientList;
	private CopyOnWriteArrayList<ClientHandler> clientList;
	private ServerSocket modelSocket;
	private int modelPort;
	private Thread modelThread;
	PrintWriter outToAdmin;
	BufferedReader inFromAdmin;
	
	ServerProtocol clientHandlerProtocol;
	ExecutorService threadPool;
	
	
	public MyServerModel() {
		try {
			this.handleLoadProperties("res/properties.xml");
			this.mazeMap=new HashMap<String,Maze3d>();
			this.solutionMap=new HashMap<Maze3d,Solution<Position>>();
			this.notifications=new HashMap<String,Object>();
			this.modelSocket= new ServerSocket(PropertiesXmlHandler.getPropertiesInstance().getServerPort());
			this.modelPort=PropertiesXmlHandler.getPropertiesInstance().getServerPort();
			this.connectedClientsAllowed=PropertiesXmlHandler.getPropertiesInstance().getAmountOfClients();
			this.threadPool=Executors.newFixedThreadPool(this.connectedClientsAllowed);
			this.clientList=new CopyOnWriteArrayList<ClientHandler>();
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
				String data=(String)clientHandler.getData();
				switch (data) {
				case "exit":
				{
					int port=clientHandler.getClientSocket().getPort();
						deleteObserver((Observer) clientHandler);

						clientHandler.getOutToClient().println("exit");;
						clientHandler.getOutToClient().flush();
						this.clientList.remove(clientHandler);

					this.connectedClients--;
					this.noteObservers("RemovedClient","removed client with port: "+port);
					
				}
					break;

				default:
					break;
				}
			}
		}
	}


	public void run(MyServerModel model) {

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
							//noteObservers("NewConnection","New client connected with port: "+tempClient.getClientSocket().getPort());
							
						}
						else
						{
							PrintWriter outToClient=new PrintWriter(someClient.getOutputStream());
							outToClient.println("Error Server is busy please try again later");
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
					outToClient.println("Ready "+name+" has been generated please type: display "+name+" when you ready");
					outToClient.flush();
				}  catch (Exception e)
				{
					e.printStackTrace();
				}
				noteObservers("NewMazeGenerated", "New maze created: "+name);

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
Future<Solution<Position>> solution=null;
PrintWriter outToClient;
try {
	outToClient = new PrintWriter(clientSocket.getOutputStream());

	if((algorithmUsed.matches("[Dd][Ff][Ss]")||algorithmUsed.matches("[Bb][Ff][Ss]"))&& this.mazeMap.containsKey(name))
	{
		if(this.solutionMap.containsKey(this.mazeMap.get(name)))
		{
			outToClient.println("SolvedAlready");
			outToClient.flush();
			Solution<Position>tempSolution=this.solutionMap.get(this.mazeMap.get(name));
			ArrayList<Position>positionList=tempSolution.getSolution();
			StringBuilder sb = new StringBuilder(positionList.size());
			for (Position position : positionList) {
				sb.append("(");
				sb.append(position.toString());
				sb.append("),");
			}
			outToClient.println(sb.toString());
			outToClient.flush();
		}
		else if(algorithmUsed.matches("[Dd][Ff][Ss]"))
		{
			
			solution=this.threadPool.submit(new Callable<Solution<Position>>() {

				@Override
				public Solution<Position> call() throws Exception {
					CommonSearcher<Position> searcher=new Dfs<Position>();
					Searchable3dMaze searchable3dMaze=new Searchable3dMaze(mazeMap.get(name));
					ArrayList<Position> solution=new ArrayList<Position>();
					solution=searcher.search(searchable3dMaze).getSolution();
					return new Solution<Position>(solution);
				}
			});
			try{
				this.solutionMap.put(this.mazeMap.get(name),solution.get());
				outToClient.println("Solved Solution for: "+name+" with algorithm: "+algorithmUsed+" is ready");
				outToClient.flush();
			}catch(Exception e)
			{
				outToClient.println("Error Encountered problem during solving: "+name+" with algorithm: "+algorithmUsed);
				outToClient.flush();
				e.printStackTrace();
			}
		}
		else if(algorithmUsed.matches("[Bb][Ff][Ss]"))
		{
			solution=this.threadPool.submit(new Callable<Solution<Position>>() {

				@Override
				public Solution<Position> call() throws Exception {
					
					CommonSearcher<Position> searcher=new Bfs<Position>();
					Searchable3dMaze searchable3dMaze=new Searchable3dMaze(mazeMap.get(name));
					ArrayList<Position> solution=new ArrayList<Position>();
					solution=searcher.search(searchable3dMaze).getSolution();
					return new Solution<Position>(solution);
				}
			});
			try
			{
				this.solutionMap.put(this.mazeMap.get(name),solution.get());
				outToClient.println("Solved Solution for: "+name+" with algorithm: "+algorithmUsed+" is ready");
				outToClient.flush();
			}
			catch(Exception e)
			{
				outToClient.println("Error Encountered problem during solving: "+name+" with algorithm: "+algorithmUsed);
				outToClient.flush();
				e.printStackTrace();
			}
		}
			
	}
	else
	{
		outToClient.println("Error solving maze with algorithm: "+algorithmUsed+" please enter valid algorithm");
		outToClient.flush();
	}		
} catch (IOException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}

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
			this.stop=false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void saveSolutionHashMapToZip() {
		HashMap<byte[],Solution<Position>> cashed=new HashMap<byte[],Solution<Position>>();
		Iterator<Maze3d>itr=solutionMap.keySet().iterator();
		while(itr.hasNext())
		{
			Maze3d maze=itr.next();
			
			try {
				cashed.put(maze.toByteArray(), solutionMap.get(maze));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fileOut=new FileOutputStream("solutions.zip");
			GZIPOutputStream zipOut=new GZIPOutputStream(fileOut);
			ObjectOutputStream objOut=new ObjectOutputStream(zipOut);
			objOut.writeObject(cashed);
			objOut.flush();
			objOut.close();
			fileOut.close();
			this.noteObservers("SaveSolutionList","Solution Map was successfully saved");
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void loadSolutionHashMapFromZip() {
		HashMap<byte[], Solution<Position>> cashed=new HashMap<byte[],Solution<Position>>();
		try
		{
		FileInputStream fileIn=new FileInputStream("solutions.zip");
		GZIPInputStream zipIn=new GZIPInputStream(fileIn);
		ObjectInputStream objIn=new ObjectInputStream(zipIn);
		cashed=(HashMap)objIn.readObject();
		objIn.close();
		fileIn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		Set set=cashed.entrySet();
		Iterator itr=set.iterator();
		int i=0;
		while(itr.hasNext())
		{
			i++;
			Map.Entry entry=(Map.Entry)itr.next(); 
			try {
				Maze3d maze=new Maze3d((byte[])entry.getKey());
				this.mazeMap.put("loaded"+i,maze);
				this.solutionMap.put(maze,(Solution<Position>)entry.getValue());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.noteObservers("Loaded", "Solution map was successfully loaded");
		
	}

	@Override
	public void handleChangeMazeStartPosition(Socket client,String mazeName, Position position) {
		
		Maze3d tempMaze=this.mazeMap.get(mazeName);
		tempMaze.getStartPosition().setY(position.getY());
		tempMaze.getStartPosition().setX(position.getX());
		tempMaze.getStartPosition().setZ(position.getZ());
		
	}

	@Override
	public void handleLoadProperties(String xmlPath) {

		File file=new File(xmlPath);
		
		if(file.exists())
		{
			try {
				PropertiesXmlHandler.readProperties(xmlPath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else
		{
			try {
				PropertiesXmlHandler.readProperties("res/properties.xml");
				this.noteObservers("Error", "Error loading xml properties from requiered path loading default form res/properties.xml");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void handleSaveProperties(Properties prop, String xmlPath) {

		try {
			PropertiesXmlHandler.writeProperties(prop, xmlPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void noteObservers(String s, Object data) {
		this.notifications.put(s,data);
		this.setChanged();
		this.notifyObservers(s);		
	}

	@Override
	public void handleDisconnectClient(String port) {
		
		for (ClientHandler clientHandler : clientList) {
			if(clientHandler.getClientSocket().getPort()==Integer.valueOf(port))
			{
				clientHandler.closeClientHandler();
				//noteObservers("RemovedClient", "Removed Client With Port: "+port);
			}
		}
		
	}

	@Override
	public CopyOnWriteArrayList<ClientHandler> handleGetClientList() {
		return this.clientList;
	}

	@Override
	public Object getDataFromModel(String required) {
		return this.notifications.get(required);
	}


}
