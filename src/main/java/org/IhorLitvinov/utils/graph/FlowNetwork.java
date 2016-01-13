package org.IhorLitvinov.utils.graph;

public interface FlowNetwork extends Graph {
    int getCapacity(int startNodeId, int endNodeId);

    void setCapacity(int startNodeId, int endNodeId, int newCapacity);
}
