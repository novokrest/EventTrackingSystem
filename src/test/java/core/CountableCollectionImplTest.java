package core;

import core.CountableCollectionImpl;
import org.junit.Assert;
import org.junit.Test;

public class CountableCollectionImplTest {
    @Test(expected = RuntimeException.class)
    public void test_Try_AddZeroCountOfElements_Should_FailedToAddElements() {
        CountableCollectionImpl<Integer> collection = new CountableCollectionImpl<Integer>();

        collection.add(1, 0);
    }

    @Test(expected = RuntimeException.class)
    public void test_Try_RemoveZeroCountOfElements_Should_FailedToAddElements() {
        CountableCollectionImpl<Integer> collection = new CountableCollectionImpl<Integer>();

        collection.remove(1, 0);
    }

    @Test(expected = RuntimeException.class)
    public void test_Try_RemoveMoreElementsThanExists_Expect_FailedToRemove() {
        CountableCollectionImpl<Integer> collection = new CountableCollectionImpl<Integer>();
        collection.add(1, 10);

        collection.remove(1, 11);
    }

    @Test
    public void test_Try_GetNonAddedElementCount_Should_ReturnZero() {
        CountableCollectionImpl<Integer> collection = new CountableCollectionImpl<Integer>();
        Assert.assertEquals(0, collection.count(1));
        Assert.assertEquals(0, collection.count(10));
        Assert.assertEquals(0, collection.count(100));
    }

    @Test
    public void test_Given_AddElementSeveralTimes_Then_GetElementCount_Should_ReturnTotalCount() {
        CountableCollectionImpl<Integer> collection = new CountableCollectionImpl<Integer>();
        collection.add(1, 10);
        collection.add(2, 20);
        collection.add(1, 11);
        collection.add(2, 22);

        Assert.assertEquals(21, collection.count(1));
        Assert.assertEquals(42, collection.count(2));
    }

    @Test
    public void test_Given_AddAndRemoveAll_Then_GetElementCount_Should_ReturnZero() {
        CountableCollectionImpl<Integer> collection = new CountableCollectionImpl<Integer>();
        collection.add(3, 100);
        collection.remove(3, 100);

        Assert.assertEquals(0, collection.count(3));
    }

    @Test
    public void test_Given_AddAndRemoveElementSeveralTimes_Then_GetElementCount_Should_ReturnTotalCount() {
        CountableCollectionImpl<Integer> collection = new CountableCollectionImpl<Integer>();
        collection.add(5, 10);
        collection.remove(5, 9);
        collection.add(5, 3);
        collection.remove(5, 4);
        collection.add(5, 55);

        Assert.assertEquals(55, collection.count(5));
    }

    @Test
    public void test_Given_AddAndRemoveSpecifiedElement_Then_GetSize_Should_SameAsBefore() {
        CountableCollectionImpl<Integer> collection = new CountableCollectionImpl<Integer>();

        Assert.assertEquals(0, collection.size());

        collection.add(1, 10);
        collection.add(2, 20);
        Assert.assertEquals(2, collection.size());

        collection.remove(1, 10);
        Assert.assertEquals(1, collection.size());

        collection.remove(2, 10);
        collection.remove(2, 10);
        Assert.assertEquals(0, collection.size());
    }
}
