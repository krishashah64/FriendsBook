import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MyFriends{
	
	public static void display() {
		ArrayList<Friend> myFriendsList = new ArrayList<Friend>(); 
		Scanner input = new Scanner(System.in);
			
			final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false"; 
			Connection conn1= null; // contains functions used to connect
			Statement stm = null; // query statement
			ResultSet rs = null; // result from database 
			ResultSet rs1 = null;
			ResultSet rs2 = null;
			
			try 
			{
			
				// connect to the database
				conn1= DriverManager.getConnection(url, "shahk5504", "2095163");
				stm = conn1.createStatement();
				
				rs = stm.executeQuery("Select * from Friend where status = 'accepted' and (id1 = '" + OnlineSystem.theLoginAccount.id + "' or id2 = '" + OnlineSystem.theLoginAccount.id + "')");
				
				while(rs.next()) 
				{
					Friend newOne = new Friend(rs.getInt("id"), rs.getString("id1"), rs.getString("id2"), rs.getString("status"));
					
					if(rs.getString("id1").equals(OnlineSystem.theLoginAccount.id)) {
						System.out.println(rs.getString("id2"));
						myFriendsList.add(newOne);
						
					}
					else {
						System.out.println(rs.getString("id1"));
						myFriendsList.add(newOne);
					}	
					
				}

				String option = "";
				
				option = getFriendIDInput();
				
				rs1 = stm.executeQuery("Select * from User where id = '" + option + "'");
				if (rs1.first()) {
					for(int j = 0;  j < myFriendsList.size(); j++) {
						if((myFriendsList.get(j).id1.equals(rs1.getString("id"))) || (myFriendsList.get(j).id2.equals(rs1.getString("id")))) {
							System.out.println("Name - " + rs1.getString("name"));
							System.out.println("School - " + rs1.getString("School"));
							MyFriends.viewPost(rs1.getString("id"));
							break;
						}					
						
					}
				}
				else {
					System.out.println("Not a valid entry!");
					OnlineSystem.theLoginAccount.welcome(false);
				}
			
				
				OnlineSystem.theLoginAccount.welcome(false);
				
			}
			
			catch(SQLException e)
			{
				System.out.println(e);
				System.out.println("Friend request NOT Sent");
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
						
		}
			
	
	public static void viewProfile() {
		
	}
	
	public static String getFriendIDInput() {
		Scanner input = new Scanner(System.in);
		
		String option = "";
		System.out.println("Enter the userid whose profile you want to check - ");
		option = input.next();
		return option;
	}
	
	public static void viewPost(String userid) {
		
		System.out.println("posts from " + userid + " - ");
		
		// TODO Auto-generated method stub
		final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false"; 
		Connection conn1= null; // contains functions used to connect
		Statement stm = null; // query statement
		ResultSet rs = null; // result from database 
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		try 
		{
		
			// connect to the database
			conn1= DriverManager.getConnection(url, "shahk5504", "2095163");
			stm = conn1.createStatement();
			
			rs = stm.executeQuery("select * from post where userID = '" + userid + "' and parent < 0");
			while(rs.next()) {
				System.out.println(rs.getString("Content"));
				MyFriends.viewComments(rs.getInt("id"));
			}
		}
		catch(SQLException e)
		{
			System.out.println(e);
			System.out.println("Friend request NOT Sent");
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
					
	}

	private static void viewComments(int postNumber) {
		
		System.out.println();
		
		final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false"; 
		Connection conn1= null; // contains functions used to connect
		Statement stm = null; // query statement
		ResultSet rs = null; // result from database 
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		try 
		{
		
			// connect to the database
			conn1= DriverManager.getConnection(url, "shahk5504", "2095163");
			stm = conn1.createStatement();
			
			rs = stm.executeQuery("select * from post where parent = " + postNumber);
			while(rs.next()) {
				System.out.println("    "+ rs.getString("Content"));
				
			}
		}
		catch(SQLException e)
		{
			System.out.println(e);
			System.out.println("Friend request NOT Sent");
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
		
	}
		
}


