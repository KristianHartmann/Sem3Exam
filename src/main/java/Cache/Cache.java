package Cache;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.core.Response;

public class Cache {
    private final int maxEntries;
    private final Map<String, Response> cache;

    public Cache(final int maxEntries) {
        this.maxEntries = maxEntries;
        this.cache = new LinkedHashMap<String, Response>() {
            protected boolean removeEldestEntry(Map.Entry<String, Response> eldest) {
                return size() > maxEntries;
            }
        };
    }

    public void clear(){
        cache.clear();
    }
    public void put(String key, Response value) {
        cache.put(key, value);
    }

    public Response get(String key) {
        return cache.get(key);
    }

    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }
}
