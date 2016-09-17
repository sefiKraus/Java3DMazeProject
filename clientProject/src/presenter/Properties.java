package presenter;

import java.io.Serializable;

public class Properties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int serverPort;
	private String serverIp;
	private boolean Gui;
	private int widthSize;
	private int heightSize;
	public Properties() {
		this.serverPort=5400;
		this.Gui=false;
		this.widthSize=400;
		this.heightSize=400;
		
	}


	public void printProperties()
	{
		System.out.println("Server port: "+this.getServerPort());
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public boolean isGui() {
		return Gui;
	}

	public void setGui(boolean gui) {
		Gui = gui;
	}

	public int getWidthSize() {
		return widthSize;
	}

	public void setWidthSize(int widthSize) {
		this.widthSize = widthSize;
	}

	public int getHeightSize() {
		return heightSize;
	}

	public void setHeightSize(int heightSize) {
		this.heightSize = heightSize;
	}


	public String getServerIp() {
		return serverIp;
	}


	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

}
