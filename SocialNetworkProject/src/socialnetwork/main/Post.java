package socialnetwork.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.InvalidInputException;

public class Post {

	private static final int START_COUNT_VALUE = 1;
	// private constants
	private static final int MIN_POST_LENGTH = 1;
	private static final int MAX_POST_LENGTH = 140;
	private static final Pattern HASHTAG_REGEX = Pattern.compile("(?<!\\w)#(\\w+)");

	// private fields
	private String text;
	private User poster;
	private LocalDateTime dateWhenPosted;
	private int postId;
	private Post originalPost;
	private PostType postType;
	private static int idCounter = 0;
	private DataBase database;
	private List<User> likes = new ArrayList<User>();
	private Set<User> newLikes = new TreeSet<User>((u1,u2) -> u1.getUsername().compareTo(u2.getUsername()));
	private List<Post> replies = new ArrayList<Post>();
	private Set<Post> newReplies = new TreeSet<Post>((r1,r2) -> r1.getDateWhenPosted().compareTo(r2.getDateWhenPosted()));
	public Set<User> getNewLikes() {
		return newLikes;
	}

	public void setNewLikes(Set<User> newLikes) {
		this.newLikes = newLikes;
	}

	public Set<Post> getNewReplies() {
		return newReplies;
	}

	public void setNewReplies(Set<Post> newReplies) {
		this.newReplies = newReplies;
	}

	public Set<Post> getNewRetweets() {
		return newRetweets;
	}

	public void setNewRetweets(Set<Post> newRetweets) {
		this.newRetweets = newRetweets;
	}

	private List<Post> retweets = new ArrayList<Post>();
	private Set<Post> newRetweets = new TreeSet<Post>((r1,r2) -> r1.getDateWhenPosted().compareTo(r2.getDateWhenPosted()));
	private List<Hashtag> hashtags = new ArrayList<Hashtag>();
	

	public void setLikes(List<User> likes) {
		this.likes = likes;
	}

	public Post(String text, User poster, DataBase database) throws InvalidInputException {
		if (Validator.isValidString(text, MIN_POST_LENGTH, MAX_POST_LENGTH))
			this.text = text;
		if (poster != null) {
			this.poster = poster;
		} else {
			throw new InvalidInputException("Not valid poster");
		}
		if (Validator.isValidObject(database)) {
			this.database = database;

		} else {
			throw new InvalidInputException("Invalid database");
		}
		this.dateWhenPosted = LocalDateTime.now();
		this.findHashtags(text);
		Post.idCounter++;
		this.postId = Post.idCounter;
	}

	public Post() {

	}

	public void setPoster(User poster) {
		this.poster = poster;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public Post getOriginalPost() {

		return originalPost;

	}

	public PostType getPostType() {
		return postType;
	}

	public void setPostType(PostType postType) {
		this.postType = postType;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setDateWhenPosted(LocalDateTime dateWhenPosted) {
		this.dateWhenPosted = dateWhenPosted;
	}

	public void setOriginalPost(Post originalPost) {
		this.originalPost = originalPost;
	}

	public class Hashtag implements Comparable<Hashtag> {

		private String name;
		private int count;

		private int hashtagId;
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

		public void decreaseHashtagCount() throws InvalidInputException {
			if (this.count > 1) {
				this.count--;
			} else {
				Post.this.database.deleteHashTag(this);
			}
		}

		@Override
		public int compareTo(Hashtag other) {
			return this.count - other.count;
		}

		public String getName() {
			return name;
		}

		public int getCount() {
			return count;
		}

		public int getHashtagId() {
			return hashtagId;
		}

		public void setHashtagId(int hashtagId) {
			this.hashtagId = hashtagId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Hashtag other = (Hashtag) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		private Post getOuterType() {
			return Post.this;
		}

	}

	public String getText() {
		return this.text;
	}

	public int getPostId() {
		return postId;
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
			replies.add(post);
		} else {
			throw new InvalidInputException("Invalid post!");
		}
	}

	public void deleteAnswer(Post post) throws InvalidInputException {
		if (Validator.isValidObject(post)) {
			if (replies.contains(post)) {
				replies.remove(post);
			} else {
				throw new InvalidInputException("Not an existing answer!");
			}
		}

	}

	public void printPeopleWhoLikedThisPost() {
		if (likes.size() > 0) {
			for (User user : likes) {
				System.out.println(user);
			}
		} else {
			System.out.println("No likes yet!");
		}
	}

	public void printAllReplies() {
		if (replies.size() > 0) {
			for (Post post : replies) {
				System.out.println(post);
			}
		} else {
			System.out.println("No replies yet");
		}
	}

	public void reply(Post originalPost) throws InvalidInputException {
		this.originalPost = originalPost;
		originalPost.addAnswer(this);
	}

	public Retweet retweet(Post originalPost) throws InvalidInputException {
		if (Validator.isValidObject(originalPost)) {
			Retweet newRetweet = new Retweet(this.text, this.poster, originalPost, database);

			originalPost.addRetweet(newRetweet);

			return newRetweet;
		}
		return null;
	}

	public void addRetweet(Retweet retweet) throws InvalidInputException {
		if (Validator.isValidObject(retweet)) {
			retweets.add(retweet);
		}
	}

	public User getPoster() {
		return poster;
	}

	public LocalDateTime getDateWhenPosted() {
		return dateWhenPosted;
	}

	public List<Post> getAnswers() {
		return replies;
	}

	public List<User> getLikes() {
		return likes;
	}

	public void addLike(User user) {
		likes.add(user);
	}

	private void findHashtags(String text) throws InvalidInputException {
		Matcher matcher = Post.HASHTAG_REGEX.matcher(text);
		while (matcher.find()) {
			Hashtag hashtag = new Hashtag(matcher.group(0));
			hashtags.add(hashtag);
			try (Connection conn = DataBase.getConnection()) {
				PreparedStatement statement = conn.prepareStatement("insert into hashtags (text, count ) values (?,?)");
				statement.setString(1, matcher.group(0));
				statement.setInt(2, START_COUNT_VALUE);
				statement.executeUpdate();
				Statement state = conn.createStatement();
				ResultSet set = state
						.executeQuery("select idhashtags from hastags where idhashtags = '" + matcher.group(0) + "'");
				hashtag.setHashtagId(set.getInt(1));
				set.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}
		database.addHashtags(this);

	}

	public List<Hashtag> getHashtags() {
		return Collections.unmodifiableList(this.hashtags);
	}

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return "User: " + this.poster + "; Posted on: " + this.dateWhenPosted.format(formatter) + ": " + this.text
				+ "\nLikes: " + this.likes.size() + "\tReplies: " + this.replies.size() + "\tRetweets: "
				+ this.retweets.size();
	}

	public void delete() throws InvalidInputException {
		if (hashtags.size() > 0) {
			for (Hashtag hashtag : hashtags) {
				hashtag.decreaseHashtagCount();
			}
		} else {
			System.out.println("No hashtags in this post.");
		}

	}

}
