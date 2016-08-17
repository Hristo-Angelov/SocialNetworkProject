package socialnetwork.main;

import java.time.LocalDate;
import java.util.*;

import exceptions.InvalidInputException;
import exceptions.Validator;

public class Post {
	private String text;
	private User poster;
	private LocalDate dateWhenPosted;
	private Post question;
	private List<Post>answers = new ArrayList<Post>();
	private List<Post>likes = new ArrayList<Post>();
	
	
	
	
	public Post(String text, User poster) throws InvalidInputException {
		
		this.text = Validator.validateString(text);
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
