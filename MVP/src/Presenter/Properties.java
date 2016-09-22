package Presenter;

import java.io.Serializable;
/**
 * 
 * @author Krausz Sefi 305371320
 * @since 10/09/2016
 */
public class Properties implements Serializable{

	private static final long serialVersionUID = 1986911182174581073L;

	private String Ip;
	private String Port;
	private boolean sound;
	private boolean GUI;
	private Integer windowWidthSize;
	private Integer windowHeightSize;
	private int amountOfThreads;

	public Properties() {
/*		this.Ip="127.0.0.1";
		this.Port="3600";
		this.sound=false;
		this.windowHeightSize=400;
		this.windowWidthSize=400;
		this.amountOfThreads=1;
		this.GUI=false;*/
	}
	public Properties(String ip, String port, boolean sound, boolean GUI, Integer windowWidthSize,
			Integer windowHeightSize, int amountOfThreads) {
		super();
		Ip = ip;
		Port = port;
		this.sound = sound;
		this.GUI = GUI;
		this.windowWidthSize = windowWidthSize;
		this.windowHeightSize = windowHeightSize;
		this.amountOfThreads = amountOfThreads;
	}
	
	
	
	public Properties(String ip, String port, String sound, String GUI, String windowWidthSize,
			String windowHeightSize, String amountOfThreads) {
		super();
		Ip = ip;
		Port = port;
		this.sound = Boolean.valueOf(sound);
		this.GUI = Boolean.valueOf(GUI);
		this.windowWidthSize = Integer.parseInt(windowWidthSize);
		this.windowHeightSize = Integer.parseInt(windowHeightSize);
		this.amountOfThreads = Integer.parseInt(amountOfThreads);
	}
	public String getIp() {
		return Ip;
	}
	public void setIp(String ip) {
		Ip = ip;
	}
	public String getPort() {
		return Port;
	}
	public void setPort(String port) {
		Port = port;
	}
	public boolean isSound() {
		return sound;
	}
	public void setSound(boolean sound) {
		this.sound = sound;
	}
	public boolean isGUI() {
		return GUI;
	}
	public void setGUI(boolean gUI) {
		GUI = gUI;
	}
	public Integer getWindowWidthSize() {
		return windowWidthSize;
	}
	public void setWindowWidthSize(Integer windowWidthSize) {
		this.windowWidthSize = windowWidthSize;
	}
	public Integer getWindowHeightSize() {
		return windowHeightSize;
	}
	public void setWindowHeightSize(Integer windowHeightSize) {
		this.windowHeightSize = windowHeightSize;
	}
	public int getAmountOfThreads() {
		return amountOfThreads;
	}
	public void setAmountOfThreads(int amountOfThreads) {
		this.amountOfThreads = amountOfThreads;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void printProperties()
	{
		System.out.println("ip: "+this.getIp()+" Port: "+this.getPort() );
		System.out.println("Gui mode: "+this.isGUI()+" sound?: "+this.isSound());
		System.out.println("Thread number: "+this.getAmountOfThreads());
		System.out.println("Height: "+this.getWindowHeightSize()+" width: "+this.getWindowWidthSize());
	}


	
}
