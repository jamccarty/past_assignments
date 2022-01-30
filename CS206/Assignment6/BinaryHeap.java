import java.util.Random;

/**
 * A sorted binary tree where every "parent" nole is larger than its
 * two children. All methods are O(1) or O(log(n)).
 * 
 * @author gtowell
 * Created APRIL 6, 2020
 * Modified: April 12, 2021
 * Modified: April 19, 2021 by @author jmccarty
 * 	- added documentation
 * 	- made size a protected variable
 * 	- made backArray a protected variable
 */
@SuppressWarnings("unchecked")
public class BinaryHeap<K extends Comparable<K>, V> extends AbstractPriorityQueue<K, V>
{

	private static final int CAPACITY = 1032; //default capacity of instance for implicit constructor
	protected Pair<K,V>[] backArray; //Pair<K,V> array that binary tree is built on
    public int size; //number of non-empty places in tree

	public BinaryHeap() {
        this(CAPACITY); //backArray.length now equal to 1032
	}
	
	public BinaryHeap(int capacity) {
        backArray = new Pair[capacity]; //backArray.length now equal to capacity
	}

    /**
	 * @return number of non-empty places in tree
	 */
	@Override
    public int size()
    {
		return size;
    }

	/**
	 * @return true if there is no data in heap
	 */
    @Override
    public boolean isEmpty()
    {
		return size==0;
    }

	/**
	 * Adds new key/value pair to tree and sorts it into the appropriate spot.
	 * @return true if action is completed successfully
	 * @return false if BinaryHeap is full
	 */
    @Override
    public boolean offer(K key, V value)
	{
		if (size >= (backArray.length - 1))
			return false; //if tree is full return false
		int loc = size++; //find next available slot in tree (proceeding top-down, left-to-right)
		backArray[loc] = new Pair<K, V>(key, value); //put new Pair into slot
		int upp = (loc - 1) / 2; //finds parent node of location
		while (loc != 0) {//while not yet at top of tree
			//if new Pair's key is smaller than its parent's key,
			//switch them so that new pair is at parent location
			//and former parent is at child location
			if (0 > backArray[loc].compareTo(backArray[upp])) {
				Pair<K, V> tmp = backArray[upp];
				backArray[upp] = backArray[loc];
				backArray[loc] = tmp;
				loc = upp;
				upp = (loc - 1) / 2;
			} else {
				break; //if new pair is larger than parent key, break loop 
			}
		}
		return true; //if action completed successfully, return false
	}
	
	/**
	 * Removes the smallest key-value pair (smallness determined by key) from the heap
	 */
	private void removeTop()
    {
		size--; //remove one from size to signify one less non-empty slot

		//put pair from bottom-right-most slot (last filled slot in backArray) 
		//at the topmost position (backArray[0])
		//from hence forth this pair will be known as Moving Pair
		backArray[0] = backArray[size];
		backArray[size]=null;
		
		//the location of Moving Pair's parent
		int parentLoc=0;

		//reorganize heap
		while (true) 
		{
	    	int bestChildLoc; //location of pair Moving Pair will switch locations with
	    	int childLoc1 = parentLoc*2+1; //finding location of first child pair of parentLoc
	    	int childLoc2 = parentLoc*2+2; //finding location of second child pair of parentLoc
			
			//if location of first child is empty (past all occupied slots) break out of loop
	    	if (childLoc1>=size) 
				break;         
			//if location of second child is empty, automatically make bestChildLoc = childLoc1  
	    	if (childLoc2>=size)
	    	{
				bestChildLoc=childLoc1; 
	    	}
	    	else
	    	{//set bestChildLoc = to smaller child's location
				int cmp = backArray[childLoc1].compareTo(backArray[childLoc2]);
				if (cmp<=0)
		    		bestChildLoc=childLoc1;
				else
		    		bestChildLoc=childLoc2;
			}
			
			//if Moving Pair is less than backArray[bestChildLoc] then switch their locations
			//otherwise, break out of the loop (heap is organized again)
	    	if (0 > backArray[bestChildLoc].compareTo(backArray[parentLoc]))
	    	{
				Pair<K,V> tmp = backArray[bestChildLoc];
				backArray[bestChildLoc] = backArray[parentLoc];
				backArray[parentLoc] = tmp;
				parentLoc=bestChildLoc;
	    	}
	    	else {
				break;
	    	}
		}
    }

	@Override
	/**
	 * Removes smallest value (as organized by key) from heap and returns it
	 * @return smallest value
	 * @return null if heap is empty
	 */
	public V poll() {
		if (isEmpty())
			return null; //return null if heap is empty
		Pair<K,V> tmp = backArray[0]; //set tmp = to smallest Pair in heap (top value)
		removeTop(); //remove top value and reorganize
		return tmp.theV; //return smallest value
	}

	@Override
	/**
	 * @return smallest value (as organized by key) in heap
	 * @return null if heap is empty
	 */
	public V peek() {
		if (isEmpty())
			return null;
		return backArray[0].theV;
	}


	public static void main(String[] args) {
        BinaryHeap<Integer, String> pq = new BinaryHeap<>(CAPACITY);
        pq.offer(1,"Jane");
        pq.offer(10,"WET");
        pq.offer(5, "WAS");
        System.out.println(pq.poll());
        System.out.println(pq.poll());
        System.out.println(pq.poll());
        System.out.println();

        pq = new BinaryHeap<>(CAPACITY);
        pq.offer(2,"Jane");
        pq.offer(1,"WET");
        pq.offer(5, "WAS");
        System.out.println(pq.poll());
        System.out.println(pq.poll());
        System.out.println(pq.poll());
		
		Random r = new Random();
		BinaryHeap<Integer, Integer> pqi = new BinaryHeap<>(CAPACITY);
		pqi.poll();
		for (int i=0; i<10; i++)
			pqi.offer(r.nextInt(), i);
		while (pqi.peek()!=null)
			System.out.println(pqi.poll());

    }


}
