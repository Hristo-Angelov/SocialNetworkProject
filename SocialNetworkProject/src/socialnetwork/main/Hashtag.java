package socialnetwork.main;

import java.time.LocalDate;

import exceptions.*;

public class Hashtag {
	private String name;
	private int count = 0;
	private boolean isTrending;
	private LocalDate dateWhenCreated = LocalDate.now();

	public Hashtag(String name) throws InvalidInputException {
		if (Validator.isValidString(name)) {
			this.name = name;
		}

	}

	public void increaseHashtagCount() {
		this.count++;
	}

}
