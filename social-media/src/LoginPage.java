import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage extends State{
	JLabel usernameLabel,registerLabel,passwordLabel,loginLabel,register;
	JPanel mainPanel,northPanel, centerPanel, southPanel,southRightPanel;
	JTextField usernameField;
	JPasswordField passField;
	JButton loginButton;
	public LoginPage(Connect con, StateManager sm) {
		super(con,sm);
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public void init() {

		// TODO Auto-generated method stub
		setSize(new Dimension(450,250));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		initPanel();
		
	}
	private void initPanel() {
		loginLabel = new JLabel ("Login");
		loginLabel.setFont(new Font("calibri",Font.PLAIN,20));
		usernameLabel = new JLabel("Username");
		passwordLabel = new JLabel("Password");
		passField = new JPasswordField();
		usernameField= new JTextField();
		mainPanel = new JPanel(new BorderLayout());
		northPanel = new JPanel();
		centerPanel = new JPanel (new GridLayout(5,1));
		loginButton = new JButton("Login");
		southPanel = new JPanel(new BorderLayout());
		register = new JLabel("Don't have account? Register");
		register.setFont(new Font("calibri", Font.BOLD, 13));
		register.setForeground(Color.BLUE);
		register.addMouseListener(sm);
		register.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
		loginButton.addActionListener(sm);
		
		southRightPanel = new JPanel();
		
		
		northPanel.add(loginLabel);
		centerPanel.add(usernameLabel);
		centerPanel.add(usernameField);
		centerPanel.add(passwordLabel);
		centerPanel.add(passField);
		centerPanel.add(loginButton);
		
		southRightPanel.add(register);
		
		mainPanel.add(northPanel,BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		southPanel.add(southRightPanel, BorderLayout.EAST);
		
		add(mainPanel);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(Object e) {
		// TODO Auto-generated method stub
		if(e == loginButton) {
			try {
				
				PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username LIKE ? AND password LIKE ?");
				ps.setString(1, usernameField.getText());
				ps.setString(2, passField.getText());
				ResultSet rs = ps.executeQuery();
				
				if(!rs.next()) {
					JOptionPane.showMessageDialog(null,"invalid username/password","error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					int i = rs.getInt("UserId");
					String name = rs.getString("Name");
					Blob blob = rs.getBlob("ProfilePic");
					User user = User.getUser(i, name,usernameField.getText(), passField.getText(),blob);
					JOptionPane.showMessageDialog(null, "Login Successfull !");
					sm.setNewPage(StateManager.HOME);
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println("ERROR");
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(Object e) {
		// TODO Auto-generated method stub
		if(e == register) {
			removeField();
			sm.setNewPage(StateManager.REGISTER);
		}
	}

	@Override
	public void removeField() {
		// TODO Auto-generated method stub
		usernameField.setText("");
		passField.setText("");
	}
	
}
