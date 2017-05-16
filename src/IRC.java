import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class IRC {
	private UserInterface UI;
	private IrcEngine IRCEngine;
	private PrintStream streamToService;
	private BufferedReader streamFromService;
	private Output bufferToTerminal; //buffer to send information to
	//Userinterface
	public IRC(){
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
		bufferToTerminal = new Output(streamFromService, UI);
		this.start();
		
	}
	
	public void start(){
		bufferToTerminal.start();
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
