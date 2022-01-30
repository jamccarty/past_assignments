import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
/**
 * Implements the interface. A concrete implementaion of a map with all
 * of the ugly search
 * @author gtowell
 * Created: Sep 21, 2020
 * 
 * @author jmccarty Modified: March 13, 2021
 * - added returnKeyArrayList() and returnValueArrayList() methods
 */
public class Map206<K,V> implements Map206Interface<K,V>{

    /** The underlying data structure to actually do the storage */
    private ArrayList<Pair<K,V>> underlying = new ArrayList<>();

    /** Holds key-value pairs together 
     * The only time it is OK (or even good) to have public instance vars is
     * in private classes.
     */
    private class Pair<L,W> {
        // the key.  Once set it canot be changed
        public final L key;
        // the value
        public W value;
        //Create a key value pair.
        Pair(L ky, W val) {
            key=ky;
            value=val;
        }
    }

    /**
     * Set a key value pair.
     * Will overwrite value if key already present.
     * @param key the key
     * @param val the new value
     */
    public void put(K key, V val) {
        Pair<K,V> pair = iContainsKey(key);
        if (pair==null) {
            Pair<K,V> np = new Pair<>(key, val);
            underlying.add(np);
        } else {
            pair.value=val;
        }
    }

    /**
     * Get the value associated with a key
     * @param key the key
     * @return the associated value
     */
    public V get(K key) {
        Pair<K,V> pair = iContainsKey(key);
        if (pair!=null)
            return pair.value;
        return null;
    }

    /**
     * does the Map contain the key?
     * @param key the key in question
     * @return true iff the key is in the map
     */
    public boolean containsKey(K key) {
        return null != iContainsKey(key);
    }

    private Pair<K,V> iContainsKey(K ky) {
        for (Pair<K,V> pair : underlying) {
            if (pair.key.equals(ky)) {
                return pair;
            }
        }
        return null;
    }

    /**
     * The number of items in the map
     * @return The number of items in the map
     */
    public int size() {
        return underlying.size();
    }

    /**
     * @return ArrayList of all keys in map
     */
    public ArrayList<K> returnKeyArrayList(){
        ArrayList<K> keys = new ArrayList<>();
        for(Pair<K,V> pair : underlying){
            keys.add(pair.key);
        }
        return keys;
    }

    /**
     * @return ArrayList of all values in map
     */
    public ArrayList<V> returnValueArrayList(){
        ArrayList<V> values = new ArrayList<>();
        for(Pair<K,V> pair : underlying){
            values.add(pair.value);
        }
        return values;
    }
    /**
     * All of the keys in the map
     * This method allows users of the map to see all of the keys.
     * That this returns a Set is to make this consistent with the Java Map interface.
     * @return All of the keys in the map
     */
    public Set<K> keySet() {
        TreeSet<K> set = new TreeSet<>();
        for (Pair<K,V> pair : underlying) {
            set.add(pair.key);  
        }
        return set;
    }

}
