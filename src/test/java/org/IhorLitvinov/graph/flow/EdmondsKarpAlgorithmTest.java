package org.IhorLitvinov.graph.flow;

import org.IhorLitvinov.graph.Implementation.MatrixFlowNetwork;
import org.IhorLitvinov.graph.flow.EdmondsKarpAlgorithm;
import org.junit.Assert;
import org.junit.Test;

public class EdmondsKarpAlgorithmTest {
    private EdmondsKarpAlgorithm edmondsKarpAlgorithm = new EdmondsKarpAlgorithm();

    private int[][] capacitiesMatrix1 = {
            {0, 3, 0, 3, 0, 0, 0},
            {0, 0, 4, 0, 0, 0, 0},
            {3, 0, 0, 1, 2, 0, 0},
            {0, 0, 0, 0, 2, 6, 0},
            {0, 1, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 9},
            {0, 0, 0, 0, 0, 0, 0}
    };

    private int[][] capacitiesMatrix2 = {
            {0, 0, 0, 0, 3, 0, 0, 7, 0, 0, 0, 0},
            {0, 0, 3, 0, 0, 0, 0, 3, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 4, 3, 0, 0},
            {0, 0, 6, 0, 0, 0, 0, 1, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 32, 0, 0, 3, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 3},
            {0, 0, 10, 0, 0, 0, 0, 3, 0, 0, 0, 0},
            {0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 5, 0, 1, 0, 0, 0, 0},
            {3, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0}
    };

    private MatrixFlowNetwork network1 = new MatrixFlowNetwork(capacitiesMatrix1);
    private MatrixFlowNetwork network2 = new MatrixFlowNetwork(capacitiesMatrix2);


    @Test(expected = IllegalArgumentException.class)
    public void edmondsKarpSourceIsSinkShouldThrowException() throws Exception {
        edmondsKarpAlgorithm.maxFlow(network1, 0, 0);
    }

    @Test
    public void flowShouldBe5FromNode0ToNode6InNetwork1() throws Exception {
        network1.restoreCapacities();

        final int SOURCE_NODE = 0;
        final int SINK_NODE = 6;

        int actualFlow = edmondsKarpAlgorithm.maxFlow(network1, SOURCE_NODE, SINK_NODE);
        final int EXPECTED_FLOW = 5;

        Assert.assertEquals(EXPECTED_FLOW, actualFlow);
    }


    @Test
    public void flowShouldBe4FromNode2ToNode4InNetwork1() throws Exception {
        network1.restoreCapacities();

        final int SOURCE_NODE = 2;
        final int SINK_NODE = 4;

        int actualFlow = edmondsKarpAlgorithm.maxFlow(network1, SOURCE_NODE, SINK_NODE);
        final int EXPECTED_FLOW = 4;

        Assert.assertEquals(EXPECTED_FLOW, actualFlow);
    }

    @Test
    public void flowShouldBe4FromNode0ToNode4InNetwork1() throws Exception {
        network1.restoreCapacities();

        final int SOURCE_NODE = 0;
        final int SINK_NODE = 4;

        int actualFlow = edmondsKarpAlgorithm.maxFlow(network1, SOURCE_NODE, SINK_NODE);
        final int EXPECTED_FLOW = 4;

        Assert.assertEquals(EXPECTED_FLOW, actualFlow);
    }

    @Test
    public void flowShouldBe5FromNode2ToNode6InNetwork1() throws Exception {
        network1.restoreCapacities();

        final int SOURCE_NODE = 2;
        final int SINK_NODE = 6;

        int actualFlow = edmondsKarpAlgorithm.maxFlow(network1, SOURCE_NODE, SINK_NODE);
        final int EXPECTED_FLOW = 5;

        Assert.assertEquals(EXPECTED_FLOW, actualFlow);
    }

    @Test
    public void flowShouldBe7FromNode11ToNode7InNetwork2() throws Exception {
        network1.restoreCapacities();

        final int SOURCE_NODE = 11;
        final int SINK_NODE = 7;

        int actualFlow = edmondsKarpAlgorithm.maxFlow(network2, SOURCE_NODE, SINK_NODE);
        final int EXPECTED_FLOW = 7;

        Assert.assertEquals(EXPECTED_FLOW, actualFlow);
    }

    @Test
    public void flowShouldBe4FromNode2ToNode10InNetwork2() throws Exception {
        network1.restoreCapacities();

        final int SOURCE_NODE = 2;
        final int SINK_NODE = 10;

        int actualFlow = edmondsKarpAlgorithm.maxFlow(network2, SOURCE_NODE, SINK_NODE);
        final int EXPECTED_FLOW = 4;

        Assert.assertEquals(EXPECTED_FLOW, actualFlow);
    }

    @Test
    public void flowShouldBe6FromNode8ToNode5InNetwork2() throws Exception {
        network1.restoreCapacities();

        final int SOURCE_NODE = 8;
        final int SINK_NODE = 5;

        int actualFlow = edmondsKarpAlgorithm.maxFlow(network2, SOURCE_NODE, SINK_NODE);
        final int EXPECTED_FLOW = 6;

        Assert.assertEquals(EXPECTED_FLOW, actualFlow);
    }
}