import core.Verifiers;

public class TimeIntervalNumberCalculator {
    private final int intervalLength;

    public TimeIntervalNumberCalculator(int intervalLength) {
        Verifiers.verify(intervalLength > 0, "Incorrect interval length: %d", intervalLength);
        this.intervalLength = intervalLength;
    }

    public long calculateIntervalNumber(long elapsed) {
        Verifiers.verify(elapsed >= 0, "Incorrect elapsed time: %d", elapsed);
        return (elapsed - (elapsed % intervalLength)) / intervalLength;
    }
}
