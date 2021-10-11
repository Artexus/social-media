import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;


public class NewPost extends InternalStates {
	JLabel title,availability;
	JPanel top, center, bot, topLeft, main, addPanel, jcbPanel;
	JTextArea textArea;
	User user;
	JScrollPane scrollPane;
	String avalabilityStrings [] = {
		"Public", "Private", "Only Me"
	};
	JButton add;
	JComboBox jcb = new JComboBox(avalabilityStrings);
	NewPost(InternalStateManager ism) {
		super(ism);
		init();
		setSize(new Dimension(450,450));
		setVisible(true);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		user = User.getUser();
		main = new JPanel(new BorderLayout());
		topLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		top = new JPanel();
		center = new JPanel();
		bot = new JPanel(new GridLayout(3,1));
		jcbPanel = new JPanel();
		
		title = new JLabel("What is on your mind ? ");
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(400,400));
		addPanel = new JPanel();
		
		
		availability = new JLabel("Availability");
		add = new JButton("Add");
		add.setPreferredSize(new Dimension(400,20));
		add.addActionListener(ism);
		addPanel.add(add);
		
		jcb.setPreferredSize(new Dimension(390, 20));
		jcbPanel.add(jcb);
		
		center.add(scrollPane);
		
		
		topLeft.add(title);
		top.add(topLeft);
		
		
		bot.add(availability);
		bot.add(jcb);
		bot.add(addPanel);
		
		main.add(top, BorderLayout.NORTH);
		main.add(bot, BorderLayout.SOUTH);
		main.add(center, BorderLayout.CENTER);
		
		add(main);
	}

	@Override
	public void actionPerformed(Object e) {
		// TODO Auto-generated method stub
		if(e == add) {
			String desc = textArea.getText();
			int availability = jcb.getSelectedIndex() + 1;
			
			PreparedStatement ps = con.prepareStatement("INSERT INTO feed VALUES(null,?,?,?,?)");
		
			try {
				ps.setInt(1, user.id);
				ps.setDate(2, new Date(System.currentTimeMillis()));
				ps.setString(3,	desc);
				ps.setInt(4, availability);
				int i = ps.executeUpdate();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "success adding tweet !");
			this.dispose();
			ism.exitPage(this);
		}
	}

	@Override
	public void clicked(Object e) {
		// TODO Auto-generated method stub
		
	}

}
