package org.IhorLitvinov.graph.Implementation;

import org.IhorLitvinov.graph.DirectedGraph;

import java.util.LinkedList;
import java.util.List;
import java.util.function.IntPredicate;

public class AdjacencyMatrixGraph implements DirectedGraph {
    private static final int NOT_CONNECTED = 0;
    private int[][] adjacencyMatrix;

    public AdjacencyMatrixGraph(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    private void checkNodeId(int nodeId) {
        if (nodeId < 0 || nodeId >= adjacencyMatrix.length) {
            throw new IllegalArgumentException(
                    "Graph doesn't contains node with id: " + nodeId
            );
        }
    }

    @Override
    public List<Integer> getChildrenIds(int nodeId) {
        return getNeighborsIds(
                nodeId,
                neighborId -> adjacencyMatrix[nodeId][neighborId]
                        != NOT_CONNECTED
        );
    }

    @Override
    public List<Integer> getParentIds(int nodeId) {
        return getNeighborsIds(
                nodeId,
                neighborId -> adjacencyMatrix[neighborId][nodeId]
                        != NOT_CONNECTED
        );
    }

    private List<Integer> getNeighborsIds(int nodeId, IntPredicate isConnected) {
        checkNodeId(nodeId);

        int nodesNumber = adjacencyMatrix.length;
        List<Integer> neighbors = new LinkedList<>();
        for (int neighborId = 0; neighborId < nodesNumber; neighborId++) {
            if (isConnected.test(neighborId)) {
                neighbors.add(neighborId);
            }
        }
        return neighbors;
    }
}
