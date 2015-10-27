package Controller;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Model.ConnectDBMS;
import Model.PersonModel;
import Model.User;
import Utilities.Utilities;
import View.DisplayTableView;
import View.PersonView;
import View.LoginView;
import View.SearchView;

public class PersonController
{
	//Instance variables
	
	private LoginView viewLog;
	private PersonView view;
	private DisplayTableView viewTable;
	private SearchView searchView;
	private ConnectDBMS modelDB;
	
	private ArrayList<PersonModel> dataList;
	
	private int pos; //position of person in the list
	
	//Constructor
	
	public PersonController(ConnectDBMS db, LoginView lv)
	{
		this.modelDB = db;
		
		initViewLog(lv);
		
		updateDataList();
	}
	
	
	//Class
	
	/**
	 * This class handling with the buttons in the address book screen. 
	 * @author idan avitan
	 *
	 */
	public class PersonHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{	
			String action = e.getActionCommand();
			
			if (action.equals("Add"))
			{
				add();
			}
			
			else if(action.equals("Remove"))
			{
				remove();
			}
			
			else if(action.equals("Update"))
			{
				update();
			}
			
			else if(action.equals("Next"))
			{
				next();
			}
			
			else if(action.equals("Previous"))
			{
				previous();
			}
			
			else if(action.equals("Clear"))
			{
				clear();
			}
			
			else if(action.equals("Main Screen"))      
			{
				view.dispose();
				initViewLog(new LoginView());
			}
			
			else if(action.equals("Display"))
			{
				displayToTable();
			}
			
			else if(action.equals("Search"))
			{
				openSearchingWindow();
			}
			
