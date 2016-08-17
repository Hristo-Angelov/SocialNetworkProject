package socialnetwork.main;

import exceptions.InvalidArgumentException;

public class Retweet extends Post {

	public Retweet(String text, User poster) throws InvalidArgumentException {
		super(text, poster);
		
	}
	
	
	@Override
	public void printPost() {
		// TODO Auto-generated method stub
		super.printPost();
	}

}
