public class TimeWatcher {
    private final long startTime = System.nanoTime();

    public long elapsedTime() {
        return System.nanoTime() - startTime;
    }
}
