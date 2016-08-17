package interfaces;

import socialnetwork.main.Post;
import socialnetwork.main.User;

public interface IUser {
	
	void approveUser(User user);
	void post(Post post);
	void deletePost(Post post);
	void reply(Post post);
	void retweet(Post post);
}
