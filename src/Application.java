

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.text.DefaultCaret;
/*
 * NEW rework:
 * think of making a class called CHANNEL, CHANNEL WILL HAVE:
 * 	each channel Object will have a channel name, a out put and an input to that channel and from that channel,
 * application will make an instatiation of a Channel class and add that to our CHANNEL ARRAY when we push a button called "add channel'
 * we will try to put a start method in each channel so that when we grab the ojects in the array we can call start on them and each INDIVIDUAL thread will handle a Channel object,
 * EACH channel object thread will have its job of handling incoming and out going traffic, 

 * 
 * the MAIN base for our clients name will be in our APPLICATION class, which will pass a nickname and MAYBE a channel to the CHANNEL class
 */




public class Application extends JFrame{ // this entire class IS IS iS a JFrame, you do not need to instantiale another JFrame. DERPPP


	//get Clients computer name, protected privacy
	
	protected String clientComputerName = System.getProperty("user.name");

	/*
	 * Reworked Areas:
	 * 	Notes: Variables
	 */
	//example of how i would instatiate this 
	/*
	
	IRC_Engine IRC_Engine_Root;
	
	String nickName;
	ArrayList<Channel> channelArray = new ArrayList<Channel>();
	
	*/
	
	
	
	//private IRCConnection IRCObject;
	
	private String channel;
	
	
	private String dir = System.getProperty("user.dir");
	
	
	//private InputThread incomingMsg;
	
