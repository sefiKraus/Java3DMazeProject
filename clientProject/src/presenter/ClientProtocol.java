package presenter;

import java.util.HashMap;

public interface ClientProtocol {
	
	public HashMap<String, Command>getCommandMap();
	
	public void fillCommandMap(Presenter presenter);
	
}
