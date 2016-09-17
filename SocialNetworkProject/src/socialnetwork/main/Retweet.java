package socialnetwork.main;

import exceptions.InvalidInputException;

public class Retweet extends Post {

	private Post question;

	public Retweet(String text, User poster, Post originalPost) throws InvalidInputException {
		super(text, poster);
		if (Validator.isValidObject(originalPost)) {
			this.question = originalPost;
		} else {
			throw new InvalidInputException("This post does not exist anymore");
		}

	}

	@Override
	public String toString() {

		return "Post:" + question.getText() + " Retweet:  " + " " + super.toString();
	}

}
