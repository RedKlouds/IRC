
public class ExeceptionTest {
	
	public static void throwSomething(int n)throws IllegalArgumentException{
		if(n < 10){
			throw new IllegalArgumentException("FUCK THIS NUMBER!");
		}
	}
	
	public static void main(String[] args){
		
		
		for(int i = 20; i > 5; i--){
			try{
				throwSomething(i);
				System.out.println(i);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
}
