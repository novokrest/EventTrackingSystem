import org.junit.Assert;
import org.junit.Test;

public class ConcurrentEventTrackerTest {
    @Test
    public void test_Given_StartedEventTracker_Then_StopEventTracker_Expect_StoppedSuccessfully() {
        ConcurrentEventTracker tracker = new ConcurrentEventTracker();
        tracker.start();
        tracker.stop();
    }

    @Test
    public void test_Given_RegisterEvent_Expect_EventRegistered() {
        ConcurrentEventTracker tracker = new ConcurrentEventTracker();

        tracker.start();
        tracker.registerEvent();
        long total = tracker.getLastDayEventsCount();
        tracker.stop();

        Assert.assertEquals(1, total);
    }
}
