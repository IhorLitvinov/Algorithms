package org.IhorLitvinov.utils.graph.search;

import com.google.common.collect.ImmutableMap;
import org.IhorLitvinov.utils.graph.Graph;
import org.IhorLitvinov.utils.graph.Implementation.AdjacencyMatrixGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class BreadthFirstSearchTest {
    private BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch();
    /**
     * Graphs visualisation you can find is examples_data directory
     */
    private int[][] adjacencyMatrix1 = {
            {0, 0, 0, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    private int[][] adjacencyMatrix2 = {
            {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}
    };

    private Graph graph1 = new AdjacencyMatrixGraph(adjacencyMatrix1);
    private Graph graph2 = new AdjacencyMatrixGraph(adjacencyMatrix2);

    private Map<Integer, Integer> nodeMarks = new HashMap<>();

    private BiConsumer<Integer, Integer> distanceMarkFunction = (current, previous) -> {
        int mark;
        if (previous.equals(current)) {
            mark = 0;
        } else {
            mark = nodeMarks.get(previous) + 1;
        }
        nodeMarks.put(current, mark);
    };

    private boolean graph1DistanceMarkingSearch(
            Integer startNode,
            Integer destinationNode
    ) {
        return breadthFirstSearch.search(
                startNode,
                distanceMarkFunction,
                nodeMarks::containsKey,
                destinationNode::equals,
                graph1::getChildrenIds
        );
    }

    private boolean graph2PathMarkingSearch(
            Integer startNode,
            Integer destinationNode
    ) {
        return breadthFirstSearch.search(
                startNode,
                nodeMarks::put,
                nodeMarks::containsKey,
                destinationNode::equals,
                graph2::getChildrenIds
        );
    }

    @Test
    public void node8ShouldBeFoundFromNode2InGraph1() throws Exception {
        nodeMarks.clear();
        final Integer START_NODE = 2;
        final Integer DESTINATION_NODE = 8;

        boolean actualFound = graph1DistanceMarkingSearch(START_NODE, DESTINATION_NODE);

        Assert.assertEquals(true, actualFound);
    }

    @Test
    public void node1ShouldNotBeFoundFromNode7InGraph1() throws Exception {
        nodeMarks.clear();
        final Integer START_NODE = 7;
        final Integer DESTINATION_NODE = 1;

        boolean actualFound = graph1DistanceMarkingSearch(START_NODE, DESTINATION_NODE);

        Assert.assertEquals(false, actualFound);
    }

    @Test
    public void testDistancesBetweenNode2andOtherNodes() throws Exception {
        nodeMarks.clear();
        final Integer START_NODE = 2;
        final Integer UNREACHABLE_DESTINATION_NODE = -1;

        graph1DistanceMarkingSearch(START_NODE, UNREACHABLE_DESTINATION_NODE);

        Map<Integer, Integer> expectedNodeDistances = ImmutableMap
                .<Integer, Integer>builder()
                .put(2, 0)
                .put(7, 1)
                .put(5, 1)
                .put(0, 1)
                .put(6, 2)
                .put(8, 2)
                .put(1, 2)
                .put(4, 2)
                .put(3, 2)
                .build();

        Assert.assertEquals(expectedNodeDistances, nodeMarks);
    }

    @Test
    public void testDistancesBetweenNode5andOtherNodes() throws Exception {
        nodeMarks.clear();
        final Integer START_NODE = 5;
        final Integer UNREACHABLE_DESTINATION_NODE = -1;

        graph1DistanceMarkingSearch(START_NODE, UNREACHABLE_DESTINATION_NODE);

        Map<Integer, Integer> expectedNodeDistances = ImmutableMap
                .<Integer, Integer>builder()
                .put(2, 1)
                .put(7, 2)
                .put(5, 0)
                .put(0, 1)
                .put(6, 3)
                .put(8, 3)
                .put(1, 1)
                .put(4, 2)
                .put(3, 2)
                .build();

        Assert.assertEquals(expectedNodeDistances, nodeMarks);
    }

    @Test
    public void testSearchWhenDestinationIsStart() throws Exception {
        nodeMarks.clear();
        final Integer START_NODE = 2;
        final Integer DESTINATION_NODE = 2;

        boolean actualFound = graph1DistanceMarkingSearch(START_NODE, DESTINATION_NODE);

        Assert.assertEquals(true, actualFound);

        Map<Integer, Integer> expectedNodeDistances = ImmutableMap.of(2, 0);

        Assert.assertEquals(expectedNodeDistances, nodeMarks);
    }

    @Test
    public void testPathsFromNode0ToOtherNodesInGraph2() throws Exception {
        nodeMarks.clear();
        final Integer START_NODE = 0;
        final Integer UNREACHABLE_DESTINATION_NODE = -1;

        graph2PathMarkingSearch(START_NODE, UNREACHABLE_DESTINATION_NODE);

        Map<Integer, Integer> expectedNodeParents = ImmutableMap
                .<Integer, Integer>builder()
                .put(0, 0)
                .put(1, 8)
                .put(2, 11)
                .put(3, 11)
                .put(4, 0)
                .put(5, 0)
                .put(6, 12)
                .put(7, 6)
                .put(8, 2)
                .put(9, 10)
                .put(10, 1)
                .put(11, 4)
                .put(12, 5)
                .build();

        Assert.assertEquals(expectedNodeParents, nodeMarks);
    }
}