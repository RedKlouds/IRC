

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class nodeTest<TBA> {
	String name;
	TBA data;
	Scanner scanner;
	public nodeTest(String name, TBA data){
		this.name = name;
		this.data = data;
	}
	
	public nodeTest(){
		this.name = setName();
	}
	public nodeTest(String name){
		this.name = name;
	}
	
	
	
	
	private String setName(){
		String retName = "";
		try{
		scanner = new Scanner(new File("C:\\Users\\MugenKlaus\\workspace\\IRC_Project_\\names.txt"));
	
		for(int i =0; i <new Random().nextInt(50); i++){
			retName = scanner.nextLine();

		}
		scanner.close();
	}catch(FileNotFoundException e){
		e.printStackTrace();
	}
		
		return retName;
		
		
	}
	
	public String getName(){ return this.name;}
	
	public String toString(){
		return this.name;
	}
	
	
	public static void main(String[] args){
		nodeTest<String> t = new nodeTest<String>();
		
		System.out.println(t);
	}
}
