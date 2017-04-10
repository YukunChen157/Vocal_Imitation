
public class CreateMultCharacter {
	public static Thread BuildThread1;
	public static Thread BuildThread2;
	public static Thread BuildThread3;
	public static Thread BuildThread4;
	public static int n = 0;
	
	public static void main(String[] args){;
		BuildThread1 = new Thread(new CreateCharacter());
		BuildThread1.start();
		BuildThread2 = new Thread(new CreateCharacter());
		BuildThread2.start();
		BuildThread3 = new Thread(new CreateCharacter());
		BuildThread3.start();
		BuildThread4 = new Thread(new CreateCharacter());
		BuildThread4.start();
	}
	
	public static int getIndex(){
		n+=1;
		return n;
	}
	
	public static String getLocation(){
		return "Samples2/S"+n;
	}
	
	
}
