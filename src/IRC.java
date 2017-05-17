/******************************************************************************
 * Author: Danny Ly
 * Date Created: 3/15/2015
 * Changlog : Please View github repository
 * 
 * 
 * Program Description:
 * 	-> This is the main driver for this program. This Program features a
 * Desktop client made  in java, which will connect you to an IRC service to
 * chat with others.
 * 	-> This program features, use of 2 threads one, to handle concurrent 
 * Incoming of text within a chat room to the client and another thread
 * to handle outgoing text/data to the web service
 * -> This program works by mainly having two gateways to the interface.
 * 
 * POSTCONDITIONS:
 * 		-> None
 * -> Assumptions: 
 * 		->User enter a valid unique ID, that has not taken.
 * POSTCONDIITON:
 * 	->initialization of a desktop client to use , you will be able
 * to chat through this desktop client.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintStream;
import javax.swing.JTextField;



public class IRC {
	private UserInterface UI;
	private IrcEngine IRCEngine;
	private PrintStream streamToService;
	private BufferedReader streamFromService;
	private ChannelThread bufferToTerminal; //buffer to send information to
	private JTextField inputMessageField;
	
	 /**************************************************************************
	 * Function: Default Constructor
	 *  
	 * Description:
	 * 	->
	 * ASSUMPTIONS:
	 * 	-> Singleton initialize only a single instance of this object
	 * PRECONDITIONS:
	 * 	-> Successful initialize of UserInterface object
	 * POSTCONDITIONS:
	 * 	-> UserInterface is initialize
	 * 	-> IRC Server socket is initialize
	 * 	-> communications between user input action listiseners and JTextArea
	 * for text appending(input).
	 **************************************************************************/
	public IRC(){
		//set up the userInterface Object
		
		UI = UserInterface.getInstance();
		IRCEngine = IrcEngine.getInstance();
		//IRCEngine = new IrcEngine();
		streamToService = IRCEngine.getStreamtoService();
		streamFromService = IRCEngine.getStreamFromService();

		//set the irc object to the interface so it can speak with it
		
		bufferToTerminal = new ChannelThread(streamFromService, UI);
		
		inputMessageField = UI.getInputTextField();
		
		this.init();
		UI.sendMsgToTerminal("[+] Connected ...");
		
	}
	/**************************************************************************
	 * Function: init
	 *  
	 * Description:
	 * 	-> initialize the IRC object(driver) by setting up listeners for keypress
	 * for the User interface, so we can have input to send to the service
	 * 	-> also clears the text after text input
	 * ASSUMPTIONS:
	 * 	-> BufferReader has been correctly initialized to accept input from service 
	 * PRECONDITIONS:
	 * 	-> proper initialization of Buffer reader
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	-> Calls run from the parent thread class to start concurrent reads of
	 * data from the webservice
	 * 	->
	 **************************************************************************/
	public void init(){
		UI.sendMsgToTerminal("[+] Connecting .....");
		inputMessageField.addActionListener(new ActionListener() {
			//Method for out strea for when data is incoming from the client UI
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				streamToService.println("PRIVMSG " + "#kiwiirc-default" +
				" :" + inputMessageField.getText());
				UI.sendMsgToTerminal(IRCEngine.getNickName() + " :" + inputMessageField.getText());
				inputMessageField.setText("");
			}
		});
		
		bufferToTerminal.start();
		
	}
	/**************************************************************************
	 * Function:  getChannel Name
	 *  
	 * Description:
	 * 	-> Returns the current channels name
	 * ASSUMPTIONS:
	 * 	-> IRC Engine Has been correctly initialized
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	-> Returns a String pertaining to the current Channel Name
	 * 	->
	 **************************************************************************/
	public String getChannelName(){
		return this.IRCEngine.getCurrentChannel();
	}
	
	public static void main(String[] args){
		IRC irc = new IRC();
	}
}

