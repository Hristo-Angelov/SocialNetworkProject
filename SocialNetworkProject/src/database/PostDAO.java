package database;

import java.util.*;

import socialnetwork.main.*;

public interface PostDAO {

	public void insertPost(Post post);

	public Post selectPost(int podtId);
	
	public List<Post> getUserPosts(User user);

	public List<Post> getNewsfeed(User user);
	
	public List<User> getLikes(Post post);
	

 
	

}
