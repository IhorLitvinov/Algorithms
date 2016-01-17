package org.IhorLitvinov.utils.graph.Implementation;

import org.IhorLitvinov.utils.graph.FlowNetwork;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MatrixFlowNetwork implements FlowNetwork {
    private int[][] capacitiesMatrix;

    private int[][] resultingCapacitiesMatrix;

    private List<Integer> neighbors;

    public MatrixFlowNetwork(int[][] capacitiesMatrix) {
        this.capacitiesMatrix = capacitiesMatrix;

        resultingCapacitiesMatrix =
                new int[capacitiesMatrix.length][capacitiesMatrix.length];

        copyCapacities();

        neighbors = IntStream
                .range(0, capacitiesMatrix.length)
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toList());
    }

    private void copyCapacities() {
        for (int index = 0; index < capacitiesMatrix.length; index++) {
            System.arraycopy(
                    capacitiesMatrix[index],
                    0,
                    resultingCapacitiesMatrix[index],
                    0,
                    capacitiesMatrix[index].length
            );
        }
    }

    @Override
    public int getCapacity(int startNodeId, int endNodeId) {
        return resultingCapacitiesMatrix[startNodeId][endNodeId];
    }

    @Override
    public void setCapacity(int startNodeId, int endNodeId, int newCapacity) {
        resultingCapacitiesMatrix[startNodeId][endNodeId] = newCapacity;
    }

    @Override
    public void restoreCapacities() {
        copyCapacities();
    }

    /**
     * Network is considered as a complete graph
     */
    @Override
    public List<Integer> getChildrenIds(int nodeId) {
        return Collections.unmodifiableList(neighbors);
    }

    /**
     * Network is considered as a complete graph
     */
    @Override
    public List<Integer> getParentIds(int nodeId) {
        return Collections.unmodifiableList(neighbors);
    }
}
