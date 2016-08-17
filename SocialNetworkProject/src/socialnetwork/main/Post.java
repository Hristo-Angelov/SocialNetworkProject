package socialnetwork.main;

import java.time.LocalDate;
import java.util.*;

import exceptions.InvalidArgumentException;

public class Post {
	private String text;
	private User poster;
	private LocalDate dateWhenPosted;
	private Post question;
	private List<Post>answers = new ArrayList<Post>();
	private List<Post>likes = new ArrayList<Post>();
	
	
	
	
	public Post(String text, User poster) throws InvalidArgumentException {
		
		this.text = User.validateText(text);
		if(poster != null){
			this.poster = poster;
		}else{
			throw new InvalidArgumentException("Not valid poster");
		}
		this.dateWhenPosted = dateWhenPosted.now();
		
	}
	
	public void printPost(){
		//TO print sth.
	}
	
	

}
