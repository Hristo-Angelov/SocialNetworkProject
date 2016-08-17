package socialnetwork.main;

import java.time.LocalDate;
import java.util.*;

import exceptions.InvalidInputException;

public class Post {

	// private constants
	private static final int MIN_POST_LENGTH = 1;
	private static final int MAX_POST_LENGTH = 140;

	// private fields
	private String text;
	private User poster;
	private LocalDate dateWhenPosted;
	private List<Post> answers = new ArrayList<Post>();
	private List<User> likes = new ArrayList<User>();
	private Set<Hashtag> hashtags = new HashSet<Hashtag>();

	public Post(String text, User poster) throws InvalidInputException {
		if (Validator.isValidString(text, MIN_POST_LENGTH, MAX_POST_LENGTH))
			this.text = text;
		if (poster != null) {
			this.poster = poster;
		} else {
			throw new InvalidInputException("Not valid poster");
		}
		this.dateWhenPosted = dateWhenPosted.now();

	}

	public String getText() {
		return this.text;
	}

	public void deleteLike(Post post) throws InvalidInputException {
		if (Validator.isValidObject(post)) {
			if (likes.contains(post)) {
				likes.remove(post);
			} else {
				throw new InvalidInputException("Not an existing like!");
			}
		}

	}

	public void addAnswer(Post post) throws InvalidInputException {
		if (Validator.isValidObject(post)) {
			answers.add(post);
		} else {
			throw new InvalidInputException("Invalid post! ");
		}
	}

	public void deleteAnswer(Post post) throws InvalidInputException {
		if (Validator.isValidObject(post)) {
			if (answers.contains(post)) {
				answers.remove(post);
			} else {
				throw new InvalidInputException("Not an existing answer!");
			}
		}

	}

	public void addReply(Post myRetweet) {

	}

	public void reply(Post originalPost) {
		// TODO Auto-generated method stub

	}

	public void retweet(Post originalPost) {

	}

	public User getPoster() {
		return poster;
	}

	public LocalDate getDateWhenPosted() {
		return dateWhenPosted;
	}

	public List<Post> getAnswers() {
		return answers;
	}

	public List<User> getLikes() {
		return likes;
	}

	public void delete() {

	}

	public void addLike(User user) {
		likes.add(user);

	}

}
