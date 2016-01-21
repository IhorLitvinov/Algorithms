package org.IhorLitvinov.graph.search;

import com.google.common.collect.ImmutableMap;
import org.IhorLitvinov.graph.DirectedGraph;
import org.IhorLitvinov.graph.Implementation.AdjacencyMatrixGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DepthFirstSearchTest {
    private DepthFirstSearch breadthFirstSearch = new DepthFirstSearch();
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

    private DirectedGraph graph1 = new AdjacencyMatrixGraph(adjacencyMatrix1);
    private DirectedGraph graph2 = new AdjacencyMatrixGraph(adjacencyMatrix2);

    private Map<Integer, Integer> nodeMarks = new HashMap<>();

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

    private boolean graph1PathMarkingSearch(
            Integer startNode,
            Integer destinationNode
    ) {
        return breadthFirstSearch.search(
                startNode,
                nodeMarks::put,
                nodeMarks::containsKey,
                destinationNode::equals,
                graph1::getChildrenIds
        );
    }

    @Test
    public void testSearchWhenDestinationIsStart() throws Exception {
        nodeMarks.clear();
        final Integer START_NODE = 7;
        final Integer DESTINATION_NODE = 7;

        boolean actualFound = graph1PathMarkingSearch(START_NODE, DESTINATION_NODE);

        Assert.assertEquals(true, actualFound);

        Map<Integer, Integer> expectedNodeDistances = ImmutableMap.of(7, 7);

        Assert.assertEquals(expectedNodeDistances, nodeMarks);
    }

    @Test
    public void node8ShouldBeFoundFromNode0InGraph1() throws Exception {
        nodeMarks.clear();
        final Integer START_NODE = 0;
        final Integer DESTINATION_NODE = 8;

        boolean actualFound = graph1PathMarkingSearch(START_NODE, DESTINATION_NODE);

        Assert.assertEquals(true, actualFound);
    }
    @Test
    public void node8ShouldBeFoundFromNode5InGraph1() throws Exception {
        nodeMarks.clear();
        final Integer START_NODE = 5;
        final Integer DESTINATION_NODE = 8;

        boolean actualFound = graph1PathMarkingSearch(START_NODE, DESTINATION_NODE);

        Assert.assertEquals(true, actualFound);
    }

    @Test
    public void node4ShouldNotBeFoundFromNode7InGraph1() throws Exception {
        nodeMarks.clear();
        final Integer START_NODE = 7;
        final Integer DESTINATION_NODE = 4;

        boolean actualFound = graph1PathMarkingSearch(START_NODE, DESTINATION_NODE);

        Assert.assertEquals(false, actualFound);
    }
    @Test
    public void node2ShouldNotBeFoundFromNode7InGraph1() throws Exception {
        nodeMarks.clear();
        final Integer START_NODE = 7;
        final Integer DESTINATION_NODE = 2;

        boolean actualFound = graph1PathMarkingSearch(START_NODE, DESTINATION_NODE);

        Assert.assertEquals(false, actualFound);
    }

    @Test
    public void node8ShouldBeFoundFromNode0InGraph2() throws Exception {
        nodeMarks.clear();
        final Integer START_NODE = 0;
        final Integer DESTINATION_NODE = 8;

        boolean actualFound = graph2PathMarkingSearch(START_NODE, DESTINATION_NODE);

        Assert.assertEquals(true, actualFound);
    }
    @Test
    public void node8ShouldBeFoundFromNode5InGraph2() throws Exception {
        nodeMarks.clear();
        final Integer START_NODE = 5;
        final Integer DESTINATION_NODE = 8;

        boolean actualFound = graph2PathMarkingSearch(START_NODE, DESTINATION_NODE);

        Assert.assertEquals(true, actualFound);
    }

    @Test
    public void testPathsFromNode0ToOtherNodesInGraph2() throws Exception {
        nodeMarks.clear();
        final Integer START_NODE = 5;
        final Integer UNREACHABLE_DESTINATION_NODE = -1;

        graph2PathMarkingSearch(START_NODE, UNREACHABLE_DESTINATION_NODE);

        Map<Integer, Integer> expectedNodeParents = ImmutableMap
                .<Integer, Integer>builder()
                .put(0, 5)
                .put(1, 8)
                .put(2, 11)
                .put(3, 11)
                .put(4, 0)
                .put(5, 5)
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