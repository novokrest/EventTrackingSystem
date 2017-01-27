package core;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicValue implements NumberEx {
    private final AtomicInteger value;

    public AtomicValue() {
        this(0);
    }

    public AtomicValue(int initialValue) {
        value = new AtomicInteger(initialValue);
    }

    @Override
    public void increment() {
        value.incrementAndGet();
    }

    @Override
    public void set(int count) {
        value.set(count);
    }

    @Override
    public int getInt() {
        return value.intValue();
    }
}
