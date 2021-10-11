import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class StateManager extends Manager{
	public static int LOGIN = 0;
	public static int REGISTER = 1;
	public static int HOME = 2;
	private ArrayList<State> states;
	public StateManager() {
		super(Connect.getConnection(),0);
		init();
	}
	public void init() {
		super.currState = 0;
		states = new ArrayList<>();
		//reserve 2 states
		states.add(null);
		states.add(null);
		states.add(null);
		currState = 0;
		setNewPage(0);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		states.get(currState).actionPerformed(e.getSource());
	}
	public void setNewPage(int x) {
		if(states.get(currState) != null)
			states.get(currState).dispose();
		currState = x;
		if(x == REGISTER) {
			states.set(x, new RegisterPage(con,this));
		}
		else if(x == LOGIN) {
			states.set(x, new LoginPage(con,this));
		}
		else if(x == HOME) {
			states.set(x, new HomePage(con,this));
		}	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		states.get(currState).mouseClicked(e.getSource());
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
