public interface PriorityQInterface<K extends Comparable<K>, V> {
    
    /**
     * The number of items in the queue
     * 
     * @return the number of items in the queie
     */
    int size();

    /**
     * Return true iff the queue is empty
     * 
     * @return true iff the queue is empty
     */
    boolean isEmpty();

    
    /**
     * Inserts the specified element into this queue if it is possible to do so
     * immediately without violating capacity restrictions, returning true upon
     * success and false on failure
     * 
     * @param k the key of the element to be added
     * @param v the value of the ek=lement 
     * @return true on success, false on failure
     */
    boolean offer(K k, V v);

    /**
     * Retrieves and removes the head of this priority queue.
     * 
     * @return the removed element, or null if the queue is empty
     */
    V poll();

    /**
     * Retrieves, but does not remove, the head of this priority queue. 
     * 
     * @return the element at the head of the queue or null if the queue is empty
     */
    V peek();


}