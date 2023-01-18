import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collections;

class Dictionary {
    private final Map<String, List<String>> dictionary = new ConcurrentHashMap<>();

    public void add(String token, String docId) {
        dictionary.putIfAbsent(token, Collections.synchronizedList(new ArrayList<>()));
        dictionary.computeIfPresent(token, (key, value) -> {
            if (value.contains(docId)) return value;
            value.add(docId);
            return value;
        });
    }

    public List<String> search(String token) {
        return dictionary.getOrDefault(token, new ArrayList<>());
    }

    public Map<String, List<String>> getDictionary() {
        return dictionary;
    }
}