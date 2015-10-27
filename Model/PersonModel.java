package Model;

public class PersonModel
{
	//Instance variables
	
	private String id;
	private String name;
	private String address;
	private String phone;
	private String email;
	
	private String[] personList; //This array is for the table display window
	
	//Class variables
	
	public static final int NUMBER_OF_ATTRIBUTES = 5;
	public static final int NUMBER_OF_SSN_DIGITS = 9;
	
	//Constructor
	
	//Default constructor
	
	public PersonModel()
	{
		this("000000000", 
			"unknown name", 
			"unknown address", 
			"0000000000",
			"unknown email");
	}
	
	public PersonModel(String id, 
					String name, 
					String address, 
					String phone, 
					String email)
	{
		setId(id);
		setName(name);
		setAddress(address);
		setPhone(phone);
		setEmail(email);
		
		setArrayPerson();
	}
	
	//Getters & Setters
	
	public void setArrayPerson()
	{
		this.personList = new String[]{id, name, address, phone, email};
	}
	
	public void setId(String id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}

	public String getAddress()
	{
		return address;
	}

	public String getPhone()
	{
		return phone;
	}

	public String getEmail() 
	{
		return email;
	}
	
	public String[] getArrayPerson()
	{
		 return personList;
	}
	
	//Instance methods
	
	public String toString()
	{
		return 	"ID: " 		+ id 		+ "\n" + 
				"Name: " 	+ name 		+ "\n" + 
				"Address: " + address	+ "\n" +
				"Phone: " 	+ phone		+ "\n" +
				"Email: "	+ email     + "\n" ;
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof PersonModel)
		{
			PersonModel pi = (PersonModel) obj;
			
			if (    this.id.equalsIgnoreCase(pi.id) )
					
			{
				return true;
			}
		}
		return false;
	}
	
}

/*
 * 				if (this.id.equalsIgnoreCase(pi.id) 		  &&
					this.name.equalsIgnoreCase(pi.name)		  &&
					this.address.equalsIgnoreCase(pi.address) &&
					this.phone.equalsIgnoreCase(pi.phone) 	  &&
					this.email.equalsIgnoreCase(pi.email))
 */
 

