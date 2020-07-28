package phonebook;

import java.time.Duration;
import java.util.List;
import java.util.StringJoiner;

public class HashTableSearchStrategy implements SearchStrategy {

    @Override
    public String getName() {
        return "hash table";
    }

    @Override
    public SearchResult search(List<Entry> entries, List<String> queries) {
        MeasuredResult<HashTable<String, Entry>> buildingHashmapResult = MeasuredResult.measure(() -> {
            HashTable<String, Entry> table = new HashTable<>(entries.size());
            entries.forEach(e -> table.add(e.getName(), e));
            return table;
        });
        HashTable<String, Entry> map = buildingHashmapResult.getResult();
        MeasuredResult<Integer> result = MeasuredResult.measure(
                () -> (int) queries.stream().filter(name -> search(map, name)).count()
        );
        return new HashTableSearchResult(
                result.getResult(), queries.size(), result.getTimeTaken(), buildingHashmapResult.getTimeTaken()
        );
    }

    private boolean search(HashTable<String, Entry> entries, String name) {
        return entries.get(name) != null;
    }

    private static class HashTableSearchResult extends SimpleSearchResult {
        private final Duration creatingTime;

        public HashTableSearchResult(int foundEntriesCount, int queryCount, Duration searchingTime, Duration creatingTime) {
            super(foundEntriesCount, queryCount, searchingTime);
            this.creatingTime = creatingTime;
        }

        @Override
        public Duration getTimeTaken() {
            return creatingTime.plus(getSearchingTime());
        }

        @Override
        public String toString() {
            StringJoiner res = new StringJoiner(System.lineSeparator());

            res.add(super.toString());
            res.add(String.format("Creating time: %s", formatDuration(creatingTime)));
            res.add(String.format("Searching time: %s", formatDuration(getSearchingTime())));

            return res.toString();
        }
    }
}
