import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;

import database.DBTestConnection;
import database.PostDAOImpl;
import socialnetwork.main.Post;
import socialnetwork.main.User;

public class TestPostDAOModule {

	@Test
	public void testSelectPost() {
		Post p = PostDAOImpl.getInstance().selectPost(16, DBTestConnection.getInstance().getConnection());
		assertNotNull(p);
		assertNotNull(p.getDateWhenPosted());
		assertNotNull(p.getText());
		
		System.out.println(p.getDateWhenPosted());
		System.out.println(p.getText());
	}
	
	@Test
	public void testGetLikes() {
		Post p = PostDAOImpl.getInstance().selectPost(16, DBTestConnection.getInstance().getConnection());
		assertNotNull(p);
		assertNotNull(p.getDateWhenPosted());
		assertNotNull(p.getText());
		Set<User> userLikes = PostDAOImpl.getInstance().getLikes(p, DBTestConnection.getInstance().getConnection());
		assertNotNUll(userLikes);
	}

	private void assertNotNUll(Set<User> userLikes) {
		// TODO Auto-generated method stub
		
	}
}
