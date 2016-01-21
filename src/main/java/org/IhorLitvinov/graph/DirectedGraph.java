package org.IhorLitvinov.graph;

import java.util.List;

public interface DirectedGraph {

    List<Integer> getChildrenIds(int nodeId);

    List<Integer> getParentIds(int nodeId);
}
