import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PanelStateManager extends Manager {
	ArrayList<PanelStates> p;
	int currStates = 0;
	public static int VIEWPAGE = 0;
	public static int SEARCHPAGE = 1;
	PanelStateManager() {
		super(Connect.getConnection(), 0);
		// TODO Auto-generated constructor stub
		init();
	}
	public PanelStates addPanelState(int x) {
		PanelStates temp = null;
		if(x == VIEWPAGE) {
			temp = new ViewPanel(con,this);
		}
		p.add(temp);
		return temp;
	}
	public PanelStates addSearchPage(ResultSet rs, int x) {
		PanelStates temp = null;
		if(x == SEARCHPAGE) {
			temp = new SearchPage(con,this,rs);
		}
		p.add(temp);
		return temp;
	}
	public void changeState(Component a) {
		for (int i = 0 ; i < p.size(); i ++) {
			if(p.get(i).equals(a)) {
				currStates = i;
				break;
			}
		}	
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		p.get(currStates).actionPerformed(e.getSource());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		p.get(currStates).mouseClicked(e.getSource());
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

	@Override
	public void init() {
		// TODO Auto-generated method stub
		p = new ArrayList<>();
	}

}
