import java.sql.Blob;

public class User {
	private static User user;
	String username;
	String password;
	String name;
	Blob blob;
	int id;
	public User(int id ,String name,String username, String password, Blob blob) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.id = id;
		this.blob = blob;
	}
	public synchronized static User getUser(int id,String name,String username, String password, Blob blob) {
		if(user == null) user = new User(id,name, username,password,blob);
		return user;
	}
	public synchronized static User getUser() {
		return user;
	}
	public synchronized static void userLogout() {
		if(user == null) return;
		user = null;
	}
}
