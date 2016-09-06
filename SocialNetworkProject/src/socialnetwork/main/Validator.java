package socialnetwork.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.InvalidInputException;

public class Validator {

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean isValidString(String input) throws InvalidInputException {
		if (input == null || input.trim().isEmpty()) {
			throw new InvalidInputException("Error! Cannot enter empty text.");
		}
		return true;
	}

	public static boolean isValidString(String input, int minLength, int maxLength) throws InvalidInputException {
		if (isValidString(input)) {
			if (input.length() < minLength) {
				throw new InvalidInputException("Error! \"" + input
						+ "\" is shorter than the minimum required length of " + minLength + " characters.");
			}
			if (input.length() > maxLength) {
				throw new InvalidInputException("Error! \"" + input + "\" is longer than the maximum allowed length of "
						+ maxLength + " characters.");
			}
		}
		return true;
	}

	public static boolean isValidEmail(String email) throws InvalidInputException {
		if (isValidString(email)) {
			 Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
			 if (!matcher.find()) {
				 throw new InvalidInputException("Error! Invalid e-mail.");
			 }
		}
		return true;
	}

	public static boolean isValidObject(Object obj) throws InvalidInputException {
		if (obj == null) {
			throw new InvalidInputException("Error! Object is null.");
		}
		return true;
	}
}
