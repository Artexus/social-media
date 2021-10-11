import java.awt.event.ActionListener;

import javax.swing.JFrame;

public abstract class State extends JFrame {
	StateManager sm;
	Connect con;
	State(Connect con, StateManager sm){
		this.sm = sm;
		this.con = con; 
	}
	State(){
		
	}
	public abstract void removeField();
	public abstract void init();
	public abstract void actionPerformed(Object e);
	public abstract void mouseClicked(Object e);
}
