import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * An incomplete Hashtable using probing. The get and put functions are stubs only.
 * They must be implemented. A completed implementation need not handle
 * deletions.  Therefore, the implementation need not worry about tombstones,
 * rehashing to reduce the size of the table, or rehashing to reduce the number 
 * of tombstones
 * 
 * @param <K> the type of key
 * @param <V> the type of value 
 * @author gtowell 
 * @author jmccarty
 * Created:  Sep 27, 2020 
 * Modified Oct 2, 2020
 * Modified March 11, 2021
 * - added workable get(), put(), and toString() methods to the ProbeHTInc class,
 * - added getKey() and getValue() to the Pair class
 * Modified March 12, 2021
 * - added returnKeyArrayList() and returnValueArrayList() methods
 *   to the Probe HTInc class
 */
public class ProbeHTInc<K, V> implements Map206Interface<K, V> {
    /**
     * Small inner class to group together key,value pairs
     */
    protected class Pair<L, W> {
        /** The key, cannot be changed */
        final L key;
        /**
         * The value. It can be changed as a second put with the key will change the
         * value
         */
        W value;

        /**
         * Returns Pair key
         * @return key 
         */
        public L getKey(){
            return key;
        }

        /**
         * Returns Pair value
         * @return value
         */
        public W getValue(){
            return value;
        }

        /**
         * Initialize the node
         */
        public Pair(L ll, W ww) {
            key = ll;
            value = ww;
        }

        /** Print the node, and all subsequent nodes in the linked list */
        public String toString() {
            return "<" + key + ", " + value + ">";
        }
    }

    /** A Constant .. One of the cases in which static are acceptable
     * This one specifies the maximum number of tombstones allowed before 
     * rehashing for tombstone accumulation
     */
    /** When the hashtable needs to grow, by what factor should it grow */
    private static final int GROWTH_RATE = 2;
    /** How full the table should be before initiating rehash/growth */
    private static final double MAX_OCCUPANCY = 0.60;
    /** The default size of the backing array */
    private static int DEFAULT_CAPACITY = 1009;
   /** The array in which the hashtable stores data */
    private Pair<K, V>[] backingArray;
    /** The number of active items in the hashtable */
    private int itemCount;
    
 
    /** Default initialization */
    public ProbeHTInc() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Initialize a hashtable of the given size
     * 
     * @param size the size of the hashtable to create
     */
    @SuppressWarnings("unchecked")
    public ProbeHTInc(int size) {
        // Cannot make an array in which you mention a parameterized type.
        // So just make the generic array. This is a narrowing cast so it does not
        // even need to be explicitly case.
        backingArray = new Pair[size];
        itemCount = 0;
     }

    /**
     * The hash function. Just uses the java object hashvalue. 
     * @param key the Key to be hashed
     * @return the hash value
     */
    private int hash(K key) {
        return Math.abs(key.hashCode()) % backingArray.length;
    }

    /**
     * The number of active items in the hashtable
     * @return The number of active items in the hashtable
     */
    public int size() {
        return itemCount;
    }

    /**
     * Add a key-value pair to the hashtable. If the key is already in the
     * hashtable, then the old value is replaced. Otherwise this adds a new
     * key-value pair
     * 
     * @param key   the key
     * @param value the value
     */
    public void put(K key, V value) {
        int location = this.hash(key); //location of new Pair in hash table
        int quadraticAdd = 1; //variable to add to location in case of quadratic probe

        if(this.size() >= MAX_OCCUPANCY * backingArray.length){
            this.rehash(backingArray.length * GROWTH_RATE);
        }

        //if location is empty, adds new pair
        if(backingArray[location] == null){
            backingArray[location] = new Pair<>(key, value);
        }else{
            //quadratic probes until finds empty location OR location with matching key
            while(backingArray[location] != null && !backingArray[location].getKey().equals(key)){
                location = location + (quadraticAdd * quadraticAdd);
                quadraticAdd++;
                if(location >= (backingArray.length - 1)){
                    location = Math.abs(((backingArray.length - 1) - location));
                }
            }

            //creates new Pair in this location (replacing old Pair with matching key if necessary)
            backingArray[location] = new Pair<>(key, value);
        }

        //if new pair is not replacing existing key, increments itemCount
        if(!this.containsKey(key)){
            itemCount++;
        }
    }

