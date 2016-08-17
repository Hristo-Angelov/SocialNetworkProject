package socialnetwork.main;

import exceptions.*;

public class Hashtag {
	private String name;
	private int count = 0;

	public Hashtag(String name) throws InvalidInputException {
		super();
		this.name = Validator.validateString(name);

	}

	public void increaseNumberOfHashtags() {
		this.count++;
	}

}
