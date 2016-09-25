package serverProject;

import java.io.Serializable;

public class Properties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int serverPort;
	private int amountOfClients;
	
	public Properties() {
		this.serverPort=5400;
		this.amountOfClients=10;
	}
	
	public Properties(int serverPort, int amountOfClients) {
		this.serverPort = serverPort;
		this.amountOfClients = amountOfClients;
	}

	public void printProperties()
	{
		System.out.println("Server port: "+this.getServerPort());
		System.out.println("Total clients allowed: "+this.getAmountOfClients());
	}
	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public int getAmountOfClients() {
		return amountOfClients;
	}

	public void setAmountOfClients(int amountOfClients) {
		this.amountOfClients = amountOfClients;
	}
	
	
	public String[] getPropertiesAndValues()
	{
		
		StringBuilder builder=new StringBuilder();
		builder.append("serverPort "+this.serverPort+" "+"Int"
				+"\namountOfClients "+this.amountOfClients+" "+"Int");
		String[] data=builder.toString().split("\n");

		
		return data;
		
	}

	
}
