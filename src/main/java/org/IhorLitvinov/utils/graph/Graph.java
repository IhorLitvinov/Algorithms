package org.IhorLitvinov.utils.graph;

import java.util.List;

public interface Graph {

    List<Integer> getChildrenIds(int nodeId);

    List<Integer> getParentIds(int nodeId);
}
