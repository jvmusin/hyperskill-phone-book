package phonebook;

import java.time.Duration;

public interface SearchResult {
    int getFoundEntriesCount();

    int getQueryCount();

    default Duration getTimeTaken() {
        return getSortingTime().plus(getSearchingTime());
    }

    default Duration getSortingTime() {
        return Duration.ZERO;
    }

    Duration getSearchingTime();
}
