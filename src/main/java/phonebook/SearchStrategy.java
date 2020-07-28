package phonebook;

import java.util.List;

public interface SearchStrategy {
    String getName();

    SearchResult search(List<Entry> entries, List<String> queries);

    default SearchResult run(List<Entry> entries, List<String> queries) {
        System.out.printf("Start searching (%s)...%n", getName());
        SearchResult result = search(entries, queries);
        System.out.println(result);
        return result;
    }
}
