import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class ViewPanel extends PanelStates{
	JPanel bot, center;
	JScrollPane scrollPane;
	JButton refresh;
	JLabel temp;
	ViewPanel(Connect con, PanelStateManager psm){
		super(Connect.getConnection(), psm);
		init();
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		refresh = new JButton("Refresh");
		refresh.addActionListener(sm);
		center = new JPanel();
		scrollPane = new JScrollPane();
		loadData();
		
		add(refresh, BorderLayout.SOUTH);
	}
	public void loadData() {
		ResultSet rs = con.executeQuery("SELECT users.username, users.name, TweetDate, TweetDesc,ProfilePic FROM feed, users WHERE feed.UserId = users.userId ORDER BY TweetDate DESC LIMIT 10 ");
		try {
			JPanel temp = new JPanel();
			temp.setLayout(new GridLayout(10,1));
			while(rs.next()) {
				JSeparator sep = new JSeparator();

				JPanel flow = new JPanel(new FlowLayout(FlowLayout.LEFT));
				JPanel anFlow = new JPanel(new GridLayout(3,1));
				JPanel main = new JPanel(new BorderLayout());
				
				JPanel x = new JPanel(new FlowLayout(FlowLayout.LEFT));
				JPanel t = new JPanel(new GridLayout(2,1));
				JLabel a = new JLabel(rs.getString("name"));
				JLabel c = new JLabel('@' + rs.getString("username"));
				c.setForeground(Color.blue);
				
				JLabel b = new JLabel(rs.getString("TweetDate"));
				JLabel e = new JLabel(rs.getString("TweetDesc"));
				e.setSize(getWidth(), 20);
				
				Blob blob = rs.getBlob("ProfilePic");
				byte [] bytes = blob.getBytes(1, (int) blob.length());
				JLabel pic = new JLabel();
				ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
				
				BufferedImage img = null;
				try {
					img = ImageIO.read(bais);
					pic.setIcon(new ImageIcon(img.getScaledInstance(60, 75, Image.SCALE_SMOOTH))); 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("ERROR");
					e1.printStackTrace();
				}
				x.add(e);
				x.setBackground(Color.white);

				t.add(a);
				t.add(c);

				flow.add(pic);
				flow.add(t);
				
				main.add(flow);
				main.add(b, BorderLayout.EAST);
				
				anFlow.add(main);
				anFlow.add(x);
				anFlow.add(sep);
				
				temp.add(anFlow);
			}
			scrollPane.setViewportView(temp);
			add(scrollPane);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	@Override
	public void actionPerformed(Object e) {
		// TODO Auto-generated method stub
		if(e == refresh) {
			loadData();
		}
	}
	@Override
	public void mouseClicked(Object e) {
		// TODO Auto-generated method stub
		
	}
	
}
