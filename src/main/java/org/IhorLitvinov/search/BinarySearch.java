package org.IhorLitvinov.search;

import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

public class BinarySearch {
    public enum BinarySearchMode {
        BIGGEST,
        LEAST
    }

    /**
     * This method used for finding index of the element
     * that satisfies the conditions using binary search
     *
     * @param compareElementWithIndexToRequired returns 0 if the element
     *                                          at the specified index fits
     *                                          the desired item, negative integer
     *                                          if it is less the desired item
     *                                          and positive integer if it is bigger
     * @param mode                              the method will return
     *                                          biggest index that fits or least
     * @return index of the item that satisfies the conditions or -1
     */
    public int find(
            IntUnaryOperator compareElementWithIndexToRequired,
            BinarySearchMode mode,
            int start,
            int end
    ) {

        IntPredicate indexFit = index -> compareElementWithIndexToRequired
                .applyAsInt(index) == 0;

        IntPredicate needToMoveStart;
        if (mode == BinarySearchMode.LEAST) {
            needToMoveStart = middle -> compareElementWithIndexToRequired
                    .applyAsInt(middle) < 0;
        } else {
            needToMoveStart = middle -> compareElementWithIndexToRequired
                    .applyAsInt(middle) <= 0;
        }

        int middle = start + (end - start) / 2;
        while (start != middle) {

            if (needToMoveStart.test(middle)) {
                start = middle;
            } else {
                end = middle;
            }

            middle = start + (end - start) / 2;
        }

        if (indexFit.test(middle)) {
            return middle;
        } else if (end > middle && indexFit.test(middle + 1)) {
            return middle + 1;
        }
        return -1;
    }
}
