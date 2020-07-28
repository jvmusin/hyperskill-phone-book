package phonebook;

@SuppressWarnings("unchecked")
public class HashTable<K extends Comparable<K>, V> {

    private MapEntry<K, V>[] entries;

    public HashTable(int size) {
        if (size <= 0) size = 1;
        if ((size & (size - 1)) != 0) size = Integer.highestOneBit(size) << 2;
        entries = new MapEntry[size];
    }

    private int findPos(K key) {
        int hash = getHash(key);
        for (int i = 0; i < entries.length; i++) {
            int at = (hash + i) & (entries.length - 1);
            if (entries[at] == null || entries[at].key.equals(key)) {
                return at;
            }
        }
        return -1;
    }

    private void grow() {
        MapEntry<K, V>[] old = entries;
        entries = new MapEntry[old.length << 1];
        for (MapEntry<K, V> entry : old) {
            add(entry.key, entry.value);
        }
    }

    private int getHash(K key) {
        return key.hashCode() & (entries.length - 1);
    }

    public void add(K key, V value) {
        int pos = findPos(key);
        if (pos == -1) {
            grow();
            pos = findPos(key);
        }
        entries[pos] = new MapEntry<>(key, value);
    }

    public V get(K key) {
        int at = findPos(key);
        MapEntry<K, V> entry;
        if (at == -1 || (entry = entries[at]) == null) return null;
        return entry.value;
    }

    private static class MapEntry<K, V> {
        final K key;
        final V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
