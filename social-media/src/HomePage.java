import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sound.midi.Soundbank;
import javax.swing.*;

public class HomePage extends State{
	JPanel main;
	JMenuBar menubar; 
	JMenu feed,account;
	JSeparator sep;
	JDesktopPane deskPane;
	JTabbedPane paneTab;
	InternalStateManager ism;
	JMenuItem viewFeed, newFeed, searchFeed,changeProfile, logoutProfile;
	HomePage(Connect con, StateManager sm){
		super(con,sm);
	
		init();
		setSize(new Dimension(1200,600));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public void init() {
		ism = new InternalStateManager();
		main = new JPanel(new BorderLayout());
		menubar = new JMenuBar();
		feed = new JMenu("Feed");
		account = new JMenu("Account");
		paneTab  = new JTabbedPane();
		deskPane = new JDesktopPane();
		
		viewFeed = new JMenuItem("View Feed");
		viewFeed.addActionListener(sm);
		
		newFeed = new JMenuItem("New Feed");
		newFeed.addActionListener(sm);
		
		JSeparator sep = new JSeparator();
		searchFeed = new JMenuItem("Search");
		searchFeed.addActionListener(sm);

		changeProfile = new JMenuItem("Change Profile");
		changeProfile.addActionListener(sm);
		logoutProfile = new JMenuItem("Logout");
		logoutProfile.addActionListener(sm);
		
		feed.add(viewFeed);
		feed.add(newFeed);
		feed.add(sep);
		feed.add(searchFeed);

		account.add(changeProfile);
		account.add(logoutProfile);
		
		menubar.add(feed);
		menubar.add(account);
		
		
		deskPane.add(ism.addNewPage(InternalStateManager.HOMEFEEDPAGE));
		
		deskPane.setBackground(Color.blue);
		
		main.add(menubar, BorderLayout.NORTH);
		main.add(deskPane,BorderLayout.CENTER);
		
		this.add(main);
	}
	@Override
	public void removeField() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(Object e) {
		// TODO Auto-generated method stub
		if(e == viewFeed) {
			if(ism.checkFeed()) {
				ism.createNewFeed("View Feed");
			}
			else {
				deskPane.add(ism.addNewPage(InternalStateManager.HOMEFEEDPAGE)).setFocusable(true);
			}
		}
		else if(e == logoutProfile) {
			User.userLogout();
			sm.setNewPage(StateManager.LOGIN);
		}
		else if(e == changeProfile) {
			deskPane.add(ism.addNewPage(InternalStateManager.CHANGEPROFILE, sm)).setFocusable(true);
		}
		else if(e == newFeed) {
			deskPane.add(ism.addNewPage(InternalStateManager.NEWFEED)).setFocusable(true);
		}
		else if(e == searchFeed) {
			String name = JOptionPane.showInputDialog("Input Username");
			ResultSet rs = con.executeQuery("SELECT * FROM users WHERE users.username = '" + name + "'");
			
			try{
				if(!rs.next()){
					JOptionPane.showMessageDialog(null, "User not found !","error",JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (HeadlessException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ism.goToHomePage(InternalStateManager.HOMEFEEDPAGE, rs, name);
		}
	}
	@Override
	public void mouseClicked(Object e) {
		// TODO Auto-generated method stub
	}
}
