/**
 * A sorted ternary tree where every "parent" nole is larger than its
 * three children. All methods are O(1) or O(log3(n)).
 * 
 * @author jmccarty
 * Created: April 19, 2021
 * 
 * @param <K> key
 * @param <V> value
 */

@SuppressWarnings("unchecked")
public class TernaryHeap<K extends Comparable<K>, V> extends BinaryHeap<K, V>{

    private static final int CAPACITY = 1032; //default capacity of instance for implicit constructor

    public TernaryHeap(int capacity) {
        backArray = new Pair[capacity]; //backArray.length now equal to capacity
	}

    public TernaryHeap(){
        this(CAPACITY);
    }

    /**
	 * Adds new key/value pair to tree and sorts it into the appropriate spot.
	 * @return true if action is completed successfully
	 * @return false if BinaryHeap is full
	 */
    @Override
    public boolean offer(K key, V value)
	{
		if (size >= (backArray.length - 1)){
			return false; //if tree is full return false
		}
		int loc = size; //find next available slot in tree (proceeding top-down, left-to-right)
		size++;
		backArray[loc] = new Pair<K, V>(key, value); //put new Pair into slot
		int upp = (loc - 1) / 3; //finds parent node of location
		while (loc != 0) {//while not yet at top of tree
			//if new Pair's key is smaller than its parent's key,
			//switch them so that new pair is at parent location
			//and former parent is at child location
			if (0 > backArray[loc].compareTo(backArray[upp])) {
				Pair<K, V> tmp = backArray[upp];
				backArray[upp] = backArray[loc];
				backArray[loc] = tmp;
				loc = upp;
				upp = (loc - 1) / 3;
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
		backArray[size] = null;
		
		//the location of Moving Pair's parent
		int parentLoc=0;

        int bestChildLoc; //location of pair Moving Pair will switch locations with

		//reorganize heap
		while (true) 
		{
	    	int childLoc1 = parentLoc*3+1; //finding location of first child pair of parentLoc
	    	int childLoc2 = parentLoc*3+2; //finding location of second child pair of parentLoc
            int childLoc3 = parentLoc*3+3; //finding location of third child pair of parentLoc
			
			//if location of first child is empty (past all occupied slots) break out of loop
	    	if (childLoc1>=size) 
				break;      

			//if location of second child is empty, automatically make bestChildLoc = childLoc1  
	    	if (childLoc2>=size){
				childLoc2 = childLoc1; //if second child loc is empty, make both loc2 and loc3
				childLoc3 = childLoc1; //lead back to loc1, since that's the only one available
	    	}if	(childLoc3>=size){
				childLoc3 = childLoc1; //if third child loc is empty, make loc3 = loc1 
				//(same reasons as above)
			}

	    	//set bestChildLoc = to smaller child's location
			int cmp12 = backArray[childLoc1].compareTo(backArray[childLoc2]);
            int cmp23 = backArray[childLoc2].compareTo(backArray[childLoc3]);
            int cmp13 = backArray[childLoc1].compareTo(backArray[childLoc3]);
			if (cmp12 <= 0 && cmp13 <= 0)
		    	bestChildLoc=childLoc1;
			else if (cmp12 > 0 && cmp23 <= 0)
		    	bestChildLoc=childLoc2;
            else 
                bestChildLoc = childLoc3;
			
			//if Moving Pair is less than backArray[bestChildLoc] then switch their locations
			//otherwise, break out of the loop (heap is organized again)
	    	if (0 > backArray[bestChildLoc].compareTo(backArray[parentLoc])) {
				Pair<K,V> tmp = backArray[bestChildLoc];
				backArray[bestChildLoc] = backArray[parentLoc];
				backArray[parentLoc] = tmp;
				parentLoc=bestChildLoc;
	    	} else {
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
		this.removeTop(); //remove top value and reorganize
		return tmp.theV; //return smallest value
	}

    public static void main(String[] args){
        TernaryHeap<Integer, String> t = new TernaryHeap<>();
        
        t.offer(30, "thirty");
        System.out.println("peek: " + t.peek()); //should return thirty
        t.offer(100, "one hundred");
        System.out.println("peek: " + t.peek()); //should return thirty
        t.offer(10, "ten");
        System.out.println("peek: " + t.peek()); //should return ten
		System.out.println("size: " + t.size()); //should return false
		System.out.println("isEmpty(): " + t.isEmpty()); //should return false

		System.out.println();

        System.out.println("poll: " + t.poll()); //should return ten
        System.out.println("poll: " + t.poll()); //should return thirty
        System.out.println("poll: " + t.poll()); //should return one hundred
        System.out.println("poll: " + t.poll()); //should return null
        System.out.println("isEmpty() " + t.isEmpty()); //should return true
    }
}
