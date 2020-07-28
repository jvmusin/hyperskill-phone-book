package phonebook;

import java.util.List;

public class LinearSearchStrategy implements SearchStrategy {
    @Override
    public SearchResult search(List<Entry> entries, List<String> queries) {
        MeasuredResult<Integer> result = MeasuredResult.measure(
                () -> (int) queries.stream().filter(name -> search(entries, name)).count()
        );
        return new SimpleSearchResult(result.getResult(), queries.size(), result.getTimeTaken());
    }

    private boolean search(List<Entry> entries, String name) {
        return entries.stream().anyMatch(entry -> entry.getName().equals(name));
    }

    @Override
    public String getName() {
        return "linear search";
    }
}
