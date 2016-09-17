package socialnetwork.main;

import exceptions.InvalidInputException;

public class Hashtag implements Comparable<Hashtag> {

		private String name;
		private int count;

		private int hashtagId;


		public Hashtag(String name) throws InvalidInputException {
			if (Validator.isValidString(name)) {
				this.name = name;
			}
			this.count = 1;

		}

		public Hashtag() {
			
		}

		public void increaseHashtagCount() {
			this.count++;

		}

		

		@Override
		public int compareTo(Hashtag other) {
			return this.count - other.count;
		}

		public String getName() {
			return name;
		}

		public int getCount() {
			return count;
		}

		public int getHashtagId() {
			return hashtagId;
		}

		public void setHashtagId(int hashtagId) {
			this.hashtagId = hashtagId;
		}

		public void setCount(int counts) {
			this.count = counts;
			
		}

		public void setName(String text) {
			this.name = text;
			
		}

		
	

	}