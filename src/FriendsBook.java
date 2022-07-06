import java.util.*;
import java.util.ArrayList;

public class FriendsBook {
	
	public ArrayList<Notification> messages = new ArrayList<Notification>();
	public ArrayList<Notification> friendRequest = new ArrayList<Notification>();

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		String sel = "";
		
		while(!sel.equals("x"))
		{
			System.out.println("Welcome to FriendsBook!");
			System.out.println("1: Register");			
			System.out.println("2: Login");			
			System.out.println("x: Leave");			
			
			//user enters an option
			sel = input.next();
			
			if(sel.equals("1")) 
			{
				// register
				AccountCreator.register();
			}
			else if (sel.equals("2"))
			{
				//login
				new OnlineSystem().login();
				
			}
			else {
				main(null);
			}
		}
		
		input.close();
		
	}
	
}
