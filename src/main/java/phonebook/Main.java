package phonebook;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Entry> entries = Files.lines(Paths.get("directory.txt"))
                .map(s -> s.split(" ", 2))
                .map(s -> new Entry(s[0], s[1]))
                .collect(Collectors.toList());
        List<String> queries = Files.readAllLines(Paths.get("find.txt"));

        SearchStrategy linearSearchStrategy = new LinearSearchStrategy();
        SearchResult result = linearSearchStrategy.run(new ArrayList<>(entries), queries);
        System.out.println();

        SearchStrategy jumpSearchStrategy = new BubbleSortJumpSearchStrategy(result.getSearchingTime().multipliedBy(10), linearSearchStrategy);
        jumpSearchStrategy.run(new ArrayList<>(entries), queries);
        System.out.println();

        SearchStrategy binarySearchStrategy = new QuickSortBinarySearchStrategy();
        binarySearchStrategy.run(new ArrayList<>(entries), queries);
        System.out.println();

        HashTableSearchStrategy hashTableSearchStrategy = new HashTableSearchStrategy();
        hashTableSearchStrategy.run(new ArrayList<>(entries), queries);
    }
}
