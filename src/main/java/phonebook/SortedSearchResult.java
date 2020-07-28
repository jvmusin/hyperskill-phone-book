package phonebook;

import java.time.Duration;
import java.util.StringJoiner;

public class SortedSearchResult extends SimpleSearchResult {

    private final Duration sortingTime;
    private final boolean sortingFailed;
    private final String backingSearchStrategyName;

    public SortedSearchResult(int foundEntriesCount, int queryCount, Duration searchingTime, Duration sortingTime, boolean sortingFailed, String backingSearchStrategyName) {
        super(foundEntriesCount, queryCount, searchingTime);
        this.sortingTime = sortingTime;
        this.sortingFailed = sortingFailed;
        this.backingSearchStrategyName = backingSearchStrategyName;
    }

    public SortedSearchResult(int foundEntriesCount, int entriesToSearchCount, Duration searchingTime, Duration sortingTime) {
        this(foundEntriesCount, entriesToSearchCount, searchingTime, sortingTime, false, null);
    }

    @Override
    public Duration getSortingTime() {
        return sortingTime;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(System.lineSeparator());

        sj.add(super.toString());

        String sortingTimeStr = String.format("Sorting time: %s", formatDuration(sortingTime));
        if (sortingFailed) sortingTimeStr += " - STOPPED, moved to " + backingSearchStrategyName;
        sj.add(sortingTimeStr);

        sj.add(String.format("Searching time: %s", formatDuration(getSearchingTime())));

        return sj.toString();
    }
}
