package controllers;

import javax.servlet.http.HttpSession;

import socialnetwork.main.Post;

public class PostValidation {

	public static boolean validatePost(Post post, HttpSession session) {
		if (post != null && post.getText() != null && !post.getText().isEmpty()) {
			if (session.getAttribute("user") != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
