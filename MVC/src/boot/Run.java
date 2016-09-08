package boot;
/**
 * @author Krausz sefi
 * @since 07/09/2016
 */
import Controller.Controller;
import Controller.MyController;
import Model.Model;
import Model.MyModel;
import View.MyView;
import View.View;

public class Run {

	public static void main(String[] args) {
		Controller controller=new MyController();
		View view=new MyView(controller);
		Model model=new MyModel(controller);
		controller.setModel(model);
		controller.setView(view);
		view.start();
		
	}

}
