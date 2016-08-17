package socialnetwork.main;

import exceptions.InvalidInputException;

public class Retweet extends Post {
	
	private Post question;

	public Retweet(String text, User poster, Post originalPost) throws InvalidInputException {
		super(text, poster);
		if(Validator.isValidObject(question)){
			this.question = question;
		}
		
	}
	


}
