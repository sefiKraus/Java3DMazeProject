package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;

public class CLI extends Observable implements Runnable {
	
	private BufferedReader in;
	private PrintWriter out;
	private Boolean flag;
	 String recivedCommand;
	
	
	
	public CLI(BufferedReader in, PrintWriter out) {
		super();
		this.in = in;
		this.out = out;
		this.flag=true;
	}



	@Override
	public void run() {
		
		String tempCommand="";
		System.out.println("-- Welcome player --");
		System.out.println("Type help to open Command Menu");
		System.out.println("Type GUI to GUI options");
		try {
			while(flag)
			{
				if(!(tempCommand=in.readLine()).isEmpty())
				{
				this.recivedCommand=tempCommand;
				this.setChanged();
				this.notifyObservers(this.recivedCommand);
				}
			}
		} catch (IOException e) {
			System.out.println(" Invalid Input ");
			e.printStackTrace();
		}
		
	}



	public BufferedReader getIn() {
		return in;
	}



	public void setIn(BufferedReader in) {
		this.in = in;
	}



	public PrintWriter getOut() {
		return out;
	}



	public void setOut(PrintWriter out) {
		this.out = out;
	}



	public Boolean getFlag() {
		return flag;
	}



	public void setFlag(Boolean flag) {
		this.flag = flag;
	}



	public String getRecivedCommand() {
		return recivedCommand;
	}



	public void setRecivedCommand(String recivedCommand) {
		this.recivedCommand = recivedCommand;
	}
	
	
	

}
