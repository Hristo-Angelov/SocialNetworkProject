package socialnetwork.main;

import database.PostDAOImpl;
import exceptions.InvalidInputException;

public class Demo {

	public static void main(String[] args) {
		// 1. Database
		DataBase database = new DataBase();
		try {
			// 2. Users
			User jim = new User("Jim", "strongPassword", "qwe@gmail.com", database);
			User john = new User("John", "streongerPassword", "asd@gmail.com", database);
			User james = new User("James", "streongestPassword", "zxc@gmail.com", database);
			
			// 3. Follow/Unfollow
		
			System.out.println(james + " follows " + jim);
			james.sendFollowRequest(jim);
			System.out.println(jim + " Followers: " + jim.getFollowers().size());
			System.out.println(james + " Following: " + james.getFollowedUsers().size());
			
			System.out.println();
			System.out.println(james + " unfollows " + jim);
			james.unfollow(jim);
			System.out.println(jim + " Followers: " + jim.getFollowers().size());
			System.out.println(james + " Following: " + james.getFollowedUsers().size());
			System.out.println();
			
			System.out.println("John goes private.");
			john.setPrivate(true);
			System.out.println("Jim requests to follow John.");
			jim.sendFollowRequest(john);
			System.out.println(jim + " Followers: " + jim.getFollowers().size());
			System.out.println(john + " Following: " + john.getFollowedUsers().size());
			
			System.out.println();
			System.out.println("John approves Jim.");
			john.approveFollower(jim);
			System.out.println(jim + " Followers: " + jim.getFollowers().size());
			System.out.println(john + " Following: " + john.getFollowedUsers().size());
			System.out.println();
			
			// 4. Posts
			Post jimPost1 = new Post("First tweet! #firstTweet2016 #greatConsoleDemo #greatConsoleDemo", jim, database);
			jim.addPost(jimPost1);
			john.likePost(jimPost1);
			james.likePost(jimPost1);
			Post jamesPost1 = new Post("@Jim Congrats! #greatPost #greatConsoleDemo", james, database);
			james.reply(jimPost1, jamesPost1);
			System.out.println(jim.getMyPosts().get(0));
			System.out.println(jamesPost1);
			Post newPost = new Post("Az sym nov post", jim, database);
			jim.addPost(newPost);
			
			
			// 5. Trending hashtags
			System.out.println("\nTrending Hashtags:");
			System.out.println(database.getTrendingHashtags());
			
			// 6. Removing posts
			jim.deletePost(jimPost1);
			System.out.println("Trending Hashtags:");
			System.out.println(database.getTrendingHashtags());
			
			// 7.Retweeting posts
			jim.retweet(jamesPost1, new Post("Eto retweetnah tozi post", jim, database));
			System.out.println("Jim's posts: ");
			System.out.println(jim.getMyPosts().get(jim.getMyPosts().size()-1));
			System.out.println(jim.getMyPosts().get(0));
			database.addUser(james);
			
			james.setUserId(1);
			database.insertPost(jamesPost1);
		
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
	}
}
