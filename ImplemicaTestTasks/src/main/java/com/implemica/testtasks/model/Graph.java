package com.implemica.testtasks.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Graph {
	private final Map<String, Integer> cityIndexMap = new HashMap<>();
	private final List<List<Edge>> adjacencyList = new ArrayList<>();

	// Add a new city to the graph
	public void addCity(String cityName) {
		cityIndexMap.put(cityName, cityIndexMap.size());
		adjacencyList.add(new ArrayList<>());
	}

	// Add a connection (edge) between cities
	public void addEdge(String cityName, int neighborIndex, int cost) {
		int cityIndex = cityIndexMap.get(cityName);
		adjacencyList.get(cityIndex).add(new Edge(neighborIndex, cost));
	}

	// Get the index of a city by name
	public int getCityIndex(String cityName) {
		return cityIndexMap.getOrDefault(cityName, -1);
	}

	// Dijkstra's algorithm to find the shortest path from a given start city
	public int[] dijkstra(int startIndex) {
		int[] distances = new int[adjacencyList.size()];
		Arrays.fill(distances, Integer.MAX_VALUE);
		distances[startIndex] = 0;

		PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.cost));
		priorityQueue.offer(new Edge(startIndex, 0));

		while (!priorityQueue.isEmpty()) {
			Edge current = priorityQueue.poll();

			if (current.cost > distances[current.destination]) {
				continue;
			}

			for (Edge edge : adjacencyList.get(current.destination)) {
				int newCost = distances[current.destination] + edge.cost;
				if (newCost < distances[edge.destination]) {
					distances[edge.destination] = newCost;
					priorityQueue.offer(new Edge(edge.destination, newCost));
				}
			}
		}

		return distances;
	}
}
