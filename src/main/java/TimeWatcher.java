public class TimeWatcher {
    private final long startTime = System.nanoTime();

    public long elapsedTimeNs() {
        return System.nanoTime() - startTime;
    }
}
