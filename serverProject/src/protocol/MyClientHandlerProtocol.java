package protocol;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import serverProject.Model;

public class MyClientHandlerProtocol implements ServerProtocol{

	private Model model;
	private HashMap<String, Command>commandMap;
	private Socket clientSocket;
	private PrintWriter outToClient;
	//private BufferedReader inFromClient;
	
	public MyClientHandlerProtocol(Model model,Socket clientSocket) {
		this.model = model;
		this.clientSocket=clientSocket;
		try {
			this.outToClient=new PrintWriter(clientSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.commandMap=new HashMap<String,Command>();
		this.fillCommandMap(this.model);
	}

	@Override
	public HashMap<String, Command> getCommandMap() {
		return this.commandMap;
	}

	@Override
	public void fillCommandMap(Model model) {
	this.commandMap.put("generate 3d maze +[^\n\r]+ [0-50]+ [0-50]+ [0-50]+",new Command() {
			
			@Override
			public void doCommand(String[] args) {
				model.handleGenerate(clientSocket ,args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]),Integer.parseInt(args[6]));
			}
		} );
		this.commandMap.put("display (?!cross section by)(?!solution)(?!solution list)[^\n\r]+",new Command() {
			
			@Override
			public void doCommand(String[] args) {
				outToClient.println("DisplayMaze");
				outToClient.flush();
				try {
					DataOutputStream dOut = new DataOutputStream(clientSocket.getOutputStream());
					dOut.writeInt((model.getMazeMap().get(args[1])).toByteArray().length);
					dOut.write(model.getMazeMap().get(args[1]).toByteArray());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} );	
		this.commandMap.put("display cross section by [XYZxyz] [0-9]+ for [^\n\r]+", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				try {
					outToClient.println("DisplayCross");
					outToClient.flush();
					
					DataOutputStream dOut = new DataOutputStream(clientSocket.getOutputStream());
					dOut.writeInt((model.getMazeMap().get(args[7])).toByteArray().length);
					dOut.write(model.getMazeMap().get(args[7]).toByteArray());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		this.commandMap.put("solve [^\n\r]+ [^\n\r]+", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				model.handleSolveMaze(clientSocket, args[1],args[2]);
			}
		});
		commandMap.put("display solution (?!list)[^\n\r]+",new Command() {

			@Override
			public void doCommand(String[] args) {
				outToClient.println("SolvedAlready");
				outToClient.flush();
				Solution<Position>tempSolution=model.getSolutionMap().get(model.getMazeMap().get(args[2]));
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
		});
		commandMap.put("display solution list", new Command() {

			@Override
			public void doCommand(String[] args) {
				outToClient.println(model.getSolutionMap());
				outToClient.flush();
			}
		});
		
	}

}
