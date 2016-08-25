package socialnetwork.main;

import exceptions.InvalidInputException;

public class Retweet extends Post {

	private Post question;

	public Retweet(String text, User poster, Post originalPost, DataBase database) throws InvalidInputException {
		super(text, poster, database);
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
