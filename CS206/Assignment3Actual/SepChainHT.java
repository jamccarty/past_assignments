import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * A fairly basic implementation of a separate chanining hashtable
 * This implementation does not have rehashing to increase 
 * the size of the underlying array when the HT get full-ish.
 * This implementation also lacks removing a key from the hashtable.
 * @param <K> the tpe of key
 * @param <V> the type of value Implements full separate chaining, but not
 *            rehashing. Similarly, the size of the underlying table, once it is
 *            created, cannot be changed.
 * @author gtowell Created: April 25, 2020 Modified: Sep 23, 2020 to use
 *         ArrayList
 * 
 * @author jmccarty Modified: March 13, 2021
 * - added returnKeyArrayList() and returnValueArrayList() methods
 */
public class SepChainHT<K, V> implements Map206Interface<K, V> {

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
         * Initialize the node
         */
        public Pair(L ll, W ww) {
            key = ll;
            value = ww;
        }

        /** Print the pair */
        public String toString() {
            return "<" + key + ", " + value + ">";
        }
    }

    /**
     * The array holding the hashtable data. Yes, this is an array of array lists
     */
    private ArrayList<Pair<K, V>>[] backingArray;

    /** The default size of the backing array */
    private static int DEFAULT_CAPACITY = 1009;

    /** For polynomial accumulation, the multiplier. */
    static int POLY_MULT = 33;

    /** Default initialization */
    public SepChainHT() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Initialize a hashtable of the given size
     * 
     * @param size the size of the hashtable to create
     */
    @SuppressWarnings("unchecked")
    public SepChainHT(int size) {
        // Cannot make an array in which you mention a parameterized type.
        // So just make the generic array. This is a narrowing cast so it does not
        // even need to be explicit.
        backingArray = new ArrayList[size];
    }

    /**
     * Implemets polynomical accumulation on strings. Since every object can be
     * translated into a string This can be run on an arbitrary object with no loss
     * of generality.
     * 
     * @param ss the string to generate a hash value for
     * @return the hash value
     */
    private int stringHasher(String ss) {
        BigInteger ll = new BigInteger("0");
        for (int i = 0; i < ss.length(); i++) {
            BigInteger bb = BigInteger.valueOf(POLY_MULT).pow(i).multiply(BigInteger.valueOf((int) ss.charAt(i)));
            ll = ll.add(bb);
            // System.out.println((int)ss.charAt(i) + " " + ll);
        }
        ll = ll.mod(BigInteger.valueOf(backingArray.length));
        return ll.intValue();
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
        int loc = stringHasher(key.toString());
        Pair<K, V> newPair = new Pair<>(key, value);
        if (backingArray[loc] == null) {
            backingArray[loc] = new ArrayList<>();
            backingArray[loc].add(newPair);
        } else {
            ArrayList<Pair<K, V>> arrLis = backingArray[loc];
            Pair<K, V> foundPair = null;
            for (Pair<K, V> pr : arrLis) {
                if (pr.key.equals(key)) {
                    foundPair = pr;
                    break;
                }
            }
            if (foundPair == null) { // key is not in the list
                arrLis.add(newPair);
            } else {
                foundPair.value = value;
            }
        }
    }

    /**
     * Get the value stored in the hashtable given the key.
     * 
     * @param key the key
     * @return the value associated with the key
     */
    public V get(K key) {
        int loc = stringHasher(key.toString());
        if (backingArray[loc] == null) {
            return null;
        }
        ArrayList<Pair<K, V>> arrLis = backingArray[loc];
        for (Pair<K, V> pr : arrLis) {
            if (pr.key.equals(key)) {
                return pr.value;
            }
        }
        return null;
    }

    /**
     * The number of distinct keys in the hshtable.
     * This is quite inefficient as it has to count.  Would be better to just 
     * keep the count in a private instance variable.
     * 
     * @return The number of distinct keys in the hashtable
     */
    public int size() {
        int count = 0;
        for (int i = 0; i < backingArray.length; i++) {
            if (backingArray[i] != null)
                count += backingArray[i].size();
        }
        return count;
    }

    /**
     * A print representation of the hashtable.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < backingArray.length; i++) {
            if (backingArray[i] != null) {
                sb.append("\n" + i);
                ArrayList<Pair<K, V>> arrLis = backingArray[i];
                for (Pair<K, V> pr : arrLis) {
                    sb.append("  " + pr.toString());
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SepChainHT<String, String> sht = new SepChainHT<>(131);
        try {
            BookReader br = new BookReader("ham.txt");
            while (true) {
                String s = br.nextWord();
                if (s==null) break;
                sht.put(s, s);
            }
        } catch (Exception ee) {
            System.out.println(ee);
        }
        System.out.println(sht.size());
        System.out.println(sht);
        // System.out.println(sht.stringHasher("abcdefabcdefgabcdefgh"));
    }

    /**
     * @return true iff the hashtable contains the key.
     * @param key the ket to check for
     */
    @Override
    public boolean containsKey(K key) {
        V value = get(key);
        return value!=null;
    }

    /**
     * @return ArrayList of all keys
     */
    public ArrayList<K> returnKeyArrayList(){
        ArrayList<K> keys = new ArrayList<>();
        for(ArrayList<Pair<K,V>> arrLis : backingArray){
            if(arrLis != null){
                for(Pair<K,V> pr : arrLis){
                    keys.add(pr.key);
                }
            }
        }
        return keys;
    }

    /**
     * @return ArrayList of all values
     */
    public ArrayList<V> returnValueArrayList(){
        ArrayList<V> values = new ArrayList<>();
        for(ArrayList<Pair<K,V>> arrLis : backingArray){
            if(arrLis != null){
                for(Pair<K,V> pr : arrLis){
                    values.add(pr.value);
                }
            }
        }
        return values;
    }


    /**
     * @return a set containing all of the active keys in the hashtable.
     */
    @Override
    public Set<K> keySet() {
        TreeSet<K> set = new TreeSet<>();
        for (ArrayList<Pair<K,V>> arrLis : backingArray) {
            if (arrLis != null) {
                for (Pair<K,V> pr : arrLis) {
                    set.add(pr.key);
                }
            }
        }
        return set;
    }
}