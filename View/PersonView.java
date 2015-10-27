package View;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JButton;

import net.java.balloontip.BalloonTip;
import Model.PersonModel;
import Utilities.Utilities;

@SuppressWarnings("serial")
public class PersonView extends MyWindowView
{
	//Instance variables

	private JPanel contentPane;
	
	private JLabel[] labelsArr;
	private JLabel customerImg;
	
	private JTextField[] textFieldsArr;

	private JButton[] buttonArr;
	private JButton displaybtn;
	private JButton mainScreen;
	private JButton searchBtn;
	
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem exit;

	private String errorList;
	private String choosedCity;
	
	private JComboBox<String> cityComboBox;
	
	private String[] cityArr;
	
	//Class variables
	
	private static final int NUMBER_OF_LABELS = 6;
	private static final int NUMBER_OF_TXT_FIELDS = 5;
	private static final int HEIGHT = 40;
	

	private static final String[] HINTS = {"Write your id here...", 
											"Write your name here...", 
											"Write your address here...", 
											"Write your phone here...", 
											"Example@gmail.com"};
	
	private static final String[] LABEL_NAMES = {"ID", "Name", "Address", "City", "Phone", "Email"};
	
	private static final String[] BUTTONS_NAMES = {"Add", "Remove", "Update", "Next", "Previous", "Clear"};
	
	private static final String[] TOOL_TIPS_BUTTONS = {"Add all the details to the list", 
														"Remove person details from the list", 
														"Update person details in the list", 
														"Next person detail", 
														"Previous person detail", 
														"Clear all fields"};
	
	//Constructor


	public PersonView()
	{
		super("Address Book");
		initUI();
	}

	//Getters & Setters
	
	/**
	 * This method return the error list.
	 * @return error list
	 */
	public String getErrorList() 
	{
		return errorList;
	}

	//Instance methods
	
	@Override
	protected void initUI() 
	{
		generalInits(); //Initialize & set Layout
		
		createLabels(); //Labels
		
		craeteTextFields(); //Text fields
		
		createButtons(); //Buttons
		
		createCheckBox(); //Check Box
		
		createMenus(); //Menu
	}
	
	@Override
	protected void generalInits()
	{
		super.setBounds(100, 100, 637, 328);
		super.initialize();
		this.contentPane = super.getMyContentPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//createStatusBarPanel(); //Display if database connected or not
	}
	
	@Override
	protected void createLabels()
	{
		int y = HEIGHT; //Height start from 40 px
		
		labelsArr = new JLabel[NUMBER_OF_LABELS];
		
		for (int i = 0; i < labelsArr.length; i++) 
		{
			labelsArr[i] = new JLabel(LABEL_NAMES[i]);
			labelsArr[i].setBounds(40, y, 75, 23);
			contentPane.add(labelsArr[i]);
			y += 30;
		}
		
		createPictureLabel();
		
		createInfoIconsLabels();
	}
	
	@Override
	protected void craeteTextFields() 
	{
		textFieldsArr = new JTextField[NUMBER_OF_TXT_FIELDS];
		
		int y = HEIGHT; //Height start from 40 px
		
		for (int i = 0; i < textFieldsArr.length; i++) 
		{
			if (i == 3)
			{
				y += 30;
			}
			
			textFieldsArr[i] = new JTextField();
			textFieldsArr[i].setBounds(120, y, 140, 23); 
			Utilities.createPromptsToTextFields(HINTS[i], textFieldsArr[i]);
			contentPane.add(textFieldsArr[i]);
			textFieldsArr[i].setColumns(10);
			y += 30;
		}
	}
	
	@Override
	protected void createButtons()
	{
//		ImageIcon[] iconsArr = {Utilities.addIcon, 
//								Utilities.removeIcon, 
//								Utilities.syncIcon, 
//								Utilities.nextIcon, 
//								Utilities.previousIcon,
//								Utilities.clearIcon};
		
		int y = HEIGHT;
		
		buttonArr = new JButton[BUTTONS_NAMES.length];
		
		for (int i = 0; i < buttonArr.length; i++) 
		{
			buttonArr[i] = new JButton(BUTTONS_NAMES[i]);
			buttonArr[i].setActionCommand(BUTTONS_NAMES[i]);
			buttonArr[i].setBounds(270, y, 118, 23);
			//buttonArr[i].setIcon(iconsArr[i]);
			buttonArr[i].setToolTipText(TOOL_TIPS_BUTTONS[i]);
			contentPane.add(buttonArr[i]);
			y+=30;
		}
		
		displaybtn = new JButton(Utilities.tableIcon);  
		displaybtn.setActionCommand("Display");
		displaybtn.setBounds(343, 260, 46, 41);
		displaybtn.setToolTipText("Showing all pepole details on the screen");
		contentPane.add(displaybtn);
		
		mainScreen = new JButton(Utilities.mainScrIcon);
		mainScreen.setActionCommand("Main Screen");
		mainScreen.setToolTipText("Home Screen");
		mainScreen.setBounds(0, 260, 46, 41);
		contentPane.add(mainScreen);
		
		searchBtn = new JButton(Utilities.searchIcon);
		searchBtn.setActionCommand("Search");
		mainScreen.setToolTipText("Search");
		searchBtn.setBounds(297, 260, 46, 40);
		contentPane.add(searchBtn);
	}
	
