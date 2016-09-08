package Controller;

import java.util.HashMap;

import Model.Model;
import View.View;
/**
 * 
 * @author Krausz sefi
 * @since 07/09/2016
 *
 */
public interface Controller {

	public HashMap<String, Command>getCommandMap();
	public Model getModel();
	public View getView();
	public void setView(View view);
	public void setModel(Model model);
	public void setCommandMap(HashMap<String, Command>map);
	public void sendToView(String string);
}
