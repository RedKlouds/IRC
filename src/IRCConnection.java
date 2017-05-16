import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JTextArea;

/*makes a Connection Object with the given hostName and port number
 * Creates a socket to connect to that port 
 * Registers a User nickname 
 *Connects to a registered channel
 * 
 *~~working output console input to channel
 * 
 */ //join #channelName(Genkiklaus)
 
public class IRCConnection{

	
	//ele 34
	//trash 30
	//heating 10
	//water 54


	//class variables storing our data
	private String hostName;
	private PrintStream outputToChannel;
	private int portNumber;
	private BufferedReader inputFromChannel;
	
	private Channel currentChannel;
	
	private InputThread inputFromServerThread;
	
	private JTextArea jTextIncomingMsg;
	String nickname;
	/*
	InputThread inputThread = new InputThread(testConnect, cha);
	
	OutputThread outputThread = new OutputThread(cha);
	
	
	*/
	
	

	public IRCConnection() throws UnknownHostException, IOException{
		//first constructor given a host name , we use a default port number of 6667
		//recall our second constructor with new given parameters
		this("irc.freenode.net", 6667, "kiwiirc-default");//default channel
		
		
	}
	public IRCConnection(String channel) throws UnknownHostException, IOException{
		this("irc.freenode.net", 6667, channel);

		//this.inputFromServerThread = new InputThread(inputStream, sendMessageTo)
	}
	
	
	
	public IRCConnection(String host, int port, String channel) throws UnknownHostException, IOException{
		/*
		 * second constructor given name and the port number we call out methods connect
		 * and register 
		 */
		setHostName(host);

		
		setPortNumber(port);
		
		connect();
		register();
		setCurrentChannel(new Channel(channel, outputToChannel));
		//inputFromServerThread = new InputThread(this, sendMessageTo)
	}
	public String getNick(){
		return this.nickname;
	}
	public Channel getCurrChan(){
		return this.currentChannel;
	}
	public PrintStream getOutputChannel(){
		return this.outputToChannel;
	}
	
	public String getChannelName(){
		return this.currentChannel.getChanName();
	}
	
	public void setIncommingMsg(JTextArea JtextArea){
		this.jTextIncomingMsg = JtextArea;
	}
	
	public BufferedReader getInputFromChannel(){
		return this.inputFromChannel;
	}
	
	
	public void setPortNumber(int newPortNum){
		this.portNumber = newPortNum;
	}
	
	
	public void setHostName(String newHostName){
		this.hostName = newHostName;
	}
	
	
	public void setCurrentChannel(Channel channel){
		this.currentChannel = channel;
	}
	
	
	private void connect() throws UnknownHostException, IOException{
		/*
		 * Makes a socket given the host and the port number, this method throws UnkownHostexecpetion and IE exeception if host is not
		 * registered or avaliable
		 * 
		 *Because we have successfully/ if we have: we are safe to instatiate a output stream to the socket because one exsist and an input stream from the socket/server
		 *
		 */
		Socket socket = new Socket(hostName, portNumber);
		
		outputToChannel = new PrintStream(socket.getOutputStream());//outgoing text from channel
		
		inputFromChannel = new BufferedReader(new InputStreamReader(socket.getInputStream())); //incoming text from channel

	}
	
	
	public Channel getChannel(String connectToChannel_Name){
		/*
		 * creates an instance of Channel class (see channel class for Doc)
		 * given the name of the channel, we will:
		 * first call the method in the IRC server /PRIVMSG #channelName : message
		 * then relay our msg to this particular channel
		 */
		return ( new Channel(connectToChannel_Name, outputToChannel));
	}
	
	

	private void register(){
		/*when a client connects to an IRC server it must identify itself, the at minimum the client must choose a nicename , which will uniqely identify
		 * the clients messages in the channel and give it to the server
		 * , the client must also tell the server the locationn from which it is connecting, this 
		 * 
		 */
		
		
		nickname = "hellodogs"; //creates a default nickname to be identified by
		String localHost = "localhost";//have a local computer name to be identified by
		
		//relay this information to the asking server 
		
		//pulled from wikihttp://en.wikipedia.org/wiki/List_of_Internet_Relay_Chat_commands#USER
		outputToChannel.println("USER" + " " + nickname + " " + localHost + " " + hostName + " " + nickname);
		
		outputToChannel.println("NICK" + " " + nickname);
	}
	/*
	public static void main(String[] args){
		
			
		
		try{
			
			new IRCConnection();
			

			

			inputThread.start();
			outputThread.start();
			
			
		}
		catch(IOException e ){
			e.printStackTrace();
		}
		
	}
	
*/
	
}


