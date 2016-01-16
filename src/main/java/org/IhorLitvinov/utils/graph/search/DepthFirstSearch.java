package org.IhorLitvinov.utils.graph.search;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Depth-first search (DFS) is an algorithm for traversing
 * or searching tree or graph data structures.
 * One starts at the root
 * (selecting some arbitrary node as the root in the case of a graph)
 * and explores as far as possible along each branch before backtracking.
 * Warning!!! This is recursive version of algorithm.
 * So if you use it on huge graphs you should increase your size of stack
 * to avoid StackOverflowError
 */
public class DepthFirstSearch {
    /**
     * @param markFunction      used to mark node as visited,
     *                          mark distance from start node or
     *                          mark sequence number of the connected component.
     *                          First parameter is current nodeId,
     *                          second is previous nodeId
     *                          (used for path marking, etc).
     *                          Should work fine when
     *                          previous node is current (initial case)
     * @param isVisited         used to check if current node was visited.
     *                          Connected to markFunction
     * @param isDestinationNode used to check if current node is destination node
     * @param neighborsFunction used to get neighbors of current node.
     *                          Argument is nodeId
     * @return true if destination node has been reached
     */
    public boolean search(
            Integer startNodeId,
            BiConsumer<Integer, Integer> markFunction,
            Predicate<Integer> isVisited,
            Predicate<Integer> isDestinationNode,
            Function<Integer, List<Integer>> neighborsFunction
    ) {
        markFunction.accept(startNodeId, startNodeId);

        return isDestinationNode.test(startNodeId) ||
                dfs(
                        startNodeId,
                        isVisited,
                        markFunction,
                        neighborsFunction,
                        isDestinationNode
                );

    }

    private boolean dfs(
            Integer nodeId,
            Predicate<Integer> isVisited,
            BiConsumer<Integer, Integer> markFunction,
            Function<Integer, List<Integer>> neighborsFunction,
            Predicate<Integer> isDestinationNode
    ) {
        List<Integer> neighborNodes = neighborsFunction.apply(nodeId);
        for (Integer neighborNodeId : neighborNodes) {
            if (!isVisited.test(neighborNodeId)) {
                markFunction.accept(neighborNodeId, nodeId);

                if (isDestinationNode.test(neighborNodeId)) {
                    return true;
                }

                boolean isDestinationFound = dfs(
                        neighborNodeId,
                        isVisited,
                        markFunction,
                        neighborsFunction,
                        isDestinationNode
                );

                if (isDestinationFound) {
                    return true;
                }
            }
        }
        return false;
    }
}
