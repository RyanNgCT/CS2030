import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

class KeyableMap<V extends Keyable> implements Keyable {
    private final String key;
    private final ImmutableMap<String, V> imMap;

    KeyableMap(String key) {
        this.key = key;
        this.imMap = new ImmutableMap<String, V>();
    }

    KeyableMap(String key, ImmutableMap<String, V> imMap) {
        this.key = key;
        this.imMap = imMap;
    }

    KeyableMap(KeyableMap<V> kMap) {
        this.imMap = kMap.imMap;    //keyMap.getMap();
        this.key = kMap.key;
    }

    public String getKey() {
        return this.key;
    }

    //private ImmutableMap<String, V> getMap() {
    //    return this.imMap;
    //}

    public KeyableMap<V> put(V item) {
        return new KeyableMap<V>(this.getKey(),
                                 this.imMap.put(item.getKey(), item));
    }

    Optional<V> get(String key) {
        return this.imMap.get(key);
    }

    @Override
    public String toString() {
        String fullStr = this.getKey() + ": {";
        Iterator<Map.Entry<String, V>> itr = this.imMap.iterator();

        while (itr.hasNext()) {
            V element = itr.next().getValue();
            fullStr += element.toString();
            if (itr.hasNext()) {
                fullStr += ", ";
            }
        }
        fullStr += "}";
        return fullStr;
    }
}
