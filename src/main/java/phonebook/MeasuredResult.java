package phonebook;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;

public class MeasuredResult<T> {
    private final T result;
    private final Duration timeTaken;

    public MeasuredResult(T result, Duration timeTaken) {
        this.result = result;
        this.timeTaken = timeTaken;
    }

    public static <T> MeasuredResult<T> measure(Callable<T> function) {
        try {
            LocalDateTime start = LocalDateTime.now();
            T result = function.call();
            LocalDateTime end = LocalDateTime.now();
            return new MeasuredResult<>(result, Duration.between(start, end));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static MeasuredResult<Void> measure(Runnable function) {
        return measure(() -> {
            function.run();
            return null;
        });
    }

    public T getResult() {
        return result;
    }

    public Duration getTimeTaken() {
        return timeTaken;
    }
}
