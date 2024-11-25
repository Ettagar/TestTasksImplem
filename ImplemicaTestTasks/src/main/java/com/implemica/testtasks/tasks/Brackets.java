package com.implemica.testtasks.tasks;

public class Brackets {
	private Brackets() {
		throw new IllegalStateException("Utility class");
	}

	public static int findCorrectExpresionsCount(int n) {
		int[] correctExpressions = new int[n + 1];

		correctExpressions[0] = 1; // Only 1 valid expression for 0 pairs (an empty string)

		for (int i = 1; i <= n; i++) { // Calculating Catalan numbers
			correctExpressions[i] = 0;

			// Use the recursive relation: C(i) = Σ C(j) * C(i-j-1) for 0 ≤ j < i
			for (int j = 0; j < i; j++) {
				correctExpressions[i] += correctExpressions[j] * correctExpressions[i - j - 1];
			}
		}

		return correctExpressions[n];
	}
}
