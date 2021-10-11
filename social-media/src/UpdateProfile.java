import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.desktop.UserSessionEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.swing.BorderFactory;
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

public class UpdateProfile extends InternalStates {
	JLabel title,nameLbl,usernameLbl, usernameTLbl, oldPasswordLbl, newPassLbl, confirmPassLbl, profilePic;
	JPanel top, main, left, right,center;
	JTextField name;
	JPasswordField oldPass, newPass, confPass;
	User user;
	JSeparator sep;
	Blob currBlob;
	String getPath;
	JButton updateBtn;
	boolean changed = false;
	private StateManager sm;
	
	public UpdateProfile(InternalStateManager ism, StateManager sm) {
		super(ism);
		this.sm = sm;
		user = User.getUser();
		init();
		setSize(new Dimension(600,450));
		setResizable(false);
		setVisible(true);
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		changed = true;
		title = new JLabel("Update Profile");
		title.setFont(new Font("Calibri", Font.BOLD, 30));
		top = new JPanel();
		main = new JPanel(new BorderLayout());
		left = new JPanel();
		right = new JPanel(new GridLayout(12,1));
		center = new JPanel(new GridLayout(1,2));
		updateBtn = new JButton("Update");
		updateBtn.addActionListener(ism);
		profilePic = new JLabel();
		
		nameLbl = new JLabel("Name");
		usernameLbl = new JLabel("Username");
		usernameTLbl = new JLabel();
		usernameTLbl.setText(user.username);
		oldPasswordLbl = new JLabel("Old Password");
		newPassLbl = new JLabel("New Password");
		confirmPassLbl = new JLabel("Confirm Password");
		
		name = new JTextField();
		oldPass = new JPasswordField();
		newPass = new JPasswordField();
		confPass = new JPasswordField();
		
		profilePic.setBorder(BorderFactory.createLineBorder(Color.black));
		profilePic.setPreferredSize(new Dimension(200,210));
		profilePic.addMouseListener(ism);
		
		sep = new JSeparator();
		sep.setVisible(true);
		

		top.add(title);
		right.add(nameLbl);
		right.add(name);
		right.add(usernameLbl);
		right.add(usernameTLbl);
		right.add(oldPasswordLbl);
		right.add(oldPass);
		right.add(newPassLbl);
		right.add(newPass);
		right.add(confirmPassLbl);
		right.add(confPass);
		right.add(sep);
		right.add(updateBtn);
		
		left.add(profilePic);
		
		center.add(left);
		center.add(right);
		loadImageData();
		main.add(top, BorderLayout.NORTH);
		main.add(center, BorderLayout.CENTER);
		add(main);
	}
	public void loadImageData() {
		PreparedStatement ps =  con.prepareStatement("SELECT * FROM users WHERE users.UserId = ?");
		ResultSet rs = null;
		try {
			ps.setInt(1, user.id);
			rs = ps.executeQuery();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			if(rs.next()) {

				Blob blob;
				try {
					blob = rs.getBlob("ProfilePic");
					currBlob = blob;
					byte [] bytes = blob.getBytes(1, (int) blob.length());
				
					ByteArrayInputStream bais = new ByteArrayInputStream(bytes);		
					BufferedImage img = null;
					try {
						img = ImageIO.read(bais);
						profilePic.setIcon(new ImageIcon(img.getScaledInstance(200, 200, Image.SCALE_SMOOTH))); 
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("ERROR");
						e1.printStackTrace();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(Object e) {
		// TODO Auto-generated method stub
		if(e == updateBtn) {
			boolean updated = false;
			boolean first = true;
			String query = "UPDATE users SET ";
			if(!name.getText().equals("")) {
				if(first) {
					updated = true;
					query = query + "Name = '" + name.getText() + "'";
				}
			}
			//password
			if(!oldPasswordLbl.getText().equals("")) {
				if(oldPass.getText() == user.password && newPass.getText() == confPass.getText() ) {
					updated = true;
					if(first) {
						query = query + " AND password = '" + newPass.getText() + "'";
					}
					else {
						query = query + "password = '" + newPass.getText() + "'";
					}
				}
			}
			if(changed) {
				Blob blob;
		
				File fi = new File(getPath);
				byte[] fileContent;
				try {
					fileContent = Files.readAllBytes(fi.toPath());
					try {
						blob = new SerialBlob(fileContent);
						query = query + "ProfilePic = '" + blob + "'";
					} catch (SerialException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			if(updated) {
				query = query + " WHERE users.UserId = " + user.id;
				System.out.println(query);
				con.executeUpdate(query);
				
			}
			JOptionPane.showMessageDialog(null, "Successfully updated. Please login again");
			User.userLogout();	
			sm.setNewPage(StateManager.LOGIN);
			
		}
	}

	@Override
	public void clicked(Object e) {
		// TODO Auto-generated method stub
		if(e == profilePic) {
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "JPG & GIF Images", "jpg", "gif");
			jfc.showSaveDialog(null);
			jfc.setFileFilter(filter);
			File file = new File(jfc.getSelectedFile().getAbsolutePath());
			double kilobytes = file.length()/1024;
			double megabytes = kilobytes/1024;
			
			if(megabytes >= 1) {
				JOptionPane.showMessageDialog(null, "Image must be under 1 MB");
			}
			else {
				changed = true;
				profilePic.setIcon(new ImageIcon(new ImageIcon(jfc.getSelectedFile().getAbsolutePath()).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
				getPath = jfc.getSelectedFile().getAbsolutePath();
			}
		}
	}

}
