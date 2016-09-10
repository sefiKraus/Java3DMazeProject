package boot;

import java.io.FileNotFoundException;

import Model.MyModel;
import Presenter.Presenter;
import View.MyView;

public class Run {

	
	public static void main(String[] args) throws FileNotFoundException {
		MyModel m= new MyModel();
		MyView v=new MyView();
		Presenter p=new Presenter(v, m);
		m.addObserver(p);
		v.addObserver(p);
		v.start();
	/*	Properties properties=new Properties("127.0.0.2", "2400", true, true, 200, 200, 3);
		PropertiesXmlHandler handler=new PropertiesXmlHandler();
		handler.writeProperties(properties, "res/properties.xml");
		Properties p=handler.getPropertiesInstance();
		p.printProperties();
		Properties temp=handler.readProperties("res/properties.xml");
		temp.printProperties();*/

		
		
	}

}
