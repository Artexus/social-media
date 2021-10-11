import javax.swing.JPanel;

public abstract class PanelStates extends JPanel{
	PanelStateManager sm;
	Connect con;
	PanelStates(Connect con, PanelStateManager sm){
		this.sm = sm;
		this.con = con; 
	}
	public abstract void init();
	public abstract void actionPerformed(Object e);
	public abstract void mouseClicked(Object e);
}
