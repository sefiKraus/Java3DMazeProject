package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;


import Controller.Command;
/**
 * 
 * @author Krausz sefi
 * @since 07/09/2016
 *	This class represents CLI
 */
public class CLI extends Thread {
	private BufferedReader in;
	private PrintWriter out;
	private HashMap<String, Command> commandMap;
	private boolean flag;
	
	
	public CLI(BufferedReader in, PrintWriter out, HashMap<String, Command> commandMap) {
		super();
		this.in = in;
		this.out = out;
		this.commandMap = commandMap;
		this.flag=true;
	}
	
	@Override
	public void run() {
		String commandRecived;
		Command command;

		System.out.println("-- Welcome player --");
		System.out.println("Type help to open Command Menu");
		try {
			while(flag&&(!(commandRecived=in.readLine()).matches("exit")))
			{
				System.out.println();
				for (String com : this.commandMap.keySet()) {
				{
					if(commandRecived.matches(com))
					{
						String[] args=commandRecived.split(" ");
						command=this.commandMap.get(com);
						command.doCommand(args);
					}

				}
			}
			}
			System.out.println("Closing CLI, thank you");
			this.commandMap.get("exit").doCommand(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	public HashMap<String, Command> getCommandMap() {
		return commandMap;
	}
	public void setCommandMap(HashMap<String, Command> commandMap) {
		this.commandMap = commandMap;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
	
	
	
	
	


	
}