    @SuppressWarnings("unchecked")
    /**
     * Rehash the current table. This should be done rarely as it is expensive
     * @param newSize the size of the table after rehashing
     */
    private void rehash(int newSize) {
        Pair<K, V>[] oldArray = backingArray;
        itemCount = 0;
        backingArray = new Pair[newSize];
        for (int i = 0; i < oldArray.length; i++) {
            if (oldArray[i] != null) {
                put(oldArray[i].key, oldArray[i].value);
            }
        }
    }

    /**
     * Gets the value associated with a key
     * @param key the key associated with the value
     * @return the associated value, or null if value cannot be found
     * @throws NullPointerException
     */
    public V get(K key) throws NullPointerException{
        int location = hash(key);//hashes key to produce ideal location

        /**
         * quadratically incremented variable added to location for quadratic probing
         * for if ideal location is not the correct one
         */
        int quadraticAdd = 0; 

        //while still searching for key
        //increments by h(x) = loc[x] + y^2 where y increments by 1 in each loop
        //if quadratic probe hits an empty space, will return null
        //once it finds a matching key, probe will return a value
        if(backingArray[location] == null){
            return null;
        }
        while(!backingArray[location].getKey().equals(key)){
            location = location + (quadraticAdd * quadraticAdd);
            quadraticAdd++;

            if(location >= (backingArray.length - 1)){
                location = Math.abs(((backingArray.length - 1) - location));
            }

            if(backingArray[location] == null){
                return null;
            }
        }
        return backingArray[location].getValue();
    }

    

    @Override
    /**
     * Does the hashtable contain the key
     * @param key the key
     * @return true iff the key is in the hashtable
     */
    public boolean containsKey(K key) {
        return null != get(key);
    }

    @Override
    /**
     * The complete set of keys active in the hashtable.
     * @return a set containing all of the keys in the hashtable
     */
    public Set<K> keySet() {
        TreeSet<K> set = new TreeSet<>();
        for (Pair<K,V> pr : backingArray) {
            if (pr!=null) {
                set.add(pr.key);
            }
        } 
        return set;
    }

    public ArrayList<K> returnKeyArrayList(){
        ArrayList<K> keys = new ArrayList<>();
        for(Pair<K,V> pr : backingArray){
            if(pr != null){
                keys.add(pr.key);
            }
        }
        return keys;
    }

    public ArrayList<V> returnValueArrayList(){
        ArrayList<V> values = new ArrayList<>();
        for(Pair<K,V> pr : backingArray){
            if(pr != null){
                values.add(pr.getValue());
            }
        }
        return values;
    }

    public Set<V> valueSet() {
        TreeSet<V> set = new TreeSet<>();
        for(Pair<K,V> pr : backingArray) {
            if(pr!=null){
                set.add(pr.getValue());
            }
        }
        return set;
    }

    @Override
    public String toString(){
        String probeString = "";
        for(Pair<K,V> pr : backingArray){
            if(pr != null){
                probeString = probeString + " " + pr;
            }
        }
        return probeString;
    }

    /**
     * main() method for testing purposes only
     */
    public static void main(String[] args) {
        ProbeHTInc<Integer, String> probe = new ProbeHTInc<>(10);
        probe.put(0, "walnut");
        probe.put(10, "applesauce");
        probe.put(1, "orange juice");
        System.out.println("getting 1, orange juice vv");
        probe.get(1);
        System.out.println("getting 10, applesauce");
        probe.get(10);
        System.out.println(probe.containsKey(0));
    }
}