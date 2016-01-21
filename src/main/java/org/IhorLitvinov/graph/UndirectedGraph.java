package org.IhorLitvinov.graph;

import java.util.List;

public abstract class UndirectedGraph implements DirectedGraph {
    @Override
    public List<Integer> getParentIds(int nodeId) {
        return getChildrenIds(nodeId);
    }
}
