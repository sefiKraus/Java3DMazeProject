package serverProject;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public interface ClientHandler {
	//public void handleClient(BufferedReader inFromClient,PrintWriter outToClient);
	public Object getData();
	public Socket getClientSocket(String ip,int port);
	public void closeClientHandler();
	public Socket getClientSocket();
	public void setClientSocket(Socket clientSocket);
	public BufferedReader getInFromClient();
	public void setInFromClient(BufferedReader inFromClient);
	public PrintWriter getOutToClient() ;
	public void setOutToClient(PrintWriter outToClient);
	
	
}
