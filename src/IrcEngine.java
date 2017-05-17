import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JOptionPane;
//problem doesn't check if user hits cancel 
//doesn't check for valid nicname
//register should be boolean to check if register was susscessful
public class IrcEngine {
	private String nickName;
	private String host;
	private Socket socket;
	private PrintStream streamToService;
	private BufferedReader streamFromService;
	private String currentChannel = "kiwiirc-default";

	private static IrcEngine _ircEngine = null;
	
	/**************************************************************************
	 * Function: 
	 *  
	 * Description:
	 * 	->
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->
	 * 	->
	 **************************************************************************/
	private IrcEngine() throws IOException{
		//get the nick name, also checks for non numm alpha input
		this.nickNamePrompt();
		this.host = "irc.freenode.net";
		//instatiate the socket
		this.socket = new Socket();
		
		//connect to the interface
		this.connect();
		this.register();
		this.joinChannel(currentChannel);
	}
	/**************************************************************************
	 * Function: 
	 *  
	 * Description:
	 * 	->
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->
	 * 	->
	 **************************************************************************/
	public synchronized static IrcEngine getInstance(){
		if(_ircEngine == null){
			try {
				_ircEngine = new IrcEngine();
			} catch (IOException e) {
				System.out.println(
						"ERROR during instaliation of "
						+ "IRC ENgine: " + e);
				e.printStackTrace();
			}
		}
		return _ircEngine;
	}
	/**************************************************************************
	 * Function: 
	 *  
	 * Description:
	 * 	->
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->
	 * 	->
	 **************************************************************************/
	public String getCurrentChannel(){
		return this.currentChannel;
	}
	/**************************************************************************
	 * Function: 
	 *  
	 * Description:
	 * 	->
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->
	 * 	->
	 **************************************************************************/
	private void joinChannel(String channelName){
		//do not include hashtage
		String channelToJoin = channelName.replace("#","");
		//join the speicic chatroom
		this.getStreamtoService().println("JOIN #" + channelToJoin);
	}
	/**************************************************************************
	 * Function: 
	 *  
	 * Description:
	 * 	->
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->
	 * 	->
	 **************************************************************************/
	public String getNickName(){
		return this.nickName;
	}
	/**************************************************************************
	 * Function: 
	 *  
	 * Description:
	 * 	->
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->
	 * 	->
	 **************************************************************************/
	private void register(){
		System.out.printf("[+] Registering with NickName: %s%n", this.nickName);

		this.getStreamtoService().println("USER" + " " + this.nickName + " " + "localhost" + " " +this.host + " " + this.nickName);
		//getStreamToServer is used whenever i need to send something to the 
		//server so we can pass around the method that returns this printStream.
		this.getStreamtoService().println("NICK" + " " + nickName);
		
	}
	/**************************************************************************
	 * Function: 
	 *  
	 * Description:
	 * 	->
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->
	 * 	->
	 **************************************************************************/
	private void connect() throws IOException{
		//connect to the socket of the irc service
		this.socket.connect(new InetSocketAddress(this.host,6667),0);
		this.setStreamToService(this.socket);
		this.setStreamFromService(this.socket);
	}
	/**************************************************************************
	 * Function: 
	 *  
	 * Description:
	 * 	->
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->
	 * 	->
	 **************************************************************************/
	private void setStreamToService(Socket socket) throws IOException{
		this.streamToService = new PrintStream(socket.getOutputStream());
	}
	/**************************************************************************
	 * Function: 
	 *  
	 * Description:
	 * 	->
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->
	 * 	->
	 **************************************************************************/
	private void setStreamFromService(Socket socket) throws IOException{
		this.streamFromService = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
	}
	/**************************************************************************
	 * Function: 
	 *  
	 * Description:
	 * 	->
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->
	 * 	->
	 **************************************************************************/
	public BufferedReader getStreamFromService(){
		return this.streamFromService;
	}
	/**************************************************************************
	 * Function: 
	 *  
	 * Description:
	 * 	->
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->
	 * 	->
	 **************************************************************************/
	public PrintStream getStreamtoService(){
		 return this.streamToService;
	}
	/**************************************************************************
	 * Function: 
	 *  
	 * Description:
	 * 	->
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->
	 * 	->
	 **************************************************************************/
	public void nickNamePrompt(){
		//helper function to check for non empty input for nickname
		this.nickName = JOptionPane.showInputDialog("Please enter a valid nicname");
		while(nickName.length() == 0){
			this.nickName = JOptionPane.showInputDialog("Please enter a valid nicname");
		}
	}
	/**************************************************************************
	 * Function: 
	 *  
	 * Description:
	 * 	->
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->
	 * 	->
	 **************************************************************************/
	public static void main(String[] args){
		try{
		IrcEngine irc = new IrcEngine();
		}catch(IOException e){
			
		}
	}
}
