package database;

import java.sql.Connection;
import java.util.*;

import exceptions.InvalidInputException;
import socialnetwork.main.*;

public interface PostDAO {

//	public void insertPost(Post post);
//
//	public Post selectPost(int postId);
//	
//	public List<Post> getUserPosts(User user);
//
//	public Set<Post> getNewsfeed(User user);
//	
//	public TreeSet<User> getLikes(Post post);

	public Post selectPost(int postId, Connection connection);

	public Set<User> getLikes(Post p, Connection connection);

	public void deletePost(Post post, Connection connection);

	public List<Post> getUserPosts(User user, Connection connection);



	void mapHashtagsToPost(Hashtag hashtag, Post post, Connection connection);

	void insertPost(Post post, Connection connection);

	void findHashtags(Post post, Connection connection) throws InvalidInputException;

	
	

 
	

}
