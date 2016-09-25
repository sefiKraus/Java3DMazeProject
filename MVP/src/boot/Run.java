package boot;



import java.io.FileNotFoundException;

import Model.MyModel;
import Presenter.Presenter;


public class Run {

	
	public static void main(String[] args) throws FileNotFoundException {

		Presenter p=new Presenter();
		MyModel model=new MyModel();
		p.setM(model);
		model.addObserver(p);
		p.initViewFromPresenter("res/properties.xml");
		
	
		
	}

}
