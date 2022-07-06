import java.sql.*;
import java.util.*;


public class Updates 
{
	static int[] updatesList = new int[3];
	static int[] postIdList = new int[3];
	static int[] result = new int[6];
		
	public int id;
	public String userid;
	public String type; // -1 -> post, 1 -> comment, 2 -> profile update
	public int postID;
	
	public Updates(int i, String uid, String t, int pid) 
	{
		id = i;
		userid = uid;
		type = t;
		postID = pid;
	}
	
	public static void create(int pid, String t) 
	{
		int id = 0;
		String userid = "";
		String type = t;
		int postID = 0;
		
		userid = OnlineSystem.theLoginAccount.id;
		
		if(type.equals("-1") || type.equals("1")) {
			postID = pid;
		}
		else if(type.equals("2")) {
			postID = -2;
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
			
			int r = stm.executeUpdate("Insert into Updates values('" 
					+ id + "', '" + userid + "', '" + type + "', '" + postID + "')");
			// commit the statements
			
			conn1.commit();
			conn1.setAutoCommit(true);

		}
		catch(SQLException e)
		{
			System.out.println(e);
			System.out.println("Update creation failed");
			e.printStackTrace(); // print out exception message or information
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
	
	public static int[] show() {
		
		Scanner input = new Scanner(System.in);
				
		System.out.println("Updates from your Friends - ");
		
			final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false"; 
			Connection conn1= null; // contains functions used to connect
			Statement stm = null; // query statement
			ResultSet rs = null; // result from database 
			ResultSet rs1 = null;
			
			try 
			{
				// connect to the database
				conn1= DriverManager.getConnection(url, "shahk5504", "2095163");
				stm = conn1.createStatement();
				
				rs = stm.executeQuery("select * from updates u where u.userID != '" + OnlineSystem.theLoginAccount.id + "' and EXISTS (select 1 from Friend f where (f.id1 = '" + OnlineSystem.theLoginAccount.id + "' or f.id2 = '" + OnlineSystem.theLoginAccount.id + "' ) and f.status = 'accepted') order by u.id DESC LIMIT 0,3");
				int i = 0;
				
	            while(rs.next())
	            {
	            	
	            	updatesList[i] = rs.getInt("id");
					postIdList[i] = rs.getInt("postID");
					
	            	if(!rs.getString("type").equals("2"))
	            	{
	            		String viewpostContent = Post.displayContent(rs.getInt("postID"));
	            		System.out.println(i + ". " + "New post from " + rs.getString("userID") + " - " + viewpostContent);
	                   
	            	}
	            	else
	            	{
	            		
	            		System.out.println(i + ". " + rs.getString("userID") + " updated their profile" );
	            		
	            	}
	            	
	            	i++;
	             	    	          
	            }
	            
	            System.arraycopy(updatesList, 0, result, 0, 2);  
	            System.arraycopy(postIdList, 0, result, 3, 3);  
	                        
	            
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
			return result;
		}
	
	
	public int getId() {
	    return id;
	}
	
	public void setId(int id) {
	    this.id = id;
	}
	
	public String getUserID() {
	    return userid;
	}
	
	public void setUserID(String userid) {
	    this.userid = userid;
	}
	
	public String getType() {
	    return type;
	}
	
	public void setType(String type) {
	    this.type = type;
	}
	
	public int getpostID() {
	    return postID;
	}
	
	public void setParent(int postID) {
	    this.postID = postID;
	}
	
}

