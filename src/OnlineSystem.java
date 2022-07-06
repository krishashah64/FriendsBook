import java.util.*;
import java.sql.*;

public class OnlineSystem {
	
	protected static User theLoginAccount;
	
	static int[] viewUpdates = new int[6];
	
    public OnlineSystem() {
		// TODO Auto-generated constructor stub
    	theLoginAccount = new User(null, null, null, null);
	}

	public void login()
    {
        //we need id and password
        Scanner input = new Scanner(System.in);
        String id = "";
        String password = "";
        
        //get the login info.
        System.out.println("Please enter your Login ID");
        id = input.next();
        System.out.println("Please enter your password");
        password = input.next();        
        
        
        final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false"; 
		Connection conn1= null; // contains functions used to connect
		Statement stm = null; // query statement
		ResultSet rs = null; // result from database 
			
		try 
		{
			// connect to the database
			conn1= DriverManager.getConnection(url, "shahk5504", "2095163");
			stm = conn1.createStatement();
			rs = stm.executeQuery("Select * from User where id = '" + id + "' and password = '" + password + "'");
			
	         // to verify login id and password
			 rs.next();
		
            if(rs.first())
            {
                    System.out.println("Welcome! " + rs.getString("name"));
                	
                	theLoginAccount.id = rs.getString("id");
                	theLoginAccount.password = rs.getString("password");
                	theLoginAccount.name = rs.getString("name");
                	theLoginAccount.school = rs.getString("school");
                	
                   // show the welcome page
                    theLoginAccount.welcome(true);
                
            }	
            else
			{
	            System.out.println("********* The login / password is incorrect **********");
	            FriendsBook.main(null);
	        }
		}
		catch(SQLException e)
		{
			System.out.println("User login failed");
		}
		finally 
		{
			// close the database
			try
			{
				conn1.close();
				stm.close();
				rs.close();
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

		}
					
		input.close();
             
    }
    
}
