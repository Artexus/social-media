import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.event.InternalFrameListener;

public abstract class Manager implements ActionListener, MouseListener{
	public Connect con;
	public int currState;
	Manager(Connect con, int currState){
		this.con = con;
		this.currState = currState;
	}
	public abstract void init();
}
