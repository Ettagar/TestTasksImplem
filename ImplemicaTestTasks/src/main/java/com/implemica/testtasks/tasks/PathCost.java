package com.implemica.testtasks.tasks;

import java.util.Scanner;

import com.implemica.testtasks.model.Graph;

public class PathCost {
	private PathCost() {
		throw new IllegalStateException("Utility class");
	}

	// Constants for maximum constraints
	private static final int MAX_TEST_CASES = 10;
	private static final int MAX_CITIES = 10000;
	private static final int MAX_PATHS = 100;

	public static void calculate(Scanner scanner) {
		consumeLeftoverNewline(scanner);

		int testCases = getValidatedInput(scanner, "Enter the number of test cases (s): ", MAX_TEST_CASES,
				"The number of test cases (s) must not exceed " + MAX_TEST_CASES + ".");

		while (testCases-- > 0) {
			Graph graph = new Graph();

			int numberOfCities = getValidatedInput(scanner, "Enter the number of cities (n): ", MAX_CITIES,
					"The number of cities (n) must not exceed " + MAX_CITIES + ".");

			populateGraph(scanner, graph, numberOfCities);

			int numberOfPaths = getValidatedInput(scanner, "Enter the number of paths to find (r): ", MAX_PATHS,
					"The number of paths (r) must not exceed " + MAX_PATHS + ".");

			processQueries(scanner, graph, numberOfPaths);

			if (testCases > 0 && !promptForNextTestCase(scanner)) {
				return; // Exit to the main menu
			}
		}
	}

	private static void consumeLeftoverNewline(Scanner scanner) {
		if (scanner.hasNextLine()) {
			scanner.nextLine();
		}
	}

	private static int getValidatedInput(Scanner scanner, String prompt, int max, String errorMessage) {
		int value = -1; // Default to an invalid value to ensure proper validation
		while (value < 0 || value > max) { // Loop until a valid input is provided
			System.out.println(prompt);
			try {
				value = Integer.parseInt(scanner.nextLine());
				if (value > max) {
					System.out.println("Error: " + errorMessage);
				}
			} catch (NumberFormatException e) {
				System.out.println("Error: Invalid number format. Please try again.");
			}
		}
		return value;
	}

	private static void populateGraph(Scanner scanner, Graph graph, int numberOfCities) {
		for (int i = 0; i < numberOfCities; i++) {
			System.out.println("Enter the name of city " + (i + 1) + ": ");
			String cityName = scanner.nextLine();
			graph.addCity(cityName);

			System.out.println("Enter the number of neighbors for city " + cityName + ": ");
			int neighbors = getValidatedInput(scanner, "Enter the number of neighbors for city " + cityName + ": ",
					Integer.MAX_VALUE, "The number of neighbors must be a valid integer.");

			for (int j = 0; j < neighbors; j++) {
				addNeighbor(scanner, graph, cityName);
			}
		}
	}

	private static void addNeighbor(Scanner scanner, Graph graph, String cityName) {
		System.out.println("Enter neighbor index and cost (e.g., '2 5'): ");
		String[] neighborData = scanner.nextLine().split(" ");
		if (neighborData.length != 2) {
			System.out.println("Invalid input format. Expected 'index cost'.");
			return;
		}

		try {
			int neighborIndex = Integer.parseInt(neighborData[0]) - 1; // Convert to zero-based index
			int cost = Integer.parseInt(neighborData[1]);
			graph.addEdge(cityName, neighborIndex, cost);
		} catch (NumberFormatException e) {
			System.out.println("Invalid numbers in neighbor data. Please check input.");
		}
	}

	private static void processQueries(Scanner scanner, Graph graph, int numberOfPaths) {
		for (int i = 0; i < numberOfPaths; i++) {
			boolean validInput = false; // Track if input is valid
			while (!validInput) {
				System.out.println("Enter the source and destination cities (e.g., 'city1 city2'): ");
				String input = scanner.nextLine();

				String[] cities = input.split(" ");
				if (cities.length != 2) {
					System.out
					.println("Invalid input. Please enter source and destination cities separated by a space.");
					continue; // Retry within the while loop
				}

				String source = cities[0];
				String destination = cities[1];
				int sourceIndex = graph.getCityIndex(source);
				int destinationIndex = graph.getCityIndex(destination);

				if (sourceIndex == -1 || destinationIndex == -1) {
					System.out.println("Invalid city names. Please check your input.");
					continue; // Retry within the while loop
				}

				// If we reach here, the input is valid
				printCost(graph, sourceIndex, destinationIndex, source, destination);
				validInput = true; // Exit the while loop
			}
		}
	}

	private static void printCost(Graph graph, int sourceIndex, int destinationIndex, String source,
			String destination) {
		int[] distances = graph.dijkstra(sourceIndex);
		int cost = distances[destinationIndex];
		if (cost == Integer.MAX_VALUE) {
			System.out.println("No path exists between " + source + " and " + destination);
		} else {
			System.out.println("Minimum cost from " + source + " to " + destination + ": " + cost);
		}
	}

	private static boolean promptForNextTestCase(Scanner scanner) {
		System.out.println("Press Enter to continue to the next test case or type 'x' to return to the menu:");
		String input = scanner.nextLine().trim(); // Read and trim the input
		return !"x".equalsIgnoreCase(input); // Return false if user types 'x', true otherwise
	}
}
