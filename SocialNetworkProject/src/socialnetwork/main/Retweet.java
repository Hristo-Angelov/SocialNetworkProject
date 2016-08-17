package socialnetwork.main;

import exceptions.InvalidInputException;

public class Retweet extends Post {

	public Retweet(String text, User poster) throws InvalidInputException {
		super(text, poster);
		
	}
	
	
	@Override
	public void printPost() {
		// TODO Auto-generated method stub
		super.printPost();
	}

}
