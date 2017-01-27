import core.Verifiers;

public class TimeIntervalNumberCalculator {
    private final long timeIntervalLengthNs;

    public TimeIntervalNumberCalculator(long timeIntervalLengthNs) {
        Verifiers.verify(timeIntervalLengthNs > 0, "Incorrect interval length: %d", timeIntervalLengthNs);
        this.timeIntervalLengthNs = timeIntervalLengthNs;
    }

    public long calculateIntervalNumber(long elapsedNs) {
        Verifiers.verify(elapsedNs >= 0, "Incorrect elapsedNs time: %d", elapsedNs);
        return (elapsedNs - (elapsedNs % timeIntervalLengthNs)) / timeIntervalLengthNs;
    }
}
