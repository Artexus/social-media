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
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class SearchPage extends PanelStates{
	ResultSet rs;
	JPanel top, center,main, topLeft,nameDetailPanel,topRight;
	JLabel profilePic, name, username, followings;
	JLabel pic ;
	JScrollPane scrollPane;
	JButton refreshBtn;
	JButton followBtn;
	public SearchPage(Connect con, PanelStateManager ism, ResultSet rs) {
		super(con, ism);
		this.rs = rs;
		init();

	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		pic = new JLabel();
		topLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		top = new JPanel(new GridLayout(1,2));
		center = new JPanel();
		main = new JPanel(new BorderLayout());
		nameDetailPanel = new JPanel(new GridLayout(3,1));

		scrollPane = new JScrollPane();
		
		loadData();
	}
	public void loadData() {
		try {
			if(rs.next()) {
				name = new JLabel(rs.getString("users.Name"));
				username = new JLabel("@" + rs.getString("users.username"));
				followings = new JLabel("followings :  followed : ");
				refreshBtn = new JButton("Refresh");
				refreshBtn.addActionListener(sm);
				followBtn = new JButton("Follow");
				
				Blob blob = rs.getBlob("ProfilePic");
				byte [] bytes = blob.getBytes(1, (int) blob.length());
				
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
				
				nameDetailPanel.add(name);
				nameDetailPanel.add(username);
				nameDetailPanel.add(followings);
				
				topLeft.add(pic);
				topLeft.add(nameDetailPanel);
				topRight.add(followBtn);
				topRight.add(refreshBtn);
				

				JPanel temp =  new JPanel(new GridLayout(10,1));
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
					
					Blob blob1 = rs.getBlob("ProfilePic");
					byte [] bytes1 = blob.getBytes(1, (int) blob.length());
					JLabel pic = new JLabel();
					ByteArrayInputStream bais1 = new ByteArrayInputStream(bytes);
					
					BufferedImage img1 = null;
					try {
						img1 = ImageIO.read(bais);
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
				top.add(topLeft);
				top.add(topRight);
				main.add(top, BorderLayout.NORTH);
				main.add(scrollPane, BorderLayout.CENTER);
			
			}
			add(main);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(Object e) {
		// TODO Auto-generated method stub
		if(e == refreshBtn) {
			loadData();
		}
	}

	@Override
	public void mouseClicked(Object e) {
		// TODO Auto-generated method stub
		
	}

}
