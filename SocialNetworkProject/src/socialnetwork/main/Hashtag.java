package socialnetwork.main;

import exceptions.*;

public class Hashtag {
	private String name;
	private int count = 0;

	public Hashtag(String name) throws InvalidInputException {
		if (Validator.isValidString(name)) {
			this.name = name;
		}

	}

	public void increaseNumberOfHashtags() {
		this.count++;
	}

}
