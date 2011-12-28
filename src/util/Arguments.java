package util;
import java.io.Serializable;


/**
 * CSPandas, CSE 331, Spring 2011
 * Group Project: Yahtzee! Due 06/04/2011
 * 
 * A Arguments class contains a few useful methods for checking validity of 
 * arguments.
 * 
 * @author Joseph Miau
 * @version Spring 2011 v1.0
 */

public final class Arguments implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Arguments() {}

	/**
	 * static method to check if String passed is empty or consists
	 * entirely of whitespace
	 * @param test the String to test for emptiness/whitespace
	 * @throws IllegalArgumentException when the String passed is empty or
	 * consists entirely of whitespace
	 */
	public static void isNotEmptyOrWhitespace(String test) {
		if (test.trim().isEmpty())
			throw new IllegalArgumentException("string cannot be empty or" +
					"consist entirely of whitespace");
	}
	
	/**
	 * static method to check if boolean inputed is valid input.
	 * @param test the boolean test to check if valid
	 * @throws IllegalArgumentException if test is false, or "invalid"
	 */
	public static void isValid(boolean test) {
		if (!test)
			throw new IllegalArgumentException("test is invalid");
	}
	
	/**
	 * static method to check if object passed is null.
	 * @param o the object to be checked if null
	 * @throws IllegalArgumentException if object o is null
	 */
	public static void isNotNull(Object o) {
		if (o == null)
			throw new IllegalArgumentException("cannot pass null");
	}
	
	/**
	 * Throws an illegal argument exception if any object in the given array is null.
	 * @param a array whose elements are to be checked for nullness (var-args)
	 * @throws IllegalArgumentException if any object in a is null
	 */
	public static void isNotNull(Object... a) {
		for (Object o : a) {
			if (o == null)
				throw new IllegalArgumentException("cannot pass null");
		}
	}
	
	/**
	 * Throws an illegal argument exception if any object in the given array is null.
	 * @param a array whose elements are to be checked if any is null
	 * @throws IllegalArgumentException if any object in a is null
	 */
	public static void isNotNullArray(Object[] a) {
		isNotNull(a);
		for (Object o : a) {
			if (o == null)
				throw new IllegalArgumentException("cannot pass null");
		}
	}
	
	/**
	 * static method to check if a number is inclusively between
	 * other two input parameters.
	 * @param a the int to be compared in range
	 * @param min the min range inclusive to be tested
	 * @param max the max range inclusive to be tested
	 * @throws IndexOutOfBoundsException if number passed is outside
	 * of min and max ranged passed
	 */
	public static void isInRange(int a, int min, int max) {
		if (a < min || a > max)
			throw new IndexOutOfBoundsException(a + ": must be between " + min + " and " + max);
	}
	
	/**
	 * static method to check if the given integer is
	 * greater than or equal to the given minimum value.
	 * @param a the int to be checked for greater than min
	 * @throws IllegalArgumentException if number passed it smaller
	 * than min value passed
	 */
	public static void isAtLeast(int a, int min) {
		if (a < min)
			throw new IndexOutOfBoundsException(a + ": must be at least " + min);
	}
}
