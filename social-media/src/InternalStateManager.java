import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;

public class InternalStateManager extends Manager {
	public static int HOMEFEEDPAGE = 0;
	private ArrayList<InternalStates> arr;
	public static int CHANGEPROFILE = 1;
	public static int NEWFEED = 2;
	public InternalStateManager() {
		super(Connect.getConnection(),0);
		init();
	}
	public void goToHomePage(int x,ResultSet rs, String name) {
		if(arr.get(x) != null) {
			((HomeFeedPage) (arr.get(x))).createSearchFeed(rs, name);
			return;
		}
		HomeFeedPage temp = new HomeFeedPage(this);
		arr.set(x, temp);
		
		((HomeFeedPage)temp).createSearchFeed(rs, name);
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		arr = new ArrayList<>();
		arr.add(null);
		arr.add(null);
		arr.add(null);
		arr.add(null);
	}

	public InternalStates addNewPage(int x) {
		// TODO Auto-generated method stub
		InternalStates temp = null;
		if(arr.get(x) != null)
			arr.get(x).dispose();
		if(x == HOMEFEEDPAGE) {
			temp = new HomeFeedPage(this);
		}
		else if(x == NEWFEED) {
			temp = new NewPost(this);
		}
		arr.set(x, temp);
		return temp;
	}
	public InternalStates addNewPage(int x, StateManager sm) {
		// TODO Auto-generated method stub
		InternalStates temp = null;
		if(arr.get(x) != null)
			arr.get(x).dispose();
		if(x == HOMEFEEDPAGE) {
			temp = new HomeFeedPage(this);
		}
		else if(x == CHANGEPROFILE) {
			temp = new UpdateProfile(this,sm);
		}
		else if(x == NEWFEED) {
			temp = new NewPost(this);
		}
		arr.set(x, temp);
		return temp;
	}
	public void changeState(Component a) {
		for(int i = 0 ; i < arr.size(); i ++) {
			if(arr.get(i) != null && arr.get(i).equals(a)) {
				currState = i;
			}
		}
	}
	public void createNewFeed(String temp) {
		for (InternalStates x : arr) {
			if(x instanceof HomeFeedPage) {
				((HomeFeedPage) x).addTab(temp, ((HomeFeedPage) x).getPanelStateManager().addPanelState(PanelStateManager.VIEWPAGE));
			}
		}
	}
	public boolean checkFeed() {
		for (InternalStates x : arr) {
			if(x instanceof HomeFeedPage) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for (InternalStates internalStates : arr) {
			if(internalStates != null)
				internalStates.actionPerformed(e.getSource());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		for (InternalStates internalStates : arr) {
			if(internalStates != null)
				internalStates.clicked(e.getSource());
		}
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

	public void exitPage(Object e) {
		// TODO Auto-generated method stub
		for (InternalStates is : arr) {
			if(is == e)
			{
				is = null;
			}
		}
	}


	
}
