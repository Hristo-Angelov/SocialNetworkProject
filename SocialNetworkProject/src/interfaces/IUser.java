package interfaces;

import exceptions.InvalidInputException;
import socialnetwork.main.Post;
import socialnetwork.main.User;

public interface IUser {
	
	public void approveFollower(User follower) throws InvalidInputException;
	void addPost(Post post) throws InvalidInputException;
	void deletePost(Post post) throws InvalidInputException;
	void reply(Post originalPost, Post myReply);
	void retweet(Post originalPost, Post myReply);
}
