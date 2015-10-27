package Utilities;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.xswingx.PromptSupport;

/**
 * This class goal is to supply Utilities to the classes in this project.
 * @author idan avitan
 *
 */
public class Utilities
{
	//Class variables 

	//Icons

	public static ImageIcon exitIcon = new ImageIcon
			("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\Exit.png");

	public static ImageIcon welcomeIcon = new ImageIcon
			("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\welcome-icon.png");

	public static ImageIcon mainPic = new ImageIcon
			("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\LoginRed.jpg");

	public static ImageIcon mainScrIcon = new ImageIcon
			("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\LoginRed.jpg");

	public static ImageIcon tableIcon = new ImageIcon
			("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\table.png");

	public static ImageIcon personInfoPic = new ImageIcon
			("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\Addressbookmain.jpg");

	public static ImageIcon printIcon = new ImageIcon
			("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\printer.png");

	public static ImageIcon writeIcon = new ImageIcon
			("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\write.png");

	public static ImageIcon doneIcon = new ImageIcon
			("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\Done-icon.png");

	public static ImageIcon infoIcon = new ImageIcon
			("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\Info-icon.png");

	public static ImageIcon addIcon = new ImageIcon
			("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\Add-icon.png");

	public static ImageIcon removeIcon = new ImageIcon
			("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\blue_button_minus_subtract.png");

	public static ImageIcon syncIcon = new ImageIcon
			("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\Sync-Blue-icon.png");

	public static ImageIcon nextIcon = new ImageIcon
			("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\Next-Blue-icon.png");

	public static ImageIcon previousIcon = new ImageIcon
			("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\Back-Blue-icon.png");

	public static ImageIcon clearIcon = new ImageIcon
			("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\Clear-icon.png");

	public static ImageIcon searchIcon = new ImageIcon("C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\Search-icon.png");
	
	public static String appIcon= 
			"C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Icons\\books.png";
	
	
	//Warning messages
	
	public static final String ID_ERROR_MSG = "id error: id must include 9 digits without letters !";
	
	public static final String NAME_ERROR_MSG = "name error: make sure that the field not empty without numbers !";
	
	public static final String ADDRESS_ERROR_MSG = "address error: make sure that the field not empty !";
	
	public static final String PHONE_ERROR_MSG = "phone error: phone number must include 10 digits without letters !";
	
	public static final String EMAIL_ERROR_MSG = "email error: make sure that the field not empty & include '@' sign !";
	
	

	//Sound alerts

	public static String succsessSound = "C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Sounds\\tada.wav";

	public static String errorSound = "C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Sounds\\Windows XP Error.wav";

	public static String logoffSound = "C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Sounds\\Windows XP Logoff Sound.wav";

	public static String logonSound = "C:\\Users\\Idan's\\Documents\\MyWorkspace\\MVC_PROJECT\\Sounds\\Windows XP Logon Sound.wav";

	//Class methods
	
	/**
	 * This method execute exit from the app.
	 * @param contentPane
	 */
	public static void exit(JPanel contentPane)
	{
		int answer = displayRepliedMessages(contentPane, "Are you sure that you want to exit?");
		
		if (answer == JOptionPane.YES_OPTION) // In case that the answer is 'YES' for exit.
		{
			System.out.println("execute exit");
			playSound(logoffSound);
			System.exit(0);
		}

		//else: stay in the app
	}
	/**
	 * This method set icon to the app in specific frame.
	 * @param frame
	 */
	public static void setAppIcon(JFrame frame)
	{
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Utilities.appIcon));
	}
	/**
	 * This method create Prompts To Text Fields.
	 */
	public static void createPromptsToTextFields(String hint, JTextField txtField)
	{
		PromptSupport.setForeground(Color.GRAY, txtField);
		PromptSupport.setPrompt(hint, txtField);
	}
	/**
	 * This method play sound.
	 * @param url
	 */
	public static void playSound(String url) 
	{
		try 
		{
			// Open an audio input stream.
			
			File soundFile = new File(url);

			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			
			// Get a sound clip resource.
			
			Clip clip = AudioSystem.getClip();
			
			// Open audio clip and load samples from the audio input stream.
			
			clip.open(audioIn);
			clip.start();
		} 
		catch (UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (LineUnavailableException e) 
		{
			e.printStackTrace();
		}
	}
	/**
	 * This method checking if id is valid
	 * @param ssn
	 * @return true if param is valid
	 */
	public static boolean isSSNValid(String ssn)
	{  
		String expression = "^\\d{3}[- ]?\\d{2}[- ]?\\d{4}$";
		
		return matching(ssn, expression);
	}
	/**
	 * This method checking if name is valid
	 * @param name
	 * @return true if param is valid
	 */
	public static boolean isNameValid(String name)
	{
		String expression = "^[a-zA-Z\\s]+";
		
		return matching(name, expression);
	}
	/**
	 * This method checking if address is valid
	 * @param address
	 * @return true if param is valid
	 */
	public static boolean isAddressValid(String address)
	{
		if (!address.equals(""))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * This method checking if phone is valid
	 * @param phoneNumber
	 * @return true if param is valid
	 */
	public static boolean isPhoneNumberValid(String phoneNumber)
	{  
		String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
		
		return matching(phoneNumber, expression);
	}
	/**
	 * This method checking if email is valid
	 * @param email
	 * @return true if param is valid
	 */
	public static boolean isEmailValid(String email)
	{  
		String expression = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
							+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		return matching(email, expression);
	}
	/**
	 * This method do match between str to expression.
	 * @param str
	 * @param exp
	 * @return true for valid str
	 */
	private static boolean matching(String str, String exp)
	{
		boolean isValid = false; 
		CharSequence inputStr = str;  
		Pattern pattern = Pattern.compile(exp);  
		Matcher matcher = pattern.matcher(inputStr);  
		if(matcher.matches())
		{  
			isValid = true;  
		}  
		return isValid;  
	}
	/**
	 * This method display a message to the screen.
	 * @param contentPane
	 * @param txt
	 */
	public static void displayMessage(JPanel contentPane, String txt)
	{
		JOptionPane.showMessageDialog(contentPane,
				txt,
				"Attention",
				JOptionPane.WARNING_MESSAGE);
	}
	/**
	 * This method display a message to the screen.
	 * @param contentPane
	 * @param txt
	 * @return int answer
	 */
	public static int displayRepliedMessages(JPanel contentPane, String txt)
	{
		return JOptionPane.showConfirmDialog(contentPane, 
				txt, 
				"Remove Message", 
				JOptionPane.YES_NO_OPTION);

		 
	}

}
