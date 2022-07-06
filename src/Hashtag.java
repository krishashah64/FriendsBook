import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Hashtag {
	public static void getHashTag(String postLine) {

		String tag = "";
		int count = 1;
		
		ArrayList<String> hashtags = new ArrayList<String>();
		
		final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false";
		Connection conn1= null;
		Statement stm = null;
		ResultSet rs = null;
			
		try 
		{
			// connect to the database
			conn1= DriverManager.getConnection(url, "shahk5504", "2095163"); 
			stm = conn1.createStatement();
			
			conn1.setAutoCommit(false);
		
			int indexHash = postLine.indexOf("#");
			while(indexHash >= 0)
			{
				int indexEnd = postLine.indexOf(" ", indexHash+1);
				
				if(indexEnd > 0) {
					tag = postLine.substring(indexHash, indexEnd);
					
					indexHash = postLine.indexOf("#", indexEnd);
				}
				else
				{
					tag = postLine.substring(indexHash).toUpperCase();
					
					break;
				}
				
				rs = stm.executeQuery("Select * from hashtag");
				
				while(rs.next()){
					int frequency = rs.getInt("freq") + 1;
					if(rs.getString("word").equals(tag)) 
					{
						//update frequency
						
						int r = stm.executeUpdate("Update hashtag set freq = '" + frequency + "' where word = '" + tag +"'");
						break;
					}
					else 
					{
						int r = stm.executeUpdate("Insert into hashtag values('" 
								+ tag + "', '" + count + "')");
						break;
					}
				}
				
			}
			
			// commit the statements
		
			conn1.commit();
			conn1.setAutoCommit(true);
			
			// successful message
			System.out.println("******** The hashtag is created Successfully *********");
			
			for(String hashtag: hashtags) 
			{
				System.out.println(hashtag);
			}
				
		}
		catch(SQLException e)
		{
			System.out.println(e);
			System.out.println("Hashtag creation failed");
			e.printStackTrace();
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
	
	public static void showTrendingHashtags() {
		
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
				rs = stm.executeQuery("Select * from hashtag Order by freq DESC LIMIT 0, 3");
				
		
				while(rs.next()) 
				{
					System.out.println("Hashtags - " + rs.getString("word"));
						
				}	
				
				System.out.println("Enter the hashtag whose posts you want to see - ");
				String hashtagInput = input.next();
				
				showPostsHavingHashtags(hashtagInput);
				
			}
			
			catch(SQLException e)
			{
				System.out.println(e);
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
		
		public static void showPostsHavingHashtags(String hashtagInput) 
		{
			
			final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false"; 
			Connection conn1= null; // contains functions used to connect
			Statement stm = null; // query statement
			ResultSet rs1 = null; // result from database 
			ResultSet rs2 = null;
			
			try 
			{
			
				// connect to the database
				conn1= DriverManager.getConnection(url, "shahk5504", "2095163");
				stm = conn1.createStatement();
				
					rs2 = stm.executeQuery("select * from post p where EXISTS (select 1 from Friend f where p.userid != '" + OnlineSystem.theLoginAccount.id + "' and ((f.id1 = '" + OnlineSystem.theLoginAccount.id + "' and f.id2 = p.userid) or (f.id2 = '" + OnlineSystem.theLoginAccount.id + "' and f.id1 = p.userid)) and f.status = 'accepted') UNION select * from post where userid = '" + OnlineSystem.theLoginAccount.id + "'");
				
					while(rs2.next()) {
						if(rs2.getString("content").contains(hashtagInput)) {
							System.out.println(rs2.getString("content") + "- post by " + rs2.getString("userid") );
							
						}
					}
					
					OnlineSystem.theLoginAccount.welcome(false);
				
			}
			
			catch(SQLException e)
			{
				System.out.println(e);
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
					e.printStackTrace();
				}

			}
			
		}
			
	}
	
