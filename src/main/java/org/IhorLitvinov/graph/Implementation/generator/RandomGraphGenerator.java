package org.IhorLitvinov.graph.Implementation.generator;

import org.IhorLitvinov.graph.Implementation.AdjacencyListGraph;
import org.IhorLitvinov.graph.Implementation.AdjacencyMatrixGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class RandomGraphGenerator {
    private static Random random = new Random(System.currentTimeMillis());

    public static AdjacencyMatrixGraph generateAdjacencyMatrixGraph(
            int nodesNumber,
            int averageEdgesPerNodeNumber
    ) {
        int[][] adjacencyMatrix = new int[nodesNumber][nodesNumber];

        for (int nodeIndex = 0; nodeIndex < nodesNumber; nodeIndex++) {
            for (int neighborIndex = nodeIndex + 1; neighborIndex < nodesNumber; neighborIndex++) {
                if (random.nextInt(nodesNumber) <= averageEdgesPerNodeNumber) {
                    adjacencyMatrix[nodeIndex][neighborIndex] = 1;
                    adjacencyMatrix[neighborIndex][nodeIndex] = 1;
                }
            }
        }
        return new AdjacencyMatrixGraph(adjacencyMatrix);
    }

    public static AdjacencyListGraph generateAdjacencyListGraph(
            int nodesNumber,
            int averageEdgesPerNodeNumber
    ) {
        List<List<Integer>> adjacencyList = new ArrayList<>(nodesNumber);
        for (int nodeIndex = 0; nodeIndex < nodesNumber; nodeIndex++) {
            adjacencyList.add(nodeIndex, new LinkedList<>());
        }

        for (int nodeIndex = 0; nodeIndex < nodesNumber; nodeIndex++) {
            for (int neighborIndex = nodeIndex + 1; neighborIndex < nodesNumber; neighborIndex++) {
                if (random.nextInt(nodesNumber) <= averageEdgesPerNodeNumber) {
                    adjacencyList.get(nodeIndex).add(neighborIndex);
                    adjacencyList.get(neighborIndex).add(nodeIndex);
                }
            }
        }
        return new AdjacencyListGraph(adjacencyList);
    }
}
