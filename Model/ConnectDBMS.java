package Model;
import java.sql.*;
import java.util.ArrayList;



public class ConnectDBMS
{
	//Class variables

	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  // JDBC driver name and database URL
	public static final String DB_URL = "jdbc:mysql://localhost:3306/database";

	//  Database credentials

	static final String USER = "root";
	static final String PASS = "261288";

	//Instance variables

	private Connection con = null;
	private Statement stmt = null;

	private ArrayList<PersonModel> list;

	private boolean connected;

	// Constructor

	public ConnectDBMS() //ArrayList<PersonInfo> list
	{
		setConnected(false);

		setArrayList();

		createConnection(); //Create connection with Database
	}

	//Instance methods

	private void setArrayList()
	{
		list = new ArrayList<PersonModel>();
	}

	public ArrayList<PersonModel> searchPerson(String idd)
	{
		try 
		{
			String sql = "SELECT * FROM personinformation WHERE id =" + idd + "";

			// Create a prepared statement

			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);


			String id = "";
			String pname = "";
			String address = "";
			String phone = "";
			String email = "";

			while (rs.next())
			{
				id = rs.getString("id");
				pname = rs.getString("name");
				address = rs.getString("address");
				phone = rs.getString("phone");
				email = rs.getString("email");

				//Create a PersonInfo object

				PersonModel person = new PersonModel( id, 
						pname, 
						address,
						phone, 
						email);

				list.add(person);

			}

			if(list.size() == 0)
			{
				System.out.println("no person");
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return list;
	}

	   public User searchUser(String username)
	   {
		   User user = null;
		   
		   try 
			{
				String sql = "SELECT * FROM users WHERE uName= '" + username + "' ";
				
				// Create a prepared statement
				
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				String userName = "";
				String password = "";
			
				while (rs.next())
				{
					userName = rs.getString("uName");
					password = rs.getString("uPassword");
			
					//Create User object
					
					user = new User(userName, password);
				}
				
				if(user == null)
				{
					System.out.println("no person");
				}
	
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			
			return user;
			
	   }

	public void insertPerson(PersonModel p)
	{
		try
		{
			PersonModel pi = new PersonModel(p.getId(), 
					p.getName(), 
					p.getAddress(), 
					p.getPhone() ,
					p.getEmail());

			String sql = "INSERT INTO personinformation(id, name, address, "
					+ "phone, email) VALUES (?,?,?,?,?) ";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, pi.getId());
			ps.setString(2, pi.getName());
			ps.setString(3, pi.getAddress());
			ps.setString(4, (pi.getPhone()));
			ps.setString(5, pi.getEmail());

			ps.executeUpdate();

			System.out.println("The person was added to the Database");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void createConnection() 
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");

			//Open a connection

			System.out.println("Connecting to a selected database...");

			con = DriverManager.getConnection(DB_URL,USER,""); //con = DriverManager.getConnection(DB_URL,USER,PASS);

			setConnected(true);

			System.out.println("Connected database successfully...");

		}
		catch(SQLException se)
		{
			setConnected(false);

			//Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e)
		{
			setConnected(false);

			//Handle errors for Class.forName
			e.printStackTrace();
		}
	}

	public ArrayList<PersonModel> bringAllData()
	{
		ArrayList<PersonModel> personInfo = new ArrayList<PersonModel>();

		try 
		{
			String sql = "SELECT * FROM personinformation";

			stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next())
			{
				String id, name, address, phone, email;

				id = rs.getString("id");
				name = rs.getString("name");
				address = rs.getString("address");
				phone = rs.getString("phone");
				email = rs.getString("email");

				PersonModel pi = new PersonModel(id, name, address, phone, email);

				personInfo.add(pi);
			}

			rs.close();
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}

		return personInfo;
	}

	public void removePerson(String id)
	{
		String sql = "DELETE FROM personinformation WHERE id="  + id + "";

		try 
		{
			stmt = con.createStatement();
			stmt.executeUpdate(sql);



			System.out.println("Person Removed");


		} 
		catch (Exception e)
		{

		}
	}

	public void updatePerson(PersonModel pi)
	{
		try
		{
			String sql = "UPDATE personinformation SET name = ?, address=? , "
					+ "phone=? , email=? where id=?";

			// Create a Prepared statement

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, pi.getName());
			ps.setString(2, pi.getAddress());
			ps.setString(3, pi.getPhone());
			ps.setString(4, pi.getEmail());
			ps.setString(5, pi.getId());

			ps.executeUpdate();

			System.out.println("this person was updated!");
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}

	public boolean isConnected() 
	{
		return connected;
	}

	public void setConnected(boolean connected)
	{
		this.connected = connected;
	}
	
	public void close()
	{
		try
		{
			if(stmt!=null)
			{

				con.close();}
		}
		catch(SQLException se)
		{

		}// do nothing
		try
		{
			if(con!=null)
				con.close();
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}//end finally try
		System.out.println("End of connection");
	}
}


