package socialnetwork.main;

import java.time.LocalDate;
import java.util.*;

import exceptions.InvalidInputException;

public class Post {
	
	// private constants
	private static final int MIN_POST_LENGTH = 1;
	private static final int MAX_POST_LENGTH = 140;
	
	// private fields
	private String text;
	private User poster;
	private LocalDate dateWhenPosted;
	private Post question;
	private List<Post>answers = new ArrayList<Post>();
	private List<Post>likes = new ArrayList<Post>();
	
	
	
	
	public Post(String text, User poster) throws InvalidInputException {
		if (Validator.isValidString(text, MIN_POST_LENGTH, MAX_POST_LENGTH))
		this.text = text;
		if(poster != null){
			this.poster = poster;
		}else{
			throw new InvalidInputException("Not valid poster");
		}
		this.dateWhenPosted = dateWhenPosted.now();
		
	}
	
	public void printPost(){
		//TO print sth.
	}
	
	

}
