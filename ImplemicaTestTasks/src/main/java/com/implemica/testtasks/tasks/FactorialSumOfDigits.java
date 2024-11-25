package com.implemica.testtasks.tasks;

import java.math.BigInteger;

public class FactorialSumOfDigits {
	private FactorialSumOfDigits() {
		throw new IllegalStateException("Utility class");
	}

	public static int calculate(int number) {
		BigInteger factorial = calculateFactorial(number);
		System.out.println("Factorial of " + number + " is: ");
		System.out.println(factorial);

		return calculateDigitSum(factorial);
	}

	private static BigInteger calculateFactorial(int n) {
		BigInteger result = BigInteger.ONE; // Representing 1 as BigInteger
		for (int i = 2; i <= n; i++) {
			result = result.multiply(BigInteger.valueOf(i)); // Multiplying to find "n"factorial
		}

		return result;
	}

	private static int calculateDigitSum(BigInteger number) {
		int sum = 0;
		while (!number.equals(BigInteger.ZERO)) { // Loop continues as long as "number" value is no equal to zero
			sum += number.mod(BigInteger.TEN).intValue(); // Extract the last digit
			number = number.divide(BigInteger.TEN); // Remove the last digit
		}

		return sum;
	}
}
