package View;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JToolBar;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.SwingConstants;

import Model.PersonModel;
import Utilities.Utilities;


@SuppressWarnings("serial")
public class DisplayTableView extends JFrame 
						  //implements creatingComponents
{
	//Instance variables 
	
	private JPanel contentPane;
	private JPanel statusbarPane;
	
	private JScrollPane scrollPane;
	
	private JTable table;
	
	private String[] columns; 
	private Object[][] data; // The whole person details
	
	private JLabel statusbarlbl;
	private JLabel updatelbl;
	
	private JPopupMenu popupMenu;
	private JMenuItem maximize;
	private JMenuItem exit;
	
	private int numberOfPeople;
	
	private JToolBar toolBar;
	
	private JButton printbtn;
	private JButton toFilebtn;
	private JButton exitbtn;
	
	//Constructor
	
	public DisplayTableView(ArrayList<PersonModel> list) 
	{
		initUI(list);
	}
	
	//Instance methods
	
	private void initUI(ArrayList<PersonModel> list)
	{
		setNumberOfPeople(list.size());
		
		generalInits(); //Initialize and Layouts
		
		createLabels(); //Labels
		
		createMenus(); //Menus
		
		createButtons(); // Buttons
		
		setTableDetails(list); //Insert to the the table person info.
		
		createTable(); //Table
	}
	/**
	 * This method received list of people & insert them to the table.
	 * @param list of people
	 */
	private void setTableDetails(ArrayList<PersonModel> list)
	{
		columns = new String[]{"ID", "Name", "Address", "Phone", "Email"}; //columns names in the table
		
		data = new Object[list.size()][columns.length]; //Each row in the data array represent person information
		
		String[] s;
		
		for (int i = 0; i < data.length; i++)
		{
			s = list.get(i).getArrayPerson();
			
			for (int j = 0; j < columns.length; j++)
			{
				data[i][j] = s[j];
			}
		}
	}
	/**
	 * This method create popup menu in the window.
	 */
	private void cratePopupMenu() 
	{
		popupMenu = new JPopupMenu();
		
		maximize = new JMenuItem("Maximize");
		maximize.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setExtendedState(JFrame.MAXIMIZED_BOTH);
			}
		});
	
		popupMenu.add(maximize);
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				Utilities.exit(contentPane);
			}
		});
		
		popupMenu.add(exit);
		
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
	 * This method write Table To File
	 */
	private void writeTableToFile()
	{
		try 
		  {
			File file = new File
					("C:\\Users\\idan avitan\\Desktop", "Data.txt"); //C:\\Users\\idan avitan\\my_workspace\\Java_Swing_Address_Book\\src\\Files
		     
		      file.createNewFile();  // creates the file
			
			  BufferedWriter bfw = new BufferedWriter(new FileWriter(file));
			  for(int i = 0 ; i < table.getColumnCount() ; i++)
			  {
			    bfw.write(table.getColumnName(i));
			    bfw.write("\t");
			  }

			  for (int i = 0 ; i < table.getRowCount(); i++)
			  {
			    bfw.newLine();
			    for(int j = 0 ; j < table.getColumnCount();j++)
			    {
			      bfw.write((String)(table.getValueAt(i,j)));
			      bfw.write("\t");;
			    }
			  }
			  bfw.close();
			  
			  JOptionPane.showMessageDialog(contentPane, 
					  						"Done !", 
					  						"Update", 
					  						JOptionPane.OK_OPTION, 
					  						Utilities.doneIcon);
		  } 
		  catch (Exception e)
		  {
			// TODO: handle exception
		  }
	}
	/**
	 * This method print the table.
	 */
	private void print()
	{
		try 
		{
			table.print();
		} 
		catch 
		(PrinterException e1) 
		{
			e1.printStackTrace();
		}
	}
	/**
	 * This method setting the general definitions of the window.
	 */
	public void generalInits()
	{
		setTitle("Person details table");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 841, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null); //Open the frame in the center of the screen
		
		Utilities.setAppIcon(this);
		
		createStatusBarPanel();
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}
	/**
	 * This method create status bar for the number of people.
	 */
	private void createStatusBarPanel() 
	{
		statusbarPane = new JPanel();
		statusbarPane.setForeground(Color.WHITE);
		statusbarPane.setBorder(new LineBorder(new Color(192, 192, 192), 2));
		contentPane.add(statusbarPane, BorderLayout.SOUTH);
		statusbarPane.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
	}
	/**
	 * This method create the menu.
	 */
	public void createMenus()
	{
		cratePopupMenu();
		
		toolBar = new JToolBar(JToolBar.VERTICAL);
		contentPane.add(toolBar, BorderLayout.WEST);
	}
	/**
	 * This method create the labels.
	 */
	public void createLabels()
	{
		statusbarlbl = new JLabel("statusbar:");
		statusbarPane.add(statusbarlbl);
		
		updatelbl = new JLabel();
		updatelbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		updatelbl.setText(numberOfPeople + " records so far");
		statusbarPane.add(updatelbl);
	}
	/**
	 * This method create the buttons.
	 */
	public void createButtons()
	{
		printbtn = new JButton(Utilities.printIcon);
		printbtn.setToolTipText("print the table");
		printbtn.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				print();
			}
		});
		toolBar.add(printbtn);
		
		toFilebtn = new JButton(Utilities.writeIcon);
		toFilebtn.setToolTipText("write the table to text file");
		toFilebtn.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				writeTableToFile();
			}
		});
		toolBar.add(toFilebtn);
		
		exitbtn = new JButton("");
		exitbtn.setVerticalAlignment(SwingConstants.BOTTOM);
		exitbtn.setToolTipText("Back");
		exitbtn.setIcon(Utilities.exitIcon);
		exitbtn.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dispose(); //back to the address book screen.
			}
		});
		toolBar.add(exitbtn);
	}
	/**
	 * This method create the Table.
	 */
	private void createTable()
	{
		table = new JTable(data, columns);
		scrollPane.setViewportView(table);
		
		table.setEnabled(false);
		
		table.setAutoCreateRowSorter(true);
	}
	/**
	 * This method set the Number Of People.
	 * @param numberOfPeople
	 */
	public void setNumberOfPeople(int numberOfPeople)
	{
		this.numberOfPeople = numberOfPeople;
	}

}
