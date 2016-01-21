package org.IhorLitvinov.graph.Implementation;

import org.IhorLitvinov.graph.UndirectedGraph;

import java.util.List;

public class AdjacencyListGraph extends UndirectedGraph {
    private List<List<Integer>> adjacencyList;

    public AdjacencyListGraph(List<List<Integer>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    @Override
    public List<Integer> getChildrenIds(int nodeId) {
        return adjacencyList.get(nodeId);
    }
}
