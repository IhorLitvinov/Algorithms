package org.IhorLitvinov.graph;

public interface FlowNetwork extends DirectedGraph {
    int getCapacity(int startNodeId, int endNodeId);

    void setCapacity(int startNodeId, int endNodeId, int newCapacity);

    void restoreCapacities();
}
