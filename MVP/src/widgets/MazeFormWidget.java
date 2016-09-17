package widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class MazeFormWidget{

	public MazeFormWidget(Composite c,int style) {
		
		Composite comp=new Composite(c, style);
		
		Label mazeNameLabel=new Label(comp, SWT.NONE);
		mazeNameLabel.setLayoutData(new GridData(SWT.None,SWT.NONE,false,false,1,1));
		mazeNameLabel.setText("Enter Maze Name:");
		
		Text mazeNameText= new Text(comp, SWT.BORDER);
		mazeNameText.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,1));
		
		
		
		
	}
	
	
}
