package core;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListCircularTest {
    @Test
    public void test_Given_EmptyList_Should_TraverseSuccessfully() {
        List<Object> emptyList = new ArrayList<>();

        for (Object ignored : ListEx.toCircular(emptyList, 0)) {
            Assert.fail();
        }
    }

    @Test
    public void test_Given_NonEmptyList_Then_IterateFromSpecifiedPosition_Should_IterateAll() {
        test_Given_NonEmptyList_Then_IterateFromSpecifiedPosition_Should_IterateAll(
                Arrays.asList(1, 2, 3, 4, 5),
                0,
                Arrays.asList(1, 2, 3, 4, 5));

        test_Given_NonEmptyList_Then_IterateFromSpecifiedPosition_Should_IterateAll(
                Arrays.asList(1, 2, 3, 4, 5),
                1,
                Arrays.asList(2, 3, 4, 5, 1));

        test_Given_NonEmptyList_Then_IterateFromSpecifiedPosition_Should_IterateAll(
                Arrays.asList(1, 2, 3, 4, 5),
                -1,
                Arrays.asList(5, 1, 2, 3, 4));
    }

    private <E> void test_Given_NonEmptyList_Then_IterateFromSpecifiedPosition_Should_IterateAll(List<E> inputList,
                                                                                                 int startPosition,
                                                                                                 List<E> expectedIteratedOrder) {
        List<E> actualIteratedOrder = new ArrayList<>(inputList.size());
        for (E i : ListEx.toCircular(inputList, startPosition)) {
            actualIteratedOrder.add(i);
        }

        Assert.assertArrayEquals(expectedIteratedOrder.toArray(), actualIteratedOrder.toArray());
    }
}
