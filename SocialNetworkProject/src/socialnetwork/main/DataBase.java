package socialnetwork.main;

import java.util.*;
import java.util.Map.Entry;

import exceptions.InvalidInputException;

public class DataBase {

	private Map<Hashtag, ArrayList<Post>> hashTags = new HashMap<Hashtag, ArrayList<Post>>();
	private Set<User> users = new HashSet<User>();

	
	public void addHashtagPost(Hashtag hashtag, Post post) throws InvalidInputException {
		if (Validator.isValidObject(hashtag) && Validator.isValidObject(post)) {
			hashtag.increaseHashtagCount();
			if (hashTags.containsKey(hashtag)) {
				hashTags.get(hashtag).add(post);
			} else {
				hashTags.put(hashtag, new ArrayList<Post>());
				hashTags.get(hashtag).add(post);

			}
		} else {
			throw new InvalidInputException("Not valid post or hashtag!");
		}
	}

	
	
	public void validateHashTags() {
		for (Entry<Hashtag, ArrayList<Post>> key : hashTags.entrySet()) {
			
			if (key.getValue().isEmpty()) {
				hashTags.remove(key.getKey(), key.getValue());
			}
		}
	}

	public ArrayList<Post> getPosts(String hashTag) {
		return (ArrayList<Post>) Collections.unmodifiableList(hashTags.get(hashTag));
	}

	public void updatePublicPosts() {

	}

	public void addUser(User user) throws InvalidInputException {
		if (user != null) {
			users.add(user);
		} else {
			throw new InvalidInputException("Not valid User");
		}
	}

	public User getUser(User user) throws InvalidInputException {
		if (Validator.isValidObject(user)) {
			return user;
		}
		throw new InvalidInputException("Not existing user!");
	}

}
