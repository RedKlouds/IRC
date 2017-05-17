
/**************************************************************************
 * Danny Ly
 * RedKlouds
 * 
 * 
 * 			Helper class Channel Thread
 *  
 * Description:
 * 	-> This class is in charge of handling incoming traffic from the web
 * service and displaying it to the terminal in the user interface
 * 	-> The reason for this class being an inheritence of Thread is because
 * We need some way to keep concurrent asynchronous process of allowing 
 *program flow to continue while we have this module in the background on
 *its own thread 'waiting' for traffic to come in.
 * ASSUMPTIONS:
 * 	-> BufferedReader has been correctly initialized
 * 	-> UserInterface has been correctly initialized(Default Constructor)
 * PRECONDITIONS:
 * 	-> None
 * 	-> None
 * POSTCONDITIONS:
 * 	-> None
 * 	-> NOne
 **************************************************************************/

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ChannelThread extends Thread{
	//private class references
	private BufferedReader buffReader;
	private UserInterface userinterface;
	/**************************************************************************
	 * Function: 
	 *  	Default constructor
	 * Description:
	 * 	-> This method correctly assigns the bufferReader from the IRC engine
	 * output stream
	 * -> this method correctly assigns the user interface reference 
	 * ASSUMPTIONS:
	 * 	-> Correctly instantiated Objects for both BufferedReader and 
	 * User interface
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	-> None
	 * 	-> None
	 **************************************************************************/
	
	public ChannelThread(BufferedReader s, UserInterface userInterface){
		this.buffReader = s;
		this.userinterface = userInterface;
	}
	/**************************************************************************
	 * Function: 
	 *  	run/start
	 * Description:
	 * 	-> This function is inherited from Thread, reads line by line
	 * each of the buffered text, and runs two pattern regular expression searches
	 * on them, to parse the user name and the message from the read text
	 * ASSUMPTIONS:
	 * 	-> The string read so far only are supported for private messages 
	 * 	-> the format is as follows <username>! [TEXT] #<channelName>:<message>
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	-> Outputs the parsed username and message to the terminal window of 
	 * the User interface 
	 **************************************************************************/
	public void run(){
		//hold the current incoming data text
		String line;
		//Regex pattern to look for
		String usrNamePattern = ":(.*)!";
		//regex tom look for for message
		String messagePattern = "PRIVMSG #.+ :(.*)";
		
		try {
			Pattern usrPattern = Pattern.compile(usrNamePattern);
			Pattern msgPattern = Pattern.compile(messagePattern);
			while((line = this.buffReader.readLine()) != null){
				//initialize these strings here
				//need to reset the strings each time a line is read
				//to ensure that we do not repeat previous text
				String msg = "";
				String terminalChannelThread = "";
				String usr = "";
				if(line.contains("PRIVMSG")){
					//run pattern matching
					Matcher matcher_usr = usrPattern.matcher(line);
					Matcher matcher_msg = msgPattern.matcher(line);
					if(matcher_usr.find() && matcher_msg.find() ){
						usr = matcher_usr.group();
						msg = matcher_msg.group();
					
					}
					//create the given string to display on the terminal window
					//concat the usrname and the user message
					terminalChannelThread = usr + " : " + msg;
					//send the message to the terminal window 
					userinterface.sendMsgToTerminal(terminalChannelThread);
					
				}
				//display the log to the terminal window, DEBUGGING
				System.out.println(line);
			}
		} catch (IOException e) {
			//if we have IO exception, throw exeception
			e.printStackTrace();
		}
	}
}