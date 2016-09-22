package boot;



import java.io.FileNotFoundException;

import Model.Model;
import Model.MyModel;
import Model.PropertiesXmlHandler;
import Presenter.Presenter;
import Presenter.Properties;
import View.MyGuiView;
import View.MyView;
import View.View;

public class Run {

	
	public static void main(String[] args) throws FileNotFoundException {
/*		MyModel m= new MyModel();
		MyView v=new MyView();
		Presenter p=new Presenter(v, m);
		m.addObserver(p);
		v.addObserver(p);
		v.start();*/
		//Properties properties=new Properties("127.0.0.1", "2400", true, true, 200, 200, 3);
	//	PropertiesXmlHandler handler=new PropertiesXmlHandler();
		//PropertiesXmlHandler.writeProperties(properties, "res/properties.xml");
/*		Properties p=handler.getPropertiesInstance();
		p.printProperties();
		Properties temp=handler.readProperties("res/properties.xml");
		temp.printProperties();*/
		Presenter p=new Presenter();
		MyModel model=new MyModel();
		p.setM(model);
		model.addObserver(p);
		p.initViewFromPresenter("res/properties.xml");
		
	}

}
