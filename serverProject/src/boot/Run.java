package boot;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import Presenter.Presenter;
import algorithms.mazeGenerators.Maze3d;
import serverProject.Model;
import serverProject.MyServerModel;
import serverProject.PropertiesXmlHandler;

public class Run {
	static volatile boolean stop;

	public static void main(String[] args) throws UnknownHostException, IOException {
		Presenter p=new Presenter();
		MyServerModel model=new MyServerModel();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ClientTest clientTest=new ClientTest();
				clientTest.run();
			}
		}).start();
	
		p.setM(model);
		model.addObserver(p);
		p.initViewFromPresenter("res/properties.xml");
		
}

	}


