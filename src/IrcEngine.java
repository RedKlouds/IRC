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

	
	public IrcEngine() throws IOException{
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
	
	public String getCurrentChannel(){
		return this.currentChannel;
	}
	private void joinChannel(String channelName){
		//do not include hashtage
		String channelToJoin = channelName.replace("#","");
		//join the speicic chatroom
		this.getStreamtoService().println("JOIN #" + channelToJoin);
	}
	
	private void register(){
		System.out.printf("[+] Registering with NickName: %s%n", this.nickName);

		this.getStreamtoService().println("USER" + " " + this.nickName + " " + "localhost" + " " +this.host + " " + this.nickName);
		//getStreamToServer is used whenever i need to send soemthig to the server so we can pass around the method that returns this printStream.
		this.getStreamtoService().println("NICK" + " " + nickName);
		
		
		//getStreamtoService().println("JOIN" + " " + "#" + getChannelName() );
	}
	private void connect() throws IOException{
		//connect to the socket of the irc service
		this.socket.connect(new InetSocketAddress(this.host,6667),0);
		this.setStreamToService(this.socket);
		this.setStreamFromService(this.socket);
	}
	
	private void setStreamToService(Socket socket) throws IOException{
		this.streamToService = new PrintStream(socket.getOutputStream());
	}
	private void setStreamFromService(Socket socket) throws IOException{
		this.streamFromService = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
	}
	
	public BufferedReader getStreamFromService(){
		return this.streamFromService;
	}
	public PrintStream getStreamtoService(){
		 return this.streamToService;
	}
	
	public void nickNamePrompt(){
		//helper function to check for non empty input for nickname
		this.nickName = JOptionPane.showInputDialog("Please enter a valid nicname");
		while(nickName.length() == 0){
			this.nickName = JOptionPane.showInputDialog("Please enter a valid nicname");
		}
	}
	
	public static void main(String[] args){
		try{
		IrcEngine irc = new IrcEngine();
		}catch(IOException e){
			
		}
	}
}
