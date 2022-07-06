import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Friend {
	
	public int id;
	public String id1; // request sender
	public String id2; // request receiver
	public String status; //pending, accepted, rejected
	
	public Friend(int d, String d1, String d2, String s)
	{
		id = d;
		id1 = d1;
		id2 = d2;
		status = s;
	}
	
	public static void sendRequest() 
	{
		
		int id = 0;
		String id1 = OnlineSystem.theLoginAccount.id;
		String id2 = "";
		String status = "pending";
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("To whom do you want to send the friend request?");
		id2 = input.nextLine();
		
			final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false"; 
			Connection conn1= null; // contains functions used to connect
			Statement stm = null; // query statement
			ResultSet rs = null; // result from database 
				
			try 
			{
			
				// connect to the database
				conn1= DriverManager.getConnection(url, "shahk5504", "2095163");
				stm = conn1.createStatement();
				rs = stm.executeQuery("SELECT count(*) as rs_count from (SELECT * FROM User where id = '" + id1 + "' UNION SELECT * FROM User where id = '" + id2 + "') as tableName");
	
		         // to verify if the ID to which friend request is to be sent exists in user database or not
				rs.next();
			
	            if(rs.getInt("rs_count") == 2  && (!id1.equals(id2)))
	            {
	
	            	rs = stm.executeQuery("Select * from friend where id1 = '" + id1 + "' and id2 = '" + id2 + "' and status != 'rejected' UNION Select * from friend where id1 = '" + id2 + "' and id2 = '" + id1 + "' and status != 'rejected'");
	            	
	            	rs.next();
	            	
	            	if(!rs.first()) {
	            		System.out.println("You sent a friend Request to " + id2);
		            	
		            	conn1.setAutoCommit(false);	
		            	
		            	int r = stm.executeUpdate("Insert into friend values('" 
		    					+ id + "', '" + id1 + "', '" + id2 + "', '" + status + "')");
		    			// commit the statements
		    			
		    			conn1.commit();
		    			conn1.setAutoCommit(true);
		    			
		    			Notification.create(id2, id1, "1", "unread");
		
		               // show the login account welcome page
		    			OnlineSystem.theLoginAccount.welcome(false);
	            	}
	            	else
	     			{
	     	            System.out.println("********* Friend request NOT Sent. Try Again **********");
	     	           OnlineSystem.theLoginAccount.welcome(false);
	     	        }
	                
	            }
	            else
     			{
     	            System.out.println("********* Friend request NOT Sent. Try Again **********");
     	           OnlineSystem.theLoginAccount.welcome(false);
     	        }
	       
		}
		
		catch(SQLException e)
		{
			System.out.println(e);
			System.out.println("Friend request NOT Sent");
			OnlineSystem.theLoginAccount.welcome(false);
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
				OnlineSystem.theLoginAccount.welcome(false);
			}

		}
					
		input.close();
		}

	
	public static void sendMessage() {
		
		Scanner input = new Scanner(System.in);
		
		String selection = "";
		
		System.out.println("To whom do you want to send the message");
		selection = input.nextLine();
		
		Notification.create(selection, OnlineSystem.theLoginAccount.id, "2", "unread");
	}

	public static void processRequest(Notification newnotification) {
		
		String request = "";
       
		Scanner input = new Scanner(System.in);
         
		System.out.println("You have following 2 options to process this Friend request - ");
    	System.out.println("1. Accept");
    	System.out.println("2. Deny");
    	
    	String str = input.next();
    	
    	if(str.equals("1")) {
    		//accept request
    		
    		request = "accepted";
    		
    	}
    	else if (str.equals("2")) {
    		//deny request
    		
    		request = "rejected";
    		
    	}
    	
    	else {
    		System.out.println("Please Enter either 1 or 2.");
    	}
    	
    	final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false"; 
		Connection conn1= null; // contains functions used to connect
		Statement stm = null; // query statement
		
		try 
		{
		
			// connect to the database
			conn1= DriverManager.getConnection(url, "shahk5504", "2095163");
			stm = conn1.createStatement();
			
			conn1.setAutoCommit(false);	
        	
        	int r = stm.executeUpdate("Update Friend set status = '" + request + "' where id1 = '"+ newnotification.userid1 + "' and id2 = '" + newnotification.userid2 + "'");
        	
        	int r1 = stm.executeUpdate("Update Notification set status = 'read' where userid1 = '"+ newnotification.userid1 + "' and userid2 = '" + newnotification.userid2 + "'");
			
			conn1.commit();
			conn1.setAutoCommit(true);
			
			System.out.println("FR processed successfully");
			
			OnlineSystem.theLoginAccount.welcome(false);
			
		}
		
		catch(SQLException e)
		{
			System.out.println(e);
			System.out.println("Friend request NOT Processed");
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
    	
	}
	
	
	public static void viewMessages(Notification newNotification) 
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Message History between you and " + newNotification.userid1);
		
		final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false"; 
		Connection conn1= null; // contains functions used to connect
		Statement stm = null; // query statement 
		ResultSet rs2 = null;
		try 
		{
			
			// connect to the database
			conn1= DriverManager.getConnection(url, "shahk5504", "2095163");
			stm = conn1.createStatement();
			
			rs2 = stm.executeQuery("Select * from Notification where type = '2' and ((userid1 = '" + newNotification.userid1 + "' and userid2 = '" + newNotification.userid2 + "') OR (userid2 = '" + newNotification.userid1 + "' and userid1 = '" + newNotification.userid2 + "'))");
			
        	while(rs2.next()) 
        	{
        		System.out.println(rs2.getString("userid1") + " - " + rs2.getString("message"));
        	}
        	
        	
        	int r1 = stm.executeUpdate("Update Notification set status = 'read' where userid1 = '"+ newNotification.userid1 + "' and userid2 = '" + newNotification.userid2 + "'");
        	
        	System.out.println("Do you want to reply to '" + newNotification.userid1 + "'?");
        	String selectedOption = input.next();
        	
        	if(selectedOption.equals("yes") || selectedOption.equals("Yes")) 
        	{
        		Notification.create(newNotification.userid1, newNotification.userid2, "2", "unread");
        	}
        	else if(selectedOption.equals("no") || selectedOption.equals("No"))
        	{
        		OnlineSystem.theLoginAccount.welcome(false);
        	}
        	else {
        		viewMessages(newNotification);
        	}
        	
		}
		
		catch(SQLException e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		finally 
		{
			// close the database
			try
			{
				conn1.close();
				stm.close();
				rs2.close();
				
			}
			catch(Exception e)
			{
				System.out.println(e);
				e.printStackTrace();
			}

		}
		
	}

}
