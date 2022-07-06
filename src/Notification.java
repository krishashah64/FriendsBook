import java.sql.*;
import java.util.*;
import java.util.ArrayList;

public class Notification {
	
	public static ArrayList<Notification> friendRequest = new ArrayList<Notification>();

	public int id;
	public String userid1; // user who sends friend request / message
	public String userid2; // notification receiver (user who receives friend request / message)
	public String message;
	public String datetime;
	public String type; // 1 --> Friend Request, 2 --> message
	public String status;

	public Notification(int nid, String uid1, String uid2, String m, String d, String t, String s) {
		id = nid;
		userid1 = uid1;
		userid2 = uid2;
		message = m;
		datetime = d;
		type = t;
		status = s;
	}

	public static void create(String id1, String id2, String t, String stat) {

		int id = 0;
		String requestReceiverId = id1;
		String requestSenderId = id2;
		String message = "";
		String datetime = "";
		String type = t;
		String status = stat;

		Scanner input = new Scanner(System.in);

		if (type == "1") {
			// Friend request
			
			message = requestSenderId + " sent you a friend request!";
		} else if (type == "2") {
			// Message
			
			System.out.println("Enter the message : ");
			message = input.nextLine();
		}

		datetime = DateAndTime.DateTime();

		final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false";
		Connection conn1 = null;
		Statement stm = null;
		ResultSet rs = null;

		try {
			// connect to the database
			conn1 = DriverManager.getConnection(url, "shahk5504", "2095163"); // here sahk5504 is login credential
			stm = conn1.createStatement();

			conn1.setAutoCommit(false);
			
			rs = stm.executeQuery("Select * from User where id = '" + requestReceiverId + "'");
				
			if(rs.first()) 
			{
				
				int r = stm.executeUpdate(
						"Insert into notification values('" + id + "', '" + requestSenderId + "', '" + requestReceiverId
								+ "', '" + message + "', '" + datetime + "', '" + type + "', '" + status + "')");
				// commit the statements
	
				conn1.commit();
				conn1.setAutoCommit(true);
	
				// successful message
				if (type == "1") {
					System.out.println("******** Friend Request sent Successfully *********");
				} else if (type == "2") {
					System.out.println("******** Message sent Successfully *********");
					OnlineSystem.theLoginAccount.welcome(false);				
					}
			}
			else {
				System.out.println("The userid you entered is not present in db. Try Again!!");
				OnlineSystem.theLoginAccount.welcome(false);
			}
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("Could not send the friend request/message");
			e.printStackTrace(); // print out exception message or information
		} finally {
			// close the database
			try {
				conn1.close();
				stm.close();

			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
				OnlineSystem.theLoginAccount.welcome(false);
			}

		}
	
		input.close();
	}
	// Check Notification
	public static void show() {

		Scanner input = new Scanner(System.in);
		System.out.println();

		final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false";
		Connection conn1 = null; // contains functions used to connect
		Statement stm = null; // query statement
		ResultSet rs1 = null;
		
		try {

			// connect to the database
			conn1 = DriverManager.getConnection(url, "shahk5504", "2095163");
			stm = conn1.createStatement();
			System.out.println("");

			rs1 = stm.executeQuery(
					"Select * from notification where status = 'unread' and userid2 = '" + OnlineSystem.theLoginAccount.id + "'");

			int i = 0;

			while (rs1.next()) {

				Notification newOne = new Notification(rs1.getInt("id"), rs1.getString("userid1"),
						rs1.getString("userid2"), rs1.getString("message"), rs1.getString("datetime"),
						rs1.getString("type"), rs1.getString("status"));

				friendRequest.add(newOne);

				System.out.println(i + ". " + rs1.getString("userid1") + " - " + rs1.getString("message"));

				i++;

			}

			if (friendRequest.size() > 0) {
				System.out.println("Enter the notification number you want to check - ");
				int option = input.nextInt();

				if (friendRequest.get(option).type.equals("1")) {
					Friend.processRequest(friendRequest.get(option));
				}

				else if (friendRequest.get(option).type.equals("2")) {

					Friend.viewMessages(friendRequest.get(option));
				}

				else {
					System.out.println("Not a valid number. Try again!");
					OnlineSystem.theLoginAccount.welcome(false);
				}

			} else {
				System.out.println("No new notifications this time!");
				System.out.println();
				OnlineSystem.theLoginAccount.welcome(false);
			}

		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			// close the database
			try {
				conn1.close();
				stm.close();
				rs1.close();
				// rs1.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
	
	public static int getNotificationCount() {
		int notificationCount = 0;
		
		final String url = "jdbc:mysql://mis-sql.uhcl.edu/shahk5504?useSSL=false";
		Connection conn1 = null; // contains functions used to connect
		Statement stm = null; // query statement
		ResultSet rs1 = null;
		
		try {

			// connect to the database
			conn1 = DriverManager.getConnection(url, "shahk5504", "2095163");
			stm = conn1.createStatement();
			System.out.println("");

			rs1 = stm.executeQuery(
					"Select * from notification where status = 'unread' and userid2 = '" + OnlineSystem.theLoginAccount.id + "'");

			int i = 0;

			while (rs1.next()) {
				notificationCount += 1;
			}
			
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			// close the database
			try {
				conn1.close();
				stm.close();
				rs1.close();
			
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		return notificationCount;
		
	}

	// get methods and set methods
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserid1() {
		return userid1;
	}

	public void setUserid1(String userid1) {
		this.userid1 = userid1;
	}

	public String getUserid2() {
		return userid2;
	}

	public void setUserid2(String userid2) {
		this.userid2 = userid2;
	}

	public String getMessage() {
		return message;
	}

	public void setContent(String message) {
		this.message = message;
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

	public String getStatus() {
		return status;
	}

	public void setParent(String status) {
		this.status = status;
	}

}
