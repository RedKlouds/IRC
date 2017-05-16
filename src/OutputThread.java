import java.util.Scanner;
/*
 * this Class handles user OUTGOING stream that will be sent given a channel object to that particular channel
 */
//Author Danny
public class OutputThread extends Thread {
/*
 * this class takes messages and outputs them to the channel
 */
	
	private Channel outputChannel;
	private Scanner userInput;
	
	public OutputThread(Channel outPutChannel){
		this.outputChannel = outPutChannel;
		
	}
	
	
	public void run(){
		try{
			
			
			userInput = new Scanner(System.in);
			while(true){
				outputChannel.println(userInput.nextLine());
			}
			
		}catch( Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args){
		String test = ":nobody1_!3288e9d8@gateway/web/freenode/ip.50.136.233.216 PRIVMSG #ubuntu :undecim nothing in the brightnes";
		String yo = ":genkiklaus!492a8dd1@gateway/web/cgi-irc/kiwiirc.com/ip.73.42.141.209 PRIVMSG #lo :FUKC YOU";
		System.out.println(test.substring(1,test.indexOf("!")) + test.substring(test.indexOf("#"), test.length()));
		//System.out.println(yo.substring(yo.indexOf("!"), 50));
		
		
		System.out.println(yo.substring(yo.indexOf("!"), yo.indexOf("#")));
		
		System.out.println(yo.replaceAll(yo.substring(yo.indexOf("!"), yo.indexOf("#")), " "));
	}
}
