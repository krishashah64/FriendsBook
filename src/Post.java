import java.sql.*;
import java.util.*;

public class Post
{
	public int id;
	public String userid;
	public String content;
	public String datetime;
	public String type; //type = -1, type = 1 -> comment
	public int parent; // for comment -> parent = same post id, for post -> parent = -2
		
	public Post(int pid, String uid, String c, String dt, String t, int p)
    {   
        id = pid;
        userid = uid;
        content = c;
        datetime = dt;
        type = t;
        parent = p; 
    }
	
	public static void create(String t, Integer p) 
	{
		int id = 0;
		String userid = "";
		String content = "";
		String datetime = "";
		String type = t;
		int parent = p; 
		Scanner input = new Scanner(System.in);
		
        userid = OnlineSystem.theLoginAccount.id;  
        
        while(content == "") {
        	System.out.println("Please enter content of your post");
            content = input.nextLine();
        }
        
        datetime = DateAndTime.DateTime();   
        
		final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false";
		Connection conn1= null;
		Statement stm = null;
			
		try 
		{
			// connect to the database
			conn1= DriverManager.getConnection(url, "shahk5504", "2095163"); 
			stm = conn1.createStatement();
			
			conn1.setAutoCommit(false);
			
			int r = stm.executeUpdate("Insert into post values('" 
					+ id + "', '" + userid + "', '" + content + "', '"
					+ datetime + "', '" + type + "', '" + parent + "')", Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs1 = stm.getGeneratedKeys();
			rs1.next();
			int rs = rs1.getInt(1);
						
			// commit the statements
		
			conn1.commit();
			conn1.setAutoCommit(true);
			
			// successful message
			System.out.println("******** The post is created Successfully *********");
		
			Updates.create(rs, "-1");
			
			Hashtag.getHashTag(content);
			
		}
		catch(SQLException e)
		{
			System.out.println(e);
			System.out.println("Post creation failed");
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
		
		OnlineSystem.theLoginAccount.welcome(false);
					
		input.close();
	}
	
	public static String displayContent(int postId) {
		
		String postContent = "";
		
		final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false"; 
		Connection conn1= null; // contains functions used to connect
		Statement stm = null; // query statement
		ResultSet rs = null; // result from database 
		
		try 
		{
		
			// connect to the database
			conn1= DriverManager.getConnection(url, "shahk5504", "2095163");
			stm = conn1.createStatement();
			rs = stm.executeQuery("Select * from post where id = '" + postId + "'");
			rs.next();
			postContent = rs.getString("content");
			
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
		
		return postContent;
		
	}

	//get methods and set methods
   	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content= content;
    }
    
    public String getDateTime() {
        return datetime;
    }

    public void setDateTime(String datetime) {
        this.datetime = datetime;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }
    
}
