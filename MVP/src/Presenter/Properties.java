package Presenter;

import java.io.Serializable;

public class Properties implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1986911182174581073L;

	private String Ip;
	private String Port;
	private boolean sound;
	private boolean GUI;
	private Integer windowWidthSize;
	private Integer windowHeightSize;
	private int amountOfThreads;

	public Properties() {
		this.Ip="127.0.0.1";
		this.Port="3600";
		this.sound=false;
		this.windowHeightSize=400;
		this.windowWidthSize=400;
		this.amountOfThreads=1;
		this.GUI=true;
	}
	public Properties(String ip, String port, boolean sound, boolean gUI, Integer windowWidthSize,
			Integer windowHeightSize, int amountOfThreads) {
		super();
		Ip = ip;
		Port = port;
		this.sound = sound;
		GUI = gUI;
		this.windowWidthSize = windowWidthSize;
		this.windowHeightSize = windowHeightSize;
		this.amountOfThreads = amountOfThreads;
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
	
	
}
