package boot;

import Model.MyModel;
import Presenter.Presenter;
import View.MyView;

public class Run {

	
	public static void main(String[] args) {
		MyModel m= new MyModel();
		MyView v=new MyView();
		Presenter p=new Presenter(v, m);
		m.addObserver(p);
		v.addObserver(p);
		v.start();
		



	}

}
