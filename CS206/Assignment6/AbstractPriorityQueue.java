/** 
 * Uses an Abstract Class because the abstract class can 
 * provide the definition of pair that is shared across all 
 * priority queue implementations.  Then each extending class must
 * provide its own implementation of the remaining methods
 * 
 * @author gtowell
 * Created: Feb 2020
 * Modified: Oct 18, 2020
 * Modified: Apr 12, 2020
 */
public abstract class AbstractPriorityQueue <K extends Comparable<K>, V> implements PriorityQInterface<K,V> {
     
    protected class Pair<L extends Comparable<L>, W> implements Comparable<Pair<L,W>> {
        /** Hold the key */
        final L theK;
        /** Hold the  value*/
        final W theV;
        /**
         * Create an Entry instance
         * @param kk the key
         * @param vv the value
         */
        public Pair(L kk, W vv) {
            theK = kk;
            theV = vv;
        }
		@Override
		public int compareTo(AbstractPriorityQueue<K, V>.Pair<L, W> o) {
            return theK.compareTo(o.theK);
        }

        public String toString() {
            return "{{"+theK+" " +theV+"}}";
        }

    }



    /**
     * The number of items in the queue
     * 
     * @return the number of items in the queie
     */
    public abstract int size();

    /**
     * Return true iff the queue is empty
     * 
     * @return true iff the queue is empty
     */
    public abstract boolean isEmpty();

    
    /**
     * Inserts the specified element into this queue if it is possible to do so
     * immediately without violating capacity restrictions, returning true upon
     * success and false on failure
     * 
     * @param k the key of the element to be added
     * @param v the value of the ek=lement 
     * @return true on success, false on failure
     */
    public abstract boolean offer(K k, V v);

    /**
     * Retrieves and removes the head of this priority queue.
     * 
     * @return the removed element, or null if the queue is empty
     */
    public abstract V poll();

    /**
     * Retrieves, but does not remove, the head of this priority queue. 
     * 
     * @return the element at the head of the queue or null if the queue is empty
     */
    public abstract V peek();


}