	public Application() throws UnknownHostException, IOException{
		adjustWindowProperties(); //just a helper method for cleaner code addjust the JFrames properties, and sets the layout
		//IRC_Engine_Root = new IRC_Engine();//creates an instance of Channel;
		
		//setupNickName(JOptionPane.showInputDialog("Enter a nickname:"));//registers a nickname for the user
		
		
		this.channel = JOptionPane.showInputDialog("Please enter a channel to join");
		
		//this.IRCObject = new IRCConnection(this.channel);
		

		
		/*
		public Application(IRCConnection IRCObject){
			//set THIS Jframes title
			setTitle(clientComputerName + " Chat Client");
			
			//set THIS Jframes Size:
			setSize(450, 450);
			
			//set THIS JFrames starting location Default (0,0) TP Left
			setLocation(20, 30);

			getContentPane().setLayout(new BorderLayout(10,15));
			
			this.IRCObject = IRCObject;
			
			*/
		
	
		//////////////////////////////////////////	SECTION FOR TERMINAL DISPLAY FIELD/////////////////////////////////////
		/*
		 * This portion our JtextArea is housed within a JScrollPane, the Pane allows when text is overflowed to create a scroll bar menu 
		 * to allow the text to be displayed in a scroll manner, and howing the Jtext is included with the JScrollPane
		 * 
		 * 
		 * 
		 * 
		 */
		
		
		//created another Jpanel to be used as terminal Panel
		JPanel terminalPanel = new JPanel();
		

		terminalPanel.setLayout(new GridLayout());//put our terminal panel into a grid layout
		
		
		//terminalPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // alignment for the terminal Panel
		
		terminalPanel.setSize(400,200); //set size of the terminal panel
		
		
		
		
		terminalPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(),"Terminal Channel Chat"));//put a nice border around our terminal panel, and some title text
		
		
		JTextArea terminalMsgDisplay = new JTextArea();
		//new InputThread(this.IRCObject, terminalMsgDisplay).start(); //made a thread here and attached it to the textArea to output
		
		DefaultCaret caret = (DefaultCaret)terminalMsgDisplay.getCaret();//auto scroll policy needs to make changes
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		terminalMsgDisplay.setCaretPosition(terminalMsgDisplay.getDocument().getLength());
		
		//IRCObject.setIncommingMsg(terminalMsgDisplay); send messages to the jtextArea
		
		JScrollPane terminalScrollPane = new JScrollPane(terminalMsgDisplay);//this is how you use a JScroll Pane DO not USE .add just throw in your text field into the constructor and let everything do the rest
		
		terminalScrollPane.setMaximumSize(new Dimension(100, 400));
	
		// makes instance of scroll Pane for our terminals text to be placed into
	
	
		
	
		

		
		//terminalMsgDisplay.append("This is Where text will be displayed from Using input and output Threads ");
		
		terminalMsgDisplay.setLineWrap(true);
		terminalMsgDisplay.setWrapStyleWord(true);

		terminalPanel.add(terminalScrollPane);
		
		getContentPane().add(terminalPanel);
		
		////////////////////////////////////////////////////// END OF TERMINAL DISPLAY WINDOW SECTION////////////////////////////
		
	
		
		
		
		
		
		
		//////////////////////////////////////////////	SECTION FOR THE CONTROL PANEL ////////////////////
		/*
		 * tHIS USES A group layout 
		 */
		
		
		//controlPanel
		
		JPanel rightPane = createAndSetJPanel(new BorderLayout(), 250, 240);
		
		
		//JPanel controlPanel = new JPanel(new BorderLayout());
		JPanel controlPanel = createAndSetJPanel(new BorderLayout(), 250, 240);
		//sets a border for the JPanel
		setBorderOnPanel(controlPanel, "Control Panel");
		
		
		JPanel controlPanelButton = createAndSetJPanel(new GridLayout(0,2,5,5), 50, 50);

		
		
		/******************************************************
		 * Idea:
		 * make seperate instances of a channel, and point thier output to the textfield
		 * keep making different instances and have them connected  to the Jtextfield
		 * 
		 * 
		 * 
		 */
		
		
		
		
		
		
		
		//JPanel controlPanelButton = new JPanel(new GridLayout(0,2,5,5));
		
		//controlPanelButton.setPreferredSize(new Dimension(50, 50));
		//controlPanel.setLayout(new GridLayout(3,3,10,30));

		
		
		
		HashMap hashmap = new HashMap<String, nodeTest<String>>();
		ArrayList<nodeTest> list = new ArrayList<nodeTest>();
		//DefaultListModel listModel = new DefaultListModel(hashmap); // this extends list, basically a list structure

		
		
		//hashmap.put();
		
		//Hashtable<String, Object> table = new Hashtable<String,Object>();

		//JPanel channelListpane = new JPanel();
		
		DefaultListModel<nodeTest> list1 = new DefaultListModel<nodeTest>();
		for(int i =0; i < 5; i++){
			nodeTest temp = new nodeTest<String>();
			list1.addElement(temp);
		}
		
		JList ChannelList = new JList(list1); // this class uses generics to have a type for the items within a data structure
		ChannelList.setLayoutOrientation(JList.VERTICAL);
		ChannelList.setPreferredSize(new Dimension(130,5));
		
		
		
		
		
		
		
		
		
		
		
		JButton[] controlButton = setLabelOnButton(new String[]{"Add Chan","Remove Chan","Button 1", "Button 2"}, controlPanelButton);

		
		controlButton[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String temp = JOptionPane.showInputDialog("Input some message/Channel to join");
				
				list1.addElement(new nodeTest(temp));
				/*
				for(int i =0; i < 20; i++){
					tet.addElement("Blah " + i * Math.random()*5);
				}
				tet.addElement(temp);
				
			*/
			}
			
		});

		controlButton[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index = ChannelList.getSelectedIndex();
				if(index <0){
					JOptionPane.showMessageDialog(null, "No items selected, please seletec a channel");
				}else{
					list1.remove(index);
					System.out.println(index);
					//getSelectedIndecies will return an int[] with multiple indexes seleteced
				}
			}
		});

		
		
		//JPanel blankPan = new JPanel();
		
		JScrollPane listScroll = new JScrollPane(ChannelList);
		
		listScroll.setPreferredSize(new Dimension(140,100)); //wideX hiegth of the JList size

		
		
		
		JPanel channelDisplay = new JPanel(new BorderLayout());
		


		channelDisplay.add(new JLabel("Channel Listing:"), BorderLayout.PAGE_START);
		channelDisplay.add(listScroll, BorderLayout.LINE_END);

		
		
		
		
		
		
		


		//icons must be sized gif/png .. etc 16x16, 32x32, 64x64x 128x128


		
		JPanel botLink = new JPanel(new FlowLayout(FlowLayout.RIGHT,3,3));
		
		JButton[] setButton = createCustomJButton(new ImageIcon[] {new ImageIcon(dir + "\\test1.gif","Check out my GitHub!"),new ImageIcon(dir + "\\test.png","Want to see amazing projects, check this guy out!"),new ImageIcon(dir + "\\linkdin.png","want to connect? :D")} , botLink);

		
		setButton[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					Desktop.getDesktop().browse(new URI("https://github.com/damugen123"));
				}catch(Exception q){
					q.printStackTrace();
				}
			}
		});
		
		setButton[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					Desktop.getDesktop().browse(new URI("https://github.com/Maome"));
				}catch(Exception qq){
					qq.printStackTrace();
				}
			}
		});
		
		
		setButton[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					Desktop.getDesktop().browse(new URI("www.linkedin.com/in/dannyly0/"));
				}catch(Exception qq){
					qq.printStackTrace();
				}
			}
		});
		
		
		//controlPanel.add(new JLabel("test"),BorderLayout.LINE_END);

		//controlPanel.add(botLink, BorderLayout.SOUTH);
	
		
		controlPanel.add(controlPanelButton, BorderLayout.NORTH);
		
		controlPanel.add(channelDisplay, BorderLayout.CENTER);
	

		
		rightPane.add(controlPanel,BorderLayout.PAGE_START);
		
		rightPane.add(botLink,BorderLayout.PAGE_END);
		
		//controlPanel.add(topInformationPanel,BorderLayout.NORTH);
		//getContentPane().add(controlPanel, BorderLayout.LINE_END);
		
		
		getContentPane().add(rightPane, BorderLayout.LINE_END);
		
		
		
		////////////////////////////////////	END OF THE CONTROL PANEL SECTION 	//////////////////////////////////////////////////////////
		
		
		
		
		
		
		//////////////////////////////////////////////		BOTTOM TEXT INPUT AREA SECTION /////////////////////////////////
		/*
		 * Need to refine this area
		 * This section uses a Border layout to shift the JTextField to the left
		 * 
		 * Notes:
		 * 	address Msg action listener to clear the text after the enter key is pressed
		 * 
		 */
		
		
		
		//set bottom message text area
		JPanel sendMessagePanel = new JPanel(new BorderLayout());
		
		
		JPanel textFramPanel = new JPanel(new GridLayout(1,1));
		

		

		JTextField sendMsgField = new JTextField("Enter your message to send here");
		JScrollPane scroll = new JScrollPane(sendMsgField);
	
		
		sendMsgField.setColumns(18); // sets a length fro this box
		textFramPanel.add(sendMsgField);

		sendMessagePanel.add(textFramPanel, BorderLayout.LINE_START);
		
		JPanel msgButtPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton[] messagePanelButtonArr= setLabelOnButton(new String[]{"Clear Chat"}, msgButtPanel);

		//setBorderOnPanel(sendMessagePanel, "Input of Message:");
		
		sendMessagePanel.add(msgButtPanel, BorderLayout.LINE_END);
		
		

		
		sendMsgField.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLoweredBevelBorder(),
				"Input Messages here") );
		
		
		
		
		
		getContentPane().add(sendMessagePanel, BorderLayout.PAGE_END);
		
		//////////////////////////////////////////////////END OF BOTTOM MESSAGE INPUT SECTION///////////////////////////////
		
		
		
		
		
		
		////////////////////////////////////////TOP DROP DOWN MENU SECTION ////////////////////////////////////////////////////
		/*
		 * This is all in Beta testing and will we Optimized and reformed 
		 * 
		 * Notes:
		 * 		for Java 2D array it is legal to define JUST the first Columns size
		 * 		IE: String[][] example =  new String[7][]; // this is ohkay
		 * 		
		 * 
		 * 
		 */
		

		
		//create a top menu drop down bar
		JMenuBar menuBar = new JMenuBar();
	
		JMenu[] parentJmenuArr =  setupJmenuBar(new String[]{"File", "About", "Help"}, menuBar); // inline instatiation for array that will be accessed only once.
		
	
		//usering multi demenional array with in-line array declaration to help with cleaner code
		JMenuItem[][] menuItemsArr = setupSubMenuItems(
				new String[][]{ 
						{"File","New#", "Exit"},  // see documenttation in this method for more information
						{"About This UI","Blank0#", "Blank1#", "Blank2"}, //testing out the 'hash' symbol seperator
						{"Get Help"} }
				,parentJmenuArr );
		
	
		//access by [col][rows]

		
		/*Top JmenuBar action listener section:
		 * 	Notes: still needs development to "group" the listener to make code cleaner and clearer
		 * 
		 */
		menuItemsArr[0][2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(-1);
			}
		});
		
		menuItemsArr[1][0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Hi, hellow, kinechiwa?"
						+ "\nCreated/Developed by Danny Ly"
						+ "\nStill in Beta Testing V0.5 ( 1/27/2015)"
						+ "\nThanks for trying!");
			}
		});
		
		menuItemsArr[2][0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				//need to add or change the title of the pop up message!
				JOptionPane.showMessageDialog(null, "Hello!"
						+ "\nSo you've stumped on some problems with this?, howeverrrrrrr If your name is Reilly"
						+ "\nGo Figure it out on your own !!!"
						+ "\n else{"
						+ "\n sorry =\\ i cannot help you i'm only a machine that takes in instructions}");
			}
		});
		

		/*
		 * End of Action Listeners for the Top JmenuBar
		 */

		//sets the Frames MenuBar
		setJMenuBar(menuBar);
		
		/////////////////////////////////////////    END OF JMENUBAR/JMENUITEM//////////////////////////////////////////
		/*
		 * 
		 * 
		 * 
		 */
		
		
		
		
		

		
		//adds a WINDOW listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.out.println("Ayyy yooo");
			}
		});
	
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	

	
	private void addComponetsToPanel(Object[] componetsArr, JPanel panel){
		
		for(int i = 0 ; i < componetsArr.length; i++ ){
			//panel.add(componetsArr[i]);
		}
		
	}
	
	private void adjustWindowProperties(){
		//set THIS Jframes title
		setTitle(clientComputerName + " Chat Client");		
		//set THIS Jframes Size:
		setPreferredSize(new Dimension(430,450));
		//setSize(500, 600);		
		//set THIS JFrames starting location Default (0,0) TP Left
		setLocation(20, 30);

		getContentPane().setLayout(new BorderLayout(10,15));
	}

	
	private JButton[] setLabelOnButton(String[] labelNames, JPanel addToPanel){
		/*
		 * given a array of same length labal names, and buttons, adds labal names in order to thier respective buttons
		 * then add those buttons to the given panel
		 */
		
		
		JButton[] returnArr = new JButton[labelNames.length];
		JButton currentButton ;
		for(int i = 0; i < labelNames.length; i++){		
	
			currentButton = new JButton(labelNames[i]);
			//currentButton.setPreferredSize(new Dimension(20,20));
			returnArr[i] = currentButton;
			addToPanel.add(currentButton);
		}
		return returnArr;
		
		
	}
	
	private JPanel createAndSetJPanel(LayoutManager layoutType,int perferWidth, int perferHeigh){
		JPanel returnPanel = new JPanel();
		returnPanel.setLayout(layoutType);
		returnPanel.setPreferredSize(new Dimension(perferWidth, perferHeigh));
		
		
		return returnPanel;
	}
	private JMenu[] setupJmenuBar(String[] labForMenu, JMenuBar menuBar){
		/*
		 * This method is called to set up the parent Jlabels for the JmenuBar 
		 * adds the text to the parent menuBar, then add the items to the pannel given to this method
		 * Notes: this returns a Jmenu, each Jmenu has been configured
		 */
		JMenu[] retJmenu = new JMenu[labForMenu.length]; // instatiates a empty Jmenu array with  the menu contained and corrected size according to the amount of labels given
		String tempLabelNam;
		JMenu currentJmenu;
		
		for(int i =0; i< labForMenu.length; i++){
			tempLabelNam = labForMenu[i];
			
			currentJmenu = new JMenu(tempLabelNam);		 // creates and adds a new Jmenu to the return array
			currentJmenu.setMnemonic(tempLabelNam.charAt(0)); // set the nemonics for that menu item
			
			retJmenu[i] = currentJmenu;

			menuBar.add(retJmenu[i]); // adds the menu item to the panel
		}
		
		return retJmenu; // returs the modified array to work with .
		
	}

	
	private void setBorderOnPanel(JPanel panel, String title){

		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLoweredBevelBorder(),
				title) );
		
	}

	
	
	
	private JMenuItem[][] setupSubMenuItems(String[][] menuItemNames, JMenu[] Jmenu  ){
		/*
		 * this method was designed to take in a 2D array of string of menuItem names and associated Jemnu for them to be added,
		 * this method will create a JmenuItem and add them to the given Jmenu component
		 * returns a 2D JMenuItem array to access each JmenuItem and add action listenrs or work with them.
		 * 	Notes:
		 * 		-- Currently impolemented:
		 * 			- each Menu has a Nemonic keyeven which allows keypress to activate that menu given its first character
		 * 			- implemented a "method" to add a seperator, by using the 'Hash#' symbol after a string to signal a seperator
		 * 			
		 */
		
		int MAX_NUM_OF_ITEM = 10; // this number because in a dimentoon array it is [colums][rows] we cannot predetermine how many rows(menu items there will be)
		
		// however we can determine how many colums there will be , this is where theis MAX num comes from so we set a standard DO NOT EXCEED marker.
		
		
		String tempNames; // optimize code so its more we only need to init this variable once to reuse.
	
		if(Jmenu.length != menuItemNames.length) { throw new RuntimeException("Sizes are not right!"); }; // throws if the colums do not match up with the amount of menu parent labels
		
		JMenuItem[][] retArr = new JMenuItem[Jmenu.length][MAX_NUM_OF_ITEM]; // instatiates a empty 2D array to be returned with correct [columns][ MAX NUM OF ITEMS]

		for(int column = 0; column < menuItemNames.length ; column++ ){ // acces the column
			
			for(int row = 0; row < menuItemNames[column].length ; row ++){ // acces the rows of that colnm
				
				tempNames = menuItemNames[column][row]; // uses this variable once
				
				retArr[column][row] = new JMenuItem(tempNames.replace("#", "")); // removes the symbol before adding the menuItem to the Jbar		
				retArr[column][row].setMnemonic(tempNames.charAt(0)); // adds a nmonic for that menuitem
				
				Jmenu[column].add(retArr[column][row]); // add's the modified menuitem to the Jmenu
				
				if(tempNames.contains("#")){ // self defined symbol to add a seperator to the menu after this name
					
					
					
					Jmenu[column].addSeparator();// adds a seperator
					
				}	
				
			}
			
		}

		return retArr; // returns the 2D modified array to work with .
	}
	
	
	private JButton[] createCustomJButton(ImageIcon[] iconArr, JPanel panel){
		/*
		 * This method could be optimized, needs works
		 * 
		 * Desc: this method returns a Jbutton of cutoms NO Opaque jbuttons (see-thru) a work around to make a link to things
		 * and sets a tooltip popup 
		 * adds buttons to the given panel
		 */
		JButton[] retArr = new JButton[iconArr.length];
		JButton currButton;
		for(int currBut = 0; currBut < iconArr.length; currBut++){
			currButton = new JButton(iconArr[currBut]);
			currButton.setOpaque(false);
			currButton.setBorderPainted(false);
			currButton.setContentAreaFilled(false);
			
			currButton.setToolTipText(iconArr[currBut].getDescription());
			currButton.setPreferredSize(new Dimension(32,32));
			
			retArr[currBut] = currButton;
			panel.add(retArr[currBut]);
			
			
		}
		
		
		return retArr;
	}


	
	
	
	public static void main(String[] args) throws IOException{
		//initialize the GUI
		/*
		try {
			//Application client = new Application();
			//IRC_Engine irve =  new IRC_Engine();
			//irve.register("REDKKKALOWS");
			IRC_Engine t1 = new IRC_Engine("kiwiirc-default");
			BufferedReader stream = t1.getStreamFromServer();
			String line;
			while((line = stream.readLine()) != null){
				System.out.println(line);
			}
		} catch (Exception a){
			a.printStackTrace();
		}*/
		
		/*catch (IOException e){
		 * {print(e)
		 * 
		 */
		
		try {
			Application client = new Application();
			//Desktop.getDesktop().browse(new URI("http://google.com"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}catch(Exception e){
			e.printStackTrace();
		}

		Socket sock = new Socket();
		BufferedReader r; 
		PrintStream p;
		Output o;
		String usern = "passionfrui59";
		InputStreamReader cin;
		
		InputL in;
		String _channel = "#kiwiirc-default";
		
		try{
			
			
			sock.connect( new InetSocketAddress("irc.freenode.net",6667),0);
			r = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			p = new PrintStream(sock.getOutputStream());
			
			p.println("USER " + usern + " localhost irc.freenode.net " + usern);
			p.println("NICK " + usern);
			p.println("JOIN " + _channel);
			o = new Output(r);
			o.start();
			in = new InputL(p, _channel);
			
			in.start();
			p.println("PRIVMSG" + " " + "#" + "kiwiirc-default" + " " + ":" + "YOLO SWAG");  

			
			
		}catch(IOException e){
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



class Output extends Thread{
	private BufferedReader br;
	public Output(){
		this(null);
	}
	public Output(BufferedReader s){
		this.br = s;
	}
	public void run(){
		String line;
		try {
			while((line = this.br.readLine()) != null){
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



