package View;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

abstract class BasicWindow extends CommonView implements Runnable{
	Display display;
	Shell shell;
	
	public BasicWindow(String windowTitle,int width,int height) {
		this.display=new Display();
		this.shell=new Shell(this.display);
		this.shell.setSize(width,height);
		this.shell.setText(windowTitle);
	}
	public BasicWindow(Shell shell, Display display) {
		super();
		this.shell = shell;
		this.display = display;
	}
	
	abstract void initWidgets();

	@Override
	public void run() {
		initWidgets();
		shell.open();
		while(!this.shell.isDisposed())
		{

		    // 1. read events, put then in a queue.
		    // 2. dispatch the assigned listener
			if(!this.display.readAndDispatch())
			{
				display.sleep();
			}
		}
		this.display.dispose();
	}
	public Display getDisplay() {
		return display;
	}
	public void setDisplay(Display display) {
		this.display = display;
	}
	public Shell getShell() {
		return shell;
	}
	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	
}