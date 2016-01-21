package org.IhorLitvinov.search;

import org.IhorLitvinov.search.BinarySearch;
import org.IhorLitvinov.search.BinarySearch.BinarySearchMode;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.IntUnaryOperator;

public class BinarySearchTest {
    private BinarySearch binarySearch = new BinarySearch();

    private int[] array = {0, 1, 3, 5, 6, 7, 8, 9, 11, 12, 14};

    private int[] elseArray = {0, 1, 3, 15, 15, 15, 15, 19, 161, 412, 614};

    private IntUnaryOperator isMore4andLess10 = index -> {
        if (array[index] > 10) {
            return 1;
        } else if (array[index] < 4) {
            return -1;
        }
        return 0;
    };

    private IntUnaryOperator elseIsMore4andLess10 = index -> {
        if (elseArray[index] > 10) {
            return 1;
        } else if (elseArray[index] < 4) {
            return -1;
        }
        return 0;
    };

    private IntUnaryOperator isEquals15 = index -> {
        if (elseArray[index] > 15) {
            return 1;
        } else if (elseArray[index] < 15) {
            return -1;
        }
        return 0;
    };

    @Test
    public void testFindBiggestValueMore4andLess10() {
        int resultIndex = binarySearch
                .find(isMore4andLess10, BinarySearchMode.BIGGEST, 0, 10);

        int expectedResultIndex = 7;
        Assert.assertEquals(expectedResultIndex, resultIndex);
    }

    @Test
    public void testFindLeastValueMore4andLess10() {
        int resultIndex = binarySearch
                .find(isMore4andLess10, BinarySearchMode.LEAST, 0, 10);
        int expectedResultIndex = 3;
        Assert.assertEquals(expectedResultIndex, resultIndex);
    }

    @Test
    public void testFindValueMore4andLess10Between0and2indexes() {
        int resultIndex = binarySearch
                .find(isMore4andLess10, BinarySearchMode.LEAST, 0, 2);
        int expectedResultIndex = -1;
        Assert.assertEquals(expectedResultIndex, resultIndex);
    }

    @Test
    public void testFindValueMore4andLess10Between8and10indexes() {
        int resultIndex = binarySearch
                .find(isMore4andLess10, BinarySearchMode.LEAST, 8, 10);
        int expectedResultIndex = -1;
        Assert.assertEquals(expectedResultIndex, resultIndex);
    }

    @Test
    public void testFindValueMore4andLess10Between2and2indexes() {
        int resultIndex = binarySearch
                .find(isMore4andLess10, BinarySearchMode.LEAST, 2, 2);
        int expectedResultIndex = -1;
        Assert.assertEquals(expectedResultIndex, resultIndex);
    }

    @Test
    public void testFindLeastValueMore4andLess10Between8and8indexes() {
        int resultIndex = binarySearch
                .find(isMore4andLess10, BinarySearchMode.LEAST, 6, 6);
        int expectedResultIndex = 6;
        Assert.assertEquals(expectedResultIndex, resultIndex);
    }

    @Test
    public void testFindBiggestValueMore4andLess10Between8and8indexes() {
        int resultIndex = binarySearch
                .find(isMore4andLess10, BinarySearchMode.BIGGEST, 6, 6);
        int expectedResultIndex = 6;
        Assert.assertEquals(expectedResultIndex, resultIndex);
    }

    @Test
    public void testFindLeastValueMore4andLess10WhereItIsNot() {
        int resultIndex = binarySearch
                .find(elseIsMore4andLess10, BinarySearchMode.LEAST, 0, 10);
        int expectedResultIndex = -1;
        Assert.assertEquals(expectedResultIndex, resultIndex);
    }

    @Test
    public void testFindBiggestValueMore4andLess10WhereItIsNot() {
        int resultIndex = binarySearch
                .find(elseIsMore4andLess10, BinarySearchMode.BIGGEST, 0, 10);
        int expectedResultIndex = -1;
        Assert.assertEquals(expectedResultIndex, resultIndex);
    }

    @Test
    public void testFindLeastValueIsEquals15WithDuplicates() {
        int resultIndex = binarySearch
                .find(isEquals15, BinarySearchMode.LEAST, 0, 10);
        int expectedResultIndex = 3;
        Assert.assertEquals(expectedResultIndex, resultIndex);
    }

    @Test
    public void testFindBiggestValueIsEquals15WithDuplicates() {
        int resultIndex = binarySearch
                .find(isEquals15, BinarySearchMode.BIGGEST, 0, 10);
        int expectedResultIndex = 6;
        Assert.assertEquals(expectedResultIndex, resultIndex);
    }
}