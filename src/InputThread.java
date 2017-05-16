import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JTextArea;
/*This class handles data stream that is INCOMING to the console, so all data given a IRC connection
 * will be recieved and printed with this class
 * 
 */
//Author Danny
public class InputThread extends Thread{
	
	
	private IRCConnection inputStream = null;
	private String incomingText;
	private Channel outputStream = null;
	private String pingServerAdd;
	
	private JTextArea sendMsgTo;
	
	
	private String refinedmsgToSend;
	public InputThread(IRCConnection inputStream, JTextArea sendMessageTo) throws UnknownHostException{
		//constructor given a IRCConnection
		this.inputStream = inputStream;
		this.sendMsgTo = sendMessageTo;
	}
	/*
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 * This class sends messages out to the console or where ever needs desired,
	 * this class is a Thread based and will retrieve text that has been send from server to this client
	 */
	public void run(){
	
		try {
			while((incomingText = inputStream.getInputFromChannel().readLine()) != null ){
				/*
			//Sthis.sleep(1000);
			if(inputText.contains("/")){
				
				System.out.print(inputText.substring(1,inputText.indexOf("!")) + inputText.substring(inputText.indexOf("#"), inputText.length()-1));
			}
			else{
				System.out.println(inputText);
			}
			//System.out.println(inputText.substring(inputText.indexOf(":"), arg1));
			}
			*/
				if(incomingText.contains("PRIVMSG") && incomingText.contains("#"+inputStream.getChannelName())){
					refinedmsgToSend = incomingText.replaceAll(incomingText.substring(incomingText.indexOf("!"), incomingText.indexOf("#")), " ");
					sendMsgTo.append("\n"+refinedmsgToSend);// adds the text to the givein jText Area
				}else{
				
				System.out.println(incomingText);
				sendMsgTo.append(incomingText);// adds the text to the givein jText Area
				
				if(incomingText.substring(0, 4).contains("PING")){
					pingServerAdd = "PONG " +incomingText.substring(6, incomingText.length());
					outputStream.println(pingServerAdd);
				}
				}
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		
		
	}
	public static void main(String[] args){
		
		String s = "PING :card.freenode.net";
		System.out.println(s.substring(0,4));
	}
}