	@Override
	protected void createMenus()
	{
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 631, 21);
		contentPane.add(menuBar);
		
		file = new JMenu("File");
		menuBar.add(file);
		
		exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_E);
		exit.setIcon(Utilities.exitIcon);
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				exit(contentPane);
			}
		});
		file.add(exit);
	}
	/**
	 * This method create the CheckBox.
	 */
	private void createCheckBox()
	{
		cityArr = loadListOfCities();
		
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>(cityArr);
		
		cityComboBox = new JComboBox<String>(comboModel);
		cityComboBox.setBounds(120, 130, 140, 20);
		cityComboBox.setEditable(false);
		choosedCity = "";
		cityComboBox.addItemListener(new ItemListener()
		{	
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					if(!e.getItem().toString().equals("SELECT CITY"))
					{
						choosedCity = ", " + e.getItem().toString();
					}
					else
					{
						choosedCity = "";
					}
				}
			}
		});
		contentPane.add(cityComboBox);
	}
	/**
	 * This method get info from the fields & create new PersonModel object.
	 * @return PersonModel
	 */
	public PersonModel getPersonInfoFromTheFields()
	{
		String id, name, address, phone, email; 

		id = textFieldsArr[0].getText(); // id field
		name = textFieldsArr[1].getText(); // name field
		address = textFieldsArr[2].getText() + choosedCity; // address field
		phone = textFieldsArr[3].getText(); // phone field
		email = textFieldsArr[4].getText(); // email field
		
		return new PersonModel(id, name, address, phone, email);
	}
	/**
	 * This method received PersonModel & set his info to the fields
	 * @param PersonModel pi
	 */
	public void setTextFields(PersonModel pm)
	{
		textFieldsArr[0].setText(pm.getId()); // id field
		textFieldsArr[1].setText(pm.getName()); // name field
		textFieldsArr[2].setText(pm.getAddress()); // address field
		textFieldsArr[3].setText(pm.getPhone()); // phone field
		textFieldsArr[4].setText(pm.getEmail()); // email field
	}
	/**
	 * This method returns false if one or more from the fields is invalid.
	 * @return boolean
	 */
	public boolean checkDetailsValidation(PersonModel pm)
	{
		errorList = "";

		boolean ans = true;

		if (!Utilities.isSSNValid(pm.getId())) 
		{
			ans = false;

			errorList += Utilities.ID_ERROR_MSG + "\n";
		}
		if(!Utilities.isNameValid(pm.getName()))
		{
			ans = false;

			errorList += Utilities.NAME_ERROR_MSG + "\n";
		}
		if(!Utilities.isAddressValid(pm.getAddress()))
		{
			ans = false;

			errorList += Utilities.ADDRESS_ERROR_MSG + "\n";
		}
		if(!Utilities.isPhoneNumberValid(pm.getPhone()))
		{
			ans = false;

			errorList += Utilities.PHONE_ERROR_MSG + "\n";
		}
		if(!Utilities.isEmailValid(pm.getEmail()))
		{
			ans = false;

			errorList += Utilities.EMAIL_ERROR_MSG + "\n";
		}
		
		
		return ans;
	}
	/**
	 * This method create BlloonTip for a specific label.
	 * @param e
	 * @param txt
	 * @param lbl
	 */
	private void createBlloonTip(MouseEvent e, String txt, JLabel lbl) 
	{
		if (e.getClickCount() == 1)
		{
			BalloonTip t = new BalloonTip(lbl, txt);
			t.setAttachedComponent(lbl);
			
			t.setCloseButton(BalloonTip.getDefaultCloseButton(), false);
					  
			//ToolTipUtils.balloonToToolTip(t, 500, 500);
		}
	}
	/**
	 * This method load List Of all Cities.
	 * @return
	 */
	private String[] loadListOfCities()
	{
		ArrayList<String> list = new ArrayList<String>();
		  
		try (BufferedReader br = new BufferedReader
				(new FileReader("C:\\ Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Files\\cities.txt")))
		{
			String line;
			while ((line = br.readLine()) != null)
			{
				list.add(line.toUpperCase());
			}
		}
		catch (Exception e)
		{
			System.out.println("Unable to load cities list");
		}
		
		String[] s = list.toArray(new String[list.size()]);
		
		return s;
		  
	}
	/**
	 * This method create InfoIcons
	 */
	private void createInfoIconsLabels() 
	{
		final JLabel infoId;
		final JLabel infoPhone;
		
		infoId = new JLabel(Utilities.infoIcon); 
		infoId.setBounds(9, 40, 21, 21);
		infoId.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				createBlloonTip(e, "id include only numbers (9 digits)", infoId);
			}
		});
		contentPane.add(infoId);
		
		infoPhone = new JLabel(Utilities.infoIcon);
		infoPhone.setBounds(9, 163, 21, 17);
		infoPhone.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e) 
			{
				createBlloonTip(e, "phone include only numbers (10 digits)", infoPhone);
			};
		});
		contentPane.add(infoPhone);
	}
	/**
	 * This method create Pictures
	 */
	private void createPictureLabel() 
	{
		customerImg = new JLabel(Utilities.personInfoPic);
		customerImg.setBounds(387, 21, 234, 237);
		contentPane.add(customerImg);
	}
}
