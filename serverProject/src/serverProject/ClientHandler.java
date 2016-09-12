package serverProject;

import java.net.Socket;

public interface ClientHandler {
	public void handleClient();
	public Object getData();
	public Socket getClientSocket(String ip,int port);
	public void closeClientHandler();
	
}
