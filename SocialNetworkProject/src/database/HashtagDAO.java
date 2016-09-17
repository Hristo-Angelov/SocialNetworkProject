package database;

import java.util.List;

import socialnetwork.main.Post;
import socialnetwork.main.Hashtag;

public interface HashtagDAO {
	
	public List<Hashtag> getTrendingHashtags();
	
	public List<Post> getPostByHashtag(Hashtag hashtag);
	
	

}
