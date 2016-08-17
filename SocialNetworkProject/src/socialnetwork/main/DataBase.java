package socialnetwork.main;

import java.util.*;

import exceptions.InvalidArgumentException;

public class DataBase {
	
	private Map<Hashtag, ArrayList<Post>> hashTags  =new HashMap<Hashtag, ArrayList<Post>>();
	private Set<User>users = new HashSet<User>();
	
	public void addPost(Post post){
		//ToDo
	}
	
	public Post getPost(String hashTag){
		//ToDo
		return null;
	}
	
	public void updatePublicPosts(){
		//ToDo
	}
	
	public void addUser(User user) throws InvalidArgumentException{
		if(user != null){
			users.add(user);
		}else{
			throw new InvalidArgumentException("Not valid User");
		}
	}
	
	public User getUser(){
		//ToDo
		return null;
	}
	
}
