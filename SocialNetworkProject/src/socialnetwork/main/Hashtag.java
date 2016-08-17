package socialnetwork.main;

import exceptions.InvalidArgumentException;

public class Hashtag {
	private String name;
	private int count = 0;

	public Hashtag(String name) throws InvalidArgumentException {
		super();
		this.name = User.validateText(name);

	}

	public void increaseNumberOfHashtags() {
		this.count++;
	}

}
