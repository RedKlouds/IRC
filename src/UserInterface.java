import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
/**************************************************************
 *	Danny Ly
 *
 * This class is used to handle population of the User interface
 * There will  be methods however to allow interfacing with the 
 * panels inorder to display text to them
 * 
 * Class Description:
 * 	-> This class handles populating the User interface where
 * it will also have one inlet and 1 outlet, the inlet allows
 * for text to come into the user interface and dispay on the 
 * the terminal window.
 * 	-> The outlet will allow for data to flow from the input to
 * the associated web service, I've tried to design this as 
 * Modulated as possible, such that it is free to allow incoming
 * as well as outgoing text to sources.
 * 
 * 
 ***************************************************************/

public class UserInterface extends JFrame{
	
	
	//Singleton, meaning only a single instance of this object may exist
	
	private static UserInterface _userinterface = null;
	//get the user computer name to used as a display for the top of the jFrame
	protected String clientComputerName = System.getProperty("user.name");
	//terminalDisplay and inputMessageField are out two connections to interfaces
	//that want to display or input data to our UI
	private JTextArea terminalDisp;
	private JTextField inputMessageField;

	//used asthetic to show computer name at top of UI client
	private String dir = System.getProperty("user.dir");
	
	
	/**************************************************************************
	 * Function: Default constructor

	 * Description:
	 * 	-> Private default constructor intentially done to, prevent instantiation
	 * of more than one type of this object, this is done through a technique 
	 * known as singleton
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> User calles getInstance instead of trying to construct the object
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	-> sets up the contentPane size and populates all panels onto the main
	 * content pane
	 * 	-> None
	 **************************************************************************/
	private UserInterface(){
		
		terminalDisp = new JTextArea();
		inputMessageField = new JTextField("Enter your message to send here");
		//set up this window properties
		adjustWindowProperties();
		//gather the required panels to add into the main panels
		JPanel terminalPanel = makeTerminalPanel(terminalDisp);
		JPanel controlPanel = makeControlPanel();
		JPanel bottomPanel = makeBottomPanel(inputMessageField);
		JMenuBar menuBar = makeMenuBar();
		
		//add all the sub panels to the main panel
		getContentPane().add(terminalPanel);
		getContentPane().add(controlPanel, BorderLayout.LINE_END);
		getContentPane().add(bottomPanel, BorderLayout.PAGE_END);
		//add the JmenuBar
		setJMenuBar(menuBar);
		//adds a WINDOW listener to test on text scenarios
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.out.println("WE OUT HERE FAM");
			}
		});
	
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**************************************************************************
	 * Function: GetInstance
	 *  
	 * Description:
	 * 	-> Entry point for the Object to get made only once, this method ensure 
	 * it is thread safe by making this method synchonized, although we can also 
	 * make this more optimized by adding 'double checking, by making just the 
	 *one method a synchonized instead of the entire function, i choosen not to
	 *because this method is so lightweight. 
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->Returns an instance of UserInterface to the caller.
	 * 	->
	 **************************************************************************/
	public synchronized static UserInterface getInstance(){
		//inorder to make sure that the object is thread safe we need
		//to synchonize the getInstance method, 
		//Synchonize as in one call after another.
		//place where the object will be returned, and only one 
		//will exising because of the following code
		if(_userinterface == null){
			//prevent to instantiate more than just the one
			_userinterface = new UserInterface();
		}
		//return the single reference to this object
		return _userinterface;
	}
	

	/**************************************************************************
	 * Function: MakeTerminalPanel
	 *  
	 * Description:
	 * 	-> This function is created to populate the panel for the terminal
	 * display also known as the chat log, which is incoming and out going
	 * traffic
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->
	 * 	->
	 **************************************************************************/
	private JPanel makeTerminalPanel(JTextArea terminalMsgDisplay){
		JPanel terminalPanel = new JPanel();
		//set the layout manager for the terminal panel
		terminalPanel.setLayout(new GridLayout());
		//set the size of the terminal panel
		//WidthXlength
		terminalPanel.setSize(800,200); 		
		//give the panel border
		terminalPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(),"Terminal Channel Chat"));
		//put a nice border around our terminal panel, and some title text
		DefaultCaret caret = (DefaultCaret)terminalMsgDisplay.getCaret();
		//auto scroll policy needs to make changes
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		terminalMsgDisplay.setCaretPosition(terminalMsgDisplay.getDocument().getLength());
		//IRCObject.setIncommingMsg(terminalMsgDisplay); send messages to the jtextArea
		//add the scroll pane
		JScrollPane terminalScrollPane = new JScrollPane(terminalMsgDisplay);
		//this is how you use a JScroll Pane DO not USE .add just throw in your text field into the constructor and let everything do the rest
		terminalScrollPane.setMaximumSize(new Dimension(100, 400));
	
		// makes instance of scroll Pane for our terminals text to be placed int
		//terminalMsgDisplay.append("This is Where text will be displayed from Using input and output Threads ");
		terminalMsgDisplay.setLineWrap(true);
		terminalMsgDisplay.setWrapStyleWord(true);
		//add the terminal scrolling panel to the terminal panel
		terminalPanel.add(terminalScrollPane);
		//add the listener for when a values is changed in the scroll pane , it will update
		//the scroll panel bar, to the lowest/maximum location
		terminalScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				// TODO Auto-generated method stub
				arg0.getAdjustable().setValue(arg0.getAdjustable().getMaximum());  
			}
	    });
	
		
		return terminalPanel;
		
	}

	/**************************************************************************
	 * Function: makeControl panel
	 *  
	 * Description:
	 * 	-> This panel handles displaying the links for facebook, github and 
	 * linkdin, 
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->
	 * 	->
	 **************************************************************************/
	private JPanel makeControlPanel(){
		
		JPanel rightPane = createAndSetJPanel(new BorderLayout(), 120, 150);
		//panel for the control panel
		
		JPanel controlPanel = createAndSetJPanel(new BorderLayout(), 250, 240);
		//sets a border for the JPanel
		setBorderOnPanel(controlPanel, "Control Panel");
			
		JPanel controlPanelButton = createAndSetJPanel(new GridLayout(0,2,5,5), 50, 50);
		

		JPanel botLink = new JPanel(new FlowLayout(FlowLayout.RIGHT,3,3));
		
		JButton[] setButton = createCustomJButton(new ImageIcon[] {
				new ImageIcon(dir + "\\RedKlouds.png","Check out my GitHub!"),
				new ImageIcon(dir + "\\linkdin.png","want to connect? :D")
				} , botLink);

		
		setButton[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					Desktop.getDesktop().browse(new URI("https://github.com/redklouds"));
				}catch(Exception q){
					q.printStackTrace();
				}
			}
		});
		
		
		setButton[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					Desktop.getDesktop().browse(new URI("www.linkedin.com/in/dannyly0/"));
				}catch(Exception qq){
					qq.printStackTrace();
				}
			}
		});

		controlPanel.add(controlPanelButton, BorderLayout.NORTH);
		
	
		
		rightPane.add(controlPanel,BorderLayout.PAGE_START);
		
		rightPane.add(botLink,BorderLayout.PAGE_END);
		
		//controlPanel.add(topInformationPanel,BorderLayout.NORTH);
		//getContentPane().add(controlPanel, BorderLayout.LINE_END);
		
		
		getContentPane().add(rightPane, BorderLayout.LINE_END);
	return rightPane;
				
	}
	/**************************************************************************
	 * Function: adjustWindowProperties
	 *  
	 * Description:
	 * 	-> Adjust the window properties of the inital ContentPane
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	-> settings for ContenPane are set
	 * 	->
	 **************************************************************************/
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
	
	
	/**************************************************************************
	 * Function: makeMenuBar
	 *  
	 * Description:
	 * 	-> makes the menu bar at the top of the UI, and adds action listeners
	 * to each one of them
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	-> Menu bar as been populated and returned to the caller to be 
	 * added into the content pane
	 * 	->
	 **************************************************************************/
	private JMenuBar makeMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		//an array to minimize code, holds the Jmenu items
		//calls a helpermethod setupJmenuBar to add them to the given
		//menuBar
		JMenu[] parentJmenuArr =  setupJmenuBar(new String[]{"File", "About", "Help"}, menuBar); // inline instatiation for array that will be accessed only once.
		
		//usering multi demenional array with in-line array declaration to help with cleaner code
		JMenuItem[][] menuItemsArr = setupSubMenuItems(
				new String[][]{ 
						{"File","New#", "Exit"},  // see documenttation in this method for more information
						{"About This UI","Blank0#", "Blank1#", "Blank2"}, //testing out the 'hash' symbol seperator
						{"Get Help"} }
				,parentJmenuArr );
		
		menuItemsArr[0][2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//when submenu is asked to exit exit the ui gracfully
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
				//need to add or change the title of the pop up message!
				JOptionPane.showMessageDialog(null, "Hello!"
						+ "\nSo you've stumped on some problems with this?, howeverrrrrrr If your name is Reilly"
						+ "\nGo Figure it out on your own !!!"
						+ "\n else{"
						+ "\n sorry =\\ i cannot help you i'm only a machine that takes in instructions}");
			}
		});
		
		return menuBar;
	}
	/**************************************************************************
	 * Function: makeBottom Panel 
	 *  
	 * Description:
	 * 	-> This function is in charge of creating the user input bar at the 
	 * bottom of the User interface, which include an text fiel and a button
	 * to clear the main terminal text
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	-> returns the pane with input and text clear button
	 * 	->
	 **************************************************************************/
	private JPanel makeBottomPanel(JTextField inputMessageField){
		
		//set bottom message text area
		JPanel sendMessagePanel = new JPanel(new BorderLayout());
		
		JPanel textFramPanel = new JPanel(new GridLayout(1,1));
		
		JScrollPane scroll = new JScrollPane(inputMessageField);
	
		inputMessageField.setColumns(18); // sets a length for this box
		textFramPanel.add(inputMessageField);

		sendMessagePanel.add(textFramPanel, BorderLayout.LINE_START);
		
		JPanel msgButtPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton[] messagePanelButtonArr= setLabelOnButton(new String[]{"Clear Chat"}, msgButtPanel);

		messagePanelButtonArr[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				terminalDisp.setText("");
				
			}
		});
		
		//setBorderOnPanel(sendMessagePanel, "Input of Message:");
		
		sendMessagePanel.add(msgButtPanel, BorderLayout.LINE_END);

		inputMessageField.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLoweredBevelBorder(),
				"Input Messages here") );


		return sendMessagePanel;
		
	}
	
	public JTextField getInputTextField(){
		return this.inputMessageField;
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
	/**************************************************************************
	 * Function:  Helper function 
	 *  
	 * Description:
	 * 	-> Given an array of label names and a panel, craete the buttons in order
	 * and populate them onto the panel that was given
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	-> return the panel of button that have been populated
	 * 	->
	 **************************************************************************/
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
	/**************************************************************************
	 * Function: Helperfunction to set the JPanel
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
	private JPanel createAndSetJPanel(LayoutManager layoutType,int perferWidth, int perferHeigh){

		JPanel returnPanel = new JPanel();
		returnPanel.setLayout(layoutType);
		returnPanel.setPreferredSize(new Dimension(perferWidth, perferHeigh));
		
		
		return returnPanel;
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
	/**************************************************************************
	 * Function: 
	 *  
	 * Description:
	 * 	-> * this method was designed to take in a 2D array of string of menuItem names and associated Jemnu for them to be added,
		 * this method will create a JmenuItem and add them to the given Jmenu component
		 * returns a 2D JMenuItem array to access each JmenuItem and add action listeners or work with them.
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	->
	 * 	->
	 **************************************************************************/
	private void setBorderOnPanel(JPanel panel, String title){

		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLoweredBevelBorder(),
				title)
				);
	}


	private JMenuItem[][] setupSubMenuItems(String[][] menuItemNames, JMenu[] Jmenu  ){
		
		int MAX_NUM_OF_ITEM = 10; // this number because in a 
		//dimentoon array it is [colums][rows] we cannot predetermine how many rows(menu items there will be)
		// however we can determine how many colums there will be , this is where theis MAX 
		//num comes from so we set a standard DO NOT EXCEED marker.		
		
		String tempNames; // optimize code so its more we only need to init this variable once to reuse.
	
		if(Jmenu.length != menuItemNames.length) { 
			throw new RuntimeException("Sizes are not right!"); 
		}; // throws if the colums do not match up with the amount of menu parent labels
		
		JMenuItem[][] retArr = new JMenuItem[Jmenu.length][MAX_NUM_OF_ITEM]; 
		// instatiates a empty 2D array to be returned with correct [columns][ MAX NUM OF ITEMS]

		for(int column = 0; column < menuItemNames.length ; column++ ){ 
			// access the column
			
			for(int row = 0; row < menuItemNames[column].length ; row ++){
				// access the rows of that column
				
				tempNames = menuItemNames[column][row]; // uses this variable once
				
				retArr[column][row] = new JMenuItem(tempNames.replace("#", ""));
				// removes the symbol before adding the menuItem to the Jbar		
				retArr[column][row].setMnemonic(tempNames.charAt(0)); 
				// adds a nmonic for that menuItem
				
				Jmenu[column].add(retArr[column][row]); // add's the modified menuitem to the Jmenu
				
				if(tempNames.contains("#")){ // self defined symbol to add a seperator to the menu after this name

					Jmenu[column].addSeparator();// adds a seperator					
				}	
				
			}
		
		}
		return retArr; // returns the 2D modified array to work with .
	}
	/**************************************************************************
	 * Function: Helper method send message to terminalk
	 *  
	 * Description:
	 * 	-> This method takes input and appends it to the terminal screen
	 * ASSUMPTIONS:
	 * 	-> None
	 * PRECONDITIONS:
	 * 	-> None
	 * 	-> None
	 * POSTCONDITIONS:
	 * 	-> text is appended to the terminal panel of the user interface
	 * 	->
	 **************************************************************************/
	public void sendMsgToTerminal(String msg){
		this.terminalDisp.append("\n" + msg);
	}
	
	public static void main(String[] args){
		UserInterface ui = new UserInterface();
		for(int i =0; i <500; i++){
			ui.sendMsgToTerminal(i + "from main yolo\n");
		}
		
	}
}


