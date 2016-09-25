package boot;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import algorithms.mazeGenerators.Maze3d;
import serverProject.Model;
import serverProject.MyServerModel;
import serverProject.PropertiesXmlHandler;

public class Run {
static volatile boolean stop;
	public static void main(String[] args) throws UnknownHostException, IOException {
		BufferedReader in;
		BufferedReader inClient;
		 PrintWriter out;
		Model model=new MyServerModel();
		stop=true;
		Socket client=new Socket("127.0.0.1", 5400);
		in=new BufferedReader(new InputStreamReader(client.getInputStream()));
		out=new PrintWriter(client.getOutputStream());
		inClient=new BufferedReader(new InputStreamReader(System.in));
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String dataRecieved;
				try {
					while(!(dataRecieved=in.readLine()).equals("exit"))
					{
						if(dataRecieved!=null)
						{
							String[] args=dataRecieved.split(" ");
							String data=(String)args[0];
							switch (data) {
							case "Ready":
							{
								System.out.println(dataRecieved);
							}
								break;
							case "DisplayMaze":
							{
								DataInputStream dIn = new DataInputStream(client.getInputStream());
								int length = dIn.readInt();  
								if(length>0) {
								    byte[] byteMaze = new byte[length];
								    dIn.readFully(byteMaze, 0, byteMaze.length); // read the message
								    Maze3d maze3d=new Maze3d(byteMaze);
								    maze3d.printMaze();
								}

								
							}
							break;
							case "DisplayCross":
							{
								DataInputStream dIn = new DataInputStream(client.getInputStream());
								int length = dIn.readInt();  
								if(length>0) {
								    byte[] byteMaze = new byte[length];
								    dIn.readFully(byteMaze, 0, byteMaze.length); // read the message
								    Maze3d maze3d=new Maze3d(byteMaze);
								   // maze3d.printMaze();
								    //TODO: create method in client side and send it following params: maze3d, cross by and index
								}
							}
							break;
							case "Solved":
							{
								System.out.println(dataRecieved);

							}
							break;
							case "SolvedAlready":
							{
								String line=in.readLine();
								System.out.println(line);
							}
							break;
							case "SolutionList":
							{

								DataInputStream dIn=new DataInputStream(client.getInputStream());
								int amount=dIn.readInt();
								if(amount>0)
								{
									
									int i=0;
									String mazeName=in.readLine();
									System.out.println(mazeName);
									while(i<amount)
									{
										String solution=in.readLine();
										System.out.println(solution);
										i++;	
									}
								}
							}
							break;
							case "Error":
							{
								System.out.println(dataRecieved);
							}
							break;
							default:
							{
								System.out.println(dataRecieved);
							}
								break;
							}
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		String lineFromUser;
		while(!(lineFromUser=inClient.readLine()).equals("exit"))
		{
			out.println(lineFromUser);
			out.flush();
		}
		out.println("exit");
		out.flush();
		client.close();
	}

}
