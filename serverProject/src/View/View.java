package View;

import java.util.ArrayList;
import java.util.Set;

import serverProject.ClientHandler;

public interface View {

	public void start();
	public void showMessage(String message);
	public void showExit();
	public void showMazeList(Set<String> set);
	public void disconnectClient(String clientPort);
	public void showClientList(ArrayList<ClientHandler>clientList);
	public Object getDataFromView(String s);

}
