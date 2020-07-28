package phonebook;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class BubbleSortJumpSearchStrategy implements SearchStrategy {

    private final Duration timeLimitToSort;
    private final SearchStrategy backingSearchStrategy;

    public BubbleSortJumpSearchStrategy(Duration timeLimitToSort, SearchStrategy backingSearchStrategy) {
        this.timeLimitToSort = timeLimitToSort;
        this.backingSearchStrategy = backingSearchStrategy;
    }

    @Override
    public SearchResult search(List<Entry> entries, List<String> queries) {
        MeasuredResult<Boolean> sortingResult = sort(entries);
        boolean failed = !sortingResult.getResult();
        if (failed) {
            SearchResult result = backingSearchStrategy.search(entries, queries);
            return new SortedSearchResult(
                    result.getFoundEntriesCount(), queries.size(),
                    sortingResult.getTimeTaken(), result.getSearchingTime(), true, backingSearchStrategy.getName()
            );
        }

        MeasuredResult<Integer> searchResult = MeasuredResult.measure(
                () -> (int) queries.stream().filter(name -> search(entries, name)).count()
        );
        return new SortedSearchResult(
                searchResult.getResult(), queries.size(), sortingResult.getTimeTaken(), searchResult.getTimeTaken()
        );
    }

    private MeasuredResult<Boolean> sort(List<Entry> entries) {
        LocalDateTime start = LocalDateTime.now();
        Duration sortingTime = Duration.ZERO;
        for (int i = 0; i < entries.size(); i++) {
            if (sortingTime.compareTo(timeLimitToSort) > 0) return new MeasuredResult<>(false, sortingTime);
            boolean anySwap = false;
            for (int j = 0; j + 1 < entries.size() - i; j++) {
                if (entries.get(j).getName().compareTo(entries.get(j + 1).getName()) > 0) {
                    Collections.swap(entries, j, j + 1);
                    anySwap = true;
                }
            }
            sortingTime = Duration.between(start, LocalDateTime.now());
            if (!anySwap) break;
        }
        return new MeasuredResult<>(true, sortingTime);
    }

    private boolean search(List<Entry> entries, String name) {
        if (entries.size() == 1) return entries.get(0).getName().equals(name);
        int step = Math.max(1, (int) Math.sqrt(entries.size()));
        for (int start = 0; start < entries.size(); ) {
            int end = Math.min(entries.size(), start + step);
            if (entries.get(end - 1).getName().compareTo(name) >= 0) return search(entries.subList(start, end), name);
            start = end;
        }
        return false;
    }

    @Override
    public String getName() {
        return "bubble sort + jump search";
    }
}
