package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.apache.tomcat.jni.Pool;

import socialnetwork.main.Hashtag;
import socialnetwork.main.Post;

public class HashtagDAOImpl implements HashtagDAO {
	
	private static final int DEFAULT_NUMBER_OF_TRENDING_HASHTAGS = 10;

	private static PostDAO postDao = null;
	private static ConnectionPool pool;

	@Override
	public List<Hashtag> getTrendingHashtags() {
		List<Hashtag>allHashtags = new ArrayList<Hashtag>();
		List<Hashtag>trending = new ArrayList<Hashtag>();
		allHashtags.sort((h1, h2) -> h1.getCount()-h2.getCount());
		trending.sort((h1, h2) -> h1.getCount()-h2.getCount());
		Connection conn  = pool.getConnection();
		try(Statement statement = conn.createStatement();) {
			String query = "SELECT * FROM hashtags;";
			ResultSet hrs = statement.executeQuery(query);
			while(hrs.next()){
				int hashtagId = hrs.getInt(1);
				String text = hrs.getString(2);
				int counts = hrs.getInt(3);
			Hashtag hashtag = new Hashtag();
			hashtag.setHashtagId(hashtagId);
			hashtag.setCount(counts);
			hashtag.setName(text);
			allHashtags.add(hashtag);
			}
			
			if(allHashtags.size()<=DEFAULT_NUMBER_OF_TRENDING_HASHTAGS){
				
				return allHashtags;
			}else{
				for (int numberOfTrendingHashtags = 0; numberOfTrendingHashtags < DEFAULT_NUMBER_OF_TRENDING_HASHTAGS; numberOfTrendingHashtags++) {
					trending.add(allHashtags.get(numberOfTrendingHashtags));
				}
				return trending;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return null;
	}
	
	
	

	@Override
	public List<Post> getPostByHashtag(Hashtag hashtag) {
		
		return null;
	}



}
