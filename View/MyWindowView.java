package View;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Utilities.Utilities;

@SuppressWarnings("serial")
public abstract class MyWindowView extends JFrame
{
	//Variables 
	
	private JPanel myContentPane;
	
	//Constructor
	
	public MyWindowView(String title)
	{
		super(title);
		//initialize();
	}
	
	//Getters & Setters
	
	public JPanel getMyContentPane() 
	{
		return myContentPane;
	}

	//Methods
	
	public void initialize()
	{
		myContentPane = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setContentPane(myContentPane);
		myContentPane.setLayout(null);
		setLocationRelativeTo(null); //Open the frame in the center of the screen
		Utilities.setAppIcon(this);
	}
	/**
	 * This method received a text message & display her to the screen.
	 * @param txt
	 */
	public void showMessage(String txt)
	{
		Utilities.displayMessage(myContentPane, txt);
	}
	/**
	 * This method display a message to the screen.
	 * @param contentPane
	 * @param txt
	 * @return int answer
	 */
	public int displayRepliedMessages(String txt)
	{
		return Utilities.displayRepliedMessages(myContentPane, txt);
	}
	/**
	 * This method execute exit from the app.
	 * @param contentPane
	 */
	public void exit(JPanel contentPane)
	{
		Utilities.exit(contentPane);
	}
	/**
	 * This method clear the fields whether they full or empty.
	 */
	public void clearTheFields()
	{
		for (Component C : myContentPane.getComponents())
		{    
			
		    if (C instanceof JTextField)
		    {
		        ((JTextField) C).setText(""); //abstract superclass
		    }
		}
	}
	/**
	 * This method received action listeners from the controller & add it to button.
	 * @param al AS ActionListener
	 */
	public void addButtonListener(ActionListener al)
	{
		for (Component C : myContentPane.getComponents())
		{    
			
		    if (C instanceof JButton)
		    {
		        ((JButton) C).addActionListener(al); //abstract superclass
		    }
		}
	}
	
	//Abstract methods
	
	/**
	 * This method initialize the whole components in the window.
	 */
	protected abstract void initUI();
	/**
	 * This method setting the general definitions of the window.
	 */
	protected abstract void generalInits();
	/**
	 * This method create the labels.
	 */
	protected abstract void createLabels();
	/**
	 * This method create the text fields.
	 */
	protected abstract void craeteTextFields();
	/**
	 * This method create the buttons.
	 */
	protected abstract void createButtons();
	/**
	 * This method create the menu.
	 */
	protected abstract void createMenus();
	
}
