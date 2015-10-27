package View;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import Utilities.Utilities;

@SuppressWarnings("serial")
public class SearchView extends MyWindowView 
{
	//Instance variables 

	private JPanel contentPane;

	private JLabel idLbl;
	private JLabel lblPleaseEnterId;

	private JTextField idTxt;

	private JButton searchBtn;

	//Constructor

	public SearchView() 
	{
		super("Search");
		initUI();
	}

	//Getters & Setters 
	
	/**
	 * This method bring the Id from the text field.
	 * @return String
	 */
	public String getIdTxt() 
	{
		return idTxt.getText();
	}
	
	//Instance methods
	
	/**
	 * This method returns false if id from the field is invalid.
	 * @return boolean
	 */
	public boolean checkDetailsValidation()
	{
		boolean ans = true;

		if (!Utilities.isSSNValid(idTxt.getText())) 
		{
			ans = false;

			showMessages(Utilities.ID_ERROR_MSG);
		}
		return ans;
	}
	/**
	 * This method received a text message & display her to the screen.
	 * @param txt
	 */
	public void showMessages(String txt)
	{
		Utilities.displayMessage(contentPane, txt);
	}

	@Override
	protected void initUI() 
	{
		generalInits(); //Initialize & Layouts
		
		createLabels(); //Labels
		
		craeteTextFields(); //Text fields
		
		createButtons(); //Buttons
		
		createMenus(); //Menus
	}

	@Override
	protected void generalInits()
	{
		setBounds(100, 100, 363, 126);
		super.initialize();
		this.contentPane = super.getMyContentPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@Override
	protected void createLabels()
	{
		idLbl = new JLabel("ID:");
		idLbl.setBounds(40, 40, 27, 14);
		contentPane.add(idLbl);
		
		lblPleaseEnterId = new JLabel("Please enter id:");
		lblPleaseEnterId.setBounds(129, 12, 134, 14);
		contentPane.add(lblPleaseEnterId);
	}

	@Override
	protected void craeteTextFields() 
	{
		idTxt = new JTextField();
		idTxt.setBounds(77, 37, 165, 20);
		contentPane.add(idTxt);
		idTxt.setColumns(10);
	}

	@Override
	protected void createButtons()
	{
		searchBtn = new JButton("Search");
		searchBtn.setActionCommand("Search Person");
		searchBtn.setBounds(258, 36, 89, 23);
		contentPane.add(searchBtn);
	}

	@Override
	protected void createMenus() 
	{
		
	}

}
