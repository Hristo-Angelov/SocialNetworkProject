package socialnetwork.main;

import java.util.*;
import java.util.Map.Entry;

import exceptions.InvalidInputException;

public class DataBase {

	private Map<Hashtag, ArrayList<Post>> hashtags = new HashMap<Hashtag, ArrayList<Post>>();
	private Set<User> users = new HashSet<User>();

	public void addHashtagPost(Hashtag hashtag, Post post) throws InvalidInputException {
		if (Validator.isValidObject(hashtag) && Validator.isValidObject(post)) {
			hashtag.increaseHashtagCount();
			if (hashtags.containsKey(hashtag)) {
				hashtags.get(hashtag).add(post);
			} else {
				hashtags.put(hashtag, new ArrayList<Post>());
				hashtags.get(hashtag).add(post);

			}
		} else {
			throw new InvalidInputException("Not valid post or hashtag!");
		}
	}

	public void validateHashTags() {
		for (Entry<Hashtag, ArrayList<Post>> key : hashtags.entrySet()) {

			if (key.getValue().isEmpty()) {
				hashtags.remove(key.getKey());
			}
		}
	}

	public ArrayList<Post> getPosts(String hashTag) {
		return (ArrayList<Post>) Collections.unmodifiableList(hashtags.get(hashTag));
	}

	public void updatePublicPosts() {
		// TODO
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

	public void addHashtags(Post post) {
		for (Hashtag hashtag : post.getHashtags()) {
			if (this.hashtags.containsKey(hashtag)) {
				for (Hashtag tag: this.hashtags.keySet()) {
					if (tag.equals(hashtag)) {
						tag.increaseHashtagCount();
					}
				}
			} else {
				this.hashtags.put(hashtag, new ArrayList<Post>());
				this.hashtags.get(hashtag).add(post);
			}
		}
	}

}
