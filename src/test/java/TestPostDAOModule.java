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
	
	
	
	@Test
	
	public void postInsertionTest(){
		Post post = new Post();
		post.setDateWhenPosted(LocalDateTime.now());
		post.setPostId(4);
		post.setText("test post #unikalen_post #unikalnarabota");
		post.setPostType(PostType.REGULAR);
		
		try {
			PostDAOImpl.getInstance().findHashtags("test post #unikalen_post #unikalnarabota", post, DBTestConnection.getInstance().getConnection());
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
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

	private void assertNotNUll(Set<User> userLikes) {
		// TODO Auto-generated method stub
		
	}
}
