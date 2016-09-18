import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.Test;

import database.DBTestConnection;
import database.PostDAOImpl;
import exceptions.InvalidInputException;
import socialnetwork.main.Post;
import socialnetwork.main.PostType;
import socialnetwork.main.User;

public class TestPostDAOModule {

//	@Test
//	public void testSelectPost() {
//		Post p = PostDAOImpl.getInstance().selectPost(1, DBTestConnection.getInstance().getConnection());
//		assertNotNull(p);
//		assertNotNull(p.getDateWhenPosted());
//		assertNotNull(p.getText());
//		
//		System.out.println(p.getDateWhenPosted());
//		System.out.println(p.getText());
//	}
	
	
//	
	@Test
	
	public void postInsertionTest() throws InvalidInputException{
		Post post = new Post();
		User user  = new User();
		user.setUserId(13);
		post.setDateWhenPosted(LocalDateTime.now());
		post.setPostId(19);
		post.setText("test post #kkk #insertUnique #123");
		post.setPostType(PostType.REGULAR);
		post.setPoster(user);
		PostDAOImpl.getInstance().insertPost(post, DBTestConnection.getInstance().getConnection());
		
	}
	
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
	
	

	private void assertNotNUll(Set<User> userLikes) {
		// TODO Auto-generated method stub
		
	}
}
