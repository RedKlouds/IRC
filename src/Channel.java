import java.io.PrintStream;
/*
 * this Class is designed to create an instance of a the specific channel we are trying to chat into to,
 * this class includes the variables of the name of the Channel
 */

public class Channel {

	private String channelName;
	private PrintStream outputToChannel; // this printStream is used in out defined println method,
	
	
	protected Channel(String channelName, PrintStream outputToChannel){
		/*Channel constructor for inilization of the channel object
		 * includes init of channel name and a Print output stream to that channel
		 */
		this.channelName = channelName;
		
		this.outputToChannel = outputToChannel;
		
		outputToChannel.println("JOIN" + " " + "#" + channelName); // calls the IRC connamd //join appends command /join #ChannelName
	}
	
	public String getChanName(){
		return this.channelName;
	}
	
	 public void println( String msg ){
		 /*
		  * defined println method that is called whenever we want to send something to the private channal using out channels output stream, and relaying it to that channel
		  */
	    outputToChannel.println ( "PRIVMSG" + " " + "#" + channelName + " " + ":" + msg );
	  }

}
