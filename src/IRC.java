/******************************************************************************
 * Author: Danny Ly
 * Date Created: 3/15/2015
 * Changelog : Please View github repository
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
 * 		->
 * -> Assumptions: 
 * 		->User enter a valid unique ID, that has not taken.
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JTextField;

import java.util.regex.Matcher;

public class IRC {
	private UserInterface UI;
	private IrcEngine IRCEngine;
	private PrintStream streamToService;
	private BufferedReader streamFromService;
	private Output bufferToTerminal; //buffer to send information to
	private JTextField inputMessageField;
	
	//Userinterface
	public IRC(){
		//set up the userInterface Object
		UI = new UserInterface();
		
		try{
			IRCEngine = new IrcEngine();
			streamToService = IRCEngine.getStreamtoService();
			streamFromService = IRCEngine.getStreamFromService();
			
		}catch (IOException e){
			//error
			System.out.println("ERROR during instaliation of "
					+ "IRC ENgine: " + e);
		}
		//set the irc object to the interface so it can speak with it
		
		bufferToTerminal = new Output(streamFromService, UI);
		
		inputMessageField = UI.getInputTextField();
		this.start();
		
	}
	
	public void start(){
		bufferToTerminal.start();
		inputMessageField.addActionListener(new ActionListener() {
			//Method for out strea for when data is incoming from the client UI
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				streamToService.println("PRIVMSG " + "#kiwiirc-default" + " :" + inputMessageField.getText());
				inputMessageField.setText("");
			}
		});
	}
	
	public String getChannelName(){
		return this.IRCEngine.getCurrentChannel();
	}
	//get the output stream,
	//push the things in output stream to the display
	public static void main(String[] args){
		IRC irc = new IRC();
		
		irc.start();
	}
}




class Output extends Thread{
	private BufferedReader br;
	UserInterface ui;
	public Output(){}
	public Output(BufferedReader s, UserInterface ui){
		this.br = s;
		this.ui = ui;
	}
	public void run(){
		String line;
		String pattern = ".*!";
		String usr ="";
		
		try {
			Pattern r = Pattern.compile(pattern);
			while((line = this.br.readLine()) != null){
				Matcher matcher = r.matcher(line);
				if(matcher.find()){
					usr = matcher.group();
					System.out.println(usr);
				}
				
				ui.sendMsgToTerminal(line);
				ui.sendMsgToTerminal(usr);
				System.out.println(line);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
class InputL extends Thread {
	
	Scanner scanner;
	String readString;
	String _channel_name;
	PrintStream printS;
	public InputL(PrintStream ps, String _channel_name){
		this.scanner = new Scanner(System.in);
		this.printS = ps;
		this._channel_name = _channel_name;
		
	}
	public void run(){
		
		readString = scanner.nextLine();
		while(this.readString != null){
			//given the input stream ,  then print to the stream
			System.out.printf("[+] printing  message to the stream %s: ", readString);
			printS.println("PRIVMSG " + " " + this._channel_name + " :" + readString);
			//read from the input stream
			//System.out.println(readString);
			if(scanner.hasNextLine()){
				readString = scanner.nextLine();
			}else{
				readString = null;
				
			}
		}		
	}
}
