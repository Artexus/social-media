import javax.swing.JInternalFrame;

public abstract class InternalStates extends JInternalFrame{
	
	public InternalStateManager ism;
	public Connect con;
	InternalStates(InternalStateManager ism){
		con = Connect.getConnection();
		this.ism = ism;
	}
	public abstract void init();
	public abstract void actionPerformed(Object e);
	public abstract void clicked(Object e);
}
