package Model;

public class User 
{
	//Instance variables
	
	private String username;
	private String password;
	
	//Constructors
	
	public User()
	{
		this("","");
	}
	
	public User(String username, String password)
	{
		setUsername(username);
		setPassword(password);
	}
	
	//Getters & Setters
	
	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	//Instance methods
	
	public String toString()
	{
		return "Username: " + this.username + "\n" + 
				"Password: " + this.password + "\n";
	}
	
 	public boolean equals(Object obj)
	{
		if (obj instanceof User) 
		{
			User user = (User)obj;
			
			if (this.username.equalsIgnoreCase(user.username) &&
				this.password.equalsIgnoreCase(user.password))
			{
				return true;
			}
		}
		
		return false;
	}
}
