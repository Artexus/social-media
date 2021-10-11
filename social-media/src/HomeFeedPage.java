import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;

import javax.swing.*;

public class HomeFeedPage extends InternalStates {
	private JTabbedPane pane;
	private PanelStateManager psm;
	public HomeFeedPage(InternalStateManager ism) {
		super(ism);
		init();
		setSize(new Dimension(900,550));
		setResizable(false);
		setVisible(true);
	}
	public void init() {
		pane = new JTabbedPane();
		psm = new PanelStateManager();
		pane.addTab("View Feed",psm.addPanelState(PanelStateManager.VIEWPAGE));
		pane.addMouseListener(ism);
		add(pane);
	}
	public void createSearchFeed(ResultSet rs, String name){
		pane.addTab("User @" + name,psm.addSearchPage(rs,PanelStateManager.SEARCHPAGE));
	}
	public PanelStateManager getPanelStateManager() {
		return psm;
	}
	public void addTab(String temp, PanelStates panelStates) {
		pane.addTab(temp, panelStates);
	}
	@Override
	public void actionPerformed(Object e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void clicked(Object e) {
		// TODO Auto-generated method stub
		psm.changeState(pane.getSelectedComponent());
	}
}
