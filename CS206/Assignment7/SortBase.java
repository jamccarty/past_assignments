import java.util.Random;

/**
 * A set of untility functions and a base class that every sorter must extend.
 * @author gtowell
 * created: April 15, 2020
 * Modified: Nov 10, 2020
 */
public abstract class SortBase {

    /** a random number generator */
    Random random;

    /**
     * Default constructor.  It seeds the randome number 
     * generator with a value based on the time.
     */
    public SortBase()
    {
        this((int)System.nanoTime());
    }

    /**
     * Constructor with a seed for the random number generator.
     * @param seed
     */
    public SortBase(int seed) {
        random=new Random(seed);
    }

    /**
     * Return an array of size containing values 0..(size-1) 
     * in random order.  Uses the Fisher-Yates shuffle algorithm
     * @param size -- the size of the array
     * @return a shuffled array
     */
    public int[] fyShuffle(int size)    {
    	int[] rtn = orderedArray(size);
	    for (int i=0; i<size-2; i++)
	    {
	        int rr = i+random.nextInt(size-i);
	        int tmp = rtn[i];
	        rtn[i]=rtn[rr];
	        rtn[rr]=tmp;
	    }
	    return rtn;
    }
    
    /**
     * Created an array of integers of the specified size in which the items stored in the
     * array are exactly equals to the indices of the array.
     * @param size the size of the array to be generated
     * @return the ordered array.
     */
    public int[] orderedArray(int size)
    {
    	int[] rtn = new int[size]; 
	    for (int i=0; i<size; i++) rtn[i]=i;
	    return rtn;
    }

    /**
     * Return the entire contents of an array on a single line.
     * @param array the array 
     * @return a string containing the entire contents
     */
    public String stringArray(int[] array) {
        StringBuilder sb = new StringBuilder();
        stringArray(array, 0, array.length, sb);
        return sb.toString();
    }
    
    public String stringArray(int[] array, int startLoc, int endLoc) {
        StringBuilder sb = new StringBuilder();
        stringArray(array, startLoc, endLoc, sb);
        return sb.toString();
    }
    
    private void stringArray(int[] array, int startLoc, int endLoc, StringBuilder sb) {
        for (int i=startLoc; i<endLoc; i++) {
            sb.append(array[i] + " ");
        }
    }

    /**
     * Receives an array of integers and sorts that array, putting the contents of the array
     * into sorted order. That is, this function should change the ordering of the elements
     * in the provided array. 
     * @param array the array to be sorted
     */
    public abstract void sortInPlace(int[] array);
}