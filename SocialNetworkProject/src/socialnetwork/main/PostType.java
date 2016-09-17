package socialnetwork.main;

public enum PostType {
	
	REGULAR, ANSWER, RETWEET;

	public static PostType fromInt(int postType) {
		switch (postType) {
		case 0:
			return PostType.REGULAR;
		case 1:
			return PostType.ANSWER;
		case 2:
			return PostType.RETWEET;
		default:
			return null;
		}
	}

}
