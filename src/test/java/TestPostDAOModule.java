import static org.junit.Assert.*;

import org.junit.Test;

import database.DBTestConnection;
import database.PostDAOImpl;
import socialnetwork.main.Post;

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
}
