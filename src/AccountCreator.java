import java.util.Scanner;
import java.sql.*;

public class AccountCreator
{
	public static void register()
	{
		//declare the variables
		String loginID = "";
		String password = "";
		String name = "";
		String school = "";
		
		Scanner input = new Scanner(System.in);
		boolean loginRequiredLength = false;
		boolean loginRequiredLetter = false;
		boolean loginRequiredDigit = false;
		boolean loginRequiredSpecialChar = false;
		
		while(!loginRequiredLength || !loginRequiredLetter || !loginRequiredDigit || !loginRequiredSpecialChar)
		{
			System.out.println("Please enter your new login id for registration");
			loginID = input.next();
			
			// to check if login id length is of 3 - 10 characters 
			if(loginID.length() >= 3 && loginID.length() <= 10 ) 
			{
				loginRequiredLength = true;
			}
			
			for(int i = 0; i < loginID.length(); i++)
			{
				char c = loginID.charAt(i); //get each character in the string
				
				// to check if the login id satisfies special character requirement
				if(c == '#' || c == '?' || c == '!' || c == '*')
				{
					loginRequiredSpecialChar = true;
				}
				
				// to check if the login id satisfies requirement of a digit using ascii code
				int ascii = (int)c;
				
				if(ascii >= 48 && ascii <= 57)
				{
					loginRequiredDigit = true;
				}
				
				// to check if the login id satisfies requirement of a letter using ascii code
				if((ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122))
				{
					loginRequiredLetter = true;
				}
			}
		}
				
		boolean passwordRequired = false;
		
		while(!passwordRequired)
		{
			System.out.println("Please enter your new password for registration");
			password = input.next();
			
			// password should not be same as loginID
			if (!password.contains(loginID)) 
			{
				passwordRequired = true;
			}
		}
		
		System.out.println("Please enter your name");
		name = input.next();
		
		System.out.println("Please enter your school name");
		school = input.next();
		
		final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false"; 
		Connection conn1= null; // contains functions used to connect
		Statement stm = null; // query statement

		try 
		{
			// connect to the database
			conn1= DriverManager.getConnection(url, "shahk5504", "2095163"); 
			stm = conn1.createStatement();
			
			conn1.setAutoCommit(false);
			
			int r = stm.executeUpdate("Insert into user values('" 
					+ loginID + "', '" + password + "', '" + name + "', '"
					+ school + "')");
			
			// commit the statements
			
			conn1.commit();
			conn1.setAutoCommit(true);
			
			// successful message
			System.out.println("******** The USER account is created Successfully *********");
			
			FriendsBook.main(null);
		}
		catch(SQLException e)
		{
			System.out.println("USER ACCOUNT creation failed");
			FriendsBook.main(null);
		}
		finally 
		{
			// close the database
			try
			{
				conn1.close();
				stm.close();
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

		}
		
		input.close();
	}

	public static void updateProfile() 
	{
		Scanner input1 = new Scanner(System.in);
		String name = "";
		String school = "";
		
		System.out.println("Enter new name - ");
		name = input1.next();
		
		System.out.println("Enter new school - ");
		school = input1.next();
		
		final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false"; 
		Connection conn1= null; // contains functions used to connect
		Statement stm = null; // query statement
		
		try 
		{
			
			conn1 = DriverManager.getConnection(url, "shahk5504", "2095163");
			stm = conn1.createStatement();
			
			conn1.setAutoCommit(false);
			
			int r = stm.executeUpdate("Update user set name = '" + name + "', school = '" + school + "'  where id = '" + OnlineSystem.theLoginAccount.id + "'");
			
			conn1.commit();
			conn1.setAutoCommit(true);
			
			System.out.println("User account updated successfully");
			
			//since this is not a post, so postID attr in updates table will have '-2' value
			Updates.create(-2, "2");
			
			OnlineSystem.theLoginAccount.welcome(false);
			
		}
		
		catch(SQLException e) 
		{
			System.out.println(e);
			System.out.println("USER ACCOUNT update failed");
			FriendsBook.main(null);
		}
		finally 
		{
			// close the database
			try
			{
				conn1.close();
				stm.close();
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

		}
					
		input1.close();			
		
	}
}
