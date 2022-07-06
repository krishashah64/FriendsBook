
import java.util.*;

public class User {
	
	static int[] viewUpdates = new int[6];
		
	public String id;
	public String password;
	public String name;
	public String school;
	public String loginId;
	
	 public User(String d, String p, String n, String s)
	    {   
	        id = d;
	        password = p;
	        name = n;
	        school = s;
	        
	    }
	 
	 public void welcome(Boolean showUpdates)
	    {
	    	if(showUpdates) {
	    		viewUpdates = Updates.show();
	    	}
	    	
	    	int notificationCount = Notification.getNotificationCount();
	    
	    	Scanner input = new Scanner(System.in);
	    	String selection = "";
	    	System.out.println();
	    	System.out.println("MENU - ");
	    	System.out.println();
			System.out.println("1: Select an Update and Post");
			System.out.println("2: Check Notifications (" + notificationCount + " new)");
			System.out.println("3: Create a new post");
			System.out.println("4: Friends");
			System.out.println("5: Update Profile");
			System.out.println("6: Send a message");
			System.out.println("7: Send a friend request");
			System.out.println("8: See hashtag in trend");
			
			selection = input.next();
			
			if(selection.equals("1")) 
			{
				System.out.println("");
				// select an update or post
				
				viewUpdates = Updates.show();
				
				 System.out.println("Enter the update number on which you want to comment - ");
		            String option = input.next();
		            
		            if(option.equals("0") && (viewUpdates[3]) > 0)
		            {
		            	Post.create("1", viewUpdates[0]);
		            }
		            else if (option.equals("1") && (viewUpdates[4]) > 0) 
		            {
		            	Post.create("1", viewUpdates[1]);
		            }
		            else if (option.equals("2") && (viewUpdates[5]) > 0) 
		            {	
		            	Post.create("1", viewUpdates[2]);
		            }
		            else 
		            {
		            	System.out.println("You cannot comment on Profile Update. Please enter Post number you wish to comment - ");
		            	System.out.println();
		            	welcome(false);
		            }
			}
			
			else if(selection.equals("2")) 
			{
				// View Notifications
				Notification.show();
			
			}
			
			else if (selection.equals("3"))
			{
				//Create a new post
				Post.create("-1", -2);
							
			}

			else if (selection.equals("4"))
			{
				//Friends
				MyFriends.display();					
			}
			
			else if (selection.equals("5"))
			{
				//Update Profile
				AccountCreator.updateProfile();
							
			}
			
			else if (selection.equals("6"))
			{
				//Send a message
				Friend.sendMessage();
			}
			
			else if (selection.equals("7"))
			{
				//Send a friend request
				Friend.sendRequest();
//				
			}
			
			else if (selection.equals("8"))
			{
				//See hashtag in trend
				Hashtag.showTrendingHashtags();
			}
			
			else 
			{
				welcome(false);
			}

		    input.close();
		
		  }
	 
	//get methods and set methods
	   	public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPsw(String password) {
	        this.password = password;
	    }
	    
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }
	    
	    public String getSchool() {
	        return school;
	    }

	    public void setSchool(String school) {
	        this.school = school;
	    }
	    
	    public String gettheLoginAccountId() {
	        return loginId;
	    }

	    public void settheLoginAccountId(String logid) {
	        this.loginId = logid;
	    }
	    
	   
}
