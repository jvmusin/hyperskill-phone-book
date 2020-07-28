package phonebook;

import java.time.Duration;

public class SimpleSearchResult implements SearchResult {
    private final int foundEntriesCount;
    private final int queryCount;
    private final Duration searchingTime;

    public SimpleSearchResult(int foundEntriesCount, int queryCount, Duration searchingTime) {
        this.foundEntriesCount = foundEntriesCount;
        this.queryCount = queryCount;
        this.searchingTime = searchingTime;
    }

    @Override
    public int getFoundEntriesCount() {
        return foundEntriesCount;
    }

    @Override
    public int getQueryCount() {
        return queryCount;
    }

    @Override
    public Duration getSearchingTime() {
        return searchingTime;
    }

    protected String formatDuration(Duration timeTaken) {
        return String.format("%d min. %d sec. %d ms.",
                timeTaken.toMinutesPart(), timeTaken.toSecondsPart(), timeTaken.toMillisPart());
    }

    @Override
    public String toString() {
        return String.format("Found %d / %d entries. Time taken: %s",
                foundEntriesCount, queryCount, formatDuration(getTimeTaken()));
    }
}