			else if(action.equals("Search Person"))
			{
				searchPerson();
			}
		}
	}
	/**
	 * This class handling with the buttons in the login screen. 
	 * @author idan avitan
	 *
	 */
	public class UserHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String str = e.getActionCommand();
			
			if(str.equals("Login"))
			{
				login();
			}
		}
		
	}
	
	//Instance methods
	
	/**
	 * This method initialize the login screen.
	 * @param lv
	 */
	private void initViewLog(LoginView lv) 
	{
		this.viewLog = lv;
		
		this.viewLog.setVisible(true);
		
		this.viewLog.addButtonListener(new UserHandler());
	}
	/**
	 * This method adding a new person to database.
	 */
	private void add() 
	{
		PersonModel p = view.getPersonInfoFromTheFields(); //Get the person info from the fields.
		
		if (!view.checkDetailsValidation(p))
		{
			Utilities.playSound(Utilities.errorSound);
			
			view.showMessage(view.getErrorList());
		}
		else if( checkExistingPerson(p) ) //Check if there is the same person in the list by the getPersonInfoFromTheFields() fun
		{
			//markLabelsToBlack();
			
			Utilities.playSound(Utilities.errorSound);
			
			view.showMessage("This person already existing");
			
			clear();
		}
		else
		{
			//markLabelsToBlack();
			
			modelDB.insertPerson(p);
			
			dataList.add(p);
		
			pos = dataList.size()-1; //Initialize the index of last person in the list.
			
			Utilities.playSound(Utilities.succsessSound);
			
			view.showMessage("This person was added successfully to the list");
			
			clear(); // After adding the details we need to clear the fields for the next action.
		}
	}
	/**
	 * This method checking if the person Exist in the database.
	 * @param person
	 * @return true if the person Exist.
	 * 			false if the person not Exist.
	 */
	private boolean checkExistingPerson(PersonModel p)
	{
		ArrayList<PersonModel> result = modelDB.searchPerson(p.getId());
		
		for (PersonModel pi : result)
		{
			if (pi.equals(p))
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * This method removing a person from the database.
	 */
	private void remove() 
	{
		PersonModel p = view.getPersonInfoFromTheFields();
		
		if (!view.checkDetailsValidation(p))
		{
			//markLabelsToRed(p);
			
			Utilities.playSound(Utilities.errorSound);
			
			view.showMessage(view.getErrorList());
			
			return;
		}
		if( !(checkExistingPerson(p)) ) 
		{
			//markLabelsToBlack();
			
			Utilities.playSound(Utilities.errorSound);
			
			view.showMessage("This person is not existing");
			
			clear();
			
			return;
		}
		
		Toolkit.getDefaultToolkit().beep();
		
		int answer = view.displayRepliedMessages("Are you sure that you want to remove this person?");
		
		if (answer == JOptionPane.YES_OPTION) // In case that the answer is 'YES' for exit.
		{
			//markLabelsToBlack();
			
			Utilities.playSound(Utilities.succsessSound);
			
			clear();
			
			modelDB.removePerson(p.getId());
			
			updateDataList();
		}
		
	}
	/**
	 * This method responsible to update the list from the database.
	 */
	private void updateDataList()
	{
		this.dataList = new ArrayList<PersonModel>();
		
		this.dataList = modelDB.bringAllData();
	}
	/**
	 * This method update a person in the database.
	 */
	private void update()
	{
		PersonModel p = view.getPersonInfoFromTheFields();
		
		if (!view.checkDetailsValidation(p))
		{
			//markLabelsToRed(p);
			
			Utilities.playSound(Utilities.errorSound);
			
			view.showMessage(view.getErrorList());	
		}
		else if(!checkExistingPerson(p))
		{
			//markLabelsToBlack();
			
			Utilities.playSound(Utilities.errorSound);
			
			view.showMessage("This person not existing in the system");
		}
		else
		{
			//markLabelsToBlack();
			
			modelDB.updatePerson(p);
			
			clear();
			
			Utilities.playSound(Utilities.succsessSound);
			
			view.showMessage("The person was updated !");
			
			updateDataList();
		}
	}
	/**
	 * This method checking if there is person next in the list & show him on the screen. 
	 */
	private void next()
	{
		if (!dataList.isEmpty() && (pos+1) <= dataList.size()-1)
		{
				pos++;
				view.setTextFields(getPersonByPosition(pos)); // Set the next person details to the the fields
		}
		else
		{
			Toolkit.getDefaultToolkit().beep();
			
			view.showMessage("There is no person after this");
			
		}
	}
	/**
	 * This method checking if there is person previous in the list & show him on the screen. 
	 */
	private void previous()
	{
		if (!dataList.isEmpty() && (pos-1) >= 0)
		{
			pos--;
			view.setTextFields(getPersonByPosition(pos)); // Set the previous person details to the the fields.
		}
		else
		{
			Toolkit.getDefaultToolkit().beep();
			
			view.showMessage("There is no person before this");
		}
	}
	/**
	 * This method bring specific person from the list by position.
	 * @param position
	 * @return person
	 */
	private PersonModel getPersonByPosition(int position) 
	{
		PersonModel pi = this.dataList.get(position); // position can be 'next' or 'previous' person in the list.
		
		return pi;
	}
	/**
	 * This method clear the fields.
	 */
	private void clear()
	{
		view.clearTheFields();
	}
	/**
	 * This method opening Searching Window.
	 */
	private void openSearchingWindow() 
	{
		searchView = new SearchView();
		searchView.setVisible(true);
		
		searchView.addButtonListener(new PersonHandler());
	}
	/**
	 * This method searching person in the database. 
	 */
	private void searchPerson()
	{
		if(searchView.checkDetailsValidation())
		{
			ArrayList<PersonModel> pm = modelDB.searchPerson(searchView.getIdTxt());
			
			if (pm.size() != 0)
			{
				view.setTextFields(pm.get(0));
				searchView.dispose();
			}
			else
			{
				searchView.showMessages("There is no person");
			}
		}
	}
	/**
	 * This method checking if the login is valid or not.
	 */
	private void login() 
	{
		if (  checkValidation() )
		{
			viewLog.markLabelsByValidation(true);
			
			//Toolkit.getDefaultToolkit().beep();
			
			Utilities.playSound(Utilities.logonSound);
			
			viewLog.loginMessage("Welcome " + viewLog.getUserNametxt());
			
			view = new PersonView();
			view.addButtonListener(new PersonHandler());
			view.setVisible(true);
			viewLog.dispose();
		}
		else 
		{
			viewLog.markLabelsByValidation(false); // In case that the user name or password are incorrect the fun' will mark them in red color.
			
			Utilities.playSound(Utilities.errorSound);
			
			viewLog.showMessage("Please check your username and password again and "
				+ "than enter 'Login' ");
			
			viewLog.clearTheFields();
		}
	}
	/**
	 * This method checking if the specific details exist in the database.
	 * @return
	 */
	private boolean checkValidation()
	{
		User user = viewLog.getUserInfoFromTheFields(); //The user that request to login
		
		User result = modelDB.searchUser(user.getUsername()); //The result from Searching username + password in database
		
		if(user.equals(result))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * This method open a window with table that show all persons in the database.
	 */
	private void displayToTable()
	{
		if (!dataList.isEmpty())
		{
			viewTable = new DisplayTableView(dataList);
			viewTable.setVisible(true);
		}
		else
		{
			Utilities.playSound(Utilities.errorSound);
			
			view.showMessage("The list is empty");
		}
	}

}
