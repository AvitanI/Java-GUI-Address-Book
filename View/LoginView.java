package View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBox;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import Model.User;
import Utilities.Utilities;


@SuppressWarnings("serial")
public class LoginView extends MyWindowView 
					//implements creatingComponents
{
	//Instance variables
	
	private JPanel contentPane;
	private JPanel statusbarPanel;
	
	private JLabel UserNamelbl;
	private JLabel passwordlbl;
	private JLabel titlelbl;
	private JLabel mainImg;
	private JLabel hyperlinklbl;
	
	private JTextField userNametxt;

	private JPasswordField passwordtxt;
	
	private JButton loginbtn;
	
	private JCheckBox passwordCB;
	
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuHelp;
	private JMenuItem exit;
	private JMenuItem about;
	
	private JPopupMenu popupMenu;
	private JMenuItem minimize;
	
	//Constructor
	
	public LoginView()
	{
		super("Login");
		initUI();
	}
	
	//Class
	
	/**
	 * This class determine clock with the current time in the login window.
	 * @author idan avitan
	 *
	 */
	private class ClockLabel extends JLabel implements ActionListener 
	{
		//Constructor
		
		public ClockLabel()
		{
			super("" + new Date());
		    Timer t = new Timer(1000, this);
		    t.start();
		}

		  public void actionPerformed(ActionEvent ae) 
		  {
			  setText((new Date()).toString());
		  }
	}
	
	//Getters & Setters
	
	/**
	 * This method bring the user name from the text field.
	 * @return String
	 */
	public String getUserNametxt() 
	{
		return userNametxt.getText();
	}

	// Instance methods
	
	@Override
	protected void initUI()
	{
		generalInits(); //Initialize & Layouts
		
		createLabels(); //Labels
		
		craeteTextFields(); //Text fields
		
		createButtons(); //Buttons
		
		createMenus(); //Menus
		
		createCheckBoxPassword(); //Check box
	}
	
	@Override
	protected void generalInits()
	{
		super.setBounds(100, 100, 627, 370);
		
		super.initialize();
		
		this.contentPane = super.getMyContentPane();
		
		createStatusBarPanel(); //Create status bar with date and time
	}
	
	@Override
	protected void createLabels()
	{
		UserNamelbl = new JLabel("Username");
		UserNamelbl.setForeground(Color.BLACK);
		UserNamelbl.setBounds(29, 106, 60, 14);
		contentPane.add(UserNamelbl);
		
		passwordlbl = new JLabel("Password");
		passwordlbl.setBounds(29, 146, 60, 14);
		contentPane.add(passwordlbl);
		
		createPictureLabel();
		
		createHyperlink();
	}
	
	@Override
	protected void craeteTextFields() 
	{
		userNametxt = new JTextField();
		userNametxt.setBounds(99, 103, 161, 20);
		Utilities.createPromptsToTextFields("write your username here...", userNametxt);
		contentPane.add(userNametxt);
		userNametxt.setColumns(10);
		
		passwordtxt = new JPasswordField();
		passwordtxt.setBounds(99, 143, 161, 20);
		Utilities.createPromptsToTextFields("write your pssword here...", passwordtxt);
		contentPane.add(passwordtxt);
	}
	
	@Override
	protected void createButtons()
	{
		loginbtn = new JButton("Login");
		loginbtn.setActionCommand("Login");
		loginbtn.setBounds(137, 214, 89, 23);
		this.getRootPane().setDefaultButton(loginbtn);
		contentPane.add(loginbtn);
	}
	
	@Override
	protected void createMenus()
	{
		menuBar = new JMenuBar();               
		menuBar.setBounds(0, 0, 620, 21);
		contentPane.add(menuBar);
		
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		menuBar.add(Box.createHorizontalGlue());
		
		menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);
		
		about = new JMenuItem("About");
		about.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
				createAboutFrame(); //This about will display information that related with this frame
			}
		});
		menuHelp.add(about);
		
		exit = new JMenuItem("Exit");
		exit.setIcon(Utilities.exitIcon);
		exit.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				exit(contentPane);
			}
		});
		menuFile.add(exit);
		
		cratePopupMenu();
	}
	/**
	 * This method create popup menu in the window.
	 */
	private void cratePopupMenu() 
	{
		popupMenu = new JPopupMenu();
		
		minimize = new JMenuItem("Minimize");
		minimize.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setState(ICONIFIED);
			}
		});
	
		popupMenu.add(minimize);
		
		addMouseListener(new MouseAdapter()
		{
            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON3) 
                {
                	popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
	
	}
	/**
	 * This method create the CheckBox.
	 */
	private void createCheckBoxPassword()
	{
		passwordCB = new JCheckBox("show password");
		passwordCB.addItemListener(new ItemListener()
		{	
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				passwordCheckBox(e);
			}
		});
		passwordCB.setBounds(99, 174, 116, 23);
		contentPane.add(passwordCB);
	}
	/**
	 * This method get info from the fields & create new user object.
	 * @return User
	 */
	public User getUserInfoFromTheFields() 
	{
		@SuppressWarnings("deprecation")
		User user = new User(userNametxt.getText(), passwordtxt.getText());
		
		return user;
	}
	/**
	 * This method determine the color of labels. 
	 * @param answer
	 */
	public void markLabelsByValidation(boolean ans)
	{
		if(ans)
		{
			UserNamelbl.setForeground(Color.black);
			passwordlbl.setForeground(Color.black);
		}
		else
		{
			UserNamelbl.setForeground(Color.red);
			passwordlbl.setForeground(Color.red);
		}
	}
	/**
	 * This method goal is to set visible of chars in the password field.
	 * @param event
	 */
	private void passwordCheckBox(ItemEvent e)
	{
		int sel = e.getStateChange();
		
		if (sel == ItemEvent.SELECTED)
		{
			passwordtxt.setEchoChar((char) 0); //Show the password on textfield
		}
		else
		{
			passwordtxt.setEchoChar('*');;
		}
	}
	/**
	 * This method create about window.
	 */
	private void createAboutFrame() 
	{
		JFrame about;
		JLabel lbl = new JLabel();
		about = new JFrame("About");
		
		about.setSize(430, 100);
		about.setResizable(false);
		
		lbl.setText("This project goal is to represent address book moddel");
		
		
		about.getContentPane().add(lbl);
		
		about.setLocationRelativeTo(null);
		about.setVisible(true);
	}
	/**
	 * This method create status bar for the clock.
	 */
	private void createStatusBarPanel()
	{
		statusbarPanel = new JPanel();
		statusbarPanel.setBorder
			(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		statusbarPanel.setLayout(null);
		statusbarPanel.setBounds(0, 317, 620, 25);
		contentPane.add(statusbarPanel);
		
		ClockLabel c = new ClockLabel();
		statusbarPanel.add(c).setBounds(5, 5, 200, 14);
	}
	/**
	 * This method create website link.
	 */
	private void createHyperlink() 
	{
		hyperlinklbl = new JLabel("<html> Website : <a href=\"\">" + "http://localhost/phpmyadmin/" + "</a></html>");
		hyperlinklbl.setBounds(10, 269, 219, 37);
		contentPane.add(hyperlinklbl);
		
		hyperlinklbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		hyperlinklbl.addMouseListener(new MouseAdapter() 
		{
		   public void mouseClicked(MouseEvent e) 
		   {
		      if (e.getClickCount() > 0) 
		      {
		          if (Desktop.isDesktopSupported()) 
		          {
		                Desktop desktop = Desktop.getDesktop();
		                try 
		                {
		                    URI uri = new URI("http://localhost/phpmyadmin/");
		                    desktop.browse(uri);
		                } 
		                catch (IOException ex) 
		                {
		                    ex.printStackTrace();
		                } 
		                catch (URISyntaxException ex) 
		                {
		                    ex.printStackTrace();
		                }
		        }
		      }
		   }
		});
	}
	/**
	 * This method received a text message & display her to the screen.
	 * @param txt
	 */
	public void loginMessage(String txt)
	{
		JOptionPane.showMessageDialog(contentPane, 
				txt, 
				getTitle(), 
				JOptionPane.DEFAULT_OPTION, 
				Utilities.welcomeIcon);
	}
	/**
	 * This method create Pictures
	 */
	private void createPictureLabel() 
	{
		titlelbl = new JLabel("Welcome to Address Book model");
		titlelbl.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 24));
		titlelbl.setBounds(29, 32, 293, 58);
		contentPane.add(titlelbl);
		
		mainImg = new JLabel(Utilities.mainPic);
		mainImg.setBounds(332, 23, 262, 283);
		contentPane.add(mainImg);
	}
}
