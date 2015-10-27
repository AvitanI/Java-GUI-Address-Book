package Run;
import java.awt.EventQueue;

import Controller.PersonController;
import Model.ConnectDBMS;
import View.LoginView;


public class RunProgram 
{
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try
				{
					LoginView viewLog = new LoginView();
			        
			    	ConnectDBMS modelDB = new ConnectDBMS();
			        
			        @SuppressWarnings("unused")
					PersonController controller = new PersonController(modelDB , viewLog);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
