package View;
/**
 * 
 * @author Krausz Sefi 305371320
 * @since 10/09/2016
 */
	public abstract class CommonGuiView extends BasicWindow implements Runnable{

	public CommonGuiView(String windowTitle, int width, int height) {
		super(windowTitle, width, height);
	}
	@Override
	void initWidgets() {
		
	}
	
}
