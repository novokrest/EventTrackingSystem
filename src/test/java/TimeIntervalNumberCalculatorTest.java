import org.junit.Assert;
import org.junit.Test;

public class TimeIntervalNumberCalculatorTest {
    @Test(expected = RuntimeException.class)
    public void test_Given_IntervalLessThanZero_Expect_FailedToInitializeCalculator() {
        new TimeIntervalNumberCalculator(-1);
    }

    @Test(expected = RuntimeException.class)
    public void test_Given_IntervalEqualsZero_Expect_FailedToInitializeCalculator() {
        new TimeIntervalNumberCalculator(0);
    }

    @Test(expected = RuntimeException.class)
    public void test_Given_ElapsedTimeLessThanZero_Expect_FailedToCalculateIntervalNumber() {
        new TimeIntervalNumberCalculator(10).calculateIntervalNumber(-1);
    }

    @Test
    public void test_Given_ElapsedTime_Expect_CalculateCorrectIntervalNumber() {
        testCalculateCorrectIntervalNumberByElapsedTime(5, 0, 0);
        testCalculateCorrectIntervalNumberByElapsedTime(5, 1, 0);
        testCalculateCorrectIntervalNumberByElapsedTime(5, 4, 0);
        testCalculateCorrectIntervalNumberByElapsedTime(5, 5, 1);
        testCalculateCorrectIntervalNumberByElapsedTime(5, 9, 1);
        testCalculateCorrectIntervalNumberByElapsedTime(5, 10, 2);
        testCalculateCorrectIntervalNumberByElapsedTime(5, 15, 3);
        testCalculateCorrectIntervalNumberByElapsedTime(5, 17, 3);
    }

    private void testCalculateCorrectIntervalNumberByElapsedTime(int intervalLength, long elapsedTime, long expectedIntervalNumber) {
        TimeIntervalNumberCalculator calculator = new TimeIntervalNumberCalculator(intervalLength);

        long actualIntervalNumber = calculator.calculateIntervalNumber(elapsedTime);

        Assert.assertEquals(expectedIntervalNumber, actualIntervalNumber);
    }
}
