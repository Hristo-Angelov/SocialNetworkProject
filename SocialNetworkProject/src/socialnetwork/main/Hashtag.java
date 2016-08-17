package socialnetwork.main;

import exceptions.InvalidInputException;

public class Hashtag {
	private String name;
	private int count = 0;

	public Hashtag(String name) throws InvalidInputException {
		super();
		this.name = User.validateText(name);

	}

	public void increaseNumberOfHashtags() {
		this.count++;
	}

}
