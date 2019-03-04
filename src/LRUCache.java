import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache {
  private LinkedHashMap<Integer, Integer> cache;
  private int capacity;

  public LRUCache(int capacity) {
    this.cache = new LinkedHashMap(capacity*4/3, 0.75f, true){
      @Override
      protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > capacity;
      }
    };
    this.capacity = capacity;
  }

  public Integer get(Integer key) {
    return cache.get(key);
  }

  public Integer put(Integer key, Integer value) {
    return cache.put(key, value);
  }

}
