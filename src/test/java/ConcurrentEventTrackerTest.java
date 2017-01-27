import org.junit.Test;

public class ConcurrentEventTrackerTest {
    @Test
    public void test_Given_StartedEventTracker_Then_StopEventTracker_Expect_StoppedSuccessfully() {
        ConcurrentEventTracker tracker = new ConcurrentEventTracker();
        tracker.start();
        tracker.stop();
    }
}
