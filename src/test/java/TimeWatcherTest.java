import org.junit.Assert;
import org.junit.Test;

public class TimeWatcherTest {
    @Test
    public void test_Try_GetElapsedTimeTwoTimes_Should_ReturnSecondTimeNotLessThanFirstTime() {
        TimeWatcher watcher = new TimeWatcher();
        final long t1 = watcher.elapsedTimeNs();
        final long t2 = watcher.elapsedTimeNs();
        Assert.assertTrue(t2 >= t1);
    }
}
