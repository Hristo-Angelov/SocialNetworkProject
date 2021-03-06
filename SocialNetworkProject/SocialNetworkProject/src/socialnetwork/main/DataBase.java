package socialnetwork.main;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;

import exceptions.InvalidInputException;
import socialnetwork.main.Post.Hashtag;

public class DataBase {
	
	private static final int MAX_NUMBER_OF_TRENDING_HASHTAGS = 10;
	private Map<Hashtag, ArrayList<Post>> hashtags = new HashMap<Hashtag, ArrayList<Post>>();
	private Set<User> users = new HashSet<User>();

	// public void addHashtagPost(Hashtag hashtag, Post post) throws
	// InvalidInputException {
	// if (Validator.isValidObject(hashtag) && Validator.isValidObject(post)) {
	// hashtag.increaseHashtagCount();
	// if (hashtags.containsKey(hashtag)) {
	// hashtags.get(hashtag).add(post);
	// } else {
	// hashtags.put(hashtag, new ArrayList<Post>());
	// hashtags.get(hashtag).add(post);
	//
	// }
	// } else {
	// throw new InvalidInputException("Not valid post or hashtag!");
	// }
	// }

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

	/**
	 * Loops through all hashtags stored in this post and and adds each one to
	 * the database if it is not already on there, in which case it increases
	 * the hashtag's count. Then the post is added to the list of posts
	 * containing the specific hashtag.
	 * 
	 * @param post
	 *            Post whose hashtags are to be stored in the database
	 */
	public void addHashtags(Post post) {
		for (Hashtag hashtag : post.getHashtags()) {
			if (this.hashtags.containsKey(hashtag)) {
				for (Hashtag tag : this.hashtags.keySet()) {
					if (tag.equals(hashtag)) {
						tag.increaseHashtagCount();
					}
				}
			} else {
				this.hashtags.put(hashtag, new ArrayList<Post>());
			}
			this.hashtags.get(hashtag).add(post);
		}
	}

	public String getTrendingHashtags() {
		StringBuilder sb = new StringBuilder();
		List<Hashtag> sortedHashtags = new ArrayList<Hashtag>(this.hashtags.keySet());
		sortedHashtags.sort((tag1, tag2) -> tag2.getCount() - tag1.getCount());
		int topTags = 1;
		for (Hashtag hashtag : sortedHashtags) {
			sb.append(hashtag.getName() + " - " + hashtag.getCount() + "\n");
			if (topTags++ >= MAX_NUMBER_OF_TRENDING_HASHTAGS) {
				break;
			}
		}
		return sb.toString();
	}

	public void deleteHashTag(Hashtag hashtag) throws InvalidInputException {
		if(Validator.isValidObject(hashtag)){
			if(hashtags.containsKey(hashtag)){
				hashtags.remove(hashtag);
			}
		}else{
			throw new InvalidInputException("Not valid hashtag!");
		}
		
	}

}
