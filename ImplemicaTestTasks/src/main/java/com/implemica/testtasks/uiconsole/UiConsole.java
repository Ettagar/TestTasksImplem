package com.implemica.testtasks.uiconsole;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.implemica.testtasks.tasks.Brackets;
import com.implemica.testtasks.tasks.FactorialSumOfDigits;
import com.implemica.testtasks.tasks.PathCost;

public class UiConsole {
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final Scanner scanner = new Scanner(System.in);

	private UiConsole() {
		throw new IllegalStateException("Utility class");
	}

	public static void create() {
		int choice;

		do {
			printMenu();
			try {
				choice = scanner.nextInt();
				switch (choice) {
				case 1:
					menuOption1();
					break;
				case 2:
					menuOption2();
					break;
				case 3:
					menuOption3();
					break;
				case 4:
					System.out.println("Goodbye!");
					break;
				default:
					System.out.println("Invalid choice. Please try again.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				scanner.nextLine(); // Consume invalid input
				choice = 0; // Reset choice to ensure loop continues
			}
		} while (choice != 4);
	}

	private static void printMenu() {
		System.out.println(ANSI_CYAN + "===========================");
		System.out.println("       MENU OPTIONS");
		System.out.println("===========================" + ANSI_RESET);
		System.out.println("1. Find number of correct bracket expressions");
		System.out.println("2. Find the minimum path cost between city pairs");
		System.out.println("3. Find the sum of the digits in N!");
		System.out.println("4. Exit");
		System.out.print("Enter your choice: ");
	}

	// 1. Find number of correct bracket expressions
	private static void menuOption1()  {
		System.out.print("Enter the number of pairs of brackets (N): ");
		int bracketsPairsCount = getValidatedInput(scanner);

		int result = Brackets.findCorrectExpresionsCount(bracketsPairsCount);

		System.out.println("Number of correct bracket expressions: " + result);
	}

	// 2. Find the minimum transportation cost between city pairs
	private static void menuOption2() {
		PathCost.calculate(scanner);
	}

	//3. Find the sum of the digits in N!
	private static void menuOption3() {
		System.out.print("Enter a number to find the sum of its digits in the factorial: ");
		int number = getValidatedInput(scanner);

		int digitSum = FactorialSumOfDigits.calculate(number);

		System.out.println("Sum of digits in the factorial of " + number + " is: " + digitSum);
	}

	private static int getValidatedInput(Scanner scanner) {
		while (true) {
			if (scanner.hasNextInt()) {
				int n = scanner.nextInt();
				if (n > 0) {
					return n; // Valid input
				}
				System.out.println("Error: N must be a non-negative integer. Please try again.");
			} else {
				System.out.println("Error: Please enter a valid integer.");
				scanner.nextLine(); // Clear invalid input
			}
		}
	}
}

