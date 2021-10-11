import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
public class RegisterPage extends State {
	JLabel titleLabel, nameLabel,usernameLabel, passwordLabel,confirmPassLabel,login,profileImage ;
	JTextField nameField, usernameField;
	JPasswordField passField, confirmPassField;
	JButton registerButton;
	JPanel northPanel,mainPanel, southPanel, centerPanel,southRightPanel, centerLeftPanel, centerRightPanel;
	String getPath;
	public RegisterPage(Connect con,StateManager sm) {
		super(con,sm);
		init();
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		setSize(650, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		mainPanel = new JPanel(new BorderLayout());
		centerLeftPanel = new JPanel();

		centerRightPanel = new JPanel(new GridLayout(10,1));

		northPanel = new JPanel();
		southPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new GridLayout(1,2));
		southRightPanel = new JPanel();
		//Labels
	
		titleLabel = new JLabel("Register");
		titleLabel.setFont(new Font("calibri",Font.BOLD, 20));
		nameLabel = new JLabel("Name");
		usernameLabel = new JLabel("Username");
		passwordLabel = new JLabel("Password");
		confirmPassLabel = new JLabel("Confirm Password");
		profileImage = new JLabel("                             Profile Image");
		profileImage.setFont(new Font("calibri",Font.BOLD, 11));

		profileImage.setBorder(BorderFactory.createLineBorder(Color.black));
		profileImage.setPreferredSize(new Dimension(200,210));
		profileImage.addMouseListener(sm);
		
		login = new JLabel("Already Have Account? Sign In");
		login.setFont(new Font("calibri", Font.BOLD, 13));
		login.setForeground(Color.BLUE);
		login.addMouseListener(sm);
		login.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
		

		//JTextField
		nameField = new JTextField();
		usernameField = new JTextField();
		
		//PassField
		passField = new JPasswordField();
		confirmPassField = new JPasswordField();
		//Button
		registerButton = new JButton("Register");
		registerButton.setBackground(Color.WHITE);
		registerButton.addActionListener(sm);
		
		//add
		northPanel.add(titleLabel);
		centerRightPanel.add(nameLabel);
		centerRightPanel.add(nameField);
		centerRightPanel.add(usernameLabel);
		centerRightPanel.add(usernameField);
		centerRightPanel.add(passwordLabel);
		centerRightPanel.add(passField);
		centerRightPanel.add(confirmPassLabel);
		centerRightPanel.add(confirmPassField);
		JSeparator sep = new JSeparator();
		sep.setVisible(false);
		centerRightPanel.add(sep);
		centerRightPanel.add(registerButton);
		southRightPanel.add(login);
		southPanel.add(southRightPanel, BorderLayout.EAST);
		
		centerLeftPanel.add(profileImage);
		
		centerPanel.add(centerLeftPanel);
		centerPanel.add(centerRightPanel);
		
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel,BorderLayout.CENTER);
		mainPanel.add(southPanel,BorderLayout.SOUTH);
		add(mainPanel);
		
		setVisible(true);
		
	}

	@Override
	public void actionPerformed(Object e) {
		// TODO Auto-generated method stub

		if(e == registerButton) {
			boolean errorS = false;
			String error = "";
			String name = nameField.getText();
			String username = usernameField.getText();
			String password = passField.getText();
			Icon icon = profileImage.getIcon();
			String confirmPassword = confirmPassField.getText();
			if(!errorS && name.equals(""))
			{
				errorS = true;
				error = "Name must be filled";
			}
			else if(!errorS && username.equals("")) {
				errorS = true;
				error = "Username must be filled";
			}
			else if(!errorS && username.equals("")) {
				for(int i = 0 ; i < username.length() ; i ++) {
					if(!Character.isAlphabetic(username.charAt(i)) && !Character.isDigit(username.charAt(i))) {
						errorS = true;
						error = "Username must be alphanumeric";
					}
				}
			}
			if(!errorS && password.equals("")) {
				errorS = true;
				error = "Password must be filled";
			}
			else if(!errorS && !password.equals("") && password.length() < 6) {
				errorS = true;
				error = "Password must be at least 6 characters";
			}
			if(!errorS && !password.equals(confirmPassword)) {
				errorS = true;
				error = "Confirm password must be the same with password";
			}
			if(!errorS && icon == null) {
				errorS = true;
				error = "Must upload Image";
			}
			if(!errorS){
				PreparedStatement ps = super.con.prepareStatement("INSERT INTO users VALUES(null,?,?,?,?)");
				try {
					ps.setString(1, name);
					ps.setString(2, username);
					ps.setString(3, password);

					File fi = new File(getPath);
					byte[] fileContent;
					try {
						fileContent = Files.readAllBytes(fi.toPath());
						Blob blob = new SerialBlob(fileContent);
						ps.setBlob(4, blob);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}		
					
					int i = ps.executeUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Register account successful !");
				sm.setNewPage(StateManager.LOGIN);
			}
		
			if(errorS) {
				JOptionPane.showMessageDialog(null, error,"error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void mouseClicked(Object e) {
		// TODO Auto-generated method stub
		if(e == login) {
			removeField();
			sm.setNewPage(StateManager.LOGIN);
		}
		else if(e == profileImage) {
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "JPG & GIF Images", "jpg", "gif");
			jfc.showSaveDialog(null);
			jfc.setFileFilter(filter);
			getPath = jfc.getSelectedFile().getAbsolutePath();
			File file = new File(jfc.getSelectedFile().getAbsolutePath());
			double kilobytes = file.length()/1024;
			double megabytes = kilobytes/1024;
			
			if(megabytes >= 1) {
				JOptionPane.showMessageDialog(null, "Image must be under 1 MB");
			}
			else {
				profileImage.setIcon(new ImageIcon(new ImageIcon(jfc.getSelectedFile().getAbsolutePath()).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
			}
		}
	}

	@Override
	public void removeField() {
		// TODO Auto-generated method stub
		nameField.setText("");
		usernameField.setText("");
		passField.setText("");
		confirmPassField.setText("");;
	}

}
