package database;

import java.sql.Connection;
import java.util.*;

import socialnetwork.main.*;

public interface PostDAO {

	public void insertPost(Post post);

	public Post selectPost(int postId);
	
	public List<Post> getUserPosts(User user);

	public Set<Post> getNewsfeed(User user);
	
	public TreeSet<User> getLikes(Post post);

	public Post selectPost(int postId, Connection connection);

	
	

 
	

}
