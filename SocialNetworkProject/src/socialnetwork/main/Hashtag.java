package socialnetwork.main;

import java.time.LocalDate;
import java.util.regex.Pattern;

import exceptions.*;

public class Hashtag {
	
	public static final Pattern HASHTAG_REGEX = Pattern.compile("(?<!\\w)#(\\w+)");
	
	private String name;
	private int count;
	private boolean isTrending;
	private LocalDate dateWhenCreated = LocalDate.now();

	public Hashtag(String name) throws InvalidInputException {
		if (Validator.isValidString(name)) {
			this.name = name;
		}
		this.count = 1;

	}

	public void increaseHashtagCount() {
		this.count++;
	}

}
