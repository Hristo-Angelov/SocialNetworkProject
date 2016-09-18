import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.util.Set;

import org.junit.Test;

import database.DBTestConnection;
import database.PostDAOImpl;
import database.UserDAOImpl;
import socialnetwork.main.Post;
import socialnetwork.main.PostType;

public class TestPostDAOModule {

//	@Test
//	public void testSelectPost() {
//		Post p = PostDAOImpl.getInstance().selectPost(15, DBTestConnection.getInstance().getConnection());
//		assertNotNull(p);
//		assertNotNull(p.getDateWhenPosted());
//		assertNotNull(p.getText());
//		
//		System.out.println(p.getDateWhenPosted());
//		System.out.println(p.getText());
//	}
	
	
//	@Test
//	public void postInsertionTest() throws InvalidInputException{
//		Post post = new Post();
//		User user  = new User();
//		user.setUserId(13);
//		post.setDateWhenPosted(LocalDateTime.now());
//		post.setPostId(4);
//		post.setText("test postjvhfdjkbhjfdb");
//		post.setPostType(PostType.REGULAR);
//		post.setPoster(user);
//		PostDAOImpl.getInstance().insertPost(post, DBTestConnection.getInstance().getConnection());
//	}
	
//	@Test
//	public void testGetLikes() {
//		Post p = PostDAOImpl.getInstance().selectPost(1, DBTestConnection.getInstance().getConnection());
//		assertNotNull(p);
//		assertNotNull(p.getDateWhenPosted());
//		assertNotNull(p.getText());
//		Set<User> userLikes = PostDAOImpl.getInstance().getLikes(p, DBTestConnection.getInstance().getConnection());
//		assertNotNUll(userLikes);
//	}
//	
//	@Test
//	public void testDeletePost(){
//		Post post  = new Post();
//		post.setDateWhenPosted(LocalDateTime.now());
//		post.setPostId(6);
//		
//	PostDAOImpl.getInstance().deletePost(post, DBTestConnection.getInstance().getConnection());
//	
//	
//	}
	
//	@Test
//	public void insertTest(){
//		Post post  = new Post();
//		post.setDateWhenPosted(LocalDateTime.now());
//		post.setPostType(PostType.REGULAR);
//		post.setText("sala bala nov post test");
//		User user = new User();
//		user.setUserId(5);
//		post.setPoster(user);
//		
//		PostDAOImpl.getInstance().insertPost(post, DBTestConnection.getInstance().getConnection());
//	}
	
//	@Test
//	public void addReplyTest() {
//		Connection connection = DBTestConnection.getInstance().getConnection();
//		Post op = PostDAOImpl.getInstance().selectPost(15, connection);
//		assertNotNull(op);
//		assertNotNull(op.getDateWhenPosted());
//		assertNotNull(op.getText());
//		
//		System.out.println(op.getDateWhenPosted());
//		System.out.println(op.getText());
//		
//		Post reply = new Post();
//		reply.setOriginalPost(op);
//		reply.setText("haha");
//		reply.setPoster(UserDAOImpl.getInstance().selectUser(11, connection));
//		reply.setPostType(PostType.ANSWER);
//		PostDAOImpl.getInstance().insertPost(reply, connection);
//		
//		Set<Post> replies = PostDAOImpl.getInstance().getReplies(op, connection);
//		System.out.println(replies);
//		assertNotNull(replies);
//	}
	
	@Test
	public void addRetweetTest() {
		Connection connection = DBTestConnection.getInstance().getConnection();
		Post op = PostDAOImpl.getInstance().selectPost(16, connection);
		assertNotNull(op);
		assertNotNull(op.getDateWhenPosted());
		assertNotNull(op.getText());
		
		System.out.println(op.getDateWhenPosted());
		System.out.println(op.getText());
		
		Post retweet = new Post();
		retweet.setOriginalPost(op);
		retweet.setText("haha");
		retweet.setPoster(UserDAOImpl.getInstance().selectUser(11, connection));
		retweet.setPostType(PostType.RETWEET);
		PostDAOImpl.getInstance().insertPost(retweet, connection);
		
		Set<Post> retweets = PostDAOImpl.getInstance().getRetweets(op, connection);
		System.out.println("retweets = " + retweets);
		assertNotNull(retweets);
		for (Post post : retweets) {
			System.out.println(post);
			assertNotNull(post);
		}
	}
	
}
