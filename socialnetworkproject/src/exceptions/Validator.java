package exceptions;

public class Validator {

	public static String validateString(String input) throws InvalidInputException {
		if (input == null || input.trim().isEmpty()) {
			throw new InvalidInputException("Error! Cannot enter empty text.");
		}
		return input;
	}
	
	public static String validateString(String input, int minLength, int maxLength) throws InvalidInputException {
		validateString(input);
		if (input.length() < minLength) {
			throw new InvalidInputException("Error! \"" + input + "\" is shorter than the minimum required length of " + minLength + " characters.");
		}
		if (input.length() > maxLength) {
			throw new InvalidInputException("Error! \"" + input + "\" is longer than the maximum allowed length of " + maxLength + " characters.");
		}
		return input;
	}
}
