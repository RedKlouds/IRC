import junit.framework.Assert;
import junit.framework.TestCase;

public class IRCTest extends TestCase {

	private static UserInterface ui;
	public IRCTest(String name){
		super(name);
	}
	
	public void setUp(){
		ui = null;
	}
	
	public void testUnique() throws InterruptedException {
		//we expect to have an interrupted error from the collision of two threads
		//both threads call UI object to make 2 UI clients
		
		Thread threadOne = new Thread(new UITestRunnable());
		Thread threadTwo = new Thread(new UITestRunnable());
		//make the threads to concurrently make instances of the UI Interface
		threadOne.start();
		//fire up the two threads to do their thing(make an instance of the singleton
		// ui object)
		threadTwo.start();
		
		//join the threads to the main thread, such that we allow to finish the program
		//IFF(only when ) they finish
		threadOne.join();
		threadTwo.join();
	}
	
	private static class UITestRunnable implements Runnable{
		public void run(){
			//get a reference to the singleton
			UserInterface _ui = UserInterface.getInstance();
			//protect the singleton memeber variable from 
			//multithreading access
			//by schychonizing the threads
			synchronized(IRCTest.class){
				//if the local reference is null
				if(_ui == null){
					ui = _ui; //then give the intatiated object a reference to this object
				}
				
				//asset to make sure that one and only one
				//instance of the singleton UI is equal to this new inistaitated
				//since Java uses reference for everyhthing the two addresses of the objects
				//so reference the same things
				Assert.assertEquals(false, _ui == ui);
			}
			
		}
	}
	
	

}
