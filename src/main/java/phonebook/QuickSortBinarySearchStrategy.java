package phonebook;

import java.util.Collections;
import java.util.List;

public class QuickSortBinarySearchStrategy implements SearchStrategy {
    @Override
    public String getName() {
        return "quick sort + binary search";
    }

    @Override
    public SearchResult search(List<Entry> entries, List<String> queries) {
        MeasuredResult<Void> sortingResult = MeasuredResult.measure(() -> quickSort(entries));
        MeasuredResult<Integer> searchingResult = MeasuredResult.measure(
                () -> (int) queries.stream().filter(name -> search(entries, name)).count()
        );
        return new SortedSearchResult(
                searchingResult.getResult(), queries.size(), searchingResult.getTimeTaken(), sortingResult.getTimeTaken()
        );
    }

    private void quickSort(List<Entry> entries) {
        quickSort(entries, 0, entries.size() - 1);
    }

    private void quickSort(List<Entry> entries, int from, int to) {
        if (from >= to) return;
        int pivot = partition(entries, from, to);
        quickSort(entries, from, pivot - 1);
        quickSort(entries, pivot + 1, to);
    }

    private int partition(List<Entry> entries, int from, int to) {
        Entry pivot = entries.get(to);
        int at = from;
        for (int i = from; i <= to; i++) {
            if (entries.get(i).getName().compareTo(pivot.getName()) <= 0) {
                Collections.swap(entries, at++, i);
            }
        }
        return at - 1;
    }

    private boolean search(List<Entry> entries, String name) {
        int l = -1;
        int r = entries.size();
        while (r - l > 1) {
            int m = l + r >> 1;
            if (entries.get(m).getName().compareTo(name) <= 0) l = m;
            else r = m;
        }
        return l != -1 && entries.get(l).getName().equals(name);
    }
}
