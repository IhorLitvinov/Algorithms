package org.IhorLitvinov.graph.search;

import org.IhorLitvinov.graph.DirectedGraph;
import org.IhorLitvinov.graph.Implementation.generator.RandomGraphGenerator;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SpeedTestSearchWithLambdas {
    private final static int TEST_NUMBERS = 100;
    private final static int TEST_ITERATIONS = 4;
    private int[] marks;
    private BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch();

    private boolean searchWithoutLambdas(
            DirectedGraph graph,
            int sourceId,
            int destinationId
    ) {

        marks[sourceId] = -1;

        Queue<Integer> queue = new LinkedList<>();

        queue.add(sourceId);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();

            List<Integer> neighbors = graph.getChildrenIds(currentNode);
            for (int neighborId : neighbors) {
                if (marks[neighborId] == 0) {
                    marks[neighborId] = currentNode + 1;

                    if (neighborId == destinationId) {
                        return true;
                    }

                    queue.add(neighborId);
                }
            }
        }
        return false;
    }

    @Test
    public void speedBenchmarkLambdasVSNoLambdasOnWarmCash() {
        final Integer START_NODE = 0;
        final Integer FINISH_NODE = 1999;
        final int GRAPH_SIZE = 2000;
        final int AVERAGE_EDGES = 30;

        long lambdaTime = 0;
        long methodTime = 0;

        long startTime;
        long finishTime;
        long workTime;

        for (int testIndex = 0; testIndex < TEST_NUMBERS; testIndex++) {
            DirectedGraph graph = RandomGraphGenerator.generateAdjacencyListGraph(GRAPH_SIZE, AVERAGE_EDGES);

            for (int testIteration = 0; testIteration < TEST_ITERATIONS; testIteration++) {
                marks = new int[GRAPH_SIZE];
                startTime = System.nanoTime();
                searchWithoutLambdas(graph, START_NODE, FINISH_NODE);
                finishTime = System.nanoTime();
                workTime = finishTime - startTime;
                if (testIteration != 0) {
                    methodTime += workTime;
                }
                System.gc();
                System.out.println(
                        "method test: " + testIndex
                                + ", iteration: " + testIteration
                                + ", iteration time: " + workTime + " ns"
                );
            }

            for (int testIteration = 0; testIteration < TEST_ITERATIONS; testIteration++) {
                marks = new int[GRAPH_SIZE];
                startTime = System.nanoTime();
                breadthFirstSearch.search(
                        START_NODE,
                        (current, previous) -> marks[current] = previous + 1,
                        current -> marks[current] != 0,
                        FINISH_NODE::equals,
                        graph::getChildrenIds
                );
                finishTime = System.nanoTime();
                workTime = finishTime - startTime;
                if (testIteration != 0) {
                    lambdaTime += workTime;
                }
                System.gc();
                System.out.println(
                        "lambda test: " + testIndex
                                + ", iteration: " + testIteration
                                + ", iteration time: " + workTime + " ns"
                );
            }
        }
        System.out.println(
                "method: " + methodTime / 1000
                        + " ms, lambda: " + lambdaTime / 1000 +" ms"
        );
    }
}
