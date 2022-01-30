import java.util.Set;

/**
 * A simplified version of Java's Map interface. This version has the important
 * stuff and none of the duplications in the Java interface
 * @author gtowell
 * Created: Sep 2020
 * Modified: Oct 2, 2020
 */
public interface Map206Interface<K, V> {
    
    /**
     * Add a key-value pair to the map. If the key is already in the
     * map, then the old value is replaced. Otherwise this adds a new
     * key-value pair
     * 
     * @param key   the key
     * @param value the value
     */
    public void put(K key, V val);
    
    /**
     * Get the value stored in the map given the key. If the key is not in the map
     * return null.
     * 
     * @param key the key
     * @return the value associated with the key
     */
    public V get(K key);


    /**
     * @return true iff the hashtable contains the key.
     * @param key the ket to check for
     */
    public boolean containsKey(K key);
    
    
    /**
     * The number of distinct keys in the map.
     * Depending on the implementation this could take O(n) time 
     * or O(1) time. Implementers are strongly encouraged to use
     * an O(1) implementation as size may be called often
     * 
     * @return The number of distinct keys in the hashtable
     */
    public int size();

    
    /**
     * All of the keys in the map.  As this is a set, the items returned must be unique.
     * This method allows users of the map to see all of the keys.
     * in this way, users can also get to all of the values.  Note that the
     * number of elements in the set returned must be equal to the valkue returned
     * by the size method
     * @return All of the keys in the map
     */
    public Set<K> keySet();
}
