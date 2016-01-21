package org.IhorLitvinov.graph.flow;

import org.IhorLitvinov.graph.FlowNetwork;
import org.IhorLitvinov.graph.search.BreadthFirstSearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Edmonds–Karp algorithm is an implementation
 * of the Ford–Fulkerson method
 * for computing the maximum flow
 * in a flow network in O(V E^2) time
 */
public class EdmondsKarpAlgorithm {
    private BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch();

    public int maxFlow(
            FlowNetwork graph,
            Integer sourceNodeId,
            Integer sinkNodeId
    ) {
        checkNodeIds(sourceNodeId, sinkNodeId);

        Map<Integer, Integer> nodeMarks = new HashMap<>();
        int maxFlow = 0;

        while (hasWay(graph, sourceNodeId, sinkNodeId, nodeMarks)) {
            int minCapacity = minCapacity(graph, sinkNodeId, nodeMarks);
            maxFlow += minCapacity;

            reduceCapacity(graph, sinkNodeId, minCapacity, nodeMarks);
            nodeMarks.clear();
        }
        return maxFlow;
    }

    private void checkNodeIds(int sourceNodeId, int sinkNodeId) {
        if (sourceNodeId == sinkNodeId) {
            throw new IllegalArgumentException(
                    "Source and destination nodes are the same! Id = " + sourceNodeId
            );
        }
    }

    private boolean hasWay(
            FlowNetwork graph,
            Integer startNodeId,
            Integer destinationNodeId,
            Map<Integer, Integer> nodeMarks
    ) {
        Function<Integer, List<Integer>> neighborsFunction = nodeId ->
                graph.getChildrenIds(nodeId)
                        .stream()
                        .filter(
                                childNode -> graph.getCapacity(nodeId, childNode) > 0
                        )
                        .collect(Collectors.toList());

        return breadthFirstSearch.search(
                startNodeId,
                nodeMarks::put,
                nodeMarks::containsKey,
                destinationNodeId::equals,
                neighborsFunction
        );
    }

    private int minCapacity(
            FlowNetwork graph,
            Integer sinkNodeId,
            Map<Integer, Integer> nodeMarks
    ) {
        int currentNode = sinkNodeId;
        int parent = nodeMarks.get(currentNode);

        int minCapacity = graph.getCapacity(parent, currentNode);

        currentNode = parent;
        parent = nodeMarks.get(currentNode);

        while (parent != currentNode) {
            int currentCapacity = graph.getCapacity(parent, currentNode);

            if (currentCapacity < minCapacity) {
                minCapacity = currentCapacity;
            }

            currentNode = parent;
            parent = nodeMarks.get(currentNode);
        }
        return minCapacity;
    }


    private void reduceCapacity(
            FlowNetwork graph,
            final Integer sinkNodeId,
            int minCapacity,
            Map<Integer, Integer> nodeMarks
    ) {
        Integer currentNodeId = sinkNodeId;
        Integer parentNodeId = nodeMarks.get(currentNodeId);

        while (!parentNodeId.equals(currentNodeId)) {
            reduceCapacity(graph, parentNodeId, currentNodeId, minCapacity);

            reduceCapacity(graph, currentNodeId, parentNodeId, -minCapacity);

            currentNodeId = parentNodeId;
            parentNodeId = nodeMarks.get(currentNodeId);
        }
    }

    private void reduceCapacity(
            FlowNetwork graph,
            int parentNodeId,
            int currentNodeId,
            int delta
    ) {
        int oldCapacity = graph.getCapacity(parentNodeId, currentNodeId);
        graph.setCapacity(parentNodeId, currentNodeId, oldCapacity - delta);
    }
}
